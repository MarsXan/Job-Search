package com.karyar.mohsen.karyar

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.karyar.mohsen.karyar.R.layout
import com.karyar.mohsen.karyar.employer.NewCompanyFragment
import com.karyar.mohsen.karyar.worker.ui.WorkerViewModel
import com.trello.rxlifecycle.components.support.RxAppCompatActivity

class MainActivity : RxAppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_main)

    supportFragmentManager!!.beginTransaction()
        .replace(R.id.container, NewCompanyFragment.newInstance())
        .addToBackStack(null)
        .commit()
  }


}
