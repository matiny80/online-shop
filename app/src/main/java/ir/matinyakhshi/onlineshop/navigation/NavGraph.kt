package ir.matinyakhshi.onlineshop.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ir.matinyakhshi.onlineshop.presentation.auth.confirmcode.ConfirmCodeScreen
import ir.matinyakhshi.onlineshop.presentation.auth.login.LoginScreen
import ir.matinyakhshi.onlineshop.presentation.auth.submitinfo.SubmitInfoScreen
import ir.matinyakhshi.onlineshop.presentation.home.HomeScreen
import ir.matinyakhshi.onlineshop.presentation.splash.SplashScreen

object Routes {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val CONFIRM_CODE = "confirm_code"
    const val SUBMIT_INFO = "submit_info"
    const val HOME = "home"
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {
        // ۱. صفحه Splash
        composable(Routes.SPLASH) {
            SplashScreen(
                onNavigateToLogin = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                },
                onNavigateToHome = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                }
            )
        }

        // ۲. صفحه ورود (Login)
        composable(Routes.LOGIN) {
            LoginScreen(
                onNavigateToConfirmCode = {
                    navController.navigate(Routes.CONFIRM_CODE)
                }
            )
        }

        // ۳. صفحه تأیید کد (Confirm Code)
        composable(Routes.CONFIRM_CODE) {
            ConfirmCodeScreen(
                onNavigateToSubmitInfo = {
                    navController.navigate(Routes.SUBMIT_INFO) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        // ۴. صفحه ثبت اطلاعات (Submit Information)
        composable(Routes.SUBMIT_INFO) {
            SubmitInfoScreen(
                onNavigateToHome = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        // ۵. صفحه اصلی (Home)
        composable(Routes.HOME) {
            HomeScreen(
                onNavigateToProductDetail = { productId ->
                    // بعداً مسیر جزئیات محصول را اضافه می‌کنیم
                }
            )
        }
    }
}