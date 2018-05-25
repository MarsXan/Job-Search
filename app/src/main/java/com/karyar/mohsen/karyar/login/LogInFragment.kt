package com.karyar.mohsen.karyar.login

import android.annotation.TargetApi
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.TextInputEditText
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import com.blankj.utilcode.util.ObjectUtils.isNotEmpty
import com.blankj.utilcode.util.StringUtils.isEmpty
import com.karyar.mohsen.karyar.AppDatabase
import com.karyar.mohsen.karyar.R
import com.karyar.mohsen.karyar.job.JobListFragment
import com.karyar.mohsen.karyar.transitions.Rotate
import com.karyar.mohsen.karyar.transitions.TextResize
import com.transitionseverywhere.ChangeBounds
import com.transitionseverywhere.Transition
import com.transitionseverywhere.TransitionManager
import com.transitionseverywhere.TransitionSet
import kotlinx.android.synthetic.main.login_fragment.mobileInput
import kotlinx.android.synthetic.main.login_fragment.mobileInputEt
import kotlinx.android.synthetic.main.login_fragment.passwordInput
import kotlinx.android.synthetic.main.login_fragment.passwordInputEt

class LogInFragment : AuthFragment() {
  private var views: MutableList<TextInputEditText> = mutableListOf()
  private var loginViewModel: LoginViewModel? = null
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    loginBtn.text = getString(R.string.log_in_label)
    view.setBackgroundColor(ContextCompat.getColor(context!!, R.color.color_log_in))
    views.add(mobileInputEt)
    views.add(passwordInputEt)
    loginViewModel = ViewModelProviders.of(activity!!)
        .get(LoginViewModel::class.java)
    loginViewModel!!.setDatabase(AppDatabase.getInstance(activity!!)!!)
    arguments?.let {
      loginViewModel!!.role = it.getString(ROLE_TYPE)
    }
    initEditText()

    loginBtn.setOnClickListener({
      when {
        isEmpty(passwordInputEt.text.toString()) -> passwordInput.error =
            "رمز عبور نباید خالی باشد!"
        isEmpty(mobileInputEt.text.toString()) -> mobileInput.error =
            "شماره موبایل نباید خالی باشد!"
        else -> {
          passwordInput.isErrorEnabled=false
          mobileInput.isErrorEnabled=false
          loginViewModel!!.getLoginInfoByPhoneNumber(mobileInputEt.text.toString())
            .observe(this, Observer {
              if (isNotEmpty(it)) {
                if (it!!.password == passwordInputEt.text.toString()) {
                  Toast.makeText(activity, "Succeed", Toast.LENGTH_LONG)
                      .show()
                } else {
                  Toast.makeText(activity, "UserOrPassWrong!", Toast.LENGTH_LONG)
                      .show()
                }
              } else {
                Toast.makeText(activity, "UserNotExist!", Toast.LENGTH_LONG)
                    .show()
              }
            })}
      }
    })

  }

  override fun authLayout(): Int {
    return R.layout.login_fragment
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  override fun fold() {
    lock = false
    val transition = Rotate()
    transition.setEndAngle(-90f)
    transition.addTarget(loginBtn)
    val set = TransitionSet()
    set.duration = resources.getInteger(R.integer.duration)
        .toLong()
    val changeBounds = ChangeBounds()
    set.addTransition(changeBounds)
    set.addTransition(transition)
    val sizeTransition = TextResize()
    sizeTransition.addTarget(loginBtn)
    set.addTransition(sizeTransition)
    set.ordering = TransitionSet.ORDERING_TOGETHER
    val padding = resources.getDimension(R.dimen.folded_label_padding) / 2
    set.addListener(object : Transition.TransitionListenerAdapter() {
      override fun onTransitionEnd(transition: Transition?) {
        super.onTransitionEnd(transition)
        loginBtn.translationX = -padding
        loginBtn.rotation = 0f
        loginBtn.isVerticalText = true
        loginBtn.requestLayout()

      }
    })
    TransitionManager.beginDelayedTransition(parent, set)
    loginBtn.setTextSize(TypedValue.COMPLEX_UNIT_PX, loginBtn.textSize / 2)
    loginBtn.setTextColor(Color.WHITE)
    val params = params
    params.leftToLeft = ConstraintLayout.LayoutParams.UNSET
    params.verticalBias = 0.5f
    loginBtn.layoutParams = params
    loginBtn.translationX = loginBtn.width / 8 - padding
  }

  override fun clearFocus() {
    for (view in views) view.clearFocus()
  }

  private fun initEditText() {
    for (editText in views) {
      if (editText.id == R.id.passwordInputEt) {
        val inputLayout = passwordInput
        val boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD)
        inputLayout.setTypeface(boldTypeface)
        editText.addTextChangedListener(object : TextWatcherAdapter() {
          override fun afterTextChanged(s: Editable) {
            inputLayout.isPasswordVisibilityToggleEnabled = s.isNotEmpty()
          }
        })
      }
      editText.setOnFocusChangeListener { _, hasFocus ->
        if (!hasFocus) {
          val isEnabled = editText.text.isNotEmpty()
          editText.isSelected = isEnabled
        }
      }
    }
  }

  companion object {
    private const val ROLE_TYPE = "ROLE"
    @JvmStatic
    fun newInstance(role: String) =
      JobListFragment().apply {
        arguments = Bundle().apply {
          putString(ROLE_TYPE, role)
        }
      }
  }

}
