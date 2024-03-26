package com.example.fooddelivery

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.fooddelivery.data.Dish
import com.example.fooddelivery.screens.CurrentFoodPage
import com.example.fooddelivery.ui.theme.FoodDeliveryTheme

class CurrentFoodActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodDeliveryTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Red),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val back = remember { mutableStateOf(false) }
                    val dish = intent.getSerializableExtra("key", Dish::class.java)
                    CurrentFoodPage(
                        back,
                        dish
                    )
                    if(back.value)
                        finish()
                }
            }
        }
    }
}

