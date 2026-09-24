package ir.matinyakhshi.onlineshop.presentation.admin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.matinyakhshi.onlineshop.data.model.Product
import ir.matinyakhshi.onlineshop.data.repository.AdminRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val repository: AdminRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // دریافت لیست محصولات به صورت زنده از دیتابیس Room
    val productsList: StateFlow<List<Product>> = repository.products
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // وضعیت‌های فرم افزودن/ویرایش
    var currentProductId by mutableStateOf<String?>(null)
        private set

    var title by mutableStateOf("")
    var price by mutableStateOf("")
    var discountPercent by mutableStateOf("")
    var stockCount by mutableStateOf("")
    var description by mutableStateOf("")
    var selectedCategory by mutableStateOf("کالای دیجیتال")
    var errorMessage by mutableStateOf<String?>(null)

    init {
        // دریافت productId از Navigation
        val productId: String? = savedStateHandle["productId"]
        if (!productId.isNullOrEmpty() && productId != "{productId}") {
            loadProductData(productId)
        }
    }

    fun loadProductData(id: String) {
        currentProductId = id
        viewModelScope.launch {
            val product = repository.getProductById(id)
            product?.let {
                title = it.title
                price = it.price.toString()
                discountPercent = it.discountPercent.toString()
                stockCount = it.stockCount.toString()
                description = it.description
                selectedCategory = it.category
            }
        }
    }

    fun saveProduct(onSuccess: () -> Unit) {
        // اعتبارسنجی ورودی‌ها
        if (title.isBlank() || price.isBlank() || stockCount.isBlank()) {
            errorMessage = "لطفاً تمامی فیلدهای ضروری را پر کنید"
            return
        }

        val parsedPrice = price.toLongOrNull() ?: 0L
        val parsedDiscount = discountPercent.toIntOrNull() ?: 0
        val parsedStock = stockCount.toIntOrNull() ?: 0

        val product = Product(
            id = currentProductId ?: "",
            title = title,
            price = parsedPrice,
            discountPercent = parsedDiscount,
            stockCount = parsedStock,
            category = selectedCategory,
            description = description
        )

        viewModelScope.launch {
            if (currentProductId == null) {
                repository.addProduct(product)
            } else {
                repository.updateProduct(product)
            }
            onSuccess()
        }
    }

    fun deleteProduct(productId: String) {
        viewModelScope.launch {
            repository.deleteProduct(productId)
        }
    }
}