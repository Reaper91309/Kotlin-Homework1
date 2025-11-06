package com.example.lab3;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final int SCISSORS = 0;
    private static final int STONE = 1;
    private static final int PAPER = 2;

    private EditText ed_name;
    private TextView tv_text, tv_name, tv_winner, tv_mmora, tv_cmora;
    private RadioButton btn_scissor, btn_stone, btn_paper;
    private Button btn_mora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();

        btn_mora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playGame();
            }
        });
    }
    private void initializeViews() {
        ed_name = findViewById(R.id.ed_name);
        tv_text = findViewById(R.id.tv_text);
        tv_name = findViewById(R.id.tv_name);
        tv_winner = findViewById(R.id.tv_winner);
        tv_mmora = findViewById(R.id.tv_mmora);
        tv_cmora = findViewById(R.id.tv_cmora);
        btn_scissor = findViewById(R.id.btn_scissor);
        btn_stone = findViewById(R.id.btn_stone);
        btn_paper = findViewById(R.id.btn_paper);
        btn_mora = findViewById(R.id.btn_mora);
    }
    private void playGame() {
        String playerName = ed_name.getText().toString();

        if (TextUtils.isEmpty(playerName)) {
            tv_text.setText("請輸入玩家姓名");
            return;
        }

        int playerMove = getPlayerMove();
        int computerMove = (int) (Math.random() * 3);

        updateMovesUI(playerName, playerMove, computerMove);

        determineWinner(playerName, playerMove, computerMove);
    }
    private int getPlayerMove() {
        if (btn_scissor.isChecked()) {
            return SCISSORS;
        } else if (btn_stone.isChecked()) {
            return STONE;
        } else {
            return PAPER;
        }
    }
    private String getMoveString(int move) {
        switch (move) {
            case SCISSORS:
                return "剪刀";
            case STONE:
                return "石頭";
            case PAPER:
                return "布";
            default:
                return "";
        }
    }
    private void updateMovesUI(String playerName, int playerMove, int computerMove) {
        tv_name.setText(String.format("名字\n%s", playerName));
        tv_mmora.setText(String.format("我方出拳\n%s", getMoveString(playerMove)));
        tv_cmora.setText(String.format("電腦出拳\n%s", getMoveString(computerMove)));
    }
    private void determineWinner(String playerName, int playerMove, int computerMove) {
        if (playerMove == computerMove) {
            tv_winner.setText("勝利者\n平手");
            tv_text.setText("平局，請再試一次！");
        } else if ((playerMove == PAPER && computerMove == STONE) ||
                (playerMove == STONE && computerMove == SCISSORS) ||
                (playerMove == SCISSORS && computerMove == PAPER)) {
            tv_winner.setText("勝利者\n" + playerName);
            tv_text.setText("恭喜您獲勝了！！！");
        } else {
            tv_winner.setText("勝利者\n電腦");
            tv_text.setText("可惜，電腦獲勝了！");
        }
    }
}