package com.mx.mundet.eats.ui.mvp.registerUser

import android.content.Intent
import android.os.Bundle
import android.transition.Fade
import android.util.Log
import android.view.Gravity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.mx.mundet.eats.App
import com.mx.mundet.eats.R
import com.mx.mundet.eats.bd.Entity.PersonasEntity
import com.mx.mundet.eats.databinding.ActivityRegisterUserBinding
import com.mx.mundet.eats.ui.base.BaseActivity
import com.mx.mundet.eats.ui.ext.changeActivity
import com.mx.mundet.eats.ui.ext.showToast
import com.mx.mundet.eats.ui.ext.singleLiveData
import com.mx.mundet.eats.ui.model.RegisterUserModel
import com.mx.mundet.eats.ui.mvp.camera.ActivityCamera
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
        //overridePendingTransition(R.anim.enter_activity, R.anim.exit_activity)
        //setupWindowAnimations()

        initSettings()
        initListeners()

    }

//    private fun setupWindowAnimations() {
//        val fade = Fade(Gravity.LEFT)
//        fade.setDuration(1000)
//        window.enterTransition = fade
//    }

    private fun initSettings(){
//        overridePendingTransition(R.anim.enter_activity, R.anim.exit_activity)
        setSupportActionBar(_binding.toolbarAddUser)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        _binding.toolbarAddUser.title = getString(R.string.text_add_person)
        _binding.toolbarAddUser.setNavigationIcon(R.drawable.ic_round_close_24)
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

        _binding.toolbarAddUser.setNavigationOnClickListener {
            finish()
        }



        _binding.txtEditTextName.doOnTextChanged { text, _, _, count ->
            //_binding.txtEditTextName.validateReactive(_binding.txtInputLayoutDate)
            registerUser.name = _binding.txtEditTextName.text?.trim().toString()


        }

       // _binding.btnSaveUser.isEnabled =

        _binding.btnSaveUser.setOnClickListener {
            val intent = Intent(this, ActivityCamera::class.java)
            //intent.putExtra("nameFile", "nameFile")
            startActivityForResult(intent, CODE_REQUEST_TAKE_PHOTO )
        }
    }

    override fun finish() {
        super.finish()
        //overridePendingTransition(R.anim.enter_activity, R.anim.exit_activity)
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

         @JvmStatic
         val CODE_REQUEST_TAKE_PHOTO = 4
     }
}