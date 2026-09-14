package com.lession.app.receiver

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.lession.app.MainActivity
import com.lession.app.R
import java.util.Calendar

const val NOTIFICATION_CHANNEL_ID = "lession_motivation"
const val NOTIFICATION_ID = 1001
const val ALARM_REQUEST_CODE = 5001

class MotivationNotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        val preferences = context.getSharedPreferences("lession_preferences", Context.MODE_PRIVATE)
        val notificationsEnabled = preferences.getBoolean("notifications_enabled", true)

        if (!notificationsEnabled) return

        val language = preferences.getString("language", "Español") ?: "Español"

        val messages = when (language) {
            "English" -> listOf(
                Pair("🔥 Time to train!", "A small workout today can take you closer to your goal."),
                Pair("💪 Your progress depends on you", "Get moving and show yourself what you're capable of."),
                Pair("🏆 Become your best version", "Every workout counts. Keep moving forward.")
            )
            "Español Latino" -> listOf(
                Pair("🔥 ¡Dale, es hora de entrenar!", "Ponete las pilas y metele un rato. Cada entrenamiento suma."),
                Pair("💪 ¡Vos podés!", "Dale movimiento al cuerpo y acercate un poco más a tu meta.")
            )
            else -> listOf(
                Pair("🔥 ¡Es hora de entrenar!", "Un pequeño entrenamiento hoy puede acercarte mucho a tu objetivo."),
                Pair("💪 Tu progreso depende de vos", "Ponete en movimiento y demostrate de lo que sos capaz.")
            )
        }

        val message = messages.random()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                return
            }
        }

        val notificationIntent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context, 900, notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.screen_dashboard)
            .setContentTitle(message.first)
            .setContentText(message.second)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message.second))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)
        scheduleNextNotification(context)
    }
}

fun scheduleNextNotification(context: Context) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, MotivationNotificationReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context, ALARM_REQUEST_CODE, intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val calendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 18)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        if (timeInMillis <= System.currentTimeMillis()) {
            add(Calendar.DAY_OF_YEAR, 1)
        }
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        if (alarmManager.canScheduleExactAlarms()) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        } else {
            alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        }
    } else {
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }
}

fun cancelNotifications(context: Context) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, MotivationNotificationReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context, ALARM_REQUEST_CODE, intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
    alarmManager.cancel(pendingIntent)
}
