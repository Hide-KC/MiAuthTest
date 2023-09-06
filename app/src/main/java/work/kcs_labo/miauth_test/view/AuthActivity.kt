package work.kcs_labo.miauth_test.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import work.kcs_labo.miauth_test.R
import work.kcs_labo.miauth_test.databinding.AuthActivityBinding
import work.kcs_labo.miauth_test.usecase.auth.RegisterAppCallback
import work.kcs_labo.miauth_test.usecase.auth.dto.ServerSoftware
import work.kcs_labo.miauth_test.usecase.preference.interactor.DataStoreInteractor
import work.kcs_labo.miauth_test.view.viewmodel.AuthActivityViewModel
import work.kcs_labo.miauth_test.view.viewmodel.AuthActivityViewModelFactory

class AuthActivity: AppCompatActivity(R.layout.auth_activity), CoroutineScope {
  private var job: Job? = null
  private var registerAppCallback: RegisterAppCallback? = null
  override val coroutineContext = Dispatchers.Main
  private val viewModel: AuthActivityViewModel by viewModels {
    AuthActivityViewModelFactory(
      application,
      DataStoreInteractor(this)
    )
  }
  private val binding: AuthActivityBinding by viewBinding()
  private val debounceTime = 500L

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding.misskeyServer.isChecked = true

    binding.targetSoftwareContainer.setOnCheckedChangeListener { _, id ->
      when (id) {
        R.id.misskey_server -> viewModel.targetSoftwareChanged(ServerSoftware.Misskey)
        R.id.mastodon_server -> viewModel.targetSoftwareChanged(ServerSoftware.Mastodon)
      }
    }

    binding.targetHost.addTextChangedListener(object: TextWatcher {
      override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

      override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

      override fun afterTextChanged(p0: Editable?) {
        binding.targetHost.text?.toString()?.let {
          job?.cancel()

          job = lifecycleScope.launch {
            delay(debounceTime)
            viewModel.hostChanged(it)
          }
        }
      }
    })

    binding.startAuthBtn.setOnClickListener {
      registerAppCallback = viewModel.startAuth(this)
    }
  }

  override fun onNewIntent(intent: Intent?) {
    super.onNewIntent(intent)
    println(intent)
    //https://miauth_test/callback?session=xxx
    //OAuth2だとJSONが返る

    intent?.data?.let {
      registerAppCallback?.let { callback ->
        viewModel.appRegistered(callback, intent)
      }
    } ?: throw IllegalStateException("intent is null")
  }
}