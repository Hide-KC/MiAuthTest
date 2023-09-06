package work.kcs_labo.miauth_test.usecase.web.webrequest

import work.kcs_labo.miauth_test.model.SessionId
import work.kcs_labo.miauth_test.model.Token

sealed interface WebRequestUseCase {
  fun onDismiss()

  interface MiRequestUseCase: WebRequestUseCase {
    suspend fun getServerPublicInfo(host: String)
    suspend fun getUserInfo(token: Token.MiAuthToken)
    suspend fun requestToken(host: String, sessionId: SessionId): Token
  }

  interface MstdnRequestUseCase: WebRequestUseCase {
    suspend fun getServerPublicInfo()
    suspend fun getUserInfo()
  }
}