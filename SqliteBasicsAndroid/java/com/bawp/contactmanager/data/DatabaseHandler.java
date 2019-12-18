package com.bawp.contactmanager.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bawp.contactmanager.R;
import com.bawp.contactmanager.model.Contact;
import com.bawp.contactmanager.util.Util;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {


    public DatabaseHandler(Context context) {
        //cum de am acces la DATABASE_NAME din cealalta clasa aici?
        // este PUBLIC si STATIC
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }


    //we create our table, cu capete de tabel:
//    sTRING = TEXT in sql;
    @Override
    public void onCreate(SQLiteDatabase db) {
        //ce-i in dreapta egalului will be read as a sql command:
        String CREATE_CONTACT_TABLE = "CREATE TABLE " + Util.TABLE_NAME + "("
                + Util.KEY_ID + " INTEGER PRIMARY KEY,"
                + Util.KEY_NAME + " TEXT,"
                + Util.KEY_PHONE_NUMBER + " TEXT" + ")";

//executi codul SQL:
        db.execSQL(CREATE_CONTACT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //faci upgrade bazei de date, adica treci de la versiunea i la versiunea i1
        //stergi tabelul, apoi il recreezi:
        String DROP_TABLE = String.valueOf(R.string.db_drop);
        db.execSQL(DROP_TABLE, new String[]{Util.DATABASE_NAME});


        //create a table again:
        //ne folosim de acelasi cod din onCreate()
        onCreate(db);

    }

    //CRUD:
    public void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
//contentValues e o clasa recomandata pt a fi fol cu databases
        ContentValues values = new ContentValues();

        //notice we never pass/add the id, e adaugat de sql auto
        values.put(Util.KEY_NAME, contact.getName());
        values.put(Util.KEY_PHONE_NUMBER, contact.getPhoneNumber());

        //insert to row:
//(table_name, nullColumnHack, values)
        db.insert(Util.TABLE_NAME, null, values);
        Log.d("DBHandler", "addContact: " + "item added");
//we must close the db connection:
        db.close();
    }


    //get a contact:
//will return a contact, so we use the Contact type (clasa creata de mine ca model):
    public Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        //cursor = obj that allows us to itterate and point  to our db:
        //the cursor will get the whole ROW,

        Cursor cursor = db.query(Util.TABLE_NAME,
                new String[]{Util.KEY_ID, Util.KEY_NAME, Util.KEY_PHONE_NUMBER},
                Util.KEY_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null
        );

        //make sure the cursor is != null
        if (cursor != null) {
            cursor.moveToFirst();

            //dupa care setezi ce vrei de pe ROW respectiv(cel gasit de cursor mai sus), alegand intre id, nume, nrTel
            Contact contact = new Contact();
            contact.setId(Integer.parseInt(cursor.getString(0)));
            contact.setName(cursor.getString(1));
            contact.setPhoneNumber(cursor.getString(2));

            return contact;

        }
        else {
            return null;
        }
        }


        //get all contacts:
    //in a list of contacts:
    public List<Contact> getAllContacts(){
        List<Contact> contactList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        //select all contacts from db:
        String selectAll = "SELECT * FROM " + Util.TABLE_NAME;
        //select everything by passing null as selectionArg:
        Cursor cursor = db.rawQuery(selectAll, null );

        //loop trough data:
        if (cursor.moveToFirst()) {
            //do while will run once before doing the comparison:
            //the do part will run ONCE no matter what:
            do {
                Contact contact = new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));

                //add contact objects to our list:
                contactList.add(contact);
            }
            //while the cursor has something else to move on to..:
            while (cursor.moveToNext());
        }
return contactList;

    }


    //update contact:
     public int updateContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        // (unde_pui, de_unde_iei):
        values.put(Util.KEY_NAME, contact.getName());
        values.put(Util.KEY_PHONE_NUMBER, contact.getPhoneNumber());

        //update the row:
         return db.update(Util.TABLE_NAME, values, Util.KEY_ID + "=?",
                 new String[]{String.valueOf(contact.getId())}
                 );

     }


     //delete single contact:
    public void deleteContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();

//        "=?" inseamna ca id-ul il vom pasa ca arg urmator (treaba cu new String[]..)
        db.delete(Util.TABLE_NAME, Util.KEY_ID + "=?",
                new String[]{String.valueOf(contact.getId())}
                );
        db.close();
    }

    //get contacts count
    public int getCount(){
        String countQuery = "SELECT * FROM " + Util.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        return cursor.getCount();
    }

    }

