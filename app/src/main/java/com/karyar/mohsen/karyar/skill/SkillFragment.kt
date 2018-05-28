package com.karyar.mohsen.karyar.skill

import android.graphics.Color
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.TextInputEditText
import android.util.TypedValue
import android.view.View
import com.blankj.utilcode.util.StringUtils
import com.karyar.mohsen.karyar.R
import com.karyar.mohsen.karyar.language.Language
import com.karyar.mohsen.karyar.profile.ProfileBaseFragment
import com.karyar.mohsen.karyar.transitions.Rotate
import com.karyar.mohsen.karyar.transitions.TextResize
import com.transitionseverywhere.ChangeBounds
import com.transitionseverywhere.Transition
import com.transitionseverywhere.TransitionManager
import com.transitionseverywhere.TransitionSet
import kotlinx.android.synthetic.main.fragment_skill.addLanguageBtn
import kotlinx.android.synthetic.main.fragment_skill.addSkillBtn
import kotlinx.android.synthetic.main.fragment_skill.languageInputEt
import kotlinx.android.synthetic.main.fragment_skill.languageRecycler
import kotlinx.android.synthetic.main.fragment_skill.skillInputEt
import kotlinx.android.synthetic.main.fragment_skill.skillRecycler

class SkillFragment : ProfileBaseFragment() {
  private var views: MutableList<TextInputEditText> = mutableListOf()
  private val textPadding: Float
    get() = resources.getDimension(R.dimen.folded_label_padding) / 2.1f

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    arguments?.let {

    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    verticalTv.text = "مهارت ها"
    verticalTv.isVerticalText = true
    foldStuff()
    verticalTv.translationX = textPadding

    val skillList = mutableListOf<Skill>()
    var simpleItemRecyclerAdapter = SimpleItemRecyclerAdapter(context!!, skillList)
    skillRecycler.adapter = simpleItemRecyclerAdapter

    addSkillBtn.setOnClickListener({

      if (!StringUtils.isEmpty(skillInputEt.text.toString())) {
        skillList.add(Skill(null, skillInputEt.text.toString()))
        skillInputEt.setText("")
        simpleItemRecyclerAdapter.notifyDataSetChanged()
      }
    })
    val languageList = mutableListOf<Language>()
    simpleItemRecyclerAdapter = SimpleItemRecyclerAdapter(context!!, languageList)
    languageRecycler.adapter = simpleItemRecyclerAdapter

    addLanguageBtn.setOnClickListener({

      if (!StringUtils.isEmpty(languageInputEt.text.toString())) {
        languageList.add(Language(null, languageInputEt.text.toString()))
        languageInputEt.setText("")
        simpleItemRecyclerAdapter.notifyDataSetChanged()
      }
    })

  }

  override fun authLayout(): Int {
    return R.layout.fragment_skill
  }

  override fun clearFocus() {
    for (view in views) view.clearFocus()
  }

  override fun fold() {
    lock = false
    val transition = Rotate()
    transition.setEndAngle(-90f)
    transition.addTarget(verticalTv)
    val set = TransitionSet()
    set.duration = resources.getInteger(R.integer.duration)
        .toLong()
    val changeBounds = ChangeBounds()
    set.addTransition(changeBounds)
    set.addTransition(transition)
    val sizeTransition = TextResize()
    sizeTransition.addTarget(verticalTv)
    set.addTransition(sizeTransition)
    set.ordering = TransitionSet.ORDERING_TOGETHER
    set.addListener(object : Transition.TransitionListenerAdapter() {
      override fun onTransitionEnd(transition: Transition?) {
        super.onTransitionEnd(transition)
        verticalTv.translationX = textPadding
        verticalTv.rotation = 0f
        verticalTv.isVerticalText = true
        verticalTv.requestLayout()
        verticalTv.text = "مهارت ها"

      }
    })
    TransitionManager.beginDelayedTransition(parent, set)
    foldStuff()
    verticalTv.translationX = -verticalTv.width / 8 + textPadding
  }

  private fun foldStuff() {
    verticalTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, verticalTv.textSize / 2f)
    verticalTv.setTextColor(Color.WHITE)
    val params = params
    params.rightToRight = ConstraintLayout.LayoutParams.UNSET
    params.verticalBias = 0.5f
    verticalTv.layoutParams = params
  }

  companion object {

    @JvmStatic fun newInstance() =
      SkillFragment().apply {
        arguments = Bundle().apply {

        }
      }
  }
}
