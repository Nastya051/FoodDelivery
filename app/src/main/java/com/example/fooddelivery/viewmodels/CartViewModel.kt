package com.example.fooddelivery.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.fooddelivery.data.DishForOrder
import com.example.fooddelivery.data.NODE_CART
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CartViewModel: ViewModel() {
    val response: MutableState<DataStateCart> = mutableStateOf(DataStateCart.Empty)

    init{
        fetchData()
    }

    fun fetchData() {
        val tempList = mutableListOf<DishForOrder>()
        response.value = DataStateCart.Loading
        FirebaseDatabase.getInstance().getReference(NODE_CART)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (DataSnap in snapshot.children) {
                        val foodItem = DataSnap.getValue(DishForOrder::class.java)
                        Log.d("MyLog","${DataSnap.key}")
                        foodItem?.key=DataSnap.key
                        if (foodItem != null)
                            tempList.add(foodItem)
                    }
                    response.value = DataStateCart.Success(tempList)
                }

                override fun onCancelled(error: DatabaseError) {
                    response.value = DataStateCart.Failure(error.message)
                }

            })
    }
}

