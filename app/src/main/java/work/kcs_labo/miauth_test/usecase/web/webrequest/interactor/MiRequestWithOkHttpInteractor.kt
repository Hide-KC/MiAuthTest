package work.kcs_labo.miauth_test.usecase.web.webrequest.interactor

import okhttp3.OkHttpClient
import work.kcs_labo.miauth_test.model.SessionId
import work.kcs_labo.miauth_test.model.Token
import work.kcs_labo.miauth_test.usecase.web.webrequest.WebRequestUseCase

class MiRequestWithOkHttpInteractor: WebRequestUseCase.MiRequestUseCase {
  private val client = OkHttpClient()

  override suspend fun getServerPublicInfo(host: String) {
    TODO("Not yet implemented")
  }

  override suspend fun getUserInfo(token: Token.MiAuthToken) {
    TODO("Not yet implemented")
  }

  override suspend fun requestToken(host: String, sessionId: SessionId): Token {
    TODO("Not yet implemented")
  }

  override fun onDismiss() {}
}