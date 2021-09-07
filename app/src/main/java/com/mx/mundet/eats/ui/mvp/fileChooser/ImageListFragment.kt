package com.mx.mundet.eats.ui.mvp.fileChooser

import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.Display
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import com.google.android.material.appbar.MaterialToolbar
import com.mx.mundet.eats.R
import com.mx.mundet.eats.databinding.FragmentImageListBinding
import com.mx.mundet.eats.ui.adapter.AdapterListFiles
import com.mx.mundet.eats.ui.base.BaseFragment
import com.mx.mundet.eats.ui.interfaces.OnItemClickListener
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.util.*

class ImageListFragment : BaseFragment(R.layout.fragment_image_list) {

    private lateinit var _binding: FragmentImageListBinding
    private val display: Display by lazy { requireActivity().windowManager.defaultDisplay }
    private var widthRv: Int? = null
    private val adapterList by lazy { AdapterListFiles() }
    private val compositeDisposable by lazy { CompositeDisposable() }
    private var model : DirectoryModel ?= null
    private var file: File? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentImageListBinding.bind(view)
        initSettings()
        initListeners()
    }

    private fun initSettings() {
        widthRv = display.width / 300
        _binding.rvListImages.setHasFixedSize(true)
        _binding.rvListImages.layoutManager = GridLayoutManager(activity, widthRv!!)
        _binding.rvListImages.itemAnimator = DefaultItemAnimator()
        adapterList.optionList = "images"
        model = arguments?.getSerializable("images") as DirectoryModel
        file = File(checkNotNull(model?.pathParent))
        setTitleToolbar("${model?.dirName}")
    }

    override fun onResume() {
        setImageList(checkNotNull(file))
        super.onResume()
    }



    private fun initListeners() {
        adapterList.onClick = object : OnItemClickListener {
            override fun OnItemClickListener(view: View, position: Int) {
                val arg = bundleOf("path" to checkNotNull(adapterList.items.valueAt(position)?.icon))
                findNavController().navigate(R.id.action_global_imageFragment, arg)
            }
        }
    }

    private fun setImageList(file: File) {
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


    private fun setTitleImages(path: String): String {
        return path.substring(path.lastIndexOf("/")).replace("/", "")
    }

    private fun setImagesList(v: File) {
        adapterList.addItem(
            DirectoryModel(
                dirName = setTitleImages(v.path),
                dirType = 1,
                pathParent = v.parent
            ).apply {
                icon = v.path
                dateCreated = Date(v.lastModified()).time
            })
        adapterList.notifyItemInserted(adapterList.items.size - 1)
        _binding.rvListImages.adapter = adapterList
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ImageListFragment().apply {
        }

        @JvmStatic
        val TAG = ImageListFragment::class.simpleName
    }
}