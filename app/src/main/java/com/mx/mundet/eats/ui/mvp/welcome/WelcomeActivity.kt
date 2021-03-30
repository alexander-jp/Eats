package com.mx.mundet.eats.ui.mvp.welcome

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.mx.mundet.eats.App
import com.mx.mundet.eats.R
import com.mx.mundet.eats.databinding.ActivityWelcomeBinding
import com.mx.mundet.eats.ui.adapter.AdapterPagerIntroApp
import com.mx.mundet.eats.ui.base.BaseActivity
import com.mx.mundet.eats.ui.base.SessionBean
import com.mx.mundet.eats.ui.ext.changeActivity
import com.mx.mundet.eats.ui.ext.changeActivityFinish
import com.mx.mundet.eats.ui.ext.underLineText
import com.mx.mundet.eats.ui.mvp.login.LoginActivity
import com.mx.mundet.eats.ui.mvp.main.MainActivity
import javax.inject.Inject

class WelcomeActivity : BaseActivity() {

    private lateinit var _binding : ActivityWelcomeBinding

    private var imageViews : Array<ImageView> = arrayOf()
    private val layouts by lazy { intArrayOf(R.layout.fragment_intro_one, R.layout.fragment_intro_two, R.layout.fragment_intro_three) }
    private val pagerAdapter :AdapterPagerIntroApp by lazy { AdapterPagerIntroApp(layouts) }

    @Inject
    lateinit var sessionBean: SessionBean

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Eats)
        (application as App).appComponent.inject(this)
        _binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        initSettings()
        initListener()
    }

    private fun initSettings(){
        if(!sessionBean.config.isFirtsWelcome){
            changeActivityFinish(LoginActivity::class.java)
        }
        _binding.viewpagerIntroApp.adapter = pagerAdapter
        createDotsPager(0)
    }

    private fun initListener(){

        _binding.btnNextIntro.setOnClickListener {
            nextSlide()
        }
        _binding.btnSkipIntro.setOnClickListener {
            sessionBean.config.isFirtsWelcome = false
            sessionBean.save()
            changeActivityFinish(LoginActivity::class.java)
        }

        _binding.viewpagerIntroApp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                createDotsPager(position)
                if (position == layouts.size - 1) {
                    _binding.btnNextIntro.text = getString(R.string.text_iniciar)
                    _binding.btnSkipIntro.visibility = View.GONE
                } else {
                    _binding.btnNextIntro.text = getString(R.string.text_siguiente)
                    _binding.btnSkipIntro.visibility = View.VISIBLE
                }
            }
        })
    }


    fun createDotsPager(position: Int){
        _binding.containerDotsPager.removeAllViews()
        imageViews = Array(layouts.size){ ImageView(this) }
        for (i in layouts.indices){
            if(i == position){
                imageViews[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.active_dots))
            }else{
                imageViews[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.default_dots))
            }
            val layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            layoutParams.setMargins(4,4,4,4)
            _binding.containerDotsPager.addView(imageViews[i], layoutParams)
        }
    }

    private fun nextSlide(){
        val nextSlide = _binding.viewpagerIntroApp.currentItem.plus(1)
        if(nextSlide < layouts.size){
            _binding.viewpagerIntroApp.currentItem = nextSlide
        }else{
            sessionBean.config.isFirtsWelcome = false
            sessionBean.save()
            changeActivityFinish(LoginActivity::class.java)
        }
    }


}