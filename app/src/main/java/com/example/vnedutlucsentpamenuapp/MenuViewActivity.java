package com.example.vnedutlucsentpamenuapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MenuViewActivity extends AppCompatActivity {

    Button btnLogin;
    ListView lvMenu;
    List<FoodItem> foodList;
    FoodAdapter adapter;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_view);

        btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuViewActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        lvMenu = findViewById(R.id.lvMenu);
        dbHelper = new DatabaseHelper(this);
        foodList = new ArrayList<>();

        loadMenuData();
    }

    private void loadMenuData() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Menu", null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("ID"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("DishName"));
            int cost = cursor.getInt(cursor.getColumnIndexOrThrow("Cost"));
            String desc = cursor.getString(cursor.getColumnIndexOrThrow("Description"));
            String image = cursor.getString(cursor.getColumnIndexOrThrow("image"));

            foodList.add(new FoodItem(id, name, cost, desc, image));
        }

        cursor.close();
        db.close();

        adapter = new FoodAdapter(this, foodList);
        lvMenu.setAdapter(adapter);
    }
}
