package com.mx.mundet.eats.ui.mvp.login

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.mx.mundet.eats.App
import com.mx.mundet.eats.R
import com.mx.mundet.eats.bd.Entity.PersonasEntity
import com.mx.mundet.eats.databinding.ActivityLoginBinding
import com.mx.mundet.eats.ui.adapter.AdapterListPerson
import com.mx.mundet.eats.ui.base.BaseActivity
import com.mx.mundet.eats.ui.base.SessionBean
import com.mx.mundet.eats.ui.ext.*
import com.mx.mundet.eats.ui.model.LoginUserModel
import com.mx.mundet.eats.ui.mvp.main.MainActivity
import com.mx.mundet.eats.ui.mvp.register.RegisterActivity
import com.mx.mundet.eats.ui.mvp.registerUser.RegisterUserActivity
import com.mx.mundet.eats.utils.InputUtils
import com.mx.mundet.eats.utils.InputUtils.validate
import javax.inject.Inject


/**
 * Created by Alexander Juárez with Date 13/03/2021
 * @author Alexander Juárez
 */

class LoginActivity : BaseActivity(), LoginContract.View {

    private lateinit var _binding: ActivityLoginBinding
    @Inject
    lateinit var lPresenter: LoginContract.Presenter

    @Inject
    lateinit var sessionBean: SessionBean

    private val loginUserMutable  by lazy { MutableLiveData<LoginUserModel>() }
    private val _loginUser : LiveData<LoginUserModel>  = loginUserMutable
    private val loginUser by lazy { LoginUserModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(_binding.root)

    }

    override fun onResume() {
        lPresenter.subscribe(this)
        if(sessionBean.config.isLogIn){
            changeActivityFinish(MainActivity::class.java)
        }
        initSettings()
        initObservers()
        initListeners()
        super.onResume()
    }

    private fun initSettings(){
        _binding.txtEditTextEmail.validate(_binding.textInputLayoutEmail){
            loginUser.email = it
            loginUserMutable.value = loginUser
        }

        _binding.txtEditTextPassword.validate(_binding.textInputLayoutPassword){
            loginUser.password = it
            loginUserMutable.value = loginUser
        }
    }

    private fun initObservers(){
        _loginUser.observe(this, {
            _binding.btnIngresar.isEnabled = InputUtils.isValidEmail(it.emailUser.value.toString()) &&
                                             InputUtils.isValidPassword(it.passwordUser.value.toString())
        })
    }

    private fun initListeners() {
        _binding.btnIngresar.setOnClickListener {
            showDialogProgress(true)
            lPresenter.loginUser(loginUser.email!!, loginUser.password!!)
        }

        _binding.appBarLogin.setNavigationOnClickListener {
            finish()
        }
        _binding.tvForgotPassword.setOnClickListener {
            showSnackBar(_binding.root, "Forgot password")
        }
        _binding.tvNotAccount.setOnClickListener {
            changeActivity(RegisterActivity::class.java)
        }
    }

    override fun showError(error: Throwable) {
        showDialogProgress(false)
        Log.e(TAG, "showError: ${error.printStackTrace()}")
    }

    override fun resultLoginUser(response: Boolean) {
        showDialogProgress(false)
        if(response){
            sessionBean.config.isLogIn = true
            sessionBean.save()
            changeActivityFinish(MainActivity::class.java)
        }else{
            showSnackBar(_binding.root, "Usuario y/o contraseña incorrecta")
        }
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