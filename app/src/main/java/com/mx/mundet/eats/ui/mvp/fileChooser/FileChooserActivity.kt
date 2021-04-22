package com.mx.mundet.eats.ui.mvp.fileChooser

import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
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
import com.mx.mundet.eats.ui.ext.changeActivityAnimationSlideBottomExplode
import com.mx.mundet.eats.ui.interfaces.OnItemClickListener
import com.mx.mundet.eats.ui.message.MsgPathFolderParent
import org.greenrobot.eventbus.EventBus

@RequiresApi(Build.VERSION_CODES.Q)
class FileChooserActivity : BaseActivity() {
    private var arrayItems: ArraySet<DirectoryModel> = arraySetOf()
    private val adapterList by lazy { AdapterListFiles() }
    private var base_path : String = "/storage/emulated/0/"
    private val toolbar by lazy { findViewById<MaterialToolbar>(R.id.toolbar_main) }

    private lateinit var _binding: ActivityFileChooserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFileChooserBinding.inflate(layoutInflater)
        setContentView(_binding.root)

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
            }
            cursor.close()
        }

    }

    private fun initSettings() {

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Selecciona una imagen"
        toolbar?.setNavigationIcon(R.drawable.ic_round_close_24)
        _binding.rvListFilesPick.setHasFixedSize(true)
        _binding.rvListFilesPick.layoutManager = GridLayoutManager(this, 2)
        adapterList.optionList = "folder"
        adapterList.items = arrayItems
        _binding.rvListFilesPick.adapter = adapterList
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
    }
}