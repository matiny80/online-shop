package ir.matinyakhshi.onlineshop.presentation.category

import androidx.lifecycle.ViewModel
import ir.matinyakhshi.onlineshop.R
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
        val categoriesList = listOf(
            Category("1", "زنانه", imageUrl = R.drawable.woman1.toString()),
            Category("2", "مردانه", imageUrl = R.drawable.image8.toString()),
            Category("3", "دخترانه", imageUrl = R.drawable.babydress1.toString()),
            Category("4", "پسرانه", imageUrl = R.drawable.polo1.toString()),
            Category("5", "نوزادی", imageUrl = R.drawable.onesie1.toString()),
            Category("6", "کفش", imageUrl = R.drawable.image11.toString())
        )

        _uiState.value = _uiState.value.copy(
            categories = categoriesList,
            isLoading = false
        )
    }


    }