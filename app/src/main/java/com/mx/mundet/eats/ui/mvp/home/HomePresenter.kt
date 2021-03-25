package com.mx.mundet.eats.ui.mvp.home

import com.mx.mundet.eats.domain.repository.UserRepository
import com.mx.mundet.eats.ui.base.RxPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Alexander Juárez with Date 19/03/2021
 * @author Alexander Juárez
 */

class HomePresenter @Inject  constructor(private val repo: UserRepository) : RxPresenter<HomeContract.View>(),HomeContract.Presenter {

    override var view: HomeContract.View?=null

    override fun subscribe(view: HomeContract.View) {
        this.view = view
    }

    override fun obtenerListaPersonas() {
        launch {
            repo.obtenerListaPersonasBD()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                           view?.resultObtenerListaPersonas(it)
                },{
                    view?.showError(it)
                })
        }
    }

    override fun unSubscribe() {
        this.view = null
        super.unSubscribe()
    }

}