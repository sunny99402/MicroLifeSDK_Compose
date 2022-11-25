package com.example.testjetpack

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "chose" ) {
        composable("chose") {
            ChoseScreen()
        }
        composable("connect") {
            ConnectScreen()
        }

    }
}