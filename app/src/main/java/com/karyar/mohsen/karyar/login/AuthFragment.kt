package com.karyar.mohsen.karyar.login

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.karyar.mohsen.karyar.R
import com.karyar.mohsen.karyar.customviews.VerticalTextView
import com.karyar.mohsen.karyar.transitions.Rotate
import com.karyar.mohsen.karyar.transitions.TextResize
import com.transitionseverywhere.ChangeBounds
import com.transitionseverywhere.TransitionManager
import com.transitionseverywhere.TransitionSet
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent

abstract class AuthFragment : Fragment() {

  private var mCallback: Callback? = null

  lateinit var loginBtn: VerticalTextView

  lateinit var parent: ViewGroup

  protected var lock: Boolean = false

  protected val params: ConstraintLayout.LayoutParams
    get() = ConstraintLayout.LayoutParams::class.java.cast(loginBtn.layoutParams)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    retainInstance = true
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val root = inflater.inflate(authLayout(), container, false)
    loginBtn = root.findViewById(R.id.loginBtn)
    parent = root.findViewById(R.id.root)
    root.setOnClickListener { _ -> unfold() }
    KeyboardVisibilityEvent.setEventListener(
        activity
    ) { isOpen ->
      mCallback!!.scale(isOpen)
      if (!isOpen) {
        clearFocus()
      }
    }
    return root
  }

  fun setCallback(callback: Callback) {
    this.mCallback = callback
  }

  @LayoutRes
  abstract fun authLayout(): Int

  abstract fun fold()
  abstract fun clearFocus()

  private fun unfold() {
    if (!lock) {
      loginBtn.isVerticalText = false
      loginBtn.requestLayout()
      val transition = Rotate()
      transition.setStartAngle(-90f)
      transition.setEndAngle(0f)
      transition.addTarget(loginBtn)
      val set = TransitionSet()
      set.duration = resources.getInteger(R.integer.duration)
          .toLong()
      val changeBounds = ChangeBounds()
      set.addTransition(changeBounds)
      set.addTransition(transition)
      val sizeTransition = TextResize()
      sizeTransition.addTarget(loginBtn)
      set.addTransition(sizeTransition)
      set.ordering = TransitionSet.ORDERING_TOGETHER
      loginBtn.post {
        TransitionManager.beginDelayedTransition(parent, set)
        loginBtn.setTextSize(
            TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.unfolded_size)
        )
        loginBtn.setTextColor(
            ContextCompat.getColor(context!!, R.color.color_label)
        )
        loginBtn.translationX = 0f
        val params = params
        params.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID
        params.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID
        params.verticalBias = 0.78f
        loginBtn.layoutParams = params
      }
      mCallback!!.show(this)
      lock = true
    }
  }

  interface Callback {
    fun show(fragment: AuthFragment)
    fun scale(hasFocus: Boolean)
  }
}
