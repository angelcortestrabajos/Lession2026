package com.lession.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lession.app.ui.theme.Purple
import com.lession.app.ui.theme.TextDark

@Composable
fun ProgressItem(
    name: String,
    value: Float
) {
    Column(
        modifier = Modifier.padding(
            bottom = 15.dp
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = name,
                fontSize = 15.sp,
                color = TextDark
            )

            Text(
                text = "${(value * 100).toInt()}%",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Purple
            )
        }

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(9.dp)
                .clip(
                    RoundedCornerShape(10.dp)
                )
                .background(
                    Purple.copy(alpha = .12f)
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(value)
                    .fillMaxHeight()
                    .clip(
                        RoundedCornerShape(10.dp)
                    )
                    .background(Purple)
            )
        }
    }
}
