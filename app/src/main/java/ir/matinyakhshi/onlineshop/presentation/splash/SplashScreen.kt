package ir.matinyakhshi.onlineshop.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ir.matinyakhshi.onlineshop.R
import ir.matinyakhshi.onlineshop.presentation.auth.AuthViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onNavigateToMain: () -> Unit,
    onNavigateToAuth: () -> Unit
) {
    val tokenState = viewModel.isUserLoggedIn.collectAsState(initial = null)

    LaunchedEffect(Unit) {
        delay(2500) // نمایش انیمیشن اسپلش

        val token = tokenState.value?.toString()
        if (!token.isNullOrBlank()) {
            onNavigateToMain() // کاربر لاگین است -> هوم اسکرین
        } else {
            onNavigateToAuth() // کاربر لاگین نیست -> صفحه ورود
        }
    }

    val backgroundColor = Color(0xFFFAF3EC)
    val circleShapeColor = Color(0xFFF3E7DC)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Box(
            modifier = Modifier
                .size(240.dp)
                .offset(x = 80.dp, y = (-80).dp)
                .clip(CircleShape)
                .background(circleShapeColor)
                .align(Alignment.TopEnd)
        )

        Box(
            modifier = Modifier
                .size(120.dp)
                .offset(x = (-50).dp, y = 180.dp)
                .clip(CircleShape)
                .background(circleShapeColor)
                .align(Alignment.TopStart)
        )

        Box(
            modifier = Modifier
                .size(260.dp)
                .offset(x = (-80).dp, y = 100.dp)
                .clip(CircleShape)
                .background(circleShapeColor)
                .align(Alignment.BottomStart)
        )

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "OnlineShop Logo",
                modifier = Modifier.size(160.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color(0xFFF25A38), fontWeight = FontWeight.Normal)) {
                        append("Online")
                    }
                    withStyle(style = SpanStyle(color = Color(0xFF0C0F26), fontWeight = FontWeight.Bold)) {
                        append("Shop")
                    }
                },
                fontSize = 28.sp
            )
        }
    }
}