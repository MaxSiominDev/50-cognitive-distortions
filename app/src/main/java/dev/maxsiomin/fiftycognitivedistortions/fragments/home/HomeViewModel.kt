package dev.maxsiomin.fiftycognitivedistortions.fragments.home

import android.app.Activity
import android.widget.Toast
import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.maxsiomin.fiftycognitivedistortions.R
import dev.maxsiomin.fiftycognitivedistortions.activity.MainActivity.Companion.cancelAlarm
import dev.maxsiomin.fiftycognitivedistortions.activity.MainActivity.Companion.setAlarm
import dev.maxsiomin.fiftycognitivedistortions.util.BaseViewModel
import dev.maxsiomin.fiftycognitivedistortions.util.SharedPrefsConfig.NOTIFICATIONS_ENABLED
import dev.maxsiomin.fiftycognitivedistortions.util.UiActions
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(uiActions: UiActions) : BaseViewModel(uiActions) {

    val notificationButtonTextLiveData = MutableLiveData<String>()

    private var notificationsEnabled = sharedPrefs.getBoolean(NOTIFICATIONS_ENABLED, true)

    init {
        updateNotificationsButtonLiveData()
    }

    private fun updateNotificationsButtonLiveData() {
        val resId = if (notificationsEnabled) R.string.disable_notifications else R.string.enable_notifications

        notificationButtonTextLiveData.value = getString(resId)
    }

    @MainThread
    fun onNotifyButtonClicked(activity: Activity) {
        notificationsEnabled = !notificationsEnabled
        sharedPrefs.edit().putBoolean(NOTIFICATIONS_ENABLED, notificationsEnabled).apply()
        updateNotificationsButtonLiveData()

        val resId = if (notificationsEnabled) R.string.notifications_enabled else R.string.notifications_disabled
        toast(resId, Toast.LENGTH_SHORT)

        if (notificationsEnabled) {
            setAlarm(activity)
        } else {
            cancelAlarm(activity)
        }
    }
}
