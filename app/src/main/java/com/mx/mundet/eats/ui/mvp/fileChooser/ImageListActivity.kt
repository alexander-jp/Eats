package com.mx.mundet.eats.ui.mvp.fileChooser

import android.annotation.SuppressLint
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.Display
import android.view.MenuItem
import android.view.View
import androidx.collection.ArraySet
import androidx.collection.arraySetOf
import androidx.recyclerview.widget.*
import com.google.android.material.appbar.MaterialToolbar
import com.mx.mundet.eats.R
import com.mx.mundet.eats.databinding.ActivityImageListBinding
import com.mx.mundet.eats.ui.adapter.AdapterListFiles
import com.mx.mundet.eats.ui.base.BaseActivity
import com.mx.mundet.eats.ui.ext.changeActivity
import com.mx.mundet.eats.ui.ext.changeActivityFinish
import com.mx.mundet.eats.ui.interfaces.OnItemClickListener
import com.mx.mundet.eats.ui.message.MsgPathFolderParent
import com.mx.mundet.eats.ui.message.MsgPathImage
import com.mx.mundet.eats.ui.mvp.camera.CameraActivity
import com.mx.mundet.eats.ui.mvp.camera.ImageActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.File
import java.util.*

class ImageListActivity : BaseActivity() {

    private lateinit var _binding: ActivityImageListBinding
    private val display : Display by lazy { this.windowManager.defaultDisplay }
    private var widthRv : Int ?=null
    private val adapterList by lazy { AdapterListFiles() }
    private val toolbar by lazy { findViewById<MaterialToolbar>(R.id.toolbar_main) }
    private val compositeDisposable by lazy { CompositeDisposable() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityImageListBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        EventBus.getDefault().register(this)
        initSettings()
        initListeners()
    }

    private fun initSettings(){
        widthRv = display.width / 300
        _binding.rvListImages.setHasFixedSize(true)
        _binding.rvListImages.layoutManager = GridLayoutManager(this, widthRv!!)
        //_binding.rvListImages.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL))
        _binding.rvListImages.itemAnimator = DefaultItemAnimator()
        adapterList.optionList = "images"
//        arrayItems.sortedByDescending { it.dateCreated }
    }

    private fun setupToolbar(title : String){
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = title
    }

    private fun initListeners(){
        adapterList.onClick = object : OnItemClickListener{
            override fun OnItemClickListener(view: View, position: Int) {
                EventBus.getDefault().postSticky(MsgPathImage(path = checkNotNull(adapterList.items.valueAt(position)?.icon)))
                changeActivity(ImageActivity::class.java)
            }
        }
    }

    private fun setImageList(file : File){
        adapterList.items.clear()
        compositeDisposable.add(
            Observable.just(file.listFiles())
                .concatMapIterable {
                    return@concatMapIterable it.asList()
                }
                .filter { t -> t.isFile }
                .filter { t ->
                            t.extension.equals("jpg", true) ||
                            t.extension.equals("png", true) ||
                            t.extension.equals("jpeg", true)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ v ->
                    setImagesList(v)
                }, {
                    Log.e(TAG, "setImageList: ${it.printStackTrace()}")
                })
        )
    }


    private fun setTitleImages(path : String) : String {
        return path.substring(path.lastIndexOf("/")).replace("/","")
    }

    private fun setImagesList(v :File){
        adapterList.addItem(DirectoryModel(dirName = setTitleImages(v.path), dirType = 1, pathParent = v.parent).apply {
            icon = v.path
            dateCreated = Date(v.lastModified()).time
        })
        adapterList.notifyItemInserted(adapterList.items.size-1)
        Log.e(TAG, "setImagesList: ${adapterList.items.size-1}")
        _binding.rvListImages.adapter = adapterList
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home-> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun getImagePathParentFolder(msg: MsgPathFolderParent) {
        setupToolbar(msg.title)
        setImageList(File(msg.pathParent))
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
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