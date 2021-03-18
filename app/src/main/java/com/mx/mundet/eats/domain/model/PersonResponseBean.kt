package com.mx.mundet.eats.domain.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Alexander Juárez with Date 13/03/2021
 * @author Alexander Juárez
 */

data class PersonResponseBean(
    @SerializedName("nombre")
    var nombre: String? = null,

    var imagen: Int? = null,

    @SerializedName("edad")
    var edad: Int? = null,

    @SerializedName("sexo")
    var sexo: String? = null,

//    @SerializedName("isSyncronized")
//    var isSyncronized: Boolean? = null,

    @SerializedName("id")
    var id: Int? = null
)