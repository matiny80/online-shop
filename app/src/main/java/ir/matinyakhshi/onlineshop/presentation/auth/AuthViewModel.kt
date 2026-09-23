package ir.matinyakhshi.onlineshop.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.matinyakhshi.onlineshop.data.local.TokenManager
import ir.matinyakhshi.onlineshop.data.remote.ApiService
import ir.matinyakhshi.onlineshop.data.remote.model.LoginRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val apiService: ApiService,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    val isUserLoggedIn = tokenManager.token

    fun login(phoneNumber: String, otpCode: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            try {
                val response = apiService.login(
                    LoginRequest(phoneNumber = phoneNumber, otpCode = otpCode)
                )
                tokenManager.saveToken(response.token)
                _uiState.value = AuthUiState.Success
            } catch (e: Exception) {
                _uiState.value = AuthUiState.Error(e.localizedMessage ?: "خطا در ورود به حساب")
            }
        }
    }
}