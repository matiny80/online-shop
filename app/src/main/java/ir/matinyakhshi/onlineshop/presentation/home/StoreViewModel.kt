package ir.matinyakhshi.onlineshop.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.matinyakhshi.onlineshop.data.model.ProductDto
import ir.matinyakhshi.onlineshop.data.model.StoreConfigDto
import ir.matinyakhshi.onlineshop.data.remote.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface StoreUiState {
    object Loading : StoreUiState
    data class Success(
        val config: StoreConfigDto,
        val products: List<ProductDto>
    ) : StoreUiState
    data class Error(val message: String) : StoreUiState
}

@HiltViewModel
class StoreViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    private val _uiState = MutableStateFlow<StoreUiState>(StoreUiState.Loading)
    val uiState: StateFlow<StoreUiState> = _uiState.asStateFlow()

    fun loadStoreData(storeId: String) {
        viewModelScope.launch {
            _uiState.value = StoreUiState.Loading
            try {
                val config = apiService.getStoreConfig(storeId)
                val products = apiService.getProducts(storeId)
                _uiState.value = StoreUiState.Success(config = config, products = products)
            } catch (e: Exception) {
                _uiState.value = StoreUiState.Error(e.localizedMessage ?: "خطایی رخ داد")
            }
        }
    }
}