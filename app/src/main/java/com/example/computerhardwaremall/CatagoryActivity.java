package com.example.computerhardwaremall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.computerhardwaremall.Models.Categories;
import com.example.computerhardwaremall.Models.CategoryModel;
import com.example.computerhardwaremall.network.NetworkClient;
import com.example.computerhardwaremall.network.NetworkService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CatagoryActivity extends AppCompatActivity {
    
    List<Categories> list;
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catagory);

        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);
        findViewById(R.id.search_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CatagoryActivity.this,HomeActivity.class));
            }
        });



        findViewById(R.id.cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CatagoryActivity.this,CartActivity.class));
            }
        });


        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        list = new ArrayList<>();

        Dialog Dialog = new Dialog(CatagoryActivity.this);
        Dialog.setContentView(R.layout.loading_dialog);
        Dialog.setTitle("Loading..");
        Dialog.setCancelable(false);
        Dialog.show();

        NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
        Call<CategoryModel> categoryModelCall = networkService.getCategory();
        categoryModelCall.enqueue(new Callback<CategoryModel>() {
            @Override
            public void onResponse(Call<CategoryModel> call, Response<CategoryModel> response) {
                Dialog.dismiss();

                if(response != null && response.body() != null) {
                    list = response.body().getCategories();
                    rv.setAdapter(new Myadapter());
                }
                else{

                    Toast.makeText(CatagoryActivity.this, "date failed" , Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<CategoryModel> call, Throwable t) {

                Toast.makeText(CatagoryActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    class Myadapter extends RecyclerView.Adapter<Myadapter.ViewHolder>
    {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(CatagoryActivity.this).inflate(R.layout.category_container,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            holder.textView.setText(list.get(position).getName());

            holder.textView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CatagoryActivity.this, ComputerListActivity.class);
                    intent.putExtra("id",list.get(holder.getAdapterPosition()).getId());
                    intent.putExtra("modelname",list.get(position).getName());
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            TextView textView;
            CardView container;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                textView = itemView.findViewById(R.id.txt);
                container = itemView.findViewById(R.id.container);
            }
        }
    }
}