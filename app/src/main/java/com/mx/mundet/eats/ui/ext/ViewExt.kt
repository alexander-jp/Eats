package com.mx.mundet.eats.ui.ext

import android.content.Intent
import android.transition.Fade
import android.transition.Slide
import android.transition.Transition
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import androidx.annotation.AttrRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Created by Alexander Juárez with Date 13/03/2021
 * @author Alexander Juárez
 */

fun Fragment.colorAttr(@AttrRes attrColor: Int): Int {
    val typedValue = TypedValue()
    requireContext().theme.resolveAttribute(attrColor, typedValue, true)
    return typedValue.data
}

fun AppCompatActivity.colorAttr(@AttrRes attrColor: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(attrColor, typedValue, true)
    return typedValue.data
}

fun AppCompatActivity.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Fragment.showToast(msg: String) {
    Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.showSnackBar(view : View?, msg: String?) {
    view?.let {
        Snackbar.make(it, msg!!, Snackbar.LENGTH_SHORT).show()
    }
}

fun Fragment.showSnackBar(msg: String) {
    view?.let {
        Snackbar.make(it, msg, Snackbar.LENGTH_SHORT).show()
    }
}

fun <T> AppCompatActivity.changeActivity(clazz: Class<T>) {
    val intent = Intent(this, clazz)
    startActivity(intent)
}

fun <T> Fragment.changeActivity(clazz: Class<T>) {
    val intent = Intent(activity, clazz)
    //val transition : Transition = Slide(Gravity.RIGHT)
    //transition.duration = 1000
    //transition.interpolator = DecelerateInterpolator()
    //activity?.window?.exitTransition = transition
    //startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity()).toBundle());
    startActivity(intent)

}

fun <T> singleLiveData(): MutableLiveData<T> {
    return MutableLiveData<T>().apply {
        value = null
    }
}

fun <T> LiveData<T>.observeSingle(owner: LifecycleOwner, observer: ((T) -> Unit)) {
    val firstIgnore = AtomicBoolean(true)
    this.observe(owner, Observer {
        if (firstIgnore.getAndSet(false)) return@Observer
        observer(it)
    })
}

fun AppCompatActivity.addFragment(fragment: Fragment, containerId: Int, addBackStack: Boolean) {
    val frag = supportFragmentManager.beginTransaction()
    if (addBackStack) {
        frag.addToBackStack(fragment::class.simpleName)
    } else {
        frag.replace(containerId, fragment).commitAllowingStateLoss()
    }
}

fun Fragment.addFragment(fragment: Fragment, containerId: Int, addBackStack: Boolean) {
    val frag = childFragmentManager.beginTransaction()
    if (addBackStack) {
        frag.addToBackStack(fragment::class.simpleName)
    } else {
        frag.replace(containerId, fragment).commitAllowingStateLoss()
    }
}

fun AppCompatActivity.addAlertFragment(alertDialog: DialogFragment, tag: String) {
    val fragmentTransaction = supportFragmentManager.beginTransaction()
    fragmentTransaction.add(alertDialog, tag)
    fragmentTransaction.commitAllowingStateLoss()
}

fun Fragment.addAlertFragment(alertDialog: DialogFragment, tag: String) {
    val fragmentTransaction = childFragmentManager.beginTransaction()
    fragmentTransaction.add(alertDialog, tag)
    fragmentTransaction.commitAllowingStateLoss()
}
