package dev.maxsiomin.fiftycognitivedistortions.activity

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.maxsiomin.fiftycognitivedistortions.R
import dev.maxsiomin.fiftycognitivedistortions.extensions.openInBrowser
import dev.maxsiomin.fiftycognitivedistortions.util.BaseViewModel
import dev.maxsiomin.fiftycognitivedistortions.util.SharedPrefsConfig.NOTIFICATIONS_ENABLED
import timber.log.Timber
import java.time.LocalTime

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val navController: NavController by lazy {
        findNavController(R.id.fragmentContainer)
    }

    private val viewModel by viewModels<BaseViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registerNotificationChannel()

        tryRestartAlarm()
    }

    private fun registerNotificationChannel() {
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW)
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }

    private fun tryRestartAlarm() {
        cancelAlarm(this)

        if (viewModel.sharedPrefs.getBoolean(NOTIFICATIONS_ENABLED, true))
            setAlarm(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.overflow_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.infoItem -> goToInfo()
            R.id.aboutMeItem -> goToAboutMe()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun goToInfo() {
        if (navController.currentDestination!!.id == R.id.infoFragment)
            return
        navController.navigate(R.id.infoFragment)
    }

    private fun goToAboutMe() {
        openInBrowser("https://maxsiomin.dev")
    }

    companion object {
        const val CHANNEL_ID = "New distortion"
        const val CHANNEL_NAME = "New distortion"

        fun cancelAlarm(context: Context) {
            val intent = Intent(context, AlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(pendingIntent)

            Timber.i("cancelAlarm executed")
        }

        fun setAlarm(context: Context) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, AlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
            alarmManager.set(AlarmManager.RTC_WAKEUP, calculateAlarmTime(), pendingIntent)

            Timber.i("setAlarm executed")
        }

        private fun calculateAlarmTime(): Long {
            val now = LocalTime.now()
            val _8am = LocalTime.of(8, 0)
            val _1pm = LocalTime.of(13, 0)
            val _6pm = LocalTime.of(18, 0)

            return System.currentTimeMillis() + when {
                now.isAfter(_6pm) -> {
                    _8am.toSecondOfDay() + LocalTime.of(23, 59).toSecondOfDay() - now.toSecondOfDay()
                }

                now.isBefore(_8am) -> {
                    _8am.toSecondOfDay() - now.toSecondOfDay()
                }

                now.isBefore(_1pm) -> {
                    _1pm.toSecondOfDay() - now.toSecondOfDay()
                }

                else -> {
                    _6pm.toSecondOfDay() - now.toSecondOfDay()
                }
            } * 1000L
        }
    }
}
