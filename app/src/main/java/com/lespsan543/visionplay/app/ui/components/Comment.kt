package com.lespsan543.visionplay.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.lespsan543.visionplay.R
import com.lespsan543.visionplay.app.data.model.CommentModel
import com.lespsan543.visionplay.app.data.util.Constants

/**
 * Componente que muestra un comentario
 *
 * @param comment comentario que se debe mostrar
 * @param width ancho al que se debe ajustar el componente
 * @param height alto al que se debe ajustar el componente
 */
@Composable
fun CommentSection(comment : CommentModel, width : Dp, height : Dp){
    Row(modifier = Modifier.padding(
        start = width * 0.1f,
        top = height * 0.02f,
        end = width * 0.1f),
        verticalAlignment = Alignment.CenterVertically)
    {
        Image(painter = painterResource(id = R.drawable.comment),
            contentDescription = null,
            modifier = Modifier
                .width(width*0.08f)
                .height(width*0.07f),
            contentScale = ContentScale.FillBounds)
        Spacer(modifier = Modifier.width(width * 0.02f))
        Column {
            Text(text = comment.user,
                textAlign = TextAlign.Justify,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontFamily = Constants.FONT_FAMILY,
                fontSize = 15.sp)
            Text(text = comment.comment,
                textAlign = TextAlign.Justify,
                color = Color.White,
                fontFamily = Constants.FONT_FAMILY,
                fontSize = 18.sp)
        }
    }
}