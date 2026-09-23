import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomNavItem("home", "خانه", Icons.Default.Home)
    object Category : BottomNavItem("category", "دسته‌بندی", Icons.Default.Category)
    object Cart : BottomNavItem("cart", "سبد خرید", Icons.Default.ShoppingBag)
    object Profile : BottomNavItem("profile", "پروفایل من", Icons.Default.Person)
}