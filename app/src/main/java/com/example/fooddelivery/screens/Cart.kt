package com.example.fooddelivery.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fooddelivery.viewmodels.DataStateCart
import com.example.fooddelivery.data.DishForOrder
import com.example.fooddelivery.data.summaOrder
import com.example.fooddelivery.ui.theme.Orange
import com.example.fooddelivery.viewmodels.CartViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Cart(back: MutableState<Boolean>, cartviewModel: CartViewModel){
    cartviewModel.fetchData()
    Scaffold(
        topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
                    title = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Корзина", fontSize = 18.sp, fontWeight = FontWeight.W600)
                        }
                    },
                    navigationIcon = {
                        Row(
                            modifier = Modifier
                                .wrapContentWidth()
                                .padding(start = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            IconButton(onClick = {
                                back.value = true
                            }) {
                                Icon(Icons.Default.ArrowBack, "")
                            }
                        }
                    },
                    modifier = Modifier.shadow(10.dp)
                )
        },
        bottomBar = {
            Box(modifier = Modifier
                .shadow(1.dp)
                .fillMaxWidth()) {
                CustomButton(clickAction = {  }, textButton = "Заказать за ${summaOrder} ₽", icon = 0, colorConteiner = Orange, colorFont = Color.White)
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            SetData(viewModel = cartviewModel)
        }
    }
}

@Composable
fun SetData(viewModel: CartViewModel) {

    when (val result = viewModel.response.value) {
        is DataStateCart.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is DataStateCart.Success -> {
            ShowLazyListCart(result.data, viewModel)
        }

        is DataStateCart.Failure -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = result.message,
                )
            }
        }

        else -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Error Fetching data",
                )
            }
        }
    }
}

@Composable
fun ShowLazyListCart(data: MutableList<DishForOrder>, vm: CartViewModel) {
    var summa = 0
    if (data.isEmpty()) {
        Box(modifier = Modifier
            .fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "Пусто, выберите блюда\n" +
                        "в каталоге :)",
                fontSize = 16.sp,
                fontWeight = FontWeight.W400,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .wrapContentHeight(Alignment.CenterVertically)
            )
        }
    } else {
        LazyColumn{
            items(data){food ->
                DishInCart(food, vm)
                summa += food.currentPrice!!
//                summaOrder=summa
            }
        }
    }
}