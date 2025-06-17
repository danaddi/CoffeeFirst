package com.example.coffeefirst.data

import com.example.coffeefirst.data.db.CartDao
import com.example.coffeefirst.data.db.CartItem
import com.example.coffeefirst.data.db.toCartItem
import com.example.coffeefirst.data.db.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CartRepository(private val cartDao: CartDao) {

    fun getCartItems(userId: String): Flow<List<CartItem>> {
        return cartDao.getCartItems(userId).map { entities ->
            entities.map { it.toCartItem() }
        }
    }

    suspend fun addToCart(cartItem: CartItem) {
        cartDao.insert(cartItem.toEntity())
    }

    suspend fun removeFromCart(cartItem: CartItem) {
        cartDao.delete(cartItem.toEntity())
    }

    suspend fun updateCartItem(cartItem: CartItem) {
        cartDao.updateQuantity(cartItem.id, cartItem.quantity)
    }

    suspend fun clearCart(userId: String) {
        cartDao.clearCart(userId)
    }
}