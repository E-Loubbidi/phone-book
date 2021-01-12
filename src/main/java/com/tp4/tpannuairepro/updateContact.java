package com.tp4.tpannuairepro;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class updateContact extends AppCompatActivity {

    private EditText firstName;
    private EditText lastName;
    private EditText job;
    private EditText phone;
    private EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contact);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                      //  .setAction("Action", null).show();
                goToHome();
            }
        });

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        job = findViewById(R.id.job);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);

        setRecord();
    }


    private void clearAll(){
        firstName.setText("");
        lastName.setText("");
        job.setText("");
        phone.setText("");
        email.setText("");
    }

    private void update(){
        DbConnexion dbConnexion = new DbConnexion(this);
        dbConnexion.updateRecord(firstName.getText().toString(), lastName.getText().toString(), job.getText().toString(), phone.getText().toString(), email.getText().toString(), getIntent().getExtras().getInt("id"));
        clearAll();
    }

    private void goToHome(){
        Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(homeIntent);
        finish();
    }

    private void setRecord(){
        DbConnexion dbConnexion = new DbConnexion(this);
        ItemModel record = dbConnexion.getOneRecord(getIntent().getExtras().getInt("id"));
        if(record!=null){
            String s[] = record.getName().split(" ");
            firstName.setText(s[0]);
            lastName.setText(s[1]);
            job.setText(record.getJob());
            phone.setText(record.getPhone());
            email.setText(record.getEmail());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_updatecontact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if(id == R.id.action_add){
            update();
        }

        return super.onOptionsItemSelected(item);
    }

}
