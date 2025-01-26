package com.example.geoquiz

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.ViewModelProvider

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"
//private const val REQUEST_CODE_CHEAT = 0

class MainActivity : ComponentActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button

    //    private lateinit var previousButton: Button
//    private lateinit var nextButton: Button
    private lateinit var previousButton: ImageButton
    private lateinit var nextButton: ImageButton
    private lateinit var questionTextView: TextView
    private lateinit var cheatButton: Button

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(this)[QuizViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "onCreate(Bundle?) called")

        setContentView(R.layout.activity_main)
//        val viewModelProvider = ViewModelProvider(this)
//        val quizViewModel = viewModelProvider[QuizViewModel::class.java]
//        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")
        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex

        enableEdgeToEdge()

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        previousButton = findViewById(R.id.previous_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.question_text_view)
        cheatButton = findViewById(R.id.cheat_button)

        trueButton.setOnClickListener { _: View ->
//            val toast = Toast.makeText(this, R.string.correct_toast, Toast.LENGTH_SHORT)
//            toast.setGravity(Gravity.START, 100, 100)
//            toast.show()
            checkAnswer(true)
        }
        falseButton.setOnClickListener { _: View ->
//            Toast.makeText(this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show()
            checkAnswer(false)
        }

        previousButton.setOnClickListener {
//            currentIndex -= 1
//            if (currentIndex < 0) {
//                currentIndex = questionBank.size - 1
//            }
            quizViewModel.moveToPrevious()
            updateQuestion()
        }

        nextButton.setOnClickListener {
//            currentIndex = (currentIndex + 1) % questionBank.size
            quizViewModel.moveToNext()
            updateQuestion()
        }

        cheatButton.setOnClickListener { view ->
//            // Start CheatActivity
////            val intent = Intent(this, CheatActivity::class.java)
//            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this, quizViewModel.currentQuestionAnswer)
////            startActivity(intent)
            val options = ActivityOptionsCompat.makeClipRevealAnimation(view, 0, 0, view.width, view.height)
            cheatActivityResultLauncher.launch(intent, options)
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i(TAG, "onSaveInstanceState")
        outState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }

    private fun updateQuestion() {
//        Log.d(TAG, "Current index: $currentIndex")
//        try {
//            val questTextResId = questionBank[currentIndex].textResId
//            questionTextView.setText(questTextResId)
//        } catch (e: ArrayIndexOutOfBoundsException) {
//            Log.e(TAG, "Index was out of bounds", e)
//        }
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
//        val correctAnswer = questionBank[currentIndex].answer
        val correctAnswer = quizViewModel.currentQuestionAnswer
//        val messageResId = if (quizViewModel.checkAnswer(userAnswer)) R.string.correct_toast else R.string.incorrect_toast

        val messageResId = when {
            quizViewModel.isCheater -> R.string.judgment_toast
//            quizViewModel.checkAnswer(userAnswer) -> R.string.correct_toast
//            else -> R.string.incorrect_toast
            userAnswer == correctAnswer -> R.string.correct_toast
            else -> R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode != RESULT_OK) {
//            return
//        }
//        if (requestCode == REQUEST_CODE_CHEAT) {
//            quizViewModel.isCheater = CheatActivity.wasAnswerShown(data ?: return)
//        }
//    }

    private val cheatActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            quizViewModel.isCheater =
                CheatActivity.wasAnswerShown(data ?: return@registerForActivityResult)
        }
    }

}