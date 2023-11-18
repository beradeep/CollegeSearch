package com.bera.josaahelpertool.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bera.josaahelpertool.components.BranchColumn
import com.bera.josaahelpertool.components.BranchColumnViewModel
import com.bera.josaahelpertool.components.CollegeColumn
import com.bera.josaahelpertool.components.CollegeColumnViewModel
import com.bera.josaahelpertool.components.CutoffColumn
import com.bera.josaahelpertool.components.CutoffColumnViewModel
import com.bera.josaahelpertool.screens.cutoffsbyrank.CBRScreen
import com.bera.josaahelpertool.screens.cutoffsbyrank.CBRViewModel
import com.bera.josaahelpertool.screens.home.HomeScreen
import com.bera.josaahelpertool.screens.home.HomeViewModel
import com.bera.josaahelpertool.screens.search.SearchScreen
import com.bera.josaahelpertool.screens.search.SearchViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = Routes.HomeScreen.route,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Start
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Start
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.End
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.End
            )
        }
    ) {
        composable(Routes.HomeScreen.route) {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(navController = navController, viewModel = homeViewModel)
        }
        composable(Routes.CollegeScreen.route + "/{category}", listOf(navArgument("category") {
            type = NavType.StringType
        })) {
            val viewModel = hiltViewModel<CollegeColumnViewModel>()
            CollegeColumn(navController = navController, viewModel = viewModel)
        }
        composable(
            Routes.BranchScreen.route + "/{college}", listOf(navArgument("college") {
                type = NavType.StringType
            })
        ) {
            val viewModel = hiltViewModel<BranchColumnViewModel>()
            BranchColumn(
                navController = navController,
                collegeName = it.arguments?.getString("college") ?: "",
                viewModel = viewModel
            )
        }
        composable(
            Routes.CutoffScreen.route + "/{college}/{branch}", listOf(navArgument("college") {
                type = NavType.StringType
            }, navArgument("branch") {
                type = NavType.StringType
            })
        ) {
            val viewModel = hiltViewModel<CutoffColumnViewModel>()
            CutoffColumn(viewModel)
        }
        composable(Routes.CBRScreen.route) {
            val viewModel = hiltViewModel<CBRViewModel>()
            CBRScreen(viewModel)
        }
        composable(Routes.SearchScreen.route) {
            val viewModel = hiltViewModel<SearchViewModel>()
            SearchScreen(navController = navController, viewModel = viewModel)
        }
    }
}