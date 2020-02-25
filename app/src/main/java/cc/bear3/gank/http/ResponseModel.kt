package cc.bear3.gank.http

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
class ResponseModel<T> {
    var category : MutableList<String>? = null
    var error = false
    var results : T? = null
}