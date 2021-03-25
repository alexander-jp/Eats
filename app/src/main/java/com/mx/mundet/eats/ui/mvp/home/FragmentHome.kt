package com.mx.mundet.eats.ui.mvp.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mx.mundet.eats.App
import com.mx.mundet.eats.R
import com.mx.mundet.eats.bd.Entity.PersonasEntity
import com.mx.mundet.eats.databinding.FragmentHomeBinding
import com.mx.mundet.eats.ui.adapter.AdapterListPerson
import com.mx.mundet.eats.ui.base.BaseFragment
import com.mx.mundet.eats.ui.interfaces.OnItemClickListener
import com.mx.mundet.eats.ui.message.MsgUserData
import com.mx.mundet.eats.ui.message.MsgUserDataRefresh
import com.mx.mundet.eats.ui.mvp.detailUser.ActivityDetailUser
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject


/**
 * Created by Alexander Juárez with Date 19/03/2021
 * @author Alexander Juárez
 */

class FragmentHome : BaseFragment(R.layout.fragment_home), HomeContract.View {

    private lateinit var _binding: FragmentHomeBinding

    @Inject
    lateinit var hPresenter: HomeContract.Presenter

    private val adapter: AdapterListPerson by lazy { AdapterListPerson() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity?.application as App).appComponent.inject(this)
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        //EventBus.getDefault().register(this)

        initSettings()
        initListener()

    }

    override fun onResume() {
        hPresenter.subscribe(this)
        hPresenter.obtenerListaPersonas()
        super.onResume()
    }

    private fun initSettings(){
        initProgress(_binding?.root)
        //(activity as MainActivity).supportActionBar?.title = getString(R.string.text_title_item_home)
        //setTitleToobar(resources.getString(R.string.text_title_item_home))
        _binding?.rvListHome?.setHasFixedSize(true)
        _binding?.rvListHome?.layoutManager = LinearLayoutManager(activity)
    }

    private fun initListener() {
        _binding?.fabAddPersonHome?.setOnClickListener {
            findNavController().navigate(R.id.action_global_fragmentHome_to_activityRegisterUser)
        }
        adapter.onClick = object : OnItemClickListener{
            override fun OnItemClickListener(view: View, position: Int) {
                EventBus.getDefault().postSticky(MsgUserData(adapter.lista[position]))
                findNavController().navigate(R.id.action_global_fragmentHome_to_activityDetailUser)
            }
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
        //EventBus.getDefault().unregister(this)
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