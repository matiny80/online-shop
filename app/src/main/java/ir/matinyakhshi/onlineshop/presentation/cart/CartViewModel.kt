package ir.matinyakhshi.onlineshop.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.matinyakhshi.onlineshop.data.local.dao.CartDao
import ir.matinyakhshi.onlineshop.data.local.entity.CartItemEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartDao: CartDao
) : ViewModel() {

    // دریافت لحظه‌ای لیست آیتم‌های سبد خرید از Room
    val cartItems: StateFlow<List<CartItemEntity>> = cartDao.getCartItems()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun increaseQuantity(item: CartItemEntity) {
        viewModelScope.launch {
            cartDao.insertOrUpdate(item.copy(quantity = item.quantity + 1))
        }
    }

    fun decreaseQuantity(item: CartItemEntity) {
        viewModelScope.launch {
            if (item.quantity > 1) {
                cartDao.insertOrUpdate(item.copy(quantity = item.quantity - 1))
            } else {
                cartDao.deleteCartItem(item)
            }
        }
    }

    fun deleteItem(item: CartItemEntity) {
        viewModelScope.launch {
            cartDao.deleteCartItem(item)
        }
    }
}