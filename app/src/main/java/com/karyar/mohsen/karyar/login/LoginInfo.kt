package com.karyar.mohsen.karyar.login

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Mohsen on 5/25/18.
 */
@Entity()
class LoginInfo(@PrimaryKey var userName: String, @ColumnInfo(
    name = "password"
) var password: String, @ColumnInfo(
    name = "role"
) var role: String) : Parcelable {
  constructor(source: Parcel) : this(
      source.readString(),
      source.readString(),
      source.readString()
  )

  override fun describeContents() = 0

  override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
    writeString(userName)
    writeString(password)
    writeString(role)
  }

  companion object {
    @JvmField val CREATOR: Parcelable.Creator<LoginInfo> = object : Parcelable.Creator<LoginInfo> {
      override fun createFromParcel(source: Parcel): LoginInfo = LoginInfo(source)
      override fun newArray(size: Int): Array<LoginInfo?> = arrayOfNulls(size)
    }
  }
}