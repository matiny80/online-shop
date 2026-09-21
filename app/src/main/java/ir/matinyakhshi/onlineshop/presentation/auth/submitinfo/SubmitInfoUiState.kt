package ir.matinyakhshi.onlineshop.presentation.auth.submitinfo

data class SubmitInfoUiState(
    val fullName: String = "",
    val email: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)