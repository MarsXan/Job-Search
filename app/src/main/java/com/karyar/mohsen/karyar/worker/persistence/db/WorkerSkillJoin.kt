package com.karyar.mohsen.karyar.worker.persistence.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import com.karyar.mohsen.karyar.Skill

/**
 * Created by Mohsen on 5/23/18.
 */

@Entity(
    primaryKeys = ["workerId","skillId"],
    foreignKeys = [(ForeignKey(
        entity = Worker::class,
        parentColumns = arrayOf("phone_number"),
        childColumns = arrayOf("workerId")
    )), (ForeignKey(
        entity = Skill::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("skillId")
    ))]
)
class WorkerSkillJoin( var workerId: Int,  var skillId: Int)