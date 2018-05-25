package com.karyar.mohsen.karyar.job

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.karyar.mohsen.karyar.AppDatabase
import com.karyar.mohsen.karyar.employer.Company
import rx.Completable

/**
 * Created by Mohsen on 5/24/18.
 */
class JobViewModel(application: Application) : AndroidViewModel(application) {
  private var appDatabase: AppDatabase? = null
  fun setAppDataBase(appDatabase: AppDatabase) {
    this.appDatabase = appDatabase
  }

  fun saveJob(job: Job): Completable {
    return Completable.fromAction({
      appDatabase!!.jobDao()
          .insert(job)
    })
  }

  fun deleteJob(job: Job): Completable {
    return Completable.fromAction({
      appDatabase!!.jobDao()
          .delete(job)
    })
  }

  fun updateJob(job: Job): Completable {
    return Completable.fromAction({
      appDatabase!!.jobDao()
          .update(job)
    })
  }

  fun getJobCompanyWithId(companyId: Int): LiveData<Company> {
    return appDatabase!!.jobDao()
        .getJobCompanyWithId(companyId)
  }

}