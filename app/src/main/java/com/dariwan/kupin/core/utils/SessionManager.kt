package com.dariwan.kupin.core.utils

import android.content.SharedPreferences
import com.dariwan.kupin.core.utils.Constant.PREFS_NAME
import android.content.Context
import com.dariwan.kupin.core.utils.Constant.KEY_NAME
import com.dariwan.kupin.core.utils.Constant.KEY_USER_EMAIL

class SessionManager(context: Context) {
    private var sharedPref: SharedPreferences =
        context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private val editor = sharedPref.edit()

    fun setStringPref(prefString: String, value: String) {
        editor.putString(prefString, value)
        editor.apply()
    }

    val getUsername = sharedPref.getString(KEY_NAME, "")
    val getEmail = sharedPref.getString(KEY_USER_EMAIL, "")

    fun clearData() {
        editor.clear().apply()
    }

}