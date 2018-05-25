package com.karyar.mohsen.karyar.employer

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.karyar.mohsen.karyar.AppDatabase
import com.karyar.mohsen.karyar.job.Job
import rx.Completable

/**
 * Created by Mohsen on 5/24/18.
 */
class CompanyViewModel(application: Application):AndroidViewModel(application) {
  private var appDatabase: AppDatabase? = null
  fun setAppDataBase(appDatabase: AppDatabase?) {
    this.appDatabase = appDatabase
  }

  fun saveCompany(company: Company): Completable {
    return Completable.fromAction({
      appDatabase!!.companyDao()
          .insert(company)
    })
  }

  fun deleteJob(company: Company): Completable {
    return Completable.fromAction({
      appDatabase!!.companyDao()
          .delete(company)
    })
  }

  fun updateJob(company: Company): Completable {
    return Completable.fromAction({
      appDatabase!!.companyDao()
          .update(company)
    })
  }

}