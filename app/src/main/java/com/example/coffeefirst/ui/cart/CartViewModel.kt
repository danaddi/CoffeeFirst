package com.example.coffeefirst.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.coffeefirst.data.CartRepository
import com.example.coffeefirst.data.db.CartItem
import com.google.firebase.auth.FirebaseAuth
import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val auth: FirebaseAuth
) : ViewModel() {
    private val userId = auth.currentUser!!.uid

    val cartItems = cartRepository.getCartItems(userId).asLiveData()

    fun addToCart(newItem: CartItem) {
        viewModelScope.launch {
            val currentItems = cartRepository.getCartItemsOnce(userId)
            val existingItem = currentItems.find { it.menuItemId == newItem.menuItemId }
            if (existingItem != null) {
                val updatedItem = existingItem.copy(quantity = existingItem.quantity + newItem.quantity)
                cartRepository.updateCartItem(updatedItem)
                Log.d("Cart","Обновляю количество для itemId=${updatedItem.id}, новое количество=${updatedItem.quantity}")
            } else {
                cartRepository.addToCart(newItem)
                Log.d("Cart","Добавляю новый элемент в корзину menuItemId=${newItem.menuItemId}")
            }
        }
    }



    fun updateCartItem(item: CartItem) {
        viewModelScope.launch {
            cartRepository.updateCartItem(item)
        }
    }

    fun removeItem(item: CartItem) {
        viewModelScope.launch {
            cartRepository.removeFromCart(item)
        }
    }
}