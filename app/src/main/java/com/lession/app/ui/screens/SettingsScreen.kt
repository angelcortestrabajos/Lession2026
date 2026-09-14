package com.lession.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lession.app.ui.components.DropdownField
import com.lession.app.ui.components.NotificationSetting
import com.lession.app.ui.components.SettingSwitch
import com.lession.app.ui.theme.Purple
import com.lession.app.ui.theme.TextDark

@Composable
fun SettingsScreen(
    language: String,
    notificationsEnabled: Boolean,
    onLanguageChange: (String) -> Unit,
    onNotificationsChange: (Boolean) -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )
            .padding(22.dp)
    ) {
        Text(
            text = "Configuración",
            fontSize = 31.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
        )

        Spacer(
            modifier = Modifier.height(25.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Language,
                        contentDescription = null,
                        tint = Purple,
                        modifier = Modifier.size(30.dp)
                    )

                    Spacer(
                        modifier = Modifier.width(14.dp)
                    )

                    Text(
                        text = "Idioma",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                }

                Spacer(
                    modifier = Modifier.height(15.dp)
                )

                DropdownField(
                    title = "Seleccionar idioma",
                    value = language,
                    options = listOf(
                        "Español",
                        "Español Latino",
                        "English"
                    ),
                    onSelected = onLanguageChange
                )
            }
        }

        Spacer(
            modifier = Modifier.height(15.dp)
        )

        NotificationSetting(
            enabled = notificationsEnabled,
            onChange = onNotificationsChange
        )

        Spacer(
            modifier = Modifier.height(13.dp)
        )

        SettingSwitch(
            title = "Alertas de esfuerzo",
            description = "Avisarme antes de alcanzar un nivel peligroso",
            defaultValue = true
        )

        Spacer(
            modifier = Modifier.height(13.dp)
        )

        SettingSwitch(
            title = "Modo oscuro",
            description = "Cambiar la apariencia de la aplicación",
            defaultValue = false
        )

        Spacer(
            modifier = Modifier.height(25.dp)
        )

        Button(
            onClick = onBack,
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Purple
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "Volver",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
