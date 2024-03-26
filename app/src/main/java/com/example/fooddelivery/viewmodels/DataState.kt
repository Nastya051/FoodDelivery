package com.example.fooddelivery.viewmodels

import com.example.fooddelivery.data.Dish
import com.example.fooddelivery.data.DishForOrder

sealed class DataState {
    class Success(val data: MutableList<Dish>) : DataState()
    class Failure(val message: String) : DataState()
    object Loading : DataState()
    object Empty : DataState()
}

sealed class DataStateCart {
    class Success(val data: MutableList<DishForOrder>) : DataStateCart()
    class Failure(val message: String) : DataStateCart()
    object Loading : DataStateCart()
    object Empty : DataStateCart()
}

sealed class DataStateSumma {
    class Success(val data: MutableList<DishForOrder>) : DataStateSumma()
    class Failure(val message: String) : DataStateSumma()
    object Loading : DataStateSumma()
    object Empty : DataStateSumma()
}