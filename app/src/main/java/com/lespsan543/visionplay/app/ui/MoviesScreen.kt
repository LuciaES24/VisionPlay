package com.lespsan543.visionplay.app.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.lespsan543.visionplay.app.ui.viewModel.MoviesOrSeriesViewModel
import com.lespsan543.visionplay.R
import com.lespsan543.visionplay.app.data.util.Constants
import com.lespsan543.visionplay.app.navigation.Routes
import com.lespsan543.visionplay.cabecera.Cabecera
import com.lespsan543.visionplay.guardar.Guardar
import com.lespsan543.visionplay.menu.Menu
import com.lespsan543.visionplay.menu.Property1

/**
 * Muestra la pantalla inicial donde irán apareciendo películas según vayamos pulsando, estas
 * se podrán añadir a favoritos y podremos navegar a otras pantallas
 *
 * @param navController nos permite realizar la navegación entre pantallas
 * @param moviesOrSeriesViewModel viewModel del que obtendremos los datos
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MoviesScreen(
    navController: NavHostController,
    moviesOrSeriesViewModel: MoviesOrSeriesViewModel
) {
    //Guarda la posición de la película que se muestra
    val moviePosition by moviesOrSeriesViewModel.moviePosition.collectAsState()
    //Lista de películas obtenida
    val movieList by moviesOrSeriesViewModel.movieList.collectAsState()
    //Propiedad del botón de guardado
    val property by moviesOrSeriesViewModel.propertyButton.collectAsState()

    //Se encarga del desplazamiento de las películas al deslizar
    var offsetX by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit){
        moviesOrSeriesViewModel.fetchMoviesInDB()
    }
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val width = maxWidth
        val height = maxHeight
        Scaffold(
            bottomBar = { Menu(modifier = Modifier.height(maxHeight.times(0.08f)),
                property1 = Property1.Inicio,
                home = { navController.navigate(Routes.MoviesScreen.route) },
                fav1 = { navController.navigate(Routes.FavoritesScreen.route) },
                genres1 = { navController.navigate(Routes.SearchGenres.route) },
                cine1 = { navController.navigate(Routes.CinemaScreen.route) }) },
            floatingActionButton = {
                Row {
                    FloatingActionButton(onClick = { navController.navigate(Routes.MoviesScreen.route) },
                        modifier = Modifier
                            .width(width.times(0.25f))
                            .height(height.times(0.05f)),
                        containerColor = Color(40,40,40),
                        shape = RectangleShape) {
                        Text(text = "Películas", color = Color.White, fontSize = 16.sp, fontFamily = Constants.FONT_FAMILY)
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    FloatingActionButton(onClick = { navController.navigate(Routes.SeriesScreen.route) },
                        modifier = Modifier
                            .width(width.times(0.25f))
                            .height(height.times(0.05f)),
                        containerColor = Color(85,85,85),
                        shape = RectangleShape
                    ) {
                        Text(text = "Series", color = Color.White, fontSize = 16.sp, fontFamily = Constants.FONT_FAMILY)
                    }
                }
            }
        ) {
            //Aparece si la información de la API ya ha sido cargada
            if (movieList.isNotEmpty()){
                //Miramos si la película ya está guardada en la base de datos
                moviesOrSeriesViewModel.findMovieInList(movieList[moviePosition].title)
                AsyncImage(model = movieList[moviePosition].poster,
                    contentDescription = "Poster película",
                    modifier = Modifier
                        .height(height)
                        .width(width)
                        .combinedClickable(enabled = true,
                            onDoubleClick = { navController.navigate(Routes.ShowMovie.route) },
                            onClick = {})
                        .offset { IntOffset(offsetX, 0) }
                        .draggable(
                            orientation = Orientation.Horizontal,
                            state = rememberDraggableState { delta ->
                                offsetX += delta.toInt()
                                Log.d("pointer", offsetX.toString())
                            },
                            onDragStopped = {
                                if (offsetX < 0) {
                                    moviesOrSeriesViewModel.newMovie()
                                } else if (offsetX > 0) {
                                    moviesOrSeriesViewModel.lastMovie()
                                }
                                offsetX = 0 }
                        )
                )
                Guardar(
                    modifier = Modifier
                        .padding(start = width*0.10f, top = height*0.85f),
                    property1 = property,
                    guardar = { moviesOrSeriesViewModel.saveMovieOrSerie(movieList[moviePosition]) },
                    eliminar = { moviesOrSeriesViewModel.deleteMovieOrSerie() }
                )
            }else{
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
        }
    }
}

/**
 * Muestra la información de la película o serie que sobre la que se pulse
 *
 * @param navController nos permite realizar la navegación entre pantallas
 * @param moviesOrSeriesViewModel viewModel del que obtendremos los datos
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowMovie(navController: NavHostController,
    moviesOrSeriesViewModel: MoviesOrSeriesViewModel
) {
    //Guarda la posición de la película que se muestra
    val moviePosition by moviesOrSeriesViewModel.moviePosition.collectAsState()
    //Lista de películas obtenida
    val movieList by moviesOrSeriesViewModel.movieList.collectAsState()
    //Propiedad del botón de guardado
    val property by moviesOrSeriesViewModel.propertyButton.collectAsState()
    //Lista de géneros de la película
    val genres by moviesOrSeriesViewModel.showGenres.collectAsState()

    //Comprobamos si la película ya ha sido guardada
    moviesOrSeriesViewModel.findMovieInList(movieList[moviePosition].title)
    moviesOrSeriesViewModel.getMovieGenresToShow(movieList[moviePosition])
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val height = maxHeight
        val width = maxWidth
        Scaffold(
            topBar = {
                Cabecera(
                    modifier = Modifier
                        .height(maxHeight.times(0.08f)),
                    property1 = com.lespsan543.visionplay.cabecera.Property1.Volver,
                    volver = { navController.navigate(Routes.MoviesScreen.route)}
                )
            },
            bottomBar = { Menu(modifier = Modifier.height(maxHeight.times(0.08f)),
                property1 = Property1.Inicio,
                home = { navController.navigate(Routes.MoviesScreen.route) },
                fav1 = { navController.navigate(Routes.FavoritesScreen.route) },
                genres1 = { navController.navigate(Routes.SearchGenres.route) },
                cine1 = { navController.navigate(Routes.CinemaScreen.route) }) },
            floatingActionButton = {
                Guardar(
                    property1 = property,
                    guardar = { moviesOrSeriesViewModel.saveMovieOrSerie(movieList[moviePosition]) },
                    eliminar = { moviesOrSeriesViewModel.deleteMovieOrSerie() }
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(199, 199, 199))
                    .verticalScroll(rememberScrollState())
                    .padding(top = maxHeight * 0.08f, bottom = maxHeight * 0.08f)
            ) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = width * 0.04f,
                        end = width * 0.04f,
                        top = height * 0.03f,
                        bottom = height * 0.03f
                    )
                ) {
                    AsyncImage(model = movieList[moviePosition].poster,
                        contentDescription = "Poster película",
                        modifier = Modifier
                            .height(height * 0.3f)
                    )
                    Spacer(modifier = Modifier.width(width * 0.03f))
                    Column {
                        Text(text = movieList[moviePosition].title,
                            fontFamily = Constants.FONT_FAMILY,
                            textAlign = TextAlign.Justify,
                            color = Color.Black,
                            fontSize = 25.sp
                        )
                        Spacer(modifier = Modifier.height(width * 0.03f))
                        Text(text = "Release date: ${movieList[moviePosition].date}",
                            fontFamily = Constants.FONT_FAMILY,
                            textAlign = TextAlign.Start,
                            color = Color.Black,
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.height(width * 0.03f))
                        Row {
                            for (i in 0..moviesOrSeriesViewModel.calculateVotes(movieList[moviePosition])-1){
                                Image(
                                    painter = painterResource(id = R.drawable.votes),
                                    contentDescription = "Votes",
                                    modifier = Modifier.width(width*0.08f)
                                )
                            }
                        }
                    }
                }
                Text(text = movieList[moviePosition].overview,
                    fontFamily = Constants.FONT_FAMILY,
                    textAlign = TextAlign.Justify,
                    color = Color.Black,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(
                        start = width * 0.05f,
                        end = width * 0.05f
                    )
                )
                Spacer(modifier = Modifier.height(width * 0.05f))
                Text(text = "Genres: \n$genres",
                    fontFamily = Constants.FONT_FAMILY,
                    textAlign = TextAlign.Justify,
                    color = Color.Black,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(
                        start = width * 0.05f,
                        end = width * 0.05f
                    )
                )
            }
        }
    }
}