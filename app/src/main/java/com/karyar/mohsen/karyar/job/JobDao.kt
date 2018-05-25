package com.karyar.mohsen.karyar.job

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.karyar.mohsen.karyar.BaseDao
import com.karyar.mohsen.karyar.employer.Company

/**
 * Created by Mohsen on 5/24/18.
 */
@Dao
abstract class JobDao : BaseDao<Job> {

  @Query(
      "SELECT * FROM company c JOIN job j ON c.id = j.company_id WHERE j.company_id=:companyId LIMIT 1"
  )
  abstract fun getJobCompanyWithId(companyId: Int): LiveData<Company>

  @Query("SELECT * FROM job WHERE company_id=:companyId")
  abstract fun findJobsForCompany(companyId: Int): LiveData<List<Job>>



}