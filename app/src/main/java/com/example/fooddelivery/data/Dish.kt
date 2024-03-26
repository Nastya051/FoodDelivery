package com.example.fooddelivery.data

import java.io.Serializable

data class Dish(
    val caloric: Double? = null,
    val carbohydrates: Double? = null,
    val category: String? = null,
    val composition: String? = null,
    val currentPrice: Int? = null,
    val fats: Double? = null,
    val name: String? = null,
    val oldPrice: Int? = null,
    val proteins: Double? = null,
    val tag: String? = null,
    val weight: Int? = null
): Serializable
