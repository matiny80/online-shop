package ir.matinyakhshi.onlineshop.presentation.category

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.matinyakhshi.onlineshop.domain.model.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.ArrayList
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(CategoryUiState())
    val uiState: StateFlow<CategoryUiState> = _uiState.asStateFlow()

    init {
        loadCategories()
    }

    private fun loadCategories() {
        val categoriesList = ArrayList<Category>()
        categoriesList.add(Category("1", "پوشاک زنانه", ""))
        categoriesList.add(Category("2", "پوشاک مردانه", ""))
        categoriesList.add(Category("3", "کفش و کتانی", ""))
        categoriesList.add(Category("4", "بچگانه", ""))

        _uiState.value = _uiState.value.copy(
            categories = categoriesList,
            isLoading = false
        )
    }
}