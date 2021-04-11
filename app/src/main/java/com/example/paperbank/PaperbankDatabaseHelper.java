package com.example.paperbank;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.security.PublicKey;

public class PaperbankDatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "user";
    static final String DB_NAME = "demo";
    static final int DB_VERSION = 1;


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE "+ TABLE_NAME + "(id INTEGER PRIMARY KEY AUTOINCREMENT, username VARCHAR, email VARCHAR, phone_no INT, gender VARCHAR, password VARCHAR)");
    }

    public PaperbankDatabaseHelper(Context context){

        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
    }

    public boolean addUser(int user_id, String username, String email, int phone_no, String gender, String password) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", user_id);
        contentValues.put("username", username);
        contentValues.put("email", email);
        contentValues.put("phone_no", phone_no);
        contentValues.put("gender", gender);
        contentValues.put("password", password);

        if(db.insert(TABLE_NAME, null, contentValues) == 1) {

            return false;
        }
        else {

            return true;
        }
    }

    public Cursor userRow() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_NAME + "", null);
        return cursor;
    }

    public boolean removeUser() {

        SQLiteDatabase db = this.getWritableDatabase();
        if( db.delete(TABLE_NAME, "", null) == 1 ) {

            return true;
        }
        else {

            return false;
        }

    }
}
