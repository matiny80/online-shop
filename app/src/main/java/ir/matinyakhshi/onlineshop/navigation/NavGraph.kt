package ir.matinyakhshi.onlineshop.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ir.matinyakhshi.onlineshop.presentation.auth.AuthScreen
import ir.matinyakhshi.onlineshop.presentation.auth.AuthViewModel
import ir.matinyakhshi.onlineshop.presentation.cart.CartScreen
import ir.matinyakhshi.onlineshop.presentation.cart.CartViewModel
import ir.matinyakhshi.onlineshop.presentation.cart.CheckoutScreen
import ir.matinyakhshi.onlineshop.presentation.cart.CheckoutViewModel
import ir.matinyakhshi.onlineshop.presentation.cart.OrderSuccessScreen
import ir.matinyakhshi.onlineshop.presentation.home.HomeScreen
import ir.matinyakhshi.onlineshop.presentation.home.StoreViewModel
// اصلاح Import براساس پکیج‌نیم جدید شما
import ir.matinyakhshi.onlineshop.presentation.product.detail.ProductDetailScreen
import ir.matinyakhshi.onlineshop.presentation.product.ProductDetailViewModel

sealed class Screen(val route: String) {
    object Auth : Screen("auth_screen")
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
    storeId: String = "default_store"
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
            val viewModel: CartViewModel = hiltViewModel()
            CartScreen(
                viewModel = viewModel,
                onChangeAddressClick = {
                    // مسیر آدرس‌ها
                },
                onCheckoutClick = {
                    navController.navigate(Screen.Checkout.createRoute(storeId))
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
            val currentStoreId = backStackEntry.arguments?.getString("storeId") ?: storeId
            val viewModel: CheckoutViewModel = hiltViewModel()

            CheckoutScreen(
                storeId = currentStoreId,
                onBackClick = { navController.popBackStack() },
                onOrderSuccess = {
                    navController.navigate(Screen.OrderSuccess.route) {
                        popUpTo(Screen.Cart.route) { inclusive = true }
                    }
                },
                viewModel = viewModel
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