package com.example.librera.config

class config {
    /*
    método companion object sirve para almacenra las variables static
    * */
    companion object{
        //ejemplo: http://direccion-ip:puerto
        var urlBase="http://10.192.89.115:8080/api/libreria/"
        var urlLibro= urlBase + "libro/"
    }
}