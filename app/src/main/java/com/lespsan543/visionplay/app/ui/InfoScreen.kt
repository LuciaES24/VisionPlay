package com.lespsan543.visionplay.app.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleUp
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.lespsan543.visionplay.R
import com.lespsan543.visionplay.app.data.util.Constants
import com.lespsan543.visionplay.app.ui.components.CommentSection
import com.lespsan543.visionplay.app.ui.components.SimilarMovieOrSerie
import com.lespsan543.visionplay.app.ui.components.YoutubeVideo
import com.lespsan543.visionplay.app.ui.viewModel.VisionPlayViewModel
import com.lespsan543.visionplay.cabecera.Cabecera
import com.lespsan543.visionplay.cabecera.Property
import com.lespsan543.visionplay.guardar.Guardar
import kotlinx.coroutines.launch

/**
 * Muestra la información de la película o serie que sobre la que se pulse
 *
 * @param navController nos permite realizar la navegación entre pantallas
 * @param visionPlayViewModel viewModel del que obtendremos los datos
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "RememberReturnType")
@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class,
    ExperimentalFoundationApi::class
)
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
    val similar = visionPlayViewModel.similarMoviesAndSeries.collectAsState()
    //Lista de plataformas en las que se encuentra la película o serie seleccionada
    val providerList by visionPlayViewModel.providerList.collectAsState()

    //Contexto en el que lanzar el enlace a la plataforma seleccionada
    val context = LocalContext.current

    //Variables para el manejo de la sección de comentarios
    val sheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)
    val scope = rememberCoroutineScope()

    DisposableEffect(Unit){
        onDispose {
            visionPlayViewModel.resetTrailer()
        }
    }
    //Buscamos el trailer de la película o serie
    visionPlayViewModel.formatTitle(movieOrSerie.title)
    //Comprobamos si la película o serie ya ha sido guardada
    visionPlayViewModel.findMovieOrSerieInList(movieOrSerie.title)
    //Buscamos los géneros de la película o serie
    visionPlayViewModel.getGenresToShow(movieOrSerie)
    //Buscamos los comentarios de la película o serie seleccionada
    visionPlayViewModel.fetchCommentsFromDB(movieOrSerie.idAPI.toString())
    //Buscamos las películas y series similares a la seleccionada
    visionPlayViewModel.findSimilarMoviesOrSeries(movieOrSerie)
    //Buscamos las plataformas en las que se encuentra la película o serie
    visionPlayViewModel.getWatchProvider(movieOrSerie)
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
                                    fontFamily = Constants.FONT_FAMILY
                                )
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                unfocusedIndicatorColor = Color.White,
                                focusedIndicatorColor = Color.White,
                                cursorColor = Color.White,
                                textColor = Color.White,
                                disabledTextColor = Color.White,
                                containerColor = Color(24,24,24)
                            ),
                            modifier = Modifier.width(width*0.7f)
                        )
                        IconButton(onClick = { visionPlayViewModel.saveComment(movieOrSerie.idAPI.toString(), commentText.value)
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
            sheetBackgroundColor = Color(40,40,40)
        )
        {
            Scaffold(
                topBar = {
                    Cabecera(
                        modifier = Modifier
                            .height(maxHeight.times(0.08f)),
                        propertyParam = Property.Volver,
                        volver = { navController.popBackStack()
                            visionPlayViewModel.changeSelectedMovieOrSerie()}
                    )
                },
                floatingActionButton = {
                    Guardar(
                        property1 = property,
                        guardar = { visionPlayViewModel.saveMovieOrSerieInFavorites(movieOrSerie) },
                        eliminar = { visionPlayViewModel.deleteMovieOrSerie() }
                    )
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(40, 40, 40))
                        .verticalScroll(rememberScrollState())
                        .padding(top = maxHeight * 0.08f, bottom = maxHeight * 0.04f)
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
                                .clip(shape = RoundedCornerShape(5.dp))
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
                    Spacer(modifier = Modifier.height(width * 0.05f))
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
                    }else{
                        Text(text = "There is no trailer",
                            fontFamily = Constants.FONT_FAMILY,
                            textAlign = TextAlign.Justify,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(
                                start = width * 0.05f,
                                end = width * 0.05f
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(width * 0.05f))
                    Text(text = "Platforms: ",
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
                    if (providerList.isNotEmpty()){
                        LazyRow (modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = width * 0.05f,
                                end = width * 0.05f
                            ))
                        {
                            items(providerList){ provider ->
                                AsyncImage(model = provider.logo,
                                    contentDescription = "Poster película",
                                    modifier = Modifier
                                        .size(height * 0.07f)
                                        .clip(shape = RoundedCornerShape(13.dp))
                                        .combinedClickable(enabled = true,
                                            onClick = {
                                                visionPlayViewModel.clickWatchProvider(provider, context)
                                            })
                                )
                                Spacer(modifier = Modifier.width(width * 0.02f))
                            }
                        }
                    }else{
                        Text(text = "Not found on any platform",
                            fontFamily = Constants.FONT_FAMILY,
                            textAlign = TextAlign.Justify,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(
                                start = width * 0.05f,
                                end = width * 0.05f
                            )
                        )
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
                    Spacer(modifier = Modifier.height(width * 0.01f))
                }
            }
        }
    }
}