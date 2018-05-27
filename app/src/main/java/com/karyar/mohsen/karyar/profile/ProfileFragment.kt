package com.karyar.mohsen.karyar.profile

import android.annotation.TargetApi
import android.graphics.Color
import android.os.Build
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.TextInputEditText
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.karyar.mohsen.karyar.R
import com.karyar.mohsen.karyar.skill.SkillFragment
import com.karyar.mohsen.karyar.transitions.Rotate
import com.karyar.mohsen.karyar.transitions.TextResize
import com.transitionseverywhere.ChangeBounds
import com.transitionseverywhere.Transition
import com.transitionseverywhere.TransitionManager
import com.transitionseverywhere.TransitionSet


class ProfileFragment : ProfileBaseFragment() {
  private var views: MutableList<TextInputEditText> = mutableListOf()
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    verticalTv.text = ""
  }

  override fun authLayout(): Int {
    return R.layout.fragment_profile
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  override fun fold() {
    lock = false
    val transition = Rotate()
    transition.setEndAngle(-90f)
    transition.addTarget(verticalTv)
    val set = TransitionSet()
    set.duration = resources.getInteger(R.integer.duration)
        .toLong()
    val changeBounds = ChangeBounds()
    set.addTransition(changeBounds)
    set.addTransition(transition)
    val sizeTransition = TextResize()
    sizeTransition.addTarget(verticalTv)
    set.addTransition(sizeTransition)
    set.ordering = TransitionSet.ORDERING_TOGETHER
    val padding = resources.getDimension(R.dimen.folded_label_padding) / 2
    set.addListener(object : Transition.TransitionListenerAdapter() {
      override fun onTransitionEnd(transition: Transition?) {
        super.onTransitionEnd(transition)
        verticalTv.translationX = -padding
        verticalTv.rotation = 0f
        verticalTv.isVerticalText = true
        verticalTv.requestLayout()
        verticalTv.text = "پروفایل"

      }
    })
    TransitionManager.beginDelayedTransition(parent, set)
    verticalTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, verticalTv.textSize / 2)
    verticalTv.setTextColor(Color.WHITE)
    val params = params
    params.leftToLeft = ConstraintLayout.LayoutParams.UNSET
    params.verticalBias = 0.5f
    verticalTv.layoutParams = params
    verticalTv.translationX = verticalTv.width / 8 - padding
  }

  override fun clearFocus() {
    for (view in views) view.clearFocus()
  }


  companion object {

    @JvmStatic fun newInstance() =
      ProfileFragment().apply {
        arguments = Bundle().apply {

        }
      }
  }
}
