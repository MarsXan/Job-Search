package com.karyar.mohsen.karyar.employer

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_new_company.*
import com.karyar.mohsen.karyar.R
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import android.widget.ArrayAdapter
import com.karyar.mohsen.karyar.AppDatabase

class NewCompanyFragment : Fragment() {
  var companyViewModel: CompanyViewModel? = null
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?): View? {
    companyViewModel = ViewModelProviders.of(this)
        .get(CompanyViewModel::class.java)
    companyViewModel!!.setAppDataBase(AppDatabase.getInstance(context!!))
    return inflater.inflate(R.layout.fragment_new_company, container, false)

  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    workerCountSp.setOnSpinnerItemClickListener({ _, _ ->

    })
    workerCountSp.setAdapter(spinnerAdapterGenerator())

  }

  private fun saveCompany(company: Company) {
    companyViewModel!!.saveCompany(company)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
          Toast.makeText(activity, "Saved", Toast.LENGTH_LONG)
              .show()
        }) {
          Toast.makeText(activity, "ERROR", Toast.LENGTH_LONG)
              .show()
          Log.e("ERROR", it.toString())
        }
  }

  private fun spinnerAdapterGenerator(): ArrayAdapter<String> {
    val categories = ArrayList<String>()
    categories.add("یک نفر")
    categories.add("۲-۱۰ نفر")
    categories.add("۱۱-۵۰ نفر")
    categories.add("۵۱-۲۰۰ نفر")
    categories.add("۲۰۱-۱۰۰۰ نفر")
    return ArrayAdapter(context, android.R.layout.simple_spinner_item, categories)

  }

  companion object {
    @JvmStatic
    fun newInstance() =
      NewCompanyFragment().apply {
        arguments = Bundle().apply {

        }
      }
  }
}
