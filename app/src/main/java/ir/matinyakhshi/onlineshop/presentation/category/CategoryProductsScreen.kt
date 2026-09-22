package ir.matinyakhshi.onlineshop.presentation.category

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ProductDummy(
    val id: String,
    val title: String,
    val price: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryProductsScreen(
    categoryId: String,
    onProductClick: (String) -> Unit,
    onBackClick: () -> Unit
) {
    // نمونه داده فرضی برای تست UI
    val products = listOf(
        ProductDummy("1", "محصول اول دسته‌بندی $categoryId", "۲۵۰,۰۰۰ تومان"),
        ProductDummy("2", "محصول دوم دسته‌بندی $categoryId", "۴۸0,۰۰۰ تومان")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("محصولات دسته‌بندی", fontSize = 18.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "بازگشت")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(products) { product ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .clickable { onProductClick(product.id) }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = product.title,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            maxLines = 2
                        )
                        Text(
                            text = product.price,
                            fontSize = 13.sp,
                            color = Color(0xFFFF5722),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}