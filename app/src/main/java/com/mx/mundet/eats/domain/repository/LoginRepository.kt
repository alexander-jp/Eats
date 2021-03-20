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

interface LoginRepository {

    fun obtenerListaPersonas(): Single<List<PersonResponseBean>>
    fun obtenerListaPersonasBD(): Single<List<PersonasEntity>>
    fun insertPersonBD(request : PersonasEntity) : Single<PersonasEntity>
}


class LoginRepositoryImpl @Inject constructor(
    private val source: PersonasSource,
    private val database: AppRoomDatabase,
    private val context: Context
) : LoginRepository {

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

}