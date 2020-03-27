package cc.bear3.osbear.app

/**
 * Description:
 * Author: TT
 * Since: 2020-03-24
 */
enum class LoginState(val index: Int) {
    Logout(0),
    InLogin(1),
    Login(2),
    GettingInfo(3),
    GotInfo(4)
}