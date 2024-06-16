package com.lespsan543.visionplay.app.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.lespsan543.visionplay.app.ui.viewModel.VisionPlayViewModel
import com.lespsan543.visionplay.app.data.util.Constants
import com.lespsan543.visionplay.app.navigation.Routes
import com.lespsan543.visionplay.cabecera.Cabecera
import com.lespsan543.visionplay.cabecera.Property
import com.lespsan543.visionplay.guardar.Guardar
import com.lespsan543.visionplay.menu.Menu
import com.lespsan543.visionplay.menu.PropertyBottomBar

/**
 * Muestra la pantalla inicial donde irán apareciendo series según vayamos pulsando, estas
 * se podrán añadir a favoritos y podremos navegar a otras pantallas
 *
 * @param navController nos permite realizar la navegación entre pantallas
 * @param visionPlayViewModel viewModel del que obtendremos los datos
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SeriesScreen(
    navController: NavHostController,
    visionPlayViewModel: VisionPlayViewModel
) {
    //Guarda la posición de la película que se muestra
    val seriePosition by visionPlayViewModel.seriePosition.collectAsState()
    //Lista de películas obtenida
    val serieList by visionPlayViewModel.serieList.collectAsState()
    //Propiedad del botón de guardado
    val property by visionPlayViewModel.propertyButton.collectAsState()
    //Guarda la búsqueda que va a realizar el usuario
    val userSearch = visionPlayViewModel.userSearch

    //Se encarga del desplazamiento de las series al deslizar
    var offsetX by remember { mutableIntStateOf(0) }


    LaunchedEffect(Unit){
        visionPlayViewModel.fetchFavoritesFromDB()
    }
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val width = maxWidth
        val height = maxHeight
        Scaffold(
            topBar = {
                Cabecera(
                    modifier = Modifier
                        .height(maxHeight.times(0.08f)),
                    Property.VisionPlay
                )
            },
            bottomBar = { Menu(modifier = Modifier.height(maxHeight.times(0.08f)),
                propertyBottomBar = PropertyBottomBar.Inicio,
                home = { navController.navigate(Routes.SeriesScreen.route) },
                fav1 = { navController.navigate(Routes.FavoritesScreen.route) },
                genres1 = { navController.navigate(Routes.SearchGenres.route) },
                cine1 = { navController.navigate(Routes.CinemaScreen.route) }) },
            floatingActionButton = {
                Row {
                    Guardar(
                        modifier = Modifier
                            .padding(end = width*0.3f),
                        property1 = property,
                        guardar = { visionPlayViewModel.saveMovieOrSerieInFavorites(serieList[seriePosition]) },
                        eliminar = { visionPlayViewModel.deleteMovieOrSerie() }
                    )
                    FloatingActionButton(onClick = { navController.navigate(Routes.MoviesScreen.route) },
                        modifier = Modifier
                            .width(width.times(0.25f))
                            .height(height.times(0.05f)),
                        containerColor = Color(40,40,40),
                        shape = RoundedCornerShape(3.dp))
                    {
                        Text(text = "Movies", color = Color.White, fontSize = 16.sp, fontFamily = Constants.FONT_FAMILY)
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    FloatingActionButton(onClick = { navController.navigate(Routes.SeriesScreen.route) },
                        modifier = Modifier
                            .width(width.times(0.25f))
                            .height(height.times(0.05f)),
                        containerColor = Color(138,0,0) ,
                        shape = RoundedCornerShape(3.dp)
                    ) {
                        Text(text = "Series", color = Color.White, fontSize = 16.sp, fontFamily = Constants.FONT_FAMILY)
                    }
                }
            }
        ) {
            //Aparece si la información de la API ya ha sido cargada
            if (serieList.isNotEmpty()){
                //Miramos si la película ya está guardada en la base de datos
                visionPlayViewModel.findMovieOrSerieInList(serieList[seriePosition].title)
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(top = height * 0.07f)
                ) {
                    OutlinedTextField(
                        value = userSearch,
                        placeholder = { Text(text = "Search", color = Color.White, fontFamily = Constants.FONT_FAMILY)},
                        onValueChange = { visionPlayViewModel.writeSearch(it) },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            disabledBorderColor = Color(138,0,0),
                            focusedBorderColor = Color(138,0,0),
                            unfocusedBorderColor = Color(138,0,0),
                            textColor = Color.White,
                            disabledTextColor = Color.White,
                            containerColor = Color(24,24,24)
                        ),
                        trailingIcon = {
                            IconButton(onClick = {
                                visionPlayViewModel.search()
                                navController.navigate(Routes.SearchScreen.route)
                            }) {
                                Icon(imageVector = Icons.Filled.Search, contentDescription = "Search", tint = Color.White)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RectangleShape
                    )
                    AsyncImage(model = serieList[seriePosition].poster,
                        contentDescription = "Poster serie",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = height*0.08f)
                            .combinedClickable(enabled = true,
                                onDoubleClick = {
                                    if(property == com.lespsan543.visionplay.guardar.Property1.Guardado){
                                        visionPlayViewModel.findMovieOrSerieInList(serieList[seriePosition].title)
                                        visionPlayViewModel.deleteMovieOrSerie()
                                    }else{
                                        visionPlayViewModel.saveMovieOrSerieInFavorites(serieList[seriePosition])
                                    }
                                },
                                onClick = {
                                    visionPlayViewModel.addSelected(serieList[seriePosition])
                                    navController.navigate(Routes.ShowMovie.route)
                                })
                            .offset { IntOffset(offsetX, 0) }
                            .draggable(
                                orientation = Orientation.Horizontal,
                                state = rememberDraggableState { delta ->
                                    offsetX += delta.toInt()
                                },
                                onDragStopped = {
                                    if (offsetX < 0) {
                                        visionPlayViewModel.newSerie()
                                    } else if (offsetX > 0) {
                                        visionPlayViewModel.lastSerie()
                                    }
                                    offsetX = 0 }
                            )
                    )
                }
            }else{
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
        }
    }
}