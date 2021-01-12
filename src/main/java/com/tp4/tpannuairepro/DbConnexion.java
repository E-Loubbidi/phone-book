package com.tp4.tpannuairepro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DbConnexion extends SQLiteOpenHelper {

    public DbConnexion(Context context){
        super(context, "mescontacts.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists contact(id Integer Primary key, firstName Text not null, lastName Text not null, job Text not null, phone Text not null, email Text not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists contact");
        onCreate(db);
    }

    public void saveRecord(String firstName, String lastName, String job, String phone, String email){
        SQLiteDatabase wDb = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("firstName", firstName);
        values.put("lastName", lastName);
        values.put("job", job);
        values.put("phone", phone);
        values.put("email", email);
        wDb.insert("contact", null, values);
    }

    public ArrayList<ItemModel> getAllRecords(){
        SQLiteDatabase rDb = this.getReadableDatabase();
        Cursor res = rDb.rawQuery("select * from contact", null);
        ArrayList<ItemModel> list = new ArrayList<>();
        res.moveToFirst();
        while(res.isAfterLast() == false){
            list.add(new ItemModel(res.getInt(res.getColumnIndex("id")), res.getString(res.getColumnIndex("firstName")) + " " + res.getString(res.getColumnIndex("lastName")), res.getString(res.getColumnIndex("job")), res.getString(res.getColumnIndex("phone")), res.getString(res.getColumnIndex("email"))));
            res.moveToNext();
        }
        return list;
    }

    public void updateRecord(String firstName, String lastName, String job, String phone, String email, Integer id){
        SQLiteDatabase wDb = this.getWritableDatabase();
        wDb.execSQL("update contact set firstName='" + firstName + "',lastName='" + lastName + "',job='" + job + "',phone='" + phone + "',email='" + email  + "' where id = " + id);
    }

    public ItemModel getOneRecord(Integer id){
        SQLiteDatabase rDb = this.getReadableDatabase();
        Cursor res = rDb.rawQuery("select * from contact where id = " + id, null);
        res.moveToFirst();
        if(res.isFirst()==true){
            return new ItemModel(res.getInt(res.getColumnIndex("id")), res.getString(res.getColumnIndex("firstName")) + " " + res.getString(res.getColumnIndex("lastName")), res.getString(res.getColumnIndex("job")), res.getString(res.getColumnIndex("phone")), res.getString(res.getColumnIndex("email")));
        }
        return null;
    }

    public void deleteRecord(Integer id){
        SQLiteDatabase wDb = this.getWritableDatabase();
        //String[] s = new String[] {String.valueOf(id)};
        //wDb.delete("contact", "id=?", s);
        wDb.execSQL("delete from contact where id='" + id + "'");
    }

    public ArrayList<ItemModel> search(String nom){
        SQLiteDatabase rDb = this.getReadableDatabase();
        Cursor res = rDb.rawQuery("select * from contact where lastName like '%" + nom + "%'" , null);
        ArrayList<ItemModel> list = new ArrayList<>();
        res.moveToFirst();
        while(res.isAfterLast() == false){
            list.add(new ItemModel(res.getInt(res.getColumnIndex("id")), res.getString(res.getColumnIndex("firstName")) + " " + res.getString(res.getColumnIndex("lastName")), res.getString(res.getColumnIndex("job")), res.getString(res.getColumnIndex("phone")), res.getString(res.getColumnIndex("email"))));
            res.moveToNext();
        }
        return list;
    }
}
