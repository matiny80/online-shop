package ir.matinyakhshi.onlineshop.presentation.auth.confirmcode

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ConfirmCodeScreen(
    onNavigateToSubmitInfo: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "تأیید کد")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onNavigateToSubmitInfo) {
            Text(text = "تأیید و ادامه")
        }
    }
}