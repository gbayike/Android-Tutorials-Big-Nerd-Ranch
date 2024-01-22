package com.olugbayike.android.geoquiz

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"
const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"

class QuizViewModel (private val savedStateHandle: SavedStateHandle) : ViewModel(){

    private val questionBank = listOf(
        Question(R.string.question_text_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    private val questionTodos = MutableList(questionBank.size) {false}

    private val answer = 0
    private var currentIndex: Int
        get() = savedStateHandle.get(CURRENT_INDEX_KEY) ?: 0
        set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)

    val currentQuestionAnswer:Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    val currentQuestionTodo: Boolean
        get() = questionTodos[currentIndex]

    fun moveToNext(){
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun setCurrentQuestionTodo(status: Boolean){
        questionTodos[currentIndex] = status
    }

}