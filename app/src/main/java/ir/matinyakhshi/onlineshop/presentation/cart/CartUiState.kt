package ir.matinyakhshi.onlineshop.presentation.cart

import ir.matinyakhshi.onlineshop.data.local.entity.CartItemEntity

sealed interface CartUiState {
    data object Loading : CartUiState
    data class Success(
        val cartItems: List<CartItemEntity>,
        val totalPrice: Long,
        val totalDiscount: Long,
        val finalPrice: Long
    ) : CartUiState
    data object Empty : CartUiState
    data class Error(val message: String) : CartUiState
}