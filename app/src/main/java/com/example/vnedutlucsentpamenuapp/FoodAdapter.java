package com.example.vnedutlucsentpamenuapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

public class FoodAdapter extends ArrayAdapter<FoodItem> {
    private Context context;
    private List<FoodItem> foodList;

    public FoodAdapter(Context context, List<FoodItem> foodList) {
        super(context, 0, foodList);
        this.context = context;
        this.foodList = foodList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.food_item_row, parent, false);

        FoodItem food = foodList.get(position);

        TextView tvName = convertView.findViewById(R.id.tvDishName);
        TextView tvDesc = convertView.findViewById(R.id.tvDescription);
        TextView tvPrice = convertView.findViewById(R.id.tvPrice);
        ImageView imgFood = convertView.findViewById(R.id.imgFood);

        tvName.setText(food.getDishName());
        tvDesc.setText(food.getDescription());
        tvPrice.setText(food.getCost() + " VND");

        int resId = context.getResources().getIdentifier(food.getImage(), "drawable", context.getPackageName());
        if (resId != 0) {
            imgFood.setImageResource(resId);
        }

        return convertView;
    }
}

