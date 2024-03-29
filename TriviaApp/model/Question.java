package com.bawp.trivia.model;

import androidx.annotation.NonNull;

public class Question {

    private String answer;
    private boolean answerTrue;

    //empty constructor
    public Question() {

    }

    //constructor
    public Question(String answer, boolean answerTrue) {
        this.answer = answer;
        this.answerTrue = answerTrue;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isAnswerTrue() {
        return answerTrue;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setAnswerTrue(boolean answerTrue) {
        this.answerTrue = answerTrue;
    }

    @NonNull
    @Override
    public String toString() {
        return "Question{" +
                "answer=" + '\'' +
                ", answerTrue=" + answerTrue +
                "}";

    }
}
