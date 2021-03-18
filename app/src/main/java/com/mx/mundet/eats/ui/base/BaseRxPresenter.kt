package com.mx.mundet.eats.ui.base

import androidx.annotation.CallSuper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by Alexander Juárez with Date 14/03/2021
 * @author Alexander Juárez
 */

abstract class RxPresenter<V> : BasePresenter<V> {

    private val disposables = CompositeDisposable()

    fun launch(job: () -> Disposable) {
        disposables.add(job())
    }


    override fun subscribe(view: V) {
        this.view = view
    }

    @CallSuper
    override fun unSubscribe() {
        disposables.clear()
        view = null
    }
}