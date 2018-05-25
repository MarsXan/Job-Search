package com.karyar.mohsen.karyar.login

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.karyar.mohsen.karyar.BaseDao

/**
 * Created by Mohsen on 5/25/18.
 */
@Dao
abstract class LoginDao : BaseDao<LoginInfo> {

  @Query("SELECT * FROM logininfo where (userName LIKE  :phoneNumber AND role LIKE :role)")
  abstract fun findLoginInfoByPhoneNumber(phoneNumber: String, role: String): LiveData<LoginInfo>
}