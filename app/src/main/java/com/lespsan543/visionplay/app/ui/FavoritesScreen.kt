package com.lespsan543.visionplay.app.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.lespsan543.apppeliculas.peliculas.ui.viewModel.FavotitesViewModel
import com.lespsan543.visionplay.R
import com.lespsan543.visionplay.app.data.util.Constants.FONT_FAMILY
import com.lespsan543.visionplay.app.navigation.Routes
import com.lespsan543.visionplay.app.ui.states.MovieOrSerieState
import com.lespsan543.visionplay.cabecera.Cabecera
import com.lespsan543.visionplay.cabecera.Property1
import com.lespsan543.visionplay.menu.Menu

/**
 * Muestra la pantalla de favoritos, donde se encuentran todas las películas y series que ha añadido el usuario
 *
 * @param navController nos permite realizar la navegación entre pantallas
 * @param favoritesViewModel viewModel del que obtendremos los datos
 */
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FavoritesScreen(navController: NavHostController, favoritesViewModel: FavotitesViewModel) {
    //Lista de películas y series que el usuario ha añadido a favoritos
    val favoritesList by favoritesViewModel.favoritesList.collectAsState()

    LaunchedEffect(Unit){
        favoritesViewModel.fetchMoviesAndSeries()
    }
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                Cabecera(
                    modifier = Modifier
                        .height(maxHeight.times(0.08f)),
                    Property1.Perfil,
                    salir = { favoritesViewModel.signOut()
                              navController.navigate(Routes.LogInScreen.route)}
                )
            },
            bottomBar = {
                Menu(
                    modifier = Modifier
                        .height(maxHeight.times(0.08f)),
                    home3 = { navController.navigate(Routes.MoviesScreen.route) },
                    search3 = { navController.navigate(Routes.SearchScreen.route) },
                    fav3 = { navController.navigate(Routes.FavoritesScreen.route) },
                    property1 = com.lespsan543.visionplay.menu.Property1.Perfil
                )
            },
            ) {
            //Se muestra si hay algún elemento en la lista de favoritos
            if(favoritesList.isNotEmpty()){
                Spacer(modifier = Modifier.height(maxHeight.times(0.08f)))
                LazyVerticalGrid(
                    GridCells.Fixed(2),
                    contentPadding = PaddingValues(4.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(199, 199, 199))
                        .padding(top = maxHeight * 0.08f, bottom = maxHeight * 0.08f)
                ) {
                    items(favoritesList) {movieOrSerie ->
                        ShowMovieOrSerie(movieOrSerie = movieOrSerie,
                            maxHeigth = maxHeight,
                            navController = navController,
                            favoritesViewModel = favoritesViewModel)
                    }
                }
                Spacer(modifier = Modifier.height(maxHeight.times(0.08f)))
            }//Se muestra si el usuario aún no ha añadido nada a favoritos
            else{
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(199, 199, 199))
                        .padding(top = maxHeight * 0.08f, bottom = maxHeight * 0.08f)
                ) {
                    Text(text = "Aún no tienes nada en favoritos",
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        fontFamily = FONT_FAMILY
                    )
                }
            }
        }
    }
}

/**
 * Muestra una película o serie mostrando la imagen y su título
 *
 * @param movieOrSerie película o serie
 * @param maxHeigth altura de la pantalla
 * @param navController nos permite realizar la navegación entre pantallas
 */
@Composable
fun ShowMovieOrSerie(
    movieOrSerie: MovieOrSerieState,
    maxHeigth: Dp,
    navController: NavHostController,
    favoritesViewModel: FavotitesViewModel
){
    Box(modifier = Modifier
        .fillMaxWidth()
        .clickable { favoritesViewModel.changeSelectedMovieOrSerie(movieOrSerie)
                     navController.navigate(Routes.ShowFavotite.route) }
        .padding(4.dp)
        .height(maxHeigth * 0.47f)
        .background(Color.Transparent)){
        Column(modifier = Modifier
            .background(Color.Transparent)
            .fillMaxSize()) {
            AsyncImage(
                model = movieOrSerie.poster,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
            )
            Text(text = movieOrSerie.title,
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontFamily = FONT_FAMILY
            )
        }
    }
}


/**
 * Muestra la información de la serie sobre la que se pulse
 *
 * @param navController nos permite realizar la navegación entre pantallas
 * @param favoritesViewModel viewModel del que obtendremos los datos
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowFavorite(navController: NavHostController,
              favoritesViewModel:FavotitesViewModel
) {
    //Película o serie que vamos a mostrar
    val movieOrSerie by favoritesViewModel.selectedMovieOrSerie.collectAsState()
    //Lista de géneros de la serie
    val genres by favoritesViewModel.showGenres.collectAsState()

    favoritesViewModel.getGenresToShow(movieOrSerie)
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val height = maxHeight
        val width = maxWidth
        Scaffold(
            topBar = {
                Cabecera(
                    modifier = Modifier
                        .height(maxHeight.times(0.08f)),
                    property1 = Property1.Volver,
                    volver = { navController.navigate(Routes.FavoritesScreen.route)}
                )
            },
            bottomBar = { Menu(
                modifier = Modifier
                    .height(maxHeight.times(0.08f)),
                home3 = { navController.navigate(Routes.MoviesScreen.route) },
                search3 = { navController.navigate(Routes.SearchScreen.route) },
                fav3 = { navController.navigate(Routes.FavoritesScreen.route) },
                property1 = com.lespsan543.visionplay.menu.Property1.Perfil
                )
            },
            floatingActionButton = {
                IconButton(onClick = { favoritesViewModel.deleteMovieOrSerie(movieOrSerie.idDoc)
                                        navController.navigate(Routes.FavoritesScreen.route)}) {
                    Icon(imageVector = Icons.Filled.Delete , contentDescription = null, tint = Color.White,
                        modifier = Modifier.size(height*0.045f) )
                }
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
                    AsyncImage(model = movieOrSerie.poster,
                        contentDescription = "Poster",
                        modifier = Modifier
                            .height(height * 0.3f)
                    )
                    Spacer(modifier = Modifier.width(width * 0.03f))
                    Column {
                        Text(text = movieOrSerie.title,
                            fontFamily = FONT_FAMILY,
                            textAlign = TextAlign.Justify,
                            color = Color.Black,
                            fontSize = 25.sp
                        )
                        Spacer(modifier = Modifier.height(width * 0.03f))
                        Text(text = "Release date: ${movieOrSerie.date}",
                            fontFamily = FONT_FAMILY,
                            textAlign = TextAlign.Start,
                            color = Color.Black,
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.height(width * 0.03f))
                        Row {
                            for (i in 0..favoritesViewModel.calculateVotes(movieOrSerie)-1){
                                Image(
                                    painter = painterResource(id = R.drawable.votes),
                                    contentDescription = "Votes",
                                    modifier = Modifier.width(width*0.08f)
                                )
                            }
                        }
                    }
                }
                Text(text = movieOrSerie.overview,
                    fontFamily = FONT_FAMILY,
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
                    fontFamily = FONT_FAMILY,
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