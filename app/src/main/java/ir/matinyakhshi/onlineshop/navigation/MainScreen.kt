package ir.matinyakhshi.onlineshop.presentation.main

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ir.matinyakhshi.onlineshop.navigation.Screen
import ir.matinyakhshi.onlineshop.presentation.home.HomeScreen
import ir.matinyakhshi.onlineshop.presentation.profile.ProfileScreen

// تعریف آیتم‌های نویگیشن بار پایین
sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomNavItem(Screen.Home.route, "خانه", Icons.Default.Home)
    object Category : BottomNavItem(Screen.Category.route, "دسته‌بندی", Icons.Default.Category)
    object Cart : BottomNavItem(Screen.Cart.route, "سبد خرید", Icons.Default.ShoppingBag)
    object Profile : BottomNavItem(Screen.Profile.route, "پروفایل من", Icons.Default.Person)
}

@Composable
fun MainScreen(
    onNavigateToProductDetail: (String) -> Unit,
    onNavigateToOrders: () -> Unit,
    onNavigateToFavorites: () -> Unit,
    onNavigateToExperiences: () -> Unit,
    onNavigateToAddresses: () -> Unit,
    onNavigateToChangePassword: () -> Unit,
    onNavigateToNotifications: () -> Unit
) {
    val bottomNavController = rememberNavController()
    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomNavItems = listOf(
        BottomNavItem.Home,
        BottomNavItem.Category,
        BottomNavItem.Cart,
        BottomNavItem.Profile
    )

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp,
                modifier = Modifier.height(65.dp)
            ) {
                bottomNavItems.forEach { item ->
                    val isSelected = currentRoute == item.route

                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            if (currentRoute != item.route) {
                                bottomNavController.navigate(item.route) {
                                    popUpTo(bottomNavController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        label = {
                            Text(
                                text = item.title,
                                fontSize = 11.sp
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFFFF5232),
                            selectedTextColor = Color(0xFFFF5232),
                            unselectedIconColor = Color(0xFF757575),
                            unselectedTextColor = Color(0xFF757575),
                            indicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = bottomNavController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            // ۱. صفحه خانه
            composable(route = BottomNavItem.Home.route) {
                HomeScreen(
                    onNavigateToProductDetail = onNavigateToProductDetail,
                    onCategoryClick = { categoryId ->
                        bottomNavController.navigate(BottomNavItem.Category.route) {
                            popUpTo(bottomNavController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )

            }

            // ۲. صفحه دسته‌بندی
            composable(BottomNavItem.Category.route) {
                // CategoryScreen(...)
            }

            // ۳. صفحه سبد خرید
            composable(BottomNavItem.Cart.route) {
                // CartScreen(...)
            }

            // ۴. صفحه پروفایل
            composable(BottomNavItem.Profile.route) {
                ProfileScreen(
                    onNavigateToOrders = onNavigateToOrders,
                    onNavigateToFavorites = onNavigateToFavorites,
                    onNavigateToExperiences = onNavigateToExperiences,
                    onNavigateToAddresses = onNavigateToAddresses,
                    onNavigateToChangePassword = onNavigateToChangePassword,
                    onNavigateToNotifications = onNavigateToNotifications,
                    onBackClick = { bottomNavController.popBackStack() }
                )
            }
        }
    }
}