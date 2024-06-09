package com.lespsan543.visionplay.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.lespsan543.visionplay.app.data.util.Constants
import com.lespsan543.visionplay.app.navigation.Routes
import com.lespsan543.visionplay.app.ui.states.MovieOrSerieState
import com.lespsan543.visionplay.app.ui.viewModel.VisionPlayViewModel

/**
 * Componente para mostrar una película o serie similar
 *
 * @param navController nos permite realizar la navegación entre pantallas
 * @param visionPlayViewModel viewModel del que obtendremos los datos
 * @param movieOrSerie película o serie que se va a mostrar
 * @param height altura a la que se tiene que ajustar el componente
 * @param width ancho al que se tiene que ajustar el componente
 */
@Composable
fun SimilarMovieOrSerie(
    navController:NavHostController,
    visionPlayViewModel: VisionPlayViewModel,
    movieOrSerie:MovieOrSerieState,
    height : Dp,
    width:Dp)
{
    Box(modifier = Modifier
        .width(width*0.3f)
        .clickable {
            visionPlayViewModel.addSelected(movieOrSerie)
            navController.navigate(Routes.ShowMovie.route)
            visionPlayViewModel.formatTitle(movieOrSerie.title)
            visionPlayViewModel.findSimilarMoviesOrSeries(movieOrSerie)
        }
        .padding(6.dp)
        .height(height * 0.27f)
        .background(Color.Transparent)){
        Column(modifier = Modifier
            .background(Color.Transparent)
        ) {
                AsyncImage(
                    model = movieOrSerie.poster,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(5.dp))
                )
                Text(text = movieOrSerie.title,
                    fontSize = 15.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    fontFamily = Constants.FONT_FAMILY,
                    maxLines = 1
                )
        }
    }
}
