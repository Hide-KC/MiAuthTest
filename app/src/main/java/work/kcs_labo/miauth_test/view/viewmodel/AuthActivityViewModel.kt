package work.kcs_labo.miauth_test.view.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import work.kcs_labo.miauth_test.model.Token
import work.kcs_labo.miauth_test.usecase.auth.AuthResultCallback
import work.kcs_labo.miauth_test.usecase.auth.AuthUseCase
import work.kcs_labo.miauth_test.usecase.auth.RegisterAppCallback
import work.kcs_labo.miauth_test.usecase.auth.dto.AuthTicket
import work.kcs_labo.miauth_test.usecase.auth.dto.ServerSoftware
import work.kcs_labo.miauth_test.usecase.auth.interactor.MiAuthInteractor
import work.kcs_labo.miauth_test.usecase.auth.interactor.OAuth2Interactor
import work.kcs_labo.miauth_test.usecase.preference.PreferenceUseCase

class AuthActivityViewModel(
  private val app: Application,
  private val preferenceUseCase: PreferenceUseCase
): AndroidViewModel(app), CoroutineScope {
  override val coroutineContext = Dispatchers.Main

  private var authUseCase: AuthUseCase? = MiAuthInteractor()
  private var ticket: AuthTicket = AuthTicket.MiAuthTicket("")
  private var host: String = ""

  private fun setAuthUseCase(interactor: AuthUseCase) {
    authUseCase?.onDismiss()
    authUseCase = interactor
  }


  fun targetSoftwareChanged(serverSoftware: ServerSoftware) {
     ticket = when (serverSoftware) {
      is ServerSoftware.Misskey -> AuthTicket.MiAuthTicket(host)
      is ServerSoftware.Mastodon -> AuthTicket.OAuth2Ticket(host)
    }
  }

  fun hostChanged(host: String) {
    this.host = host
  }

  fun startAuth(context: Context): RegisterAppCallback {
    setAuthUseCase(
      when (ticket) {
        is AuthTicket.MiAuthTicket -> MiAuthInteractor()
        is AuthTicket.OAuth2Ticket -> OAuth2Interactor()
      }
    )

    val resultCallback = when (ticket) {
      is AuthTicket.MiAuthTicket -> {
        object: AuthResultCallback.MiAuthResultCallback {
          override fun onAuthFinish(ticket: AuthTicket, token: Token) {
            (ticket as AuthTicket.MiAuthTicket).let {
              viewModelScope.launch {
                preferenceUseCase.storeToken(it.host, token)
                println("MiAuth finished: token = ${token.token}")
              }
            }
          }

          override fun onFailed(err: Exception) {
            println(err)
          }
        }
      }

      is AuthTicket.OAuth2Ticket -> {
        object: AuthResultCallback.OAuth2ResultCallback {
          override fun onAuthFinish(ticket: AuthTicket, token: Token) {
            TODO("Not yet implemented")
          }

          override fun onFailed(err: Exception) {
            TODO("Not yet implemented")
          }
        }
      }
    }

    return authUseCase?.startAuth(context, ticket, resultCallback)
      ?: throw IllegalStateException("authUseCase is null")
  }

  fun appRegistered(registerAppCallback: RegisterAppCallback, callbackIntent: Intent) {
    viewModelScope.launch {
      registerAppCallback.onRegistered(callbackIntent)
    }
  }

  override fun onCleared() {
    super.onCleared()
    authUseCase?.onDismiss()
    authUseCase = null
  }

}