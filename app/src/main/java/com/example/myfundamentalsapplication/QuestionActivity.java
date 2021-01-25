package com.example.myfundamentalsapplication;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Scanner;

public class QuestionActivity extends AppCompatActivity {
    public static final long TIME_LEFT_IN_MILLIS = 30000;

    private static final String KEY_SCORE = "keyScore";
    private static final String KEY_QUESTION_COUNT = "keyQuestionCount";
    private static final String KEY_MILLIS_LEFT = "keyMillisLeft";
    private static final String KEY_ANSWERED = "keyAnswered";
    private static final String KEY_QUESTION_LIST = "keyQuestionList";

    private TextView textViewQuestion;
    private TextView textViewScore;
    private TextView textViewCount;
    private TextView textViewCountDown;
    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private Button buttonConfirmNext;

    private ColorStateList textColorDefaultRb;
    private ColorStateList textColorDefaultCd;

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;

    private ArrayList<Question> questionList;
    private int questionCounter;
    private int questionCountTotal;
    private Question currentQuestion;

    private int score;
    private boolean answered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        textViewQuestion = findViewById(R.id.text_view_question);
        textViewCount = findViewById(R.id.text_view_question_count);
        textViewCountDown = findViewById(R.id.text_view_countdown);
        textViewScore = findViewById(R.id.text_view_score);
        rbGroup = findViewById(R.id.radio_group);
        rb1 = findViewById(R.id.radio_button1);
        rb2 = findViewById(R.id.radio_button2);
        rb3 = findViewById(R.id.radio_button3);
        rb4 = findViewById(R.id.radio_button4);
        buttonConfirmNext = findViewById(R.id.button_confirm_next);

        textColorDefaultRb = rb1.getTextColors();
        textColorDefaultCd = textViewCountDown.getTextColors();

        if (savedInstanceState == null) {
            String file = "questions.txt";
            questionList = readAllQuestions(file);
            questionCountTotal = questionList.size();
            Collections.shuffle(questionList);

            showNextQuestion();
        } else {
            questionList = savedInstanceState.getParcelableArrayList(KEY_QUESTION_LIST);
            questionCounter = savedInstanceState.getInt(KEY_QUESTION_COUNT);
            questionCountTotal = questionList.size();
            timeLeftInMillis = savedInstanceState.getLong(KEY_MILLIS_LEFT);
            answered = savedInstanceState.getBoolean(KEY_ANSWERED);
            score = savedInstanceState.getInt(KEY_SCORE);
            currentQuestion = questionList.get(questionCounter - 1);

            if(!answered) {
                startCountDown();
            } else {
                updateCountDownText();
                showSolution();
            }
        }

        buttonConfirmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!answered) {
                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()){
                        checkAnswer();
                    }
                    else {
                        Toast.makeText(QuestionActivity.this,R.string.alege_varianta,Toast.LENGTH_SHORT).show();
                    }
                } else {
                    showNextQuestion();
                }
            }
        });
    }

    private ArrayList<Question> readAllQuestions(String file) {
        ArrayList<Question> toateIntrebarile = new ArrayList<Question>();
        Locale locale = getResources().getConfiguration().locale;
        InputStream is;
        Scanner scanner;
        try {
            is = getAssets().open(file);
            scanner = new Scanner(is);

            //Log.i("MainActivity.java", Charset.defaultCharset().displayName());
            int nrOfQuestions = StringToInt(scanner.nextLine(), true);
            //Log.i("Intrebare.java", "!!!!!!!!!!!!!!" + nrOfQuestions);
            //Log.i("Intrebare.java", "\nnrOfQuestions : " + nrOfQuestions);
            for (int i = 0; i < nrOfQuestions; i++) {
                //Log.i("Intrebare.java", "i:" + i);
                Question question = new Question();
                String buf = scanner.nextLine();
                question.setQuestion(buf);
                buf = scanner.nextLine();
                question.setOption1(buf);
                buf = scanner.nextLine();
                question.setOption2(buf);
                buf = scanner.nextLine();
                question.setOption3(buf);
                buf = scanner.nextLine();
                question.setOption4(buf);
                int answerNr = StringToInt(scanner.nextLine(), false);
                //Log.i("Intrebare.java", "answerNr:" + answerNr);
                question.setAnswerNr(answerNr);
                //buf = scanner.nextLine();
                toateIntrebarile.add(question);
            }
            scanner.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return toateIntrebarile;
    }

    private void showNextQuestion() {
        rb1.setTextColor(textColorDefaultRb);
        rb2.setTextColor(textColorDefaultRb);
        rb3.setTextColor(textColorDefaultRb);
        rb4.setTextColor(textColorDefaultRb);
        rbGroup.clearCheck();

        if (questionCounter < questionCountTotal) {
            currentQuestion = questionList.get(questionCounter);

            textViewQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());
            rb4.setText(currentQuestion.getOption4());

            questionCounter++;
            textViewCount.setText("Question: " + questionCounter + "/" + questionCountTotal);
            answered = false;
            buttonConfirmNext.setText("Confirm");

            timeLeftInMillis = TIME_LEFT_IN_MILLIS;
            startCountDown();
        } else {
            finishQuiz();
        }
    }

    private void startCountDown(){
        countDownTimer = new CountDownTimer(timeLeftInMillis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateCountDownText();
                checkAnswer();
            }
        }.start();
    }

    private void updateCountDownText() {
        //int hours = (int)(timeLeftInMillis / 1000) / 60 / 60;
        int minutes = (int)(timeLeftInMillis / 1000) / 60 % 60;
        int seconds = (int)(timeLeftInMillis / 1000) % 60;

        String timeFormatted = String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        textViewCountDown.setText(timeFormatted);

        if (timeLeftInMillis < 10000) {
            textViewCountDown.setTextColor(Color.RED);
        } else {
            textViewCountDown.setTextColor(textColorDefaultCd);
        }
    }

    private void checkAnswer() {
        answered = true;

        countDownTimer.cancel();

        RadioButton rbSelected = findViewById(rbGroup.getCheckedRadioButtonId());
        int answerNr = rbGroup.indexOfChild(rbSelected) + 1;

        if (answerNr == currentQuestion.getAnswerNr()) {
            score++;
            textViewScore.setText("Score: " + score);
        }

        showSolution();
    }

    private void showSolution() {
        rb1.setTextColor(Color.RED);
        rb2.setTextColor(Color.RED);
        rb3.setTextColor(Color.RED);
        rb4.setTextColor(Color.RED);

        switch (currentQuestion.getAnswerNr()) {
            case 1:
                rb1.setTextColor(getResources().getColor(R.color.teal_700));
                break;
            case 2:
                rb2.setTextColor(getResources().getColor(R.color.teal_700));
                break;
            case 3:
                rb3.setTextColor(getResources().getColor(R.color.teal_700));
                break;
            case 4:
                rb4.setTextColor(getResources().getColor(R.color.teal_700));
                break;
        }

        if (questionCounter < questionCountTotal)
            buttonConfirmNext.setText(R.string.next_button);
        else
            buttonConfirmNext.setText(R.string.finish_button);
    }

    private void finishQuiz() {
        finish();
    }

    private static int StringToInt(String string, boolean isFirstLineOfFile) {
        int nr = 0, power = 1, inceput = -1;
        /**
         * Este foarte important sa tii cont ca un fisier codat in UTF-8 va avea la inceput un caracter
         * U+FEFF, sau 65279 in decimal care reprezinta inceput de fisier(BOM), acesta trebuie ignarat la trecerea string-ului
         * in int. Nu toate fisierele UTF-8 contin acest caracter,
         * https://en.wikipedia.org/wiki/Byte_order_mark
         */
        if (isFirstLineOfFile && string.charAt(0)==65279) inceput = 0;
        //System.out.println("String-ul primit in StringToInt este : \"" + string + "\"");
        //Log.i("Intrebare.java","String-ul are lungimea: "+string.length());
        //if (Character.getNumericValue(string.charAt(0))==65279)
        //Log.i("Intrebare.java","contine BOM");
        for (int i = string.length() - 1; i > inceput; i--) {
            if (Character.isDigit(string.charAt(i))) {
                nr += Character.getNumericValue(string.charAt(i)) * power;
                power *= 10;
                //Log.i("Intrebare.java", "Char at " + i + " is  \"" + string.charAt(i) + "\"" + "and numeric representation is" + Integer.toString((int) string.charAt(i)));
            }
        }
        return nr;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // if the countDown hasn't started is null
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SCORE,score);
        outState.putBoolean(KEY_ANSWERED,answered);
        outState.putLong(KEY_MILLIS_LEFT, timeLeftInMillis);
        outState.putInt(KEY_QUESTION_COUNT,questionCounter);
        outState.putParcelableArrayList(KEY_QUESTION_LIST,questionList);
    }

    @Override
    public void onBackPressed() {
        Intent it = new Intent(QuestionActivity.this, MainActivity.class);
        startActivity(it);
    }

}
