
package com.example.geoquiz

//import android.util.Log
import androidx.lifecycle.ViewModel

//private const val TAG = "QuizViewModel"

class QuizViewModel : ViewModel() {

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

   var currentIndex : Int = 0
//       get() = currentIndex
//       set(index) {
////            if (index != currentIndex) {
//                currentIndex = index
////            }
//        }
//
    var isCheater = false

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPrevious() {
        currentIndex = if (currentIndex == 0) {
            questionBank.size - 1
        } else {
            currentIndex - 1
        }
    }

//    fun checkAnswer(userAnswer: Boolean): Boolean {
//        Log.d(TAG, "User answer: $userAnswer")
//        return userAnswer == currentQuestionAnswer
//    }
}