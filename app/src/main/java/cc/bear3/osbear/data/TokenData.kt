package cc.bear3.osbear.data

import cc.bear3.osbear.http.BaseResponse

/**
 * Description:
 * Author: TT
 * Since: 2020-03-23
 */
data class TokenData  (
    val access_token: String?,
    val expires_in: Long = 0,
    val refresh_token: String?,
    val token_type: String?,
    val uid: Int =0
): BaseResponse()