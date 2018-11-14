package com.example.mikechirkov.culinaryapplication;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    protected TextView tv_create_acc;
    protected EditText field_login;
    protected EditText field_password;
    protected Button log_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_create_acc = (TextView)findViewById(R.id.create_acc);

        tv_create_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateAccountActivity.class);
                startActivity(intent);
            }
        });

        log_in = (Button)findViewById(R.id.btn_logIn);
        log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnClickAutorization(v);
            }
        });


    }

    public void OnClickAutorization(View view) {

        field_login = findViewById(R.id.field_login);
        field_password = findViewById(R.id.field_password);

        CallBack callBack = new CallBack();


        if (!field_login.getText().toString().equals("") && !field_password.getText().toString().equals("")) {
            Thread t = new Thread(new Authorization(field_login.getText().toString(), field_password.getText().toString(), callBack, "logIn"));
            t.start();

            try {
                t.join();
            } catch (Exception e) {

            }

            Toast toast = Toast.makeText(this, callBack.getStatus(), Toast.LENGTH_LONG);
            toast.show();


            if (callBack.getStatus().equals("yes")) {
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent);
            }


        } else {
            Toast toast = Toast.makeText(this, "Заполните поля", Toast.LENGTH_LONG);
            toast.show();
            field_password.setBackgroundColor(Color.RED);

        }
    }

}
