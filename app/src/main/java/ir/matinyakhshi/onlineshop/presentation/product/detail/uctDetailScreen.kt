package ir.matinyakhshi.onlineshop.presentation.product.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.matinyakhshi.onlineshop.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    onBackClick: () -> Unit,
    onAddToCartClick: () -> Unit
) {
    var showShareBottomSheet by remember { mutableStateOf(false) }
    var selectedSize by remember { mutableStateOf("M") }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("جزئیات محصول", fontSize = 18.sp, fontWeight = FontWeight.Bold) },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "بازگشت"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { showShareBottomSheet = true }) {
                            Image(
                                painter = painterResource(id = R.drawable.share),
                                contentDescription = "اشتراک‌گذاری",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                )
            },
            bottomBar = {
                Surface(
                    shadowElevation = 8.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = onAddToCartClick,
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722)),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp)
                        ) {
                            Text("افزودن به سبد خرید", fontSize = 16.sp, color = Color.White, fontWeight = FontWeight.Bold)
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column(horizontalAlignment = Alignment.End) {
                            Text("قیمت:", fontSize = 12.sp, color = Color.Gray)
                            Text(
                                "۱,۵۵۰,۰۰۰ تومان",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                // تصویر اصلی محصول
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFF5F5F5)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.rectangle434),
                        contentDescription = "تصویر محصول",
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // عنوان محصول
                Text(
                    text = "ست بلوز و شلوار مردانه",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                // بخش انتخاب سایز
                Text(text = "انتخاب سایز:", fontSize = 14.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("S", "M", "L", "XL").forEach { size ->
                        SizeChip(
                            size = size,
                            isSelected = selectedSize == size,
                            onSelect = { selectedSize = size }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // توضیحات محصول
                Text(text = "توضیحات:", fontSize = 14.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "این محصول تهیه شده از پارچه باکیفیت و مناسب استفاده روزمره می‌باشد. جنس پارچه ۱۰۰٪ پنبه بوده و قابلیت شستشوی مداوم را دارد.",
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    lineHeight = 22.sp
                )
            }
        }

        // Bottom Sheet مربوط به اشتراک‌گذاری
        if (showShareBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showShareBottomSheet = false },
                sheetState = rememberModalBottomSheetState()
            ) {
                ShareBottomSheetContent(onDismiss = { showShareBottomSheet = false })
            }
        }
    }
}

@Composable
fun SizeChip(
    size: String,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(44.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(if (isSelected) Color(0xFFFF5722) else Color.Transparent)
            .border(
                width = 1.dp,
                color = if (isSelected) Color(0xFFFF5722) else Color.Gray,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onSelect() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = size,
            color = if (isSelected) Color.White else Color.Black,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ShareBottomSheetContent(onDismiss: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Text(
            text = "اشتراک‌گذاری در:",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        val shareOptions = listOf(
            "تلگرام",
            "واتساپ",
            "پیامک",
            "کپی",
            "سایر"
        )

        shareOptions.forEach { option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDismiss() }
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.share),
                    contentDescription = option,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = option, fontSize = 15.sp)
            }
            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))
        }
    }
}