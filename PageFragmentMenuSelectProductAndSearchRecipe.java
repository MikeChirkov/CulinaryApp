package com.example.mikechirkov.culinaryapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filterable;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class PageFragmentMenuSelectProductAndSearchRecipe extends ArrayAdapter<State> implements Filterable {

    private LayoutInflater inflater;
    private int layout;
    private List<State> states;
    private Context mContext;
    private List<String> listFilterProducts;

    private ArrayList<State> arrayList;

    public PageFragmentMenuSelectProductAndSearchRecipe(Context context, int resource, List<State> states) {
        super(context, resource, states);
        this.states = states;
        mContext = context;
        this.inflater = LayoutInflater.from(context);
        this.arrayList = new ArrayList<State>();
        this.arrayList.addAll(states);
    }


    @Override
    public int getCount(){
        return states.size();
    }

    @Override
    public State getItem(int i){
        return states.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }

    private static class ViewHolder {
        private TextView nameProduct;
        private CheckBox flag;
    }

   @Override
    @NonNull
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            view = inflater.inflate(R.layout.fragment_menu_select_product_and_search_recipe_row, null);

            final PageFragmentMenuSelectProductAndSearchRecipe.ViewHolder viewHolder = new PageFragmentMenuSelectProductAndSearchRecipe.ViewHolder();

            viewHolder.nameProduct = (TextView) view.findViewById(R.id.nameProduct);
            viewHolder.flag = (CheckBox) view.findViewById(R.id.nameProductCheckbox);

            viewHolder.flag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    State element = (State) viewHolder.flag.getTag();
                    element.setFlag(buttonView.isChecked());
                }
            });

            view.setTag(viewHolder);
            viewHolder.flag.setTag(states.get(position));

            //---------------------

        } else {
            view = convertView;
            ((ViewHolder) view.getTag()).flag.setTag(states.get(position));
        }

        PageFragmentMenuSelectProductAndSearchRecipe.ViewHolder holder = (PageFragmentMenuSelectProductAndSearchRecipe.ViewHolder) view.getTag();
        holder.nameProduct.setText(states.get(position).getName());
        holder.flag.setChecked(states.get(position).getFlag());

        return view;
    }

    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        states.clear();
        if(charText.length()==0){
            states.addAll(arrayList);
        }
        else{
            for(State state : arrayList){
                if(state.getName().toLowerCase(Locale.getDefault())
                        .contains(charText)){
                    states.add(state);
                }
            }
        }
        notifyDataSetChanged();
    }

}


