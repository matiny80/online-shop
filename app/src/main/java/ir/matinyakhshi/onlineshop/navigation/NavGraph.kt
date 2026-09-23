package ir.matinyakhshi.onlineshop.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import ir.matinyakhshi.onlineshop.presentation.auth.AuthScreen
import ir.matinyakhshi.onlineshop.presentation.category.CategoryProductsScreen
import ir.matinyakhshi.onlineshop.presentation.category.CategoryScreen
import ir.matinyakhshi.onlineshop.presentation.main.MainScreen
import ir.matinyakhshi.onlineshop.presentation.orders.OrdersScreen
import ir.matinyakhshi.onlineshop.presentation.profile.ChangePasswordScreen
import ir.matinyakhshi.onlineshop.presentation.profile.FavoritesScreen
import ir.matinyakhshi.onlineshop.presentation.profile.NotificationsScreen
import ir.matinyakhshi.onlineshop.presentation.profile.PurchaseExperiencesScreen
import ir.matinyakhshi.onlineshop.presentation.splash.SplashScreen

sealed class Screen(val route: String) {
    object Splash : Screen("splash_screen")
    object Main : Screen("main_screen")
    object Home : Screen("home_screen")
    object Category : Screen("category_screen")
    object CategoryProducts : Screen("category_products/{categoryId}") {
        fun createRoute(categoryId: String) = "category_products/$categoryId"
    }
    object Cart : Screen("cart_screen")
    object Profile : Screen("profile_screen")

    object Address : Screen("address_screen")
    object Auth : Screen("auth_screen")
    object OrdersHistory : Screen("orders_history_screen")
    object ProductDetail : Screen("product_detail/{productId}") {
        fun createRoute(productId: String) = "product_detail/$productId"
    }
    object Checkout : Screen("checkout_screen/{storeId}") {
        fun createRoute(storeId: String) = "checkout_screen/$storeId"
    }
    object OrderSuccess : Screen("order_success_screen")

    object Favorites : Screen("favorites_screen")
    object PurchaseExperiences : Screen("purchase_experiences_screen")
    object ChangePassword : Screen("change_password_screen")
    object Notifications : Screen("notifications_screen")
}

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    defaultStoreId: String = "default_store"
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        // ۱. صفحه اسپلش (چک کردن لاگین بودن کاربر)
        composable(route = Screen.Splash.route) {
            SplashScreen(
                onNavigateToMain = {
                    navController.popBackStack() // حذف اسپلش از BackStack
                    navController.navigate(Screen.Main.route)
                },
                onNavigateToAuth = {
                    navController.popBackStack() // حذف اسپلش از BackStack
                    navController.navigate(Screen.Auth.route)
                }
            )
        }

        // ۲. صفحه ورود / ثبت نام
        composable(route = Screen.Auth.route) {
            AuthScreen(
                onAuthSuccess = {
                    navController.popBackStack() // حذف صفحه ورود
                    navController.navigate(Screen.Main.route)
                }
            )
        }

        // ۳. بدنه اصلی برنامه
        composable(route = Screen.Main.route) {
            MainScreen(
                onNavigateToProductDetail = { productId ->
                    navController.navigate(Screen.ProductDetail.createRoute(productId))
                },
                onNavigateToOrders = { navController.navigate(Screen.OrdersHistory.route) },
                onNavigateToFavorites = { navController.navigate(Screen.Favorites.route) },
                onNavigateToExperiences = { navController.navigate(Screen.PurchaseExperiences.route) },
                onNavigateToAddresses = { navController.navigate(Screen.Address.route) },
                onNavigateToChangePassword = { navController.navigate(Screen.ChangePassword.route) },
                onNavigateToNotifications = { navController.navigate(Screen.Notifications.route) }
            )
        }

        // ۴. صفحه دسته‌بندی‌ها
        composable(route = Screen.Category.route) {
            CategoryScreen(
                onCategoryClick = { categoryId ->
                    navController.navigate(Screen.CategoryProducts.createRoute(categoryId))
                },
                onBackClick = { navController.popBackStack() },
                onNotificationClick = { navController.navigate(Screen.Notifications.route) }
            )
        }

        // ۵. صفحه محصولات دسته‌بندی انتخابی (استخراج categoryId از arguments)
        composable(route = Screen.CategoryProducts.route) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId") ?: ""
            CategoryProductsScreen(
                categoryId = categoryId,
                onProductClick = { productId ->
                    navController.navigate(Screen.ProductDetail.createRoute(productId))
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        // ۶. صفحه تاریخچه سفارشات
        composable(route = Screen.OrdersHistory.route) {
            OrdersScreen(
                onBackClick = { navController.popBackStack() },
                onNavigateToNotifications = { navController.navigate(Screen.Notifications.route) }
            )
        }

        // ۷. مسیرهای فرعی بخش پروفایل
        composable(route = Screen.ChangePassword.route) {
            ChangePasswordScreen(onBackClick = { navController.popBackStack() })
        }

        composable(route = Screen.Notifications.route) {
            NotificationsScreen(onBackClick = { navController.popBackStack() })
        }

        composable(route = Screen.Favorites.route) {
            FavoritesScreen(
                onProductClick = { productId ->
                    navController.navigate(Screen.ProductDetail.createRoute(productId))
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(route = Screen.PurchaseExperiences.route) {
            PurchaseExperiencesScreen(onBackClick = { navController.popBackStack() })
        }
    }
}