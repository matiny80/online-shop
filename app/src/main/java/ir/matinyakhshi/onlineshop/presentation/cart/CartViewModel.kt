package ir.matinyakhshi.onlineshop.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.matinyakhshi.onlineshop.data.local.dao.CartDao
import ir.matinyakhshi.onlineshop.data.local.entity.CartItemEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartDao: CartDao
) : ViewModel() {

    val uiState: StateFlow<CartUiState> = cartDao.getCartItems()
        .map { items ->
            if (items.isEmpty()) {
                CartUiState.Empty
            } else {
                // ✅ تبدیل هر دو سمت ضرب به Long و اطمینان از عدم null بودن
                val totalPrice = items.sumOf { item ->
                    (item.price ?: 0L).toLong() * item.quantity.toLong()
                }

                val totalDiscount = items.sumOf { item ->
                    (item.discountPrice ?: 0L).toLong() * item.quantity.toLong()
                }

                val finalPrice = totalPrice - totalDiscount

                CartUiState.Success(
                    cartItems = items,
                    totalPrice = totalPrice,
                    totalDiscount = totalDiscount,
                    finalPrice = finalPrice
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CartUiState.Loading
        )

    fun increaseQuantity(item: CartItemEntity) {
        viewModelScope.launch {
            cartDao.updateQuantity(item.productId, item.quantity + 1)
        }
    }

    fun decreaseQuantity(item: CartItemEntity) {
        viewModelScope.launch {
            if (item.quantity > 1) {
                cartDao.updateQuantity(item.productId, item.quantity - 1)
            } else {
                cartDao.deleteCartItem(item.productId)
            }
        }
    }

    fun removeItem(item: CartItemEntity) {
        viewModelScope.launch {
            cartDao.deleteCartItem(item.productId)
        }
    }
}