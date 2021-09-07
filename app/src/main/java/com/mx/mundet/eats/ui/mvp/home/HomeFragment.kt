package com.mx.mundet.eats.ui.mvp.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.core.os.HandlerCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.mx.mundet.eats.App
import com.mx.mundet.eats.R
import com.mx.mundet.eats.bd.Entity.PersonasEntity
import com.mx.mundet.eats.databinding.FragmentHomeBinding
import com.mx.mundet.eats.ui.adapter.AdapterListPerson
import com.mx.mundet.eats.ui.base.BaseFragment
import com.mx.mundet.eats.ui.ext.showSnackBar
import com.mx.mundet.eats.ui.ext.showToast
import com.mx.mundet.eats.ui.interfaces.OnItemClickListener
import com.mx.mundet.eats.ui.message.MsgUserData
import com.mx.mundet.eats.ui.message.MsgUserDataRefresh
import com.mx.mundet.eats.ui.mvp.main.MainActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject


/**
 * Created by Alexander Juárez with Date 19/03/2021
 * @author Alexander Juárez
 */

class HomeFragment : BaseFragment(R.layout.fragment_home), HomeContract.View {

    private lateinit var _binding: FragmentHomeBinding

    @Inject
    lateinit var hPresenter: HomeContract.Presenter

    private val adapter: AdapterListPerson by lazy { AdapterListPerson() }
    val actionMode : ActionMode by lazy { createActionMode()}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity?.application as App).appComponent.inject(this)
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        EventBus.getDefault().register(this)
        //setHasOptionsMenu(true)

        initSettings()
        initListener()
        hPresenter.subscribe(this)
        hPresenter.obtenerListaPersonas()
    }

    private fun initSettings(){
        initProgress(_binding.root)
        _binding.rvListHome.setHasFixedSize(true)
        _binding.rvListHome.layoutManager = LinearLayoutManager(requireContext())
        _binding.rvListHome.itemAnimator = DefaultItemAnimator()
    }

    private fun initListener() {
        _binding.fabAddPersonHome.setOnClickListener {
            findNavController().navigate(R.id.action_global_activityRegisterUser)
        }
        adapter.onClick = object : OnItemClickListener {
            override fun OnItemClickListener(view: View, position: Int) {
                EventBus.getDefault().postSticky(MsgUserData(adapter.lista[position]))
                findNavController().navigate(R.id.action_global_activityDetailUser)
            }
        }
        adapter.longClick = object : OnItemClickListener {
            override fun OnItemClickListener(view: View, position: Int) {
                createActionMode()
            }
        }
    }


    private fun createActionMode () : ActionMode {
        return (activity as AppCompatActivity).startSupportActionMode(object : ActionMode.Callback {
            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                (activity as AppCompatActivity).supportActionBar?.hide()
                (activity as AppCompatActivity).menuInflater.inflate(R.menu.menu_item_action_mode_home, menu)
                return true
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean = false

            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                when(item?.itemId){
                    R.id.delete_item_home -> showToast("Delete all")
                    R.id.update_item_home -> showToast("Update file")
                }
               return true
            }

            @SuppressLint("RestrictedApi")
            override fun onDestroyActionMode(mode: ActionMode?) {
                Handler().postDelayed({
                    if(checkNotNull(mode?.menu?.hasVisibleItems())){
                        (activity as AppCompatActivity).supportActionBar?.show()
                    }
                }, 300)
                mode?.finish()
            }

        })!!
    }

    override fun showError(error: Throwable) {
        showProgress(false)
        showSnackBar(error.message)
    }

    override fun resultObtenerListaPersonas(response: List<PersonasEntity>) {
        adapter.lista.clear()
        adapter.lista.addAll(response)
        adapter.notifyItemInserted(adapter.lista.size-1)
        _binding.rvListHome.adapter = adapter
        showProgress(false)
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun msgRefreshData(msg : MsgUserDataRefresh){
        hPresenter.obtenerListaPersonas()
    }

    override fun onDestroyView() {
        hPresenter.unSubscribe()
        showProgress(false)
        EventBus.getDefault().unregister(this)
        super.onDestroyView()
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment().apply {

        }

        @JvmStatic
        val TAG = HomeFragment::class.simpleName
    }
}