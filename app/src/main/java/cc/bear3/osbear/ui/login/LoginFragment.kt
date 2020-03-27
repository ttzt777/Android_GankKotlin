package cc.bear3.osbear.ui.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import cc.bear3.osbear.app.APP_REDIRECT_URL
import cc.bear3.osbear.app.AUTHORIZE_URL
import cc.bear3.osbear.app.LoginManager
import cc.bear3.osbear.data.TokenData
import cc.bear3.osbear.databinding.FragmentLoginBinding
import cc.bear3.osbear.http.HttpError
import cc.bear3.osbear.http.HttpManager
import cc.bear3.osbear.http.HttpObserver
import cc.bear3.osbear.ui.common.BaseFragment
import cc.bear3.osbear.utils.bindOnClickListener
import cc.bear3.osbear.utils.getUrlParams
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers.io
import timber.log.Timber

/**
 * Description:
 * Author: TT
 * Since: 2020-03-23
 */
class LoginFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLoginBinding.inflate(inflater, container, false)

        subscribeUi(binding)

        return binding.root
    }

    private fun subscribeUi(binding: FragmentLoginBinding) {
        initWebView(binding.web)

        bindOnClickListener(binding.bar.getBackView(), { popUp() })
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView(webView: WebView) {
        with (webView.settings) {
            textZoom = 120
            useWideViewPort = true
            loadWithOverviewMode = true
            layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
            // 设置允许JS弹窗
            // 设置允许JS弹窗
            javaScriptCanOpenWindowsAutomatically = true
            // 设置与Js交互的权限
            // 设置与Js交互的权限
            javaScriptEnabled = true // 启用javascript

            val appCachePath: String = requireContext().applicationContext.cacheDir.absolutePath
            setAppCachePath(appCachePath) //设置数据库缓存路径

            allowFileAccess = true
            setAppCacheEnabled(false)
            blockNetworkImage = false
            cacheMode = WebSettings.LOAD_NO_CACHE //设置 缓存模式    

            domStorageEnabled = true //重点 开启 DOM storage API 功能  

            databaseEnabled = true //开启 database storage API 功能 

            // 设置允许JS弹窗
            // 设置允许JS弹窗
            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }

        //设置连接监听
        webView.webViewClient = object  : WebViewClient() {
            private var isGetCodeFlag = false

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                if (isGetCodeFlag) {
                    return false
                }

                request?.let {
                    Timber.d("url -- %s", it.url)
                    val redirectUrl = "${APP_REDIRECT_URL}?code="
                    if (it.url.toString().contains(redirectUrl)) {
                        isGetCodeFlag = true

                        val url = it.url.toString().substring(it.url.toString().lastIndexOf(redirectUrl))
                        val params = getUrlParams(url)

                        val code = params["code"]
                        if (code.isNullOrEmpty()) {
                            Timber.e("login failed")
                        } else {
                            getToken(code)
                        }

                        return false
                    }
                }

                return super.shouldOverrideUrlLoading(view, request)
            }

//            override fun shouldInterceptRequest(
//                view: WebView?,
//                request: WebResourceRequest?
//            ): WebResourceResponse? {
//                request?.let {
//                    Timber.d("url -- %s", it.url)
//                    val redirectUrl = "${APP_REDIRECT_URL}?code="
//                    if (it.url.toString().contains(redirectUrl)) {
//                        val url = it.url.toString().substring(it.url.toString().lastIndexOf(redirectUrl))
//                        val params = getUrlParams(url)
//
//                        val code = params["code"]
//                        if (code.isNullOrEmpty()) {
//                            Timber.e("login failed")
//                        } else {
//                            getToken(code)
//                        }
//
//                        return null
//                    }
//                }
//
//                return super.shouldInterceptRequest(view, request)
//            }
        }

        webView.loadUrl(AUTHORIZE_URL)
    }

    private fun getToken(code : String) {
        showLoadingDialog()

        HttpManager.accountApi.getToken(code = code)
            .subscribeOn(io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : HttpObserver<TokenData>() {
                override fun onSuccess(data: TokenData) {
                    if (data.access_token.isNullOrEmpty()) {
                        handleError(HttpError.DATA)
                        return
                    }

                    LoginManager.setToken(data.access_token, System.currentTimeMillis() + data.expires_in * 1000)
                    popUp()
                }

                override fun onFailed(error: HttpError) {
                    Timber.e("login failed")
                }

                override fun onFinish() {
                    dismissLoadingDialog()
                }
            })
    }
}