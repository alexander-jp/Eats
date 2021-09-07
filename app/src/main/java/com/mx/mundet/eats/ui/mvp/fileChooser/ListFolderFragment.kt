package com.mx.mundet.eats.ui.mvp.fileChooser

import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.mx.mundet.eats.R
import com.mx.mundet.eats.databinding.FragmentListFolderBinding
import com.mx.mundet.eats.ui.adapter.AdapterListFiles
import com.mx.mundet.eats.ui.base.BaseFragment
import com.mx.mundet.eats.ui.interfaces.OnItemClickListener
import java.io.Serializable

@RequiresApi(Build.VERSION_CODES.Q)
class ListFolderFragment : BaseFragment(R.layout.fragment_list_folder) {
    private val adapterList by lazy { AdapterListFiles() }
    private var basePath : String = "/storage/emulated/0/"

    private lateinit var _binding: FragmentListFolderBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentListFolderBinding.bind(view)

        initSettings()
        initListeners()
        getAllImages()

    }


    private fun getAllImages() {

        val query = requireActivity().contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            "${MediaStore.Images.Media.DATE_TAKEN} DESC"
        )

        query?.use { cursor ->

            while (cursor.moveToNext()){

                val absolutePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.RELATIVE_PATH))
                val imagePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATA))
                val path_end = imagePath.substringBeforeLast('/')
                adapterList.addItem(DirectoryModel(dirName = path_end.substringAfterLast('/'),
                    dirType = 1,
                    pathParent = basePath.plus(absolutePath)
                ).apply {
                    icon = imagePath
                })
                adapterList.notifyItemInserted(adapterList.items.size-1)
                _binding.rvListFilesPick.adapter = adapterList
            }
            cursor.close()
        }

    }


    private fun initSettings() {
        setTitleToolbar("Elige una imagen")
        _binding.rvListFilesPick.setHasFixedSize(true)
        _binding.rvListFilesPick.layoutManager = GridLayoutManager(requireActivity(), 2)
        _binding.rvListFilesPick.itemAnimator = DefaultItemAnimator()
        adapterList.optionList = "folder"
    }

    private fun initListeners() {
        adapterList.onClick = object : OnItemClickListener {
            override fun OnItemClickListener(view: View, position: Int) {
                val arg = bundleOf("images" to  adapterList.items.valueAt(position))
                findNavController().navigate(R.id.action_global_imageListFragment, arg)
            }
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = ListFolderFragment().apply {
        }

        @JvmStatic
        val TAG = ListFolderFragment::class.simpleName

    }
}