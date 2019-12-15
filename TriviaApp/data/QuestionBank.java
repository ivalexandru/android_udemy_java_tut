package com.bawp.trivia.data;


import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.bawp.trivia.controller.AppController;
import com.bawp.trivia.model.Question;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

//creat in Listener cu alt+enter pe TAG:
import static com.bawp.trivia.controller.AppController.TAG;

//this will have a method that will be able to get all the questions:
public class QuestionBank {
    ArrayList<Question> questionArrayList = new ArrayList<>();
    private String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";

    //will return an array containing ALL of the questions
//    super(Method.GET, url, null, listener, errorListener);
    public List<Question> getQuestions(final AnswerListAsyncResponse callBack){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                (JSONArray) null,


                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0; i < response.length(); i++){
                            try {
                                Question question = new Question();
                                question.setAnswer(response.getJSONArray(i).get(0).toString());
                                question.setAnswerTrue(response.getJSONArray(i).getBoolean(1));

                                //add question objects to list;
                                questionArrayList.add(question);
                                Log.d("hello", "onResponse: " + question.getAnswer());


                                //get(0) pt ca in array-ul mare sunt arrayuri mai mici
                                //asa ca fiecare loop de sus face loop pt arrayurile mai mici
                                //dar in fiecare array mai mic, exista 2 iteme
//                                Log.d("JSON", "onResponse: " + response.getJSONArray(i).get(1));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            if(callBack != null) {
                                callBack.processFinished(questionArrayList);
                            }

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }

        );
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

        return questionArrayList;
    }
}
