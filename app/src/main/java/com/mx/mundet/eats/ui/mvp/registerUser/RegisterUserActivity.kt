package com.mx.mundet.eats.ui.mvp.registerUser

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Camera
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import coil.clear
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mx.mundet.eats.App
import com.mx.mundet.eats.R
import com.mx.mundet.eats.bd.Entity.PersonasEntity
import com.mx.mundet.eats.databinding.ActivityRegisterUserBinding
import com.mx.mundet.eats.ui.base.BaseActivity
import com.mx.mundet.eats.ui.dialog.DialogMaterialCalendar
import com.mx.mundet.eats.ui.dialog.DialogMaterialTimer
import com.mx.mundet.eats.ui.ext.addAlertFragment
import com.mx.mundet.eats.ui.ext.addDropdownAdapter
import com.mx.mundet.eats.ui.ext.changeActivity
import com.mx.mundet.eats.ui.ext.showSnackBar
import com.mx.mundet.eats.ui.message.MsgImageUser
import com.mx.mundet.eats.ui.message.MsgUserDataRefresh
import com.mx.mundet.eats.ui.model.MetodoPagoModel
import com.mx.mundet.eats.ui.model.RegisterUserModel
import com.mx.mundet.eats.ui.mvp.camera.CameraActivity
import com.mx.mundet.eats.ui.mvp.fileChooser.FileChooserActivity
import com.mx.mundet.eats.utils.InputUtils
import com.mx.mundet.eats.utils.InputUtils.validate
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.File
import javax.inject.Inject

class RegisterUserActivity : BaseActivity(), RegisterUserContract.View {
    private lateinit var _binding : ActivityRegisterUserBinding
    private var data : RegisterUserModel ?=null

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
        EventBus.getDefault().register(this)
        initSettings()
        initObservers()
        initListeners()

    }

    private fun initSettings(){
        setSupportActionBar(_binding.toolbarAddUser)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        _binding.toolbarAddUser.title = getString(R.string.text_add_person)
//        _binding.imgAccountUser.load(R.drawable.ic_outline_account_circle_24){
//            transformations(CircleCropTransformation())
//        }
        _binding.toolbarAddUser.setNavigationIcon(R.drawable.ic_round_close_24)
        addDropdownAdapter(items = MetodoPagoModel().listData(), view = _binding.txtInputLayoutSexo.editText){

        }
    }

    override fun onResume() {
        rPresenter.subscribe(this)
        if(data!=null){
            _registerUserMutable.value = data
        }
        super.onResume()
    }

    override fun onPause() {
        data = registerUser
        super.onPause()
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
            //changeActivity(CameraActivity::class.java)
            showBottomSheetDialog()
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
            showDialogProgress(true)
            rPresenter.insertPerson(PersonasEntity(nombre = registerUser.name, edad = 23, sexo = "Hombre"))
        }

        _binding.txtInputLayoutDate.setEndIconOnClickListener { showDatePicker(View(this)) }

        _binding.txtInputLayoutTime.setEndIconOnClickListener {showTimePicker(View(this)) }
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

    @SuppressLint("InflateParams")
    private fun showBottomSheetDialog(){
        val sheet = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.nav_sheet_images, null)
        sheet.setContentView(view)
        val btnTakePhoto = view.findViewById<AppCompatImageButton>(R.id.btnTakePhoto)
        val btnPickImage = view.findViewById<AppCompatImageButton>(R.id.btnPickImage)
        btnTakePhoto.setOnClickListener {
            takePhotoCamera()
            sheet.dismiss()
        }
        btnPickImage.setOnClickListener {
            pickPhotoGallery()
            sheet.dismiss()
        }
        sheet.dismissWithAnimation = true
        sheet.show()
    }

    private fun takePhotoCamera(){
        val i = Intent(this, CameraActivity::class.java)
        i.putExtra(CameraActivity.EXTRA_NAME_FILE, "")
        startActivityForResult(i, CODE_REQUEST_TAKE_PHOTO)
    }

    private fun pickPhotoGallery(){
        val i = Intent(this, FileChooserActivity::class.java)
        i.putExtra(CameraActivity.EXTRA_NAME_FILE, "")
        startActivityForResult(i, CODE_REQUEST_PICK_PHOTO)
    }

    override fun showError(error: Throwable) {
        showSnackBar(_binding.root, error.message)
    }

    override fun resultInsertPerson(response: PersonasEntity) {
        EventBus.getDefault().postSticky(MsgUserDataRefresh())
        showDialogProgress(false)
        showSnackBar(_binding.root, "Se ha registrado correctamente")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.e(TAG, "onActivityResult: resultCode $resultCode")
        when(requestCode){
            CODE_REQUEST_PICK_PHOTO -> {
                Log.e(TAG, "onActivityResult PICK: ${data?.data?.path}")
            }
            CODE_REQUEST_TAKE_PHOTO -> {
                    Log.e(TAG, "onActivityResult: TAKE ${data?.data?.path}")
            }
            else-> Log.e(TAG, "onActivityResult: else")
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun setImageUser(msg : MsgImageUser){
        _binding.imgAccountUser.load(msg.img){
            transformations(CircleCropTransformation())
        }
    }

    override fun onDestroy() {
        rPresenter.unSubscribe()
        EventBus.getDefault().unregister(this)
        super.onDestroy()
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

         @JvmStatic
         val CODE_REQUEST_PICK_PHOTO = 5
     }

}