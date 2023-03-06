package com.example.collectionmanagement.collection_book.prentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.collectionmanagement.collection_book.prentation.Home.HomePage

var home:String ="Home"

@Composable
fun ComposeNavigation(){
    var navController= rememberNavController();
    NavHost(navController = navController, startDestination = home){
        composable(route = home){
            HomePage()
        }
    }
}