package com.lespsan543.visionplay.cabecera

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.google.relay.compose.RelayContainer
import com.google.relay.compose.RelayContainerArrangement
import com.google.relay.compose.RelayContainerScope
import com.google.relay.compose.RelayText
import com.google.relay.compose.RelayVector
import com.google.relay.compose.tappable
import com.lespsan543.visionplay.R

// Design to select for Cabecera
enum class Property {
    Perfil,
    Genres,
    Cartelera,
    Volver,
    VisionPlay
}

/**
 * This composable was generated from the UI Package 'cabecera'.
 * Generated code; do not edit directly
 */
@Composable
fun Cabecera(
    modifier: Modifier = Modifier,
    propertyParam: Property = Property.Perfil,
    username: String = "",
    generoSeleccionado: String = "",
    salir: () -> Unit = {},
    volver: () -> Unit = {}
) {
    when (propertyParam) {
        Property.Perfil -> TopLevelPropertyPerfil(modifier = modifier) {
            Salir2PropertyPerfil()
            FavoritesPropertyPerfil(username = username)
            SalirPropertyPerfil(salir = salir)
        }
        Property.Genres -> TopLevelPropertyGenres(modifier = modifier) {
            GenresPropertyGenres()
        }
        Property.Cartelera -> TopLevelPropertyCartelera(modifier = modifier) {
            BillboardPropertyCartelera()
        }
        Property.Volver -> TopLevelPropertyVolver(modifier = modifier) {
            VolverPropertyVolver(volver = volver)
            GNeroPropertyVolver(generoSeleccionado = generoSeleccionado)
            Volver2PropertyVolver()
        }
        Property.VisionPlay -> TopLevelPropertyVisionPlay(modifier = modifier) {
            VisionPlayPropertyVisionPlay()
        }
    }
}

@Preview(widthDp = 358, heightDp = 56)
@Composable
private fun CabeceraPropertyPerfilPreview() {
    MaterialTheme {
        RelayContainer {
            Cabecera(
                username = "Favorites",
                salir = {},
                volver = {},
                generoSeleccionado = "Género",
                propertyParam = Property.Perfil,
                modifier = Modifier.rowWeight(1.0f).columnWeight(1.0f)
            )
        }
    }
}

@Preview(widthDp = 358, heightDp = 56)
@Composable
private fun CabeceraPropertyGenresPreview() {
    MaterialTheme {
        RelayContainer {
            Cabecera(
                username = "Favorites",
                salir = {},
                volver = {},
                generoSeleccionado = "Género",
                propertyParam = Property.Genres,
                modifier = Modifier.rowWeight(1.0f).columnWeight(1.0f)
            )
        }
    }
}

@Preview(widthDp = 358, heightDp = 56)
@Composable
private fun CabeceraPropertyCarteleraPreview() {
    MaterialTheme {
        RelayContainer {
            Cabecera(
                username = "Favorites",
                salir = {},
                volver = {},
                generoSeleccionado = "Género",
                propertyParam = Property.Cartelera,
                modifier = Modifier.rowWeight(1.0f).columnWeight(1.0f)
            )
        }
    }
}

@Preview(widthDp = 358, heightDp = 56)
@Composable
private fun CabeceraPropertyVolverPreview() {
    MaterialTheme {
        RelayContainer {
            Cabecera(
                username = "Favorites",
                salir = {},
                volver = {},
                generoSeleccionado = "Género",
                propertyParam = Property.Volver,
                modifier = Modifier.rowWeight(1.0f).columnWeight(1.0f)
            )
        }
    }
}

@Preview(widthDp = 358, heightDp = 56)
@Composable
private fun CabeceraPropertyVisionPlayPreview() {
    MaterialTheme {
        RelayContainer {
            Cabecera(
                username = "Favorites",
                salir = {},
                volver = {},
                generoSeleccionado = "Género",
                propertyParam = Property.VisionPlay,
                modifier = Modifier.rowWeight(1.0f).columnWeight(1.0f)
            )
        }
    }
}

@Composable
fun Salir2PropertyPerfil(modifier: Modifier = Modifier) {
    RelayVector(
        vector = painterResource(R.drawable.cabecera_salir2),
        modifier = modifier.requiredWidth(27.1875.dp).requiredHeight(28.0.dp)
    )
}

@Composable
fun FavoritesPropertyPerfil(
    username: String,
    modifier: Modifier = Modifier
) {
    RelayText(
        content = username,
        fontSize = 28.0.sp,
        fontFamily = kameron,
        color = Color(
            alpha = 255,
            red = 255,
            green = 255,
            blue = 255
        ),
        height = 1.18701171875.em,
        maxLines = -1,
        modifier = modifier.requiredWidth(130.0.dp).requiredHeight(28.0.dp).wrapContentHeight(
            align = Alignment.CenterVertically,
            unbounded = true
        )
    )
}

@Composable
fun SalirPropertyPerfil(
    salir: () -> Unit,
    modifier: Modifier = Modifier
) {
    RelayVector(
        vector = painterResource(R.drawable.cabecera_salir),
        modifier = modifier.tappable(onTap = salir).requiredWidth(27.1875.dp).requiredHeight(28.0.dp)
    )
}

@Composable
fun TopLevelPropertyPerfil(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        backgroundColor = Color(
            alpha = 255,
            red = 138,
            green = 0,
            blue = 0
        ),
        arrangement = RelayContainerArrangement.Row,
        padding = PaddingValues(
            start = 15.0.dp,
            top = 12.0.dp,
            end = 15.0.dp,
            bottom = 12.0.dp
        ),
        itemSpacing = 76.0,
        content = content,
        modifier = modifier.fillMaxWidth(1.0f).fillMaxHeight(1.0f)
    )
}

@Composable
fun GenresPropertyGenres(modifier: Modifier = Modifier) {
    RelayText(
        content = "Genres",
        fontSize = 28.0.sp,
        fontFamily = kameron,
        color = Color(
            alpha = 255,
            red = 255,
            green = 255,
            blue = 255
        ),
        height = 1.18701171875.em,
        maxLines = -1,
        modifier = modifier.requiredWidth(100.0.dp).requiredHeight(31.0.dp).wrapContentHeight(
            align = Alignment.Bottom,
            unbounded = true
        )
    )
}

@Composable
fun TopLevelPropertyGenres(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        backgroundColor = Color(
            alpha = 255,
            red = 138,
            green = 0,
            blue = 0
        ),
        arrangement = RelayContainerArrangement.Row,
        padding = PaddingValues(
            start = 136.0.dp,
            top = 12.0.dp,
            end = 136.0.dp,
            bottom = 12.0.dp
        ),
        itemSpacing = 10.0,
        content = content,
        modifier = modifier.fillMaxWidth(1.0f).fillMaxHeight(1.0f)
    )
}

@Composable
fun BillboardPropertyCartelera(modifier: Modifier = Modifier) {
    RelayText(
        content = "Billboard",
        fontSize = 28.0.sp,
        fontFamily = kameron,
        color = Color(
            alpha = 255,
            red = 255,
            green = 255,
            blue = 255
        ),
        height = 1.18701171875.em,
        maxLines = -1,
        modifier = modifier.requiredWidth(133.0.dp).requiredHeight(33.0.dp).wrapContentHeight(
            align = Alignment.CenterVertically,
            unbounded = true
        )
    )
}

@Composable
fun TopLevelPropertyCartelera(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        backgroundColor = Color(
            alpha = 255,
            red = 138,
            green = 0,
            blue = 0
        ),
        arrangement = RelayContainerArrangement.Row,
        padding = PaddingValues(
            start = 136.0.dp,
            top = 12.0.dp,
            end = 136.0.dp,
            bottom = 12.0.dp
        ),
        itemSpacing = 10.0,
        content = content,
        modifier = modifier.fillMaxWidth(1.0f).fillMaxHeight(1.0f)
    )
}

@Composable
fun VolverPropertyVolver(
    volver: () -> Unit,
    modifier: Modifier = Modifier
) {
    RelayVector(
        vector = painterResource(R.drawable.cabecera_volver),
        modifier = modifier.tappable(onTap = volver).requiredWidth(27.0.dp).requiredHeight(28.0.dp)
    )
}

@Composable
fun GNeroPropertyVolver(
    generoSeleccionado: String,
    modifier: Modifier = Modifier
) {
    RelayText(
        content = generoSeleccionado,
        fontSize = 28.0.sp,
        fontFamily = kameron,
        color = Color(
            alpha = 255,
            red = 255,
            green = 255,
            blue = 255
        ),
        height = 1.18701171875.em,
        maxLines = -1,
        modifier = modifier.requiredWidth(151.0.dp).requiredHeight(27.0.dp).wrapContentHeight(
            align = Alignment.CenterVertically,
            unbounded = true
        )
    )
}

@Composable
fun Volver2PropertyVolver(modifier: Modifier = Modifier) {
    RelayVector(
        vector = painterResource(R.drawable.cabecera_volver2),
        modifier = modifier.requiredWidth(27.0.dp).requiredHeight(28.0.dp)
    )
}

@Composable
fun TopLevelPropertyVolver(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        backgroundColor = Color(
            alpha = 255,
            red = 138,
            green = 0,
            blue = 0
        ),
        arrangement = RelayContainerArrangement.Row,
        padding = PaddingValues(
            start = 14.0.dp,
            top = 12.0.dp,
            end = 14.0.dp,
            bottom = 12.0.dp
        ),
        itemSpacing = 70.0,
        content = content,
        modifier = modifier.fillMaxWidth(1.0f).fillMaxHeight(1.0f)
    )
}

@Composable
fun VisionPlayPropertyVisionPlay(modifier: Modifier = Modifier) {
    RelayText(
        content = "VisionPlay",
        fontSize = 28.0.sp,
        fontFamily = kameron,
        color = Color(
            alpha = 255,
            red = 255,
            green = 255,
            blue = 255
        ),
        height = 1.18701171875.em,
        maxLines = -1,
        modifier = modifier.requiredWidth(157.0.dp).requiredHeight(25.0.dp).wrapContentHeight(
            align = Alignment.CenterVertically,
            unbounded = true
        )
    )
}

@Composable
fun TopLevelPropertyVisionPlay(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        backgroundColor = Color(
            alpha = 255,
            red = 138,
            green = 0,
            blue = 0
        ),
        arrangement = RelayContainerArrangement.Row,
        padding = PaddingValues(
            start = 15.0.dp,
            top = 12.0.dp,
            end = 15.0.dp,
            bottom = 12.0.dp
        ),
        itemSpacing = 76.0,
        content = content,
        modifier = modifier.fillMaxWidth(1.0f).fillMaxHeight(1.0f)
    )
}
