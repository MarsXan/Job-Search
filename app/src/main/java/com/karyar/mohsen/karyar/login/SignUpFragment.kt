package com.karyar.mohsen.karyar.login

import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.TextInputEditText
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import com.blankj.utilcode.util.StringUtils.isEmpty
import com.karyar.mohsen.karyar.R
import com.karyar.mohsen.karyar.job.JobListFragment
import com.karyar.mohsen.karyar.transitions.Rotate
import com.karyar.mohsen.karyar.transitions.TextResize
import com.transitionseverywhere.ChangeBounds
import com.transitionseverywhere.Transition
import com.transitionseverywhere.TransitionManager
import com.transitionseverywhere.TransitionSet
import kotlinx.android.synthetic.main.sign_up_fragment.confirmPasswordEt
import kotlinx.android.synthetic.main.sign_up_fragment.confirmPasswordInput
import kotlinx.android.synthetic.main.sign_up_fragment.mobileInput
import kotlinx.android.synthetic.main.sign_up_fragment.mobileInputEt
import kotlinx.android.synthetic.main.sign_up_fragment.passwordInput
import kotlinx.android.synthetic.main.sign_up_fragment.passwordInputEt
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class SignUpFragment : AuthFragment() {

  private var views: MutableList<TextInputEditText> = mutableListOf()
  private var loginViewModel: LoginViewModel? = null
  private val textPadding: Float
    get() = resources.getDimension(R.dimen.folded_label_padding) / 2.1f

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    view.setBackgroundColor(ContextCompat.getColor(context!!, R.color.color_sign_up))
    loginViewModel = ViewModelProviders.of(activity!!)
        .get(LoginViewModel::class.java)
    arguments?.let {
      loginViewModel!!.role = it.getString(ROLE_TYPE)
    }


    loginBtn.text = getString(R.string.sign_up_label)
    views.add(mobileInputEt)
    views.add(passwordInputEt)
    views.add(confirmPasswordEt)
    initEditText()
    loginBtn.isVerticalText = true
    foldStuff()
    loginBtn.translationX = textPadding
    loginBtn.setOnClickListener({
      when {
        isEmpty(passwordInputEt.text.toString()) -> passwordInput.error =
            "رمز عبور نباید خالی باشد!"
        isEmpty(mobileInputEt.text.toString()) -> mobileInput.error =
            "شماره موبایل نباید خالی باشد!"
        isEmpty(confirmPasswordEt.text.toString()) -> confirmPasswordInput.error =
            "تکرار رمز عبور نباید خالی باشد!"
        (confirmPasswordEt.text.toString() != passwordInputEt.text.toString()) -> confirmPasswordInput.error =
            "پسوردها مطابقت ندارند!"
        else -> {
          passwordInput.isErrorEnabled = false
          mobileInput.isErrorEnabled = false
          confirmPasswordInput.isErrorEnabled = false
          loginViewModel!!.saveLoginInfo(
              LoginInfo(
                  mobileInputEt.text.toString(), passwordInputEt.text.toString(),
                  loginViewModel!!.role!!
              )
          )
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe({
                Toast.makeText(activity, "Succeed", Toast.LENGTH_LONG)
                    .show()
              }) {
                Toast.makeText(activity, "ERROR", Toast.LENGTH_LONG)
                    .show()
                Log.e("ERROR", it.toString())
              }

        }
      }
    })

  }

  override fun authLayout(): Int {
    return R.layout.sign_up_fragment
  }

  override fun clearFocus() {
    for (view in views) view.clearFocus()
  }

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
    set.addListener(object : Transition.TransitionListenerAdapter() {
      override fun onTransitionEnd(transition: Transition?) {
        super.onTransitionEnd(transition)
        loginBtn.translationX = textPadding
        loginBtn.rotation = 0f
        loginBtn.isVerticalText = true
        loginBtn.requestLayout()

      }
    })
    TransitionManager.beginDelayedTransition(parent, set)
    foldStuff()
    loginBtn.translationX = -loginBtn.width / 8 + textPadding
  }

  private fun foldStuff() {
    loginBtn.setTextSize(TypedValue.COMPLEX_UNIT_PX, loginBtn.textSize / 2f)
    loginBtn.setTextColor(Color.WHITE)
    val params = params
    params.rightToRight = ConstraintLayout.LayoutParams.UNSET
    params.verticalBias = 0.5f
    loginBtn.layoutParams = params
  }

  private fun initEditText() {
    for (editText in views) {
      if (editText.id == R.id.passwordInputEt) {
        val inputLayout = passwordInput
        val confirmLayout = confirmPasswordInput
        val boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD)
        inputLayout.setTypeface(boldTypeface)
        confirmLayout.setTypeface(boldTypeface)
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
    private const val ROLE_TYPE = "Role"
    @JvmStatic
    fun newInstance(role: String) =
      SignUpFragment().apply {
        arguments = Bundle().apply {
          putString(ROLE_TYPE, role)
        }
      }
  }
}
