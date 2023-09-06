package work.kcs_labo.miauth_test.model

sealed class Token(open val token: String) {

  data class MiAuthToken(
    override val token: String,
    val user: User.MiUser.MiUserDetailed? = null
  ): Token(token)

  data class OAuth2Token(
    override val token: String,
    val tokenType: String,
    val scope: String,
    val createdAt: Long
  ): Token(token)
}