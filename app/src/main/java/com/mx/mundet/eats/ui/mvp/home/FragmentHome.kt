package com.mx.mundet.eats.ui.mvp.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.mx.mundet.eats.App
import com.mx.mundet.eats.R
import com.mx.mundet.eats.bd.Entity.PersonasEntity
import com.mx.mundet.eats.databinding.FragmentHomeBinding
import com.mx.mundet.eats.ui.adapter.AdapterListPerson
import com.mx.mundet.eats.ui.base.BaseFragment
import com.mx.mundet.eats.ui.ext.changeActivity
import com.mx.mundet.eats.ui.mvp.registerUser.RegisterUserActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by Alexander Juárez with Date 19/03/2021
 * @author Alexander Juárez
 */

class FragmentHome : BaseFragment(R.layout.fragment_home), HomeContract.View {

    private var _binding: FragmentHomeBinding? = null

    @Inject
    lateinit var hPresenter: HomeContract.Presenter

    private val adapter: AdapterListPerson by lazy { AdapterListPerson() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity?.application as App).appComponent.inject(this)
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        initSettings()
        initListener()

    }

    override fun onResume() {
        hPresenter.subscribe(this)
        CoroutineScope(Dispatchers.IO).launch {
            hPresenter.obtenerListaPersonas()
        }
        super.onResume()
    }

    private fun initSettings(){
        initProgress(_binding?.root)
        setTitleToobar(resources.getString(R.string.text_title_item_home))
        _binding?.rvListHome?.setHasFixedSize(true)
        _binding?.rvListHome?.layoutManager = LinearLayoutManager(activity)
    }

    private fun initListener() {
        _binding?.fabAddPersonHome?.setOnClickListener {
            changeActivity(RegisterUserActivity::class.java)
        }
    }

    override fun showError(error: Throwable) {
        Log.e(TAG, "showError: ${error.localizedMessage}")
    }

    override fun resultObtenerListaPersonas(response: List<PersonasEntity>) {
        adapter.lista = response as ArrayList<PersonasEntity>
        _binding?.rvListHome?.adapter = adapter
        showProgress(false)
    }

    override fun onDestroyView() {
        hPresenter.unSubscribe()
        super.onDestroyView()
    }


    companion object {
        @JvmStatic
        fun newInstance() = FragmentHome().apply {

        }

        @JvmStatic
        val TAG = FragmentHome::class.simpleName
    }
}