package com.lespsan543.visionplay.app.ui

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleUp
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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
import com.lespsan543.visionplay.app.ui.components.CommentSection
import com.lespsan543.visionplay.app.ui.components.YoutubeVideo
import com.lespsan543.visionplay.cabecera.Cabecera
import com.lespsan543.visionplay.guardar.Guardar
import com.lespsan543.visionplay.menu.Menu
import com.lespsan543.visionplay.menu.Property1
import kotlinx.coroutines.launch

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
        moviesOrSeriesViewModel.fetchFavoritesFromDB()
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
                        containerColor = Color(138,0,0),
                        shape = RectangleShape) {
                        Text(text = "Películas", color = Color.White, fontSize = 16.sp, fontFamily = Constants.FONT_FAMILY)
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    FloatingActionButton(onClick = { navController.navigate(Routes.SeriesScreen.route) },
                        modifier = Modifier
                            .width(width.times(0.25f))
                            .height(height.times(0.05f)),
                        containerColor = Color(40,40,40),
                        shape = RectangleShape
                    ) {
                        Text(text = "Series", color = Color.White, fontSize = 16.sp, fontFamily = Constants.FONT_FAMILY)
                    }
                }
            }
        ) {
            //Aparece si la información ya ha sido cargada
            if (movieList.isNotEmpty()){
                //Miramos si la película ya está guardada en la base de datos
                moviesOrSeriesViewModel.findMovieInList(movieList[moviePosition].title)
                AsyncImage(model = movieList[moviePosition].poster,
                    contentDescription = "Poster película",
                    modifier = Modifier
                        .fillMaxSize()
                        .combinedClickable(enabled = true,
                            onDoubleClick = {
                                if(property == com.lespsan543.visionplay.guardar.Property1.Guardado){
                                    moviesOrSeriesViewModel.deleteMovieOrSerie()
                                }else{
                                    moviesOrSeriesViewModel.saveMovieOrSerie(movieList[moviePosition])
                                }
                            },
                            onClick = {
                                navController.navigate(Routes.ShowMovie.route)
                                moviesOrSeriesViewModel.formatTitle(movieList[moviePosition].title)
                            })
                        .offset { IntOffset(offsetX, 0) }
                        .draggable(
                            orientation = Orientation.Horizontal,
                            state = rememberDraggableState { delta ->
                                offsetX += delta.toInt()
                            },
                            onDragStopped = {
                                if (offsetX < 0) {
                                    moviesOrSeriesViewModel.newMovie()
                                } else if (offsetX > 0) {
                                    moviesOrSeriesViewModel.lastMovie()
                                }
                                offsetX = 0
                            }
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
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
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
    //Id del trailer a mostrar
    val trailerId by moviesOrSeriesViewModel.trailerId.collectAsState()
    //Lista de comentarios de la película
    val commentsList by moviesOrSeriesViewModel.commentsList.collectAsState()

    val sheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)

    val scope = rememberCoroutineScope()
    
    val commentText = moviesOrSeriesViewModel.commentText

    DisposableEffect(Unit){
        onDispose {
            moviesOrSeriesViewModel.resetTrailer()
        }
    }
    //Comprobamos si la película ya ha sido guardada
    moviesOrSeriesViewModel.findMovieInList(movieList[moviePosition].title)
    moviesOrSeriesViewModel.getMovieGenresToShow(movieList[moviePosition])
    moviesOrSeriesViewModel.fetchCommentsFromDB(movieList[moviePosition].title)
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val height = maxHeight
        val width = maxWidth
        BottomSheetScaffold(scaffoldState = scaffoldState,
            sheetPeekHeight = 0.dp,
            sheetContent = {
            //Contenido de la sección de comentarios
            Column(modifier = Modifier
                .fillMaxWidth()
                .height(height * 0.75f)
                .padding(bottom = height * 0.02f),
                horizontalAlignment = Alignment.CenterHorizontally)
            {
                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(
                            top = height * 0.02f,
                            bottom = height * 0.02f,
                            start = width * 0.1f
                        )
                        .fillMaxWidth())
                {
                    TextField(
                        value = commentText.value,
                        onValueChange = { moviesOrSeriesViewModel.writeComment(it) },
                        textStyle = TextStyle.Default.copy(fontFamily = Constants.FONT_FAMILY, fontSize = 18.sp),
                        placeholder = {
                            Text(text = "Make a comment...",
                            color = Color.White,
                            fontFamily = Constants.FONT_FAMILY)
                                },
                        colors = TextFieldDefaults.textFieldColors(
                            unfocusedIndicatorColor = Color.White,
                            focusedIndicatorColor = Color.White,
                            cursorColor = Color.White,
                            textColor = Color.White,
                            disabledTextColor = Color.White,
                            containerColor = Color(35,35,35)
                        ),
                        modifier = Modifier.width(width*0.7f)
                    )
                    IconButton(onClick = { moviesOrSeriesViewModel.saveComment(movieList[moviePosition].title, commentText.value)
                        moviesOrSeriesViewModel.fetchCommentsFromDB(movieList[moviePosition].title)
                        moviesOrSeriesViewModel.newComment()})
                    {
                        Icon(
                            Icons.Default.ArrowCircleUp,
                            contentDescription = null,
                            modifier = Modifier
                                .size(width*0.1f),
                            tint = Color.White
                        )
                    }
                }
                Divider(color = Color.White, thickness = 2.dp)
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(commentsList){
                        CommentSection(comment = it, width = width, height = height)
                        Spacer(modifier = Modifier.width(width * 0.02f))
                    }
                }
            }
        },
            sheetBackgroundColor = Color(40,40,40))
        {
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
                    Text(text = "Overview:",
                        fontFamily = Constants.FONT_FAMILY,
                        textAlign = TextAlign.Justify,
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(
                            start = width * 0.05f,
                            end = width * 0.05f
                        )
                    )
                    Spacer(modifier = Modifier.height(width * 0.03f))
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
                    Text(text = "Genres:",
                        fontFamily = Constants.FONT_FAMILY,
                        textAlign = TextAlign.Justify,
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(
                            start = width * 0.05f,
                            end = width * 0.05f
                        )
                    )
                    Spacer(modifier = Modifier.height(width * 0.03f))
                    Text(text = genres,
                        fontFamily = Constants.FONT_FAMILY,
                        textAlign = TextAlign.Justify,
                        color = Color.Black,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(
                            start = width * 0.05f,
                            end = width * 0.05f
                        )
                    )
                    Spacer(modifier = Modifier.height(width * 0.03f))
                    Text(text = "Trailer: ",
                        fontFamily = Constants.FONT_FAMILY,
                        textAlign = TextAlign.Justify,
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(
                            start = width * 0.05f,
                            end = width * 0.05f
                        )
                    )
                    Spacer(modifier = Modifier.height(width * 0.05f))
                    if (trailerId!=""){
                        YoutubeVideo(id = trailerId, lifecycleOwner = LocalLifecycleOwner.current, width = width, height = height)
                        Spacer(modifier = Modifier.height(width * 0.07f))
                    }
                    Spacer(modifier = Modifier.height(width * 0.05f))
                    Button(modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = width * 0.05f,
                            end = width * 0.05f
                        ),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(40,40,40)),
                        onClick = {
                        scope.launch {
                            if (sheetState.isCollapsed){
                                sheetState.expand()
                            }else{
                                sheetState.collapse()
                            }
                        }

                     }) {
                        Text(text = "Show comments",
                            color = Color.White,
                            fontFamily = Constants.FONT_FAMILY,
                            fontSize = 20.sp)
                    }
                    Spacer(modifier = Modifier.height(width * 0.05f))
                }
            }
        }
    }
}