package com.lespsan543.visionplay.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
 * Muestra una película o serie mostrando la imagen, su tipo y título
 *
 * @param movieOrSerie película o serie
 * @param maxHeigth altura de la pantalla
 * @param navController nos permite realizar la navegación entre pantallas
 * @param visionPlayViewModel viewModel del que obtenemos la información
 */
@Composable
fun ShowResult(
    movieOrSerie: MovieOrSerieState,
    maxHeigth: Dp,
    navController: NavHostController,
    visionPlayViewModel : VisionPlayViewModel
){
    Box(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            visionPlayViewModel.addSelected(movieOrSerie)
            navController.navigate(Routes.ShowMovie.route)
            visionPlayViewModel.formatTitle(movieOrSerie.title)
        }
        .clip(shape = RoundedCornerShape(5.dp))
        .padding(10.dp)
        .height(maxHeigth * 0.41f)
        .background(Color.Transparent)){
        Column(modifier = Modifier
            .background(Color(25,25,25))
            .border(2.dp, Color(138,0,0))
            .fillMaxSize()) {
            AsyncImage(
                model = movieOrSerie.poster,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Text(text = movieOrSerie.type.uppercase(),
                fontSize = 18.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontFamily = Constants.FONT_FAMILY
            )
            Text(text = movieOrSerie.title,
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontFamily = Constants.FONT_FAMILY,
                maxLines = 1
            )
        }
    }
}