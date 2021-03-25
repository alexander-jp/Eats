package com.mx.mundet.eats.ui.mvp.detailUser

import com.mx.mundet.eats.domain.repository.UserRepository
import com.mx.mundet.eats.ui.base.RxPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Alexander Juárez with Date 24/03/2021
 * @author Alexander Juárez
 */

class DetailUserPresenter @Inject constructor(private val repo : UserRepository) : RxPresenter<DetailUserContract.View>(), DetailUserContract.Presenter {
    override var view: DetailUserContract.View? = null

    override fun subscribe(view: DetailUserContract.View) {
        this.view = view
    }

    override fun getPeople(userId : Int) {
        launch {
            repo.getPeople(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view?.resultGetPeople(it)
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