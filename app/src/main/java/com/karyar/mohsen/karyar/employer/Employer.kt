package com.karyar.mohsen.karyar.employer

import android.arch.persistence.room.Entity
import android.os.Parcel
import android.os.Parcelable
import com.karyar.mohsen.karyar.User

/**
 * Created by Mohsen on 5/23/18.
 */
@Entity(primaryKeys = ["phone_number"])
class Employer(var eId: Int?) : User(), Parcelable {
  constructor(source: Parcel) : this(
      source.readValue(Int::class.java.classLoader) as Int?
  )

  override fun describeContents() = 0

  override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
    writeValue(eId)
  }

  companion object {
    @JvmField val CREATOR: Parcelable.Creator<Employer> = object : Parcelable.Creator<Employer> {
      override fun createFromParcel(source: Parcel): Employer = Employer(source)
      override fun newArray(size: Int): Array<Employer?> = arrayOfNulls(size)
    }
  }
}