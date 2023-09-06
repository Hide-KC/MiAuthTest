package work.kcs_labo.miauth_test.usecase.preference.interactor

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import work.kcs_labo.miauth_test.model.Token
import work.kcs_labo.miauth_test.usecase.preference.PreferenceUseCase

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "tokens")

class DataStoreInteractor(private val context: Context): PreferenceUseCase, CoroutineScope {
  private val job = Job()
  override val coroutineContext = job + Dispatchers.IO

  override suspend fun storeToken(host: String, token: Token) {
    withContext(coroutineContext) {
      val key = stringPreferencesKey(host)
      when (token) {
        is Token.MiAuthToken -> {
          context.dataStore.edit { tokens ->
            tokens[key] = token.token
          }
        }
        is Token.OAuth2Token -> {
          context.dataStore.edit { tokens ->
            tokens[key] = token.token
          }
        }
      }
    }
  }

  override suspend fun loadToken(host: String, token: Token): Token {
    return withContext(coroutineContext) {
      val key = stringPreferencesKey(host)

      val tokenFlow = when(token) {
        is Token.MiAuthToken -> {
          context.dataStore.data.map { tokens ->
            val result = tokens[key] ?: ""
            token.copy(result)
          }
        }
        is Token.OAuth2Token -> {
          context.dataStore.data.map { tokens ->
            val result = tokens[key] ?: ""
            token.copy(result)
          }
        }
      }
      tokenFlow.last()
    }
  }
}