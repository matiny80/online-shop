package ir.matinyakhshi.onlineshop.presentation.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AuthScreen(
    viewModel: AuthViewModel,
    onAuthSuccess: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    var phoneNumber by remember { mutableStateOf("") }
    var otpCode by remember { mutableStateOf("") }

    LaunchedEffect(uiState) {
        if (uiState is AuthUiState.Success) {
            onAuthSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "ورود / ثبت‌نام",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("شماره موبایل") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = otpCode,
            onValueChange = { otpCode = it },
            label = { Text("کد تأیید") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (uiState is AuthUiState.Error) {
            Text(
                text = (uiState as AuthUiState.Error).message,
                color = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(
            onClick = { viewModel.login(phoneNumber, otpCode) },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = uiState !is AuthUiState.Loading && phoneNumber.isNotBlank(),
            shape = RoundedCornerShape(8.dp)
        ) {
            if (uiState is AuthUiState.Loading) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
            } else {
                Text("ورود به برنامه")
            }
        }

        Button(
            onClick = { onAuthSuccess() }, // مستقیم به HomeScreen می‌رود
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("ورود مهمان (تست صفحات)")
        }
    }
}