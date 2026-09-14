package com.lession.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lession.app.ui.theme.Background
import com.lession.app.ui.theme.TextDark
import com.lession.app.ui.components.MenuItem

@Composable
fun MenuScreen(
    onHome: () -> Unit,
    onProfile: () -> Unit,
    onSettings: () -> Unit,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(22.dp)
    ) {
        Text(
            text = "Menú",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
        )

        Spacer(
            modifier = Modifier.height(25.dp)
        )

        MenuItem(
            icon = Icons.Default.Home,
            title = "Inicio",
            onClick = onHome
        )

        MenuItem(
            icon = Icons.Default.Person,
            title = "Perfil",
            onClick = onProfile
        )

        MenuItem(
            icon = Icons.Default.Settings,
            title = "Configuración",
            onClick = onSettings
        )

        MenuItem(
            icon = Icons.Default.Logout,
            title = "Cerrar sesión",
            onClick = onLogout
        )
    }
}
