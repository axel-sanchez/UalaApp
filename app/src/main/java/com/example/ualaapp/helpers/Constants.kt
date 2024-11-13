package com.example.ualaapp.helpers

object Constants {
    const val BASE_URL =
    "https://gist.githubusercontent.com/hernan-uala/dce8843a8edbe0b0018b32e137bc2b3a/raw/0996accf70cb0ca0e16f9a99e0ee185fafca7af1/"

    //Endpoints
    const val GET_CITIES = "cities.json"
    const val ID_CITY = "idCity"

    enum class ApiError(var error: String) {
        GENERIC("Hubo un error al obtener las ciudades"),
        EMPTY_CITIES("No se obtuvo ninguna ciudad"),
        NETWORK_ERROR("Hubo un error en la conexión de internet")
    }

}