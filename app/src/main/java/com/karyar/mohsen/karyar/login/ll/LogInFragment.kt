package com.karyar.mohsen.karyar.login.ll

import android.annotation.TargetApi
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
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
import kotlinx.android.synthetic.main.login_fragment.email_input_edit
import kotlinx.android.synthetic.main.login_fragment.password_input
import kotlinx.android.synthetic.main.login_fragment.password_input_edit

class LogInFragment : AuthFragment() {
  private var views: MutableList<TextInputEditText> = mutableListOf()
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    caption.text = getString(R.string.log_in_label)
    view.setBackgroundColor(ContextCompat.getColor(context!!, R.color.color_log_in))

    views.add(email_input_edit)
    views.add(password_input_edit)

    for (editText in views) {
      if (editText.id == R.id.password_input_edit) {
        val inputLayout = password_input
        val boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD)
        inputLayout.setTypeface(boldTypeface)
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
  }

  override fun authLayout(): Int {
    return R.layout.login_fragment
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
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
    val padding = resources.getDimension(R.dimen.folded_label_padding) / 2
    set.addListener(object : Transition.TransitionListenerAdapter() {
      override fun onTransitionEnd(transition: Transition?) {
        super.onTransitionEnd(transition)
        caption.translationX = -padding
        caption.rotation = 0f
        caption.isVerticalText = true
        caption.requestLayout()

      }
    })
    TransitionManager.beginDelayedTransition(parent, set)
    caption.setTextSize(TypedValue.COMPLEX_UNIT_PX, caption.textSize / 2)
    caption.setTextColor(Color.WHITE)
    val params = params
    params.leftToLeft = ConstraintLayout.LayoutParams.UNSET
    params.verticalBias = 0.5f
    caption.layoutParams = params
    caption.translationX = caption.width / 8 - padding
  }

  override fun clearFocus() {
    for (view in views) view.clearFocus()
  }

}
