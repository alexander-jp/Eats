package com.mx.mundet.eats.ui.mvp.fileChooser

import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.BaseColumns
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.collection.ArraySet
import androidx.collection.arraySetOf
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.appbar.MaterialToolbar
import com.mx.mundet.eats.R
import com.mx.mundet.eats.databinding.ActivityFileChooserBinding
import com.mx.mundet.eats.ui.adapter.AdapterListFiles
import com.mx.mundet.eats.ui.base.BaseActivity
import com.mx.mundet.eats.ui.ext.changeActivity
import com.mx.mundet.eats.ui.interfaces.OnItemClickListener
import com.mx.mundet.eats.ui.message.MsgPathFolderParent
import org.greenrobot.eventbus.EventBus
import java.io.File
import java.util.regex.Pattern

@RequiresApi(Build.VERSION_CODES.Q)
class FileChooserActivity : BaseActivity() {
    private val folderFiles by lazy { filesDir }
    private var files: File? = null
    private val foolderRoot by lazy { Environment.getExternalStorageDirectory() }
    private var listItem: ArrayList<DirectoryModel> = arrayListOf()
    private var arrayItem: Array<DirectoryModel> = arrayOf()
    private var arrayItems: ArraySet<DirectoryModel> = arraySetOf()
    private val adapterList by lazy { AdapterListFiles() }
    private var toolbar: MaterialToolbar? = null
    private var paths: DirectoryModel? = null
    private val uriImages = BaseColumns._COUNT
    private var base_path : String = "/storage/emulated/0/"

    //    private val projection = arrayOf(MediaStore.MediaColumns.DATA)
    private val projection = arrayOf(MediaStore.MediaColumns.DATA)


    private lateinit var _binding: ActivityFileChooserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFileChooserBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        toolbar = findViewById(R.id.toolbar_main)

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
            null
        )
        query?.use { cursor ->
            while (cursor.moveToNext()){
                val absolutePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.RELATIVE_PATH))
                val imagePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA))
                val path_end = imagePath.substringBeforeLast('/')
                arrayItems.add(DirectoryModel(dirName = path_end.substringAfterLast('/'),
                    dirType = 1,
                    pathParent = base_path.plus(absolutePath)
                ).apply {
                    icon = imagePath
                })
                //val f = file.listFiles()?.last()
//                val obs = Observable.just(file.listFiles()?.last())
//                    .distinct {p-> p.path}
//                    .firstElement()
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe({ f->
//                        Log.e(TAG, "getAllImages: OnNEXT: ${file.path}")
//                        arrayItems.add(DirectoryModel(dirName = absolutePath, dirType = 1).apply {
//                            icon = f?.path
//                            count = file.listFiles()?.size
//                        })
//                    }, {
//                        Log.e(TAG, "getAllImages: onError ${it.message}")
//                    }, {
//                        Log.e(TAG, "getAllImages: onComplete")
//                    })
//                var number = 1
//                while(number<=1){
//                    if(checkNotNull(f?.extension== "jpg" || f?.extension=="png" || f?.extension=="jpeg")){
//                        arrayItems.add(DirectoryModel(dirName = absolutePath, dirType = 1).apply {
//                            icon = f?.path
//                            count = file.listFiles()?.size
//                        })
//                        Log.e(TAG, "getAllImages: ${f?.path}")
//                    }
//                    number += 1
//                    Log.e(TAG, "getAllImages: number-> $number")
//                    break
//                }

//                arrayItems.forEach { v->
//                    listItem.add(DirectoryModel(dirName = v.dirName, dirType = v.dirType).apply {
//                        icon = v.icon
//                        count =v.count
//                    })
//                }

//                    Log.e(TAG, "getAllImages: is file ${f?.path}", )
//                    arrayItems.add(DirectoryModel(dirName = f?.name, dirType = 1).apply {
//                            icon = f?.path
//                            count = file.listFiles()?.size
//                        })
//                    arrayItems.forEach { v->
//                        listItem.add(DirectoryModel(dirName = v.dirName, dirType = v.dirType).apply {
//                            icon = v.icon
//                            count =v.count
//                        })
//                    }


//                file.listFiles()?.first { v-> v.isFile }?.listFiles()?.forEach { f->
//                    var number = 1
//                    while (number <= 1 && f.isFile){
//                        arrayItems.add(DirectoryModel(dirName = f.name, dirType = 1).apply {
//                            icon = f.path
//                            count = file.listFiles()?.size
//                        })
//                        number = number.plus(1)
//                    }
//                }
//                arrayItems.forEach {
//                    listItem.add(DirectoryModel(dirName = it.dirName, dirType = it.dirType))
//                }

            }
            cursor.close()
        }

//
//        val obs2 = Observable.just(arrayItem)
//            .map {
//                it.distinctBy { it.dirName}.forEach { v->
//                    paths = v
//                }
//                return@map paths
//            }
//            .distinct {
//            }
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                Log.e(TAG, "onNext : $it")
//            },{
//                Log.e(TAG, "onError : ${it.message}")
//            },{
//                Log.e(TAG, "onComplete")
//            })

        // while (checkNotNull(cursor?.moveToNext())) {
        //   val absolutePath = cursor?.getString(cursor.getColumnIndex(MediaStore.MediaColumns.RELATIVE_PATH))
//            listItem.add(DirectoryModel(dirName = absolutePath, 0))

//            val obs = Observable.just(absolutePath)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                Log.e(TAG, "onNext : $it")
//                //val oso = DirectoryModel(dirName = it, 0)
//                //listItem.add(oso)
//            },{
//                Log.e(TAG, "onError : ${it.message}")
//            }, {
////                    adapterList.items = listItem
////                    _binding.rvListFilesPick.adapter = adapterList
//            })


        // }
        //   cursor?.close()
    }

    private fun initSettings() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Selecciona un archivo"
        toolbar?.title = "Selecciona un archivo"
        toolbar?.setNavigationIcon(R.drawable.ic_round_close_24)
        _binding.rvListFilesPick.setHasFixedSize(true)
        _binding.rvListFilesPick.layoutManager = GridLayoutManager(this, 2)

//        files = File(foolderRoot.toURI())
//        files?.listFiles()?.map { v ->
//            if (v.isFile) {
//                val paths = v.extension
//                ///storage/emulated/0/Android/data/com.mx.mundet.eats.debug/files/pic210408175443.jpg
//                if (paths == "jpeg" || paths == "png" || paths == "jpg") {
//                    val item = DirectoryModel(dirName = v.name, 1).apply {
//                        icon = v.path
//                    }
//                    //listItem.add(item)
//                }
//            } else {
//                if (checkNotNull(v.listFiles()?.isNotEmpty())) {
//                    checkNotNull(v.listFiles()?.distinctBy { v.name }?.forEach { p ->
//                        val paths = p.extension
//                        if (p.isFile) {
//                            Log.e(TAG, "initSettings 0: ${v.listFiles()?.last()?.path}")
//                            Log.e(TAG, "initSettings 0: ${v.listFiles()?.last()?.extension}")
//
//                            if (paths == "jpeg" || paths == "png" || paths == "jpg") {
//                                Log.e(TAG, "initSettings: entro en imagen type : $paths")
//                                val data = DirectoryModel(dirName = v.name, 1).apply {
//                                    icon = p?.path
//                                    count = v.listFiles()?.size
//                                }
////                                listItem.add(data)
//                            }
//                        } else {
////                            listItem.add(DirectoryModel(dirName = p.name, 0))
//                        }
//                    })
////                    if(checkNotNull(v.listFiles()?.last()?.isFile)){
////                        val paths = v.listFiles()?.last()?.path?.substring(v.listFiles()!!.last().path.lastIndexOf("."))
////                        if(paths == ".jpeg" || paths == ".png" || paths == ".jpg"){
////                            val data = DirectoryModel(dirName = v.name, 1).apply {
////                                icon = v.listFiles()?.last()?.path
////                                count = v.listFiles()?.size
////                            }
////                            listItem.add(data)
////                        }
////                    }
////                } else {
////                        listItem.add(DirectoryModel(dirName = v.name, 0))
////                    }
//                }
//            }

            adapterList.items = arrayItems
            _binding.rvListFilesPick.adapter = adapterList
    }

    private fun initListeners() {
        adapterList.onClick = object : OnItemClickListener{
            override fun OnItemClickListener(view: View, position: Int) {
                EventBus.getDefault().postSticky(MsgPathFolderParent(pathParent = checkNotNull(adapterList.items.valueAt(position)?.pathParent)))
                changeActivity(ImageListActivity::class.java)
            }
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = FileChooserActivity().apply {

        }

        @JvmStatic
        val TAG = FileChooserActivity::class.simpleName
    }
}