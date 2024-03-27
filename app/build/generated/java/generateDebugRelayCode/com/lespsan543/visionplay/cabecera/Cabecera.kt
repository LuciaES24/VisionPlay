package com.lespsan543.visionplay.cabecera

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.google.relay.compose.MainAxisAlignment
import com.google.relay.compose.RelayContainer
import com.google.relay.compose.RelayContainerArrangement
import com.google.relay.compose.RelayContainerScope
import com.google.relay.compose.RelayText
import com.google.relay.compose.RelayVector
import com.google.relay.compose.tappable
import com.lespsan543.visionplay.R

// Design to select for Cabecera
enum class Property1 {
    Perfil,
    BuscarPeli,
    Cartelera,
    Volver
}

/**
 * This composable was generated from the UI Package 'cabecera'.
 * Generated code; do not edit directly
 */
@Composable
fun Cabecera(
    modifier: Modifier = Modifier,
    property1: Property1 = Property1.Perfil,
    salir: () -> Unit = {},
    volver: () -> Unit = {}
) {
    when (property1) {
        Property1.Perfil -> TopLevelProperty1Perfil(modifier = modifier) {
            SalirProperty1Perfil(salir = salir)
            FavoritesProperty1Perfil()
            Salir1Property1Perfil(salir = salir)
        }
        Property1.BuscarPeli -> TopLevelProperty1BuscarPeli(modifier = modifier) {
            GenresProperty1BuscarPeli()
        }
        Property1.Cartelera -> TopLevelProperty1Cartelera(modifier = modifier) {
            BillboardProperty1Cartelera()
        }
        Property1.Volver -> TopLevelProperty1Volver(modifier = modifier) {
            VolverProperty1Volver(volver = volver)
        }
    }
}

@Preview(widthDp = 358, heightDp = 56)
@Composable
private fun CabeceraProperty1PerfilPreview() {
    MaterialTheme {
        RelayContainer {
            Cabecera(
                salir = {},
                volver = {},
                property1 = Property1.Perfil,
                modifier = Modifier.rowWeight(1.0f).columnWeight(1.0f)
            )
        }
    }
}

@Preview(widthDp = 358, heightDp = 56)
@Composable
private fun CabeceraProperty1BuscarPeliPreview() {
    MaterialTheme {
        RelayContainer {
            Cabecera(
                salir = {},
                volver = {},
                property1 = Property1.BuscarPeli,
                modifier = Modifier.rowWeight(1.0f).columnWeight(1.0f)
            )
        }
    }
}

@Preview(widthDp = 358, heightDp = 56)
@Composable
private fun CabeceraProperty1CarteleraPreview() {
    MaterialTheme {
        RelayContainer {
            Cabecera(
                salir = {},
                volver = {},
                property1 = Property1.Cartelera,
                modifier = Modifier.rowWeight(1.0f).columnWeight(1.0f)
            )
        }
    }
}

@Preview(widthDp = 358, heightDp = 56)
@Composable
private fun CabeceraProperty1VolverPreview() {
    MaterialTheme {
        RelayContainer {
            Cabecera(
                salir = {},
                volver = {},
                property1 = Property1.Volver,
                modifier = Modifier.rowWeight(1.0f).columnWeight(1.0f)
            )
        }
    }
}

@Composable
fun SalirProperty1Perfil(
    salir: () -> Unit,
    modifier: Modifier = Modifier
) {
    RelayVector(
        vector = painterResource(R.drawable.cabecera_salir),
        modifier = modifier.tappable(onTap = salir).requiredWidth(27.1875.dp).requiredHeight(28.0.dp)
    )
}

@Composable
fun FavoritesProperty1Perfil(modifier: Modifier = Modifier) {
    RelayText(
        content = "Favorites",
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
        modifier = modifier.requiredWidth(117.0.dp).requiredHeight(29.0.dp)
    )
}

@Composable
fun Salir1Property1Perfil(
    salir: () -> Unit,
    modifier: Modifier = Modifier
) {
    RelayVector(
        vector = painterResource(R.drawable.cabecera_salir1),
        modifier = modifier.tappable(onTap = salir).requiredWidth(27.1875.dp).requiredHeight(28.0.dp)
    )
}

@Composable
fun TopLevelProperty1Perfil(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        backgroundColor = Color(
            alpha = 255,
            red = 107,
            green = 107,
            blue = 107
        ),
        arrangement = RelayContainerArrangement.Row,
        padding = PaddingValues(
            start = 15.0.dp,
            top = 12.0.dp,
            end = 15.0.dp,
            bottom = 12.0.dp
        ),
        itemSpacing = 84.0,
        content = content,
        modifier = modifier.fillMaxWidth(1.0f).fillMaxHeight(1.0f)
    )
}

@Composable
fun GenresProperty1BuscarPeli(modifier: Modifier = Modifier) {
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
        textAlign = TextAlign.Left,
        maxLines = -1,
        modifier = modifier.requiredWidth(95.0.dp).requiredHeight(31.0.dp)
    )
}

@Composable
fun TopLevelProperty1BuscarPeli(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        backgroundColor = Color(
            alpha = 255,
            red = 107,
            green = 107,
            blue = 107
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
fun BillboardProperty1Cartelera(modifier: Modifier = Modifier) {
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
        modifier = modifier.requiredWidth(127.0.dp).requiredHeight(32.0.dp)
    )
}

@Composable
fun TopLevelProperty1Cartelera(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        backgroundColor = Color(
            alpha = 255,
            red = 107,
            green = 107,
            blue = 107
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
fun VolverProperty1Volver(
    volver: () -> Unit,
    modifier: Modifier = Modifier
) {
    RelayVector(
        vector = painterResource(R.drawable.cabecera_volver),
        modifier = modifier.tappable(onTap = volver).requiredWidth(27.0.dp).requiredHeight(28.0.dp)
    )
}

@Composable
fun TopLevelProperty1Volver(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        backgroundColor = Color(
            alpha = 255,
            red = 107,
            green = 107,
            blue = 107
        ),
        mainAxisAlignment = MainAxisAlignment.Start,
        arrangement = RelayContainerArrangement.Row,
        padding = PaddingValues(
            start = 14.0.dp,
            top = 12.0.dp,
            end = 14.0.dp,
            bottom = 12.0.dp
        ),
        itemSpacing = 10.0,
        content = content,
        modifier = modifier.fillMaxWidth(1.0f).fillMaxHeight(1.0f)
    )
}
