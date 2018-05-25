package com.karyar.mohsen.karyar

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import java.util.Date

/**
 * Created by Mohsen on 5/22/18.
 */
@Entity
open class User(
  var id: Int?, @ColumnInfo(name = "name") var name: String, @ColumnInfo(
      name = "last_name"
  ) var lastName: String, @ColumnInfo(name = "phone_number") var phoneNumber: String, @ColumnInfo(
      name = "image"
  ) var image: Int, @ColumnInfo(name = "email") var email: String?, @ColumnInfo(
      name = "b_date"
  ) var bDate: String?, @ColumnInfo(name = "gender") var gender: Int, @ColumnInfo(
      name = "desc"
  ) var description: String?) : Parcelable {
  @Ignore
  constructor() : this(null, "", "", "", 0, "", "", 1, "")

  constructor(source: Parcel) : this(
      source.readValue(Int::class.java.classLoader) as Int?,
      source.readString(),
      source.readString(),
      source.readString(),
      source.readInt(),
      source.readString(),
      source.readString(),
      source.readInt(),
      source.readString()
  )

  override fun describeContents() = 0

  override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
    writeValue(id)
    writeString(name)
    writeString(lastName)
    writeString(phoneNumber)
    writeInt(image)
    writeString(email)
    writeString(bDate)
    writeInt(gender)
    writeString(description)
  }

  companion object {
    @JvmField val CREATOR: Parcelable.Creator<User> = object : Parcelable.Creator<User> {
      override fun createFromParcel(source: Parcel): User = User(source)
      override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)
    }
  }
}