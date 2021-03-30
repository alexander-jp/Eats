package com.mx.mundet.eats.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

/**
 * Created by Alexander Juárez with Date 27/03/2021
 * @author Alexander Juárez
 */

class AdapterPagerIntroApp constructor(private var layout: IntArray): PagerAdapter() {

    override fun getCount(): Int {
        return layout.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context).inflate(layout[position], container, false)
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val view = `object` as View
        container.removeView(view)
    }
}