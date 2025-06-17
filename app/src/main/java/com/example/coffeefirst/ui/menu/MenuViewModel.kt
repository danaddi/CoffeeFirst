package com.example.coffeefirst.ui.menu

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffeefirst.R
import com.example.coffeefirst.data.CartRepository
import com.example.coffeefirst.data.model.MenuItem
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val userId = auth.currentUser?.uid ?: ""

    val menuItems = listOf(
        MenuItem("coffee1", "Капучино", R.drawable.latte, "Кофе"),
        MenuItem("coffee2", "Латте", R.drawable.latte, "Кофе"),
        MenuItem("tea1", "Зеленый чай", R.drawable.water, "Чай"),
        MenuItem("food1", "Сэндвич", R.drawable.hamburger, "Еда"),
        MenuItem("dessert1", "Чизкейк", R.drawable.cheesecake, "Десерты")
    )

    private val _selectedCategory = MutableStateFlow("Кофе")
    val selectedCategory = _selectedCategory.asStateFlow()

    val filteredItems = selectedCategory.map { category ->
        menuItems.filter { it.category == category }
    }.stateIn(viewModelScope, SharingStarted.Lazily, menuItems.filter { it.category == "Кофе" })

    fun selectCategory(category: String) {
        Log.d("MenuViewModel", "Category selected: $category")
        _selectedCategory.value = category
    }
}
