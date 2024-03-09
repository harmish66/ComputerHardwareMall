package com.example.computerhardwaremall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.computerhardwaremall.Models.ComputerModel;
import com.example.computerhardwaremall.Models.LoginModel;
import com.example.computerhardwaremall.database.DatabaseHalper;
import com.example.computerhardwaremall.network.NetworkClient;
import com.example.computerhardwaremall.network.NetworkService;
import com.example.computerhardwaremall.utils.Constant;
import com.example.computerhardwaremall.utils.SesionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {
    RecyclerView rv;
    List<ComputerModel>list;
    TextView total,place;
    DatabaseHalper databaseHalper;
    LinearLayout nodata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        total = findViewById(R.id.total);
        list = new ArrayList<>();
        rv = findViewById(R.id.rv);
        place = findViewById(R.id.place);
        nodata = findViewById(R.id.nodata);
        rv.setLayoutManager(new LinearLayoutManager(CartActivity.this));
        rv.setHasFixedSize(true);

        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placeorder();
            }
        });

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
        @Override
        protected void onResume() {
            super.onResume();
            getData();

    }

    void getData() {

      list.clear();
        databaseHalper = new DatabaseHalper(CartActivity.this);
        Cursor cursor = databaseHalper.getData();
        if (cursor.moveToFirst()) {
            do {
                list.add(new ComputerModel(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4)));
            } while (cursor.moveToNext());

            cursor.close();
        }
        if (list != null && list.size() > 0) {
            rv.setAdapter(new Myadapter());
            total.setText(list.get(0).getPrice());
            getTotal(list);
            rv.setVisibility(View.VISIBLE);
            nodata.setVisibility(View.GONE);
            place.setEnabled(true);
            place.setAlpha(1f);
        }else{
            rv.setVisibility(View.GONE);
            nodata.setVisibility(View.VISIBLE);
            place.setEnabled(false);
            place.setAlpha(.5f);
        }

    }

        class Myadapter extends RecyclerView.Adapter<Myadapter.ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(CartActivity.this).inflate(R.layout.cart_container, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

            holder.textView.setText(list.get(position).getModelname());
            holder.brand.setText(list.get(position).getBrand());
            holder.price.setText(list.get(position).getPrice());
            Glide.with(CartActivity.this).load(list.get(position).getPhoto()).into(holder.imageView);

            holder.remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Dialog dialog = new Dialog(CartActivity.this);
                    dialog.setContentView(R.layout.delete_dialog);
                    dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    dialog.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            databaseHalper.deldteItem(list.get(position).getId());
                            getData();
                            notifyDataSetChanged();
                            getTotal(list);
                            dialog.dismiss();
                        }
                    });

                    dialog.show();

                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView textView, price, brand;
            CardView container;
            ImageView imageView, remove;


            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                textView = itemView.findViewById(R.id.modelname);
                container = itemView.findViewById(R.id.container_main);
                price = itemView.findViewById(R.id.price);
                imageView = itemView.findViewById(R.id.image);
                brand = itemView.findViewById(R.id.brand);
                remove = itemView.findViewById(R.id.remove);
            }
        }
    }

    void getTotal(List<ComputerModel> list) {
        long price = 0;
        for (int i = 0;i < list.size() ; i++)
        {
            if(list.get(i).getPrice() != null && !list.get(i).getPrice().equals(""))
            price += (long) Long.parseLong(list.get(i).getPrice());
        }
       total.setText("₹"+price+"");
    }

    void placeorder(){
        if (!SesionManager.getIsLoggedin(this).equalsIgnoreCase("")){
            Constant.list = list;
            startActivity(new Intent(CartActivity.this,PlaceOrderActivity.class).putExtra("price",total.getText().toString()));
        }else {
            startActivity(new Intent(CartActivity.this,LoginActivity.class));
        }

    }

}