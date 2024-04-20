package com.lespsan543.visionplay.app.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.lespsan543.visionplay.R
import com.lespsan543.visionplay.app.data.util.Constants.FONT_FAMILY
import com.lespsan543.visionplay.app.navigation.Routes
import com.lespsan543.visionplay.app.ui.states.MovieOrSerieState
import com.lespsan543.visionplay.app.ui.viewModel.CinemaViewModel
import com.lespsan543.visionplay.cabecera.Cabecera
import com.lespsan543.visionplay.cabecera.Property1
import com.lespsan543.visionplay.menu.Menu

/**
 * Muestra la pantalla de la cartelera, en la que podemos ver las películas que se encuentran en el cine
 *
 * @param navController nos permite realizar la navegación entre pantallas
 * @param cinemaViewModel viewModel del que obtendremos los datos
 */
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CinemaScreen(navController: NavHostController, cinemaViewModel: CinemaViewModel) {
    //Lista de películas que se encuentran en el cine actualmente
    val cineList by cinemaViewModel.cinemaList.collectAsState()
    //Boolean que controla si se debe mostrar el trailer
    val showTrailer by cinemaViewModel.showTrailer.collectAsState()
    //Identificador del trailer que se debe mostrar
    val trailerId by cinemaViewModel.trailerId.collectAsState()

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val height = maxHeight
        val width = maxWidth
        Scaffold(
            topBar = {
                Cabecera(
                    modifier = Modifier
                        .height(maxHeight.times(0.08f)),
                    Property1.Cartelera
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
                    property1 = com.lespsan543.visionplay.menu.Property1.Cine
                )
            },
        ) {
            //Se muestra si hay algún elemento en la lista de favoritos
            if(cineList.isNotEmpty()){
                Spacer(modifier = Modifier.height(maxHeight.times(0.08f)))
                LazyColumn(modifier = Modifier
                    .fillMaxSize()
                    .background(Color(199, 199, 199))
                    .padding(top = maxHeight * 0.08f, bottom = maxHeight * 0.08f)
                ){
                    items(cineList){movie ->
                        MovieRow(height = height,
                            width = width,
                            movieOrSerie = movie,
                            cinemaViewModel = cinemaViewModel)
                    }
                }
                Spacer(modifier = Modifier.height(maxHeight.times(0.08f)))
            }//Se muestra si el usuario aún no ha añadido nada a favoritos
            else{
                //Aparece si aún no ha cargado la información de la API
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .height(height * 0.5f)
                            .width(width * 0.5f)
                    )
                }
            }

            //Se muestra el trailer si cuando el usuario pulsa sobre una película
            if(showTrailer){
                Dialog(onDismissRequest = { cinemaViewModel.showMovieTrailer()
                    cinemaViewModel.resetTrailer()}) {
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

/**
 * Formato para mostrar una película
 *
 * @param height altura de la pantalla
 * @param width ancho de la pantalla
 * @param movieOrSerie película o serie que se va a mostrar
 * @param cinemaViewModel viewModel del que obtenemos la información
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieRow(height: Dp,
             width : Dp,
             movieOrSerie:MovieOrSerieState,
             cinemaViewModel: CinemaViewModel){
    BoxWithConstraints {
        val widthCard = maxWidth
        Box(
            modifier = Modifier
                .height(height * 0.3f)
                .fillMaxWidth()
                .padding(bottom = height * 0.02f)
                .combinedClickable(enabled = true,
                    onClick = {
                        cinemaViewModel.formatTitle(movieOrSerie.title)
                        cinemaViewModel.showMovieTrailer()
                    })
                .background(Color(40, 40, 40))
        ) {
            Row {
                AsyncImage(model = movieOrSerie.poster,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(widthCard * 0.5f))
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center)
                {
                    Spacer(modifier = Modifier.height(height * 0.03f))
                    Text(text = movieOrSerie.title,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontFamily = FONT_FAMILY,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(height * 0.03f))
                    Text(text = movieOrSerie.date,
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontFamily = FONT_FAMILY,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(height * 0.03f))
                    Row {
                        for (i in 0..cinemaViewModel.calculateVotes(movieOrSerie)-1){
                            Image(
                                painter = painterResource(id = R.drawable.votes),
                                contentDescription = "Votes",
                                modifier = Modifier.width(width*0.08f),
                                colorFilter = ColorFilter.tint(Color.White)
                            )
                        }
                    }
                }
            }
        }
    }
}