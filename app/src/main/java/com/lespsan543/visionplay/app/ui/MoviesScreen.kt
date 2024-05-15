package com.lespsan543.visionplay.app.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.ColorFilter
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
import com.lespsan543.visionplay.app.ui.viewModel.VisionPlayViewModel
import com.lespsan543.visionplay.R
import com.lespsan543.visionplay.app.data.util.Constants
import com.lespsan543.visionplay.app.navigation.Routes
import com.lespsan543.visionplay.app.ui.components.CommentSection
import com.lespsan543.visionplay.app.ui.components.SimilarMovieOrSerie
import com.lespsan543.visionplay.app.ui.components.YoutubeVideo
import com.lespsan543.visionplay.cabecera.Cabecera
import com.lespsan543.visionplay.cabecera.Property
import com.lespsan543.visionplay.guardar.Guardar
import com.lespsan543.visionplay.menu.Menu
import com.lespsan543.visionplay.menu.PropertyBottomBar
import kotlinx.coroutines.launch

/**
 * Muestra la pantalla inicial donde irán apareciendo películas según vayamos pulsando, estas
 * se podrán añadir a favoritos y podremos navegar a otras pantallas
 *
 * @param navController nos permite realizar la navegación entre pantallas
 * @param visionPlayViewModel viewModel del que obtendremos los datos
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MoviesScreen(
    navController: NavHostController,
    visionPlayViewModel: VisionPlayViewModel
) {
    //Guarda la posición de la película que se muestra
    val moviePosition by visionPlayViewModel.moviePosition.collectAsState()
    //Lista de películas obtenida
    val movieList by visionPlayViewModel.movieList.collectAsState()
    //Propiedad del botón de guardado
    val property by visionPlayViewModel.propertyButton.collectAsState()
    //Guarda la búsqueda que va a realizar el usuario
    var userSearch = visionPlayViewModel.userSearch

    //Se encarga del desplazamiento de las películas al deslizar
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
                        shape = RoundedCornerShape(3.dp),
                        containerColor = Color(138,0,0)) {
                        Text(text = "Películas", color = Color.White, fontSize = 16.sp, fontFamily = Constants.FONT_FAMILY)
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    FloatingActionButton(onClick = { navController.navigate(Routes.SeriesScreen.route) },
                        modifier = Modifier
                            .width(width.times(0.25f))
                            .height(height.times(0.05f)),
                        containerColor = Color(40,40,40),
                        shape = RoundedCornerShape(3.dp)
                    ) {
                        Text(text = "Series", color = Color.White, fontSize = 16.sp, fontFamily = Constants.FONT_FAMILY)
                    }
                }
            }
        ) {
            //Aparece si la información ya ha sido cargada
            if (movieList.isNotEmpty()){
                //Miramos si la película ya está guardada en la base de datos
                visionPlayViewModel.findMovieInList(movieList[moviePosition].title)
                //OutlinedTextField(value = userSearch, onValueChange = { visionPlayViewModel.writeSearch(it) })
                AsyncImage(model = movieList[moviePosition].poster,
                    contentDescription = "Poster película",
                    modifier = Modifier
                        .fillMaxSize()
                        .combinedClickable(enabled = true,
                            onDoubleClick = {
                                if (property == com.lespsan543.visionplay.guardar.Property1.Guardado) {
                                    visionPlayViewModel.deleteMovieOrSerie()
                                } else {
                                    visionPlayViewModel.saveMovieOrSerie(movieList[moviePosition])
                                }
                            },
                            onClick = {
                                visionPlayViewModel.addSelected(movieList[moviePosition])
                                navController.navigate(Routes.ShowMovie.route)
                                visionPlayViewModel.formatTitle(movieList[moviePosition].title)
                                visionPlayViewModel.changeBottomBar(PropertyBottomBar.Inicio)
                            })
                        .offset { IntOffset(offsetX, 0) }
                        .draggable(
                            orientation = Orientation.Horizontal,
                            state = rememberDraggableState { delta ->
                                offsetX += delta.toInt()
                            },
                            onDragStopped = {
                                if (offsetX < 0) {
                                    visionPlayViewModel.newMovie()
                                } else if (offsetX > 0) {
                                    visionPlayViewModel.lastMovie()
                                }
                                offsetX = 0
                            }
                        )
                        .border(
                            border = BorderStroke(0.dp, color = Color.Transparent),
                            shape = CutCornerShape(3.dp)
                        )
                )
                Guardar(
                    modifier = Modifier
                        .padding(start = width*0.10f, top = height*0.85f),
                    property1 = property,
                    guardar = { visionPlayViewModel.saveMovieOrSerie(movieList[moviePosition]) },
                    eliminar = { visionPlayViewModel.deleteMovieOrSerie() }
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
 * @param visionPlayViewModel viewModel del que obtendremos los datos
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ShowMovie(navController: NavHostController,
              visionPlayViewModel: VisionPlayViewModel
) {
    //Película que ha sido seleccionada
    val movieOrSerie by visionPlayViewModel.selectedMovieOrSerie.collectAsState()
    //Propiedad del botón de guardado
    val property by visionPlayViewModel.propertyButton.collectAsState()
    //Lista de géneros de la película
    val genres by visionPlayViewModel.showGenres.collectAsState()
    //Id del trailer a mostrar
    val trailerId by visionPlayViewModel.trailerId.collectAsState()
    //Lista de comentarios de la película
    val commentsList by visionPlayViewModel.commentsList.collectAsState()
    //Comentario que introduce el usuario
    val commentText = visionPlayViewModel.commentText
    //Lista de películas similares
    val similar = visionPlayViewModel.similarMovies.collectAsState()
    //Propiedad del menú dependiendo de la pantalla en la que se encontraba anteriormente
    val propertyBottomBar = visionPlayViewModel.propertyBottomBar.collectAsState()

    //Variables para el manejo de la sección de comentarios
    val sheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)
    val scope = rememberCoroutineScope()

    DisposableEffect(Unit){
        onDispose {
            visionPlayViewModel.resetTrailer()
        }
    }
    //Comprobamos si la película ya ha sido guardada
    visionPlayViewModel.findMovieInList(movieOrSerie.title)
    visionPlayViewModel.getGenresToShow(movieOrSerie)
    visionPlayViewModel.fetchCommentsFromDB(movieOrSerie.title)
    visionPlayViewModel.findSimilarMovies(movieOrSerie)
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
                .border(width = 2.dp, color = Color(138, 0, 0)),
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
                        onValueChange = { visionPlayViewModel.writeComment(it) },
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
                    IconButton(onClick = { visionPlayViewModel.saveComment(movieOrSerie.title, commentText.value)
                        visionPlayViewModel.fetchCommentsFromDB(movieOrSerie.title)
                        visionPlayViewModel.newComment()})
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
                Divider(color = Color(138,0,0), thickness = 2.dp)
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(commentsList){
                        CommentSection(comment = it, width = width, height = height)
                        Spacer(modifier = Modifier.height(height * 0.01f))
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
                        propertyParam = com.lespsan543.visionplay.cabecera.Property.Volver,
                        volver = { navController.popBackStack()
                                   visionPlayViewModel.changeSelectedMovieOrSerie() }
                    )
                },
                bottomBar = { Menu(modifier = Modifier.height(maxHeight.times(0.08f)),
                    propertyBottomBar = propertyBottomBar.value,
                    home = { navController.navigate(Routes.MoviesScreen.route) },
                    fav1 = { navController.navigate(Routes.FavoritesScreen.route) },
                    genres1 = { navController.navigate(Routes.SearchGenres.route) },
                    cine1 = { navController.navigate(Routes.CinemaScreen.route) }) },
                floatingActionButton = {
                    Guardar(
                        property1 = property,
                        guardar = { visionPlayViewModel.saveMovieOrSerie(movieOrSerie) },
                        eliminar = { visionPlayViewModel.deleteMovieOrSerie() }
                    )
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(40, 40, 40))
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
                            contentDescription = "Poster película",
                            modifier = Modifier
                                .height(height * 0.3f)
                                .border(
                                    border = BorderStroke(0.dp, color = Color.Transparent),
                                    shape = RoundedCornerShape(3.dp)
                                )
                        )
                        Spacer(modifier = Modifier.width(width * 0.03f))
                        Column {
                            Text(text = movieOrSerie.title,
                                fontFamily = Constants.FONT_FAMILY,
                                textAlign = TextAlign.Justify,
                                fontSize = 25.sp
                            )
                            Spacer(modifier = Modifier.height(width * 0.03f))
                            Text(text = "Release date: ${movieOrSerie.date}",
                                fontFamily = Constants.FONT_FAMILY,
                                textAlign = TextAlign.Start,
                                fontSize = 18.sp
                            )
                            Spacer(modifier = Modifier.height(width * 0.03f))
                            Row {
                                for (i in 1..5){
                                    val colorFilter = if (i <= visionPlayViewModel.calculateVotes(movieOrSerie)){
                                        Color.White
                                    }else{
                                        Color(25,25,25)
                                    }
                                    Image(
                                        painter = painterResource(id = R.drawable.votes),
                                        contentDescription = "Votes",
                                        modifier = Modifier.width(width*0.08f),
                                        colorFilter = ColorFilter.tint(color = colorFilter)
                                    )
                                }
                            }
                        }
                    }
                    Text(text = "Overview:",
                        fontFamily = Constants.FONT_FAMILY,
                        textAlign = TextAlign.Justify,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(
                            start = width * 0.05f,
                            end = width * 0.05f
                        )
                    )
                    Spacer(modifier = Modifier.height(width * 0.03f))
                    Text(text = movieOrSerie.overview,
                        fontFamily = Constants.FONT_FAMILY,
                        textAlign = TextAlign.Justify,
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
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(138,0,0)),
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
                    Spacer(modifier = Modifier.height(width * 0.1f))
                    Text(text = "Similar: ",
                        fontFamily = Constants.FONT_FAMILY,
                        textAlign = TextAlign.Justify,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(
                            start = width * 0.05f,
                            end = width * 0.05f
                        )
                    )
                    Spacer(modifier = Modifier.height(width * 0.03f))
                    if (similar.value.isNotEmpty()){
                        LazyRow(modifier = Modifier.fillMaxSize()) {
                            items(similar.value){
                                SimilarMovieOrSerie(
                                    movieOrSerie = it,
                                    height = height,
                                    width = width,
                                    visionPlayViewModel = visionPlayViewModel,
                                    navController = navController)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(width * 0.05f))
                }
            }
        }
    }
}