package ir.matinyakhshi.onlineshop.presentation.product

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.matinyakhshi.onlineshop.data.local.dao.CartDao
import ir.matinyakhshi.onlineshop.data.local.entity.CartItemEntity // ✅ اصلاح پکیج CartItemEntity
import ir.matinyakhshi.onlineshop.data.model.ProductDto
import ir.matinyakhshi.onlineshop.data.remote.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val apiService: ApiService,
    private val cartDao: CartDao,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProductDetailUiState>(ProductDetailUiState.Loading)
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    private val productId: String? = savedStateHandle["productId"]

    init {
        productId?.let { loadProductDetail(it) }
    }

    fun loadProductDetail(id: String) {
        viewModelScope.launch {
            _uiState.value = ProductDetailUiState.Loading
            try {
                val product = apiService.getProductDetail(id)
                _uiState.value = ProductDetailUiState.Success(product)
            } catch (e: Exception) {
                _uiState.value = ProductDetailUiState.Error(e.localizedMessage ?: "خطا در دریافت اطلاعات محصول")
            }
        }
    }

    // اضافه کردن محصول به دیتابیس سبد خرید
    fun addToCart(product: ProductDto) {
        viewModelScope.launch {
            val cartItem = CartItemEntity(
                productId = product.id.toString(), // تبدیل به String در صورت Int بودن ID
                title = product.title,
                price = product.price,
                discountPrice = product.discountPrice, // مقداردهی قیمت تخفیف‌خورده (در صورت وجود در Dto)
                imageUrl = product.images.firstOrNull(),
                quantity = 1
            )
            cartDao.insertOrUpdate(cartItem) // ✅ اصلاح نام متد از insertOrUpdateItem به insertOrUpdate
        }
    }
}