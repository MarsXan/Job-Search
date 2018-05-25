package com.karyar.mohsen.karyar

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import com.karyar.mohsen.karyar.login.LoginInfo
import com.karyar.mohsen.karyar.worker.persistence.db.Worker
import java.util.Date

/**
 * Created by Mohsen on 5/22/18.
 */
@Entity(
    indices = [(Index(value = ["login_info_id"], name = "ldx"))],
    foreignKeys = [(ForeignKey(
        entity = LoginInfo::class,
        parentColumns = arrayOf("userId"),
        childColumns = arrayOf("login_info_id")
        , onDelete = ForeignKey.CASCADE
    ))]
)
open class User(
  var id: Int?, @ColumnInfo(name = "name") var name: String, @ColumnInfo(
      name = "last_name"
  ) var lastName: String, @ColumnInfo(name = "phone_number") var phoneNumber: String, @ColumnInfo(
      name = "image"
  ) var image: Int, @ColumnInfo(name = "email") var email: String?, @ColumnInfo(
      name = "b_date"
  ) var bDate: String?, @ColumnInfo(name = "gender") var gender: Int, @ColumnInfo(
      name = "desc"
  ) var description: String?, @ColumnInfo(name = "login_info_id") var login_info_id: String) : Parcelable {

  constructor(parcel: Parcel) : this(
      parcel.readValue(Int::class.java.classLoader) as? Int,
      parcel.readString(),
      parcel.readString(),
      parcel.readString(),
      parcel.readInt(),
      parcel.readString(),
      parcel.readString(),
      parcel.readInt(),
      parcel.readString(),
      parcel.readString()
  ) {
  }

  @Ignore
  constructor() : this(null, "", "", "", 0, "", "", 1, "", "")

  override fun writeToParcel(parcel: Parcel, flags: Int) {
    parcel.writeValue(id)
    parcel.writeString(name)
    parcel.writeString(lastName)
    parcel.writeString(phoneNumber)
    parcel.writeInt(image)
    parcel.writeString(email)
    parcel.writeString(bDate)
    parcel.writeInt(gender)
    parcel.writeString(description)
    parcel.writeString(login_info_id)
  }

  override fun describeContents(): Int {
    return 0
  }

  companion object CREATOR : Creator<User> {
    override fun createFromParcel(parcel: Parcel): User {
      return User(parcel)
    }

    override fun newArray(size: Int): Array<User?> {
      return arrayOfNulls(size)
    }
  }

}