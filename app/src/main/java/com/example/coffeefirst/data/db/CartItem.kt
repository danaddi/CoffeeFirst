package com.example.coffeefirst.data.db


data class CartItem(
    val id: Int = 0,
    val userId: String,
    val menuItemId: String,
    val quantity: Int
)
