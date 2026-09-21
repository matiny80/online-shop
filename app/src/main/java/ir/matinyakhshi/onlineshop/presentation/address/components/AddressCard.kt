package ir.matinyakhshi.onlineshop.presentation.address.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.matinyakhshi.onlineshop.ui.theme.*

@Composable
fun AddressCard(
    recipientName: String,
    address: String,
    postalCode: String,
    phoneNumber: String,
    isSelected: Boolean,
    onSelect: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .then(
                if (isSelected) Modifier.border(1.5.dp, BorderRed, RoundedCornerShape(16.dp))
                else Modifier
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AddressDetailRow(label = "گیرنده:", value = recipientName)
            AddressDetailRow(label = "آدرس:", value = address)
            AddressDetailRow(label = "کد پستی:", value = postalCode)
            AddressDetailRow(label = "شماره همراه:", value = phoneNumber)

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = Color.LightGray)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Radio button and Label
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    RadioButton(
                        selected = isSelected,
                        onClick = onSelect,
                        colors = RadioButtonDefaults.colors(selectedColor = BorderRed)
                    )
                    Text(
                        text = "آدرس پیش فرض",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                }

                // Action Buttons
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = onEdit,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFF0EC)),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp)
                    ) {
                        Text("ویرایش", color = BorderRed, fontSize = 12.sp)
                    }

                    Button(
                        onClick = onDelete,
                        colors = ButtonDefaults.buttonColors(containerColor = LightGrayBg),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp)
                    ) {
                        Text("حذف", color = TextGray, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun AddressDetailRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, fontWeight = FontWeight.Bold, color = TextGray, fontSize = 13.sp)
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = value, fontWeight = FontWeight.Normal, color = TextDark, fontSize = 13.sp)
    }
}