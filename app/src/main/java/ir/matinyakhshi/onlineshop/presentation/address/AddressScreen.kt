package ir.matinyakhshi.onlineshop.presentation.address

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.matinyakhshi.onlineshop.R

data class UserAddress(
    val id: String,
    val recipientName: String,
    val fullAddress: String,
    val postalCode: String,
    val phoneNumber: String,
    val isSelected: Boolean = false
)

@Composable
fun AddressScreen(
    onBackClick: () -> Unit = {},
    onNavigateToCheckout: (UserAddress) -> Unit = {}
) {
    val backgroundColor = Color(0xFFFAF3EC)
    val primaryOrange = Color(0xFFF25A38)

    var showAddressDialog by remember { mutableStateOf(false) }
    var addressToEdit by remember { mutableStateOf<UserAddress?>(null) }

    var addresses by remember {
        mutableStateOf(
            listOf(
                UserAddress(
                    id = "1",
                    recipientName = "محمد عزیزی",
                    fullAddress = "بیرجند-غفاری ۱۶-پلاک ۲۵",
                    postalCode = "۹۸۷۴۳۶۵۳۷۴",
                    phoneNumber = "۰۹۱۱۱۱۱۱۱۱۱",
                    isSelected = true
                ),
                UserAddress(
                    id = "2",
                    recipientName = "الهه لطفی",
                    fullAddress = "بیرجند- توحید ۱۶-پلاک ۲۵",
                    postalCode = "۹۸۷۴۳۶۵۳۷۴",
                    phoneNumber = "۰۹۱۱۱۱۱۱۱۱۱",
                    isSelected = false
                )
            )
        )
    }

    Scaffold(
        topBar = { AddressTopBar(onBackClick = onBackClick) },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(backgroundColor)
                    .padding(16.dp)
            ) {
                Button(
                    onClick = {
                        val selected = addresses.find { it.isSelected }
                        selected?.let { onNavigateToCheckout(it) }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = primaryOrange),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    enabled = addresses.any { it.isSelected }
                ) {
                    Text(
                        text = "انتخاب آدرس و ادامه خرید",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        },
        containerColor = backgroundColor
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "بازگشت",
                            tint = primaryOrange
                        )
                    }
                    Text(
                        text = "آدرس های من",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0C0F26)
                    )
                }
            }

            item {
                Text(
                    text = "آدرس مورد نظر خود را انتخاب کنید.",
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            items(addresses, key = { it.id }) { address ->
                AddressCardItem(
                    address = address,
                    onSelect = {
                        addresses = addresses.map { it.copy(isSelected = it.id == address.id) }
                    },
                    onEdit = {
                        addressToEdit = address
                        showAddressDialog = true
                    },
                    onDelete = {
                        addresses = addresses.filter { it.id != address.id }
                    }
                )
            }

            item {
                OutlinedButton(
                    onClick = {
                        addressToEdit = null
                        showAddressDialog = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = primaryOrange),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = SolidColor(primaryOrange)
                    )
                ) {
                    Text(
                        text = "+ ثبت آدرس جدید",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        if (showAddressDialog) {
            AddressFormDialog(
                initialAddress = addressToEdit,
                onDismiss = { showAddressDialog = false },
                onSave = { recipient, fullAddress, postalCode, phone ->
                    if (addressToEdit != null) {
                        // ویرایش آدرس قبلی
                        addresses = addresses.map {
                            if (it.id == addressToEdit!!.id) {
                                it.copy(
                                    recipientName = recipient,
                                    fullAddress = fullAddress,
                                    postalCode = postalCode,
                                    phoneNumber = phone
                                )
                            } else it
                        }
                    } else {
                        // افزودن آدرس جدید
                        val newAddress = UserAddress(
                            id = System.currentTimeMillis().toString(),
                            recipientName = recipient,
                            fullAddress = fullAddress,
                            postalCode = postalCode,
                            phoneNumber = phone,
                            isSelected = addresses.isEmpty()
                        )
                        addresses = addresses + newAddress
                    }
                    showAddressDialog = false
                }
            )
        }
    }
}

@Composable
private fun AddressTopBar(onBackClick: () -> Unit) {
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
fun AddressCardItem(
    address: UserAddress,
    onSelect: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val primaryOrange = Color(0xFFF25A38)
    val borderColor = if (address.isSelected) primaryOrange else Color.Transparent

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.5.dp, borderColor, RoundedCornerShape(16.dp))
            .clickable { onSelect() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            AddressDetailRow(label = "گیرنده:", value = address.recipientName)
            Spacer(modifier = Modifier.height(8.dp))
            AddressDetailRow(label = "آدرس:", value = address.fullAddress)
            Spacer(modifier = Modifier.height(8.dp))
            AddressDetailRow(label = "کد پستی:", value = address.postalCode)
            Spacer(modifier = Modifier.height(8.dp))
            AddressDetailRow(label = "شماره همراه:", value = address.phoneNumber)

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = Color(0xFFEEEEEE), thickness = 1.dp)
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onSelect() }
                ) {
                    RadioButton(
                        selected = address.isSelected,
                        onClick = onSelect,
                        colors = RadioButtonDefaults.colors(selectedColor = primaryOrange)
                    )
                    Text(
                        text = "آدرس پیش فرض",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF0C0F26)
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = onEdit,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFDE8E3),
                            contentColor = primaryOrange
                        ),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
                        elevation = ButtonDefaults.buttonElevation(0.dp)
                    ) {
                        Text(text = "ویرایش", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = onDelete,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFF2F2F2),
                            contentColor = Color.Gray
                        ),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
                        elevation = ButtonDefaults.buttonElevation(0.dp)
                    ) {
                        Text(text = "حذف", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun AddressDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            modifier = Modifier.width(90.dp)
        )
        Text(
            text = value,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF0C0F26)
        )
    }
}

@Composable
fun AddressFormDialog(
    initialAddress: UserAddress? = null,
    onDismiss: () -> Unit,
    onSave: (recipient: String, address: String, postalCode: String, phone: String) -> Unit
) {
    var recipientName by remember { mutableStateOf(initialAddress?.recipientName ?: "") }
    var fullAddress by remember { mutableStateOf(initialAddress?.fullAddress ?: "") }
    var postalCode by remember { mutableStateOf(initialAddress?.postalCode ?: "") }
    var phoneNumber by remember { mutableStateOf(initialAddress?.phoneNumber ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (initialAddress == null) "ثبت آدرس جدید" else "ویرایش آدرس",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = recipientName,
                    onValueChange = { recipientName = it },
                    label = { Text("نام گیرنده") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = fullAddress,
                    onValueChange = { fullAddress = it },
                    label = { Text("آدرس کامل") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = postalCode,
                    onValueChange = { postalCode = it },
                    label = { Text("کد پستی") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    label = { Text("شماره همراه") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (recipientName.isNotBlank() && fullAddress.isNotBlank()) {
                        onSave(recipientName, fullAddress, postalCode, phoneNumber)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF25A38))
            ) {
                Text("ثبت")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("انصراف", color = Color.Gray)
            }
        }
    )
}