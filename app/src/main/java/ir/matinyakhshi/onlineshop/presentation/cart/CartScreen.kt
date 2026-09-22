package ir.matinyakhshi.onlineshop.presentation.cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import ir.matinyakhshi.onlineshop.data.local.entity.CartItemEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    onChangeAddressClick: () -> Unit,
    onCheckoutClick: () -> Unit,
    viewModel: CartViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("سبد خرید") })
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (val state = uiState) {
                is CartUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is CartUiState.Empty -> {
                    Text(
                        text = "سبد خرید شما خالی است",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is CartUiState.Error -> {
                    Text(
                        text = state.message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is CartUiState.Success -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        LazyColumn(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(state.cartItems, key = { it.productId }) { item ->
                                CartItemCard(
                                    item = item,
                                    onIncrease = { viewModel.increaseQuantity(item) },
                                    onDecrease = { viewModel.decreaseQuantity(item) },
                                    onRemove = { viewModel.removeItem(item) }
                                )
                            }
                        }

                        // بخش خلاصه فاکتور و دکمه پرداخت
                        CartSummarySection(
                            totalPrice = state.totalPrice,
                            totalDiscount = state.totalDiscount,
                            finalPrice = state.finalPrice,
                            onCheckoutClick = onCheckoutClick
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CartItemCard(
    item: CartItemEntity,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = item.imageUrl,
                contentDescription = item.title,
                modifier = Modifier
                    .size(80.dp)
                    .clip(MaterialTheme.shapes.small),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${item.price} تومان",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            // کنترل تعداد (بدون نیاز به آیکون Remove)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                IconButton(onClick = onDecrease, modifier = Modifier.size(32.dp)) {
                    if (item.quantity == 1) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "حذف"
                        )
                    } else {
                        Text(
                            text = "−",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }

                Text(
                    text = item.quantity.toString(),
                    style = MaterialTheme.typography.bodyLarge
                )

                IconButton(onClick = onIncrease, modifier = Modifier.size(32.dp)) {
                    Icon(Icons.Default.Add, contentDescription = "افزایش")
                }
            }
        }
    }
}

@Composable
fun CartSummarySection(
    totalPrice: Long,
    totalDiscount: Long,
    finalPrice: Long,
    onCheckoutClick: () -> Unit
) {
    Surface(
        shadowElevation = 8.dp,
        tonalElevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("قیمت کل:")
                Text("$totalPrice تومان")
            }

            if (totalDiscount > 0) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("تخفیف:", color = MaterialTheme.colorScheme.error)
                    Text("$totalDiscount تومان", color = MaterialTheme.colorScheme.error)
                }
            }

            HorizontalDivider()

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("مبلغ قابل پرداخت:", style = MaterialTheme.typography.titleMedium)
                Text(
                    "$finalPrice تومان",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onCheckoutClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("ادامه فرآیند خرید")
            }
        }
    }
}