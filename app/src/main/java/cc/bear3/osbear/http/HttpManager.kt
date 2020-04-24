package cc.bear3.osbear.http

import cc.bear3.osbear.BuildConfig
import cc.bear3.osbear.app.BASE_API_URL
import cc.bear3.osbear.http.api.AccountApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Description:
 * Author: TT
 * From: 2019/5/8
 * Last Version: 1.0.0
 * Last Change Time: 2019/5/8
 * ----------- History ---------
 * *-*
 * version:
 * description:
 * time: 2019/5/8
 * *-*
 */
object HttpManager {
    private const val TIMEOUT = 10L

    val accountApi: AccountApi by lazy { getApi(AccountApi::class.java) }

    // OkHttp 客户端
    private val mOkHttpClient: OkHttpClient by lazy {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(HttpRequestInterceptor())
//                    .dns(ApiDns())
//                    .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(HttpLogInterceptor())
        }
        builder.build()
    }

    /**
     * 获取Api
     *
     * @param cls
     * @param <T>
     * @return
    </T> */
    private fun <T> getApi(cls: Class<T>): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_API_URL)
            .client(mOkHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
//            .addConverterFactory(GsonConverterFactory.create(buildGson()))//定义int，后台返回string，防止解析失败报错（解析异常默认返回0）
            .build()

        return retrofit.create(cls)
    }
}