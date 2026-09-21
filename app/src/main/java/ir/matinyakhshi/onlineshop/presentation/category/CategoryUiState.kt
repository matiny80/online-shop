package ir.matinyakhshi.onlineshop.presentation.category

import ir.matinyakhshi.onlineshop.domain.model.Category
import java.util.Collections.emptyList

data class CategoryUiState(
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)