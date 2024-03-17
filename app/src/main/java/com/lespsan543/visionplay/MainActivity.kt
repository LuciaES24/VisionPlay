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
import com.lespsan543.apppeliculas.peliculas.ui.viewModel.FavotitesViewModel
import com.lespsan543.apppeliculas.peliculas.ui.viewModel.LogInOrRegisterViewModel
import com.lespsan543.visionplay.app.ui.viewModel.MoviesOrSeriesViewModel
import com.lespsan543.visionplay.app.navigation.Routes
import com.lespsan543.visionplay.app.ui.FavoritesScreen
import com.lespsan543.visionplay.app.ui.LogInScreen
import com.lespsan543.visionplay.app.ui.MoviesScreen
import com.lespsan543.visionplay.app.ui.RegisterScreen
import com.lespsan543.visionplay.app.ui.SeriesScreen
import com.lespsan543.visionplay.app.ui.ShowFavorite
import com.lespsan543.visionplay.app.ui.ShowMovie
import com.lespsan543.visionplay.app.ui.ShowSerie
import com.lespsan543.visionplay.ui.theme.VisionPlayTheme

/**
 * Clase principal que se encarga de mostrar la interfaz al usuario y guarda la configuración de navegación de la app
 *
 * @property moviesOrSeriesViewModel viewModel que maneja los datos de las pantallas MoviesScreen() y SeriesScreen()
 * @property logInOrRegisterViewModel viewModel que maneja los datos de las pantallas LogInScreen() y RegisterScreen()
 * @property favotitesViewModel viewModel que maneja los datos de la pantalla de FavoritesScreen()
 */
class MainActivity : ComponentActivity() {
    private val moviesOrSeriesViewModel : MoviesOrSeriesViewModel by viewModels()
    private val logInOrRegisterViewModel : LogInOrRegisterViewModel by viewModels()
    private val favotitesViewModel : FavotitesViewModel by viewModels()

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
                    NavHost(navController = navController, startDestination = Routes.LogInScreen.route) {
                        composable(Routes.LogInScreen.route) {
                            LogInScreen(navController, logInOrRegisterViewModel)
                        }
                        composable(Routes.RegisterScreen.route) {
                            RegisterScreen(navController, logInOrRegisterViewModel)
                        }
                        composable(Routes.MoviesScreen.route) {
                            MoviesScreen(navController, moviesOrSeriesViewModel)
                        }
                        composable(Routes.SeriesScreen.route) {
                            SeriesScreen(navController, moviesOrSeriesViewModel)
                        }
                        composable(Routes.FavoritesScreen.route) {
                            FavoritesScreen(navController, favotitesViewModel)
                        }
                        composable(Routes.ShowMovie.route) {
                            ShowMovie(navController, moviesOrSeriesViewModel)
                        }
                        composable(Routes.ShowSerie.route) {
                            ShowSerie(navController, moviesOrSeriesViewModel)
                        }
                        composable(Routes.ShowFavotite.route) {
                            ShowFavorite(navController, favotitesViewModel)
                        }
                    }
                }
            }
        }
    }
}