import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.matinyakhshi.onlineshop.R

// مدل برای داده‌های هر گزینه
data class ShareOptionItem(
    val title: String,
    val iconRes: Int? = null,
    val iconVector: ImageVector? = null,
    val iconTint: Color = Color.Unspecified
)

@Composable
fun ShareBottomSheetContent(onDismiss: () -> Unit) {
    // لیست گزینه‌ها بر اساس تصویر
    val shareOptions = listOf(
        ShareOptionItem("تلگرام", iconVector = Icons.Default.DateRange, iconTint = Color(0xFF24A1DE)),
        ShareOptionItem("واتساپ", iconVector = Icons.Default.AccountCircle, iconTint = Color(0xFF25D366)),
        ShareOptionItem("پیامک", iconVector = Icons.Default.Email, iconTint = Color(0xFF4A4A4A)),
        ShareOptionItem("کپی", iconVector = Icons.Default.Done, iconTint = Color(0xFFFF5722)),
        ShareOptionItem("سایر", iconVector = Icons.Default.Share, iconTint = Color(0xFFFF5722))
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        shareOptions.forEachIndexed { index, option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        // اکشن اشتراک‌گذاری
                        onDismiss()
                    }
                    .padding(vertical = 14.dp),
                horizontalArrangement = Arrangement.End, // چیدمان راست‌به‌چپ
                verticalAlignment = Alignment.CenterVertically
            ) {
                // متن گزینه
                Text(
                    text = option.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF212121)
                )

                Spacer(modifier = Modifier.width(16.dp))

                // آیکون گزینه (وکتور یا تصویر)
                if (option.iconRes != null) {
                    Icon(
                        painter = painterResource(id = option.iconRes),
                        contentDescription = option.title,
                        modifier = Modifier.size(28.dp),
                        tint = Color.Unspecified
                    )
                } else if (option.iconVector != null) {
                    Icon(
                        imageVector = option.iconVector,
                        contentDescription = option.title,
                        modifier = Modifier.size(28.dp),
                        tint = option.iconTint
                    )
                }
            }

            // رسم خط نقطه‌چین بین آیتم‌ها
            if (index < shareOptions.size - 1) {
                DashedDivider(
                    color = Color.LightGray.copy(alpha = 0.7f),
                    thickness = 1.dp
                )
            }
        }
    }
}

// کامپوننت سفارشی برای رسم خط نقطه‌چین (Dashed Line)
@Composable
fun DashedDivider(
    color: Color = Color.Gray,
    thickness: androidx.compose.ui.unit.Dp = 1.dp
) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(thickness)
    ) {
        val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
        drawLine(
            color = color,
            start = androidx.compose.ui.geometry.Offset(0f, 0f),
            end = androidx.compose.ui.geometry.Offset(size.width, 0f),
            pathEffect = pathEffect,
            strokeWidth = thickness.toPx()
        )
    }
}