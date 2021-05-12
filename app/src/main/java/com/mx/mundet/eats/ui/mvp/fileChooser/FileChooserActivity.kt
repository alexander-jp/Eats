package com.mx.mundet.eats.ui.mvp.fileChooser

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.collection.ArraySet
import androidx.collection.arraySetOf
import androidx.core.os.EnvironmentCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.appbar.MaterialToolbar
import com.mx.mundet.eats.R
import com.mx.mundet.eats.databinding.ActivityFileChooserBinding
import com.mx.mundet.eats.ui.adapter.AdapterListFiles
import com.mx.mundet.eats.ui.base.BaseActivity
import com.mx.mundet.eats.ui.ext.changeActivityAnimationSlideBottomExplode
import com.mx.mundet.eats.ui.ext.showSnackBar
import com.mx.mundet.eats.ui.interfaces.OnItemClickListener
import com.mx.mundet.eats.ui.message.MsgPathFolderParent
import com.mx.mundet.eats.ui.mvp.camera.CameraActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus

@RequiresApi(Build.VERSION_CODES.Q)
class FileChooserActivity : BaseActivity() {
    private var arrayItems: ArraySet<DirectoryModel> = arraySetOf()
    private val adapterList by lazy { AdapterListFiles() }
    private var base_path : String = "/storage/emulated/0/"
    private val base_paths by lazy { Environment.getExternalStorageDirectory()}
    private val toolbar by lazy { findViewById<MaterialToolbar>(R.id.toolbar_main) }
    private var paths : String?=null

    private lateinit var _binding: ActivityFileChooserBinding
    private val compositeDisposable by lazy { CompositeDisposable() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFileChooserBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        Log.e(TAG, "onCreate: $base_paths")
        checkPermissions()

        initSettings()
        initListeners()
        getAllImages()
    }



    private fun getAllImages() {


        val query = application.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            "${MediaStore.Images.Media.DATE_TAKEN} DESC"
        )


        query?.use { cursor ->

//            compositeDisposable.add(
//                Observable.just(cursor)
//                    .map {
//                        while (cursor.moveToNext()){
//                            paths = it.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME))
//                        }
//                        return@map paths
//                    }
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe({
//                        Log.e(TAG, "getAllImages: folder name $it")
//                    }, {
//                        Log.e(TAG, "getAllImages: ${it.printStackTrace()}")
//                    }, {
//                        Log.e(TAG, "getAllImages: onComplete")
//                    })
//            )


            while (cursor.moveToNext()){


                val absolutePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.RELATIVE_PATH))
                val imagePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATA))
                val path_end = imagePath.substringBeforeLast('/')
                adapterList.addItem(DirectoryModel(dirName = path_end.substringAfterLast('/'),
                    dirType = 1,
                    pathParent = base_path.plus(absolutePath)
                ).apply {
                    icon = imagePath
                })
                adapterList.notifyItemInserted(adapterList.items.size-1)
                Log.e(TAG, "getAllImages: ${adapterList.items.size-1}")
                _binding.rvListFilesPick.adapter = adapterList
            }
            cursor.close()
        }

    }

    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ), RC_PERMISOS
                )

            } else {
                //TODO permiso concedido
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            when (requestCode) {
                RC_PERMISOS -> print("Permiso concedido") //TODO Permiso concedido
            }
        } else {
            finish()
        }
    }


    private fun initSettings() {

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Elige una imagen"
        _binding.rvListFilesPick.setHasFixedSize(true)
        _binding.rvListFilesPick.layoutManager = GridLayoutManager(this, 2)
        _binding.rvListFilesPick.itemAnimator = DefaultItemAnimator()
        adapterList.optionList = "folder"
    }

    private fun initListeners() {
        adapterList.onClick = object : OnItemClickListener{
            override fun OnItemClickListener(view: View, position: Int) {
                EventBus.getDefault().postSticky(MsgPathFolderParent(pathParent = checkNotNull(adapterList.items.valueAt(position)?.pathParent),
                    title = checkNotNull(adapterList.items.valueAt(position)?.dirName)))
                changeActivityAnimationSlideBottomExplode(ImageListActivity::class.java)
            }
        }
        toolbar.setNavigationOnClickListener { finish() }
    }


    companion object {
        @JvmStatic
        fun newInstance() = FileChooserActivity().apply {
        }

        @JvmStatic
        val TAG = FileChooserActivity::class.simpleName

        @JvmStatic
        private val RC_PERMISOS = 203
        val EXTRA_NAME_FILE = "nameFile"
    }
}