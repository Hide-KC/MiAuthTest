package work.kcs_labo.miauth_test.usecase.auth.interactor

import android.content.Context
import work.kcs_labo.miauth_test.usecase.auth.AuthUseCase
import work.kcs_labo.miauth_test.usecase.auth.dto.AuthTicket
import work.kcs_labo.miauth_test.usecase.auth.AuthResultCallback
import work.kcs_labo.miauth_test.usecase.auth.RegisterAppCallback

class OAuth2Interactor: AuthUseCase.OAuth2UseCase {

  override fun startAuth(context: Context, ticket: AuthTicket, resultCallback: AuthResultCallback): RegisterAppCallback {
    TODO("Not yet implemented")

  }

  override fun onDismiss() {}
}