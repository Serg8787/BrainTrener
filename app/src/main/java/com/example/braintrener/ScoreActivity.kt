package com.example.braintrener

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ScoreActivity : AppCompatActivity() {
    lateinit var textViewscore: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)
        textViewscore = findViewById(R.id.textViewScore)
        val intent = intent
        if (intent != null && intent.hasExtra("res"));
        val res = intent!!.getIntExtra("res", 0)
//        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val preferences = getSharedPreferences("sharedpref", Context.MODE_PRIVATE)
        val max = preferences.getInt("max", 0)
        val result = String.format("Ваш результат %s\nМаксимальный результат %s", res, max)
        textViewscore.setText(result)
    }

    fun onclickStartGame(view: View?) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}