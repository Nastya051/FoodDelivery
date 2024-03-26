package com.example.fooddelivery.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fooddelivery.data.Dish
import com.example.fooddelivery.R
import com.example.fooddelivery.addDish
import com.example.fooddelivery.ui.theme.Orange


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CurrentFoodPage(
    back: MutableState<Boolean>,
    dish: Dish?
) {
    Scaffold {
        Row(modifier = Modifier.padding(start = 16.dp, top = 16.dp)) {
            FloatingActionButton(
                onClick = {
                    back.value = true

                }, shape = CircleShape, containerColor = Color.White
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    ""
                )
            }
        }
            FoodInfo(
                dish
            )
    }
}

@Composable
fun FoodInfo(
    dish: Dish?
) {
    val context = LocalContext.current
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp)) {
        Image(
            painter = painterResource(id = R.drawable.food),
            contentDescription = "food",
        )
        Text(text = "${dish?.name}", fontSize = 34.sp,modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxHeight()
            .weight(0.2f)
            )
        Text(text = "${dish?.composition}", color = Color.Gray,modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .fillMaxHeight()
            .weight(0.1f))
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .fillMaxHeight()
            .weight(0.1f)
        ) {
            Text(text = "Вес", color = Color.Gray, modifier = Modifier
                .fillMaxWidth()
                .weight(weight = 0.8f)
                .align(Alignment.CenterVertically))
            Text(text = "${dish?.weight} г", modifier = Modifier
                .fillMaxWidth()
                .weight(weight = 0.2f)
                .align(Alignment.CenterVertically), textAlign = TextAlign.End)
        }
        Divider(color = Color.LightGray, thickness = 1.dp)
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .fillMaxHeight()
            .weight(0.1f)) {
            Text(text = "Энерг. ценность", color = Color.Gray, modifier = Modifier
                .fillMaxWidth()
                .weight(weight = 0.7f)
                .align(Alignment.CenterVertically))
            Text(text = "${dish?.caloric} ккал", modifier = Modifier
                .fillMaxWidth()
                .weight(weight = 0.3f)
                .align(Alignment.CenterVertically), textAlign = TextAlign.End)
        }
        Divider(color = Color.LightGray, thickness = 1.dp)
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .fillMaxHeight()
            .weight(0.1f)) {
            Text(text = "Белки", color = Color.Gray, modifier = Modifier
                .fillMaxWidth()
                .weight(weight = 0.8f)
                .align(Alignment.CenterVertically))
            Text(text = "${dish?.proteins} г", modifier = Modifier
                .fillMaxWidth()
                .weight(weight = 0.2f)
                .align(Alignment.CenterVertically), textAlign = TextAlign.End)
        }
        Divider(color = Color.LightGray, thickness = 1.dp)
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .fillMaxHeight()
            .weight(0.1f)) {
            Text(text = "Жиры", color = Color.Gray, modifier = Modifier
                .fillMaxWidth()
                .weight(weight = 0.8f)
                .align(Alignment.CenterVertically))
            Text(text = "${dish?.fats} г", modifier = Modifier
                .fillMaxWidth()
                .weight(weight = 0.2f)
                .align(Alignment.CenterVertically), textAlign = TextAlign.End)
        }
        Divider(color = Color.LightGray, thickness = 1.dp)
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .fillMaxHeight()
            .weight(0.1f)) {
            Text(text = "Углеводы", color = Color.Gray, modifier = Modifier
                .fillMaxWidth()
                .weight(weight = 0.8f)
                .align(Alignment.CenterVertically))
            Text(text = "${dish?.carbohydrates} г", modifier = Modifier
                .fillMaxWidth()
                .weight(weight = 0.2f)
                .align(Alignment.CenterVertically), textAlign = TextAlign.End)
        }
        Divider(color = Color.LightGray, thickness = 1.dp)
        CustomButton(clickAction = {
            addDish(currPrice = dish?.currentPrice!!, name = dish.name!!, oldPrice = dish.oldPrice!!, count = 1)
            Toast.makeText(context, "Товар добавлен в корзину!", Toast.LENGTH_SHORT).show()
                                   },
            textButton = "В корзину за ${dish?.currentPrice} ₽", icon = 0, colorConteiner = Orange, colorFont = Color.White)
    }
}