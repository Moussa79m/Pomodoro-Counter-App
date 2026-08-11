//package com.example.pomodorowatch
//
//import androidx.compose.runtime.Composable
//import androidx.navigation.NavType
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.rememberNavController
//import androidx.navigation.navArgument
//import com.example.pomodorowatch.ViewModel.TimerViewModel
//import com.example.pomodorowatch.ui.theme.Screens.SessionDetailScreen
//
//@Composable
//fun AppNavigation(viewModel: TimerViewModel) {
//    val navController= rememberNavController()
//    NavHost(
//        navController = navController,
//        startDestination = Screen.Timer.route
//    ){
//        composable(route=Screen.SessionDetail.route,
//            arguments = listOf(navArgument("sessionId"){type= NavType.IntType})
//            ){ backStackEntry->
//            val sessionId=backStackEntry.arguments?.getInt("sessionId")?:0
//
//            SessionDetailScreen(sessionId, viewModel, onBackClick ={navController.popBackStack()} )
//        }
//    }
//}