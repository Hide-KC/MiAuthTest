package work.kcs_labo.miauth_test.usecase.auth

import android.content.Context
import android.content.Intent
import work.kcs_labo.miauth_test.usecase.auth.dto.AuthTicket
import work.kcs_labo.miauth_test.model.Token

sealed interface AuthUseCase {
  fun startAuth(context: Context, ticket: AuthTicket, resultCallback: AuthResultCallback): RegisterAppCallback
  fun onDismiss()
  interface MiAuthUseCase: AuthUseCase
  interface OAuth2UseCase: AuthUseCase
}

sealed interface AuthResultCallback {
  fun onAuthFinish(ticket: AuthTicket, token: Token)
  fun onFailed(err: Exception)
  interface MiAuthResultCallback: AuthResultCallback
  interface OAuth2ResultCallback: AuthResultCallback
}

interface RegisterAppCallback {
  suspend fun onRegistered(callbackIntent: Intent)
  suspend fun onFailed(err: Exception)
}