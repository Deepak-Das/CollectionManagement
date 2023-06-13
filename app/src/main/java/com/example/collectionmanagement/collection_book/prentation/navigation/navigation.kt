package com.example.collectionmanagement.collection_book.prentation.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import com.example.collectionmanagement.collection_book.prentation.Daily.DailyScreen
import com.example.collectionmanagement.collection_book.prentation.DebtorScreen
import com.example.collectionmanagement.collection_book.prentation.Home.HomePage
import com.example.collectionmanagement.collection_book.prentation.LoanPage
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController


//sealed class Router(val route:String);

enum class Router{
    HomeScreen,
    DebtorScreen,
    DailyScreen,
    LoneScreen,
    PaymentByIdScreen,
    PaymentByLimit,

}


//class HomeNav:Router(route = "Home")
//class DebtorNav:Router(route = "Debtor")

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ComposeNavigation(){
    var navController= rememberAnimatedNavController();
    AnimatedNavHost(navController = navController, startDestination = Router.HomeScreen.toString()){
        composable(

            route = Router.HomeScreen.toString()

        ){
            HomePage(navHostController = navController)
        }
        composable(
            Router.DebtorScreen.toString(),
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))

            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))

            },

        ) { DebtorScreen() }
        composable(
            Router.LoneScreen.toString(),
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))

            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))

            },

        ) {
            LoanPage()
        }
        composable(
            Router.DailyScreen.toString(),
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))

            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))

            },

        ) {
            //todo: send on lone page on click payrow
            DailyScreen(navHostController = navController)
        }

    }
}