package work.kcs_labo.miauth_test.usecase.auth.dto

import work.kcs_labo.miauth_test.usecase.auth.MiAuthPermission
import work.kcs_labo.miauth_test.model.SessionId
import java.util.UUID
import work.kcs_labo.miauth_test.model.Token

sealed class AuthTicket(open val token: Token) {

  abstract fun copyTicket(host: String): AuthTicket

  data class MiAuthTicket(
    val host: String,
    override val token: Token.MiAuthToken = Token.MiAuthToken("")
  ): AuthTicket(token) {

    val sessionId = SessionId(UUID.randomUUID().toString())
    val permissions =
      listOf(
        MiAuthPermission.ReadAccount,
        MiAuthPermission.WriteAccount,
        MiAuthPermission.ReadNotifications,
        MiAuthPermission.WriteNotes
      )

    override fun copyTicket(host: String): AuthTicket {
      return this.copy(host = host)
    }
  }

  data class OAuth2Ticket(
    val host: String,
    override val token: Token.OAuth2Token = Token.OAuth2Token("", "", "", 0L)
  ):
    AuthTicket(token) {
    override fun copyTicket(host: String): AuthTicket {
      return this.copy(host = host)
    }
  }
}