package com.olugbayike.android.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.olugbayike.android.geoquiz.databinding.ActivityMainBinding

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var textView: TextView

    private val questionBank = listOf(
        Question(R.string.question_text_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    private val answer = 0
    private var currentIndex = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        trueButton = findViewById(R.id.true_button)
//        falseButton = findViewById(R.id.false_button)
//        textView = findViewById(R.id.text)

        binding.text.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        binding.trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
            if (binding.trueButton.isEnabled()){
                binding.trueButton.setEnabled(false)
            }
            if (binding.falseButton.isEnabled()){
                binding.falseButton.setEnabled(false)
            }
        }

        binding.falseButton.setOnClickListener { view: View ->
            checkAnswer(false)
            if (binding.trueButton.isEnabled()){
                binding.trueButton.setEnabled(false)
            }
            if (binding.falseButton.isEnabled()){
                binding.falseButton.setEnabled(false)
            }

        }

        binding.nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
            if (!binding.trueButton.isEnabled()){
                binding.trueButton.setEnabled(true)
            }
            if (!binding.falseButton.isEnabled()){
                binding.falseButton.setEnabled(true)
            }
        }

        updateQuestion()

        binding.previousButton.setOnClickListener {
            if (currentIndex > 0) {
                currentIndex = (currentIndex - 1) % questionBank.size
            }
                updateQuestion()
        }
        updateQuestion()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }


    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun updateQuestion(){
        val questionTextResId = questionBank[currentIndex].textResId
        binding.text.setText(questionTextResId)
    }



    private fun checkAnswer(userAnswer: Boolean){
        val correctAnswer = questionBank[currentIndex].answer

        val messageResId = if (userAnswer == correctAnswer){
            answer+1
            R.string.correct_toast
        }else{
            R.string.incorrect_toast
        }

        Snackbar.make(
            binding.text,
            messageResId,
            Snackbar.LENGTH_SHORT
        ).show()
    }
}