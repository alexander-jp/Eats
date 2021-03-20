package com.mx.mundet.eats.ui.mvp.login

import com.mx.mundet.eats.bd.Entity.PersonasEntity
import com.mx.mundet.eats.domain.model.PersonResponseBean
import com.mx.mundet.eats.domain.repository.LoginRepository
import com.mx.mundet.eats.ui.base.RxPresenter
import com.mx.mundet.eats.ui.mvp.login.LoginContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Alexander Juárez with Date 13/03/2021
 * @author Alexander Juárez
 */


//class LoginPresenter  (private val repo : LoginRepository): RxPresenter<LoginContract.View>(), LoginContract.Presenter {

class LoginPresenter @Inject constructor(private val repo: LoginRepository) : RxPresenter<LoginContract.View>(), LoginContract.Presenter {

    override var view: LoginContract.View? = null

    override fun subscribe(view: LoginContract.View) {
        this.view = view
    }


    override fun obtenerListaPersonas() {
        launch {
            repo.obtenerListaPersonasBD()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view?.resultObtenerListaPersonas(it)
                }, {
                    view?.showError(it)
                })
        }
    }

    override fun insertPerson(request: PersonasEntity) {
        launch {
            repo.insertPersonBD(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                     view?.resultInsertPerson(it)
                },{
                    view?.showError(it)
                })
        }
    }

    override fun unSubscribe() {
        super.unSubscribe()
        this.view = null
    }

}