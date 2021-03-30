package com.mx.mundet.eats.ui.mvp.registerUser

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import coil.load
import coil.transform.CircleCropTransformation
import com.mx.mundet.eats.App
import com.mx.mundet.eats.R
import com.mx.mundet.eats.bd.Entity.PersonasEntity
import com.mx.mundet.eats.databinding.ActivityRegisterUserBinding
import com.mx.mundet.eats.ui.base.BaseActivity
import com.mx.mundet.eats.ui.dialog.DialogMaterialCalendar
import com.mx.mundet.eats.ui.dialog.DialogMaterialTimer
import com.mx.mundet.eats.ui.ext.addAlertFragment
import com.mx.mundet.eats.ui.ext.showSnackBar
import com.mx.mundet.eats.ui.model.RegisterUserModel
import com.mx.mundet.eats.ui.mvp.camera.CameraActivity
import com.mx.mundet.eats.utils.InputUtils
import com.mx.mundet.eats.utils.InputUtils.validate
import javax.inject.Inject

class RegisterUserActivity : BaseActivity(), RegisterUserContract.View {
    private lateinit var _binding : ActivityRegisterUserBinding

    @Inject
    lateinit var rPresenter : RegisterUserContract.Presenter

    private val _registerUserMutable : MutableLiveData<RegisterUserModel> by lazy { MutableLiveData<RegisterUserModel>() }
    private val registerUserLiveData: LiveData<RegisterUserModel> = _registerUserMutable
    private val registerUser by lazy { RegisterUserModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterUserBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        initSettings()
        initObservers()
        initListeners()

    }

    private fun initSettings(){
        setSupportActionBar(_binding.toolbarAddUser)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        _binding.toolbarAddUser.title = getString(R.string.text_add_person)
        _binding.imgAccountUser.load(R.drawable.cuenta){
            transformations(CircleCropTransformation())
        }
        _binding.toolbarAddUser.setNavigationIcon(R.drawable.ic_round_close_24)
        val items = listOf("Masculino", "Femenino")
        val adapter = ArrayAdapter(this, R.layout.item_list_sexo, items)
        (_binding.txtInputLayoutSexo.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    override fun onResume() {
        rPresenter.subscribe(this)
        super.onResume()
    }

    private fun initObservers(){
        registerUserLiveData.observe(this, {

            _binding.btnSaveUser.isEnabled = InputUtils.isValidEmail(it.userName.value.toString()) &&
                    InputUtils.isValidLettersTextArea(it.userDescription.value.toString()) &&
                    InputUtils.isValidLetters(it.userDateBirth.value.toString()) &&
                    InputUtils.isValidLetters(it.userTimer.value.toString())
        })
    }

    private fun initListeners(){

        _binding.fabAddImageUser.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }

        _binding.toolbarAddUser.setNavigationOnClickListener {
            finish()
        }

        _binding.txtEditTextName.validate(_binding.txtInputLayoutName, validate = {
            registerUser.name = it
            _registerUserMutable.value = registerUser
        })

        _binding.txtEditTextDescripcion.validate(_binding.txtInputLayoutDescripcion, validate = {
            registerUser.description = it
            _registerUserMutable.value = registerUser
        })

        _binding.btnSaveUser.setOnClickListener {
            rPresenter.insertPerson(PersonasEntity(nombre = registerUser.name, edad = 23, sexo = "Hombre"))
            showDialogProgress(true)
            //intent.putExtra("nameFile", "nameFile")
            //startActivityForResult(intent, CODE_REQUEST_TAKE_PHOTO )
        }

        _binding.txtInputLayoutDate.setEndIconOnClickListener {
            showDatePicker(View(this))
        }

        _binding.txtInputLayoutTime.setEndIconOnClickListener {
            showTimePicker(View(this))
        }
    }

    fun showDatePicker(view: View){

        val calendarAlert = DialogMaterialCalendar.newInstance().onCreateDialog()
        calendarAlert.addOnPositiveButtonClickListener {
            registerUser.dateBirth = calendarAlert.headerText
            _registerUserMutable.value = registerUser
            _binding.txtEditTextDateUser.setText(registerUser.dateBirth)
        }
        addAlertFragment(calendarAlert, DialogMaterialCalendar.TAG!!)
    }

    fun showTimePicker(view: View){
        val timerAlert = DialogMaterialTimer.newInstance().onCreateDialog()
        timerAlert.addOnPositiveButtonClickListener {
            registerUser.timerBirth = "${timerAlert.hour}:${timerAlert.minute}"
            _registerUserMutable.value =  registerUser
            _binding.txtEditTextTimeUser.setText(registerUser.timerBirth)
        }
        addAlertFragment(timerAlert, DialogMaterialTimer.TAG!!)
    }

    override fun showError(error: Throwable) {
        Log.e(TAG, "showError: ${error.printStackTrace()}")
    }

    override fun resultInsertPerson(response: PersonasEntity) {
        showDialogProgress(false)
        showSnackBar(_binding.root, "Se ha registrado correctamente")
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu_item_add_user, menu)
//        menu?.get(0)?.isEnabled = false
//        return super.onCreateOptionsMenu(menu)
//    }
//
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item.itemId){
//            R.id.item_add_user -> {
//                showToast("Add user")
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }

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