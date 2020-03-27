package cc.bear3.osbear.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Description:
 * Author: TT
 * Since: 2020-01-30
 */
fun getTimeFormatString(time: Long, format: String) : String {
    val formatter = SimpleDateFormat(format, Locale.CHINA)
    return formatter.format(Date(time))
}

fun formatTime(time: String?, format: String = "yyyy-MM-dd'T'HH:mm:ss.SSS Z") : String{
    if (time == null) {
        return ""
    }

    val formatter = SimpleDateFormat(format, Locale.CHINA)
    val timeMs = formatter.parse(time.replace("Z", " UTC"))?.time ?: 0

    return getTimeFormatString(timeMs, "yyyy-MM-dd")
}