package com.example.jqiu.geoquize;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import java.util.ArrayList;


public class MainActivity extends Activity {
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPrevButton;
    private Button mCheatButton;
    private TextView mQuizeQuestion;

    private final String KEY_INDEX = "question_current_index";
    private final String KEY_IS_CHEATER_ARRAY = "is_cheater_array";

    private int mQuestionIndex = 0;
    private final TrueFalse[] mQuestionBank = new TrueFalse[]
            {
                    new TrueFalse(R.string.question_oceans, true),
                    new TrueFalse(R.string.question_mideast, false),
                    new TrueFalse(R.string.question_africa, false),
                    new TrueFalse(R.string.question_americas, true),
                    new TrueFalse(R.string.question_asia, true),
            };
    private boolean[] mIsCheaterArray = new boolean[mQuestionBank.length];

    private void checkAnswer( boolean userAnswer )
    {
        boolean trueAnswer = mQuestionBank[mQuestionIndex].getAnswer();
        int messageResId = 0;
        if (mIsCheaterArray[mQuestionIndex]) {
            messageResId = R.string.judgment_toast;
        } else {
            if (userAnswer == trueAnswer) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
                .show();
    }

    private void updateQuestion()
    {
        int questionId = mQuestionBank[mQuestionIndex].getQuestion();
        mQuizeQuestion.setText(questionId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mQuizeQuestion = (TextView)findViewById(R.id.quize_question);
        if (savedInstanceState != null) {
            mQuestionIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mIsCheaterArray = savedInstanceState.getBooleanArray(KEY_IS_CHEATER_ARRAY);
        }
        updateQuestion();

        mTrueButton = (Button)findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton = (Button)findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuestionIndex = (mQuestionIndex+1) % mQuestionBank.length;
                updateQuestion();

            }
        });

        mPrevButton = (Button)findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mQuestionIndex = (mQuestionBank.length+mQuestionIndex-1)%mQuestionBank.length;
                updateQuestion();
            }
        });

        mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View v)
            {
                Intent i = new Intent(MainActivity.this,CheatActivity.class);
                boolean answerIsTrue = mQuestionBank[mQuestionIndex].getAnswer();
                i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE,answerIsTrue);// send extra value to child activity
                startActivityForResult(i, 0);// expect get result from child activity
            }
        });
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_INDEX, mQuestionIndex);
        savedInstanceState.putBooleanArray(KEY_IS_CHEATER_ARRAY, mIsCheaterArray);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        mIsCheaterArray[mQuestionIndex] = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
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
}
