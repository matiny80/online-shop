package ir.matinyakhshi.onlineshop.presentation.cart

sealed interface CheckoutUiState {
    object Idle : CheckoutUiState
    object Loading : CheckoutUiState
    data class Success(val message: String = "سفارش با موفقیت ثبت شد") : CheckoutUiState
    data class Error(val message: String) : CheckoutUiState
}