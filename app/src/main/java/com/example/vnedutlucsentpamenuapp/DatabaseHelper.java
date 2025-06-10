package com.example.vnedutlucsentpamenuapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Clinic.db";
    private static final int DATABASE_VERSION = 8;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Users (" +
                "ID INTEGER PRIMARY KEY," +
                "username TEXT," +
                "password TEXT," +
                "role TEXT)");

        db.execSQL("CREATE TABLE Menu (" +
                "ID INTEGER PRIMARY KEY," +
                "DishName TEXT," +
                "Cost INT," +
                "Description TEXT," +
                "image TEXT)");

        insertSampleData(db);
    }

    private void insertSampleData(SQLiteDatabase db) {
        db.execSQL("INSERT INTO Users VALUES (1, 'admin', '123456','Admin')");
        db.execSQL("INSERT INTO Users VALUES (2, 'nhanvien1', 'abc123','Nhân viên')");
        db.execSQL("INSERT INTO Users VALUES (3, 'nhanvien2', '987654','Nhân viên')");

        db.execSQL("INSERT INTO Menu VALUES (1, 'Phở bò', 50000, 'Phở bò tái chín thơm ngon ', 'pho_bo')");
        db.execSQL("INSERT INTO Menu VALUES (2, 'Cơm gà', 45000, 'Cơm gà giòn, nước sốt đặc biệt ', 'com_ga')");
        db.execSQL("INSERT INTO Menu VALUES (3, 'Bún chả Hà Nội', 40000, 'Bún chả thịt nướng, nước mắm chua ngọt  ','bun_cha')");
        db.execSQL("INSERT INTO Menu VALUES (4, 'Gỏi cuốn tôm thịt', 30000, 'Cuốn tôm thịt, nước chấm đậm đà ', 'goi_cuon')");
        db.execSQL("INSERT INTO Menu VALUES (5, 'Bánh mì pate', 25000, 'Bánh mì nóng giòn, pate béo ngậy ', 'banh_mi')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Users");
        db.execSQL("DROP TABLE IF EXISTS Menu");
        onCreate(db);
    }
}

