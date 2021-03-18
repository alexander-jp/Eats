package com.mx.mundet.eats.ui.mvp.login

import android.os.Bundle
import android.util.Log
import com.mx.mundet.eats.App
import com.mx.mundet.eats.databinding.LoginActivityBinding
import com.mx.mundet.eats.domain.model.PersonResponseBean
import com.mx.mundet.eats.domain.repository.LoginRepositoryImpl
import com.mx.mundet.eats.ui.base.BaseActivity
import com.mx.mundet.eats.ui.ext.showToast
import com.mx.mundet.eats.ui.mvp.login.LoginContract
import javax.inject.Inject


/**
 * Created by Alexander Juárez with Date 13/03/2021
 * @author Alexander Juárez
 */

class LoginActivity : BaseActivity(), LoginContractPrueba.View{

    private lateinit var _binding: LoginActivityBinding

//    @Inject
//    lateinit var lView: LoginContract.View
//
    @Inject
    lateinit var lPresenter: LoginContractPrueba.Presenter
    //var lPresenter : LoginContractPrueba.Presenter?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.loginComponent().create().inject(this)
        super.onCreate(savedInstanceState)
        _binding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        initProgress(_binding.root)
        //initContract()
        //lPresenter = LoginPresenter(LoginRepositoryImpl())
        lPresenter.setView(this)
        lPresenter.obtenerShowToast()

    }

//    private fun initContract() {
//        lView = object : LoginContract.View {
//            override val presenter: LoginContract.Presenter = lPresenter
//
//            override fun showError(error: Throwable) {
//                Log.e("TAG", "showError: ${error.printStackTrace()}")
//            }
//
//            override fun resultObtenerListaPersonas(response: List<PersonResponseBean>) {
//                showProgress(false)
//            }
//        }
//        lPresenter.subscribe(lView)
//    }

    override fun onDestroy() {
        super.onDestroy()
        //lPresenter?.unSubscribe()
    }

    override fun onResume() {
        super.onResume()

//        GlobalScope.launch(Dispatchers.Main) {
//            lPresenter?.obtenerListaPersonas()
//            showProgress(true)
//        }
    }

    override fun resultShowToast(msg: String) {
        showToast(msg)
    }


}