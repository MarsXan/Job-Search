package com.karyar.mohsen.karyar

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.karyar.mohsen.karyar.employer.Company
import com.karyar.mohsen.karyar.employer.CompanyDao
import com.karyar.mohsen.karyar.employer.Employer
import com.karyar.mohsen.karyar.job.Job
import com.karyar.mohsen.karyar.job.JobDao
import com.karyar.mohsen.karyar.language.Language
import com.karyar.mohsen.karyar.language.LanguageDao
import com.karyar.mohsen.karyar.language.WorkerLanguageJoin
import com.karyar.mohsen.karyar.login.LoginDao
import com.karyar.mohsen.karyar.login.LoginInfo
import com.karyar.mohsen.karyar.skill.Skill
import com.karyar.mohsen.karyar.skill.SkillDao
import com.karyar.mohsen.karyar.skill.WorkerSkillJoin
import com.karyar.mohsen.karyar.workExperience.WorkExperience
import com.karyar.mohsen.karyar.workExperience.WorkExperienceDao
import com.karyar.mohsen.karyar.worker.persistence.dao.WorkerDao
import com.karyar.mohsen.karyar.worker.persistence.db.Worker

/**
 * Created by Mohsen on 5/22/18.
 */
@Database(
    entities = [ Worker::class, Language::class, WorkExperience::class, Skill::class,LoginInfo::class,
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
  abstract fun loginDao():LoginDao

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