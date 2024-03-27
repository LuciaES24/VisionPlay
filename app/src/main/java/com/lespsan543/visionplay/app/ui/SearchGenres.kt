package com.lespsan543.visionplay.app.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.lespsan543.visionplay.app.data.util.Constants
import com.lespsan543.visionplay.app.navigation.Routes
import com.lespsan543.visionplay.app.ui.viewModel.SearchGenresViewModel
import com.lespsan543.visionplay.cabecera.Cabecera
import com.lespsan543.visionplay.guardar.Guardar
import com.lespsan543.visionplay.menu.Menu
import com.lespsan543.visionplay.menu.Property1

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchGenres(navController: NavHostController, searchGenresViewModel: SearchGenresViewModel){
    val genresToShow = searchGenresViewModel.genresToShow

    LaunchedEffect(Unit){
        searchGenresViewModel.fetchMoviesInDB()
    }
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val height = maxHeight
        Scaffold(
            bottomBar = {
                Menu(
                    modifier = Modifier
                        .height(maxHeight.times(0.08f)),
                    home2 = { navController.navigate(Routes.MoviesScreen.route) },
                    genres2 = { navController.navigate(Routes.SearchGenres.route) },
                    fav2 = { navController.navigate(Routes.FavoritesScreen.route) },
                    cine2 = { navController.navigate(Routes.CinemaScreen.route) },
                    property1 = Property1.Generos
                )
            },
            topBar = {
                Cabecera(
                    modifier = Modifier
                        .height(maxHeight.times(0.08f)),
                    property1 = com.lespsan543.visionplay.cabecera.Property1.Genres
                )
            }
        ){
            Spacer(modifier = Modifier.height(maxHeight.times(0.08f)))
            LazyVerticalGrid(
                GridCells.Fixed(2),
                contentPadding = PaddingValues(4.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(199, 199, 199))
                    .padding(top = maxHeight * 0.08f, bottom = maxHeight * 0.08f)
            ) {
                items(genresToShow){ genre ->
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .clickable { searchGenresViewModel.getActualGenre(genre)
                        navController.navigate(Routes.ShowByGenre.route)}
                        .padding(6.dp)
                        .height(height * 0.4f)
                        .background(Color(85, 85, 85))){
                        Column(modifier = Modifier
                            .background(Color.Transparent)
                            .fillMaxSize(),
                            verticalArrangement = Arrangement.Center) {
                            Text(text = genre,
                                fontSize = 25.sp,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                color = Color.White,
                                fontFamily = Constants.FONT_FAMILY
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ShowMoviesAndSeriesByGenre(navController: NavHostController, searchGenresViewModel: SearchGenresViewModel){
    //Lista de películas y series encontradas a partir del género seleccionado
    val moviesAndSeriesList by searchGenresViewModel.moviesAndSeriesList.collectAsState()
    //Posición actual de la película o serie que se muestra
    val position by searchGenresViewModel.position.collectAsState()
    //Propiedad del botón de guardado
    val property by searchGenresViewModel.propertyButton.collectAsState()

    //Se encarga del desplazamiento de las películas o series al deslizar
    var offsetX by remember { mutableIntStateOf(0) }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val width = maxWidth
        val height = maxHeight
        Scaffold(
            bottomBar = { Menu(modifier = Modifier.height(maxHeight.times(0.08f)),
                property1 = Property1.Inicio,
                home2 = { navController.navigate(Routes.MoviesScreen.route) },
                fav2 = { navController.navigate(Routes.FavoritesScreen.route) },
                genres2 = { navController.navigate(Routes.SearchGenres.route) },
                cine2 = { navController.navigate(Routes.CinemaScreen.route) }) },
            topBar = {
                Cabecera(
                    modifier = Modifier
                        .height(maxHeight.times(0.08f)),
                    com.lespsan543.visionplay.cabecera.Property1.Volver,
                    volver = { navController.navigate(Routes.SearchGenres.route) }
                )
            }
        ) {
            //Aparece si la información de la API ya ha sido cargada
            if (moviesAndSeriesList.isNotEmpty()){
                //Miramos si la película ya está guardada en la base de datos
                searchGenresViewModel.findMovieInList(moviesAndSeriesList[position].title)
                AsyncImage(model = moviesAndSeriesList[position].poster,
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
                                    searchGenresViewModel.newMovieOrSerie()
                                } else if (offsetX > 0) {
                                    searchGenresViewModel.lastMovieOrSerie()
                                }
                                offsetX = 0
                            }
                        )
                )
                Guardar(
                    modifier = Modifier
                        .padding(start = width*0.10f, top = height*0.85f),
                    property1 = property,
                    guardar = { searchGenresViewModel.saveMovieOrSerie(moviesAndSeriesList[position]) },
                    eliminar = { searchGenresViewModel.deleteMovieOrSerie() }
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