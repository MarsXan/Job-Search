package com.karyar.mohsen.karyar.worker.persistence.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.karyar.mohsen.karyar.Skill
import com.karyar.mohsen.karyar.worker.persistence.db.Worker

/**
 * Created by Mohsen on 5/23/18.
 */
@Dao
interface WorkerDao {
  @get:Query("SELECT * FROM worker")
  val workers: LiveData<List<Worker>>

  @Query("SELECT * FROM worker where wId LIKE  :id ")
  fun findByName(
    id: Int?
  ): Worker

  @Insert(onConflict = REPLACE)
  fun insert(worker: Worker)

  @Update
  fun update(worker: Worker)

  @Query(
      "SELECT * FROM skill INNER JOIN workerskilljoin ON skill.id=workerskilljoin.skillId WHERE workerskilljoin.workerId=:workerId"
  )
  fun getSKillByWorkerId(workerId: Int): LiveData<List<Skill>>

}