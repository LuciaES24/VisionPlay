package com.lespsan543.visionplay.app.data.model

/**
 * Modelo de datos que guarda la información de un comentario
 */
data class CommentModel(
    var idDoc : String = "",
    var movieOrSerie : String = "",
    var comment : String = "",
    var user : String = ""
)
