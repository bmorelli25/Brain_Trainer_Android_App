package com.example.morellib.braintrainer;

//Simple Android Math App. 30 seconds to answer as many math questions as possible.
//There are four multiple choice answers. Your score is tallied as you go.

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //Assorted textviews must be setup
    TextView timerText;
    TextView equationText;
    TextView scoreText;
    TextView answerText;
    TextView startButton;

    //Four possible answers set up
    TextView numberTL;
    TextView numberTR;
    TextView numberBL;
    TextView numberBR;

    //Random Number generator to create equation and answers
    Random rand = new Random();
    //CountDownTimer helps with our 30 second timer
    CountDownTimer countDown;

    //integers!
    int randomNumberOne;
    int randomNumberTwo;
    int addOrSubtractEquation;
    int randomNumberTotal;
    int randomTextAssign;
    int questionsRight;
    int questionsTotal;
    int clickedButtonTag;

    //Creates and returns a random int between 1 and 'x'
    public int randomNumberGenerator(int x) {
        int randomNumber = rand.nextInt(x-1)+1;
        return randomNumber;
    }
    //Determines whether our equation is going to be addition or subtraction by creating a random number
    public int randomNumberTotal(){
        int total;
        if(addOrSubtractEquation ==1){
            total = randomNumberOne+randomNumberTwo;
        }else{
            total = randomNumberOne-randomNumberTwo;
        }
        return total;
    }
    //Creates 3 answers close to the correct answer
    public int createCloseAnswers(){
        int answersInt;
        int randomAnswerNumber = randomNumberGenerator(15);
        addOrSubtractEquation = randomNumberGenerator(2);
        int addOrSubtractAnswers = randomNumberGenerator(2);

        if (addOrSubtractAnswers == 1){
            answersInt = randomAnswerNumber + randomNumberTotal;

        } else  {
            answersInt = randomAnswerNumber - randomNumberTotal;
        }

        return answersInt;
    }
    //Happens when we click start game
    public void startGame(){
        //Create two random numbers between 1 and 20
        randomNumberOne = randomNumberGenerator(20);
        randomNumberTwo = randomNumberGenerator(20);
        //random number, 1 or 2. 1 means addition, 2 means subtraction
        randomNumberTotal = randomNumberGenerator(2);
        //random number 1-4 to determine where our correct answer goes
        randomTextAssign = randomNumberGenerator(4);
        //sets the text of our equation
        if(addOrSubtractEquation ==1){
            equationText.setText(randomNumberOne +" + "+randomNumberTwo);
        }else{
            equationText.setText(randomNumberOne +" - "+randomNumberTwo);
        }
        //creates our 4 answers
        int answer1 = randomNumberTotal();
        int answer2 = createCloseAnswers();
        int answer3 = createCloseAnswers();
        int answer4 = createCloseAnswers();
        //cycles through our 4 answers and assigns them textViews.
        if (randomTextAssign == 1){
            numberTL.setText(Integer.toString(answer1));
            numberTR.setText(Integer.toString(answer2));
            numberBL.setText(Integer.toString(answer3));
            numberBR.setText(Integer.toString(answer4));
        } else if (randomTextAssign == 2){
            numberTL.setText(Integer.toString(answer4));
            numberTR.setText(Integer.toString(answer1));
            numberBL.setText(Integer.toString(answer2));
            numberBR.setText(Integer.toString(answer3));
        } else if (randomTextAssign == 3){
            numberTL.setText(Integer.toString(answer3));
            numberTR.setText(Integer.toString(answer4));
            numberBL.setText(Integer.toString(answer1));
            numberBR.setText(Integer.toString(answer2));
        } else if (randomTextAssign == 4){
            numberTL.setText(Integer.toString(answer2));
            numberTR.setText(Integer.toString(answer3));
            numberBL.setText(Integer.toString(answer4));
            numberBR.setText(Integer.toString(answer1));
        }


    }
    //runs when we select an answer
    public void answerClicked(View view){
        //uses the tag of the view to determine which view was clicked
        clickedButtonTag =Integer.parseInt(view.getTag().toString());
        Log.i("Tag", ""+view.getTag());

        if (randomTextAssign == clickedButtonTag){
            //happens if we clicked on the correct answer. Increase score, show correct
            questionsRight++;
            questionsTotal++;
            answerText.setText("Correct!");
        } else {
            //happens if we click on incorrect answer
            questionsTotal++;
            answerText.setText("Wrong!");
        }

        //either way, we update the score text to show the new score.
        scoreText.setText(questionsRight+"/"+questionsTotal);
        //sets up a new math question
        startGame();
    }

    //after time has run out and we click new game, this code resets the views and numbers so we can play again
    public void newGame(View view){
        startButton.setVisibility(View.GONE);
        answerText.setText("");
        questionsRight = 0;
        questionsTotal = 0;
        timer();
        startGame();
    }

    //code that is run after the timer runs out
    public void endGame(){
        startButton.setVisibility(View.VISIBLE);
        answerText.setText("Game Over");
    }

    //timer controls our 30 second countdown.
    public void timer(){

        final long timerLength = 30000; //30 seconds
        countDown = new CountDownTimer(timerLength, 1000){ //update every 1 second
            public void onTick(long millisecondsUntilDone){
                long secs = (millisecondsUntilDone / 1000);
                String secsString = "";
                if (secs < 10){
                    secsString = "0"+String.valueOf(secs); //adds zero to timer for time less than 10 seconds
                }else{
                    secsString = String.valueOf(secs);
                }

                timerText.setText(":"+secsString); //sets the text
            }

            public void onFinish(){ //when done sets time to :00 and runs the endGame() code
                timerText.setText(":00");
                endGame();
            }

        }.start();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setting up all of our text views and our button
        timerText = (TextView)findViewById(R.id.timerText);
        equationText = (TextView)findViewById(R.id.equationText);
        scoreText = (TextView)findViewById(R.id.scoreText);
        answerText = (TextView)findViewById(R.id.answerText);

        numberTL = (TextView)findViewById(R.id.numberTL);
        numberTR = (TextView)findViewById(R.id.numberTR);
        numberBL = (TextView)findViewById(R.id.numberBL);
        numberBR = (TextView)findViewById(R.id.numberBR);

        startButton = (TextView)findViewById(R.id.startButton);




        //ignore this code
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    //sets up our menu which we haven't used in this app.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    //code that runs when a menu item is selected.
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
}
