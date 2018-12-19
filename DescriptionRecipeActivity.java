package com.example.mikechirkov.culinaryapplication;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DescriptionRecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_recipe);

        TextView tv = findViewById(R.id.textViewDescribeRecipe);
        CheckBox cb = findViewById(R.id.checkFavourites);
        ImageView iv = findViewById(R.id.imageViewDescribeRecipe);
        //iv.setImageResource(R.drawable.china);



        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("key");


            int idRecipe = extras.getInt("idRecipe");

            boolean flag = extras.getBoolean("checkFav");
            //String imageName = "r" + idRecipe;

            tv.setText(value);

            //int imageId = getResources().getIdentifier(imageName, "drawable", getPackageName());
            //String image = getResources().getResourceName(imageid);
            //System.out.println(imageId);

            iv.setImageResource(idRecipe);
            cb.setChecked(flag);
        }

        ViewPager viewPager = findViewById(R.id.viewPagerDescribeRecipe);
        viewPager.setAdapter(new DescriptionFragmentPagerAdapter(getSupportFragmentManager(), DescriptionRecipeActivity.this));

        // Передаём ViewPager в TabLayout
        TabLayout tabLayout = findViewById(R.id.tabLayoutDescribeRecipe);
        tabLayout.setupWithViewPager(viewPager);


    }



}