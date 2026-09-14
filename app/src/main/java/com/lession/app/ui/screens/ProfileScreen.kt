package com.lession.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lession.app.ui.components.DropdownField
import com.lession.app.ui.theme.Purple
import com.lession.app.ui.theme.PurpleLight
import com.lession.app.ui.theme.Red
import com.lession.app.ui.theme.TextDark

@Composable
fun ProfileScreen(
    name: String,
    age: String,
    height: String,
    weight: String,
    gender: String,
    bodyType: String,
    avatarAdded: Boolean,
    onAgeChange: (String) -> Unit,
    onHeightChange: (String) -> Unit,
    onWeightChange: (String) -> Unit,
    onGenderChange: (String) -> Unit,
    onBodyTypeChange: (String) -> Unit,
    onAvatarClick: () -> Unit,
    onSave: () -> Unit,
    onLogout: () -> Unit,
    onDeleteAccount: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(22.dp)
    ) {
        Text(
            text = "Mi perfil",
            fontSize = 31.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
        )

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(125.dp)
                    .clip(CircleShape)
                    .background(Purple.copy(alpha = .12f))
                    .border(3.dp, Purple, CircleShape)
                    .clickable { onAvatarClick() },
                contentAlignment = Alignment.Center
            ) {
                if (avatarAdded) {
                    Text(
                        text = if (name.isNotBlank()) name.take(1).uppercase() else "L",
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        color = Purple
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.AddAPhoto,
                        contentDescription = "Agregar avatar",
                        tint = Purple,
                        modifier = Modifier.size(42.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Tocá para agregar avatar",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color.Gray,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = if (name.isBlank()) "Usuario" else name,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = age,
            onValueChange = onAgeChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Edad") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(14.dp))

        DropdownField(
            title = "Género",
            value = gender,
            options = listOf("Masculino", "Femenino", "Prefiero no decirlo"),
            onSelected = onGenderChange
        )

        Spacer(modifier = Modifier.height(14.dp))

        DropdownField(
            title = "Tipo de cuerpo",
            value = bodyType,
            options = listOf("Hectomorfo", "Endomorfo", "Mesomorfo"),
            onSelected = onBodyTypeChange
        )

        Spacer(modifier = Modifier.height(14.dp))

        OutlinedTextField(
            value = height,
            onValueChange = onHeightChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Altura (cm)") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(14.dp))

        OutlinedTextField(
            value = weight,
            onValueChange = onWeightChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Peso (kg)") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(25.dp))

        Button(
            onClick = onSave,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Purple
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "Guardar cambios",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Button(
            onClick = onLogout,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PurpleLight
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Icon(Icons.Default.Logout, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Cerrar sesión",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onDeleteAccount,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Red
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "Eliminar cuenta",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(25.dp))
    }
}
