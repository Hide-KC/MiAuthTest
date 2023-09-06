package work.kcs_labo.miauth_test.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import work.kcs_labo.miauth_test.R

class HomeActivity : AppCompatActivity(), CoroutineScope {
  private val job = Job()
  override val coroutineContext = job + Dispatchers.Main
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.home_activity)
    startActivity(Intent(this@HomeActivity, AuthActivity::class.java))
  }
}