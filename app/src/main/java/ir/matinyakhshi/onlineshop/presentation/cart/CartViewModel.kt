package ir.matinyakhshi.onlineshop.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.matinyakhshi.onlineshop.data.local.dao.CartDao
import ir.matinyakhshi.onlineshop.data.local.entity.CartItemEntity
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CartUiState(
    val cartItems: List<CartItemEntity> = emptyList(),
    val rawTotalPrice: Double = 0.0,
    val totalDiscount: Double = 0.0,
    val shippingFee: Double = 100000.0,
    val finalPrice: Double = 0.0,
    val isLoading: Boolean = false
)

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartDao: CartDao
) : ViewModel() {

    val uiState: StateFlow<CartUiState> = cartDao.getCartItems()
        .map { items ->
            val rawTotal = items.sumOf { (it.price) * it.quantity }
            val discount = items.sumOf { ((it.price) - (it.discountPrice ?: it.price)) * it.quantity }
            val shipping = if (items.isNotEmpty()) 100000.0 else 0.0
            val final = (rawTotal - discount) + shipping

            CartUiState(
                cartItems = items,
                rawTotalPrice = rawTotal,
                totalDiscount = discount,
                shippingFee = shipping,
                finalPrice = final
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CartUiState(isLoading = true)
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

    fun removeItem(productId: String) {
        viewModelScope.launch {
            cartDao.deleteCartItem(productId)
        }
    }
}