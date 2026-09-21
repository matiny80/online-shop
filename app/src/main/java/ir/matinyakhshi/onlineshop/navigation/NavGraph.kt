package ir.matinyakhshi.onlineshop.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ir.matinyakhshi.onlineshop.presentation.cart.CartScreen
import ir.matinyakhshi.onlineshop.presentation.cart.CartViewModel
import ir.matinyakhshi.onlineshop.presentation.cart.CheckoutScreen
import ir.matinyakhshi.onlineshop.presentation.cart.CheckoutViewModel
import ir.matinyakhshi.onlineshop.presentation.home.HomeScreen
import ir.matinyakhshi.onlineshop.presentation.home.StoreViewModel
import ir.matinyakhshi.onlineshop.presentation.product.ProductDetailScreen
import ir.matinyakhshi.onlineshop.presentation.product.ProductDetailViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: String = "home",
    storeId: String = "default_store"
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = "home") {
            val viewModel: StoreViewModel = hiltViewModel()

            HomeScreen(
                viewModel = viewModel,
                onNavigateToProductDetail = { productId ->
                    navController.navigate("product_detail/$productId")
                }
            )
        }

        composable(
            route = "product_detail/{productId}",
            arguments = listOf(
                navArgument("productId") { type = NavType.StringType }
            )
        ) {
            val viewModel: ProductDetailViewModel = hiltViewModel()

            ProductDetailScreen(
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() },
                onAddToCartClick = { product ->
                    viewModel.addToCart(product)
                    navController.navigate("cart")
                }
            )
        }

        composable(route = "cart") {
            val viewModel: CartViewModel = hiltViewModel()

            CartScreen(
                viewModel = viewModel,
                onCheckoutClick = {
                    navController.navigate("checkout")
                }
            )
        }

        composable(route = "checkout") {
            val viewModel: CheckoutViewModel = hiltViewModel()

            CheckoutScreen(
                viewModel = viewModel,
                storeId = storeId,
                onBackClick = { navController.popBackStack() },
                onOrderSuccess = {
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            )
        }
    }
}