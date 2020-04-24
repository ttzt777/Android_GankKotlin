package cc.bear3.osbear.http

import cc.bear3.osbear.app.BASE_API_URL
import cc.bear3.osbear.app.LoginManager
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Description:
 * Author: TT
 * Since: 2020-03-30
 */
class HttpRequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val url = request.url
        val method = request.method

        return if (url.toString() != (BASE_API_URL + "token")) {
            val sb = StringBuffer(url.toString())
            sb.append("&access_token=").append(LoginManager.token)
            val newRequest = request.newBuilder().url(sb.toString()).build()
            chain.proceed(newRequest)
        } else {
            chain.proceed(request)
        }
    }
}