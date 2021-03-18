package com.mx.mundet.eats.ui.mvp.login

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

class LoginPresenter  @Inject constructor(private val repo : LoginRepository): LoginContractPrueba.Presenter{

    var views : LoginContractPrueba.View?=null

//    override var view: LoginContract.View? = null
//
//    override fun obtenerListaPersonas() {
//        launch {
//            repo.obtenerListaPersonas()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    view?.resultObtenerListaPersonas(it)
//                },{
//                    view?.showError(it)
//                })
//        }
//    }

//    override fun obtenerShowToast() {
//        view?.resultShowToast("Texto desde el presenter")
//    }

    override fun obtenerShowToast() {
        views?.resultShowToast("Resultado de la prueba desde el presenter")
    }

    override fun setView(view: LoginActivity) {
        this.views = view
    }

}