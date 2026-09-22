package ir.matinyakhshi.onlineshop.presentation.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.matinyakhshi.onlineshop.data.local.dao.CartDao
import ir.matinyakhshi.onlineshop.data.local.entity.CartItemEntity
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val cartDao: CartDao
) : ViewModel() {

    fun addToCart(productId: String, title: String, price: Long) {
        viewModelScope.launch {
            val item = CartItemEntity(
                productId = productId,
                title = title,
                price = price,
                quantity = 1
            )
            cartDao.insertOrUpdate(item)
        }
    }
}