package com.karyar.mohsen.karyar.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Point
import android.os.Bundle
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.ImageViewTarget
import com.karyar.mohsen.karyar.AppDatabase
import com.karyar.mohsen.karyar.R
import com.karyar.mohsen.karyar.Role
import com.karyar.mohsen.karyar.Role.WORKER
import com.karyar.mohsen.karyar.profile.ProfileActivity
import com.karyar.mohsen.karyar.utils.StorageUtil

import kotlinx.android.synthetic.main.activity_login.first
import kotlinx.android.synthetic.main.activity_login.last
import kotlinx.android.synthetic.main.activity_login.logo
import kotlinx.android.synthetic.main.activity_login.pager
import kotlinx.android.synthetic.main.activity_login.scrolling_background

class LoginActivity : AppCompatActivity() {
  private var loginViewModel: LoginViewModel? = null
  private var sharedElements: MutableList<ImageView> = mutableListOf()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)
    loginViewModel = ViewModelProviders.of(this)
        .get(LoginViewModel::class.java)
    loginViewModel!!.setDatabase(AppDatabase.getInstance(this)!!)

    sharedElements.add(logo)
    sharedElements.add(first)
    sharedElements.add(last)

    for (element in sharedElements) {
      @ColorRes val color =
        if (element.id != R.id.logo) R.color.white_transparent else R.color.color_logo_log_in
      DrawableCompat.setTint(element.drawable, ContextCompat.getColor(this, color))
    }
    if (StorageUtil.getPrefBoolean(isLoggedIn, false)) {
      startActivity(Intent(this, ProfileActivity::class.java))
      finish()
    } else
      loadBigImageWithGlide(WORKER)
  }

  private fun loadBigImageWithGlide(role: Role) {
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
              val adapter = AuthAdapter(
                  supportFragmentManager, pager, scrolling_background, sharedElements, role
              )
              pager.adapter = adapter
            }
          }
        })
  }

  override fun onDestroy() {
    super.onDestroy()
    AppDatabase.destroyInstance()

  }
  private fun screenSize(): IntArray {
    val display = windowManager.defaultDisplay
    val size = Point()
    display.getSize(size)
    return intArrayOf(size.x, size.y)
  }

  companion object {
    const val isLoggedIn = "isLoggedIn"
  }

}
