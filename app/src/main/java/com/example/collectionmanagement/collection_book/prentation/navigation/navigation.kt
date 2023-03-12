package com.example.collectionmanagement.collection_book.prentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.collectionmanagement.collection_book.prentation.DebtorScreen
import com.example.collectionmanagement.collection_book.prentation.Home.HomePage

var home:String ="Home"
var debtor:String ="Debtor"

//sealed class Router(val route:String);

enum class Router{
    HomeScreen,
    DebtorScreen

}


//class HomeNav:Router(route = "Home")
//class DebtorNav:Router(route = "Debtor")

@Composable
fun ComposeNavigation(){
    var navController= rememberNavController();
    NavHost(navController = navController, startDestination = Router.HomeScreen.toString()){
        composable(route = Router.HomeScreen.toString()){
            HomePage(navHostController = navController)
        }
        composable(route = Router.DebtorScreen.toString()){
            DebtorScreen()
        }
    }
}