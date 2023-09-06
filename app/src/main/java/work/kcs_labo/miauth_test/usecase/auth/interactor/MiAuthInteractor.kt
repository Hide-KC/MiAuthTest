package work.kcs_labo.miauth_test.usecase.auth.interactor

import android.content.Context
import android.content.Intent
import android.net.Uri
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import work.kcs_labo.miauth_test.R
import work.kcs_labo.miauth_test.model.SessionId
import work.kcs_labo.miauth_test.usecase.auth.AuthUseCase
import work.kcs_labo.miauth_test.usecase.auth.dto.AuthTicket
import work.kcs_labo.miauth_test.model.Token
import work.kcs_labo.miauth_test.model.User
import work.kcs_labo.miauth_test.usecase.auth.AuthResultCallback
import work.kcs_labo.miauth_test.usecase.auth.RegisterAppCallback
import java.io.IOException
import java.net.URLEncoder

class MiAuthInteractor: AuthUseCase.MiAuthUseCase, CoroutineScope {
  override val coroutineContext = Dispatchers.IO
  private val client = OkHttpClient()
  private lateinit var host: String
  private lateinit var sessionId: SessionId

  override fun startAuth(
    context: Context,
    ticket: AuthTicket,
    resultCallback: AuthResultCallback
  ): RegisterAppCallback {
    registerApp(context, ticket as AuthTicket.MiAuthTicket)

    val registerAppCallback = object: RegisterAppCallback {
      override suspend fun onRegistered(callbackIntent: Intent) {
        withContext(Dispatchers.IO) {
          try {
            delay(1000) //トークンリクエストが早すぎるとサーバが間に合わないので少し待つ
            val token = requestToken(context, callbackIntent)
            resultCallback.onAuthFinish(ticket, token) // Return to AuthViewModel
          } catch (err: Exception) {
            println(err)
            resultCallback.onFailed(err)
          }
        }
      }

      override suspend fun onFailed(err: Exception) {
        TODO("Not yet implemented")
      }
    }

    return registerAppCallback
  }

  private fun registerApp(context: Context, ticket: AuthTicket.MiAuthTicket) {
    host = ticket.host
    sessionId = ticket.sessionId

    val appIconUriEncode = URLEncoder.encode(
      "https://raw.githubusercontent.com/Hide-KC/TsanInAozora/master/icon128.png",
      "utf-8"
    )
    val origCallbackUrl =
      context.getString(R.string.callback_scheme_app) + context.getString(R.string.callback_path_prefix)
    val callbackUrl = URLEncoder.encode(origCallbackUrl, "utf-8")
    val joinPermissions = ticket.permissions.joinToString(
      "%2C", "", "", -1, "",
      transform = { p ->
        p.toString()
      })

    val url = StringBuilder()
      .append("https://")
      .append(host)
      .append("/miauth")
      .append("/${sessionId.id}")
      .append("?name=${context.getString(R.string.app_name)}")
//      .append("&icon=$appIconUriEncode")
      .append("&callback=$callbackUrl")
      .append("&permission=$joinPermissions")
      .toString()

    println("url: $url")
    val uri = Uri.parse(url)
    val intent = Intent(Intent.ACTION_VIEW, uri)
    context.startActivity(intent)
  }

  private fun requestToken(context: Context, callbackIntent: Intent): Token {
    val url = "https://$host/api/miauth/${sessionId.id}/check"
    val request = Request.Builder()
      .url(url)
      .post("{}".toRequestBody(contentType = "application/json".toMediaType()))
      .build()

    client.newCall(request).execute().use { response ->
      if (!response.isSuccessful) throw IOException("Unexpected code $response")
      val jsonData = response.body!!.string()
      val jsonObj = JSONObject(jsonData)
      println(jsonObj)
      val user = User.MiUser.MiUserDetailed.create(jsonObj)
      return Token.MiAuthToken(jsonObj.getString("token"))
    }
  }

  override fun onDismiss() {}
}