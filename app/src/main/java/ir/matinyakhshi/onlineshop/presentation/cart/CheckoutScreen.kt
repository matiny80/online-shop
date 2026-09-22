package ir.matinyakhshi.onlineshop.presentation.cart

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    storeId: String,
    onBackClick: () -> Unit, // ✅ اضافه شد
    onOrderSuccess: () -> Unit,
    viewModel: CheckoutViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    var receiverName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    LaunchedEffect(uiState) {
        when (uiState) {
            is CheckoutUiState.Success -> {
                Toast.makeText(context, "سفارش با موفقیت ثبت شد", Toast.LENGTH_SHORT).show()
                onOrderSuccess()
            }
            is CheckoutUiState.Error -> {
                val message = (uiState as CheckoutUiState.Error).message
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("تکمیل و ثبت سفارش") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) { // ✅ استفاده از دکمه بازگشت
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "بازگشت")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = receiverName,
                onValueChange = { receiverName = it },
                label = { Text("نام و نام خانوادگی تحویل‌گیرنده") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text("شماره تماس") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("آدرس دقیق") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    viewModel.submitOrder(
                        storeId = storeId,
                        receiverName = receiverName,
                        phoneNumber = phoneNumber,
                        address = address
                    )
                },
                enabled = uiState !is CheckoutUiState.Loading && receiverName.isNotBlank() && phoneNumber.isNotBlank() && address.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                if (uiState is CheckoutUiState.Loading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
                } else {
                    Text("تأیید و ثبت نهایی سفارش")
                }
            }
        }
    }
}