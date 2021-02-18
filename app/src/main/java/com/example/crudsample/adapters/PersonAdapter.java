package com.example.crudsample.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.crudsample.R;
import com.example.crudsample.data.Person;

import java.net.URI;
import java.util.ArrayList;

public class PersonAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Person> personArrayList;

    public PersonAdapter(Context context, ArrayList<Person> personArrayList) {
        this.context = context;
        this.personArrayList = personArrayList;
    }

    @Override
    public int getCount() {

       if(personArrayList == null){
           return  0;
       }
       else{
           return personArrayList.size();
       }
    }

    @Override
    public Object getItem(int position) {
        return personArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Integer.parseInt(personArrayList.get(position).getId());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.person_item, parent, false);
        TextView personName, personAge, personPhone;
        ImageView personImage;

        personName = view.findViewById(R.id.name);
        personAge = view.findViewById(R.id.age);
        personPhone = view.findViewById(R.id.phone);
        personImage = view.findViewById(R.id.photo);

        Person currentPerson = personArrayList.get(position);
        personName.setText(currentPerson.getName());
        personAge.setText(currentPerson.getAge());
        personPhone.setText(currentPerson.getPhone());
       if(currentPerson.getImage() != null){
          personImage.setImageURI(Uri.parse(currentPerson.getImage()));
       }

        return view;
    }
}
