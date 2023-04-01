package com.latoin.ve.stlator.data

import android.content.Context

class Pref(context: Context) {

    private val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun setAuthSeen(isSeen: Boolean) {
        pref.edit().putBoolean(IS_AUTH, isSeen).apply()
    }

    fun isAuthSeen(): Boolean {
        return pref.getBoolean(IS_AUTH, false)
    }


    companion object {
        private const val PREF_NAME = "pref"
        const val IS_AUTH = "isAuthorized"
    }
}