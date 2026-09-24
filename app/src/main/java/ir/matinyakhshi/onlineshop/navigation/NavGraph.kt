package ir.matinyakhshi.onlineshop.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ir.matinyakhshi.onlineshop.presentation.address.AddressScreen
import ir.matinyakhshi.onlineshop.presentation.admin.AddEditProductScreen
import ir.matinyakhshi.onlineshop.presentation.admin.AdminDashboardScreen
import ir.matinyakhshi.onlineshop.presentation.auth.AuthScreen
import ir.matinyakhshi.onlineshop.presentation.cart.CartScreen
import ir.matinyakhshi.onlineshop.presentation.category.CategoryProductsScreen
import ir.matinyakhshi.onlineshop.presentation.category.CategoryScreen
import ir.matinyakhshi.onlineshop.presentation.checkout.CheckoutScreen
import ir.matinyakhshi.onlineshop.presentation.detail.ProductDetailScreen
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

    // مسیرهای پنل مدیریت
    object AdminDashboard : Screen("admin_dashboard_screen")
    object AddEditProduct : Screen("add_edit_product_screen?productId={productId}") {
        fun createRoute(productId: String? = null) =
            if (!productId.isNullOrEmpty()) "add_edit_product_screen?productId=$productId" else "add_edit_product_screen"
    }
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
        // ۱. صفحه اسپلش
        composable(route = Screen.Splash.route) {
            SplashScreen(
                onNavigateToMain = {
                    navController.popBackStack()
                    navController.navigate(Screen.Main.route)
                },
                onNavigateToAuth = {
                    navController.popBackStack()
                    navController.navigate(Screen.Auth.route)
                }
            )
        }

        // ۲. صفحه ورود / ثبت نام
        composable(route = Screen.Auth.route) {
            AuthScreen(
                onAuthSuccess = {
                    navController.popBackStack()
                    navController.navigate(Screen.Main.route)
                },
                onAdminAuthSuccess = {
                    navController.popBackStack()
                    navController.navigate(Screen.AdminDashboard.route)
                }
            )
        }

        // ۳. بدنه اصلی برنامه
        composable(route = Screen.Main.route) {
            MainScreen(
                onNavigateToProductDetail = { productId ->
                    navController.navigate(Screen.ProductDetail.createRoute(productId))
                },
                onNavigateToCart = {
                    navController.navigate(Screen.Cart.route)
                },
                onNavigateToOrders = { navController.navigate(Screen.OrdersHistory.route) },
                onNavigateToFavorites = { navController.navigate(Screen.Favorites.route) },
                onNavigateToExperiences = { navController.navigate(Screen.PurchaseExperiences.route) },
                onNavigateToAddresses = { navController.navigate(Screen.Address.route) },
                onNavigateToChangePassword = { navController.navigate(Screen.ChangePassword.route) },
                onNavigateToNotifications = { navController.navigate(Screen.Notifications.route) }
            )
        }

        // ۳.۱. صفحه جزئیات محصول
        composable(
            route = Screen.ProductDetail.route,
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            ProductDetailScreen(
                productId = productId,
                onBackClick = { navController.popBackStack() }
            )
        }

        // ۳.۲. صفحه سبد خرید
        composable(route = Screen.Cart.route) {
            CartScreen(
                onNavigateToCheckout = { storeId ->
                    navController.navigate(Screen.Checkout.createRoute(storeId))
                },
                onBackClick = { navController.popBackStack() }
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

        // ۵. صفحه محصولات دسته‌بندی
        composable(
            route = Screen.CategoryProducts.route,
            arguments = listOf(navArgument("categoryId") { type = NavType.StringType })
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId") ?: ""
            CategoryProductsScreen(
                categoryId = categoryId,
                onProductClick = { productId ->
                    navController.navigate(Screen.ProductDetail.createRoute(productId))
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        // ۶. صفحه آدرس‌ها
        composable(route = Screen.Address.route) {
            AddressScreen(
                onBackClick = { navController.popBackStack() },
                onNavigateToCheckout = { selectedAddress ->
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("selected_address_id", selectedAddress.id)

                    navController.navigate(Screen.Checkout.createRoute(defaultStoreId))
                }
            )
        }

        // ۷. صفحه تاریخچه سفارشات
        composable(route = Screen.OrdersHistory.route) {
            OrdersScreen(
                onBackClick = { navController.popBackStack() },
                onNavigateToNotifications = { navController.navigate(Screen.Notifications.route) }
            )
        }

        // ۸. صفحه پیش‌فاکتور
        composable(
            route = Screen.Checkout.route,
            arguments = listOf(navArgument("storeId") { type = NavType.StringType })
        ) {
            CheckoutScreen(
                onBackClick = { navController.popBackStack() },
                onChangeAddressClick = { navController.navigate(Screen.Address.route) },
                onPaymentClick = { navController.navigate(Screen.OrderSuccess.route) }
            )
        }

        // ۹. صفحات پروفایل
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

        // ================== مسیرهای پنل مدیریت ==================

        // ۱۰. داشبورد مدیریت
        composable(route = Screen.AdminDashboard.route) {
            AdminDashboardScreen(
                onNavigateToAddProduct = {
                    navController.navigate(Screen.AddEditProduct.createRoute())
                },
                onNavigateToEditProduct = { productId ->
                    navController.navigate(Screen.AddEditProduct.createRoute(productId))
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        // ۱۱. افزودن / ویرایش محصول
        composable(
            route = Screen.AddEditProduct.route,
            arguments = listOf(
                navArgument("productId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")
            AddEditProductScreen(
                productId = productId,
                onBackClick = { navController.popBackStack() },
                onSaveSuccess = { navController.popBackStack() }
            )
        }
    }
}