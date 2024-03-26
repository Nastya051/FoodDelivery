package com.example.fooddelivery.screens

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fooddelivery.CurrentFoodActivity
import com.example.fooddelivery.data.Dish
import com.example.fooddelivery.R
import com.example.fooddelivery.ui.theme.GrayButton

@Composable
fun CurrentCard(showCurrentFood: MutableState<Boolean>, dish: Dish?) {
    var flag = remember { mutableStateOf(false) }
    val context = LocalContext.current
    Card(
        onClick = {
            showCurrentFood.value = true
            val intent = Intent(context, CurrentFoodActivity::class.java)
            intent.putExtra("key", dish)
            context.startActivity(intent)
        }, modifier = Modifier
            .wrapContentSize()
            .padding(8.dp)
    ) {
        var id_foto = R.drawable.non
        when (dish?.tag) {
            "sale" -> {
                id_foto = R.drawable.sale
            }

            "hot" -> {
                id_foto = R.drawable.hot
            }

            "vegetarian" -> {
                id_foto = R.drawable.without_meat
            }

            "non" -> {
                id_foto = R.drawable.non
            }
        }
        Image(
            painter = painterResource(id = id_foto),
            contentDescription = "Filter", modifier = Modifier
                .scale(1.5f)
                .padding(8.dp)

        )
        Image(
            painter = painterResource(id = R.drawable.food),
            contentDescription = "Filter", modifier = Modifier
                .padding(vertical = 12.dp, horizontal = 16.dp)
                .align(Alignment.CenterHorizontally)
        )
        Column(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "${dish?.name}",
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier
                    .padding(horizontal = 12.dp),
                textAlign = TextAlign.Center
            )
            Text(
                text = "${dish?.weight} г",
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(horizontal = 12.dp),
                textAlign = TextAlign.Center
            )
            if (flag.value)
                Bottom(flag)
            else
                if (dish?.tag == "sale")
                    CustomButton(
                        clickAction = { flag.value = true },
                        textButton = "${dish.currentPrice} ₽",
                        oldPrice = "${dish.oldPrice} ₽",
                        0,
                        Color.White,
                        Color.Black
                    )
                else
                    CustomButton(
                        clickAction = { flag.value = true },
                        textButton = "${dish?.currentPrice} ₽",
                        icon = 0,
                        colorConteiner = Color.White,
                        colorFont = Color.Black
                    )
        }
    }
}

@Composable
fun Bottom(flag: MutableState<Boolean>) {
    val count = remember { mutableStateOf(1) }
    Row(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .fillMaxWidth(), horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                if (count.value > 1)
                    count.value--
                else
                    flag.value = false
            }, shape = RoundedCornerShape(8.dp), colors = ButtonDefaults.buttonColors(
                GrayButton
            ), modifier = Modifier.padding(end = 8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.minus),
                "",
                modifier = Modifier.scale(2.5f)
            )
        }
        Text(
            text = count.value.toString(),
            fontSize = 16.sp,
            fontWeight = FontWeight.W500,
            color = Color.Black,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        Button(
            onClick = { count.value++ },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                GrayButton
            ),
            modifier = Modifier.padding(start = 8.dp, end = 16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.plus),
                "",
                modifier = Modifier.scale(2.5f)
            )
        }
    }
}