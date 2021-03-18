package com.mx.mundet.eats.ui.base

/**
 * Created by Alexander Juárez with Date 14/03/2021
 * @author Alexander Juárez
 */

interface BasePresenter<V> {

    fun subscribe(view: V)

    fun unSubscribe()

    var view : V?

}