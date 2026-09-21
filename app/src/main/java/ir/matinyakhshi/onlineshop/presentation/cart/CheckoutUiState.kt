package ir.matinyakhshi.onlineshop.presentation.cart

sealed interface CheckoutUiState {
    object Idle : CheckoutUiState
    object Loading : CheckoutUiState
    object Success : CheckoutUiState
    data class Error(val message: String) : CheckoutUiState
}