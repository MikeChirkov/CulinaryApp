package com.example.mikechirkov.culinaryapplication;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {

    protected TextView tv_create_acc;
    protected EditText field_login;
    protected EditText field_password;
    protected Button log_in;
    SharedPreferences sPref;

    final String SAVED_LOGIN = "saved_login";
    final String SAVED_PASSWORD = "saved_password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_create_acc = (TextView)findViewById(R.id.create_acc);
        field_login = findViewById(R.id.field_login);
        field_password = findViewById(R.id.field_password);

        field_login.getBackground().setColorFilter(Color.LTGRAY, PorterDuff.Mode.SRC_ATOP);
        field_password.getBackground().setColorFilter(Color.LTGRAY, PorterDuff.Mode.SRC_ATOP);

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

        if(loadText())
        {
            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(intent);
        }

    }

    public void OnClickAutorization(View view) {


       /* CallBack callBack = new CallBack();


        if (!field_login.getText().toString().equals("") && !field_password.getText().toString().equals("")) {
            Thread t = new Thread(new Authorization(field_login.getText().toString(), field_password.getText().toString(), callBack, "logIn"));
            t.start();

            try {
                t.join();
            } catch (Exception e) {

            }


            if (callBack.getStatus().equals("yes")) {*/
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent);
                saveText();
            /*}
            else{
                Toast toast = Toast.makeText(this, callBack.getStatus(), Toast.LENGTH_LONG);
                toast.show();

                field_password.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);

                //field_password.getBackground().setColorFilter(Color.LTGRAY, PorterDuff.Mode.SRC_ATOP);
            }


        } else {
            Toast toast = Toast.makeText(this, "Заполните поля", Toast.LENGTH_LONG);
            toast.show();
            //field_password.setBackgroundColor(Color.RED);
        }*/

    }

    void saveText() {
        sPref = getPreferences(MODE_PRIVATE);
        Editor ed = sPref.edit();
        ed.putString(SAVED_LOGIN, field_login.getText().toString());
        ed.putString(SAVED_PASSWORD, field_password.getText().toString());
        ed.commit();
    }
    boolean loadText() {
        sPref = getPreferences(MODE_PRIVATE);
        String login = sPref.getString(SAVED_LOGIN, "");
        String pass = sPref.getString(SAVED_PASSWORD, "");
        if(login.isEmpty() && pass.isEmpty()) {
            return false;
        }
        else {
            return true;
        }
    }
    @Override
    public void onRestart(){
        super.onRestart();
        sPref = getPreferences(MODE_PRIVATE);
        sPref.edit().clear().commit();

        field_password.getBackground().setColorFilter(Color.LTGRAY, PorterDuff.Mode.SRC_ATOP);
        field_login.setText("");
        field_password.setText("");
    }

}
