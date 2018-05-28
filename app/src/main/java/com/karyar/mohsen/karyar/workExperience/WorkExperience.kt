package com.karyar.mohsen.karyar.workExperience

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import com.karyar.mohsen.karyar.worker.persistence.db.Worker

/**
 * Created by Mohsen on 5/23/18.
 */
@Entity(
    indices = [(Index(value = ["worker_id"], name = "idx"))],
    foreignKeys = [(ForeignKey(
        entity = Worker::class,
        parentColumns = arrayOf("phone_number"),
        childColumns = arrayOf("worker_id")
        , onDelete = CASCADE
    ))]
)
class WorkExperience(
  @PrimaryKey(autoGenerate = true) var id: Int?, @ColumnInfo(
      name = "company"
  ) var company: String, @ColumnInfo(name = "start_date") var startDate: String, @ColumnInfo(
      name = "end_date"
  ) var endDate: String?, @ColumnInfo(name = "worker_id") var workerId: Int
) : Parcelable {
  constructor(source: Parcel) : this(
      source.readValue(Int::class.java.classLoader) as Int?,
      source.readString(),
      source.readString(),
      source.readString(),
      source.readInt()
  )

  override fun describeContents() = 0

  override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
    writeValue(id)
    writeString(company)
    writeString(startDate)
    writeString(endDate)
    writeInt(workerId)
  }

  companion object {
    @JvmField val CREATOR: Parcelable.Creator<WorkExperience> =
      object : Parcelable.Creator<WorkExperience> {
        override fun createFromParcel(source: Parcel): WorkExperience =
          WorkExperience(source)

        override fun newArray(size: Int): Array<WorkExperience?> = arrayOfNulls(size)
      }
  }
}