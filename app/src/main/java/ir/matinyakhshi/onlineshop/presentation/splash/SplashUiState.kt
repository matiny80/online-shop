package ir.matinyakhshi.onlineshop.presentation.splash

data class SplashUiState(
    val isLoading: Boolean = true,
    val isLoggedIn: Boolean = false,
    val isNetworkAvailable: Boolean = true
)