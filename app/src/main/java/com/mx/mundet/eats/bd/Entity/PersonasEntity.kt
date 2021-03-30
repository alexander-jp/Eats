package com.mx.mundet.eats.bd.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by Alexander Juárez with Date 18/03/2021
 * @author Alexander Juárez
 */

@Entity(tableName = "personas")
class PersonasEntity(

    @PrimaryKey
    var id: Int? = null,

    @SerializedName("nombre")
    var nombre: String? = null,

    @SerializedName("edad")
    var edad: Int? = null,

    @SerializedName("sexo")
    var sexo: String? = null,

    @SerializedName("aPaterno")
    var aPaterno : String? = null,

    @SerializedName("aMaterno")
    var aMaterno : String? = null,

    @SerializedName("email")
    var email : String? = null,

    @SerializedName("password")
    var password : String? = null

//    @SerializedName("isSyncronized")
//    var isSyncronized: Boolean? = null,
)