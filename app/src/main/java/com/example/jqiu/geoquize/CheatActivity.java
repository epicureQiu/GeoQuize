package com.example.jqiu.geoquize;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.content.Intent;


public class CheatActivity extends Activity {

    public static final String EXTRA_ANSWER_IS_TRUE = "answer_is_true";
    public static final String EXTRA_ANSWER_SHOWN = "answer_shown";
    public static final String KEY_ANSWER_TEXT = "answer_text";

    private boolean mAnswerIsTrue;
    private Button mShowAnswer;
    private TextView mAnswerTextView;
    private String mAnswerText=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, true);//expect get value from parent activity
                                                                                // second parameter is a default value

        mAnswerTextView = (TextView)findViewById(R.id.answerTextView);

        // Answer will not be shown until the user
        // presses the button
        setAnswerShownResult(false);

        if (savedInstanceState != null) {
            mAnswerText = savedInstanceState.getString(KEY_ANSWER_TEXT, null);
            if(mAnswerText != null )
            {
                mAnswerTextView.setText(mAnswerText);
                setAnswerShownResult(true);
            }
        }

        mShowAnswer = (Button)findViewById(R.id.showAnswerButton);
        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnswerText = mAnswerIsTrue == true ? "True" : "False";
                mAnswerTextView.setText(mAnswerText);
                setAnswerShownResult(true);
            }
        });


    }

    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(KEY_ANSWER_TEXT, mAnswerText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cheat, menu);
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
