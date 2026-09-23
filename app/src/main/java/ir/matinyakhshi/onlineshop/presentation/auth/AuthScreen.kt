package ir.matinyakhshi.onlineshop.presentation.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onAuthSuccess: () -> Unit,
    onAdminAuthSuccess: () -> Unit // افزودن کالبک ورود ادمین
) {
    val uiState by viewModel.uiState.collectAsState()

    var usernameOrPhone by remember { mutableStateOf("") }
    var passwordOrOtp by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

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
            text = "ورود به حساب کاربری",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = usernameOrPhone,
            onValueChange = {
                usernameOrPhone = it
                errorMessage = null
            },
            label = { Text("شماره موبایل ") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = passwordOrOtp,
            onValueChange = {
                passwordOrOtp = it
                errorMessage = null
            },
            label = { Text("کد تأیید ") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // نمایش پیام خطا در صورت اشتباه بودن اطلاعات ادمین یا خطای سرور
        if (errorMessage != null) {
            Text(
                text = errorMessage!!,
                color = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(8.dp))
        } else if (uiState is AuthUiState.Error) {
            Text(
                text = (uiState as AuthUiState.Error).message,
                color = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(
            onClick = {
                // بررسی ورود ادمین
                if (usernameOrPhone.trim() == "Matin" && passwordOrOtp.trim() == "9384") {
                    onAdminAuthSuccess()
                } else {
                    // در غیر این صورت تلاش برای ورود کاربر عادی
                    viewModel.login(usernameOrPhone, passwordOrOtp)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = uiState !is AuthUiState.Loading && usernameOrPhone.isNotBlank(),
            shape = RoundedCornerShape(8.dp)
        ) {
            if (uiState is AuthUiState.Loading) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
            } else {
                Text("ورود به برنامه")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { onAuthSuccess() },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("ورود مهمان (تست صفحات)")
        }
    }
}