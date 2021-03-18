package com.mx.mundet.eats.ui.base

/**
 * Created by Alexander Juárez with Date 14/03/2021
 * @author Alexander Juárez
 */

interface BaseView<out T : BasePresenter<*>> {

    fun showError(error: Throwable)

    val presenter: T
}