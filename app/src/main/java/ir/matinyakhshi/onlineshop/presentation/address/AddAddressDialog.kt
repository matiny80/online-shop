package ir.matinyakhshi.onlineshop.presentation.address

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun AddAddressDialog(
    onDismiss: () -> Unit,
    onAddressAdded: (title: String, fullAddress: String, postalCode: String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var fullAddress by remember { mutableStateOf("") }
    var postalCode by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "افزودن آدرس جدید",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("عنوان آدرس (مثلاً خانه، محل کار)") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    singleLine = true
                )

                OutlinedTextField(
                    value = postalCode,
                    onValueChange = { if (it.length <= 10) postalCode = it },
                    label = { Text("کد پستی (۱۰ رقمی)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    singleLine = true
                )

                OutlinedTextField(
                    value = fullAddress,
                    onValueChange = { fullAddress = it },
                    label = { Text("نشانی کامل پستی") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    shape = RoundedCornerShape(10.dp),
                    maxLines = 3
                )

                if (errorMessage != null) {
                    Text(
                        text = errorMessage!!,
                        color = Color.Red,
                        fontSize = 12.sp
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("انصراف", color = Color.Gray)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            if (title.isBlank() || fullAddress.isBlank() || postalCode.isBlank()) {
                                errorMessage = "لطفاً تمام موارد را پر کنید"
                            } else if (postalCode.length < 10) {
                                errorMessage = "کد پستی باید ۱۰ رقم باشد"
                            } else {
                                onAddressAdded(title, fullAddress, postalCode)
                                onDismiss()
                            }
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722))
                    ) {
                        Text("ذخیره آدرس")
                    }
                }
            }
        }
    }
}