package com.mx.mundet.eats.ui.ext

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.provider.MediaStore
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.transition.Explode
import android.transition.Slide
import android.transition.Transition
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.annotation.AttrRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.SnackbarContentLayout
import com.mx.mundet.eats.R
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

fun AppCompatActivity.showToastSuccess(view : View?, msg: String?, click : (View) -> Unit) {
    view?.let {
        val v : View = layoutInflater.inflate(R.layout.message_sucess, null)
        val btnHide = v.findViewById<MaterialButton>(R.id.btnClosedMessage)
        val txtView = v.findViewById<TextView>(R.id.tvt_body_message)
        txtView.setOnClickListener {
            Log.e("tag", "MI ERROR")
        }
        btnHide.setOnClickListener {
            Log.e("tag", "MI ERROR")
            click.invoke(it)
        }
        val toast : Toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT)
        toast.view = v
        toast.setMargin(0f, 0f)
        toast.show()
    }
}

fun Fragment.showSnackBar(msg: String?) {
    view?.let {
        Snackbar.make(it, msg!!, Snackbar.LENGTH_SHORT).show()
    }
}

fun <T> AppCompatActivity.changeActivity(clazz: Class<T>) {
    val intent = Intent(this, clazz)
    startActivity(intent)
}

fun <T> AppCompatActivity.changeActivityFinish(clazz: Class<T>) {
    val intent = Intent(this, clazz)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    startActivity(intent)
    finish()
}

fun <T> Fragment.changeActivityFinish(clazz: Class<T>) {
    val intent = Intent(activity, clazz)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    startActivity(intent)
    activity?.finish()
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

fun <T> AppCompatActivity.changeActivityAnimationSlideBottomExplode(clazz: Class<T>) {
    val intent = Intent(this, clazz)
    val explode : Transition = Explode()
    val slide : Transition = Slide(Gravity.TOP)
//    explode.duration = 500
//    slide.duration = 800
//    window?.exitTransition = slide
//    window?.enterTransition = explode
    window?.reenterTransition = explode
    window?.returnTransition = explode
    startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle())
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

fun underLineText(content: String): SpannableString {
    val s = SpannableString(content)
    s.setSpan(UnderlineSpan(), 0, s.length, 0)
    return s
}

fun changeColorCharacterLastIndex(content: String): SpannableString {
    val span = SpannableString(content)
    span.setSpan(ForegroundColorSpan(Color.RED), content.lastIndex, span.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    return span
}

fun <T> AppCompatActivity.addDropdownAdapter (items : List<T>, view : EditText?, adapter : () -> Unit){
    val adapters = ArrayAdapter(this, R.layout.item_list_dropdown, items)
    (view as? AutoCompleteTextView)?.setAdapter(adapters)
    adapter.invoke()
}

fun <T> Fragment.addDropdownAdapter (items : List<T>, view : EditText?, adapter : () -> Unit){
    val adapters = ArrayAdapter(requireContext(), R.layout.item_list_dropdown, items)
    (view as? AutoCompleteTextView)?.setAdapter(adapters)
    adapter.invoke()
}

fun Fragment.uriToFilePath(uri : Uri) : String{
    var path : String?=null
    val cursor = activity?.contentResolver?.query(uri, null,null,null, null)
    cursor?.use {
        while (cursor.moveToNext()){
            path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA))
        }
        cursor.close()
    }
    return path!!
}

