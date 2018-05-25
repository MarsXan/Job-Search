package com.karyar.mohsen.karyar.worker.persistence.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey

/**
 * Created by Mohsen on 5/23/18.
 */

@Entity(
    primaryKeys = ["workerId","languageId"],
    foreignKeys = [(ForeignKey(
        entity = Worker::class,
        parentColumns = arrayOf("phone_number"),
        childColumns = arrayOf("workerId")
    )), (ForeignKey(
        entity = Language::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("languageId")
    ))]
)
class WorkerLanguageJoin( var workerId: Int,  var languageId: Int)