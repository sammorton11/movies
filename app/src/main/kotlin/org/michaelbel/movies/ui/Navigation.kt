package org.michaelbel.movies.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.michaelbel.movies.details.DetailsScreen
import org.michaelbel.movies.feed.FeedScreen
import org.michaelbel.movies.navigation.NavGraph

@Composable
fun NavigationContent(
    navController: NavHostController,
    onAppUpdateClicked: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = NavGraph.Home.route
    ) {
        composable(route = NavGraph.Home.route) {
            FeedScreen(navController, onAppUpdateClicked)
        }
        composable(
            route = NavGraph.Movie.routeWithArgs,
            arguments = listOf(navArgument(NavGraph.Movie.argMovieId) { type = NavType.LongType })
        ) { backStackEntry ->
            val movieId: Long? = backStackEntry.arguments?.getLong(NavGraph.Movie.argMovieId)
            if (movieId != null) {
                DetailsScreen(
                    navController = navController,
                    movieId = movieId
                )
            }
        }
    }
}