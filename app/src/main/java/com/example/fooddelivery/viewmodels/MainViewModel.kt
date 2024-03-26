package com.example.fooddelivery.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.fooddelivery.data.Dish
import com.example.fooddelivery.data.DishForOrder
import com.example.fooddelivery.data.NODE_CART
import com.example.fooddelivery.data.NODE_DISHES
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainViewModel() : ViewModel() {

    val response: MutableState<DataState> = mutableStateOf(DataState.Empty)
    val responseSumma: MutableState<DataStateSumma> = mutableStateOf(DataStateSumma.Empty)

     fun fetchDatabyCategory(filter: String) {
        val tempList = mutableListOf<Dish>()
        response.value = DataState.Loading
        FirebaseDatabase.getInstance().getReference(NODE_DISHES).orderByChild("category").equalTo(filter)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (DataSnap in snapshot.children) {
                        val foodItem = DataSnap.getValue(Dish::class.java)
                        if (foodItem != null)
                            tempList.add(foodItem)
                    }
                    response.value = DataState.Success(tempList)
                }

                override fun onCancelled(error: DatabaseError) {
                    response.value = DataState.Failure(error.message)
                }

            })
    }

    fun fetchDatabyCategoryAndFilter(category: String, filter: MutableList<String>) {
        val tempList = mutableListOf<Dish>()
        response.value = DataState.Loading
        FirebaseDatabase.getInstance().getReference(NODE_DISHES).orderByChild("category").equalTo(category)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (DataSnap in snapshot.children) {
                        val foodItem = DataSnap.getValue(Dish::class.java)
                        Log.d("MyLog", "filters in view model ${filter[0]} ${filter[1]} ${filter[2]}")
                        if (foodItem != null && (foodItem.tag == filter[0] || foodItem.tag == filter[1] || foodItem.tag == filter[2]))
                            tempList.add(foodItem)
                    }
                    response.value = DataState.Success(tempList)
                }

                override fun onCancelled(error: DatabaseError) {
                    response.value = DataState.Failure(error.message)
                }

            })
    }

    fun fetchDatabyName(name: String) {
        val tempList = mutableListOf<Dish>()
        response.value = DataState.Loading
        FirebaseDatabase.getInstance().getReference(NODE_DISHES).orderByChild("name").equalTo(name)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (DataSnap in snapshot.children) {
                        val foodItem = DataSnap.getValue(Dish::class.java)
                        Log.d("MyLog", "find name ${foodItem?.name}")
                        if (foodItem != null) {
                            tempList.add(foodItem)
                        }
                    }
                    response.value = DataState.Success(tempList)
                }

                override fun onCancelled(error: DatabaseError) {
                    response.value = DataState.Failure(error.message)
                }

            })
    }

    fun fetchDataSummaCart() {
        val tempList = mutableListOf<DishForOrder>()
        responseSumma.value = DataStateSumma.Loading
        FirebaseDatabase.getInstance().getReference(NODE_CART)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (DataSnap in snapshot.children) {
                        val foodItem = DataSnap.getValue(DishForOrder::class.java)
                        Log.d("MyLog", "find name ${foodItem?.name}")
                        if (foodItem != null) {
                            tempList.add(foodItem)
                        }
                    }
                    responseSumma.value = DataStateSumma.Success(tempList)
                }
                override fun onCancelled(error: DatabaseError) {
                    responseSumma.value = DataStateSumma.Failure(error.message)
                }

            })
    }
}