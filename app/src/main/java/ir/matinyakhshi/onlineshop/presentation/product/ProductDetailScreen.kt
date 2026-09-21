package ir.matinyakhshi.onlineshop.presentation.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.matinyakhshi.onlineshop.R
import ir.matinyakhshi.onlineshop.ui.theme.*

@Composable
fun ProductDetailScreen(
    onBackClick: () -> Unit,
    onAddToCartClick: () -> Unit
) {
    var quantity by remember { mutableIntStateOf(1) }

    Box(modifier = Modifier.fillMaxSize().background(BackgroundCream)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 80.dp)
        ) {
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = TextDark
                    )
                }
                Text("جزئیات محصول", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(48.dp))
            }

            // Image Slider Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    // Badge Discount
                    Box(
                        modifier = Modifier
                            .padding(12.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(BorderRed)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                            .align(Alignment.TopEnd)
                    ) {
                        Text("%۱۵", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }

                    // Main Product Image Placeholder
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground), // جایگزین با عکس محصول
                        contentDescription = "Product Image",
                        modifier = Modifier
                            .size(200.dp)
                            .align(Alignment.Center)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Content Section
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                // Title and Rating
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("ست سویشرت و شلوار مردانه", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextDark)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Star, contentDescription = "Rating", tint = Color(0xFFFFB800), modifier = Modifier.size(18.dp))
                        Text("۴.۵", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Description Title & Text
                Text("توضیحات", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextDark)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "با توجه به تفاوت رنگ‌ها در صفحه نمایش دستگاه‌های مختلف، ممکن است رنگ محصول ۱۰٪ تا ۱۵٪ با واقعیت متفاوت باشد.",
                    fontSize = 13.sp,
                    color = TextGray,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Features Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("ویژگی‌ها", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        FeatureRow(label = "وزن خالص:", value = "۴۲۰ گرم")
                        FeatureRow(label = "وزن با بسته‌بندی:", value = "۴۵۰ گرم")
                    }
                }
            }
        }

        // Bottom Add To Cart Bar
        Surface(
            modifier = Modifier.align(Alignment.BottomCenter),
            shadowElevation = 8.dp,
            color = Color.White
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Price Section
                Column {
                    Text("۱,۸۵۰,۰۰۰ تومان", fontSize = 12.sp, color = TextGray, textDecoration = TextDecoration.LineThrough)
                    Text("۱,۵۵۰,۰۰۰ تومان", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = BorderRed)
                }

                // Add Button
                Button(
                    onClick = onAddToCartClick,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = OrangePrimary)
                ) {
                    Text("افزودن به سبد خرید", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun FeatureRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, fontSize = 12.sp, color = TextGray)
        Text(value, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextDark)
    }
}