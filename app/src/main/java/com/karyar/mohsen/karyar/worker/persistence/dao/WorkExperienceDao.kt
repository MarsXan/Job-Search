package com.karyar.mohsen.karyar.worker.persistence.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.karyar.mohsen.karyar.worker.persistence.db.WorkExperience

/**
 * Created by Mohsen on 5/23/18.
 */
@Dao
interface WorkExperienceDao {
  @get:Query("SELECT * FROM workexperience")
  val all: LiveData<List<WorkExperience>>

  @Query("SELECT COUNT(*) from workexperience")
  fun countWorkExperiences(): Int

  @Insert
  fun insertAll(vararg workExperience: WorkExperience)

  @Delete
  fun delete(workExperience: WorkExperience)

  @Query("SELECT * FROM workexperience WHERE worker_id=:workerId")
  fun getWorkExperienceByWorkerId(workerId: Int): LiveData<List<WorkExperience>>
}