package cc.bear3.osbear.utils

import android.view.View
import androidx.navigation.NavController
import cc.bear3.osbear.app.LoginManager

/**
 * Description:
 * Author: TT
 * Since: 2020-03-23
 */
private const val MIN_CLICK_DELAY_TIME = 500
private var lastTime = 0L

fun isFastClick(): Boolean {
    val crtTime = System.currentTimeMillis()
    val delta = crtTime - lastTime

    return if (delta in 0 until MIN_CLICK_DELAY_TIME) {
        true
    } else {
        lastTime = crtTime
        false
    }
}

internal inline fun bindOnClickListener(view: View, crossinline callback: () -> Unit, navController: NavController? = null) {
    view.setOnClickListener {
        if (isFastClick()) {
            return@setOnClickListener
        }

        if (navController != null) {
            if (!LoginManager.isLoginWithJump(navController)) {
                return@setOnClickListener
            }
        }

        callback()
    }
}