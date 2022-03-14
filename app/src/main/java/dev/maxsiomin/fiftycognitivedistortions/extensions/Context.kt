package dev.maxsiomin.fiftycognitivedistortions.extensions

import android.content.Context
import androidx.preference.PreferenceManager
import dev.maxsiomin.fiftycognitivedistortions.util.SharedPrefs

fun Context.getDefaultSharedPrefs(): SharedPrefs =
    PreferenceManager.getDefaultSharedPreferences(this)
