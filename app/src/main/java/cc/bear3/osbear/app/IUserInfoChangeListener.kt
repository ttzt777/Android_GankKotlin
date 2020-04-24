package cc.bear3.osbear.app

import cc.bear3.osbear.data.UserInfo

/**
 * Description:
 * Author: TT
 * Since: 2020-03-30
 */
interface IUserInfoChangeListener {
    fun onUserInfoChanged(userInfo: UserInfo)
}