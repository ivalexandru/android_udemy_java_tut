package com.bawp.trivia.data;

import com.bawp.trivia.model.Question;

import java.util.ArrayList;

public interface AnswerListAsyncResponse {
    //this method will be overridden at the class lvl:
    void processFinished(ArrayList<Question> questionArrayList);
}
