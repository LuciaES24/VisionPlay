package com.lespsan543.visionplay.menu

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.google.relay.compose.RelayContainer
import com.google.relay.compose.RelayContainerArrangement
import com.google.relay.compose.RelayContainerScope
import com.google.relay.compose.RelayVector
import com.google.relay.compose.tappable
import com.lespsan543.visionplay.R

// Design to select for Menu
enum class Property1 {
    Inicio,
    Generos,
    Cine,
    Favoritos
}

/**
 * This composable was generated from the UI Package 'menu'.
 * Generated code; do not edit directly
 */
@Composable
fun Menu(
    modifier: Modifier = Modifier,
    property1: Property1 = Property1.Inicio,
    home: () -> Unit = {},
    genres1: () -> Unit = {},
    cine1: () -> Unit = {},
    fav1: () -> Unit = {},
    home2: () -> Unit = {},
    genres2: () -> Unit = {},
    cine2: () -> Unit = {},
    fav2: () -> Unit = {},
    home3: () -> Unit = {},
    genres3: () -> Unit = {},
    cine3: () -> Unit = {},
    fav3: () -> Unit = {},
    home4: () -> Unit = {},
    genres4: () -> Unit = {},
    cine4: () -> Unit = {},
    fav4: () -> Unit = {}
) {
    when (property1) {
        Property1.Inicio -> TopLevelProperty1Inicio(modifier = modifier) {
            HomeBProperty1Inicio(home = home)
            GenW1Property1Inicio(genres1 = genres1)
            CineW1Property1Inicio(cine1 = cine1) {
                VectorProperty1Inicio(
                    modifier = Modifier.boxAlign(
                        alignment = Alignment.TopStart,
                        offset = DpOffset(
                            x = -0.5.dp,
                            y = -0.5001068115234375.dp
                        )
                    ).rowWeight(1.0f).columnWeight(1.0f)
                )
                Frame4Property1Inicio(
                    modifier = Modifier.boxAlign(
                        alignment = Alignment.TopStart,
                        offset = DpOffset(
                            x = 4.229398898460829.dp,
                            y = 5.103504386781026.dp
                        )
                    )
                ) {
                    Frame11Property1Inicio(
                        modifier = Modifier.boxAlign(
                            alignment = Alignment.TopStart,
                            offset = DpOffset(
                                x = 34.56658564133404.dp,
                                y = 24.406732250508455.dp
                            )
                        )
                    ) {}
                }
                Frame5Property1Inicio(
                    modifier = Modifier.boxAlign(
                        alignment = Alignment.TopStart,
                        offset = DpOffset(
                            x = 26.051012303115158.dp,
                            y = 2.8633273323729043.dp
                        )
                    )
                ) {}
                Frame6Property1Inicio(
                    modifier = Modifier.boxAlign(
                        alignment = Alignment.TopStart,
                        offset = DpOffset(
                            x = 6.487529913554932.dp,
                            y = 22.095887871982683.dp
                        )
                    )
                ) {}
                Frame8Property1Inicio(
                    modifier = Modifier.boxAlign(
                        alignment = Alignment.TopStart,
                        offset = DpOffset(
                            x = 30.412661242711188.dp,
                            y = 11.429616969854763.dp
                        )
                    )
                ) {}
                Frame7Property1Inicio(
                    modifier = Modifier.boxAlign(
                        alignment = Alignment.TopStart,
                        offset = DpOffset(
                            x = 1.8202418308512591.dp,
                            y = 13.686813946108387.dp
                        )
                    )
                ) {}
                Frame9Property1Inicio(
                    modifier = Modifier.boxAlign(
                        alignment = Alignment.TopStart,
                        offset = DpOffset(
                            x = 1.79486083984375.dp,
                            y = 12.599403381347656.dp
                        )
                    )
                ) {}
                Frame10Property1Inicio(
                    modifier = Modifier.boxAlign(
                        alignment = Alignment.TopStart,
                        offset = DpOffset(
                            x = 26.213587511938503.dp,
                            y = 0.7757730883695331.dp
                        )
                    )
                ) {}
            }
            FavW1Property1Inicio(fav1 = fav1)
        }
        Property1.Generos -> TopLevelProperty1Generos(modifier = modifier) {
            HomeW2Property1Generos(home2 = home2)
            Genres2Property1Generos(genres2 = genres2)
            CineW2Property1Generos(cine2 = cine2) {
                VectorProperty1Generos(
                    modifier = Modifier.boxAlign(
                        alignment = Alignment.TopStart,
                        offset = DpOffset(
                            x = -0.5.dp,
                            y = -0.5001068115234375.dp
                        )
                    ).rowWeight(1.0f).columnWeight(1.0f)
                )
                Frame4Property1Generos(
                    modifier = Modifier.boxAlign(
                        alignment = Alignment.TopStart,
                        offset = DpOffset(
                            x = 4.229530940929806.dp,
                            y = 5.103101850720805.dp
                        )
                    )
                ) {
                    Frame11Property1Generos(
                        modifier = Modifier.boxAlign(
                            alignment = Alignment.TopStart,
                            offset = DpOffset(
                                x = 34.56658564133404.dp,
                                y = 24.406732250508455.dp
                            )
                        )
                    ) {}
                }
                Frame5Property1Generos(
                    modifier = Modifier.boxAlign(
                        alignment = Alignment.TopStart,
                        offset = DpOffset(
                            x = 26.051056488621285.dp,
                            y = 2.863102352912108.dp
                        )
                    )
                ) {}
                Frame6Property1Generos(
                    modifier = Modifier.boxAlign(
                        alignment = Alignment.TopStart,
                        offset = DpOffset(
                            x = 6.487608520713774.dp,
                            y = 22.094489408021087.dp
                        )
                    )
                ) {}
                Frame8Property1Generos(
                    modifier = Modifier.boxAlign(
                        alignment = Alignment.TopStart,
                        offset = DpOffset(
                            x = 30.412743415932606.dp,
                            y = 11.428882087632402.dp
                        )
                    )
                ) {}
                Frame7Property1Generos(
                    modifier = Modifier.boxAlign(
                        alignment = Alignment.TopStart,
                        offset = DpOffset(
                            x = 1.8202822016696558.dp,
                            y = 13.685944759659812.dp
                        )
                    )
                ) {}
                Frame9Property1Generos(
                    modifier = Modifier.boxAlign(
                        alignment = Alignment.TopStart,
                        offset = DpOffset(
                            x = 1.79486083984375.dp,
                            y = 12.598663330078125.dp
                        )
                    )
                ) {}
                Frame10Property1Generos(
                    modifier = Modifier.boxAlign(
                        alignment = Alignment.TopStart,
                        offset = DpOffset(
                            x = 26.213643529484642.dp,
                            y = 0.7756342977640145.dp
                        )
                    )
                ) {}
            }
            FavW2Property1Generos(fav2 = fav2)
        }
        Property1.Cine -> TopLevelProperty1Cine(modifier = modifier) {
            HomeW3Property1Cine(home3 = home3)
            GenW2Property1Cine(genres3 = genres3)
            CineBProperty1Cine(cine3 = cine3) {
                Group1Property1Cine {
                    VectorProperty1Cine(
                        modifier = Modifier.boxAlign(
                            alignment = Alignment.TopStart,
                            offset = DpOffset(
                                x = -0.5.dp,
                                y = -0.5001068115234375.dp
                            )
                        ).rowWeight(1.0f).columnWeight(1.0f)
                    )
                    Frame4Property1Cine(
                        modifier = Modifier.boxAlign(
                            alignment = Alignment.TopStart,
                            offset = DpOffset(
                                x = 4.229530940929806.dp,
                                y = 5.103101850720805.dp
                            )
                        )
                    ) {
                        Frame11Property1Cine(
                            modifier = Modifier.boxAlign(
                                alignment = Alignment.TopStart,
                                offset = DpOffset(
                                    x = 34.56658564133404.dp,
                                    y = 24.406732250508455.dp
                                )
                            )
                        ) {}
                    }
                    Frame5Property1Cine(
                        modifier = Modifier.boxAlign(
                            alignment = Alignment.TopStart,
                            offset = DpOffset(
                                x = 26.051056488621285.dp,
                                y = 2.86308852463452.dp
                            )
                        )
                    ) {}
                    Frame6Property1Cine(
                        modifier = Modifier.boxAlign(
                            alignment = Alignment.TopStart,
                            offset = DpOffset(
                                x = 6.487608520713774.dp,
                                y = 22.094489408021087.dp
                            )
                        )
                    ) {}
                    Frame8Property1Cine(
                        modifier = Modifier.boxAlign(
                            alignment = Alignment.TopStart,
                            offset = DpOffset(
                                x = 30.412743415932606.dp,
                                y = 11.428882087632402.dp
                            )
                        )
                    ) {}
                    Frame7Property1Cine(
                        modifier = Modifier.boxAlign(
                            alignment = Alignment.TopStart,
                            offset = DpOffset(
                                x = 1.8202822016696558.dp,
                                y = 13.685944759659812.dp
                            )
                        )
                    ) {}
                    Frame9Property1Cine(
                        modifier = Modifier.boxAlign(
                            alignment = Alignment.TopStart,
                            offset = DpOffset(
                                x = 1.79486083984375.dp,
                                y = 12.598663330078125.dp
                            )
                        )
                    ) {}
                    Frame10Property1Cine(
                        modifier = Modifier.boxAlign(
                            alignment = Alignment.TopStart,
                            offset = DpOffset(
                                x = 26.213643529484642.dp,
                                y = 0.7756342977640145.dp
                            )
                        )
                    ) {}
                }
            }
            FavW3Property1Cine(fav3 = fav3)
        }
        Property1.Favoritos -> TopLevelProperty1Favoritos(modifier = modifier) {
            HomeW1Property1Favoritos(home4 = home4)
            Genres4Property1Favoritos(genres4 = genres4)
            Group4Property1Favoritos(cine4 = cine4) {
                Group3Property1Favoritos {
                    Group2Property1Favoritos {
                        VectorProperty1Favoritos(
                            modifier = Modifier.boxAlign(
                                alignment = Alignment.TopStart,
                                offset = DpOffset(
                                    x = -0.5.dp,
                                    y = -0.5001220703125.dp
                                )
                            ).rowWeight(1.0f).columnWeight(1.0f)
                        )
                        Frame4Property1Favoritos(
                            modifier = Modifier.boxAlign(
                                alignment = Alignment.TopStart,
                                offset = DpOffset(
                                    x = 4.229530940929806.dp,
                                    y = 5.103101850720805.dp
                                )
                            )
                        ) {
                            Frame11Property1Favoritos(
                                modifier = Modifier.boxAlign(
                                    alignment = Alignment.TopStart,
                                    offset = DpOffset(
                                        x = 34.56658564133404.dp,
                                        y = 24.406732250508455.dp
                                    )
                                )
                            ) {}
                        }
                        Frame5Property1Favoritos(
                            modifier = Modifier.boxAlign(
                                alignment = Alignment.TopStart,
                                offset = DpOffset(
                                    x = 26.051056488621285.dp,
                                    y = 2.863102352912108.dp
                                )
                            )
                        ) {}
                        Frame6Property1Favoritos(
                            modifier = Modifier.boxAlign(
                                alignment = Alignment.TopStart,
                                offset = DpOffset(
                                    x = 6.487608520713774.dp,
                                    y = 22.094489408021087.dp
                                )
                            )
                        ) {}
                        Frame8Property1Favoritos(
                            modifier = Modifier.boxAlign(
                                alignment = Alignment.TopStart,
                                offset = DpOffset(
                                    x = 30.412743415932606.dp,
                                    y = 11.428882087632402.dp
                                )
                            )
                        ) {}
                        Frame7Property1Favoritos(
                            modifier = Modifier.boxAlign(
                                alignment = Alignment.TopStart,
                                offset = DpOffset(
                                    x = 1.8202822016696558.dp,
                                    y = 13.685944759659812.dp
                                )
                            )
                        ) {}
                        Frame9Property1Favoritos(
                            modifier = Modifier.boxAlign(
                                alignment = Alignment.TopStart,
                                offset = DpOffset(
                                    x = 1.79486083984375.dp,
                                    y = 12.598663330078125.dp
                                )
                            )
                        ) {}
                        Frame10Property1Favoritos(
                            modifier = Modifier.boxAlign(
                                alignment = Alignment.TopStart,
                                offset = DpOffset(
                                    x = 26.213643529484642.dp,
                                    y = 0.7756342977640145.dp
                                )
                            )
                        ) {}
                    }
                }
            }
            FavBProperty1Favoritos(fav4 = fav4)
        }
    }
}

@Preview(widthDp = 360, heightDp = 50)
@Composable
private fun MenuProperty1InicioPreview() {
    MaterialTheme {
        RelayContainer {
            Menu(
                home = {},
                genres1 = {},
                cine1 = {},
                fav1 = {},
                home2 = {},
                genres2 = {},
                cine2 = {},
                fav2 = {},
                home3 = {},
                genres3 = {},
                cine3 = {},
                fav3 = {},
                home4 = {},
                genres4 = {},
                cine4 = {},
                fav4 = {},
                property1 = Property1.Inicio,
                modifier = Modifier.rowWeight(1.0f).columnWeight(1.0f)
            )
        }
    }
}

@Preview(widthDp = 360, heightDp = 50)
@Composable
private fun MenuProperty1GenerosPreview() {
    MaterialTheme {
        RelayContainer {
            Menu(
                home = {},
                genres1 = {},
                cine1 = {},
                fav1 = {},
                home2 = {},
                genres2 = {},
                cine2 = {},
                fav2 = {},
                home3 = {},
                genres3 = {},
                cine3 = {},
                fav3 = {},
                home4 = {},
                genres4 = {},
                cine4 = {},
                fav4 = {},
                property1 = Property1.Generos,
                modifier = Modifier.rowWeight(1.0f).columnWeight(1.0f)
            )
        }
    }
}

@Preview(widthDp = 360, heightDp = 50)
@Composable
private fun MenuProperty1CinePreview() {
    MaterialTheme {
        RelayContainer {
            Menu(
                home = {},
                genres1 = {},
                cine1 = {},
                fav1 = {},
                home2 = {},
                genres2 = {},
                cine2 = {},
                fav2 = {},
                home3 = {},
                genres3 = {},
                cine3 = {},
                fav3 = {},
                home4 = {},
                genres4 = {},
                cine4 = {},
                fav4 = {},
                property1 = Property1.Cine,
                modifier = Modifier.rowWeight(1.0f).columnWeight(1.0f)
            )
        }
    }
}

@Preview(widthDp = 360, heightDp = 50)
@Composable
private fun MenuProperty1FavoritosPreview() {
    MaterialTheme {
        RelayContainer {
            Menu(
                home = {},
                genres1 = {},
                cine1 = {},
                fav1 = {},
                home2 = {},
                genres2 = {},
                cine2 = {},
                fav2 = {},
                home3 = {},
                genres3 = {},
                cine3 = {},
                fav3 = {},
                home4 = {},
                genres4 = {},
                cine4 = {},
                fav4 = {},
                property1 = Property1.Favoritos,
                modifier = Modifier.rowWeight(1.0f).columnWeight(1.0f)
            )
        }
    }
}

@Composable
fun HomeBProperty1Inicio(
    home: () -> Unit,
    modifier: Modifier = Modifier
) {
    RelayVector(
        vector = painterResource(R.drawable.menu_home_b),
        modifier = modifier.tappable(onTap = home).requiredWidth(25.0.dp).requiredHeight(25.0.dp)
    )
}

@Composable
fun GenW1Property1Inicio(
    genres1: () -> Unit,
    modifier: Modifier = Modifier
) {
    RelayVector(
        vector = painterResource(R.drawable.menu_gen_w1),
        modifier = modifier.tappable(onTap = genres1).requiredWidth(25.0.dp).requiredHeight(25.0.dp)
    )
}

@Composable
fun VectorProperty1Inicio(modifier: Modifier = Modifier) {
    RelayVector(
        vector = painterResource(R.drawable.menu_vector),
        modifier = modifier.padding(
            paddingValues = PaddingValues(
                start = 0.0.dp,
                top = 0.0.dp,
                end = 0.0.dp,
                bottom = 0.3926219940185547.dp
            )
        ).fillMaxWidth(1.0f).fillMaxHeight(1.0f)
    )
}

@Composable
fun Frame11Property1Inicio(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        backgroundColor = Color(
            alpha = 255,
            red = 255,
            green = 255,
            blue = 255
        ),
        isStructured = false,
        content = content,
        modifier = modifier.graphicsLayer(rotationZ = 46.999994389132155f).requiredWidth(2.0835719108581543.dp).requiredHeight(4.1175923347473145.dp)
    )
}

@Composable
fun Frame4Property1Inicio(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        backgroundColor = Color(
            alpha = 255,
            red = 255,
            green = 255,
            blue = 255
        ),
        isStructured = false,
        content = content,
        modifier = modifier.graphicsLayer(rotationZ = -26.06340327912455f).requiredWidth(26.634578704833984.dp).requiredHeight(16.89792823791504.dp)
    )
}

@Composable
fun Frame5Property1Inicio(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        backgroundColor = Color(
            alpha = 255,
            red = 255,
            green = 255,
            blue = 255
        ),
        isStructured = false,
        content = content,
        modifier = modifier.graphicsLayer(rotationZ = -26.0633877335449f).requiredWidth(7.12874698638916.dp).requiredHeight(9.980875968933105.dp)
    )
}

@Composable
fun Frame6Property1Inicio(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        backgroundColor = Color(
            alpha = 255,
            red = 255,
            green = 255,
            blue = 255
        ),
        isStructured = false,
        content = content,
        modifier = modifier.graphicsLayer(rotationZ = -73.0448971739751f).requiredWidth(1.8746775388717651.dp).requiredHeight(3.6961638927459717.dp)
    )
}

@Composable
fun Frame8Property1Inicio(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        backgroundColor = Color(
            alpha = 255,
            red = 255,
            green = 255,
            blue = 255
        ),
        isStructured = false,
        content = content,
        modifier = modifier.graphicsLayer(rotationZ = -77.03519284177064f).requiredWidth(3.076141119003296.dp).requiredHeight(2.24349308013916.dp)
    )
}

@Composable
fun Frame7Property1Inicio(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        backgroundColor = Color(
            alpha = 255,
            red = 255,
            green = 255,
            blue = 255
        ),
        isStructured = false,
        content = content,
        modifier = modifier.graphicsLayer(rotationZ = -26.063395506335805f).requiredWidth(7.12874698638916.dp).requiredHeight(9.980875968933105.dp)
    )
}

@Composable
fun Frame9Property1Inicio(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        backgroundColor = Color(
            alpha = 255,
            red = 255,
            green = 255,
            blue = 255
        ),
        isStructured = false,
        content = content,
        modifier = modifier.requiredWidth(1.7948718070983887.dp).requiredHeight(2.6998724937438965.dp)
    )
}

@Composable
fun Frame10Property1Inicio(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        backgroundColor = Color(
            alpha = 255,
            red = 255,
            green = 255,
            blue = 255
        ),
        isStructured = false,
        content = content,
        modifier = modifier.graphicsLayer(rotationZ = 114.93847276419876f).requiredWidth(2.31640362739563.dp).requiredHeight(3.8481969833374023.dp)
    )
}

@Composable
fun CineW1Property1Inicio(
    cine1: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        isStructured = false,
        clipToParent = false,
        content = content,
        modifier = modifier.tappable(onTap = cine1).requiredWidth(35.0.dp).requiredHeight(27.001588821411133.dp)
    )
}

@Composable
fun FavW1Property1Inicio(
    fav1: () -> Unit,
    modifier: Modifier = Modifier
) {
    RelayVector(
        vector = painterResource(R.drawable.menu_fav_w1),
        modifier = modifier.tappable(onTap = fav1).requiredWidth(25.0.dp).requiredHeight(25.0.dp)
    )
}

@Composable
fun TopLevelProperty1Inicio(
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
            start = 40.0.dp,
            top = 10.0.dp,
            end = 40.0.dp,
            bottom = 10.0.dp
        ),
        itemSpacing = 70.0,
        content = content,
        modifier = modifier.fillMaxWidth(1.0f).fillMaxHeight(1.0f)
    )
}

@Composable
fun HomeW2Property1Generos(
    home2: () -> Unit,
    modifier: Modifier = Modifier
) {
    RelayVector(
        vector = painterResource(R.drawable.menu_home_w2),
        modifier = modifier.tappable(onTap = home2).requiredWidth(25.0.dp).requiredHeight(25.0.dp)
    )
}

@Composable
fun Genres2Property1Generos(
    genres2: () -> Unit,
    modifier: Modifier = Modifier
) {
    RelayVector(
        vector = painterResource(R.drawable.menu_genres2),
        modifier = modifier.tappable(onTap = genres2).requiredWidth(25.0.dp).requiredHeight(25.0.dp)
    )
}

@Composable
fun VectorProperty1Generos(modifier: Modifier = Modifier) {
    RelayVector(
        vector = painterResource(R.drawable.menu_vector1),
        modifier = modifier.padding(
            paddingValues = PaddingValues(
                start = 0.0.dp,
                top = 0.0.dp,
                end = 0.0.dp,
                bottom = 0.39259910583496094.dp
            )
        ).fillMaxWidth(1.0f).fillMaxHeight(1.0f)
    )
}

@Composable
fun Frame11Property1Generos(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        backgroundColor = Color(
            alpha = 255,
            red = 255,
            green = 255,
            blue = 255
        ),
        isStructured = false,
        content = content,
        modifier = modifier.graphicsLayer(rotationZ = 46.999994389132155f).requiredWidth(2.0835719108581543.dp).requiredHeight(4.1175923347473145.dp)
    )
}

@Composable
fun Frame4Property1Generos(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        backgroundColor = Color(
            alpha = 255,
            red = 255,
            green = 255,
            blue = 255
        ),
        isStructured = false,
        content = content,
        modifier = modifier.graphicsLayer(rotationZ = -26.062074100911282f).requiredWidth(26.634275436401367.dp).requiredHeight(16.897123336791992.dp)
    )
}

@Composable
fun Frame5Property1Generos(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        backgroundColor = Color(
            alpha = 255,
            red = 255,
            green = 255,
            blue = 255
        ),
        isStructured = false,
        content = content,
        modifier = modifier.graphicsLayer(rotationZ = -26.06205855459426f).requiredWidth(7.128666400909424.dp).requiredHeight(9.980401039123535.dp)
    )
}

@Composable
fun Frame6Property1Generos(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        backgroundColor = Color(
            alpha = 255,
            red = 255,
            green = 255,
            blue = 255
        ),
        isStructured = false,
        content = content,
        modifier = modifier.graphicsLayer(rotationZ = -73.04395640230992f).requiredWidth(1.8745766878128052.dp).requiredHeight(3.6961452960968018.dp)
    )
}

@Composable
fun Frame8Property1Generos(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        backgroundColor = Color(
            alpha = 255,
            red = 255,
            green = 255,
            blue = 255
        ),
        isStructured = false,
        content = content,
        modifier = modifier.graphicsLayer(rotationZ = -77.03445515830722f).requiredWidth(3.0759692192077637.dp).requiredHeight(2.2434864044189453.dp)
    )
}

@Composable
fun Frame7Property1Generos(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        backgroundColor = Color(
            alpha = 255,
            red = 255,
            green = 255,
            blue = 255
        ),
        isStructured = false,
        content = content,
        modifier = modifier.graphicsLayer(rotationZ = -26.06206632775385f).requiredWidth(7.128666400909424.dp).requiredHeight(9.980401039123535.dp)
    )
}

@Composable
fun Frame9Property1Generos(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        backgroundColor = Color(
            alpha = 255,
            red = 255,
            green = 255,
            blue = 255
        ),
        isStructured = false,
        content = content,
        modifier = modifier.requiredWidth(1.7948718070983887.dp).requiredHeight(2.6997134685516357.dp)
    )
}

@Composable
fun Frame10Property1Generos(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        backgroundColor = Color(
            alpha = 255,
            red = 255,
            green = 255,
            blue = 255
        ),
        isStructured = false,
        content = content,
        modifier = modifier.graphicsLayer(rotationZ = 114.93976271369657f).requiredWidth(2.316291570663452.dp).requiredHeight(3.848156452178955.dp)
    )
}

@Composable
fun CineW2Property1Generos(
    cine2: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        isStructured = false,
        clipToParent = false,
        content = content,
        modifier = modifier.tappable(onTap = cine2).requiredWidth(35.0.dp).requiredHeight(27.0.dp)
    )
}

@Composable
fun FavW2Property1Generos(
    fav2: () -> Unit,
    modifier: Modifier = Modifier
) {
    RelayVector(
        vector = painterResource(R.drawable.menu_fav_w2),
        modifier = modifier.tappable(onTap = fav2).requiredWidth(25.0.dp).requiredHeight(25.0.dp)
    )
}

@Composable
fun TopLevelProperty1Generos(
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
            start = 40.0.dp,
            top = 10.0.dp,
            end = 40.0.dp,
            bottom = 10.0.dp
        ),
        itemSpacing = 70.0,
        content = content,
        modifier = modifier.fillMaxWidth(1.0f).fillMaxHeight(1.0f)
    )
}

@Composable
fun HomeW3Property1Cine(
    home3: () -> Unit,
    modifier: Modifier = Modifier
) {
    RelayVector(
        vector = painterResource(R.drawable.menu_home_w3),
        modifier = modifier.tappable(onTap = home3).requiredWidth(25.0.dp).requiredHeight(25.0.dp)
    )
}

@Composable
fun GenW2Property1Cine(
    genres3: () -> Unit,
    modifier: Modifier = Modifier
) {
    RelayVector(
        vector = painterResource(R.drawable.menu_gen_w2),
        modifier = modifier.tappable(onTap = genres3).requiredWidth(25.0.dp).requiredHeight(25.0.dp)
    )
}

@Composable
fun VectorProperty1Cine(modifier: Modifier = Modifier) {
    RelayVector(
        vector = painterResource(R.drawable.menu_vector2),
        modifier = modifier.padding(
            paddingValues = PaddingValues(
                start = 0.0.dp,
                top = 0.0.dp,
                end = 0.0.dp,
                bottom = 0.39259910583496094.dp
            )
        ).fillMaxWidth(1.0f).fillMaxHeight(1.0f)
    )
}

@Composable
fun Frame11Property1Cine(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        backgroundColor = Color(
            alpha = 255,
            red = 255,
            green = 255,
            blue = 255
        ),
        isStructured = false,
        content = content,
        modifier = modifier.graphicsLayer(rotationZ = 46.999994389132155f).requiredWidth(2.0835719108581543.dp).requiredHeight(4.1175923347473145.dp)
    )
}

@Composable
fun Frame4Property1Cine(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        backgroundColor = Color(
            alpha = 255,
            red = 69,
            green = 69,
            blue = 69
        ),
        isStructured = false,
        content = content,
        modifier = modifier.graphicsLayer(rotationZ = -26.062074100911282f).requiredWidth(26.634275436401367.dp).requiredHeight(16.897123336791992.dp)
    )
}

@Composable
fun Frame5Property1Cine(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        backgroundColor = Color(
            alpha = 255,
            red = 69,
            green = 69,
            blue = 69
        ),
        isStructured = false,
        content = content,
        modifier = modifier.graphicsLayer(rotationZ = -26.06205855459426f).requiredWidth(7.128666400909424.dp).requiredHeight(9.980401039123535.dp)
    )
}

@Composable
fun Frame6Property1Cine(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        backgroundColor = Color(
            alpha = 255,
            red = 69,
            green = 69,
            blue = 69
        ),
        isStructured = false,
        content = content,
        modifier = modifier.graphicsLayer(rotationZ = -73.04395640230992f).requiredWidth(1.8745766878128052.dp).requiredHeight(3.6961452960968018.dp)
    )
}

@Composable
fun Frame8Property1Cine(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        backgroundColor = Color(
            alpha = 255,
            red = 69,
            green = 69,
            blue = 69
        ),
        isStructured = false,
        content = content,
        modifier = modifier.graphicsLayer(rotationZ = -77.03445515830722f).requiredWidth(3.0759692192077637.dp).requiredHeight(2.2434864044189453.dp)
    )
}

@Composable
fun Frame7Property1Cine(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        backgroundColor = Color(
            alpha = 255,
            red = 69,
            green = 69,
            blue = 69
        ),
        isStructured = false,
        content = content,
        modifier = modifier.graphicsLayer(rotationZ = -26.06206632775385f).requiredWidth(7.128666400909424.dp).requiredHeight(9.980401039123535.dp)
    )
}

@Composable
fun Frame9Property1Cine(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        backgroundColor = Color(
            alpha = 255,
            red = 69,
            green = 69,
            blue = 69
        ),
        isStructured = false,
        content = content,
        modifier = modifier.requiredWidth(1.7948718070983887.dp).requiredHeight(2.6997134685516357.dp)
    )
}

@Composable
fun Frame10Property1Cine(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        backgroundColor = Color(
            alpha = 255,
            red = 69,
            green = 69,
            blue = 69
        ),
        isStructured = false,
        content = content,
        modifier = modifier.graphicsLayer(rotationZ = 114.93976271369657f).requiredWidth(2.316291570663452.dp).requiredHeight(3.848156452178955.dp)
    )
}

@Composable
fun Group1Property1Cine(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        isStructured = false,
        clipToParent = false,
        content = content,
        modifier = modifier.requiredWidth(35.0.dp).requiredHeight(27.0.dp)
    )
}

@Composable
fun CineBProperty1Cine(
    cine3: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        isStructured = false,
        clipToParent = false,
        content = content,
        modifier = modifier.tappable(onTap = cine3).requiredWidth(35.0.dp).requiredHeight(27.0.dp)
    )
}

@Composable
fun FavW3Property1Cine(
    fav3: () -> Unit,
    modifier: Modifier = Modifier
) {
    RelayVector(
        vector = painterResource(R.drawable.menu_fav_w3),
        modifier = modifier.tappable(onTap = fav3).requiredWidth(25.0.dp).requiredHeight(25.0.dp)
    )
}

@Composable
fun TopLevelProperty1Cine(
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
            start = 40.0.dp,
            top = 10.0.dp,
            end = 40.0.dp,
            bottom = 10.0.dp
        ),
        itemSpacing = 70.0,
        content = content,
        modifier = modifier.fillMaxWidth(1.0f).fillMaxHeight(1.0f)
    )
}

@Composable
fun HomeW1Property1Favoritos(
    home4: () -> Unit,
    modifier: Modifier = Modifier
) {
    RelayVector(
        vector = painterResource(R.drawable.menu_home_w1),
        modifier = modifier.tappable(onTap = home4).requiredWidth(25.0.dp).requiredHeight(25.0.dp)
    )
}

@Composable
fun Genres4Property1Favoritos(
    genres4: () -> Unit,
    modifier: Modifier = Modifier
) {
    RelayVector(
        vector = painterResource(R.drawable.menu_genres4),
        modifier = modifier.tappable(onTap = genres4).requiredWidth(25.0.dp).requiredHeight(25.0.dp)
    )
}

@Composable
fun VectorProperty1Favoritos(modifier: Modifier = Modifier) {
    RelayVector(
        vector = painterResource(R.drawable.menu_vector3),
        modifier = modifier.padding(
            paddingValues = PaddingValues(
                start = 0.0.dp,
                top = 0.0.dp,
                end = 0.0.dp,
                bottom = 0.39259910583496094.dp
            )
        ).fillMaxWidth(1.0f).fillMaxHeight(1.0f)
    )
}

@Composable
fun Frame11Property1Favoritos(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        backgroundColor = Color(
            alpha = 255,
            red = 255,
            green = 255,
            blue = 255
        ),
        isStructured = false,
        content = content,
        modifier = modifier.graphicsLayer(rotationZ = 46.999994389132155f).requiredWidth(2.0835719108581543.dp).requiredHeight(4.1175923347473145.dp)
    )
}

@Composable
fun Frame4Property1Favoritos(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        backgroundColor = Color(
            alpha = 255,
            red = 255,
            green = 255,
            blue = 255
        ),
        isStructured = false,
        content = content,
        modifier = modifier.graphicsLayer(rotationZ = -26.062074100911282f).requiredWidth(26.634275436401367.dp).requiredHeight(16.897123336791992.dp)
    )
}

@Composable
fun Frame5Property1Favoritos(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        backgroundColor = Color(
            alpha = 255,
            red = 255,
            green = 255,
            blue = 255
        ),
        isStructured = false,
        content = content,
        modifier = modifier.graphicsLayer(rotationZ = -26.06205855459426f).requiredWidth(7.128666400909424.dp).requiredHeight(9.980401039123535.dp)
    )
}

@Composable
fun Frame6Property1Favoritos(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        backgroundColor = Color(
            alpha = 255,
            red = 255,
            green = 255,
            blue = 255
        ),
        isStructured = false,
        content = content,
        modifier = modifier.graphicsLayer(rotationZ = -73.04395640230992f).requiredWidth(1.8745766878128052.dp).requiredHeight(3.6961452960968018.dp)
    )
}

@Composable
fun Frame8Property1Favoritos(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        backgroundColor = Color(
            alpha = 255,
            red = 255,
            green = 255,
            blue = 255
        ),
        isStructured = false,
        content = content,
        modifier = modifier.graphicsLayer(rotationZ = -77.03445515830722f).requiredWidth(3.0759692192077637.dp).requiredHeight(2.2434864044189453.dp)
    )
}

@Composable
fun Frame7Property1Favoritos(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        backgroundColor = Color(
            alpha = 255,
            red = 255,
            green = 255,
            blue = 255
        ),
        isStructured = false,
        content = content,
        modifier = modifier.graphicsLayer(rotationZ = -26.06206632775385f).requiredWidth(7.128666400909424.dp).requiredHeight(9.980401039123535.dp)
    )
}

@Composable
fun Frame9Property1Favoritos(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        backgroundColor = Color(
            alpha = 255,
            red = 255,
            green = 255,
            blue = 255
        ),
        isStructured = false,
        content = content,
        modifier = modifier.requiredWidth(1.7948718070983887.dp).requiredHeight(2.6997134685516357.dp)
    )
}

@Composable
fun Frame10Property1Favoritos(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        backgroundColor = Color(
            alpha = 255,
            red = 255,
            green = 255,
            blue = 255
        ),
        isStructured = false,
        content = content,
        modifier = modifier.graphicsLayer(rotationZ = 114.93976271369657f).requiredWidth(2.316291570663452.dp).requiredHeight(3.848156452178955.dp)
    )
}

@Composable
fun Group2Property1Favoritos(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        isStructured = false,
        clipToParent = false,
        content = content,
        modifier = modifier.requiredWidth(35.0.dp).requiredHeight(27.0.dp)
    )
}

@Composable
fun Group3Property1Favoritos(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        isStructured = false,
        clipToParent = false,
        content = content,
        modifier = modifier.requiredWidth(35.0.dp).requiredHeight(27.0.dp)
    )
}

@Composable
fun Group4Property1Favoritos(
    cine4: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        isStructured = false,
        clipToParent = false,
        content = content,
        modifier = modifier.tappable(onTap = cine4).requiredWidth(35.0.dp).requiredHeight(27.0.dp)
    )
}

@Composable
fun FavBProperty1Favoritos(
    fav4: () -> Unit,
    modifier: Modifier = Modifier
) {
    RelayVector(
        vector = painterResource(R.drawable.menu_fav_b),
        modifier = modifier.tappable(onTap = fav4).requiredWidth(25.0.dp).requiredHeight(25.0.dp)
    )
}

@Composable
fun TopLevelProperty1Favoritos(
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
            start = 40.0.dp,
            top = 10.0.dp,
            end = 40.0.dp,
            bottom = 10.0.dp
        ),
        itemSpacing = 70.0,
        content = content,
        modifier = modifier.fillMaxWidth(1.0f).fillMaxHeight(1.0f)
    )
}
