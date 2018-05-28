package com.karyar.mohsen.karyar.language

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.karyar.mohsen.karyar.R.layout
import com.karyar.mohsen.karyar.language.LanguageRecyclerAdapter.LanguageVH
import kotlinx.android.synthetic.main.item_skill.view.deleteItem
import kotlinx.android.synthetic.main.item_skill.view.itemName

/**
 * Created by Mohsen on 5/27/18.
 */
class LanguageRecyclerAdapter(var mContext: Context,
  var laguages: MutableList<Language>) : RecyclerView.Adapter<LanguageVH>() {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageVH {
    val view = LayoutInflater.from(parent.context)
        .inflate(layout.item_skill, parent, false)
    return LanguageVH(view)
  }

  override fun getItemCount(): Int {
    return laguages.size
  }

  override fun onBindViewHolder(holder: LanguageVH, position: Int) {
    val language = laguages[position]
    holder.mName.text = language.name
    holder.mDelete.setOnClickListener({
      laguages.remove(language)
      notifyDataSetChanged()
    })
  }

  inner class LanguageVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val mName: TextView = itemView.itemName
    val mDelete: ImageView = itemView.deleteItem

  }
}