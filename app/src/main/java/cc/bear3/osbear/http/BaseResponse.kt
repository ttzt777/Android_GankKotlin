package cc.bear3.osbear.http

import com.google.gson.annotations.SerializedName

/**
 * Description:
 * Author: TT
 * From: 2019/8/14
 * Last Version: 1.0.0
 * Last Change Time: 2019/8/14
 * ----------- History ---------
 * *-*
 * version:
 * description:
 * time: 2019/8/14
 * *-*
 */
open class BaseResponse {
    var error: String? = null
    @SerializedName("error_description")
    val errorDescription: String? = null
}