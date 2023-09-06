package work.kcs_labo.miauth_test.usecase.preference

import work.kcs_labo.miauth_test.model.Token

//ホストをキーにする→トークンを払いだす
//どんな形式のトークンが払いだされるかは呼び出し側は知らない
//ただ欲しいサイト（ホスト）に対応したトークンが欲しい
//Interactor内でトークンを識別する必要がある
//基本DataStore以外に保存することはないとする

interface PreferenceUseCase {
  suspend fun storeToken(host: String, token: Token)
  suspend fun loadToken(host: String, token: Token): Token
}
