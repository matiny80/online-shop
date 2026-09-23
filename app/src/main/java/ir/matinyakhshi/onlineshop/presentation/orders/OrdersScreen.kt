package ir.matinyakhshi.onlineshop.presentation.orders

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.matinyakhshi.onlineshop.R

// مدل داده سفارش
data class OrderItem(
    val id: String,
    val orderCode: String,
    val date: String,
    val title: String,
    val price: String,
    val isSuccess: Boolean,
    val imageRes: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersScreen(
    onBackClick: () -> Unit,
    onNavigateToNotifications: () -> Unit = {}
) {
    // اطلاعات فرضی کاربر
    val userName = "بهرام افشاری"
    val userPhone = "۰۹۳۰۰۳۱۸۲۳۹"

    // لیست نمونه سفارشات
    val orders = listOf(
        OrderItem(
            id = "1",
            orderCode = "۱۲۳۶۴",
            date = "۲۶ مهر ۱۴۰۲",
            title = "ست سویشرت و شلوار مردانه",
            price = "۱,۵۵۰,۰۰۰ تومان",
            isSuccess = true,
            imageRes = R.drawable.rectangle434 // تصویر محصول دلخواه از drawable
        ),
        OrderItem(
            id = "2",
            orderCode = "۱۲۳۶۴",
            date = "۲۶ مهر ۱۴۰۲",
            title = "ست سویشرت و شلوار مردانه ست سویشرت و شلوار مردانه ست سویشرت",
            price = "۱,۵۵۰,۰۰۰ تومان",
            isSuccess = true,
            imageRes = R.drawable.image13
        ),
        OrderItem(
            id = "3",
            orderCode = "۱۲۳۶۴",
            date = "۲۶ مهر ۱۴۰۲",
            title = "ست سویشرت و شلوار مردانه",
            price = "۱,۵۵۰,۰۰۰ تومان",
            isSuccess = false,
            imageRes = R.drawable.image9
        ),
        OrderItem(
            id = "4",
            orderCode = "۱۲۳۶۴",
            date = "۲۶ مهر ۱۴۰۲",
            title = "ست سویشرت و شلوار مردانه ست سویشرت و شلوار مردانه",
            price = "۱,۵۵۰,۰۰۰ تومان",
            isSuccess = true,
            imageRes = R.drawable.image14
        )
    )

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.logo),
                                contentDescription = "لوگو",
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "OnlineShop",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "بازگشت"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = onNavigateToNotifications) {
                            Icon(
                                imageVector = Icons.Outlined.Notifications,
                                contentDescription = "نوتیفیکیشن"
                            )
                        }
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "منو"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
                )
            },
            containerColor = Color(0xFFFAF7F5) // پس‌زمینه کرم روشن مشابه طرح
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // کارت بنر بالای صفحه (هدر کاربر)
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    UserProfileHeaderCard(name = userName, phone = userPhone)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "سفارشات من",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF333333),
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp)
                    )
                }

                // آیتم‌های لیست سفارشات
                items(orders) { order ->
                    OrderItemCard(order = order)
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun UserProfileHeaderCard(
    name: String,
    phone: String
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFF5722)), // رنگ نارنجی بنر
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = phone,
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.9f)
                )
            }

            // تصویر پروفایل
            Box(
                modifier = Modifier
                    .size(68.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ellipse_profile), // یا عکس پروفایل
                    contentDescription = "عکس پروفایل",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(62.dp)
                        .clip(CircleShape)
                )
            }
        }
    }
}

@Composable
fun OrderItemCard(order: OrderItem) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.5.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // سمت راست: توضیحات سفارش
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // کد سفارش و تاریخ
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "کد سفارش: ${order.orderCode}",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = order.date,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // عنوان محصول
                Text(
                    text = order.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF212121),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(12.dp))

                // وضعیت پرداخت و قیمت
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // لیبل وضعیت
                    Surface(
                        color = if (order.isSuccess) Color(0xFFE8F5E9) else Color(0xFFFFEBEE),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = if (order.isSuccess) "پرداخت موفق" else "پرداخت ناموفق",
                            color = if (order.isSuccess) Color(0xFF388E3C) else Color(0xFFD32F2F),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    // قیمت
                    Text(
                        text = order.price,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF424242)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // سمت چپ: تصویر محصول
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFF5F5F5)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = order.imageRes),
                    contentDescription = order.title,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize().padding(4.dp)
                )
            }
        }
    }
}