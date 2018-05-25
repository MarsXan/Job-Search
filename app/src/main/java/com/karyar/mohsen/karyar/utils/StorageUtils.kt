package com.karyar.mohsen.karyar.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.gson.Gson
import java.lang.reflect.Type

/**
 * Created by Mohsen on 5/24/18.
 */

class StorageUtil {
  companion object {
    private var mPreferences: SharedPreferences? = null

    fun init(context: Context) {
      mPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun putPrefString(key: String, value: String) {
      mPreferences!!.edit()
          .putString(key, value)
          .apply()
    }

    fun getPrefString(key: String, defValue: String): String? {
      return mPreferences!!.getString(key, defValue)
    }

    fun putPrefBoolean(key: String, value: Boolean) {
      mPreferences!!.edit()
          .putBoolean(key, value)
          .apply()
    }

    fun getPrefBoolean(key: String, defValue: Boolean): Boolean {
      return mPreferences!!.getBoolean(key, defValue)
    }

    fun putPrefInt(key: String, value: Int) {
      mPreferences!!.edit()
          .putInt(key, value)
          .apply()
    }

    fun getPrefInt(key: String, defValue: Int): Int {
      return mPreferences!!.getInt(key, defValue)
    }

    fun clearPrefs() {
      mPreferences!!.edit()
          .clear()
          .apply()
    }

    fun clearItem(key: String) {
      mPreferences!!.edit()
          .remove(key)
          .apply()
    }

    operator fun contains(key: String): Boolean {
      return mPreferences!!.contains(key)
    }

    fun <T> getDeserialized(key: String, tClass: Class<T>): T? {
      val t: Any?
      t = try {
        Gson().fromJson(getPrefString(key, ""), tClass)
      } catch (var4: Exception) {
        null
      }

      return t
    }

    fun <T> getDeserialized(key: String, type: Type): T? {

      return try {
        Gson().fromJson<Any?>(getPrefString(key, ""), type) as T?
      } catch (var4: Exception) {
        null
      }
    }

    fun putSerialized(key: String, `object`: Any) {
      putPrefString(key, Gson().toJson(`object`))
    }
  }
}