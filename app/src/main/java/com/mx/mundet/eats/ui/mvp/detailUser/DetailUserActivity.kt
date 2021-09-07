package com.mx.mundet.eats.ui.mvp.detailUser

import android.os.Bundle
import android.util.Log
import com.mx.mundet.eats.App
import com.mx.mundet.eats.bd.Entity.PersonasEntity
import com.mx.mundet.eats.databinding.ActivityDetailUserBinding
import com.mx.mundet.eats.ui.base.BaseActivity
import com.mx.mundet.eats.ui.ext.showSnackBar
import com.mx.mundet.eats.ui.ext.showToast
import com.mx.mundet.eats.ui.ext.showToastSuccess
import com.mx.mundet.eats.ui.message.MsgUserData
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

/**
 * Created by Alexander Juárez with Date 21/03/2021
 * @author Alexander Juárez
 */

class DetailUserActivity : BaseActivity(), DetailUserContract.View {

    private lateinit var _binding: ActivityDetailUserBinding
    private var people : PersonasEntity?=null

    @Inject
    lateinit var dPresenter : DetailUserContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        EventBus.getDefault().register(this)
        initSettings()
        initListeners()
    }

    override fun onResume() {
        dPresenter.subscribe(this)
        people?.id?.let {
            dPresenter.getPeople(it)
        }
        super.onResume()
    }

    private fun initSettings() {
        initProgress(_binding.root)
        setSupportActionBar(_binding.toolbarDetailUser)

    }

    private fun initListeners() {
        _binding.toolbarDetailUser.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public fun setTitleToolbar(msg: MsgUserData) {
        Log.e(TAG, "setTitleToolbar: ${msg.data.nombre}")
        _binding.toolbarDetailUser.title = msg.data.nombre
        people = msg.data
    }

    override fun showError(error: Throwable) {
        showSnackBar(_binding.root, error.message)
    }

    override fun resultGetPeople(response: PersonasEntity) {
        showProgress(false)
        showToastSuccess(_binding.root, "${response.nombre}", click = {
           showToast("hiciste click por invoke")
        })
        //showSnackBar(_binding.root, "${response.nombre}")
    }
    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        dPresenter.subscribe(this)
        super.onDestroy()
    }



    companion object {
        @JvmStatic
        fun newInstance() = DetailUserActivity().apply {

        }

        @JvmStatic
        val TAG = DetailUserActivity::class.simpleName
    }
}