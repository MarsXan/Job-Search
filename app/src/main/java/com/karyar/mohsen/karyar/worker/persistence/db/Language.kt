package com.karyar.mohsen.karyar.worker.persistence.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Mohsen on 5/23/18.
 */
@Entity
class Language(@PrimaryKey(autoGenerate = true) var id: Int?, @ColumnInfo(
    name = "name"
) var name: String) : Parcelable {
  constructor(source: Parcel) : this(
      source.readValue(Int::class.java.classLoader) as Int?,
      source.readString()
  )

  override fun describeContents() = 0

  override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
    writeValue(id)
    writeString(name)
  }

  companion object {
    @JvmField val CREATOR: Parcelable.Creator<Language> = object : Parcelable.Creator<Language> {
      override fun createFromParcel(source: Parcel): Language =
        Language(source)
      override fun newArray(size: Int): Array<Language?> = arrayOfNulls(size)
    }
  }
}