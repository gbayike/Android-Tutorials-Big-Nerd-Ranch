package com.olugbayike.android.geoquiz

import androidx.lifecycle.SavedStateHandle
import org.junit.Test
import org.junit.Assert.*

class QuizViewModelTest{
    @Test
    fun provideExpectedQuestionText() {
        val savedStateHandle = SavedStateHandle(mapOf(CURRENT_INDEX_KEY to 5));
        val quizViewModel = QuizViewModel(savedStateHandle)
        assertEquals(R.string.question_asia, quizViewModel.currentQuestionText)
        val currentAnswer = quizViewModel.currentQuestionAnswer
        assertTrue(currentAnswer)
        quizViewModel.moveToNext()
        assertEquals(R.string.question_text_australia, quizViewModel.currentQuestionText)
    }
}