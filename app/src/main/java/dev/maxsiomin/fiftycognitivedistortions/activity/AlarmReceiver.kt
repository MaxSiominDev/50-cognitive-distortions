package dev.maxsiomin.fiftycognitivedistortions.activity

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import dev.maxsiomin.fiftycognitivedistortions.R
import dev.maxsiomin.fiftycognitivedistortions.activity.MainActivity.Companion.CHANNEL_ID
import dev.maxsiomin.fiftycognitivedistortions.activity.MainActivity.Companion.setAlarm
import dev.maxsiomin.fiftycognitivedistortions.extensions.getDefaultSharedPrefs
import dev.maxsiomin.fiftycognitivedistortions.util.SharedPrefsConfig.DISTORTIONS_SHOWED
import timber.log.Timber

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        Timber.i("onReceive called")

        val i = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_IMMUTABLE)

        with (context.getDefaultSharedPrefs()) {
            this.edit().putInt(DISTORTIONS_SHOWED, this.getInt(DISTORTIONS_SHOWED, 1) + 1).apply()
        }

        setAlarm(context)

        showNotification(context, pendingIntent)
    }

    private fun showNotification(context: Context, intent: PendingIntent) {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(context.getString(R.string.new_distortion))
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(intent)
            .setAutoCancel(true)
            .build()

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(0, notification)
    }
}
