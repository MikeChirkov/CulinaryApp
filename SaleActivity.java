package com.example.mikechirkov.culinaryapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class SaleActivity extends AppCompatActivity {

    final List<SalesInfo> salesInfos = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);

        final ListView listView = findViewById(R.id.listView);

        setInitialData();


            final SalesAdapter salesAdapter = new SalesAdapter(getApplicationContext(), 1, salesInfos);
            listView.setAdapter(salesAdapter);

            AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                    SalesInfo selectedItem = (SalesInfo) parent.getItemAtPosition(position);
                    Toast.makeText(getApplicationContext(), "Был выбран пункт " + selectedItem.getNameProduct()+ " " + selectedItem.getNameMagaz(),
                            Toast.LENGTH_SHORT).show();

//                    Intent intent = new Intent(getApplicationContext(), DescriptionRecipeActivity.class);
//                    intent.putExtra("recipeId", selectedItem.getIdRecipe());
//                    intent.putExtra("recipeName", selectedItem.getNameRecipe());
//                    intent.putExtra("recipeImg", selectedItem.getImgRecipe());
//                    intent.putExtra("recipeDesc", selectedItem.getDescriptionRecipeRecipe());
//                    startActivity(intent);

                }
            };
            listView.setOnItemClickListener(itemListener);
        }

    private void setInitialData() {
        salesInfos.clear();
        salesInfos.add(new SalesInfo("Молоко", "Молочные", "Пятёрочка", "69.90"));
        salesInfos.add(new SalesInfo("павпвапавпвапвап павпвапвап п вапв", "пвапва", "Магнит", "69.90"));
        salesInfos.add(new SalesInfo("павпвапавпвапвап павпвапвап п вапвавыавыаываываываыв авы аыв аыв аыв аыв аыв аыв аыв аыввыавыва", "пвапва", "METRO", "69.90"));
    }
}



class SalesInfo {
    private String nameProduct;
    private String nameMagaz;
    private String category;
    private String price;


    public SalesInfo(String nameProduct, String category, String nameMagaz, String price) {

        this.nameProduct = nameProduct;
        this.nameMagaz = nameMagaz;
        this.category = category;
        this.price = price;

    }


    public String getNameProduct() {
        return this.nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getNameMagaz() {
        return nameMagaz;
    }

    public String getCategory() {
        return category;
    }

    public String getPrice() {
        return price;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setNameMagaz(String nameMagaz) {
        this.nameMagaz = nameMagaz;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}