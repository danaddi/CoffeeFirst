package com.example.coffeefirst.data.db

import com.example.coffeefirst.data.db.CartItem
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class CartItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String,
    val menuItemId: String,
    val name: String,
    val quantity: Int
)

fun CartItemEntity.toCartItem(): CartItem {
    return CartItem(
        id = id,
        userId = userId,
        menuItemId = menuItemId,
        name = name,
        quantity = quantity
    )
}

fun CartItem.toEntity(): CartItemEntity {
    return CartItemEntity(
        id = id,
        userId = userId,
        menuItemId = menuItemId,
        name = name,
        quantity = quantity
    )
}
