package ir.matinyakhshi.onlineshop.presentation.detail

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ir.matinyakhshi.onlineshop.presentation.product.ProductDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productId: String,
    onBackClick: () -> Unit,
    viewModel: ProductDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(productId) {
        if (productId.isNotEmpty()) {
            viewModel.loadProduct(productId)
        }
    }

    val productState by viewModel.product.collectAsState()

    var showShareSheet by remember { mutableStateOf(false) }
    var isFavorite by remember { mutableStateOf(false) }

    val sheetState = rememberModalBottomSheetState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "جزئیات محصول", fontSize = 18.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "بازگشت"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { showShareSheet = true }) {
                        Icon(imageVector = Icons.Default.Share, contentDescription = "اشتراک‌گذاری")
                    }
                    IconButton(onClick = { isFavorite = !isFavorite }) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "علاقه‌مندی",
                            tint = if (isFavorite) Color.Red else Color.Gray
                        )
                    }
                }
            )
        },
        bottomBar = {
            productState?.let { product ->
                Surface(
                    shadowElevation = 8.dp,
                    color = Color.White
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = {
                                viewModel.addToCart(
                                    productId = product.id,
                                    title = product.title,
                                    price = product.price
                                )
                            },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722)),
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp)
                        ) {
                            Text(
                                text = "افزودن به سبد خرید",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "قیمت محصول",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                            Text(
                                text = product.price,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF212121)
                            )
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        val product = productState

        if (product != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                // ۱. باکس تصویر اصلی محصول
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFF5F5F5)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = product.imageRes),
                        contentDescription = product.title,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // ۲. عنوان محصول
                Text(
                    text = product.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF212121)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // ۳. وضعیت موجودی و دسته‌بندی
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = Color(0xFFE8F5E9),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "موجود در انبار",
                            color = Color(0xFF2E7D32),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    Text(
                        text = "دسته‌بندی: پوشاک",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                HorizontalDivider(color = Color(0xFFEEEEEE))

                Spacer(modifier = Modifier.height(16.dp))

                // ۴. توضیحات محصول
                Text(
                    text = "توضیحات محصول",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF212121)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "این محصول با بهترین کیفیت دوخت و پارچه تهیه شده و مناسب برای استفاده روزمره و مجلسی می‌باشد.",
                    fontSize = 14.sp,
                    color = Color(0xFF616161),
                    lineHeight = 22.sp,
                    textAlign = TextAlign.Justify
                )
            }
        } else {
            // حالت لودینگ
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(0xFFFF5722))
            }
        }

        if (showShareSheet) {
            ModalBottomSheet(
                onDismissRequest = { showShareSheet = false },
                sheetState = sheetState
            ) {
                ShareBottomSheetContent(onDismiss = { showShareSheet = false })
            }
        }
    }
}

// کامپوننت‌های اشتراک‌گذاری
data class ShareOptionItem(
    val title: String,
    val iconVector: ImageVector? = null,
    val iconTint: Color = Color.Unspecified
)

@Composable
fun ShareBottomSheetContent(onDismiss: () -> Unit) {
    val shareOptions = listOf(
        ShareOptionItem("تلگرام", iconVector = Icons.Default.Send, iconTint = Color(0xFF24A1DE)),
        ShareOptionItem("واتساپ", iconVector = Icons.Default.AccountCircle, iconTint = Color(0xFF25D366)),
        ShareOptionItem("پیامک", iconVector = Icons.Default.Email, iconTint = Color(0xFF4A4A4A)),
        ShareOptionItem("کپی لینک", iconVector = Icons.Default.Done, iconTint = Color(0xFFFF5722)),
        ShareOptionItem("سایر", iconVector = Icons.Default.Share, iconTint = Color(0xFFFF5722))
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Text(
            text = "اشتراک‌گذاری محصول",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        shareOptions.forEachIndexed { index, option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDismiss() }
                    .padding(vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (option.iconVector != null) {
                    Icon(
                        imageVector = option.iconVector,
                        contentDescription = option.title,
                        modifier = Modifier.size(24.dp),
                        tint = option.iconTint
                    )
                }

                Text(
                    text = option.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF212121)
                )
            }

            if (index < shareOptions.size - 1) {
                DashedDivider(
                    color = Color.LightGray.copy(alpha = 0.7f),
                    thickness = 1.dp
                )
            }
        }
    }
}

@Composable
fun DashedDivider(
    color: Color = Color.Gray,
    thickness: androidx.compose.ui.unit.Dp = 1.dp
) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(thickness)
    ) {
        val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
        drawLine(
            color = color,
            start = androidx.compose.ui.geometry.Offset(0f, 0f),
            end = androidx.compose.ui.geometry.Offset(size.width, 0f),
            pathEffect = pathEffect,
            strokeWidth = thickness.toPx()
        )
    }
}