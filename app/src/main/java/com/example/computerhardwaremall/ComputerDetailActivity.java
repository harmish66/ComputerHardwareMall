package com.example.computerhardwaremall;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.computerhardwaremall.Models.ComputerModel;
import com.example.computerhardwaremall.database.DatabaseHalper;

public class ComputerDetailActivity extends AppCompatActivity {

    ImageView imageView;
    TextView modelname, price, color, manufacturer , warranty,  brand , des, hadder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computer_detail);


        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        hadder = findViewById(R.id.hadder);
        if (getIntent() != null)
            hadder .setText(getIntent().getStringExtra("brand"));
        findViewById(R.id.cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHalper databaseHalper  = new DatabaseHalper(getApplicationContext());
                ComputerModel ComputerModel = new ComputerModel();
                ComputerModel.setModelname(getIntent().getStringExtra("modelname"));
                ComputerModel.setBrand(getIntent().getStringExtra("brand"));
                ComputerModel.setPrice(getIntent().getStringExtra("price"));
                ComputerModel.setColor(getIntent().getStringExtra("color"));
                ComputerModel.setPhoto(getIntent().getStringExtra("photo"));
                databaseHalper.insert(ComputerModel);
                Toast.makeText(getApplicationContext(),"Item Added to Cart..",Toast.LENGTH_SHORT).show();
            }
        });
        imageView = findViewById(R.id.image);
        modelname = findViewById(R.id.modelname);
        price = findViewById(R.id.price);
        color = findViewById(R.id.color);
        manufacturer = findViewById(R.id.manufacturer);
        warranty = findViewById(R.id.warranty);
        brand = findViewById(R.id.brand);
        des = findViewById(R.id.description);

        Glide.with(ComputerDetailActivity.this).load(getIntent().getStringExtra("photo")).into(imageView);
        modelname.setText(getIntent().getStringExtra("modelname"));
        price.setText("₹"+getIntent().getStringExtra("price"));
        color.setText(getIntent().getStringExtra("color"));
        manufacturer.setText(getIntent().getStringExtra("manufacturer"));
        warranty.setText(getIntent().getStringExtra("warranty"));
        brand.setText(getIntent().getStringExtra("brand"));
        des.setText(getIntent().getStringExtra("des"));


    }
}