package com.karyar.mohsen.karyar.worker.persistence.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.os.Parcel
import android.os.Parcelable
import com.karyar.mohsen.karyar.User

/**
 * Created by Mohsen on 5/22/18.
 */
@Entity(primaryKeys = ["phone_number"])
class Worker(var wId: Int?, @ColumnInfo(
    name = "expertise"
) var expertise: String) : User(),
    Parcelable {
  constructor(source: Parcel) : this(
      source.readValue(Int::class.java.classLoader) as Int?,
      source.readString()
  )

  override fun describeContents() = 0

  override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
    writeValue(wId)
    writeString(expertise)
  }

  companion object {
    @JvmField val CREATOR: Parcelable.Creator<Worker> = object : Parcelable.Creator<Worker> {
      override fun createFromParcel(source: Parcel): Worker =
        Worker(source)
      override fun newArray(size: Int): Array<Worker?> = arrayOfNulls(size)
    }
  }
}