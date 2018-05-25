package com.karyar.mohsen.karyar.worker.persistence.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.karyar.mohsen.karyar.worker.persistence.db.Language

/**
 * Created by Mohsen on 5/23/18.
 */
@Dao
interface LanguageDao {
  @get:Query("SELECT * FROM language")
  val languages: LiveData<Language>

  @Query(
      "SELECT * FROM language INNER JOIN workerlanguagejoin ON language.id=workerlanguagejoin.languageId WHERE workerlanguagejoin.workerId=:workerId"
  )
  fun getWorkerLanguages(workerId: Int): LiveData<Language>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insert(language: Language)

  @Update
  fun update(language: Language)
}