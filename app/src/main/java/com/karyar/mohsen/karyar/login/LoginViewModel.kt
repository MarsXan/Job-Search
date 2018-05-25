package com.karyar.mohsen.karyar.login

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.karyar.mohsen.karyar.AppDatabase
import com.karyar.mohsen.karyar.worker.persistence.db.Worker
import rx.Completable

/**
 * Created by Mohsen on 5/25/18.
 */
class LoginViewModel(application: Application) : AndroidViewModel(application) {
  var role: String? = null
  var appDatabase: AppDatabase? = null

  fun setDatabase(database: AppDatabase) {
    this.appDatabase = database
  }

  fun saveLoginInfo(login: LoginInfo): Completable {
    return Completable.fromAction({
      appDatabase!!.loginDao()
          .insert(login)
    })
  }

  fun getLoginInfoByPhoneNumber(phoneNumber: String): LiveData<LoginInfo> {
    return appDatabase!!.loginDao()
        .findLoginInfoByPhoneNumber(phoneNumber)
  }

  fun getUserByPhoneNumber(phoneNumber: String): LiveData<Worker> {
    return appDatabase!!.workerDao()
        .findByPhoneNumber(phoneNumber)
  }
}