package com.example.braintrener

import android.R.*
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.braintrener.R.color.*
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var textViewTimer: TextView
    lateinit var textViewOnion0: TextView
    lateinit var textViewOnion1: TextView
    lateinit var textViewOnion2: TextView
    lateinit var textViewOnion3: TextView
    lateinit var textViewScore: TextView
    lateinit var textViewQuestion: TextView
    private var options: ArrayList<TextView?>? = null
    private var question: String? = null
    private var rightAnswer = 0
    private val rightQuestion = 0
    private var rightAnswerposition = 0
    private var isPositiv = false
    private val min = 5
    private val max = 30
    private var countOfQuestion = 0
    private var countOfRightAnswer = 0
    private var gameOver = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textViewOnion0 = findViewById(R.id.textViewOpininon0)
        textViewOnion1 = findViewById(R.id.textViewOpinin1)
        textViewOnion2 = findViewById(R.id.textViewOpinion2)
        textViewOnion3 = findViewById(R.id.textViewOpinion3)
        textViewScore = findViewById(R.id.textViewScore)
        textViewQuestion = findViewById(R.id.textViewQuestion)
        textViewTimer = findViewById(R.id.textViewTimer)
        options = ArrayList()
        options?.add(textViewOnion0)
        options?.add(textViewOnion1)
        options?.add(textViewOnion2)
        options?.add(textViewOnion3)
        playGame()
        val countDownTimer: CountDownTimer = object : CountDownTimer(15000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                textViewTimer.setText(getTime(millisUntilFinished))
                if (millisUntilFinished < 10000) {
                    textViewTimer.setTextColor(Color.parseColor("#e34234"))
                   

                }
            }

            override fun onFinish() {
                gameOver = true
                val preferences = getSharedPreferences("sharedpref", Context.MODE_PRIVATE)
                val max = preferences.getInt("max", 0)
                if (countOfRightAnswer >= max) {
                    preferences.edit().putInt("max", countOfRightAnswer).apply()
                }
                val intent = Intent(this@MainActivity, ScoreActivity::class.java)
                intent.putExtra("res", countOfRightAnswer)
                startActivity(intent)
            }
        }
        countDownTimer.start()
    }

    private fun generateQuestion() {
        val a = (Math.random() * (max - min + 1) + min).toInt()
        val b = (Math.random() * (max - min + 1) + min).toInt()
        val mark = (Math.random() * 2).toInt()
        isPositiv = mark == 1
        if (isPositiv) {
            rightAnswer = a + b
            question = String.format("%s + %s", a, b)
        } else {
            rightAnswer = a - b
            question = String.format("%s - %s", a, b)
        }
        textViewQuestion.text = question
        rightAnswerposition = (Math.random() * 4).toInt()
    }

    private fun generateWrongAnswer(): Int {
        var res: Int
        do {
            res = (Math.random() * max * 2 + 1 - (max - min)).toInt()
        } while (res == rightAnswer)
        return res
    }

    fun onClickAnswer(view: View) {
        if (!gameOver) {
            val textView = view as TextView
            val answer = textView.text.toString()
            val chooseAnswer = answer.toInt()
            if (chooseAnswer == rightAnswer) {
                countOfRightAnswer++
                Toast.makeText(this, "Верно", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Неверно", Toast.LENGTH_SHORT).show()
            }
            countOfQuestion++
            playGame()
        }
    }

    private fun playGame() {
        generateQuestion()
        for (i in options!!.indices) {
            if (i == rightAnswerposition) {
                options!![i]!!.text = rightAnswer.toString()
            } else {
                options!![i]!!.text = generateWrongAnswer().toString()
            }
        }
        val score = String.format("%s / %s", countOfRightAnswer, countOfQuestion)
        textViewScore.text = score
    }

    private fun getTime(mills: Long): String {
        var seconds = mills.toInt() / 1000
        val min = seconds / 60
        seconds = seconds % 60
        return String.format("%02d:%02d", min, seconds)
    }
}


