package com.example.computerhardwaremall;

import static com.example.computerhardwaremall.utils.Constant.list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.computerhardwaremall.Models.LoginModel;
import com.example.computerhardwaremall.database.DatabaseHalper;
import com.example.computerhardwaremall.network.NetworkClient;
import com.example.computerhardwaremall.network.NetworkService;
import com.example.computerhardwaremall.utils.Constant;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceOrderActivity extends AppCompatActivity {

    TextView name, email, mobile;
    EditText address;

    Myadapter myadapter = new Myadapter();

    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        mobile = findViewById(R.id.mobile);
        address = findViewById(R.id.add);
        rv = findViewById(R.id.rv);


        name.setText(getSharedPreferences("User", MODE_PRIVATE).getString("name", "N/A"));
        email.setText(getSharedPreferences("User", MODE_PRIVATE).getString("email", "N/A"));
        mobile.setText(getSharedPreferences("User", MODE_PRIVATE).getString("mobile", "N/A"));

        findViewById(R.id.place).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PlaceOrderActivity.this,MyOrderActivity.class));
                if (address != null && address.getText() != null && !address.getText().toString().equals("")) {
                    String datetime = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()) + " " + new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                    placeOrder(datetime, getIntent().getStringExtra("price").toString(), address.getText().toString(), getSharedPreferences("User", MODE_PRIVATE).getString("mobile", "N/A"), getSharedPreferences("User", MODE_PRIVATE).getString("email", "N/A"));
                }
            }
        });


        if (Constant.list != null && Constant.list.size() > 0) {
            rv.setAdapter(myadapter);
        }


    }

    void placeOrder(String dateTime, String price, String add, String mobile, String email) {
        HashMap<String, String> map = new HashMap<>();
        map.put("datetime", dateTime);
        map.put("price", price);
        map.put("add", add);
        map.put("mobile", mobile);
        map.put("email", email);
        map.put("products", new Gson().toJson(Constant.list));

        NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
        Call< LoginModel > call = networkService.placeOrder(map);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                new DatabaseHalper(PlaceOrderActivity.this).deleteCart(null);
                Constant.list.clear();
                myadapter.notifyDataSetChanged();
                address.setText("");
                finish();
                Toast.makeText(getApplicationContext(), "" + response.body().getMassage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }


    class Myadapter extends RecyclerView.Adapter<Myadapter.ViewHolder> {
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new Myadapter.ViewHolder(LayoutInflater.from(PlaceOrderActivity.this).inflate(R.layout.cart_container, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull Myadapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

            holder.textView.setText(Constant.list.get(position).getModelname());
            holder.company.setText(Constant.list.get(position).getBrand());
            holder.price.setText(Constant.list.get(position).getPrice());
    //        holder.remove.setVisibility(View.GONE);
           Glide.with(PlaceOrderActivity.this).load(Constant.list.get(position).getPhoto()).into(holder.imageView);

        }

        @Override
        public int getItemCount() {
            return Constant.list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView textView, price, company;
            CardView container;
            ImageView imageView, remove;


            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                textView = itemView.findViewById(R.id.modelname);
                container = itemView.findViewById(R.id.container_main);
                price = itemView.findViewById(R.id.price);
               imageView = itemView.findViewById(R.id.image);
                company = itemView.findViewById(R.id.brand);
                remove = itemView.findViewById(R.id.remove);

            }
        }
    }

}