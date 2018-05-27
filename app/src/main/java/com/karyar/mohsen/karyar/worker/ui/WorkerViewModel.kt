package com.karyar.mohsen.karyar.worker.ui

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.karyar.mohsen.karyar.AppDatabase
import com.karyar.mohsen.karyar.skill.Skill
import com.karyar.mohsen.karyar.worker.persistence.db.Language
import com.karyar.mohsen.karyar.worker.persistence.db.Worker
import rx.Completable

/**
 * Created by Mohsen on 5/23/18.
 */
class WorkerViewModel(application: Application) :
    AndroidViewModel(application) {
  private var appDatabase: AppDatabase? = null

  fun setAppDataBase(appDatabase: AppDatabase) {
    this.appDatabase = appDatabase
  }

  fun saveWorker(worker: Worker): Completable {
    return Completable.fromAction({
      appDatabase!!.workerDao()
          .insert(worker)
    })
  }

  fun saveLanguage(language: Language): Completable {
    return Completable.fromAction({
      appDatabase!!.languageDao()
          .insert(language)
    })
  }

  fun saveSkill(skill: Skill): Completable {
    return Completable.fromAction({
      appDatabase!!.skillDao()
          .insert(skill)
    })
  }

  fun getWorkerSkills(): LiveData<List<Skill>> {
    return appDatabase!!.skillDao()
        .all
  }

}