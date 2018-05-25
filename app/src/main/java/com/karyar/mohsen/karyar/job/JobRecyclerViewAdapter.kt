package com.karyar.mohsen.karyar.job

import android.arch.lifecycle.Observer
import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.blankj.utilcode.util.ObjectUtils
import com.blankj.utilcode.util.ObjectUtils.isNotEmpty
import com.karyar.mohsen.karyar.R.layout
import com.karyar.mohsen.karyar.job.JobRecyclerViewAdapter.ViewHolder

import kotlinx.android.synthetic.main.fragment_job.view.*

class JobRecyclerViewAdapter(
  private val mValues: List<Job>, private val mJobViewModel: JobViewModel, var mFragment: Fragment)
  : RecyclerView.Adapter<ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context)
        .inflate(layout.fragment_job, parent, false)
    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val item = mValues[position]
    holder.mName.text = item.id.toString()
    holder.mCity.text = item.name
    mJobViewModel.getJobCompanyWithId(item.companyId)
        .observe(mFragment, Observer {
          if (isNotEmpty(it))
            holder.mCompany.text = it!!.name
        })

  }

  override fun getItemCount(): Int = mValues.size

  inner class ViewHolder(private val mView: View) : RecyclerView.ViewHolder(mView) {
    val mName: TextView = mView.name
    val mCity: TextView = mView.city
    val mCompany: TextView = mView.company
  }
}
