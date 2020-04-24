package cc.bear3.osbear.app

import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import cc.bear3.osbear.R
import cc.bear3.osbear.data.TokenData
import cc.bear3.osbear.data.UserInfo
import cc.bear3.osbear.http.HttpError
import cc.bear3.osbear.http.HttpManager
import cc.bear3.osbear.http.HttpObserver
import cc.bear3.osbear.utils.*
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Description:
 * Author: TT
 * Since: 2020-03-21
 */
object LoginManager {
    var token: String? = null
        private set
        get() {
            return if (field.isNullOrEmpty()) {
                ""
            } else {
                checkTokenExpires()
                field
            }
        }

    // token过期时间
    private var expires: Long = 0

    val loginState = MutableLiveData(LoginState.Logout)

    var userInfo: UserInfo = UserInfo()
        private set(value) {
            field = value

            for (listener in userInfoChangeListeners) {
                listener.onUserInfoChanged(field)
            }
        }

    private val userInfoChangeListeners = HashSet<IUserInfoChangeListener>()

    private var refreshDisposable: Disposable? = null
    private var userInfoDisposable: Disposable? = null

    init {
        token = AccountSp.getString(AccountSp.SP_TOKEN)
        expires = AccountSp.getLong(AccountSp.SP_EXPIRES)

        if (!token.isNullOrEmpty() && expires > System.currentTimeMillis()) {
            loginState.value = LoginState.Login
            loadUserInfo()
        } else {
            loginState.value = LoginState.Logout
        }
    }

    fun setToken(token: String, expires: Long) {
        this.token = token
        this.expires = expires

        AccountSp.getEditor()
            .putString(AccountSp.SP_TOKEN, token)
            .putLong(AccountSp.SP_EXPIRES, expires)
            .apply()

        if (loginState.value?.index ?: LoginState.Logout.index < LoginState.Login.index) {
            loginState.value = LoginState.Login
            loadUserInfoFromServer()
        }
    }

    fun logout() {
        refreshDisposable?.let {
            it.dispose()
            refreshDisposable = null
        }

        userInfoDisposable?.let {
            it.dispose()
            userInfoDisposable = null
        }

        loginState.value = LoginState.Logout
        token = null
        expires = System.currentTimeMillis()
        userInfo = UserInfo()
        AccountSp.getEditor().clear()
    }

    fun isLoginWithJump(navController: NavController): Boolean {
        return if (isLogin()) {
            true
        } else {
            navController.navigate(R.id.login_fragment)
            false
        }
    }

    fun isLogin(): Boolean {
        return loginState.value?.index ?: LoginState.Logout.index >= LoginState.Login.index
    }

    fun getUserInfoIfNeeded() {
        if (loginState.value?.index ?: LoginState.Logout.index >= LoginState.GettingInfo.index) {
            return
        }

        loadUserInfoFromServer()
    }

    fun refreshUserInfo() {
        if (loginState.value?.index ?: LoginState.Logout.index < LoginState.Login.index) {
            return
        }

        loadUserInfoFromServer()
    }

    fun addUserInfoChangeListener(listener: IUserInfoChangeListener) {
        userInfoChangeListeners.add(listener)
    }

    fun removeUserInfoChangeListener(listener: IUserInfoChangeListener) {
        userInfoChangeListeners.remove(listener)
    }

    private fun checkTokenExpires(): Boolean {
        if (loginState.value == null || loginState.value!!.index < LoginState.Login.index) {
            return true
        }

        if (expires - System.currentTimeMillis() <= 0) {
            logout()
            return true
        } else if (expires - System.currentTimeMillis() < AUTO_REFRESH_TOKEN_LIMIT) {
            refreshToken()
        }

        return false
    }

    private fun loadUserInfo() {
        loadUserInfoFromSp()

        loadUserInfoFromServer()
    }

    private fun loadUserInfoFromSp() {
        val userInfoString = AccountSp.getString(AccountSp.SP_USER_INFO)

        userInfoString?.let {
            if (it.isEmpty()) {
                return@let
            }

            val gson = Gson()
            val userInfo = gson.fromJson(it, UserInfo::class.java)

            with(userInfo) {
                if (!name.isNullOrEmpty() && uid != 0L) {
                    this@LoginManager.userInfo = this
                    loginState.value = LoginState.GotInfo
                }
            }
        }
    }

    private fun loadUserInfoFromServer() {
        if (checkTokenExpires()) {
            return
        }

        if (userInfoDisposable != null) {
            return
        }

        if (crtLoginStateIndex() < LoginState.GettingInfo.index) {
            loginState.value = LoginState.GettingInfo
        }

        HttpManager.accountApi.getMyInformation()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : HttpObserver<UserInfo>() {
                override fun onSubscribe(d: Disposable) {
                    super.onSubscribe(d)

                    userInfoDisposable = d
                }

                override fun onFinish() {
                    super.onFinish()

                    userInfoDisposable?.dispose()
                    userInfoDisposable = null
                }

                override fun onSuccess(data: UserInfo) {
                    userInfo = data

                    saveUserInfoToSp()

                    if (crtLoginStateIndex() < LoginState.GotInfo.index) {
                        loginState.value = LoginState.GotInfo
                    }
                }

                override fun onFailed(error: HttpError) {

                }
            })
    }

    private fun refreshToken() {
        if (token.isNullOrEmpty() || expires <= System.currentTimeMillis()) {
            return
        }

        if (refreshDisposable != null) {
            return
        }

        HttpManager.accountApi.refreshToken(token = token!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : HttpObserver<TokenData>() {
                override fun onSubscribe(d: Disposable) {
                    super.onSubscribe(d)

                    refreshDisposable = d
                }

                override fun onFinish() {
                    super.onFinish()
                    refreshDisposable?.dispose()
                    refreshDisposable = null
                }

                override fun onSuccess(data: TokenData) {
                    if (data.refresh_token.isNullOrEmpty()) {
                        handleError(HttpError.DATA)
                        return
                    }

                    setToken(
                        data.refresh_token,
                        System.currentTimeMillis() + data.expires_in * 1000
                    )
                }

                override fun onFailed(error: HttpError) {

                }
            })
    }

    private fun saveUserInfoToSp() {
        val userInfoString: String = Gson().toJson(userInfo)

        AccountSp.putString(AccountSp.SP_USER_INFO, userInfoString)
    }

    private fun crtLoginStateIndex(): Int {
        return loginState.value?.index ?: LoginState.Logout.index
    }
}