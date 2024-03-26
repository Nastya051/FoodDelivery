package com.example.fooddelivery.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fooddelivery.data.DishForOrder
import com.example.fooddelivery.R
import com.example.fooddelivery.deleteDish
import com.example.fooddelivery.ui.theme.GrayButton
import com.example.fooddelivery.updateDish
import com.example.fooddelivery.viewmodels.CartViewModel

@Composable
fun DishInCart(dish: DishForOrder, vm: CartViewModel) {
    val count = remember { mutableStateOf(dish.count) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .padding(vertical = 1.dp),
        shape = RectangleShape,
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.food_cart), "", modifier = Modifier
                    .padding(16.dp)
                    .scale(1.8f)
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .weight(0.25f)
            )
            Column(modifier = Modifier
                .padding(start = 16.dp)
                .weight(0.75f)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.65f)
                ) {
                    Text(
                        text = "${dish.name}",
                        fontSize = 14.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.W400,
                        modifier = Modifier.padding(bottom = 16.dp, top = 16.dp)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.35f)
                ) {
                    Button(
                        onClick = {
                            if (count.value!! > 1) {
                                count.value = count.value!! - 1
                                updateDish(dish.key!!, count.value!!)
                            } else
                                deleteDish(dish.key!!)
                            vm.fetchData()
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
                        onClick = {
                            count.value = count.value!! + 1
                            updateDish(dish.key!!, count.value!!)
                        },
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
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.End
                    ) {
                        if (dish.oldPrice != 0) {
                            Text(
                                text = "${dish.currentPrice} ₽",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.W500,
                                modifier = Modifier.padding(end = 16.dp),
                                color = Color.Black,
                                textAlign = TextAlign.End
                            )
                            Text(
                                text = "${dish.oldPrice} ₽",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.W400,
                                modifier = Modifier.padding(end = 16.dp),
                                textDecoration = TextDecoration.LineThrough,
                                textAlign = TextAlign.End
                            )
                        } else {
                            Text(
                                text = "${dish.currentPrice} ₽",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.W500,
                                modifier = Modifier
                                    .padding(end = 16.dp), textAlign = TextAlign.End
                            )
                        }
                    }
                }
            }
        }
    }
}