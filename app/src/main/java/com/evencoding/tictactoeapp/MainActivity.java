package com.evencoding.tictactoeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView playerOneScore, playerTwoScore, playerStatus;
    private Button[] buttons = new Button[9];
    private Button resetGame;

    int[][] winningPositions = {
            {0,1,2},{3,4,5},{6,7,8},
            {0,3,6},{1,4,7},{2,5,8},
            {0,4,8},{2,4,6}
    };

    boolean activePlayer;
    //p1 -> 0
    //p2 -> 1
    //empty -> 2
    int[] gameState = {20,2,2,2,2,2,2,2,2};
    private int playerOneScoreCount, playerTwoScoreCount, roundCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerOneScore = (TextView)findViewById(R.id.playerOneScore);
        playerTwoScore = (TextView)findViewById(R.id.playerTwoScore);
        playerStatus = (TextView)findViewById(R.id.playerStatus);

        resetGame = (Button)findViewById(R.id.resetGame);

        for(int i = 0; i < buttons.length; i++){
            int resourceID = getResources().getIdentifier("btn_" + i, "id", getPackageName() );
            buttons[i] = (Button)findViewById(resourceID);
            buttons[i].setOnClickListener(this);
        }

        playerOneScoreCount = 0;
        playerTwoScoreCount = 0;
        activePlayer = true;
        roundCount = 0;

        resetGame.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                playAgain();
                playerOneScoreCount = 0;
                playerTwoScoreCount = 0;
                updatePlayerScore();
            }
        });
    }

    @Override
    public void onClick(View view) {
        Log.i("test", "Button is clicked");
        if(!((Button)view).getText().toString().equals("")){
            return;
        }

        String button_ID = view.getResources().getResourceEntryName(view.getId()); //btn_2
        int gameStatePointer = Integer.parseInt(button_ID.substring(button_ID.length()-1, button_ID.length()));  //2

        if (activePlayer){
            ((Button)view).setText("X");
            ((Button)view).setTextColor(Color.parseColor("#FFC34A"));
            gameState[gameStatePointer] = 0;
        } else {
            ((Button)view).setText("O");
            ((Button)view).setTextColor(Color.parseColor("#70FFEA"));
            gameState[gameStatePointer] = 1;
        }

        roundCount++;

        if(checkWinner()){
            if(activePlayer){
                playerOneScoreCount++;
                Toast.makeText(this,"Player One Won!", Toast.LENGTH_SHORT ).show();
            } else {
                playerTwoScoreCount++;
                Toast.makeText(this,"Player Two Won!", Toast.LENGTH_SHORT ).show();
            }
            updatePlayerScore();
            playAgain();
            return;
        } else if(roundCount == 9){
            Toast.makeText(this, "No winner!", Toast.LENGTH_SHORT).show();
            playAgain();
            return;
        }
        activePlayer = !activePlayer;



    }

    public boolean checkWinner(){
        boolean winnerResult = false;
        for (int[] winPos: winningPositions){
            if (gameState[winPos[0]] == gameState[winPos[1]] &&
                    gameState[winPos[0]] == gameState[winPos[2]] &&
                        gameState[winPos[0]] !=2){
                winnerResult = true;
            }
        }
        return winnerResult;
    }

    public void updatePlayerScore(){
        playerOneScore.setText(Integer.toString(playerOneScoreCount));
        playerTwoScore.setText(Integer.toString(playerTwoScoreCount));
        if(playerOneScoreCount > playerTwoScoreCount){
            playerStatus.setText("Player One is winning!");
        } else if(playerTwoScoreCount > playerOneScoreCount){
            playerStatus.setText("Player Two is winning!");
        } else {
            playerStatus.setText("");
        }
    }

    public void playAgain(){
        roundCount = 0;
        activePlayer = true;

        for( int i = 0; i < buttons.length; i++ ){
            buttons[i].setText("");
            gameState[i] = 2;
        }
    }
}