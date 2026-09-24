package ir.matinyakhshi.onlineshop.presentation.home

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ir.matinyakhshi.onlineshop.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// مدل داده محصولات UI (با مقادیر پیش‌فرض برای حل خطاهای ProductDetailScreen بدون دستکاری لیست)
data class ProductItem(
    val id: String,
    val title: String,
    val price: String,
    val imageRes: Int,
    val category: String = "پوشاک",
    val stockCount: Int = 10,
    val description: String = "توضیحات محصول به زودی اضافه می‌شود."
)

// مدل دسته‌بندی‌ها
data class CategoryItem(
    val id: String,
    val title: String,
    val iconRes: Int
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    viewModel: StoreViewModel = hiltViewModel(),
    onNavigateToProductDetail: (String) -> Unit,
    onCategoryClick: (String) -> Unit = {}
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // لیست دسته‌بندی‌ها (بدون تغییر)
    val categories = listOf(
        CategoryItem("men", "مردانه", R.drawable.tshirt1),
        CategoryItem("women", "زنانه", R.drawable.woman1),
        CategoryItem("kids", "دخترانه", R.drawable.babydress1),
        CategoryItem("boy", "پسرانه", R.drawable.polo1),
        CategoryItem("baby", "نوزادی", R.drawable.onesie1),
        CategoryItem("shoes", "کفش", R.drawable.shoes21)
    )

    // لیست محصولات برای نمایش در Grid (بدون تغییر)
    val products = listOf(
        ProductItem("1", "پیراهن مردانه", "۱,۲۵۰,۰۰۰ تومان", R.drawable.image16),
        ProductItem("2", "پیراهن مردانه", "۴۵۰,۰۰۰ تومان", R.drawable.image8),
        ProductItem("3", "مانتو زنانه شیک", "۸۹۰,۰۰۰ تومان", R.drawable.image9),
        ProductItem("4", "پیراهن مردانه", "۳۲۰,۰۰۰ تومان", R.drawable.image14),
        ProductItem("5", "کفش ورزشی نایک", "۱,۲۵۰,۰۰۰ تومان", R.drawable.image11),
        ProductItem("6", "پیراهن مردانه", "۴۵۰,۰۰۰ تومان", R.drawable.image13),
        ProductItem("7", "مانتو زنانه شیک", "۸۹۰,۰۰۰ تومان", R.drawable.image9),
        ProductItem("8", "پیراهن مردانه", "۳۲۰,۰۰۰ تومان", R.drawable.image15)
    )

    // لیست بنرها
    val banners = listOf(R.drawable.baner, R.drawable.baner)
    val pagerState = rememberPagerState(pageCount = { banners.size })

    // تایمر اسلایدر
    LaunchedEffect(Unit) {
        while (true) {
            delay(4000)
            val nextPage = (pagerState.currentPage + 1) % banners.size
            pagerState.animateScrollToPage(
                page = nextPage,
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            )
        }
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet(
                    modifier = Modifier.width(250.dp),
                    drawerContainerColor = Color.White
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "دسته‌بندی‌ها",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFF5722),
                            modifier = Modifier.padding(vertical = 12.dp)
                        )

                        HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))

                        Spacer(modifier = Modifier.height(8.dp))

                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            items(categories) { category ->
                                Text(
                                    text = category.title,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.DarkGray,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            scope.launch { drawerState.close() }
                                            onCategoryClick(category.id)
                                        }
                                        .padding(vertical = 12.dp, horizontal = 8.dp)
                                )
                                HorizontalDivider(color = Color(0xFFF5F5F5))
                            }
                        }
                    }
                }
            }
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        navigationIcon = {
                            IconButton(onClick = {
                                scope.launch { drawerState.open() }
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "منو",
                                    tint = Color.Black
                                )
                            }
                        },
                        title = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("فروشگاه آنلاین", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.width(8.dp))
                                Image(
                                    painter = painterResource(id = R.drawable.logo),
                                    contentDescription = "لوگو",
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }
                    )
                }
            ) { paddingValues ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // ۱. بنر اسلایدر
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(140.dp)
                                .clip(RoundedCornerShape(16.dp))
                        ) {
                            HorizontalPager(
                                state = pagerState,
                                modifier = Modifier.fillMaxSize()
                            ) { page ->
                                Image(
                                    painter = painterResource(id = banners[page]),
                                    contentDescription = "بنر تخفیف",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    }

                    // ۲. دسته‌بندی‌ها
                    item {
                        Column {
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
                                    CategoryCard(
                                        category = category,
                                        onClick = { onCategoryClick(category.id) }
                                    )
                                }
                            }
                        }
                    }

                    // ۳. عنوان محصولات
                    item {
                        Text(
                            text = "جدیدترین محصولات",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // ۴. لیست محصولات
                    item {
                        val rowCount = (products.size + 1) / 2
                        val gridHeight = (rowCount * 200).dp

                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            userScrollEnabled = false,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(gridHeight)
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
        }
    }
}

@Composable
fun CategoryCard(
    category: CategoryItem,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
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