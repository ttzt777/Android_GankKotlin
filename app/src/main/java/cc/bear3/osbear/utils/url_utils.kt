package cc.bear3.osbear.utils

/**
 * Description:
 * Author: TT
 * Since: 2020-03-23
 */
internal fun getUrlParams(url: String?) : HashMap<String, String> {
    val resultMap = hashMapOf<String, String>()

    if (url.isNullOrEmpty() || !url.contains("?")) {
        return resultMap
    }

    val strings = url.split("?")
    if (strings.size < 2) {
        return resultMap
    }

    val params = strings[1].split("&")
    for (param in params) {
        val keyValues = param.split("=")
        if (keyValues.isEmpty() || keyValues.size != 2) {
            continue
        }

        resultMap[keyValues[0]] = keyValues[1]
    }

    return resultMap
}