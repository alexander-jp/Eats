package com.mx.mundet.eats.ui.mvp.fileChooser

import android.os.Build
import android.os.Bundle
import android.os.Environment
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.appbar.MaterialToolbar
import com.mx.mundet.eats.R
import com.mx.mundet.eats.databinding.ActivityFileChooserBinding
import com.mx.mundet.eats.ui.adapter.AdapterListFiles
import com.mx.mundet.eats.ui.base.BaseActivity
import java.io.File

@RequiresApi(Build.VERSION_CODES.O)
class FileChooserActivity : BaseActivity() {
    private val folderFiles by lazy { filesDir }
    private var files: File? = null
    private val foolderRoot by lazy { Environment.getExternalStorageDirectory() }
    private var listItem: ArrayList<DirectoryModel> = arrayListOf()
    private val adapterList by lazy { AdapterListFiles() }
    private var toolbar: MaterialToolbar? = null


    private lateinit var _binding: ActivityFileChooserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFileChooserBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        toolbar = findViewById(R.id.toolbar_main)

        initSettings()
        initListeners()
    }

    private fun initSettings() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Selecciona un archivo"
        toolbar?.title = "Selecciona un archivo"
        toolbar?.setNavigationIcon(R.drawable.ic_round_close_24)
        _binding.rvListFilesPick.setHasFixedSize(true)
        _binding.rvListFilesPick.layoutManager = GridLayoutManager(this, 2)

        files = File(foolderRoot.toURI())
        files?.listFiles()?.map { v ->
            if (v.isFile) {
                val paths = v.path.substring(v.path.lastIndexOf("."))
                ///storage/emulated/0/Android/data/com.mx.mundet.eats.debug/files/pic210408175443.jpg
                if (paths == ".jpeg" || paths == ".png" || paths == ".jpg") {
                    val item = DirectoryModel(dirName = v.name, 1).apply {
                        icon = v.path
                    }
                    listItem.add(item)
                } else {
                if (checkNotNull(v.listFiles()?.isNotEmpty())) {
                    v.listFiles()?.forEach { p ->
                        val count = 1
                        while (p.isFile && count <= 1) {
                            val paths = v.listFiles()?.last()?.path?.substring(
                                v.listFiles()!!.last().path.lastIndexOf(".")
                            )
                            if (paths == ".jpeg" || paths == ".png" || paths == ".jpg") {
                                val data = DirectoryModel(dirName = v.name, 1).apply {
                                    icon = v.listFiles()?.last()?.path
                                    numberCount = v.listFiles()?.size
                                }
                                listItem.add(data)
                            }
                            count.plus(1)
                        }
                    }
                } else {
                    listItem.add(DirectoryModel(dirName = v.name, 0))
                }
            }
        }

        adapterList.items = listItem
        _binding.rvListFilesPick.adapter = adapterList
    }
}

private fun initListeners() {

}


companion object {
    @JvmStatic
    fun newInstance() = FileChooserActivity().apply {

    }

    @JvmStatic
    val TAG = FileChooserActivity::class.simpleName
}
}