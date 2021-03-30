package com.mx.mundet.eats.domain.repository

import android.content.Context
import android.util.Log
import com.mx.mundet.eats.bd.AppRoomDatabase
import com.mx.mundet.eats.bd.Entity.PersonasEntity
import com.mx.mundet.eats.domain.api.PersonasSource
import com.mx.mundet.eats.domain.model.PersonResponseBean
import com.mx.mundet.eats.utils.NetworkUtils
import io.reactivex.Single
import javax.inject.Inject

interface UserRepository {

    fun obtenerListaPersonas(): Single<List<PersonResponseBean>>
    fun obtenerListaPersonasBD(): Single<List<PersonasEntity>>
    fun insertPersonBD(request: PersonasEntity): Single<PersonasEntity>
    fun getPeople(userId: Int): Single<PersonasEntity>
    fun login(userName : String, password : String) : Single<Boolean>
}


class UserRepositoryImpl @Inject constructor(
    private val source: PersonasSource,
    private val database: AppRoomDatabase,
    private val context: Context
) : UserRepository {

    override fun obtenerListaPersonas(): Single<List<PersonResponseBean>> {
        return source.getAllPersonas()
            .doOnError {
                if (!NetworkUtils.isInternetAvailable(context)) {
                    obtenerListaPersonasBD()
                }
            }
            .doOnSuccess {
                Log.e("TAG", "obtenerListaPersonas: exitoso")
            }
    }

    override fun obtenerListaPersonasBD(): Single<List<PersonasEntity>> = Single.create {
            val p = database.personasDao().queryPersonas()
            it.onSuccess(p)
    }

    override fun insertPersonBD(request: PersonasEntity): Single<PersonasEntity> {
        return Single.create {
            val p = database.personasDao().insert(request)
            it.onSuccess(request)
        }
    }

    override fun getPeople(userId: Int): Single<PersonasEntity> {
        return Single.create {
            val p = database.personasDao().queryPersona(userId)
            it.onSuccess(p)
        }
    }

    override fun login(userName: String, password: String): Single<Boolean> {
        return Single.create {
            val l = database.personasDao().login(userName, password)
            it.onSuccess(l)
        }
    }

}