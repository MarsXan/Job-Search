package com.karyar.mohsen.karyar

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Mohsen on 5/23/18.
 */
class MetaRes(var id: Int, var title: String) : Parcelable {
  constructor(source: Parcel) : this(
      source.readInt(),
      source.readString()
  )

  override fun describeContents() = 0

  override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
    writeInt(id)
    writeString(title)
  }

  companion object {
    @JvmField val CREATOR: Parcelable.Creator<MetaRes> = object : Parcelable.Creator<MetaRes> {
      override fun createFromParcel(source: Parcel): MetaRes = MetaRes(source)
      override fun newArray(size: Int): Array<MetaRes?> = arrayOfNulls(size)
    }
  }
}