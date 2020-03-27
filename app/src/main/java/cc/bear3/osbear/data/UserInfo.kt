package cc.bear3.osbear.data

import cc.bear3.osbear.http.BaseResponse

/**
 * Description:
 * Author: TT
 * Since: 2020-03-21
 */
class UserInfo : BaseResponse() {
    var avatar: String? = null
    var email: String? = null
    var gender: String? = null
    var id: Long = 0
    var location: String? = null
    var name: String? = null
    var url: String? = null
}