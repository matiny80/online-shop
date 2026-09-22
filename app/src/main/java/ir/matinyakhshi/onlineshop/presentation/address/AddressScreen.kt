package ir.matinyakhshi.onlineshop.presentation.address

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class UserAddress(
    val id: String,
    val title: String,
    val fullAddress: String,
    val postalCode: String,
    val isSelected: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressScreen(
    onBackClick: () -> Unit,
    onAddressSelected: (UserAddress) -> Unit
) {
    var showAddDialog by remember { mutableStateOf(false) }
    var addresses by remember {
        mutableStateOf(
            listOf(
                UserAddress(
                    id = "1",
                    title = "خانه",
                    fullAddress = "تهران، خیابان آزادی، کوچه شهید احمدی، پلاک ۱۲",
                    postalCode = "۱۲۳۴۵۶۷۸۹۰",
                    isSelected = true
                )
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("آدرس‌های من", fontSize = 18.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "بازگشت")
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = Color(0xFFFF5722),
                contentColor = Color.White,
                shape = RoundedCornerShape(16.dp),
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("افزودن آدرس جدید") }
            )
        }
    ) { paddingValues ->
        if (addresses.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("هیچ آدرسی ثبت نشده است", color = Color.Gray)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(top = 12.dp, bottom = 80.dp)
            ) {
                items(addresses) { address ->
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (address.isSelected) Color(0xFFFFF3E0) else Color.White
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                addresses = addresses.map { it.copy(isSelected = it.id == address.id) }
                                onAddressSelected(address)
                            }
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.Top
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = if (address.isSelected) Color(0xFFFF5722) else Color.Gray,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = address.title, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = address.fullAddress, fontSize = 13.sp, color = Color.DarkGray)
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(text = "کد پستی: ${address.postalCode}", fontSize = 12.sp, color = Color.Gray)
                            }
                            if (address.isSelected) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "انتخاب شده",
                                    tint = Color(0xFFFF5722),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        if (showAddDialog) {
            AddAddressDialog(
                onDismiss = { showAddDialog = false },
                onAddressAdded = { title, fullAddress, postalCode ->
                    val newAddress = UserAddress(
                        id = System.currentTimeMillis().toString(),
                        title = title,
                        fullAddress = fullAddress,
                        postalCode = postalCode,
                        isSelected = false
                    )
                    addresses = addresses + newAddress
                }
            )
        }
    }
}