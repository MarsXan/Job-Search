package com.karyar.mohsen.karyar.profile

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Bitmap
import android.graphics.Point
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.ImageViewTarget
import com.karyar.mohsen.karyar.R
import com.karyar.mohsen.karyar.Role
import kotlinx.android.synthetic.main.activity_profile.pager
import kotlinx.android.synthetic.main.activity_profile.scrolling_background

class ProfileActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_profile)
    loadBigImageWithGlide()


  }
  private fun loadBigImageWithGlide() {
    val screenSize = screenSize()
    Glide.with(this)
        .asBitmap()
        .load(R.drawable.busy)
        .apply(
            RequestOptions
                .diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC)
                .override(screenSize[0] * 2, screenSize[1])
        )
        .into(object : ImageViewTarget<Bitmap>(scrolling_background) {
          override fun setResource(resource: Bitmap?) {
            scrolling_background.setImageBitmap(resource)
            scrolling_background.post {
              scrolling_background.scrollTo(-scrolling_background.width / 2, 0)
              val xAnimator =
                ObjectAnimator.ofFloat(
                    scrolling_background, View.SCALE_X, 4f, scrolling_background.scaleX
                )
              val yAnimator =
                ObjectAnimator.ofFloat(
                    scrolling_background, View.SCALE_Y, 4f, scrolling_background.scaleY
                )
              val set = AnimatorSet()
              set.playTogether(xAnimator, yAnimator)
              set.duration = resources.getInteger(R.integer.duration)
                  .toLong()
              set.start()
            }
            pager.post {
              val adapter = ProfileAdapter(
                  supportFragmentManager, pager, scrolling_background, Role.WORKER
              )
              pager.adapter = adapter
            }
          }
        })
  }

  private fun screenSize(): IntArray {
    val display = windowManager.defaultDisplay
    val size = Point()
    display.getSize(size)
    return intArrayOf(size.x, size.y)
  }
}
