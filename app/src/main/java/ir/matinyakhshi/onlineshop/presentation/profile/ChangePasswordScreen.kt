package ir.matinyakhshi.onlineshop.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.matinyakhshi.onlineshop.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswordScreen(
    onBackClick: () -> Unit
) {
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var currentPasswordVisible by remember { mutableStateOf(false) }
    var newPasswordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "بازگشت")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFFAF5F0))
                .verticalScroll(rememberScrollState())
        ) {
            // ۱. هدر بالای صفحه با مشخصات کاربر
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                    .background(Color(0xFFFF5232))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "بهرام افشاری",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "۰۹۳۰۰۳۱۸۲۳۹",
                            fontSize = 14.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ellipse_profile),
                            contentDescription = "Profile",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ۲. فرم تغییر رمز عبور
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Text(
                    text = "تغییر رمز",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF212121),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )

                Spacer(modifier = Modifier.height(12.dp))

                // قوانین رمز عبور
                Text(
                    text = "در انتخاب رمز عبور موارد زیر را در نظر بگیرید:\n" +
                            "• رمز عبور باید حداقل ۸ کاراکتر باشد\n" +
                            "• شامل حروف و عدد باشد\n" +
                            "• شامل علامت باشد (!@#$%)\n" +
                            "• از حروف بزرگ و کوچک استفاده شود.",
                    fontSize = 13.sp,
                    color = Color(0xFF666666),
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                // ۱. رمز عبور فعلی
                PasswordInputField(
                    label = "رمز عبور فعلی:*",
                    value = currentPassword,
                    onValueChange = { currentPassword = it },
                    isVisible = currentPasswordVisible,
                    onToggleVisibility = { currentPasswordVisible = !currentPasswordVisible }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // ۲. رمز عبور جدید
                PasswordInputField(
                    label = "رمز عبور جدید:*",
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    isVisible = newPasswordVisible,
                    onToggleVisibility = { newPasswordVisible = !newPasswordVisible }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // ۳. تکرار رمز عبور جدید
                PasswordInputField(
                    label = "تکرار رمز عبور جدید:*",
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    isVisible = confirmPasswordVisible,
                    onToggleVisibility = { confirmPasswordVisible = !confirmPasswordVisible }
                )

                Spacer(modifier = Modifier.height(28.dp))

                // دکمه تغییر رمز عبور
                Button(
                    onClick = { /* ثبت تغییرات */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5232))
                ) {
                    Text(
                        text = "تغییر رمز عبور",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun PasswordInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isVisible: Boolean,
    onToggleVisibility: () -> Unit
) {
    Column {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF212121)
        )

        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = Color(0xFFFF5232),
                unfocusedBorderColor = Color.Transparent
            ),
            visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
            leadingIcon = {
                IconButton(onClick = onToggleVisibility) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Show/Hide Password",
                        tint = if (isVisible) Color(0xFFFF5232) else Color.Gray
                    )
                }
            },
            singleLine = true
        )
    }
}