package com.example.computerhardwaremall;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.computerhardwaremall.Models.LoginModel;
import com.example.computerhardwaremall.network.NetworkClient;
import com.example.computerhardwaremall.network.NetworkService;
import com.example.computerhardwaremall.utils.SesionManager;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class LoginActivity extends AppCompatActivity {

    EditText email, pass;
    Button login;
    TextView register;
    boolean isEmailValid, isPasswordValid;
    TextInputLayout emailError, passError;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        preferences = getSharedPreferences("User", MODE_PRIVATE);
        editor = preferences.edit();

        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);

        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        register = (TextView) findViewById(R.id.register);
        emailError = (TextInputLayout) findViewById(R.id.emailError);
        passError = (TextInputLayout) findViewById(R.id.passError);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (email == null || email.getText() == null || email.getText().toString() == null || !verifyEmail(email.getText().toString())) {
                    emailError.setError(getResources().getString(R.string.email_error));
                }
                if (pass.getText().toString().isEmpty()) {
                    passError.setError(getResources().getString(R.string.password_error));
                    isPasswordValid = false;
                } else if (pass.getText().length() < 6) {
                    passError.setError(getResources().getString(R.string.error_invalid_password));
                    isPasswordValid = false;
                } else {
                    isPasswordValid = true;
                    passError.setErrorEnabled(false);
                    login(email.getText().toString(), pass.getText().toString());
                }


            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));

            }
        });
    }

    void login(String email,String pass){

        Dialog Dialog = new Dialog(LoginActivity.this);
        Dialog.setContentView(R.layout.loading_dialog);
        Dialog.setTitle("Loading..");
        Dialog.setCancelable(false);
        Dialog.show();

        NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
        Call< LoginModel > call = networkService.loginUser(email,pass);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call< LoginModel > call, Response< LoginModel > response) {
                Dialog.dismiss();
                if (response != null && response.body() != null && response.body().getSuccess().equals("1")) {
                    Toast.makeText(LoginActivity.this, "" + response.body().getMassage(), Toast.LENGTH_SHORT).show();
                    editor.putString("name",response.body().getData().getName());
                    editor.putString("email",response.body().getData().getEmail());
                    editor.putString("mobile",response.body().getData().getMobile());
                    editor.apply();
                    SesionManager.setIsLoggedin(LoginActivity.this,"LoggedIn");
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Dialog.dismiss();
                Toast.makeText(LoginActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    boolean verifyEmail(String email) {

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (email.matches(emailPattern) && email.length() > 0) {
            return true;
        }

        return false;
    }
}