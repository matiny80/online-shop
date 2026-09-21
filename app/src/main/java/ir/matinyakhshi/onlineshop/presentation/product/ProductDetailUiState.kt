package ir.matinyakhshi.onlineshop.presentation.product

import ir.matinyakhshi.onlineshop.data.model.ProductDto

sealed interface ProductDetailUiState {
    object Loading : ProductDetailUiState
    data class Success(val product: ProductDto) : ProductDetailUiState
    data class Error(val message: String) : ProductDetailUiState
}