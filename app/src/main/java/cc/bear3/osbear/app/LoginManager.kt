package cc.bear3.osbear.app

import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import cc.bear3.osbear.R
import cc.bear3.osbear.data.UserInfo

/**
 * Description:
 * Author: TT
 * Since: 2020-03-21
 */
object LoginManager {
    var token: String? = null
        private set(value) {
            field = value

            if (loginState.value!!.index <  LoginState.Login.index) {
                loginState.value = LoginState.Login
            }
        }
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

    var userInfo: UserInfo? = null

    init {

    }

    fun setToken(token: String, expires: Long) {
        this.token = token
        this.expires = expires
    }

    fun logout() {

    }

    fun isLoginWithJump(navController: NavController) : Boolean{
        return if (isLogin()) {
            true
        } else {
            navController.navigate(R.id.login_fragment)
            false
        }
    }

    fun isLogin() : Boolean {
        return loginState.value?.index ?: LoginState.Logout.index >= LoginState.Login.index
    }

    private fun checkTokenExpires() {
        if (loginState.value == null || loginState.value!!.index < LoginState.Login.index) {
            return
        }

        if (expires - System.currentTimeMillis() < AUTO_REFRESH_TOKEN_LIMIT) {
            refreshToken()
        }
    }

    private fun getUserInfo() {

    }

    private fun refreshToken() {

    }
}