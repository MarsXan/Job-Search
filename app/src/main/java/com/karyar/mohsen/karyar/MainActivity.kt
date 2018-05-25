package com.karyar.mohsen.karyar

import android.os.Bundle
import com.karyar.mohsen.karyar.R.layout
import com.karyar.mohsen.karyar.employer.NewCompanyFragment
import com.trello.rxlifecycle.components.support.RxAppCompatActivity

class MainActivity : RxAppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_login)

    supportFragmentManager!!.beginTransaction()
        .replace(R.id.container, NewCompanyFragment.newInstance())
        .addToBackStack(null)
        .commit()
  }


}
