package com.example.fooddelivery.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun CustomButton(
    clickAction: () -> Unit,
    textButton: String,
    oldPrice: String = "",
    icon: Int,
    colorConteiner: Color,
    colorFont: Color
) {
    Button(
        shape = RoundedCornerShape(8.dp), modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 32.dp),
        colors = ButtonDefaults.buttonColors(containerColor = colorConteiner),
        onClick = clickAction
    ) {
        if (icon != 0)
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "Cart",
                modifier = Modifier
                    .scale(2.5f)
                    .padding(end = 8.dp)
            )
        Text(textButton, fontWeight = FontWeight.W500, color = colorFont)
        Text(
            oldPrice,
            fontWeight = FontWeight.W400,
            color = Color.Gray,
            fontSize = 14.sp,
            modifier = Modifier.padding(start = 8.dp),
            style = TextStyle(textDecoration = TextDecoration.LineThrough)
        )
    }
}