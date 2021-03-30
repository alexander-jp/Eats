package com.mx.mundet.eats.ui.mvp.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mx.mundet.eats.App
import com.mx.mundet.eats.bd.Entity.PersonasEntity
import com.mx.mundet.eats.databinding.ActivityRegisterBinding
import com.mx.mundet.eats.ui.base.BaseActivity
import com.mx.mundet.eats.ui.ext.changeActivity
import com.mx.mundet.eats.ui.ext.changeActivityFinish
import com.mx.mundet.eats.ui.ext.showSnackBar
import com.mx.mundet.eats.ui.model.RegisterModel
import com.mx.mundet.eats.ui.mvp.login.LoginActivity
import com.mx.mundet.eats.ui.mvp.main.MainActivity
import com.mx.mundet.eats.utils.InputUtils
import com.mx.mundet.eats.utils.InputUtils.validate
import javax.inject.Inject

class RegisterActivity : BaseActivity(), RegisterContract.View {

    private lateinit var _binding : ActivityRegisterBinding

    private val _registerMutable by lazy { MutableLiveData<RegisterModel>() }
    private val _registerLiveData :LiveData<RegisterModel> = _registerMutable
    private val registerModel by lazy { RegisterModel() }

    @Inject
    lateinit var rPresenter : RegisterContract.Presenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).appComponent.inject(this)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        initSettings()
        initObservers()
        initListeners()
        rPresenter.subscribe(this)
    }

    private fun initSettings(){
        _binding.txtEditTextNameRegister.validate(_binding.txtInputLayoutNameRegister){
            Log.e(TAG, "initSettings: nameUser $it")
            registerModel.name = it
            _registerMutable.value = registerModel
        }
        _binding.txtEditTextApRegister.validate(_binding.txtInputLayoutApRegister){
            registerModel.Ap = it
            _registerMutable.value = registerModel
        }
        _binding.txtEditTextAmRegister.validate(_binding.txtInputLayoutAmRegister){
            registerModel.Am = it
            _registerMutable.value = registerModel
        }
        _binding.txtEditTextEmailRegister.validate(_binding.txtInputLayoutEmailRegister){
            registerModel.email = it
            _registerMutable.value = registerModel
        }
        _binding.txtEditTextPasswordRegister.validate(_binding.txtInputLayoutPasswordRegister){
            registerModel.password = it
            _registerMutable.value = registerModel
        }
    }

    private fun initListeners(){
        _binding.appbarRegister.setNavigationOnClickListener {
            finish()
        }
        _binding.btnRegisterUser.setOnClickListener {
            showDialogProgress(true)
            rPresenter.registerUser(PersonasEntity(nombre = registerModel.name, sexo = "HombreX",
                edad = 33, aMaterno = registerModel.Am, aPaterno = registerModel.Ap, email = registerModel.email,
                password = registerModel.password))
        }
        _binding.btnWhatAccount.setOnClickListener {
            changeActivity(LoginActivity::class.java)
        }
    }

    private fun initObservers(){
        _registerLiveData.observe(this, {
            _binding.btnRegisterUser.isEnabled =
                InputUtils.isValidLetters(it.userName.value.toString()) &&
                        it.userAp.value?.let { it1 -> InputUtils.isValidLetters(it1) } == true &&
                        it.userAm.value?.let { it1 -> InputUtils.isValidLetters(it1) } == true &&
                        InputUtils.isValidEmail(it.userEmail.value.toString()) &&
                        InputUtils.isValidPassword(it.userPassword.value.toString())
        })
    }

    override fun showError(error: Throwable) {
        Log.e(TAG, "showError: ${error.localizedMessage}")
    }

    override fun resultRegisterUser(response: PersonasEntity) {
        showDialogProgress(false)
        showSnackBar(_binding.root, "Se ha registrado correctamente")
        changeActivityFinish(LoginActivity::class.java)
    }

     companion object{
          @JvmStatic
          fun newInstance() = RegisterActivity().apply{

          }

          @JvmStatic
          val TAG = RegisterActivity::class.simpleName
     }
}