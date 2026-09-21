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
import ir.matinyakhshi.onlineshop.presentation.home.HomeScreen
import ir.matinyakhshi.onlineshop.presentation.home.StoreViewModel
import ir.matinyakhshi.onlineshop.presentation.product.ProductDetailScreen
import ir.matinyakhshi.onlineshop.presentation.product.ProductDetailViewModel

sealed class Screen(val route: String) {
    object Auth : Screen("auth_screen")
    object Home : Screen("home_screen")
    object ProductDetail : Screen("product_detail/{productId}") {
        fun createRoute(productId: String) = "product_detail/$productId"
    }
    object Cart : Screen("cart_screen")
    object Checkout : Screen("checkout_screen")
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
        // صفحه ورود
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

        // صفحه اصلی
        composable(route = Screen.Home.route) {
            val viewModel: StoreViewModel = hiltViewModel()
            HomeScreen(
                viewModel = viewModel,
                onNavigateToProductDetail = { productId ->
                    navController.navigate(Screen.ProductDetail.createRoute(productId))
                }
            )
        }

        // صفحه جزئیات محصول
        composable(
            route = Screen.ProductDetail.route,
            arguments = listOf(
                navArgument("productId") { type = NavType.StringType }
            )
        ) {
            val viewModel: ProductDetailViewModel = hiltViewModel()
            ProductDetailScreen(
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() },
                onAddToCartClick = {
                    navController.navigate(Screen.Cart.route)
                }
            )
        }

        // صفحه سبد خرید
        composable(route = Screen.Cart.route) {
            val viewModel: CartViewModel = hiltViewModel()
            CartScreen(
                viewModel = viewModel,
                onChangeAddressClick = {
                    // مسیر آدرس‌ها
                },
                onCheckoutClick = {
                    navController.navigate(Screen.Checkout.route)
                }
            )
        }

        // صفحه تسویه حساب
        composable(route = Screen.Checkout.route) {
            val viewModel: CheckoutViewModel = hiltViewModel()
            CheckoutScreen(
                viewModel = viewModel,
                storeId = storeId,
                onBackClick = { navController.popBackStack() },
                onOrderSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) {
                            inclusive = false
                        }
                    }
                }
            )
        }
    }
}