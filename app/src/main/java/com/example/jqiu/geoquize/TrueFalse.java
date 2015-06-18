package com.example.jqiu.geoquize;

import android.app.Application;
import android.test.ApplicationTestCase;

/**
 * Created by jqiu on 6/17/15.
 */
public class TrueFalse {

    private int mQuestion;
    private boolean mAnswer;

    public int getQuestion() {
        return mQuestion;
    }

    public void setQuestion(int question) {
        mQuestion = question;
    }

    public boolean getAnswer() {
        return mAnswer;
    }

    public void setAnswer(boolean answer) {
        mAnswer = answer;
    }

    TrueFalse(int question, boolean answer) {
        mQuestion = question;
        mAnswer = answer;
    }
}