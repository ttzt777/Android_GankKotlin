package cc.bear3.osbear.data

/**
 * Description:
 * Author: TT
 * Since: 2020-03-21
 */
class UserInfo : NoticeResponse() {
    var uid: Long = 0
    var name: String? = null
    var gender: Int = 0
    var province: String? = null
    var city: String? = null
    var platforms: List<String>? = null
    var expertise: List<String>? = null
    var joinTime: String? = null
    var lastLoginTime: String? = null
    var portrait: String? = null
    var fansCount: Int = 0
    var favoriteCount: Int = 0
    var followersCount: Int = 0
}
