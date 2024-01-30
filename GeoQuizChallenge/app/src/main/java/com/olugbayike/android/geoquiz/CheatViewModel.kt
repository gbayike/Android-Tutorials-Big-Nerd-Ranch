package com.olugbayike.android.geoquiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

const val ANSWER_IS_TRUE_KEY = "ANSWER_IS_TRUE_KEY"
const val CHEAT_BUTTON_CLICK = "CHEAT_BUTTON_CLICK"

class CheatViewModel (private val savedStateHandle: SavedStateHandle): ViewModel() {
    var answerIsTrue: Boolean
        get() = savedStateHandle.get(ANSWER_IS_TRUE_KEY)?: false
        set(value) = savedStateHandle.set(ANSWER_IS_TRUE_KEY,value)

//    fun setAnswerIsTrue(answer: Boolean) {
//        answerIsTrue = answer
//    }

    var cheatButtonClick: Boolean
        get() = savedStateHandle.get(CHEAT_BUTTON_CLICK)?: false
        set(value) = savedStateHandle.set(CHEAT_BUTTON_CLICK,value)

    val answerText : Int
        get() = when {
        answerIsTrue -> R.string.true_button
        else -> R.string.false_button
    }
}