package com.karyar.mohsen.karyar.job

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import com.karyar.mohsen.karyar.employer.Company

/**
 * Created by Mohsen on 5/23/18.
 */
@Entity(
    indices = [(Index(value = ["company_id"], name = "cId"))],
    foreignKeys = [(ForeignKey(
        entity = Company::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("company_id")
        , onDelete = ForeignKey.CASCADE
    ))]
)
class Job(@PrimaryKey(autoGenerate = true) var id: Int, @ColumnInfo(name = "name")
var name: String, @ColumnInfo(name = "minimum_work_experience")
var minimumWorkExperience: String, @ColumnInfo(name = "desc") var desc: String?, @ColumnInfo(
    name = "city"
) var city: String, @ColumnInfo(
    name = "company_id"
) var companyId: Int) : Parcelable {
  constructor(source: Parcel) : this(
      source.readInt(),
      source.readString(),
      source.readString(),
      source.readString(),
      source.readString(),
      source.readInt()
  )

  override fun describeContents() = 0

  override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
    writeInt(id)
    writeString(name)
    writeString(minimumWorkExperience)
    writeString(desc)
    writeString(city)
    writeInt(companyId)
  }

  companion object {
    @JvmField val CREATOR: Parcelable.Creator<Job> = object : Parcelable.Creator<Job> {
      override fun createFromParcel(source: Parcel): Job = Job(source)
      override fun newArray(size: Int): Array<Job?> = arrayOfNulls(size)
    }
  }
}