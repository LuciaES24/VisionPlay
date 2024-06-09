package com.lespsan543.visionplay.app.navigation

/**
 * Gestiona las diferentes rutas que componen la navegación de la aplicación
 *
 * @param route ruta a la que va a dirigirse
 */
sealed class Routes(val route:String) {
    object LogInScreen : Routes("LogInScreen")

    object RegisterScreen : Routes("RegisterScreen")

    object MoviesScreen : Routes("MoviesScreen")

    object SeriesScreen : Routes("SeriesScreen")

    object FavoritesScreen : Routes("FavoritesScreen")

    object SearchGenres : Routes("SearchGenres")

    object ShowByGenre : Routes("ShowByGenre")

    object SearchScreen : Routes("SearchScreen")

    object ShowMovie : Routes("ShowMovie")

    object CinemaScreen : Routes("CinemaScreen")
}