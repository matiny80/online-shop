package ir.matinyakhshi.onlineshop.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.matinyakhshi.onlineshop.data.local.dao.CartDao
import ir.matinyakhshi.onlineshop.data.remote.ApiService
import ir.matinyakhshi.onlineshop.data.remote.model.CreateOrderRequest
import ir.matinyakhshi.onlineshop.data.remote.model.OrderItemDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val apiService: ApiService,
    private val cartDao: CartDao
) : ViewModel() {

    private val _uiState = MutableStateFlow<CheckoutUiState>(CheckoutUiState.Idle)
    val uiState: StateFlow<CheckoutUiState> = _uiState.asStateFlow()

    fun submitOrder(
        storeId: String,
        receiverName: String,
        phoneNumber: String,
        address: String
    ) {
        viewModelScope.launch {
            _uiState.value = CheckoutUiState.Loading
            try {
                val cartItems = cartDao.getCartItems().first()
                if (cartItems.isEmpty()) {
                    _uiState.value = CheckoutUiState.Error("سبد خرید خالی است")
                    return@launch
                }

                val orderItems = cartItems.map {
                    OrderItemDto(
                        productId = it.productId,
                        quantity = it.quantity,
                        price = it.price
                    )
                }

                val request = CreateOrderRequest(
                    storeId = storeId,
                    receiverName = receiverName,
                    phoneNumber = phoneNumber,
                    address = address,
                    items = orderItems
                )

                val response = apiService.createOrder(request)
                if (response.isSuccessful) {
                    cartDao.clearCart()
                    _uiState.value = CheckoutUiState.Success()
                } else {
                    _uiState.value = CheckoutUiState.Error("خطا در ثبت سفارش")
                }
            } catch (e: Exception) {
                _uiState.value = CheckoutUiState.Error(e.localizedMessage ?: "خطای غیرمنتظره")
            }
        }
    }
}