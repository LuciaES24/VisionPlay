package com.lespsan543.visionplay.guardar

import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.relay.compose.RelayContainer
import com.google.relay.compose.RelayContainerScope
import com.google.relay.compose.RelayVector
import com.google.relay.compose.tappable
import com.lespsan543.visionplay.R

// Design to select for Guardar
enum class Property1 {
    Default,
    Guardado
}

/**
 * This composable was generated from the UI Package 'guardar'.
 * Generated code; do not edit directly
 */
@Composable
fun Guardar(
    modifier: Modifier = Modifier,
    property1: Property1 = Property1.Default,
    guardar: () -> Unit = {},
    eliminar: () -> Unit = {}
) {
    when (property1) {
        Property1.Default -> TopLevelProperty1Default(
            guardar = guardar,
            modifier = modifier
        ) {
            GuardadoProperty1Default()
        }
        Property1.Guardado -> TopLevelProperty1Guardado(
            guardar = guardar,
            modifier = modifier
        ) {
            Guardado2Property1Guardado(eliminar = eliminar)
        }
    }
}

@Preview(widthDp = 30, heightDp = 28)
@Composable
private fun GuardarProperty1DefaultPreview() {
    MaterialTheme {
        Guardar(
            guardar = {},
            eliminar = {},
            property1 = Property1.Default
        )
    }
}

@Preview(widthDp = 30, heightDp = 28)
@Composable
private fun GuardarProperty1GuardadoPreview() {
    MaterialTheme {
        Guardar(
            guardar = {},
            eliminar = {},
            property1 = Property1.Guardado
        )
    }
}

@Composable
fun GuardadoProperty1Default(modifier: Modifier = Modifier) {
    RelayVector(
        vector = painterResource(R.drawable.guardar_guardado),
        modifier = modifier.requiredWidth(29.999998092651367.dp).requiredHeight(27.525001525878906.dp)
    )
}

@Composable
fun TopLevelProperty1Default(
    guardar: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        itemSpacing = 10.0,
        clipToParent = false,
        content = content,
        modifier = modifier.tappable(onTap = guardar)
    )
}

@Composable
fun Guardado2Property1Guardado(
    eliminar: () -> Unit,
    modifier: Modifier = Modifier
) {
    RelayVector(
        vector = painterResource(R.drawable.guardar_guardado2),
        modifier = modifier.tappable(onTap = eliminar).requiredWidth(30.0.dp).requiredHeight(27.525001525878906.dp)
    )
}

@Composable
fun TopLevelProperty1Guardado(
    guardar: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        itemSpacing = 10.0,
        clipToParent = false,
        content = content,
        modifier = modifier.tappable(onTap = guardar)
    )
}
