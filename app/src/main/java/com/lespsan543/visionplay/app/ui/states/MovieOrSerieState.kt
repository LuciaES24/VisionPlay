package com.lespsan543.visionplay.app.ui.states

data class MovieOrSerieState(
    var idAPI : Int = 0,
    var title : String = "",
    var overview : String = "",
    var poster : String = "",
    var date : String = "",
    var votes : String = "",
    var idDoc : String = "",
    var genres : List<String> = emptyList(),
    var type : String = ""
)