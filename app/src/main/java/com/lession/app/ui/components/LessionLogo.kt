package com.lession.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lession.app.ui.theme.Purple

@Composable
fun LessionLogo() {
    Box(
        modifier = Modifier
            .size(150.dp)
            .clip(RoundedCornerShape(35.dp))
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "L",
            color = Purple,
            fontSize = 82.sp,
            fontWeight = FontWeight.Black
        )
    }
}
