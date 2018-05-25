package com.karyar.mohsen.karyar

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Mohsen on 5/23/18.
 */
@Entity
class Skill(
  @PrimaryKey(autoGenerate = true) var id: Int?, @ColumnInfo(
      name = "skill_name"
  ) var skillName: String
) : Parcelable {
  constructor(source: Parcel) : this(
      source.readValue(Int::class.java.classLoader) as Int?,
      source.readString()
  )

  override fun describeContents() = 0

  override fun writeToParcel(
    dest: Parcel,
    flags: Int
  ) = with(dest) {
    writeValue(id)
    writeString(skillName)
  }

  override fun toString(): String {
    return "Skill(id=$id, skillName='$skillName')"
  }

  companion object {
    @JvmField val CREATOR: Parcelable.Creator<Skill> = object : Parcelable.Creator<Skill> {
      override fun createFromParcel(source: Parcel): Skill = Skill(source)
      override fun newArray(size: Int): Array<Skill?> = arrayOfNulls(size)
    }
  }

}