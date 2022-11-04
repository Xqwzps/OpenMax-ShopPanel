package com.horizam.openmax_android.activities

import android.content.Context
import android.content.SharedPreferences

class PrefManager (var _context: Context) {
     var pref : SharedPreferences
     var editor : SharedPreferences.Editor

    // shared pref mode
    private var PRIVATE_MODE = 0

    companion object {
        // Shared preferences file name
        private const val PREF_NAME = "tiklPrefs"
        private const val WRITE_MODE = "writeMode"

    }
    init {
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }

    var writeMode : Boolean
        get() = pref.getBoolean(WRITE_MODE, true)
        set(value) {
            editor.putBoolean(WRITE_MODE, value)
            editor.apply()
            editor.commit()
        }

}