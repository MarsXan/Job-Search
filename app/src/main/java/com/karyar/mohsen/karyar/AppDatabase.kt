package com.karyar.mohsen.karyar

import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database
import android.content.Context
import com.karyar.mohsen.karyar.employer.Company
import com.karyar.mohsen.karyar.employer.CompanyDao
import com.karyar.mohsen.karyar.employer.Employer
import com.karyar.mohsen.karyar.job.Job
import com.karyar.mohsen.karyar.job.JobDao
import com.karyar.mohsen.karyar.worker.persistence.db.Language
import com.karyar.mohsen.karyar.worker.persistence.dao.LanguageDao
import com.karyar.mohsen.karyar.worker.persistence.db.WorkExperience
import com.karyar.mohsen.karyar.worker.persistence.db.Worker
import com.karyar.mohsen.karyar.worker.persistence.db.WorkerLanguageJoin
import com.karyar.mohsen.karyar.worker.persistence.db.WorkerSkillJoin
import com.karyar.mohsen.karyar.worker.persistence.dao.WorkExperienceDao
import com.karyar.mohsen.karyar.worker.persistence.dao.WorkerDao

/**
 * Created by Mohsen on 5/22/18.
 */
@Database(
    entities = [ Worker::class, Language::class, WorkExperience::class, Skill::class,
      Employer::class, Job::class, Company::class, WorkerSkillJoin::class, WorkerLanguageJoin::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

  abstract fun workerDao(): WorkerDao
  abstract fun skillDao(): SkillDao
  abstract fun jobDao(): JobDao
  abstract fun companyDao():CompanyDao
  abstract fun languageDao(): LanguageDao
  abstract fun workExperienceDao(): WorkExperienceDao

  companion object {
    private var INSTANCE: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase? {
      if (INSTANCE == null) {
        synchronized(AppDatabase::class) {
          INSTANCE = Room.databaseBuilder(
              context.applicationContext,
              AppDatabase::class.java, "KarYar.db"
          )
              .build()
        }
      }
      return INSTANCE
    }

    fun destroyInstance() {
      INSTANCE = null
    }
  }
}