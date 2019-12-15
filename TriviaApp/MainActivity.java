package com.bawp.trivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;
import com.bawp.trivia.controller.AppController;
import com.bawp.trivia.data.AnswerListAsyncResponse;
import com.bawp.trivia.data.QuestionBank;
import com.bawp.trivia.model.Question;

import java.util.ArrayList;
import java.util.List;

import static android.media.CamcorderProfile.get;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView questionTextView;
    private TextView questionCounterTextView;
    private Button trueButton;
    private Button falseButton;
    private ImageButton nextButton;
    private ImageButton prevButton;
    private int currentQuestionIndex = 0;

    private List<Question> questionList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nextButton = findViewById(R.id.next_button);
        prevButton = findViewById(R.id.prev_button);
        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        questionCounterTextView = findViewById(R.id.counter_text);
        questionTextView = findViewById(R.id.question_textview);

        nextButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        trueButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);


        questionList = new QuestionBank().getQuestions(new AnswerListAsyncResponse() {
            @Override
            public void processFinished(ArrayList<Question> questionArrayList) {
                //intrebarea ce va fi afisata la prima rulare a programului:
                questionTextView.setText(questionArrayList.get(currentQuestionIndex).getAnswer());
//                questionCounterTextView.setText(currentQuestionIndex + " / " + questionArrayList.size());  // 0 out of 300
                Log.d("Inside", "processFinished: " + questionArrayList);
            }
        });

//        Log.d("Main ", "onCreate: " + questionList);



    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.prev_button:
                if (currentQuestionIndex > 0){
                    currentQuestionIndex = (currentQuestionIndex - 1) % questionList.size();
                    updateQuestion();
                    break;
                }
                Toast.makeText(this, "CANNOT GO BACKerr", Toast.LENGTH_SHORT).show();
                break;
            case R.id.next_button:
                currentQuestionIndex = (currentQuestionIndex + 1 ) % questionList.size();
                updateQuestion();
                break;
            case R.id.true_button:
                checkAnswer(true);
                updateQuestion();
                break;
            case R.id.false_button:
                checkAnswer(false);
                updateQuestion();
                break;
        }
    }

    private void checkAnswer(boolean userChooseCorrect) {
        //ne uitam in json, unde avem deja true/false pt fiecare intrebare (vezi mai jos)
        //daca ce alege user == ce e in json => answerIsTrue
        //get answer from our question:
        //toate raspunsurile au un array si true/false, isAnswerTrue pur si simplu returneaza ce e in arrat deja
        // asa arata jsonul:
//        [
//"Horoscopes accurately predict future events 85% of the time.",
//false
//],
    boolean answerIsTrue = questionList.get(currentQuestionIndex).isAnswerTrue();
    int toastMessageId = 0;
    if(userChooseCorrect == answerIsTrue) {
        fadeView();
    toastMessageId = R.string.correct_answer;
    } else {
        shakeAnimation();
        toastMessageId = R.string.wrong_answer;

    }
        Toast.makeText(MainActivity.this, toastMessageId, Toast.LENGTH_SHORT).show();
    }

    private void updateQuestion() {
        String question = questionList.get(currentQuestionIndex).getAnswer();
        questionTextView.setText(question);
        questionCounterTextView.setText(currentQuestionIndex + " / " + questionList.size());  // 0 out of 300

    }


    private void fadeView(){
        final CardView cardView = findViewById(R.id.cardView);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(250);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);

        cardView.setAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
cardView.setCardBackgroundColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private void shakeAnimation(){
        //load our xml animation:
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake_animation);

        //iti face intelliJ aici AUTOMAT FINAL
        //ALTFEL NU POTI ACCESA
        final CardView cardView = findViewById(R.id.cardView);
        cardView.setAnimation(shake);

        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setCardBackgroundColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


}
