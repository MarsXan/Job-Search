package com.karyar.mohsen.karyar.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.util.SparseArray
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView

import com.karyar.mohsen.karyar.R
import com.karyar.mohsen.karyar.Role
import com.karyar.mohsen.karyar.customviews.AnimatedViewPager
import com.karyar.mohsen.karyar.login.AuthFragment.Callback

class AuthAdapter(manager: FragmentManager,
  private val pager: AnimatedViewPager,
  private val authBackground: ImageView,
  private val sharedElements: List<ImageView>,val role: Role)
  : FragmentStatePagerAdapter(manager),
    Callback {
  private val authArray: SparseArray<AuthFragment>
  private val factor: Float

  init {
    this.authArray = SparseArray(count)
    pager.duration = 350
    val textSize = pager.resources.getDimension(R.dimen.folded_size)
    val textPadding = pager.resources.getDimension(R.dimen.folded_label_padding)
    factor = 1 - (textSize + textPadding) / pager.width
  }

  override fun getItem(position: Int): AuthFragment {
    var fragment: AuthFragment? = authArray.get(position)
    if (fragment == null) {
      fragment = if (position != 1) LogInFragment.newInstance(role.name) else SignUpFragment.newInstance(role.name)
      authArray.put(position, fragment)
      fragment.setCallback(this)
    }
    return fragment
  }

  override fun show(fragment: AuthFragment) {
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

  private fun getPageOffsetX(fragment: AuthFragment): Float {
    val pageWidth = fragment.view!!.width
    return pageWidth - pageWidth * factor
  }

  private fun shiftSharedElements(pageOffsetX: Float, forward: Boolean) {
    val context = pager.context
    //since we're clipping the page, we have to adjust the shared elements
    val shiftAnimator = AnimatorSet()
//    for (view in sharedElements) {
//      var translationX = if (forward) (pageOffsetX) else -pageOffsetX
//      val temp = (view.width / 2f)
//      translationX -= if (forward) temp else -temp
//      val shift = ObjectAnimator.ofFloat<View>(view, View.TRANSLATION_X, 0f, translationX)
//      shiftAnimator.playTogether(shift)
//    }

    val color = ContextCompat.getColor(
        context, if (forward) R.color.color_logo_sign_up else R.color.color_logo_log_in
    )
    DrawableCompat.setTint(sharedElements[0].drawable, color)
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
    val logo = sharedElements[0]

    val scaleAnimation = AnimatorSet()
    scaleAnimation.playTogether(ObjectAnimator.ofFloat(logo, View.SCALE_X, logoScale))
    scaleAnimation.playTogether(ObjectAnimator.ofFloat(logo, View.SCALE_Y, logoScale))
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
