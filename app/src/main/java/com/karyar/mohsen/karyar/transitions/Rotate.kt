package com.karyar.mohsen.karyar.transitions

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import com.karyar.mohsen.karyar.R
import com.transitionseverywhere.Transition
import com.transitionseverywhere.TransitionValues
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

class Rotate : Transition {

  private var startAngle: Float = 0.toFloat()
  private var endAngle: Float = 0.toFloat()

  constructor() {}

  constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
    if (attrs != null) {
      val array = context.obtainStyledAttributes(attrs, R.styleable.Rotate)
      startAngle = array.getFloat(R.styleable.Rotate_start_angle, 0f)
      endAngle = array.getFloat(R.styleable.Rotate_end_angle, 0f)
      array.recycle()
    }
  }

  fun setEndAngle(endAngle: Float) {
    this.endAngle = endAngle
  }

  fun setStartAngle(startAngle: Float) {
    this.startAngle = startAngle
  }

  override fun captureStartValues(transitionValues: TransitionValues) {
    transitionValues.values[PROPNAME_ROTATION] = startAngle
  }

  override fun captureEndValues(transitionValues: TransitionValues) {
    transitionValues.values[PROPNAME_ROTATION] = endAngle
  }

  override fun createAnimator(sceneRoot: ViewGroup?, startValues: TransitionValues?,
    endValues: TransitionValues?): Animator? {
    if (startValues == null || endValues == null) {
      return null
    }
    val view = endValues.view
    val startRotation = startValues.values[PROPNAME_ROTATION] as Float
    val endRotation = endValues.values[PROPNAME_ROTATION] as Float
    if (startRotation != endRotation) {
      view.rotation = startRotation
      return ObjectAnimator.ofFloat(
          view, View.ROTATION,
          startRotation, endRotation
      )
    }
    return null
  }

  companion object {

    private val PROPNAME_ROTATION = "vpaliy:rotate:rotation"
  }
}