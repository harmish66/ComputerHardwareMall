package com.example.computerhardwaremall.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.computerhardwaremall.Models.ComputerModel;

public class DatabaseHalper extends SQLiteOpenHelper {

    public static  String DATABASE_NAME = "Cart.db";
    public static  int DATABASE_VERSION = 1;

    public static  String TABLE_NAME = "carttable";
    public static  String KEY_ID = "id";
    public static  String MODEL_NAME = "model";
    public static  String BRAND = "brand";
    public static  String PRICE = "price";
    public static  String PHOTO = "photo";



    public DatabaseHalper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_NAME +" (" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MODEL_NAME + " TEXT , " +
                BRAND + " TEXT , " +
                PRICE + " TEXT , " +
                PHOTO + " TEXT ) " ;
        db.execSQL(query);

    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int  oldVersion, int newVersion ){
        db.execSQL("DROP TABLE "+ TABLE_NAME);
        onCreate(db);
    }
    public void insert(ComputerModel ComputerModel ){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MODEL_NAME , ComputerModel.getModelname());
        contentValues.put(BRAND , ComputerModel.getBrand());
        contentValues.put(PRICE , ComputerModel.getPrice());
        contentValues.put(PHOTO , ComputerModel.getPhoto());
        db.insert(TABLE_NAME, null,contentValues);
    }
    public Cursor getData() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM " + TABLE_NAME,null);
        return cursor;
    }

    public void deldteItem(String id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID+" = ?", new String[]{id});
    }
    public void deleteCart(String id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME,null, null);
    }
}
