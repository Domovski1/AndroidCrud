package com.example.crudsample.data;

public class AppData {
    public static final String DB_NAME = "PERSON_DB";
    public static final int DB_VERSION = 1;

    public static final String TABLE_PERSON = "PERSON_TABLE";


    public static final String PERSON_ID = "ID";
    public static final String PERSON_NAME = "NAME";
    public static final String PERSON_AGE = "AGE";
    public static final String PERSON_PHONE = "PHONE";
    public static final String PERSON_IMAGE = "IMAGE";
    public static final String PERSON_ADD_TIMESTAMP = "ADD_TIMESTAMP";
    public static final String PERSON_UPDATE_TIMESTAMP = "UPDATE_TIMESTAMP";

    public static final String CREATE_TABLE_PERSON = "CREATE TABLE " + TABLE_PERSON + " ("
            + PERSON_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PERSON_NAME + " TEXT, "
            + PERSON_AGE + " TEXT, "
            + PERSON_PHONE + " TEXT, "
            + PERSON_IMAGE + " TEXT, "
            + PERSON_ADD_TIMESTAMP + " TEXT, "
            + PERSON_UPDATE_TIMESTAMP + " TEXT"
            + " );";


    public static  final int CAMERA_REQUEST_CODE = 100;
    public static  final int STORAGE_REQUEST_CODE = 101;
    public static  final int IMAGE_PICK_CAMERA_CODE = 100;
    public static  final int IMAGE_PICK_GALLERY_CODE = 101;
}
