package com.lespsan543.visionplay.app.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.lespsan543.visionplay.app.data.util.Constants.FONT_FAMILY
import com.lespsan543.visionplay.app.navigation.Routes
import com.lespsan543.visionplay.app.ui.states.MovieOrSerieState
import com.lespsan543.visionplay.app.ui.viewModel.VisionPlayViewModel
import com.lespsan543.visionplay.cabecera.Cabecera
import com.lespsan543.visionplay.cabecera.Property
import com.lespsan543.visionplay.menu.Menu
import com.lespsan543.visionplay.menu.PropertyBottomBar

/**
 * Muestra la pantalla de favoritos, donde se encuentran todas las películas y series que ha añadido el usuario
 *
 * @param navController nos permite realizar la navegación entre pantallas
 * @param visionPlayViewModel viewModel del que obtendremos los datos
 */
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FavoritesScreen(navController: NavHostController, visionPlayViewModel: VisionPlayViewModel) {
    //Lista de películas y series que el usuario ha añadido a favoritos
    val favoritesList by visionPlayViewModel.favoritesInDB.collectAsState()
    //Nombre del usuario
    val username by visionPlayViewModel.userName.collectAsState()
    //Indica si se está reiniciando la base de datos
    val loadingDB = visionPlayViewModel.loadingDB

    LaunchedEffect(Unit){
        visionPlayViewModel.fetchFavoritesFromDB()
        visionPlayViewModel.findUserInDB()
    }
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val height = maxHeight
        val width = maxWidth
        Scaffold(
            topBar = {
                Cabecera(
                    modifier = Modifier
                        .height(maxHeight.times(0.08f)),
                    Property.Perfil,
                    salir = { visionPlayViewModel.signOut()
                              navController.navigate(Routes.LogInScreen.route)
                              visionPlayViewModel.resetLogInOrRegister()},
                    username = username
                )
            },
            bottomBar = {
                Menu(
                    modifier = Modifier
                        .height(maxHeight.times(0.08f)),
                    home4 = { navController.navigate(Routes.MoviesScreen.route) },
                    genres4 = { navController.navigate(Routes.SearchGenres.route) },
                    fav4 = { navController.navigate(Routes.FavoritesScreen.route) },
                    cine4 = { navController.navigate(Routes.CinemaScreen.route) },
                    propertyBottomBar = PropertyBottomBar.Favoritos
                )
            },
            floatingActionButton = {
                if (visionPlayViewModel.isAdmin()){
                    IconButton(onClick = { visionPlayViewModel.showLoading()
                                           visionPlayViewModel.restartDB()}
                    ){
                        Icon(imageVector = Icons.Filled.CloudDownload , contentDescription = null, tint = Color(138,0,0),
                            modifier = Modifier.size(maxHeight*0.045f) )
                    }
                }
            }
            ) {
            //Se muestra si se está actualizando la base de datos
            if (loadingDB){
                Dialog(onDismissRequest = {  }) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .height(height * 0.5f)
                                .width(width * 0.5f),
                            color = Color(138,0,0)
                        )
                    }
                }
            }
            //Se muestra si hay algún elemento en la lista de favoritos
            if(favoritesList.isNotEmpty()){
                Spacer(modifier = Modifier.height(maxHeight.times(0.08f)))
                LazyVerticalGrid(
                    GridCells.Fixed(2),
                    contentPadding = PaddingValues(4.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(40, 40, 40))
                        .padding(top = maxHeight * 0.08f, bottom = maxHeight * 0.08f)
                ) {
                    items(favoritesList) {movieOrSerie ->
                        ShowMovieOrSerie(movieOrSerie = movieOrSerie,
                            maxHeigth = maxHeight,
                            navController = navController,
                            visionPlayViewModel = visionPlayViewModel)
                    }
                }
            }//Se muestra si el usuario aún no ha añadido nada a favoritos
            else{
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(40, 40, 40))
                        .padding(top = maxHeight * 0.08f, bottom = maxHeight * 0.08f)
                ) {
                    Text(text = "There is nothing in favorites yet",
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
 * @param visionPlayViewModel viewModel del que obtenemos la información
 */
@Composable
fun ShowMovieOrSerie(
    movieOrSerie: MovieOrSerieState,
    maxHeigth: Dp,
    navController: NavHostController,
    visionPlayViewModel : VisionPlayViewModel
){
    Box(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            visionPlayViewModel.changeFavoriteSelected(movieOrSerie)
            visionPlayViewModel.addSelected(movieOrSerie)
            navController.navigate(Routes.ShowMovie.route)
            visionPlayViewModel.formatTitle(movieOrSerie.title)
        }
        .padding(8.dp)
        .height(maxHeigth * 0.4f)
        .background(Color.Transparent)){
        Column(modifier = Modifier
            .background(Color.Transparent)
            .fillMaxSize()) {
            AsyncImage(
                model = movieOrSerie.poster,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(5.dp))
            )
            Text(text = movieOrSerie.title,
                fontSize = 18.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontFamily = FONT_FAMILY,
                maxLines = 1
            )
        }
    }
}