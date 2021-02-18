package com.example.crudsample.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.crudsample.data.AppData;
import com.example.crudsample.data.Person;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context) {
        super(context, AppData.DB_NAME, null, AppData.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(AppData.CREATE_TABLE_PERSON);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + AppData.TABLE_PERSON);
        onCreate(db);
    }

    // метод, для удаления данных по ID
    public void deletePerson(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(AppData.TABLE_PERSON, AppData.PERSON_ID + " = ?", new String[]{id});
        db.close();
    }


    //Добавление данных
    public long insertPerson(String name, String age, String phone, String image, String addTimeStamp, String updateTimeStamp) {
        // получение доступа к БД
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AppData.PERSON_NAME, name);
        values.put(AppData.PERSON_AGE, age);
        values.put(AppData.PERSON_PHONE, phone);
        values.put(AppData.PERSON_IMAGE, image);
        values.put(AppData.PERSON_ADD_TIMESTAMP, addTimeStamp);
        values.put(AppData.PERSON_UPDATE_TIMESTAMP, updateTimeStamp);
        long id = db.insert(AppData.TABLE_PERSON, null, values);
        db.close();
        return id;
    }

    public void updatePerson (String id, String name, String age, String phone, String image, String addTimeStamp, String updateTimeStamp) {
        SQLiteDatabase db = this.getWritableDatabase();

        //Создаём хранилище данных
        ContentValues values = new ContentValues();
        values.put(AppData.PERSON_NAME, name);
        values.put(AppData.PERSON_AGE, age);
        values.put(AppData.PERSON_PHONE, phone);
        values.put(AppData.PERSON_IMAGE, image);
        values.put(AppData.PERSON_ADD_TIMESTAMP, addTimeStamp);
        values.put(AppData.PERSON_UPDATE_TIMESTAMP, updateTimeStamp);

        db.update(AppData.TABLE_PERSON, values, AppData.PERSON_ID + " = ?", new String[]{id} );
        db.close();

    }


    public ArrayList<Person> getAllData(String orderBy) {
        ArrayList<Person> personArrayList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + AppData.TABLE_PERSON + " ORDER BY " + orderBy;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToNext()) {
            do {
                 Person person = new Person(
                         "" + cursor.getInt(cursor.getColumnIndex(AppData.PERSON_ID)),
                         "" + cursor.getString(cursor.getColumnIndex(AppData.PERSON_NAME)),
                         "" + cursor.getString(cursor.getColumnIndex(AppData.PERSON_AGE)),
                         "" + cursor.getString(cursor.getColumnIndex(AppData.PERSON_PHONE)),
                         "" + cursor.getString(cursor.getColumnIndex(AppData.PERSON_IMAGE)),
                         "" + cursor.getString(cursor.getColumnIndex(AppData.PERSON_ADD_TIMESTAMP)),
                         "" + cursor.getString(cursor.getColumnIndex(AppData.PERSON_UPDATE_TIMESTAMP))
                 );
                 personArrayList.add(person);
            } while (cursor.moveToNext());
        }

        db.close();
        return personArrayList;
    }
}