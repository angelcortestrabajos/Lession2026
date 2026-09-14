package com.lession.app

import android.Manifest
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.lession.app.receiver.MotivationNotificationReceiver
import com.lession.app.ui.navigation.Screen
import com.lession.app.ui.screens.*
import com.lession.app.ui.theme.Background
import com.lession.app.ui.theme.Red
import java.util.Calendar

private const val NOTIFICATION_CHANNEL_ID = "lession_motivation"
private const val NOTIFICATION_ID = 1001
private const val ALARM_REQUEST_CODE = 5001

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        WindowCompat.setDecorFitsSystemWindows(
            window,
            false
        )

        WindowInsetsControllerCompat(
            window,
            window.decorView
        ).apply {
            hide(WindowInsetsCompat.Type.statusBars())
            hide(WindowInsetsCompat.Type.navigationBars())
            systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        createNotificationChannel()
        requestNotificationPermission()

        setContent {
            LessionApp()
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Motivación Lession",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notificaciones motivacionales de Lession"
            }

            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    200
                )
            }
        }
    }
}

/* ============================================================
   PROGRAMAR NOTIFICACIÓN
   ============================================================ */

fun scheduleNextNotification(context: Context) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, MotivationNotificationReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        ALARM_REQUEST_CODE,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val calendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 18)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)

        if (timeInMillis <= System.currentTimeMillis()) {
            add(Calendar.DAY_OF_YEAR, 1)
        }
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        if (alarmManager.canScheduleExactAlarms()) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        } else {
            alarmManager.setAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        }
    } else {
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }
}

/* ============================================================
   CANCELAR NOTIFICACIONES
   ============================================================ */

fun cancelNotifications(context: Context) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, MotivationNotificationReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        ALARM_REQUEST_CODE,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    alarmManager.cancel(pendingIntent)
    NotificationManagerCompat.from(context).cancelAll()
}

/* ============================================================
   APP
   ============================================================ */

@Composable
private fun LessionApp() {
    val context = LocalContext.current
    val preferences = remember {
        context.getSharedPreferences("lession_preferences", Context.MODE_PRIVATE)
    }

    var screen by remember { mutableStateOf(Screen.WELCOME) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var bodyType by remember { mutableStateOf("") }
    var rememberPassword by remember { mutableStateOf(false) }
    var avatarAdded by remember { mutableStateOf(false) }
    var language by remember {
        mutableStateOf(preferences.getString("language", "Español") ?: "Español")
    }
    var notificationsEnabled by remember {
        mutableStateOf(preferences.getBoolean("notifications_enabled", true))
    }
    var showDeleteDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        when (screen) {
            Screen.WELCOME -> {
                WelcomeScreen(
                    onLogin = { screen = Screen.LOGIN },
                    onSignup = { screen = Screen.SIGNUP }
                )
            }
            Screen.LOGIN -> {
                LoginScreen(
                    email = email,
                    password = password,
                    rememberPassword = rememberPassword,
                    onEmailChange = { email = it },
                    onPasswordChange = { password = it },
                    onRememberChange = { rememberPassword = it },
                    onLogin = {
                        if (email.isBlank() || password.isBlank()) return@LoginScreen
                        screen = Screen.PROFILE
                    },
                    onBack = { screen = Screen.WELCOME }
                )
            }
            Screen.SIGNUP -> {
                SignupScreen(
                    name = name,
                    email = email,
                    password = password,
                    confirmPassword = confirmPassword,
                    onNameChange = { name = it },
                    onEmailChange = { email = it },
                    onPasswordChange = { password = it },
                    onConfirmPasswordChange = { confirmPassword = it },
                    onSignup = {
                        if (name.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()) return@SignupScreen
                        if (password != confirmPassword) return@SignupScreen
                        screen = Screen.PROFILE
                    },
                    onBack = { screen = Screen.WELCOME }
                )
            }
            Screen.PROFILE -> {
                ProfileScreen(
                    name = name,
                    age = age,
                    height = height,
                    weight = weight,
                    gender = gender,
                    bodyType = bodyType,
                    avatarAdded = avatarAdded,
                    onAgeChange = { age = it },
                    onHeightChange = { height = it },
                    onWeightChange = { weight = it },
                    onGenderChange = { gender = it },
                    onBodyTypeChange = { bodyType = it },
                    onAvatarClick = { avatarAdded = true },
                    onSave = {
                        screen = Screen.DASHBOARD
                        if (notificationsEnabled) scheduleNextNotification(context)
                    },
                    onLogout = {
                        email = ""
                        password = ""
                        confirmPassword = ""
                        screen = Screen.LOGIN
                    },
                    onDeleteAccount = { showDeleteDialog = true }
                )
            }
            Screen.DASHBOARD -> {
                DashboardScreen(
                    onMenu = { screen = Screen.MENU }
                )
            }
            Screen.MENU -> {
                MenuScreen(
                    onHome = { screen = Screen.DASHBOARD },
                    onProfile = { screen = Screen.PROFILE },
                    onSettings = { screen = Screen.SETTINGS },
                    onLogout = {
                        email = ""
                        password = ""
                        screen = Screen.LOGIN
                    }
                )
            }
            Screen.SETTINGS -> {
                SettingsScreen(
                    language = language,
                    notificationsEnabled = notificationsEnabled,
                    onLanguageChange = {
                        language = it
                        preferences.edit().putString("language", it).apply()
                    },
                    onNotificationsChange = {
                        notificationsEnabled = it
                        preferences.edit().putBoolean("notifications_enabled", it).apply()
                        if (it) scheduleNextNotification(context) else cancelNotifications(context)
                    },
                    onBack = { screen = Screen.MENU }
                )
            }
        }

        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = {
                    Text(
                        text = "Eliminar cuenta",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    Text(
                        text = "¿Estás seguro de que querés eliminar tu cuenta? Se eliminarán tus datos y esta acción no se puede deshacer.",
                        fontSize = 16.sp
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showDeleteDialog = false
                            cancelNotifications(context)
                            name = ""
                            email = ""
                            password = ""
                            confirmPassword = ""
                            age = ""
                            height = ""
                            weight = ""
                            gender = ""
                            bodyType = ""
                            avatarAdded = false
                            screen = Screen.LOGIN
                        }
                    ) {
                        Text(
                            text = "Eliminar",
                            color = Red,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showDeleteDialog = false }
                    ) {
                        Text(text = "Cancelar", fontSize = 16.sp)
                    }
                }
            )
        }
    }
}
