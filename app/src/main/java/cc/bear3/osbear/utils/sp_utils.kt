package cc.bear3.osbear.utils

import android.content.Context
import android.content.SharedPreferences
import cc.bear3.osbear.app.BearApp
import cc.bear3.osbear.app.SP_NAME_ACCOUNT

sealed class SpHandler(spName: String) {
    val sharedPreferences: SharedPreferences by lazy {
        BearApp.appContext.getSharedPreferences(
            spName,
            Context.MODE_PRIVATE
        )
    }

    fun getEditor(): SharedPreferences.Editor {
        return sharedPreferences.edit()
    }
}

object AccountSp : SpHandler(SP_NAME_ACCOUNT)

fun SpHandler.putString(key: String, value: String) {
    getEditor().putString(key, value).apply()
}

fun SpHandler.getString(key: String, defValue: String? = null): String? {
    return sharedPreferences.getString(key, defValue)
}

fun SpHandler.putInt(key: String, value: Int) {
    getEditor().putInt(key, value).apply()
}

fun SpHandler.getInt(key: String, defValue: Int = 0): Int {
    return sharedPreferences.getInt(key, defValue)
}

fun SpHandler.putLong(key: String, value: Long) {
    getEditor().putLong(key, value).apply()
}

fun SpHandler.getLong(key: String, defValue: Long = 0): Long {
    return sharedPreferences.getLong(key, defValue)
}

fun SpHandler.putFloat(key: String, value: Float) {
    getEditor().putFloat(key, value).apply()
}

fun SpHandler.getFloat(key: String, defValue: Float = 0f): Float {
    return sharedPreferences.getFloat(key, defValue)
}

fun SpHandler.putBoolean(key: String, value: Boolean) {
    getEditor().putBoolean(key, value).apply()
}

fun SpHandler.getBoolean(key: String, defValue: Boolean = false): Boolean {
    return sharedPreferences.getBoolean(key, defValue)
}