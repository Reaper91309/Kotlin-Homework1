package com.example.lab3

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var edName: EditText
    private lateinit var tvText: TextView
    private lateinit var radioGroup: RadioGroup
    private lateinit var btnMora: Button
    private lateinit var tvName: TextView
    private lateinit var tvWinner: TextView
    private lateinit var tvMyMora: TextView
    private lateinit var tvTargetMora: TextView

    // 在 companion object 中定義常數，取代 magic numbers
    private companion object {
        const val SCISSORS = 0
        const val STONE = 1
        const val PAPER = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initializeViews()

        btnMora.setOnClickListener {
            playGame()
        }
    }

    private fun initializeViews() {
        edName = findViewById(R.id.edName)
        tvText = findViewById(R.id.tvText)
        radioGroup = findViewById(R.id.radioGroup)
        btnMora = findViewById(R.id.btnMora)
        tvName = findViewById(R.id.tvName)
        tvWinner = findViewById(R.id.tvWinner)
        tvMyMora = findViewById(R.id.tvMyMora)
        tvTargetMora = findViewById(R.id.tvTargetMora)
    }

    private fun playGame() {
        val playerName = edName.text.toString()

        if (playerName.isBlank()) {
            tvText.text = "請輸入玩家姓名"
            return
        }

        val playerMove = getPlayerMove()
        val computerMove = (SCISSORS..PAPER).random() // 語意更清晰的隨機數產生

        updateMovesUI(playerName, playerMove, computerMove)

        determineWinner(playerName, playerMove, computerMove)
    }
    private fun getPlayerMove(): Int {
        return when (radioGroup.checkedRadioButtonId) {
            R.id.btnScissor -> SCISSORS
            R.id.btnStone -> STONE
            else -> PAPER
        }
    }
    private fun getMoveString(move: Int): String {
        return when (move) {
            SCISSORS -> "剪刀"
            STONE -> "石頭"
            else -> "布"
        }
    }
    private fun updateMovesUI(playerName: String, playerMove: Int, computerMove: Int) {
        tvName.text = "名字\n$playerName"
        tvMyMora.text = "我方出拳\n${getMoveString(playerMove)}"
        tvTargetMora.text = "電腦出拳\n${getMoveString(computerMove)}"
    }
    private fun determineWinner(playerName: String, playerMove: Int, computerMove: Int) {
        when {
            playerMove == computerMove -> {
                tvWinner.text = "勝利者\n平手"
                tvText.text = "平局，請再試一次！"
            }
            (playerMove == PAPER && computerMove == STONE) ||
                    (playerMove == STONE && computerMove == SCISSORS) ||
                    (playerMove == SCISSORS && computerMove == PAPER) -> {
                tvWinner.text = "勝利者\n$playerName"
                tvText.text = "恭喜你獲勝了！！！"
            }
            else -> {
                tvWinner.text = "勝利者\n電腦"
                tvText.text = "可惜，電腦獲勝了！"
            }
        }
    }
}