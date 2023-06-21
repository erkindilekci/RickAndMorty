package com.erkindilekci.rickandmorty.util

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.erkindilekci.rickandmorty.presentation.detailscreen.DetailsScreen
import com.erkindilekci.rickandmorty.presentation.listscreen.ListScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.ListScreen.route) {
        composable(Screen.ListScreen.route) {
            ListScreen(navController = navController)
        }
        composable(Screen.DetailsScreen.route) {
            DetailsScreen(navController = navController)
        }
    }
}
