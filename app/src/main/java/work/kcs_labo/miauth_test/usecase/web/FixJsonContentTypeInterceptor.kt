package work.kcs_labo.miauth_test.usecase.web

import okhttp3.Interceptor
import okhttp3.Response

class FixJsonContentTypeInterceptor: Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response {
    val original = chain.request()

    val fixed = original.newBuilder()
      .header("content-type", "application/json")
      .build()

    return chain.proceed(fixed)
  }
}