package com.karyar.mohsen.karyar

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Update

/**
 * Created by Mohsen on 5/24/18.
 */
@Dao
interface BaseDao<in T> {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insert(type: T)

  @Insert
  fun insert(vararg obj: T)

  @Update
  fun update(type: T)

  @Delete
  fun delete(type: T)
}