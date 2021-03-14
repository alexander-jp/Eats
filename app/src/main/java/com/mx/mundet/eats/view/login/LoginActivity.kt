package com.mx.mundet.eats.view.login

import android.os.Bundle
import com.mx.mundet.eats.R
import com.mx.mundet.eats.databinding.LoginActivityBinding
import com.mx.mundet.eats.view.base.BaseActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by Alexander Juárez with Date 13/03/2021
 * @author Alexander Juárez
 */

class LoginActivity : BaseActivity() {
    private lateinit var _binding: LoginActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(_binding.root)

    }

    override fun onResume() {
        super.onResume()
        initProgress(_binding.root)
        GlobalScope.launch(Dispatchers.Main) {
            delay(4000)
            showProgress(false)
            delay(1000)
            showProgress(true)
        }
    }
}