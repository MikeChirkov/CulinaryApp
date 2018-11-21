package com.example.mikechirkov.culinaryapplication;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class CreateAccountActivity extends AppCompatActivity {

    Button btn_back;
    Button btn_create_account;
    private TextView field_create_login;
    private TextView field_create_email;
    private TextView field_create_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);



        btn_back = (Button)findViewById(R.id.btn_back_LogIn);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_create_account = (Button) findViewById(R.id.btn_create_account);

        field_create_login = findViewById(R.id.field_create_login);
        field_create_password = findViewById(R.id.field_create_password);
        field_create_email = findViewById(R.id.field_create_email);

        field_create_login.getBackground().setColorFilter(Color.LTGRAY, PorterDuff.Mode.SRC_ATOP);
        field_create_password.getBackground().setColorFilter(Color.LTGRAY, PorterDuff.Mode.SRC_ATOP);
        field_create_email.getBackground().setColorFilter(Color.LTGRAY, PorterDuff.Mode.SRC_ATOP);

        btn_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                System.out.println(field_create_email);
                System.out.println(field_create_login);
                System.out.println(field_create_password);

                CallBack callBack = new CallBack();

                Thread t;

                if (!field_create_login.getText().toString().equals("") && !field_create_email.getText().toString().equals("") && !field_create_password.getText().toString().equals("")) {

                    t = new Thread(new CreateAccount(field_create_login.getText().toString(), field_create_password.getText().toString(), field_create_email.getText().toString(), callBack, "createAccount"));
                    t.start();

                    try {
                        t.join();


                        if (callBack.getStatus().equals("yes"))
                        {
                            finish();
                            Toast toast = Toast.makeText(getApplicationContext(), "Аккаунт успешно создан!", Toast.LENGTH_LONG);
                            toast.show();
                        }

                    } catch (Exception e) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Такой аккаунт уже создан!", Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Заполните все поля", Toast.LENGTH_LONG);
                    toast.show();
                }

            }
        });
    }

    @Override
    public void onRestart(){
        super.onRestart();
        field_create_email.setText("");
        field_create_login.setText("");
        field_create_password.setText("");
    }

    class CreateAccount extends Thread {
        private String login;
        private String password;
        private String email;
        private String proc;
        private CallBack callBack;


        public CreateAccount(String client_login, String client_password, String client_email, CallBack callBack, String proc) {
            login = client_login;
            password = client_password;
            email = client_email;
            this.proc = proc;
            this.callBack = callBack;
        }


        public void run() {

            try {

                //System.out.println("START!!!!!!!!!!");
                URL url = new URL("http://lewanov888.000webhostapp.com/?proc=" + proc + "&newLogin=" + login + "&newPassword=" + password + "&newEmail=" + email);
                URLConnection yc = url.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                String inputLine;
                StringBuilder sb = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    System.out.println(inputLine);
                    sb.append(inputLine + "\n");
                }
                in.close();

                inputLine = sb.toString();
                JSONObject jObject = new JSONObject(inputLine);
                String aJsonString = (String) jObject.get("statusCreateAcc");
                callBack.setStatus(aJsonString);

                System.out.println(inputLine + " status createAcc");


            } catch (Exception e) {

                System.out.println(e.getMessage() + " Error converted to JSON");
            }
        }
    }
}
