package com.example.coffeefirst.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.coffeefirst.data.CartRepository
import com.example.coffeefirst.data.db.CartItem
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
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

    fun addToCart(item: CartItem) {
        viewModelScope.launch {
            cartRepository.addToCart(item)
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