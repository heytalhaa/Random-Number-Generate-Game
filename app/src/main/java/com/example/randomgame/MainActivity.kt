package com.example.randomgame

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
   private lateinit var player1TV : TextView
    private lateinit  var player2TV : TextView
    private lateinit var score1TV : TextView
    private lateinit var score2TV : TextView
    private lateinit  var timerTV : TextView
    private lateinit  var button : Button
    private lateinit  var result : TextView
    private lateinit  var resetBtn : Button
    private lateinit var setRange : EditText
    private lateinit var setTimer : EditText

    private var player1TotalScore =0
    private var player2TotalScore =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        player1TV = findViewById(R.id.player1TV)
        player2TV = findViewById(R.id.player2TV)
         score1TV = findViewById(R.id.score1TV)
         score2TV = findViewById(R.id.score2TV)
         timerTV = findViewById(R.id.timerTextView)
         button = findViewById(R.id.rollButton)
         result = findViewById(R.id.resultTextView)
        setRange = findViewById(R.id.setRangeET)
        setTimer = findViewById(R.id.setTimerET)
        resetBtn = findViewById(R.id.resetBtn)


        button.setOnClickListener {
            startGame()
        }
        resetBtn.setOnClickListener {
           setRange.text.clear()
            setTimer.text.clear()
            player1TV.text = "Player 1:"
            player2TV.text = "Player 2:"
            score1TV.text = "Score:"
            score2TV.text = "Score:"
            timerTV.text = ""
            result.text = ""
        }
    }
    private fun startGame(){
        button.isEnabled = false

        player1TotalScore = 0
        player2TotalScore = 0
        result.text = ""
        var setRangeNumber = Integer.parseInt(setRange.text.toString())
        val setTimerNumbers = setTimer.text.toString().toLongOrNull() ?: 0L
        val durationInMillis = setTimerNumbers * 1000

        if (setTimerNumbers > 0) {
            object : CountDownTimer(durationInMillis, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    timerTV.text = "Timer : ${millisUntilFinished / 1000}"

                    val player1Random = Random.nextInt(setRangeNumber)
                    val player2Random = Random.nextInt(setRangeNumber)
                    player1TV.text = "Player 1: $player1Random"
                    player2TV.text = "Player 2: $player2Random"

                    player1TotalScore += player1Random
                    player2TotalScore += player2Random

                    score1TV.text = "Score: $player1TotalScore"
                    score2TV.text = "Score: $player2TotalScore"
                }

                override fun onFinish() {
                    timerTV.text = "Time's up!"
                    button.isEnabled = true
                    winner()
                }
            }.start()
        } else{
            Toast.makeText(this, "Please Enter Valid Number!",Toast.LENGTH_SHORT)
        }
    }

    private fun winner() {
        if (player1TotalScore > player2TotalScore){
            result.text = "Player 1 Wins!"
        }else if (player1TotalScore < player2TotalScore){
            result.text = "Player 2 wins!"
        }else{
            result.text = "It's Draw!"
        }
    }
}