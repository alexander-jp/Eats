package com.mx.mundet.eats.ui.mvp.registerUser

import com.mx.mundet.eats.bd.Entity.PersonasEntity
import com.mx.mundet.eats.domain.repository.LoginRepository
import com.mx.mundet.eats.ui.base.RxPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Alexander Juárez with Date 18/03/2021
 * @author Alexander Juárez
 */

class RegisterUserPresenter @Inject constructor(private val repo : LoginRepository): RxPresenter<RegisterUserContract.View>(),
    RegisterUserContract.Presenter {

    override var view: RegisterUserContract.View? = null

    override fun subscribe(view: RegisterUserContract.View) {
        this.view = view
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
        this.view = null
        super.unSubscribe()
    }
}