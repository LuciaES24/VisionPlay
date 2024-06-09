package com.lespsan543.visionplay.app.data.util

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.lespsan543.visionplay.R

/**
 * Archivo en el que se definen variables globales de toda la aplicación
 * como url de la api, apikey y colores para la Ui
 */
object Constants {
    //URL de la API
    const val BASE_URL = "https://api.themoviedb.org/3/"

    //URL obtener las imágenes de la API
    const val BASE_URL_IMG = "https://image.tmdb.org/t/p/w500"

    //Key de la API
    const val API_KEY = "cb955504b67f8ca87ce99f412e7b9efd"

    //Url API de Youtube
    const val YOUTUBE_URL = "https://www.googleapis.com/youtube/v3/"

    //Key de la API de Youtube
    const val YOUTUBE_KEY = "AIzaSyAUinDiS_SPFFI1Nu_zQByaQ9RJzJiljYk"

    //Fuente de texto que utilizamos en toda la aplicación
    val FONT_FAMILY = FontFamily(Font(R.font.kameron_font))
}