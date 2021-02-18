package com.example.crudsample;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.crudsample.Helper.DatabaseHelper;
import com.example.crudsample.data.AppData;
import com.example.crudsample.data.Person;

import java.net.URI;

public class PersonAddActivity extends AppCompatActivity {

    private ImageView personImageView;
    private EditText personNameText, personAgeText, personPhoneText;
    private Button personSaveButton;

    private Uri imageUri;
    private DatabaseHelper dbHelper;

    String name, age, phone, timeStamp;
    private Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_add);

        personImageView = findViewById(R.id.person_photo);
        personNameText = findViewById(R.id.person_name);
        personAgeText = findViewById(R.id.person_age);
        personPhoneText = findViewById(R.id.person_phone);

        personSaveButton = findViewById(R.id.person_save_button);
        dbHelper = new DatabaseHelper(this);

        if (getIntent().getExtras() !=  null)
        {
            Bundle bundle = getIntent().getExtras();
            Person person = (Person) bundle.getSerializable(Person.class.getSimpleName());
            personImageView.setImageURI(Uri.parse(person.getImage()));
            imageUri = Uri.parse(person.getImage());
            personNameText.setText(person.getName());
            personAgeText.setText(person.getAge());
            personPhoneText.setText(person.getPhone());
            id = Integer.parseInt(person.getId());
        }

        personImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePickDialog();
            }
        });

        personSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });

    }

    private void getData() {
        name = "" + personNameText.getText().toString().trim();
        age = "" + personAgeText.getText().toString().trim();
        phone = "" + personPhoneText.getText().toString().trim();
        timeStamp = "" + System.currentTimeMillis();

        if (getIntent().getExtras() !=  null)
        {
            dbHelper.updatePerson(
                    "" + id,
                    "" + name,
                    "" + age,
                    "" + phone,
                    "" + imageUri,
                    "" + timeStamp,
                    "" +  timeStamp
            );
        }
        else {
            long id = dbHelper.insertPerson(
                    "" + name,
                    "" + age,
                    "" + phone,
                    "" + imageUri,
                    "" + timeStamp,
                    "" + timeStamp
            );
        }
        startActivity(new Intent(PersonAddActivity.this, MainActivity.class));

    }

    private void imagePickDialog() {
        String[] options = {"Camera", "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Select for image");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    pickFromCamera();
                } else {
                    pickFromStorage();
                }
            }
        });

        builder.create().show();
    }

    private void pickFromStorage() {
        Intent galeryIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        galeryIntent.setType("image/*");
        startActivityForResult(galeryIntent, AppData.IMAGE_PICK_GALLERY_CODE);
    }

    private void pickFromCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Image title");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image description");

        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

        startActivityForResult(cameraIntent, AppData.IMAGE_PICK_CAMERA_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            switch (requestCode) {
                case AppData.CAMERA_REQUEST_CODE:
                    personImageView.setImageURI(imageUri);
                    break;
                case AppData.STORAGE_REQUEST_CODE:
                    imageUri = data.getData();
                    personImageView.setImageURI(imageUri);
                    break;

            }
        }

    }


}