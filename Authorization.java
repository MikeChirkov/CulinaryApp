package com.example.mikechirkov.culinaryapplication;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class Authorization extends Thread {

    private String login;
    private String password;
    private String proc;
    private CallBack callBack;

    public Authorization(String client_login, String client_password, CallBack callBack, String proc) {
        login = client_login;
        password = client_password;
        this.proc = proc;
        this.callBack = callBack;
    }


    public void run() {

        try {
            System.out.println("///////////////////////////////////////////////");

            URL url = new URL("http://lewanov888.000webhostapp.com/?proc=" + proc + "&login=" + login + "&password=" + password);

            URLConnection yc = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            String inputLine;
            StringBuilder sb = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine + "\n");
            }
            in.close();


            inputLine = sb.toString();

            System.err.println(inputLine.toString());

            JSONObject jObject = new JSONObject(inputLine);
            String aJsonString = (String) jObject.get("status");
            callBack.setStatus(aJsonString);

            System.out.println(inputLine.toString() + "2222 status autorization");

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }
}