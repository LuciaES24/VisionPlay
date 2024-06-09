package com.lespsan543.visionplay.app.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.lespsan543.visionplay.app.data.util.Constants
import com.lespsan543.visionplay.app.navigation.Routes
import com.lespsan543.visionplay.app.ui.components.ShowResult
import com.lespsan543.visionplay.app.ui.viewModel.VisionPlayViewModel
import com.lespsan543.visionplay.cabecera.Cabecera
import com.lespsan543.visionplay.cabecera.Property
import com.lespsan543.visionplay.menu.Menu
import com.lespsan543.visionplay.menu.PropertyBottomBar

/**
 * Pantalla en la que se muestran los resultados de la búsqueda que ha realizado el usuario
 *
 * @param navController nos permite realizar la navegación entre pantallas
 * @param visionPlayViewModel viewModel del que obtendremos los datos
 */
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchScreen(navController : NavHostController, visionPlayViewModel: VisionPlayViewModel){
    //Lista de resultados de la búsqueda del usuario
    val searchList by visionPlayViewModel.searchList.collectAsState()
    //Búsqueda que realiza el usuario
    val userSearch = visionPlayViewModel.userSearch

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val width = maxWidth
        val height = maxHeight
        Scaffold(
            topBar = {
                Cabecera(
                    modifier = Modifier
                        .height(maxHeight.times(0.08f)),
                    Property.Volver,
                    volver = {
                        navController.navigate(Routes.MoviesScreen.route)
                        visionPlayViewModel.resetSearch()
                    }
                )
            },
            bottomBar = {
                Menu(modifier = Modifier.height(maxHeight.times(0.08f)),
                    propertyBottomBar = PropertyBottomBar.Inicio,
                    home = { navController.navigate(Routes.MoviesScreen.route) },
                    fav1 = { navController.navigate(Routes.FavoritesScreen.route) },
                    genres1 = { navController.navigate(Routes.SearchGenres.route) },
                    cine1 = { navController.navigate(Routes.CinemaScreen.route) })
            }
        ) {
            if (searchList.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = height * 0.07f)
                        .background(Color(40,40,40))
                ) {
                    OutlinedTextField(
                        value = userSearch,
                        singleLine = true,
                        placeholder = {
                            Text(
                                text = "Search",
                                color = Color.White,
                                fontFamily = Constants.FONT_FAMILY
                            )
                        },
                        onValueChange = { visionPlayViewModel.writeSearch(it) },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            disabledBorderColor = Color(138, 0, 0),
                            focusedBorderColor = Color(138, 0, 0),
                            unfocusedBorderColor = Color(138, 0, 0),
                            textColor = Color.White,
                            disabledTextColor = Color.White,
                            containerColor = Color(24, 24, 24)
                        ),
                        trailingIcon = {
                            IconButton(onClick = {
                                visionPlayViewModel.search()
                                navController.navigate(Routes.SearchScreen.route)
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Search,
                                    contentDescription = "Search",
                                    tint = Color.White
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RectangleShape
                    )
                    Text(
                        text = "Search results:",
                        fontFamily = Constants.FONT_FAMILY,
                        textAlign = TextAlign.Start,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(
                            top = height * 0.02f,
                            bottom = height * 0.02f,
                            start = width * 0.04f,
                            end = width * 0.04f
                        )
                    )
                    LazyVerticalGrid(
                        GridCells.Fixed(2),
                        contentPadding = PaddingValues(4.dp),
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(40, 40, 40))
                            .padding(bottom = height*0.1f)
                    ) {
                        items(searchList) { movieOrSerie ->
                            ShowResult(
                                maxHeigth = height,
                                navController = navController,
                                visionPlayViewModel = visionPlayViewModel,
                                movieOrSerie = movieOrSerie
                            )
                        }
                    }
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
