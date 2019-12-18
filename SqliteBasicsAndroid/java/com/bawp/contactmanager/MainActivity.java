package com.bawp.contactmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.bawp.contactmanager.data.DatabaseHandler;
import com.bawp.contactmanager.model.Contact;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHandler db = new DatabaseHandler(this);


        Contact jeremy = new Contact();
        jeremy.setName("Jeremy");
        jeremy.setPhoneNumber("72256487");

//adauga pe jeremy de fiecare data cand rulezi app:
        db.addContact(jeremy);


        Contact jason = new Contact();
        jason.setName("Jason");
        jason.setPhoneNumber("021654987");
        db.addContact(jason);


        //get 1 contact:
        Contact c = db.getContact(2);
        c.setName("NewJeremy");
        c.setPhoneNumber("8989989");
        Log.d("Main", "onCreate: " + c.getName() + " " + c.getPhoneNumber());

        //updateContact expects an int, that is the id
//        int updateRow = db.updateContact(c);
//        Log.d("rowId", "onCreate: " + updateRow);

        //delete a contact:
//        db.deleteContact(c);


        List<Contact> contactList = db.getAllContacts();


        //every time we get a contactList, it will be added to the contact obj:
        for(Contact contact: contactList){
            Log.d("MainActivity", "onCreate: " + contact.getName());
        }
    }
}
