package com.lespsan543.visionplay.app.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.lespsan543.visionplay.app.navigation.Routes
import com.lespsan543.visionplay.app.ui.components.CinemaMovie
import com.lespsan543.visionplay.app.ui.components.YoutubeVideo
import com.lespsan543.visionplay.app.ui.viewModel.VisionPlayViewModel
import com.lespsan543.visionplay.cabecera.Cabecera
import com.lespsan543.visionplay.cabecera.Property
import com.lespsan543.visionplay.menu.Menu
import com.lespsan543.visionplay.menu.PropertyBottomBar

/**
 * Muestra la pantalla de la cartelera, en la que podemos ver las películas que se encuentran en el cine
 *
 * @param navController nos permite realizar la navegación entre pantallas
 * @param visionPlayViewModel viewModel del que obtendremos los datos
 */
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CinemaScreen(navController: NavHostController, visionPlayViewModel: VisionPlayViewModel) {
    //Lista de películas que se encuentran en el cine actualmente
    val cineList by visionPlayViewModel.cinemaList.collectAsState()
    //Boolean que controla si se debe mostrar el trailer
    val showTrailer by visionPlayViewModel.showTrailer.collectAsState()
    //Identificador del trailer que se debe mostrar
    val trailerId by visionPlayViewModel.trailerId.collectAsState()

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val height = maxHeight
        val width = maxWidth
        Scaffold(
            topBar = {
                Cabecera(
                    modifier = Modifier
                        .height(maxHeight.times(0.08f)),
                    Property.Cartelera
                )
            },
            bottomBar = {
                Menu(
                    modifier = Modifier
                        .height(maxHeight.times(0.08f)),
                    home3 = { navController.navigate(Routes.MoviesScreen.route) },
                    genres3 = { navController.navigate(Routes.SearchGenres.route) },
                    fav3 = { navController.navigate(Routes.FavoritesScreen.route) },
                    cine3 = { navController.navigate(Routes.CinemaScreen.route) },
                    propertyBottomBar = PropertyBottomBar.Cine
                )
            },
        ) {
            //Se muestra si hay algún elemento en la lista de favoritos
            if(cineList.isNotEmpty()){
                Spacer(modifier = Modifier.height(maxHeight.times(0.08f)))
                LazyColumn(modifier = Modifier
                    .fillMaxSize()
                    .background(Color(40, 40, 40))
                    .padding(top = maxHeight * 0.08f, bottom = maxHeight * 0.08f)
                ){
                    items(cineList){movie ->
                        CinemaMovie(height = height,
                            width = width,
                            movieOrSerie = movie,
                            visionPlayViewModel = visionPlayViewModel)
                    }
                }
                Spacer(modifier = Modifier.height(maxHeight.times(0.08f)))
            }
            else{
                //Aparece si aún no ha cargado la información de la API
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .height(height * 0.5f)
                            .width(width * 0.5f),
                        color = Color(138,0,0)
                    )
                }
            }

            //Se muestra el trailer si cuando el usuario pulsa sobre una película
            if(showTrailer){
                Dialog(onDismissRequest = { visionPlayViewModel.showMovieTrailer()
                    visionPlayViewModel.resetTrailer()}) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center) {
                        if (trailerId!=""){
                            YoutubeVideo(id = trailerId, lifecycleOwner = LocalLifecycleOwner.current, width = width, height = height)
                        }
                    }
                }
            }
        }
    }
}