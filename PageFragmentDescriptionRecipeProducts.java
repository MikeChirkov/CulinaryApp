package com.example.mikechirkov.culinaryapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;


public class PageFragmentDescriptionRecipeProducts extends BaseAdapter {

    private Context mContext;
    private final String[] listProduct;

    // private final int[] gridViewImageId;

    public PageFragmentDescriptionRecipeProducts(Context context, String[] listProduct) {
        mContext = context;
        this.listProduct = listProduct;
        //this.gridViewString = gridViewString;
    }

    @Override
    public int getCount() {
        return listProduct.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View listViewRow;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            listViewRow = inflater.inflate(R.layout.fragment_description_recipe_products_row, null);
            TextView nameProd = (TextView) listViewRow.findViewById(R.id.nameProduct);
            nameProd.setText(listProduct[i]);

            TextView countProd = (TextView) listViewRow.findViewById(R.id.countProduct);
            countProd.setText("123");

            TextView mesProd = (TextView) listViewRow.findViewById(R.id.measureProduct);
            mesProd.setText("гр.");


        } else {
            listViewRow = (View) convertView;
        }

        return listViewRow;
    }


}