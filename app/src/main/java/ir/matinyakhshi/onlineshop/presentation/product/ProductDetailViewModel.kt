package ir.matinyakhshi.onlineshop.presentation.product

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.matinyakhshi.onlineshop.R
import ir.matinyakhshi.onlineshop.data.local.dao.CartDao
import ir.matinyakhshi.onlineshop.data.local.entity.CartItemEntity
import ir.matinyakhshi.onlineshop.presentation.home.ProductItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val cartDao: CartDao,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // نگهداری محصول انتخابی
    private val _product = MutableStateFlow<ProductItem?>(null)
    val product: StateFlow<ProductItem?> = _product.asStateFlow()

    // وضعیت لودینگ
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // لیست کامل داده‌های فیک (بدون وابستگی به Entity)
    private val dummyProducts = listOf(
        ProductItem("1", "پیراهن مردانه", "۱,۲۵۰,۰۰۰ تومان", R.drawable.image16),
        ProductItem("2", "پیراهن مردانه", "۴۵۰,۰۰۰ تومان", R.drawable.image8),
        ProductItem("3", "مانتو زنانه شیک", "۸۹۰,۰۰۰ تومان", R.drawable.image9),
        ProductItem("4", "پیراهن مردانه", "۳۲۰,۰۰۰ تومان", R.drawable.image14),
        ProductItem("5", "کفش ورزشی نایک", "۱,۲۵۰,۰۰۰ تومان", R.drawable.image11),
        ProductItem("6", "پیراهن مردانه", "۴۵۰,۰۰۰ تومان", R.drawable.image13),
        ProductItem("7", "مانتو زنانه شیک", "۸۹۰,۰۰۰ تومان", R.drawable.image9),
        ProductItem("8", "پیراهن مردانه", "۳۲۰,۰۰۰ تومان", R.drawable.image15)
    )

    init {
        val productId: String? = savedStateHandle["productId"]
        Log.d("PRODUCT_DEBUG", "Received productId: $productId")

        if (!productId.isNullOrEmpty()) {
            loadProduct(productId)
        } else {
            loadProduct("1")
        }
    }

    fun loadProduct(id: String) {
        viewModelScope.launch {
            _isLoading.value = true

            // پیدا کردن محصول از روی آی‌دی در لیست فیک
            val foundProduct = dummyProducts.find { it.id == id } ?: dummyProducts.first()
            _product.value = foundProduct

            _isLoading.value = false
        }
    }

    fun addToCart(productId: String, title: String, price: String) {
        viewModelScope.launch {
            // تبدیل قیمت متنی به Long برای ذخیره در سبد خرید
            val numericPrice = price.replace("[^0-9]".toRegex(), "").toLongOrNull() ?: 0L

            val item = CartItemEntity(
                productId = productId,
                title = title,
                price = numericPrice,
                quantity = 1
            )
            cartDao.insertOrUpdate(item)
        }
    }
}