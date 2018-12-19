package com.example.mikechirkov.culinaryapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

public class PageFragmentMenuGetUserRecipe extends ArrayAdapter<State> {

    private LayoutInflater inflater;
    private int layout;
    private List<State> states;
    private Context mContext;

    public PageFragmentMenuGetUserRecipe(Context context, int resource, List<State> states) {
        super(context, resource, states);
        this.states = states;
        mContext = context;
        this.inflater = LayoutInflater.from(context);
    }

    static class ViewHolder {
        private ImageView imgRecipe;
        private TextView textRecipe;
        private CheckBox checkFavourRecipe;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            view = inflater.inflate(R.layout.fragment_menu_profile_row, null);

            final PageFragmentMenuGetUserRecipe.ViewHolder viewHolder = new PageFragmentMenuGetUserRecipe.ViewHolder();


            viewHolder.imgRecipe = (ImageView) view.findViewById(R.id.imgReciepe);
            viewHolder.textRecipe = (TextView) view.findViewById(R.id.nameReciepe);


            view.setTag(viewHolder);
        } else {
            view = convertView;
        }

        PageFragmentMenuGetUserRecipe.ViewHolder holder = (PageFragmentMenuGetUserRecipe.ViewHolder) view.getTag();
        holder.imgRecipe.setImageResource(states.get(position).getImgProduct());
        holder.textRecipe.setText(states.get(position).getNameProduct());


        return view;
    }
}
