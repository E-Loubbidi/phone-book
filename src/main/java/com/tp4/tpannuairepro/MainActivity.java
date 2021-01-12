package com.tp4.tpannuairepro;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                      //  .setAction("Action", null).show();
                add();
            }
        });

         final ArrayList<ItemModel> arrayList = getAll();
         final CustomAdapter adapter = new CustomAdapter(this, arrayList);
         final ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Toast.makeText(MainActivity.this, "Hello world",Toast.LENGTH_SHORT).show();
                //update(parent, view, position, id);
                Intent updateContactIntent = new Intent(getApplicationContext(), updateContact.class);
                Bundle b = new Bundle();
                ItemModel item = (ItemModel) parent.getItemAtPosition(position);
                b.putInt("id", item.getId());
                updateContactIntent.putExtras(b);
                startActivity(updateContactIntent);
                finish();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> av, View v, int pos, long id)
            {
                ItemModel item = (ItemModel) av.getItemAtPosition(pos);
                delete(item.getId(), adapter);
                return true;
            }
        });

         EditText searchEditText = findViewById(R.id.searchEditText);
        searchEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                    //EditText.setText("");
                   adapter.arrayList = searchContact(s.toString());
                adapter.notifyDataSetChanged();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        else if(id == R.id.action_search){

        }

        return super.onOptionsItemSelected(item);
    }

    private void add(){
        Intent addContactIntent = new Intent(getApplicationContext(), addContact.class);
        startActivity(addContactIntent);
        finish();
    }

    private ArrayList<ItemModel> getAll(){
        DbConnexion dbConnexion = new DbConnexion(this);
        ArrayList<ItemModel> list = dbConnexion.getAllRecords();
        return list;
    }

    private void deleteContact(Integer id, CustomAdapter adapter){
        DbConnexion dbConnexion = new DbConnexion(this);
        dbConnexion.deleteRecord(id);
        adapter.arrayList = getAll();
    }


    private void delete(final Integer ide, final CustomAdapter adapter){
        //dbConnexion.deleteRecord(Integer.parseInt(id.getText().toString()));
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Confirmation de Suppression");
        builder.setMessage("Voulez vous supprimer ce contact ?")
                .setCancelable(false)
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteContact(ide, adapter);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(),"Le contact est supprimé",
                                Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                Toast.makeText(getApplicationContext(), "Le contact n'a pas été supprimé",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private ArrayList<ItemModel> searchContact(String nom){
        DbConnexion dbConnexion = new DbConnexion(this);
        return dbConnexion.search(nom);
    }

    public class CustomAdapter extends BaseAdapter {

        Context context;
        ArrayList<ItemModel> arrayList;

        public CustomAdapter(Context context, ArrayList<ItemModel> arrayList) {
            this.context = context;
            this.arrayList = arrayList;
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return arrayList.get(position);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
            }
            TextView name, job, email, phone;
            name = (TextView) convertView.findViewById(R.id.txt_name);
            job = (TextView) convertView.findViewById(R.id.txt_job);
            email = (TextView) convertView.findViewById(R.id.txt_email);
            phone = (TextView) convertView.findViewById(R.id.txt_phone);
            Button callbtn = (Button) convertView.findViewById(R.id.btn_call);

            name.setText(arrayList.get(position).getName());
            job.setText(arrayList.get(position).getJob());
            email.setText(arrayList.get(position).getEmail());
            phone.setText(arrayList.get(position).getPhone());

            final ItemModel temp = arrayList.get(position);


            callbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri telephone = Uri.parse("tel:" + temp.getPhone());
                    Intent secondeActivite = new Intent(Intent.ACTION_DIAL, telephone);
                    startActivity(secondeActivite);

                }
            });

            return convertView;
        }
    }
}
