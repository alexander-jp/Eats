package com.mx.mundet.eats.domain.repository

import android.util.Log
import com.mx.mundet.eats.domain.api.PersonasSource
import com.mx.mundet.eats.domain.model.PersonResponseBean
import io.reactivex.Single

interface LoginRepository {
    fun obtenerListaPersonas() : Single<List<PersonResponseBean>>
}

class LoginRepositoryImpl : LoginRepository {

//class LoginRepositoryImpl (private val source : PersonasSource): LoginRepository {

    override fun obtenerListaPersonas(): Single<List<PersonResponseBean>> {
//        return source.getAllPersonas()
//            .doOnError {
//                it.printStackTrace()
//            }
//            .doOnSuccess {
//                Log.e("TAG", "obtenerListaPersonas: exitoso")
//            }
        TODO("not implement")
    }

}