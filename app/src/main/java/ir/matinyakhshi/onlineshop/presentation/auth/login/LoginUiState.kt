package ir.matinyakhshi.onlineshop.presentation.auth.login

data class LoginUiState(
    val isLoading: Boolean = false,
    val phoneNumber: String = "",
    val errorMessage: String? = null,
    val isSuccess: Boolean = false
)