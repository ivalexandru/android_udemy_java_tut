package com.bawp.spref;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity  {
    private EditText enterMessage;
    private Button saveButton;
    private TextView showMessageTextView;

    private static final String MESSAGE_ID = "messages_prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        saveButton = findViewById(R.id.button);
        enterMessage = findViewById(R.id.message_editText);
        showMessageTextView = findViewById(R.id.show_message_textview);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //trim gets rid of all the extra spaces;
                String message = enterMessage.getText().toString().trim();

                //create the SharedPrefference instantiations, so that we are able to save the data:
                //primul arg e key, apoi mode (we can limit the accesibility from other apps with mode):
//                MODE_PRIVATE = ONLY THIS APP IS ABLE TO ACCESS THIS...(xml file)
                SharedPreferences sharedPreferences = getSharedPreferences(MESSAGE_ID, MODE_PRIVATE );


                //invoke the SharedPreferences editor:
                //ony "editor" will be able to access..
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("key_message", message);

                editor.apply(); //this saves to disk;

            }
        });

        //retrieve the info we saved to disk:
        //use the same mode we passed when saving the data
        SharedPreferences getShareData = getSharedPreferences(MESSAGE_ID, MODE_PRIVATE);
        //default_value in caz ca nu tasteaza nimic userul:
        String value = getShareData.getString("key_message", "default_value");

        //afisez the retrieved info intr-un textView:
        showMessageTextView.setText(value);


        //textul nou nu va fi afisat imediat ce apesi pe buton;
        //apesi butonul, inchizi app iar cand o deschizi iar iti va aparea textul introdus.

    }


}
