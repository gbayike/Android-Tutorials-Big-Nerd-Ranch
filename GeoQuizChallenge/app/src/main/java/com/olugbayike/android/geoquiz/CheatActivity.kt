package com.olugbayike.android.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.SavedStateHandle
import com.olugbayike.android.geoquiz.databinding.ActivityCheatBinding

const val EXTRA_ANSWER_SHOWN = "com.olugbayike.android.geoquiz.answer_shown"
private const val EXTRA_ANSWER_IS_TRUE =
    "com.olugbayike.android.geoquiz.answer_is_true"

class CheatActivity: AppCompatActivity() {

    private lateinit var binding: ActivityCheatBinding
    private val cheatViewModel: CheatViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setContentView(R.layout.activity_cheat)
        cheatViewModel.answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)


        val apiLevel: Int = Build.VERSION.SDK_INT
        binding.apiLevel.setText("API Level $apiLevel")
        binding.showAnswerButton.setOnClickListener {
            setCheatStatus()
            cheatViewModel.cheatButtonClick = true
        }
        if (cheatViewModel.cheatButtonClick) {
            setCheatStatus()
        }
    }

    fun setCheatStatus() {
        val answerText = cheatViewModel.answerText
        binding.answerTextView.setText(answerText)

        setAnswerShownResult(true)
    }

    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
    }

    companion object{
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }
}