package com.example.vnedutlucsentpamenuapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MenuManageActivity extends AppCompatActivity {
    TextView tvWelcome;
    ListView lvMenu;
    List<FoodItem> foodList;
    FoodAdapter adapter;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_menu_manage);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String role = intent.getStringExtra("role");

        tvWelcome = findViewById(R.id.tvWelcome);
        tvWelcome.setText("Xin chào, " + username + " ("+role+")");

        Button btnLogout = findViewById(R.id.btn_logout);

        btnLogout.setOnClickListener(v -> {
            Toast.makeText(this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();

            Intent intent0 = new Intent(MenuManageActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent0);
            finish();
        });

        lvMenu = findViewById(R.id.lvMenu);
        dbHelper = new DatabaseHelper(this);
        foodList = new ArrayList<>();
        loadMenuData();

        Button btnAddFood = findViewById(R.id.btnAddFood);
        btnAddFood.setOnClickListener(v -> showAddFoodDialog());

        lvMenu.setOnItemClickListener((parent, view, position, id) -> {
            FoodItem item = foodList.get(position);
            showEditDeleteDialog(item);
        });

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

    private void showAddFoodDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thêm món ăn mới");

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_food, null);
        EditText edtDishName = dialogView.findViewById(R.id.edtDishName);
        EditText edtCost = dialogView.findViewById(R.id.edtCost);
        EditText edtDescription = dialogView.findViewById(R.id.edtDescription);

        builder.setView(dialogView);

        builder.setPositiveButton("Thêm", (dialog, which) -> {
            String name = edtDishName.getText().toString().trim();
            String costStr = edtCost.getText().toString().trim();
            String desc = edtDescription.getText().toString().trim();

            if (name.isEmpty() || costStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ tên và giá", Toast.LENGTH_SHORT).show();
                return;
            }

            int cost = Integer.parseInt(costStr);

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("DishName", name);
            values.put("Cost", cost);
            values.put("Description", desc);
            values.put("image", "");

            long newRowId = db.insert("Menu", null, values);
            db.close();

            if (newRowId != -1) {
                Toast.makeText(this, "Đã thêm món mới", Toast.LENGTH_SHORT).show();
                foodList.clear();
                loadMenuData();
            } else {
                Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    private void showEditDeleteDialog(FoodItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chỉnh sửa / Xóa món");

        View view = getLayoutInflater().inflate(R.layout.dialog_edit_food, null);
        EditText edtName = view.findViewById(R.id.edtEditDishName);
        EditText edtCost = view.findViewById(R.id.edtEditCost);
        EditText edtDesc = view.findViewById(R.id.edtEditDescription);

        edtName.setText(item.getDishName());
        edtCost.setText(String.valueOf(item.getCost()));
        edtDesc.setText(item.getDescription());

        builder.setView(view);

        builder.setPositiveButton("Lưu", (dialog, which) -> {
            String newName = edtName.getText().toString().trim();
            String costStr = edtCost.getText().toString().trim();
            String newDesc = edtDesc.getText().toString().trim();

            if (newName.isEmpty() || costStr.isEmpty()) {
                Toast.makeText(this, "Không được để trống tên và giá", Toast.LENGTH_SHORT).show();
                return;
            }

            int newCost = Integer.parseInt(costStr);

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("DishName", newName);
            values.put("Cost", newCost);
            values.put("Description", newDesc);

            int rows = db.update("Menu", values, "ID = ?", new String[]{String.valueOf(item.getId())});
            db.close();

            if (rows > 0) {
                Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                foodList.clear();
                loadMenuData();
            } else {
                Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Xóa", (dialog, which) -> {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            int rows = db.delete("Menu", "ID = ?", new String[]{String.valueOf(item.getId())});
            db.close();

            if (rows > 0) {
                Toast.makeText(this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                foodList.clear();
                loadMenuData();
            } else {
                Toast.makeText(this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNeutralButton("Hủy", null);
        builder.show();
    }
}
