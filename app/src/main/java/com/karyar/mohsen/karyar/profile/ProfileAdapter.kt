package com.karyar.mohsen.karyar.profile

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.util.SparseArray
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import com.karyar.mohsen.karyar.R
import com.karyar.mohsen.karyar.Role
import com.karyar.mohsen.karyar.customviews.AnimatedViewPager
import com.karyar.mohsen.karyar.profile.ProfileBaseFragment.Callback
import com.karyar.mohsen.karyar.skill.SkillFragment

/**
 * Created by Mohsen on 5/27/18.
 */

class ProfileAdapter(manager: FragmentManager,
  private val pager: AnimatedViewPager,
  private val authBackground: ImageView, val role: Role)
  : FragmentStatePagerAdapter(manager),
    Callback {
  private val authArray: SparseArray<ProfileBaseFragment>
  private val factor: Float

  init {
    this.authArray = SparseArray(count)
    pager.duration = 350
    val textSize = pager.resources.getDimension(R.dimen.folded_size)
    val textPadding = pager.resources.getDimension(R.dimen.folded_label_padding)
    factor = 1 - (textSize + textPadding) / pager.width
  }

  override fun getItem(position: Int): ProfileBaseFragment {
    var fragment: ProfileBaseFragment? = authArray.get(position)
    if (fragment == null) {
      fragment =
          if (position != 1) ProfileFragment.newInstance() else SkillFragment.newInstance()
      authArray.put(position, fragment)
      fragment.setCallback(this)
    }
    return fragment
  }

  override fun show(fragment: ProfileBaseFragment) {
    val index = authArray.keyAt(authArray.indexOfValue(fragment))
    pager.setCurrentItem(index, true)
    shiftSharedElements(getPageOffsetX(fragment), index == 1)
    for (jIndex in 0 until authArray.size()) {
      if (jIndex != index) {
        authArray.get(jIndex)
            .fold()
      }
    }
  }

  private fun getPageOffsetX(fragment: ProfileBaseFragment): Float {
    val pageWidth = fragment.view!!.width
    return pageWidth - pageWidth * factor
  }

  private fun shiftSharedElements(pageOffsetX: Float, forward: Boolean) {
    val context = pager.context
    //since we're clipping the page, we have to adjust the shared elements
    val shiftAnimator = AnimatorSet()
    //scroll the background by x
    val offset = authBackground.width / 2
    val scrollAnimator =
      ObjectAnimator.ofInt(authBackground, "scrollX", if (forward) offset else -offset)
    shiftAnimator.playTogether(scrollAnimator)
    shiftAnimator.interpolator = AccelerateDecelerateInterpolator()
    shiftAnimator.duration = (pager.resources.getInteger(R.integer.duration) / 2).toLong()
    shiftAnimator.start()
  }

  override fun scale(hasFocus: Boolean) {

    val scale = if (hasFocus) 1f else 1.4f
    val logoScale = if (hasFocus) 0.75f else 1f

    val scaleAnimation = AnimatorSet()
    scaleAnimation.playTogether(ObjectAnimator.ofFloat<View>(authBackground, View.SCALE_X, scale))
    scaleAnimation.playTogether(ObjectAnimator.ofFloat<View>(authBackground, View.SCALE_Y, scale))
    scaleAnimation.duration = 200
    scaleAnimation.interpolator = AccelerateDecelerateInterpolator()
    scaleAnimation.start()
  }

  override fun getPageWidth(position: Int): Float {
    return factor
  }

  override fun getCount(): Int {
    return 2
  }
}
