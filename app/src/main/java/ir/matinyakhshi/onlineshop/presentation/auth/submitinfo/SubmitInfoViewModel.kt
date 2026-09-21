package ir.matinyakhshi.onlineshop.presentation.auth.submitinfo

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SubmitInfoViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(SubmitInfoUiState())
    val uiState: StateFlow<SubmitInfoUiState> = _uiState.asStateFlow()

    fun onFullNameChanged(newName: String) {
        _uiState.value = _uiState.value.copy(fullName = newName)
    }

    fun onEmailChanged(newEmail: String) {
        _uiState.value = _uiState.value.copy(email = newEmail)
    }

    fun submitInformation(onSuccess: () -> Unit) {
        // بعداً منطق ارسال به API/Repository را اضافه می‌کنیم
        onSuccess()
    }
}