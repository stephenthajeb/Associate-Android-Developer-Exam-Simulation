package com.dicoding.courseschedule.notification

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.data.Course
import com.dicoding.courseschedule.data.DataRepository
import com.dicoding.courseschedule.ui.home.HomeActivity
import com.dicoding.courseschedule.util.NOTIFICATION_CHANNEL_ID
import com.dicoding.courseschedule.util.NOTIFICATION_CHANNEL_NAME
import com.dicoding.courseschedule.util.executeThread
import java.util.*

class DailyReminder : BroadcastReceiver() {

    companion object{
        const val REMINDER_NOTIF = 1
        const val ACTION_TO_HOME = 2
    }

    override fun onReceive(context: Context, intent: Intent) {
        executeThread {
            val repository = DataRepository.getInstance(context)
            val courses = repository?.getTodaySchedule()
            Log.d("reminder", "test")
            courses?.let {
                if (it.isNotEmpty()) showNotification(context, it)
            }
        }
    }

    //TODO 12 : Implement daily reminder for every 06.00 a.m using AlarmManager
    fun setDailyReminder(context: Context) {
        Log.d("reminder", "on")
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context,DailyReminder::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, REMINDER_NOTIF, intent, 0)

        val repeatingTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 25)
            set(Calendar.SECOND, 0)
        }

        //For TestingPurpose
        // alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
        //    Calendar.getInstance().timeInMillis,
        //    1000*60,
        //    pendingIntent)

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
            repeatingTime.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent)
    }

    fun cancelAlarm(context: Context) {
        Log.d("reminder", "off")
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, DailyReminder::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, REMINDER_NOTIF, intent, 0)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
    }

    private fun showNotification(context: Context, content: List<Course>) {
        //TODO 13 : Show today schedules in inbox style notification & open HomeActivity when notification tapped
        Log.d("reminder", "shown")
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationStyle = NotificationCompat.InboxStyle()
        val timeString = context.resources.getString(R.string.notification_message_format)
        content.forEach {
            val courseData = String.format(timeString, it.startTime, it.endTime, it.courseName)
            notificationStyle.addLine(courseData)
        }

        val intent = Intent(context, HomeActivity::class.java)
        intent.action = Intent.ACTION_MAIN
        val pendingIntent = PendingIntent.getActivity(context, ACTION_TO_HOME, intent, 0)

        var notification = NotificationCompat
            .Builder(context, "daily-reminder")
            .setSmallIcon(R.drawable.ic_notifications)
            .setStyle(notificationStyle)
            .setContentIntent(pendingIntent)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            notification.setChannelId(NOTIFICATION_CHANNEL_ID)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(Calendar.getInstance().timeInMillis.toInt(), notification.build())
    }
}