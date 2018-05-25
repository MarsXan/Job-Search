package com.karyar.mohsen.karyar.login.ll

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.TextInputEditText
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.util.TypedValue
import android.view.View
import com.karyar.mohsen.karyar.R
import com.karyar.mohsen.karyar.transitions.Rotate
import com.karyar.mohsen.karyar.transitions.TextResize
import com.transitionseverywhere.ChangeBounds
import com.transitionseverywhere.Transition
import com.transitionseverywhere.TransitionManager
import com.transitionseverywhere.TransitionSet
import kotlinx.android.synthetic.main.sign_up_fragment.*

class SignUpFragment : AuthFragment() {

  private var views: MutableList<TextInputEditText> = mutableListOf()

  private val textPadding: Float
    get() = resources.getDimension(R.dimen.folded_label_padding) / 2.1f

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    view.setBackgroundColor(ContextCompat.getColor(context!!, R.color.color_sign_up))
    caption.text = getString(R.string.sign_up_label)
    views.add(email_input_edit)
    views.add(password_input_edit)
    views.add(confirm_password_edit)
    for (editText in views) {
      if (editText.id == R.id.password_input_edit) {
        val inputLayout = password_input
        val confirmLayout = confirm_password
        val boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD)
        inputLayout.setTypeface(boldTypeface)
        confirmLayout.setTypeface(boldTypeface)
        editText.addTextChangedListener(object : TextWatcherAdapter() {
          override fun afterTextChanged(s: Editable) {
            inputLayout.isPasswordVisibilityToggleEnabled = s.isNotEmpty()
          }
        })
      }
      editText.setOnFocusChangeListener { _, hasFocus ->
        if (!hasFocus) {
          val isEnabled = editText.text.isNotEmpty()
          editText.isSelected = isEnabled
        }
      }
    }
    caption.isVerticalText = true
    foldStuff()
    caption.translationX = textPadding
  }

  override fun authLayout(): Int {
    return R.layout.sign_up_fragment
  }

  override fun clearFocus() {
    for (view in views) view.clearFocus()
  }

  override fun fold() {
    lock = false
    val transition = Rotate()
    transition.setEndAngle(-90f)
    transition.addTarget(caption)
    val set = TransitionSet()
    set.duration = resources.getInteger(R.integer.duration)
        .toLong()
    val changeBounds = ChangeBounds()
    set.addTransition(changeBounds)
    set.addTransition(transition)
    val sizeTransition = TextResize()
    sizeTransition.addTarget(caption)
    set.addTransition(sizeTransition)
    set.ordering = TransitionSet.ORDERING_TOGETHER
    set.addListener(object : Transition.TransitionListenerAdapter() {
      override fun onTransitionEnd(transition: Transition?) {
        super.onTransitionEnd(transition)
        caption.translationX = textPadding
        caption.rotation = 0f
        caption.isVerticalText = true
        caption.requestLayout()

      }
    })
    TransitionManager.beginDelayedTransition(parent, set)
    foldStuff()
    caption.translationX = -caption.width / 8 + textPadding
  }

  private fun foldStuff() {
    caption.setTextSize(TypedValue.COMPLEX_UNIT_PX, caption.textSize / 2f)
    caption.setTextColor(Color.WHITE)
    val params = params
    params.rightToRight = ConstraintLayout.LayoutParams.UNSET
    params.verticalBias = 0.5f
    caption.layoutParams = params
  }
}
