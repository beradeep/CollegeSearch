package com.bera.collegesearch.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bera.collegesearch.components.BranchColumn
import com.bera.collegesearch.components.CollegeColumn
import com.bera.collegesearch.components.CutoffColumn
import com.bera.collegesearch.screens.cutoffsbyrank.CBRScreen
import com.bera.collegesearch.screens.home.HomeScreen


@Composable
fun Navigation(isDarkMode: Boolean, onDarkModeToggle: () -> Unit) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.HomeScreen.route) {
        composable(Routes.HomeScreen.route) {
            HomeScreen(
                navController = navController,
                isDarkMode = isDarkMode,
                onDarkModeToggle = onDarkModeToggle
            )
        }
        composable(Routes.CollegeScreen.route + "/{category}", listOf(navArgument("category") {
            type = NavType.StringType
        })) {
            CollegeColumn(navController = navController)
        }
        composable(
            Routes.BranchScreen.route + "/{college}", listOf(navArgument("college") {
                type = NavType.StringType
            })
        ) {
            BranchColumn(
                navController = navController,
                collegeName = it.arguments?.getString("college") ?: ""
            )
        }
        composable(
            Routes.CutoffScreen.route + "/{college}/{branch}", listOf(navArgument("college") {
                type = NavType.StringType
            }, navArgument("branch") {
                type = NavType.StringType
            })
        ) {
            CutoffColumn()
        }
        composable(Routes.CBRScreen.route) {
            CBRScreen()
        }
    }

}