package com.karyar.mohsen.karyar.employer

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Mohsen on 5/23/18.
 */

@Entity(
    indices = [(Index(value = ["employer_id"], name = "eId"))],
    foreignKeys = [(ForeignKey(
        entity = Employer::class,
        parentColumns = arrayOf("phone_number"),
        childColumns = arrayOf("employer_id")
        , onDelete = ForeignKey.CASCADE
    ))]
)
class Company(@PrimaryKey(autoGenerate = true) var id: Int?, @ColumnInfo(
    name = "name"
) var name: String, @ColumnInfo(
    name = "established_year"
) var establishedYear: String?, @ColumnInfo(
    name = "w_count"
) var wCount: String?, @ColumnInfo(name = "banner") var banner: Int, @ColumnInfo(
    name = "desc"
) var desc: String, @ColumnInfo(name = "image") var image: Int, @ColumnInfo(
    name = "site"
) var site: String?, @ColumnInfo(name = "address") var address: String, @ColumnInfo(
    name = "employer_id"
) var employerId: String)

  : Parcelable {
  constructor(source: Parcel) : this(
      source.readValue(Int::class.java.classLoader) as Int?,
      source.readString(),
      source.readString(),
      source.readString(),
      source.readInt(),
      source.readString(),
      source.readInt(),
      source.readString(),
      source.readString(),
      source.readString()
  )

  override fun describeContents() = 0

  override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
    writeValue(id)
    writeString(name)
    writeString(establishedYear)
    writeString(wCount)
    writeInt(banner)
    writeString(desc)
    writeInt(image)
    writeString(site)
    writeString(address)
    writeString(employerId)
  }

  companion object {
    @JvmField val CREATOR: Parcelable.Creator<Company> = object : Parcelable.Creator<Company> {
      override fun createFromParcel(source: Parcel): Company = Company(source)
      override fun newArray(size: Int): Array<Company?> = arrayOfNulls(size)
    }
  }
}