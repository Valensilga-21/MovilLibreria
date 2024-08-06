package com.example.librera.models

data class libro(
    var id_libro: String,
    var titulo: String,
    var autor:String,
    var isbn: String,
    var genero: String,
    var num_ejemplares_dispo: Int,
    var num_ejemplares_ocupados: Int

)
