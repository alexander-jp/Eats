package com.mx.mundet.eats.ui.mvp.fileChooser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Display
import androidx.collection.ArraySet
import androidx.collection.arraySetOf
import androidx.core.view.DisplayCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.mx.mundet.eats.R
import com.mx.mundet.eats.databinding.ActivityImageListBinding
import com.mx.mundet.eats.ui.adapter.AdapterListFiles
import com.mx.mundet.eats.ui.base.BaseActivity
import com.mx.mundet.eats.ui.message.MsgPathFolderParent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.File

class ImageListActivity : BaseActivity() {

    private lateinit var _binding: ActivityImageListBinding
    private val display : Display by lazy { this.windowManager.defaultDisplay }
    private var widthRv : Int ?=null
    private val adapterList by lazy { AdapterListFiles() }
    private var arrayItems: ArraySet<DirectoryModel> = arraySetOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityImageListBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        EventBus.getDefault().register(this)
        initSettings()
    }

    private fun initSettings(){
        widthRv = display.width / 250
        _binding.rvListImages.setHasFixedSize(true)
        _binding.rvListImages.layoutManager = GridLayoutManager(this, widthRv!!)
        adapterList.items = arrayItems
        _binding.rvListImages.adapter = adapterList
    }

    private fun setImageList(file : File){
        adapterList.items.clear()
        file.listFiles()?.forEach { v->
            if(v.isFile){
                when(v.extension){
                    "jpg" -> arrayItems.add(DirectoryModel(dirName = (v.path.substring(v.path.lastIndexOf("/"))).replace("/",""), dirType = 1, pathParent = v.parent).apply { icon = v.path })
                    "png"-> arrayItems.add(DirectoryModel(dirName = (v.path.substring(v.path.lastIndexOf("/"))).replace("/",""), dirType = 1, pathParent = v.parent).apply { icon = v.path })
                    "jpeg"-> arrayItems.add(DirectoryModel(dirName = (v.path.substring(v.path.lastIndexOf("/"))).replace("/",""), pathParent = v.parent).apply { icon = v.path })
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun getImagePathParentFolder(msg: MsgPathFolderParent) {
        setImageList(File(msg.pathParent))
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ImageListActivity().apply {
        }

        @JvmStatic
        val TAG = ImageListActivity::class.simpleName
    }
}