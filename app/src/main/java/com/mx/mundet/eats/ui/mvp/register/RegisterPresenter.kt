package com.mx.mundet.eats.ui.mvp.register

import com.mx.mundet.eats.bd.Entity.PersonasEntity
import com.mx.mundet.eats.domain.repository.UserRepository
import com.mx.mundet.eats.ui.base.RxPresenter
import com.mx.mundet.eats.ui.model.RegisterModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Alexander Juárez with Date 29/03/2021
 * @author Alexander Juárez
 */

class RegisterPresenter (private val repo : UserRepository): RxPresenter<RegisterContract.View>(),  RegisterContract.Presenter {
    override var view: RegisterContract.View? = null

    override fun registerUser(req: PersonasEntity) {
        launch {
            repo.insertPersonBD(req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view?.resultRegisterUser(it)
                },{
                    view?.showError(it)
                })
        }
    }

    override fun subscribe(view: RegisterContract.View) {
        this.view = view
        super.subscribe(view)
    }

    override fun unSubscribe() {
        this.view = null
        super.unSubscribe()
    }

}