package com.example.fooddelivery

import com.example.fooddelivery.data.DishForOrder
import com.example.fooddelivery.data.NODE_CART
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

fun addDish(currPrice: Int, name: String, oldPrice: Int, count: Int){
    val database = Firebase.database
    val myRef = database.getReference(NODE_CART)
    val dishid = myRef.push().key
    myRef.child(dishid.toString()).child("count").setValue(2)

    val dish = DishForOrder(
        currentPrice = currPrice,
        name = name,
        oldPrice = oldPrice,
        count = count
    )
    myRef.child(dishid.toString()).child("count").setValue(2)
    myRef.child(dishid.toString()).setValue(dish)
}

fun updateDish(key: String, count: Int){
    val database = Firebase.database
    val myRef = database.getReference(NODE_CART)
    myRef.child(key).child("count").setValue(count)
}

fun deleteDish(key: String){
    val database = Firebase.database
    val myRef = database.getReference(NODE_CART)
    myRef.child(key).removeValue()
}