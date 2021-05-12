package com.mx.mundet.eats.presenter

import com.mx.mundet.eats.domain.repository.UserRepository
import com.mx.mundet.eats.domain.repository.UserRepositoryImpl
import com.mx.mundet.eats.ui.mvp.home.HomeContract
import com.mx.mundet.eats.ui.mvp.home.HomeFragment
import com.mx.mundet.eats.ui.mvp.home.HomePresenter
import org.junit.Before
import org.junit.BeforeClass
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

/**
 * Created by Alexander Juárez with Date 25/04/2021
 * @author Alexander Juárez
 */

class HomePresenterTest {

    var presenter : HomePresenter?=null
    private var viewMock : HomeContract.View?=null
    private var repoMock : UserRepository?=null

//    @Before
//    fun init(){
//        viewMock = mock(HomeFragment::class.java)
//        repoMock = mock(UserRepositoryImpl::class.java)
//        `when`(repoMock?.getPeople(1)).thenReturn()
//    }

}