package com.karyar.mohsen.karyar

import android.app.Application
import com.karyar.mohsen.karyar.utils.StorageUtil
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

/**
 * Created by Mohsen on 5/24/18.
 */
class MainApplication:Application() {
  override fun onCreate() {
    super.onCreate()
    CalligraphyConfig.initDefault(
        CalligraphyConfig.Builder()
            .setDefaultFontPath("fonts/IRAN Sans.ttf")
            .setFontAttrId(R.attr.fontPath)
            .build()

    )
    StorageUtil.init(this)
  }
}