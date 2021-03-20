package com.mx.mundet.eats.ui.mvp.registerUser

import android.os.Bundle
import android.util.Log
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.mx.mundet.eats.App
import com.mx.mundet.eats.bd.Entity.PersonasEntity
import com.mx.mundet.eats.databinding.ActivityRegisterUserBinding
import com.mx.mundet.eats.ui.base.BaseActivity
import com.mx.mundet.eats.ui.ext.observeSingle
import com.mx.mundet.eats.ui.ext.showToast
import com.mx.mundet.eats.ui.ext.singleLiveData
import com.mx.mundet.eats.ui.model.RegisterUserModel
import javax.inject.Inject

class RegisterUserActivity : BaseActivity(), RegisterUserContract.View {
    private lateinit var _binding : ActivityRegisterUserBinding

    @Inject
    lateinit var rPresenter : RegisterUserContract.Presenter

    private val _registerUserMutable = singleLiveData<RegisterUserModel>()
    val registerUserLiveData: LiveData<RegisterUserModel> = _registerUserMutable
    val registerUser by lazy { RegisterUserModel() }
    val valid: MediatorLiveData<Boolean> by lazy { MediatorLiveData<Boolean>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterUserBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        initListeners()

    }
    override fun onResume() {
        rPresenter.subscribe(this)
        super.onResume()
    }

    init {
        valid.apply {
            addSource(registerUser.userName) { _registerUserMutable.value = registerUser}
        }
    }


    private fun initListeners(){

        registerUserLiveData.observe(this) {
            Log.e(TAG, "initListeners: ${it}")
        }



        _binding.txtEditTextName.doOnTextChanged { text, _, _, count ->
            //_binding.txtEditTextName.validateReactive(_binding.txtInputLayoutDate)
            registerUser.name = _binding.txtEditTextName.text?.trim().toString()


        }

       // _binding.btnSaveUser.isEnabled =

        _binding.btnSaveUser.setOnClickListener {

        }
    }

    override fun showError(error: Throwable) {
        Log.e(TAG, "showError: ${error.printStackTrace()}")
    }

    override fun resultInsertPerson(response: PersonasEntity) {
        showToast("Se ha registrado correctamente")
    }

     companion object{
          @JvmStatic
          fun newInstance() = RegisterUserActivity().apply{

          }

          @JvmStatic
          val TAG = RegisterUserActivity::class.simpleName
     }
}