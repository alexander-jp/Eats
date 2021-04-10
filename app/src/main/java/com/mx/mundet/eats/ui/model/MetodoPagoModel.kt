package com.mx.mundet.eats.ui.model

/**
 * Created by Alexander Juárez with Date 03/04/2021
 * @author Alexander Juárez
 */

data class MetodoPagoModel(
    var id: Int? = null,
    var nombre: String? = null
) {

    fun listData() : ArrayList<MetodoPagoModel>{
        return arrayListOf(
            MetodoPagoModel(1, "Mercado Libre"),
            MetodoPagoModel(2, "Banco Azteca"),
            MetodoPagoModel(3, "Banco Banorte"),
            MetodoPagoModel(4, "Banco Bancomer")
        )
    }

    override fun toString(): String {
        return nombre!!
    }


}