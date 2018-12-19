package com.example.mikechirkov.culinaryapplication;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class PageFragmentMenuCreateRecipe {

    private LayoutInflater inflater;
    private Context mContext;

    public PageFragmentMenuCreateRecipe(Context context) {
        mContext = context;
        this.inflater = LayoutInflater.from(context);
    }

    static class ViewHolder {
        private ImageButton imgCamera;
        private Button buttonCamera;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            view = inflater.inflate(R.layout.fragment_create_reciepe, null);

            PageFragmentMenuCreateRecipe.ViewHolder viewHolder = new PageFragmentMenuCreateRecipe.ViewHolder();
            viewHolder.imgCamera = (ImageButton) view.findViewById(R.id.imageBut);
            view.setTag(viewHolder);

        } else {
            view = convertView;
        }

        return view;
    }
}
