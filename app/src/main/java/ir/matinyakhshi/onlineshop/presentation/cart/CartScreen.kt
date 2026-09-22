package ir.matinyakhshi.onlineshop.presentation.cart

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ir.matinyakhshi.onlineshop.data.local.entity.CartItemEntity
import ir.matinyakhshi.onlineshop.ui.theme.*
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CartScreen(
    viewModel: CartViewModel = hiltViewModel(),
    onChangeAddressClick: () -> Unit,
    onCheckoutClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundCream)
            .padding(16.dp)
    ) {
        Text("سبد خرید", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = TextDark)

        Spacer(modifier = Modifier.height(12.dp))

        // Address Overview Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                CartDetailRow(label = "گیرنده:", value = "محمد عزیزی")
                CartDetailRow(label = "آدرس:", value = "بیرجند- غفاری ۱۶- پلاک ۲۵")
                CartDetailRow(label = "کد پستی:", value = "۹۸۷۴۳۶۵۳۷۴")
                CartDetailRow(label = "شماره همراه:", value = "۰۹۱۱۱۱۱۱۱۱۱")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = onChangeAddressClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(42.dp),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, BorderRed)
        ) {
            Text("به آدرس دیگری برود", color = BorderRed, fontWeight = FontWeight.Bold, fontSize = 13.sp)
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Cart Items List
        if (uiState.cartItems.isEmpty()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text("سبد خرید شما خالی است", color = TextGray, fontSize = 16.sp)
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.cartItems, key = { it.productId }) { item ->
                    CartItemCard(
                        item = item,
                        onIncrease = { viewModel.increaseQuantity(item) },
                        onDecrease = { viewModel.decreaseQuantity(item) },
                        onRemove = { viewModel.removeItem(item.productId) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Pricing Summary Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                PriceSummaryRow(label = "جمع قیمت:", value = formatPrice(uiState.rawTotalPrice), valueColor = TextDark)
                PriceSummaryRow(label = "تخفیف:", value = formatPrice(uiState.totalDiscount), valueColor = BorderRed)
                PriceSummaryRow(label = "هزینه ارسال:", value = formatPrice(uiState.shippingFee), valueColor = TextDark)

                HorizontalDivider(color = Color.LightGray)

                PriceSummaryRow(label = "مبلغ نهایی:", value = formatPrice(uiState.finalPrice), valueColor = TextDark, isBold = true)

                Spacer(modifier = Modifier.height(4.dp))

                Button(
                    onClick = onCheckoutClick,
                    enabled = uiState.cartItems.isNotEmpty(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = OrangePrimary)
                ) {
                    Text("پرداخت نهایی", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun CartItemCard(
    item: CartItemEntity,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(item.title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = TextDark)
                Spacer(modifier = Modifier.height(4.dp))
                Text(formatPrice(item.price), fontSize = 12.sp, color = TextGray)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onDecrease, modifier = Modifier.size(32.dp)) {
                    Icon(
                        imageVector = if (item.quantity == 1) Icons.Default.Delete else Icons.Default.Add,
                        contentDescription = "Decrease",
                        tint = BorderRed
                    )
                }
                Text("${item.quantity}", fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp))
                IconButton(onClick = onIncrease, modifier = Modifier.size(32.dp)) {
                    Icon(Icons.Default.Add, contentDescription = "Increase", tint = OrangePrimary)
                }
            }
        }
    }
}

@Composable
private fun CartDetailRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, fontWeight = FontWeight.Bold, color = TextGray, fontSize = 13.sp)
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = value, color = TextDark, fontSize = 13.sp)
    }
}

@Composable
private fun PriceSummaryRow(label: String, value: String, valueColor: Color, isBold: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 13.sp, fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal, color = TextGray)
        Text(text = value, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = valueColor)
    }
}

private fun formatPrice(price: Double): String {
    val formatter = NumberFormat.getInstance(Locale("fa", "IR"))
    return "${formatter.format(price.toLong())} تومان"
}