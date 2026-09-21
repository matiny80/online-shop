package ir.matinyakhshi.onlineshop.presentation.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ir.matinyakhshi.onlineshop.presentation.product.ProductDetailViewModel
import ir.matinyakhshi.onlineshop.ui.theme.*

@Composable
fun CartScreen(
    viewModel: CartViewModel = hiltViewModel(),
    onChangeAddressClick: () -> Unit,
    onCheckoutClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundCream)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("سبد خرید", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = TextDark)

        Spacer(modifier = Modifier.height(16.dp))

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

        Spacer(modifier = Modifier.height(12.dp))

        // Change Address Button
        OutlinedButton(
            onClick = onChangeAddressClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp),
            shape = RoundedCornerShape(12.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, BorderRed)
        ) {
            Text("به آدرس دیگری برود", color = BorderRed, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "تقریباً تا ۵ روز آینده این محصول به‌دست شما می‌رسد",
            fontSize = 12.sp,
            color = TextGray,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(24.dp))

        // Pricing Summary Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                PriceSummaryRow(label = "جمع قیمت:", value = "۱۱,۱۰۰,۰۰۰ تومان", valueColor = TextDark)
                PriceSummaryRow(label = "تخفیف:", value = "۱,۵۵۰,۰۰۰ تومان", valueColor = BorderRed)
                PriceSummaryRow(label = "هزینه ارسال:", value = "۱۰۰,۰۰۰ تومان", valueColor = TextDark)

                HorizontalDivider(color = Color.LightGray)

                PriceSummaryRow(label = "مبلغ نهایی:", value = "۹,۸۵۰,۰۰۰ تومان", valueColor = TextDark, isBold = true)

                Spacer(modifier = Modifier.height(8.dp))

                // Final Payment Button
                Button(
                    onClick = onCheckoutClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
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
        Text(text = label, fontSize = 14.sp, fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal, color = TextGray)
        Text(text = value, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = valueColor)
    }
}