package com.example.mifoodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;


public class menu extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    int count = 0;

    Date date = java.util.Calendar.getInstance().getTime();
    SimpleDateFormat ft = new SimpleDateFormat ("Hmm");
    String dateformatted = ft.format(date);

    String type_of_food;

    String number;

    Vector<String> vector = new Vector();

    ArrayList<String> food_beverage_category = new ArrayList<>();

    DatabaseReference database;
    ArrayList<String> menu_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        final LinearLayout order_layout = findViewById(R.id.order_list);
        permission();

        database = FirebaseDatabase.getInstance().getReference("Stall 1");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    if(!(snapshot.getKey().contains("Specialfood")) && !(snapshot.getKey().contains("Number"))){

                        food_beverage_category.add(snapshot.getKey());
                    }


                }


            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Spinner spin =  findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, food_beverage_category);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(this);

        ImageButton delete = findViewById(R.id.delete_button);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                order_layout.removeAllViews();
                vector.removeAllElements();

            }
        });

        Button order_button = findViewById(R.id.button3);
        order_button.setOnClickListener(this);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        display_menu(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void display_menu(int position){
        final LinearLayout layout = findViewById(R.id.menu_list);
        layout.removeAllViews();

        switch (position) {

            case 0:
                type_of_food = food_beverage_category.get(0);
                Log.d("cock", type_of_food);
                menu_list.clear();
                break;
            case 1:
                type_of_food = food_beverage_category.get(1);
                Log.d("cock", type_of_food);
                menu_list.clear();
                break;
            case 2:
                type_of_food = food_beverage_category.get(2);
                Log.d("cock", type_of_food);
                menu_list.clear();
                break;


        }

        Log.d("cock", type_of_food);
        database = FirebaseDatabase.getInstance().getReference().child("Stall 1").child(type_of_food);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.d("cock", snapshot.getValue().toString());

                    menu_list.add(snapshot.getValue().toString());

                }


                String[] menu_list_string = new String[menu_list.size()];

                for (int i = 0; i < menu_list.size(); i++){

                    menu_list_string[i] = menu_list.get(i);


                }

                load_array(menu_list_string);


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    protected void permission() {

        final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {

            if (!(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS))) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);

            }

        }

    }


    public void sendit(){

        database = FirebaseDatabase.getInstance().getReference().child("Stall 1").child("Number");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                SmsManager smsManager = SmsManager.getDefault();

                number = dataSnapshot.getValue(String.class);

                smsManager.sendTextMessage(number, null, "Orders:" + " " + vector, null, null);
                Toast.makeText(getApplicationContext(), "Food Ordered", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





                }







    private void load_array(final String[] y) {


        final LinearLayout layout = findViewById(R.id.menu_list);
        final LinearLayout order_layout = findViewById(R.id.order_list);


        for(int i = 0; i < y.length; i++){

            final TextView textView = new TextView(this);
            final TextView textView2 = new TextView(this);

            Log.d("cock", y[i]);
            textView2.setPadding(0,20,0,20);
            order_layout.addView(textView2);

            textView.setText(y[i]);
            textView.setPadding(0, 20, 0, 20);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (vector.contains(textView.getText().toString())){

                        count = 1;
                        for(int i = 0; i < vector.size(); i++){

                            if(vector.elementAt(i).equals(textView.getText().toString())){

                                count++;
                            }


                        }

                        textView2.setText(textView.getText().toString() + " " +"x" + count);
                        Log.d("cock", String.valueOf(count));
                        count = 0;

                    }



                    else{
                        count = 1;
                        textView2.setText(textView.getText().toString() + " " + "x" + count);

                    }

                    vector.add(textView.getText().toString());

                }
            });

            layout.addView(textView);

        }

    }

    @Override
    public void onClick(View v) {
        sendit();
    }
}
