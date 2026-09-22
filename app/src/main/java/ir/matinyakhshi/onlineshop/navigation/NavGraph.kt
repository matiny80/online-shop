package ir.matinyakhshi.onlineshop.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ir.matinyakhshi.onlineshop.presentation.address.AddressScreen
import ir.matinyakhshi.onlineshop.presentation.auth.AuthScreen
import ir.matinyakhshi.onlineshop.presentation.auth.AuthViewModel
import ir.matinyakhshi.onlineshop.presentation.cart.CartScreen
import ir.matinyakhshi.onlineshop.presentation.cart.CartViewModel
import ir.matinyakhshi.onlineshop.presentation.cart.CheckoutScreen
import ir.matinyakhshi.onlineshop.presentation.cart.CheckoutViewModel
import ir.matinyakhshi.onlineshop.presentation.cart.OrderSuccessScreen
import ir.matinyakhshi.onlineshop.presentation.category.CategoryProductsScreen
import ir.matinyakhshi.onlineshop.presentation.category.CategoryScreen
import ir.matinyakhshi.onlineshop.presentation.home.HomeScreen
import ir.matinyakhshi.onlineshop.presentation.home.StoreViewModel
import ir.matinyakhshi.onlineshop.presentation.product.ProductDetailViewModel
import ir.matinyakhshi.onlineshop.presentation.product.detail.ProductDetailScreen
import ir.matinyakhshi.onlineshop.presentation.profile.OrdersHistoryScreen

sealed class Screen(val route: String) {
    object Address : Screen("address_screen")
    object Auth : Screen("auth_screen")
    object Category : Screen("category_screen")
    object OrdersHistory : Screen("orders_history_screen")
    object Home : Screen("home_screen")
    object ProductDetail : Screen("product_detail/{productId}") {
        fun createRoute(productId: String) = "product_detail/$productId"
    }
    object Cart : Screen("cart_screen")
    object Checkout : Screen("checkout_screen/{storeId}") {
        fun createRoute(storeId: String) = "checkout_screen/$storeId"
    }
    object OrderSuccess : Screen("order_success_screen")
}

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    defaultStoreId: String = "default_store"
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Auth.route
    ) {
        // ۱. صفحه ورود
        composable(route = Screen.Auth.route) {
            val viewModel: AuthViewModel = hiltViewModel()
            AuthScreen(
                viewModel = viewModel,
                onAuthSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Auth.route) { inclusive = true }
                    }
                }
            )
        }

        // مسیریابی دسته‌بندی
        composable(route = Screen.Category.route) {
            CategoryScreen(
                onCategoryClick = { categoryId ->
                    // هدایت به لیست محصولات این دسته‌بندی
                    navController.navigate("products_by_category/$categoryId")

                }
            )
        }

        composable(
            route = "products_by_category/{categoryId}",
            arguments = listOf(navArgument("categoryId") { type = NavType.StringType })
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId") ?: ""
            // اینجا Screen مربوط به لیست محصولات (مثلاً CategoryProductsScreen) را قرار بده
            CategoryProductsScreen(
                categoryId = categoryId,
                onProductClick = { productId ->
                    navController.navigate("product_detail/$productId")
                },
                onBackClick = { navController.popBackStack() }
            )
        }


// مسیریابی تاریخچه سفارشات
        composable(route = Screen.OrdersHistory.route) {
            OrdersHistoryScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        // ۲. صفحه اصلی
        composable(route = Screen.Home.route) {
            val viewModel: StoreViewModel = hiltViewModel()
            HomeScreen(
                viewModel = viewModel,
                onNavigateToProductDetail = { productId ->
                    navController.navigate(Screen.ProductDetail.createRoute(productId))
                }
            )
        }

        // ۳. صفحه جزئیات محصول
        composable(
            route = Screen.ProductDetail.route,
            arguments = listOf(
                navArgument("productId") { type = NavType.StringType }
            )
        ) {
            val viewModel: ProductDetailViewModel = hiltViewModel()
            ProductDetailScreen(
                onBackClick = { navController.popBackStack() },
                onAddToCartClick = {
                    navController.navigate(Screen.Cart.route)
                }
            )
        }

        // ۴. صفحه سبد خرید
        composable(route = Screen.Cart.route) {
            CartScreen(
                onNavigateToCheckout = { storeId ->
                    navController.navigate(Screen.Checkout.createRoute(storeId))
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(route = Screen.Address.route) {
            AddressScreen(
                onBackClick = { navController.popBackStack() },
                onAddressSelected = { selectedAddress ->
                    // ذخیره آدرس انتخابی و بازگشت
                    navController.popBackStack()
                }
            )
        }

        // ۵. صفحه تسویه حساب
        composable(
            route = Screen.Checkout.route,
            arguments = listOf(
                navArgument("storeId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val currentStoreId = backStackEntry.arguments?.getString("storeId") ?: defaultStoreId
            val viewModel: CheckoutViewModel = hiltViewModel()

            CheckoutScreen(
                storeId = currentStoreId,
                viewModel = viewModel,
                onOrderSuccess = {
                    navController.navigate(Screen.OrderSuccess.route) {
                        popUpTo(Screen.Home.route) { inclusive = false }
                    }
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        // ۶. صفحه موفقیت سفارش
        composable(route = Screen.OrderSuccess.route) {
            OrderSuccessScreen(
                onHomeClick = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }
    }
}