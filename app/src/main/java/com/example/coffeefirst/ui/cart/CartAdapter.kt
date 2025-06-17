package com.example.coffeefirst.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.example.coffeefirst.data.db.CartItem
import com.example.coffeefirst.databinding.ItemCartBinding

class CartAdapter(
    private val onRemoveClick: (CartItem) -> Unit,
    private val onQuantityChange: (CartItem) -> Unit
) : ListAdapter<CartItem, CartAdapter.CartViewHolder>(DiffCallback()) {

    inner class CartViewHolder(val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CartItem) {
            binding.itemName.text = item.menuItemId
            binding.itemQuantity.text = item.quantity.toString()
            binding.btnRemove.setOnClickListener { onRemoveClick(item) }
            binding.btnIncrease.setOnClickListener {
                onQuantityChange(item.copy(quantity = item.quantity + 1))
            }
            binding.btnDecrease.setOnClickListener {
                if (item.quantity > 1) onQuantityChange(item.copy(quantity = item.quantity - 1))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCartBinding.inflate(inflater, parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<CartItem>() {
        override fun areItemsTheSame(old: CartItem, newItem: CartItem) = old.id == newItem.id
        override fun areContentsTheSame(old: CartItem, newItem: CartItem) = old == newItem
    }
}