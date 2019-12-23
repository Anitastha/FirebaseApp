package com.anita.reminderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.Snapshot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
EditText names, pass, lon;
Button btnSave;
DatabaseReference databaseReference;
User user;
long maxid=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        names = findViewById(R.id.name);
        pass = findViewById(R.id.pass);
        lon = findViewById(R.id.lon);
        btnSave = findViewById(R.id.btnSave);
        user = new User();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("User");

        //for auto increment
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if(dataSnapshot.exists())
                maxid=(dataSnapshot.getChildrenCount());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

         btnSave.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 int lo = Integer.parseInt(lon.getText().toString().trim());
                 user.setName(names.getText().toString().trim());
                 user.setPassword(pass.getText().toString().trim());
                 user.setLon(lo);

//                 databaseReference.push().setValue(user);
                 //for child class
//                 databaseReference.child("user1").setValue(user);

                 //for auto-increment
                 databaseReference.child(String.valueOf(maxid+1)).setValue(user);
                 Toast.makeText(MainActivity.this, "Data saved in firebase", Toast.LENGTH_SHORT).show();

                 startActivity(new Intent(MainActivity.this, Activity_retrieve.class));
             }
         });

    }
}
