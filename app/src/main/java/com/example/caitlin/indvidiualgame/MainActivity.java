package com.example.caitlin.indvidiualgame;

import android.content.pm.ActivityInfo;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;


public class MainActivity extends ActionBarActivity {

    public static final String TAG = "QUACKGAME";
    private static final int numLetters = 5;

    private int level = 0;


    private String targetWord;
    private String partialWord = "";

    private TextView levelDisplay;
    private TextView quackProgressTextView;
    private TextView timerTextView;
    private ArrayList<Button> buttonList;
    private Button backspaceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //determine screen size
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // get references to GUI components
        quackProgressTextView = //displays letters player has clicked
                (TextView) findViewById(R.id.quackProgressTextView);
        quackProgressTextView.setText("");

        timerTextView = (TextView) findViewById(R.id.timerTextView);
        timer.start();

        levelDisplay = (TextView) findViewById(R.id.levelDisplayTextView);
        levelDisplay.setText(
                getResources().getString(R.string.level, level));

        // configure listeners for backspace button
        backspaceButton = (Button) findViewById(R.id.backspaceButton);
        backspaceButton.setOnClickListener(backspaceButtonListener);

        buttonList = new ArrayList<Button>();

        buttonList.add((Button) findViewById(R.id.button1));
        buttonList.add((Button) findViewById(R.id.button2));
        buttonList.add((Button) findViewById(R.id.button3));
        buttonList.add((Button) findViewById(R.id.button4));
        buttonList.add((Button) findViewById(R.id.button5));

        targetWord = getResources().getString(R.string.target_word);


        newLevel();
    } // end onCreate




    public ArrayList<String> getShuffledLetters(String targetWord){
        ArrayList<String> letters = new ArrayList<String>();

        for(int i = 0; i< targetWord.length(); i++){
            String letter = Character.toString(targetWord.charAt(i));
            letters.add(letter);
        }

        Collections.shuffle(letters);

        return letters;
    }



    public OnClickListener letterButtonListener = new OnClickListener() {
        @Override
        public void onClick(View v) {

            Button clickedButton = (Button) v;
            String letter = clickedButton.getText().toString();
            partialWord = partialWord + letter;
            quackProgressTextView.setText(partialWord);


            if(partialWord.equalsIgnoreCase(targetWord)){
                newLevel();
            }

        }
    };


    public OnClickListener backspaceButtonListener = new OnClickListener() {
        @Override
        public void onClick(View v) {

            if(partialWord.length() > 0){
                partialWord = partialWord.substring(0, partialWord.length()-1);
            }else{
                partialWord = "";
            }
            quackProgressTextView.setText(partialWord);
        }
    };

    public void newLevel(){
        level++;
        levelDisplay.setText(getResources().getString(R.string.level, level));
        partialWord = "";
        quackProgressTextView.setText(partialWord);

        ArrayList<String> shuffledLetters = getShuffledLetters(targetWord);

        int index = 0;
        for (Button b : buttonList)
        {
            b.setText(shuffledLetters.get(index));
            b.setOnClickListener(letterButtonListener);
            index++;
        }
    }


    @Override
    protected void onStart(){
        super.onStart();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    CountDownTimer timer =  new CountDownTimer(30000, 1000) {

        public void onTick(long millisUntilFinished) {
            timerTextView.setText("Time: " + millisUntilFinished / 1000);
        }

    public void onFinish() {
        timerTextView.setText("done!");
    }
    };
}
