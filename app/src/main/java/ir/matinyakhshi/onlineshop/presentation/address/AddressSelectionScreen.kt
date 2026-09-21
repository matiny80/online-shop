package ir.matinyakhshi.onlineshop.presentation.address

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.matinyakhshi.onlineshop.presentation.address.components.AddressCard
import ir.matinyakhshi.onlineshop.ui.theme.*

@Composable
fun AddressSelectionScreen(
    onBackClick: () -> Unit,
    onAddNewAddress: () -> Unit,
    onContinueClick: () -> Unit
) {
    var selectedAddressId by remember { mutableStateOf("1") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundCream)
            .padding(16.dp)
    ) {
        // Top Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = BorderRed
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text("آدرس های من", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextDark)
                Text("آدرس مورد نظر خود را انتخاب کنید.", fontSize = 13.sp, color = TextGray)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Address List
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            AddressCard(
                recipientName = "محمد عزیزی",
                address = "بیرجند- غفاری ۱۶- پلاک ۲۵",
                postalCode = "۹۸۷۴۳۶۵۳۷۴",
                phoneNumber = "۰۹۱۱۱۱۱۱۱۱۱",
                isSelected = selectedAddressId == "1",
                onSelect = { selectedAddressId = "1" },
                onEdit = {},
                onDelete = {}
            )

            AddressCard(
                recipientName = "الهه لطفی",
                address = "گنبد کاووس- توحید ۱۶- پلاک ۲۵",
                postalCode = "۹۸۷۴۳۶۵۳۷۴",
                phoneNumber = "۰۹۱۱۱۱۱۱۱۱۱",
                isSelected = selectedAddressId == "2",
                onSelect = { selectedAddressId = "2" },
                onEdit = {},
                onDelete = {}
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Add Address Button
            // Add Address Button
            OutlinedButton(
                onClick = onAddNewAddress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, BorderRed) // اصلاح شد
            ) {
                Text("+ ثبت آدرس جدید", color = BorderRed, fontWeight = FontWeight.Bold)
            }
        }

        // Bottom CTA Button
        Button(
            onClick = onContinueClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = OrangePrimary)
        ) {
            Text("انتخاب آدرس و ادامه خرید", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}