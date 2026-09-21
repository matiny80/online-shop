package ir.matinyakhshi.onlineshop.presentation.auth.confirmcode

data class ConfirmCodeUiState(
    val isLoading: Boolean = false,
    val code: String = "",
    val errorMessage: String? = null
)
