package com.karyar.mohsen.karyar.job

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.karyar.mohsen.karyar.R.layout

class JobListFragment : Fragment() {
private var mJobViewModel :JobViewModel?=null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    arguments?.let {

    }
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?): View? {
    val view = inflater.inflate(layout.fragment_job_list, container, false)
    mJobViewModel = ViewModelProviders.of(this).get(JobViewModel::class.java)
    // Set the adapter
    val mFragment = this
    if (view is RecyclerView) {
      with(view) {
        layoutManager = LinearLayoutManager(context)
        adapter = JobRecyclerViewAdapter(
            mutableListOf(),mJobViewModel!!,mFragment
        )
      }
    }
    return view
  }





  companion object {
    @JvmStatic
    fun newInstance(columnCount: Int) =
      JobListFragment().apply {
        arguments = Bundle().apply {

        }
      }
  }
}
