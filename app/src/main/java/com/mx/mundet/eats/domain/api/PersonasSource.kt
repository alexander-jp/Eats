package com.mx.mundet.eats.domain.api

import com.mx.mundet.eats.domain.model.PersonResponseBean
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface PersonasSource {

    @GET("/personas")
    @Headers("Content-type: application/json")
    fun getAllPersonas()  : Single<List<PersonResponseBean>>

}