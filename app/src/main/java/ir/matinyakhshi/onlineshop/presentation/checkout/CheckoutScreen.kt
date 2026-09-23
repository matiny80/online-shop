package ir.matinyakhshi.onlineshop.presentation.checkout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.matinyakhshi.onlineshop.R
import ir.matinyakhshi.onlineshop.presentation.address.UserAddress

@Composable
fun CheckoutScreen(
    address: UserAddress = UserAddress(
        id = "1",
        recipientName = "محمد عزیزی",
        fullAddress = "بیرجند-غفاری ۱۶-پلاک ۲۵",
        postalCode = "۹۸۷۴۳۶۵۳۷۴",
        phoneNumber = "۰۹۱۱۱۱۱۱۱۱۱1"
    ),
    totalPrice: String = "۱۱,۱۰۰,۰۰۰",
    discountPrice: String = "۱,۵۵۰,۰۰۰",
    shippingPrice: String = "۱۰۰,۰۰۰",
    finalPrice: String = "۹,۸۵۰,۰۰۰",
    onChangeAddressClick: () -> Unit = {},
    onPaymentClick: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    val backgroundColor = Color(0xFFFAF3EC)
    val primaryOrange = Color(0xFFF25A38)

    Scaffold(
        topBar = { CheckoutTopBar(onBackClick = onBackClick) },
        bottomBar = { BottomNavigationBar() },
        containerColor = backgroundColor
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "سبد خرید",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0C0F26),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                )

                // کارت اطلاعات آدرس
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        AddressRow(label = "گیرنده:", value = address.recipientName)
                        AddressRow(label = "آدرس:", value = address.fullAddress)
                        AddressRow(label = "کد پستی:", value = address.postalCode)
                        AddressRow(label = "شماره همراه:", value = address.phoneNumber)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // دکمه تغییر آدرس
                OutlinedButton(
                    onClick = onChangeAddressClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = androidx.compose.ui.graphics.SolidColor(primaryOrange)
                    )
                ) {
                    Text(
                        text = "به آدرس دیگری برود",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = primaryOrange
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "تقریبا تا ۵ روز آینده این محصول بدست شما میرسد",
                    fontSize = 13.sp,
                    color = Color.Gray,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // کارت خلاصه فاکتور
            Card(
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    PriceRow(label = "جمع قیمت:", value = "$totalPrice تومان")
                    PriceRow(
                        label = "تخفیف:",
                        value = "$discountPrice تومان",
                        valueColor = primaryOrange
                    )
                    PriceRow(label = "هزینه ارسال:", value = "$shippingPrice تومان")

                    Spacer(modifier = Modifier.height(8.dp))

                    PriceRow(
                        label = "مبلغ نهایی:",
                        value = "$finalPrice تومان",
                        isBold = true
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = onPaymentClick,
                        colors = ButtonDefaults.buttonColors(containerColor = primaryOrange),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                    ) {
                        Text(
                            text = "پرداخت نهایی",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AddressRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Text(text = value, fontSize = 13.sp, color = Color(0xFF0C0F26), fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = label, fontSize = 13.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun PriceRow(
    label: String,
    value: String,
    valueColor: Color = Color(0xFF0C0F26),
    isBold: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = value,
            fontSize = if (isBold) 18.sp else 15.sp,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Medium,
            color = valueColor
        )
        Text(
            text = label,
            fontSize = if (isBold) 16.sp else 14.sp,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
            color = Color.Gray
        )
    }
}

@Composable
private fun CheckoutTopBar(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFF0C0F26)
                )
            }
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "OnlineShop",
                modifier = Modifier.height(32.dp)
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ellipse_profile),
                contentDescription = "Profile",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.LightGray, CircleShape)
            )

            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "Notifications",
                    tint = Color(0xFF0C0F26)
                )
            }

            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Outlined.Menu,
                    contentDescription = "Menu",
                    tint = Color(0xFF0C0F26)
                )
            }
        }
    }
}

@Composable
private fun BottomNavigationBar() {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            selected = true,
            onClick = {},
            icon = { Icon(Icons.Outlined.Home, contentDescription = null) },
            label = { Text("خانه") }
        )
        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = { Icon(Icons.Outlined.GridView, contentDescription = null) },
            label = { Text("دسته‌بندی") }
        )
        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = { Icon(Icons.Outlined.ShoppingBag, contentDescription = null) },
            label = { Text("سبد خرید") }
        )
        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = { Icon(Icons.Outlined.Person, contentDescription = null) },
            label = { Text("پروفایل من") }
        )
    }
}