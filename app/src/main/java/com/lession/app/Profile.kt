package com.lession.app

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Profile(

    val id: String,

    val nombre: String = "",

    val apellido: String = "",

    val email: String = "",

    val edad: Int? = null,

    val genero: String? = null,

    @SerialName("tipo_cuerpo")
    val tipoCuerpo: String? = null,

    val altura: Double? = null,

    val peso: Double? = null,

    val objetivo: String? = null,

    @SerialName("nivel_actividad")
    val nivelActividad: String? = null,

    @SerialName("avatar_url")
    val avatarUrl: String? = null,

    @SerialName("bluetooth_conectado")
    val bluetoothConectado: Boolean = false,

    @SerialName("wifi_conectado")
    val wifiConectado: Boolean = false,

    val idioma: String = "Español"


)
