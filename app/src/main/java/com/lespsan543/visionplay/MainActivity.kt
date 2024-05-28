package com.lespsan543.visionplay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lespsan543.visionplay.app.ui.viewModel.VisionPlayViewModel
import com.lespsan543.visionplay.app.navigation.Routes
import com.lespsan543.visionplay.app.ui.CinemaScreen
import com.lespsan543.visionplay.app.ui.FavoritesScreen
import com.lespsan543.visionplay.app.ui.InitialScreen
import com.lespsan543.visionplay.app.ui.LogInScreen
import com.lespsan543.visionplay.app.ui.MoviesScreen
import com.lespsan543.visionplay.app.ui.RegisterScreen
import com.lespsan543.visionplay.app.ui.SearchGenres
import com.lespsan543.visionplay.app.ui.SearchScreen
import com.lespsan543.visionplay.app.ui.SeriesScreen
import com.lespsan543.visionplay.app.ui.ShowMovie
import com.lespsan543.visionplay.app.ui.ShowMoviesAndSeriesByGenre
import com.lespsan543.visionplay.ui.theme.VisionPlayTheme

/**
 * Clase principal que se encarga de mostrar la interfaz al usuario y guarda la configuración de navegación de la app
 *
 * @property visionPlayViewModel viewModel que maneja los datos de las pantallas una vez iniciada la sesión del usuario
 */
class MainActivity : ComponentActivity() {
    private val visionPlayViewModel : VisionPlayViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VisionPlayTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "Initial") {
                        composable("Initial"){
                            InitialScreen(navController = navController, visionPlayViewModel = visionPlayViewModel)
                        }
                        composable(Routes.LogInScreen.route) {
                            LogInScreen(navController, visionPlayViewModel)
                        }
                        composable(Routes.RegisterScreen.route) {
                            RegisterScreen(navController, visionPlayViewModel)
                        }
                        composable(Routes.MoviesScreen.route) {
                            MoviesScreen(navController, visionPlayViewModel)
                        }
                        composable(Routes.SeriesScreen.route) {
                            SeriesScreen(navController, visionPlayViewModel)
                        }
                        composable(Routes.FavoritesScreen.route) {
                            FavoritesScreen(navController, visionPlayViewModel)
                        }
                        composable(Routes.ShowMovie.route) {
                            ShowMovie(navController, visionPlayViewModel)
                        }
                        composable(Routes.SearchGenres.route) {
                            SearchGenres(navController, visionPlayViewModel)
                        }
                        composable(Routes.ShowByGenre.route) {
                            ShowMoviesAndSeriesByGenre(navController, visionPlayViewModel)
                        }
                        composable(Routes.CinemaScreen.route) {
                            CinemaScreen(navController, visionPlayViewModel)
                        }
                        composable(Routes.SearchScreen.route) {
                            SearchScreen(navController, visionPlayViewModel)
                        }
                    }
                }
            }
        }
    }
}