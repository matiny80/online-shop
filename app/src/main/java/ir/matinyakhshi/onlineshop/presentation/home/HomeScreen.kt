package ir.matinyakhshi.onlineshop.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.matinyakhshi.onlineshop.R

// مدل ساده داده محصولات
data class ProductItem(
    val id: String,
    val title: String,
    val price: String,
    val imageRes: Int
)

// مدل ساده دسته‌بندی‌ها
data class CategoryItem(
    val title: String,
    val iconRes: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: StoreViewModel,
    onNavigateToProductDetail: (String) -> Unit
) {
    // لیست دسته‌بندی‌ها بر اساس فایل‌های drawable موجود در پروژه شما
    val categories = listOf(
        CategoryItem("کفش", R.drawable.shoes21),
        CategoryItem("تی‌شرت", R.drawable.tshirt1),
        CategoryItem("زنانه", R.drawable.woman1),
        CategoryItem("بچگانه", R.drawable.babydress1)
    )

    // نمونه لیست محصولات
    val products = listOf(
        ProductItem("1", "کفش ورزشی نایک", "۱,۲۵۰,۰۰۰ تومان", R.drawable.rectangle434),
        ProductItem("2", "تی‌شرت اسپرت سبز", "۴۵۰,۰۰۰ تومان", R.drawable.tshirt1),
        ProductItem("3", "پیراهن زنانه شیک", "۸۹۰,۰۰۰ تومان", R.drawable.woman1),
        ProductItem("4", "لباس بچگانه", "۳۲۰,۰۰۰ تومان", R.drawable.babydress1)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "لوگو",
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("فروشگاه آنلاین", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                },
                actions = {
                    // جایگزینی با آیکون استاندارد منوی سه خط (Menu)
                    IconButton(onClick = { /* باز کردن منو یا کشو */ }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "منو",
                            tint = Color.Black
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            // ۱. بنر تبلیغاتی بالای صفحه
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baner),
                    contentDescription = "بنر تخفیف",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ۲. بخش دسته‌بندی‌ها (افقی)
            Text(
                text = "دسته‌بندی‌ها",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(categories) { category ->
                    CategoryCard(category = category)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ۳. بخش محصولات (شبکه‌ای/گرید)
            Text(
                text = "جدیدترین محصولات",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(products) { product ->
                    ProductCard(
                        product = product,
                        onClick = { onNavigateToProductDetail(product.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryCard(category: CategoryItem) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { }
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFF5F5F5)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = category.iconRes),
                contentDescription = category.title,
                modifier = Modifier.size(36.dp)
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = category.title, fontSize = 12.sp, color = Color.DarkGray)
    }
}

@Composable
fun ProductCard(
    product: ProductItem,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFFAFAFA)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = product.imageRes),
                    contentDescription = product.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = product.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = product.price,
                fontSize = 13.sp,
                color = Color(0xFFFF5722),
                fontWeight = FontWeight.Bold
            )
        }
    }
}