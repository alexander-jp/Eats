package com.mx.mundet.eats.ui.mvp.login

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.mx.mundet.eats.App
import com.mx.mundet.eats.bd.Entity.PersonasEntity
import com.mx.mundet.eats.databinding.ActivityLoginBinding
import com.mx.mundet.eats.domain.model.PersonResponseBean
import com.mx.mundet.eats.domain.repository.LoginRepositoryImpl
import com.mx.mundet.eats.ui.adapter.AdapterListPerson
import com.mx.mundet.eats.ui.base.BaseActivity
import com.mx.mundet.eats.ui.ext.changeActivity
import com.mx.mundet.eats.ui.ext.showToast
import com.mx.mundet.eats.ui.mvp.login.LoginContract
import com.mx.mundet.eats.ui.mvp.registerUser.RegisterUserActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by Alexander Juárez with Date 13/03/2021
 * @author Alexander Juárez
 */

class LoginActivity : BaseActivity(), LoginContract.View {

    private lateinit var _binding: ActivityLoginBinding
    @Inject
    lateinit var lPresenter: LoginContract.Presenter

    private var adapter: AdapterListPerson?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        initProgress(_binding.root)

    }

    override fun onResume() {
        lPresenter.subscribe(this)
        lPresenter.obtenerListaPersonas()
        initListeners()
        super.onResume()
    }


    private fun initListeners() {
        _binding.rvListPerson.setHasFixedSize(true)
        _binding.rvListPerson.layoutManager = LinearLayoutManager(this)

        _binding.fabAddPerson.setOnClickListener {
            changeActivity(RegisterUserActivity::class.java)
        }

    }

    override fun showError(error: Throwable) {
        Log.e(TAG, "showError: ${error.printStackTrace()}")
    }

    override fun resultObtenerListaPersonas(response: List<PersonasEntity>) {
        adapter = AdapterListPerson()
        adapter?.lista = response as ArrayList<PersonasEntity>
        _binding.rvListPerson.adapter = adapter
        showProgress(false)
    }

    override fun resultInsertPerson(response: PersonasEntity) {
        showToast("Se ha registrado correctamente")
    }

    override fun onDestroy() {
        super.onDestroy()
        lPresenter.unSubscribe()
    }

    companion object {
        @JvmStatic
        fun newInstance() = LoginActivity().apply {}

        @JvmStatic
        val TAG = LoginActivity::class.simpleName
    }

}