package ir.matinyakhshi.onlineshop.presentation.home

import ir.matinyakhshi.onlineshop.domain.model.Category
import ir.matinyakhshi.onlineshop.domain.model.Product
import java.util.Collections.emptyList

data class HomeUiState(
    val categories: List<Category> = emptyList(),
    val featuredProducts: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)