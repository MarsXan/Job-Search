package com.karyar.mohsen.karyar.skill

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.karyar.mohsen.karyar.R.layout
import com.karyar.mohsen.karyar.skill.SkillRecyclerApdater.SkillVH
import kotlinx.android.synthetic.main.item_skill.view.deleteItem
import kotlinx.android.synthetic.main.item_skill.view.itemName

/**
 * Created by Mohsen on 5/27/18.
 */
class SkillRecyclerApdater(var mContext: Context,
  var skills: List<Skill>) : RecyclerView.Adapter<SkillVH>() {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillVH {
    val view = LayoutInflater.from(parent.context)
        .inflate(layout.item_skill, parent, false)
    return SkillVH(view)
  }

  override fun getItemCount(): Int {
    return skills.size
  }

  override fun onBindViewHolder(holder: SkillVH, position: Int) {
    val skill = skills[position]
    holder.mName.text = skill.skillName
  }

  inner class SkillVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val mName: TextView = itemView.itemName
    val mDelete: ImageView = itemView.deleteItem

  }
}