package cc.bear3.osbear.http.api

import cc.bear3.osbear.app.APP_ID
import cc.bear3.osbear.app.APP_REDIRECT_URL
import cc.bear3.osbear.app.APP_SECRET
import cc.bear3.osbear.data.TokenData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Description:
 * Author: TT
 * Since: 2020-03-21
 */
interface AccountApi {
    @GET("token")
    fun getToken(
        @Query("client_id") clientId: String = APP_ID,
        @Query("client_secret") clientSecret: String = APP_SECRET,
        @Query("grant_type") grantType: String = "authorization_code",
        @Query("redirect_uri") redirectUri: String = APP_REDIRECT_URL,
        @Query("code") code: String
    ): Observable<TokenData>

    @GET("token")
    fun refreshToken(
        @Query("client_id") clientId: String = APP_ID,
        @Query("client_secret") clientSecret: String = APP_SECRET,
        @Query("grant_type") grantType: String = "refresh_token",
        @Query("redirect_uri") redirectUri: String = APP_REDIRECT_URL,
        @Query("refresh_token") token: String
    ): Observable<TokenData>
}