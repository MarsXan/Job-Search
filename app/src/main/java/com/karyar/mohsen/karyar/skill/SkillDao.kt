package com.karyar.mohsen.karyar.skill

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.karyar.mohsen.karyar.BaseDao

/**
 * Created by Mohsen on 5/23/18.
 */
@Dao
interface SkillDao : BaseDao<Skill> {

  @get:Query("SELECT * FROM skill")
  val all: LiveData<List<Skill>>

  @Query("SELECT COUNT(*) from skill")
  fun countSkills(): Int

  @Insert
  override fun insert(skill: Skill)

  @Delete
  override fun delete(skill: Skill)

  @Query(
      "SELECT * FROM skill INNER JOIN workerskilljoin ON skill.id=workerskilljoin.skillId WHERE workerskilljoin.workerId=:workerId"
  )
  fun getSKillByWorkerId(workerId: Int): LiveData<List<Skill>>

  @Update
  override fun update(type: Skill)
}