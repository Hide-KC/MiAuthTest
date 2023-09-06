package work.kcs_labo.miauth_test.usecase.auth.dto

sealed class ServerSoftware {
  data object Misskey: ServerSoftware()
  data object Mastodon: ServerSoftware()
}
