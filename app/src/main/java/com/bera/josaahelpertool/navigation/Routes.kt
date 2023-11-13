package com.bera.josaahelpertool.navigation

sealed class Routes(val route: String) {
    object HomeScreen: Routes(route = "home_screen")
    object CollegeScreen: Routes(route = "college_screen")
    object BranchScreen: Routes(route = "branch_screen")
    object CutoffScreen: Routes(route = "cutoff_screen")
    object CBRScreen: Routes(route = "cbr_screen")
    object WebView: Routes(route = "web_view?page=")
    object SearchScreen: Routes(route = "search_screen")
}
