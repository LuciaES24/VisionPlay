package com.lespsan543.visionplay.app.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.lespsan543.visionplay.R
import com.lespsan543.visionplay.app.data.util.Constants
import com.lespsan543.visionplay.app.ui.states.MovieOrSerieState
import com.lespsan543.visionplay.app.ui.viewModel.CinemaViewModel

/**
 * Formato para mostrar una película
 *
 * @param height altura de la pantalla
 * @param width ancho de la pantalla
 * @param movieOrSerie película o serie que se va a mostrar
 * @param cinemaViewModel viewModel del que obtenemos la información
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CinemaMovie(
    height : Dp,
    width:Dp,
    cinemaViewModel: CinemaViewModel,
    movieOrSerie : MovieOrSerieState
){
    Row(modifier = Modifier
        .height(height * 0.35f)
        .fillMaxWidth()
        .padding(start = width * 0.05f, end = width * 0.05f, top = height*0.04f, bottom = height*0.04f)
        .combinedClickable(enabled = true,
            onClick = {
                cinemaViewModel.formatTitle(movieOrSerie.title)
                cinemaViewModel.showMovieTrailer()
            })) {
        AsyncImage(model = movieOrSerie.poster,
            contentDescription = null,
            modifier = Modifier
                .height(height * 0.4f)
                .width(width * 0.35f)
                .border(
                    border = BorderStroke(0.dp, color = Color.Transparent),
                    shape = RoundedCornerShape(5.dp)
                )
        )
        Spacer(modifier = Modifier.width(width * 0.05f))
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .width(width * 0.6f)
                .border(
                    border = BorderStroke(2.dp, color = Color(138, 0, 0)),
                    shape = RoundedCornerShape(5.dp)
                )
                .background(Color(25, 25, 25)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = movieOrSerie.title,
                fontFamily = Constants.FONT_FAMILY,
                textAlign = TextAlign.Justify,
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp)
            )
            Spacer(modifier = Modifier.height(width * 0.03f))
            Text(text = "Release date: ${movieOrSerie.date}",
                fontFamily = Constants.FONT_FAMILY,
                textAlign = TextAlign.Start,
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp)
            )
            Spacer(modifier = Modifier.height(width * 0.03f))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                for (i in 1..5){
                    val colorFilter = if (i <= cinemaViewModel.calculateVotes(movieOrSerie)){
                        Color.White
                    }else{
                        Color(40,40,40)
                    }
                    Image(
                        painter = painterResource(id = R.drawable.votes),
                        contentDescription = "Votes",
                        modifier = Modifier.width(width*0.08f),
                        colorFilter = ColorFilter.tint(color = colorFilter)
                    )
                }
            }
            Button(onClick = {cinemaViewModel.formatTitle(movieOrSerie.title)
                cinemaViewModel.showMovieTrailer()},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 2.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(138,0,0)),
            ) {
                Text(text = "Trailer",
                    fontFamily = Constants.FONT_FAMILY,
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp)
            }
        }
    }
}