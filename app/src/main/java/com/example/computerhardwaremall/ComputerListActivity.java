package com.example.computerhardwaremall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.computerhardwaremall.Models.Categories;
import com.example.computerhardwaremall.Models.CategoryModel;
import com.example.computerhardwaremall.Models.ComputerModel;
import com.example.computerhardwaremall.Models.ComputerResponseModel;
import com.example.computerhardwaremall.network.NetworkClient;
import com.example.computerhardwaremall.network.NetworkService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComputerListActivity extends AppCompatActivity {

    RecyclerView rv;
    List<ComputerModel> list;
    TextView hadder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computer_list);

        hadder = findViewById(R.id.hadder);
        if (getIntent() != null)
        hadder .setText(getIntent().getStringExtra("modelname"));

        findViewById(R.id.cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ComputerListActivity.this,CartActivity.class));
            }
        });

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(ComputerListActivity.this));
        rv.setHasFixedSize(true);
        list = new ArrayList<>();
        Dialog Dialog = new Dialog(ComputerListActivity.this);
        Dialog.setContentView(R.layout.loading_dialog);
        Dialog.setTitle("Loading..");
        Dialog.setCancelable(false);
        Dialog.show();
        NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
        Call<ComputerResponseModel> call = networkService.getComputer(getIntent().getStringExtra("id"));
        call.enqueue(new Callback<ComputerResponseModel>() {
            @Override
            public void onResponse(Call<ComputerResponseModel> call, Response<ComputerResponseModel> response) {
                Dialog.dismiss();
                if(response != null && response.body()!= null)
                {
                    list = response.body().getComputer();
                    rv.setAdapter(new ComputerAdpter());

                }
                else
                {
                    Toast.makeText(ComputerListActivity.this, "data failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ComputerResponseModel> call, Throwable t) {
                Toast.makeText(ComputerListActivity.this, "data failed", Toast.LENGTH_SHORT).show();
                Dialog.dismiss();

            }
        });

    }

    class ComputerAdpter extends RecyclerView.Adapter<ComputerAdpter.ViewHolder>{


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(ComputerListActivity.this).inflate(R.layout.computercontainer, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

            Glide.with(ComputerListActivity.this).load(list.get(position).getPhoto()).into(holder.imageView);
            holder.modelname.setText(list.get(position).getModelname());
            holder.price.setText(list.get(position).getPrice());
            holder.brand.setText(list.get(position).getBrand());

            holder.main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ComputerListActivity.this,ComputerDetailActivity.class);
                    intent.putExtra("modelname",list.get(position).getModelname());
                    intent.putExtra("color",list.get(position).getColor());
                    intent.putExtra("manufacturer",list.get(position).getManufacturer());
                    intent.putExtra("price",list.get(position).getPrice());
                    intent.putExtra("brand",list.get(position).getBrand());
                    intent.putExtra("des",list.get(position).getDes());
                    intent.putExtra("warranty",list.get(position).getwarranty());
                    intent.putExtra("photo",list.get(position).getPhoto());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            CardView main;
            ImageView imageView;
            TextView modelname, price, brand ;

            public ViewHolder(@NonNull View view) {
                super(view);

                imageView = view.findViewById(R.id.image);
                main = view.findViewById(R.id.container_main);
                modelname = view.findViewById(R.id.modelname);
                price = view.findViewById(R.id.price);
               // color = view.findViewById(R.id.color);
               // warranty = view.findViewById(R.id.warranty);
               // manufacturer = view.findViewById(R.id.manufacturer);
               // des = view.findViewById(R.id.description);
                brand=view.findViewById(R.id.brand);
            }
        }
    }
}
