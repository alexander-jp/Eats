package com.mx.mundet.eats.ui.mvp.login

import com.mx.mundet.eats.domain.repository.UserRepository
import com.mx.mundet.eats.ui.base.RxPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Alexander Juárez with Date 13/03/2021
 * @author Alexander Juárez
 */


//class LoginPresenter  (private val repo : LoginRepository): RxPresenter<LoginContract.View>(), LoginContract.Presenter {

class LoginPresenter @Inject constructor(private val repo: UserRepository) : RxPresenter<LoginContract.View>(), LoginContract.Presenter {

    override var view: LoginContract.View? = null

    override fun subscribe(view: LoginContract.View) {
        this.view = view
    }

    override fun loginUser(userName: String, password: String) {
        launch {
            repo.login(userName, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view?.resultLoginUser(it)
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