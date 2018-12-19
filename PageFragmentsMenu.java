package com.example.mikechirkov.culinaryapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.widget.Toast.*;

public class PageFragmentsMenu extends Fragment {

    private Uri file;


    final String TAG = "myLogs";

    private ImageButton imageButton;
    private Button addEdit;
    private Button delEdit;
    private LinearLayout linearLayout;
    private int countID = 0;

    ArrayList<EditText> editTexts;
    ArrayList<LinearLayout> linearLayouts;

    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    private SearchView searchProducts;
    private SearchView searchFavourites;

    private ImageButton menu;

    private List<State> states = new ArrayList();

    private List<State> states_profle = new ArrayList();

    SharedPreferences pref;
    final String SAVED_LOGIN = "saved_login";
    final String SAVED_PASSWORD = "saved_password";

    ArrayList<String> items = new ArrayList<>();

    ListView listView;

    ListView listViewProfile;

    ArrayList<State> arrayList;

    /*int[] gridViewImageId = {
            R.drawable.pitsa, R.drawable.r2, R.drawable.r3, R.drawable.r4, R.drawable.r5, R.drawable.r6, R.drawable.r7,
    };*/

    public static PageFragmentsMenu newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragmentsMenu fragment = new PageFragmentsMenu();
        fragment.setArguments(args);

        System.out.println("newInstance " + page);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPage = getArguments().getInt(ARG_PAGE);
        }
        editTexts = new ArrayList<EditText>();
        linearLayouts = new ArrayList<LinearLayout>();
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;

        System.err.println("PAGE ----");


        if (mPage == 1) {
            System.err.println("PAGE 1");

            view = inflater.inflate(R.layout.fragment_menu_profile, container, false);

            initGridView();

            searchFavourites = view.findViewById(R.id.searchFavourites);

            menu = view.findViewById(R.id.btn_menu);

            menu.setOnClickListener(viewClickListener);



            listViewProfile = view.findViewById(R.id.reciepeList);

            final PageFragmentMenuGetUserRecipe adapter = new PageFragmentMenuGetUserRecipe(getContext(), 1, states_profle);

            listViewProfile.setAdapter(adapter);

            AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                    // получаем выбранный пункт
                    State selectedState = (State) parent.getItemAtPosition(position);
                    Toast.makeText(getContext(), "Был выбран пункт " + selectedState.getNameProduct() + " " + selectedState.getImgProduct(),
                            Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getContext(), DescriptionRecipeActivity.class);
                    intent.putExtra("key", selectedState.getNameProduct());
                    intent.putExtra("idRecipe", selectedState.getImgProduct());
                    intent.putExtra("checkFav", selectedState.getFavour());
                    startActivity(intent);
                }
            };
            listViewProfile.setOnItemClickListener(itemListener);

            System.err.println("PAGE 1++++");
            return view;


        } else if (mPage == 2) {

            System.err.println("PAGE 2");

            view = inflater.inflate(R.layout.fragment_menu_select_product_and_search_recipe, container, false);

            setInitialData();

            listView = view.findViewById(R.id.productList);

            final PageFragmentMenuSelectProductAndSearchRecipe adapter = new PageFragmentMenuSelectProductAndSearchRecipe(getContext(), 1, states);

            listView.setAdapter(adapter);

            AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                    // получаем выбранный пункт
                    State selectedState = (State) parent.getItemAtPosition(position);
                    makeText(getContext(), "Был выбран пункт " + selectedState.getName() + " = " + selectedState.getFlag(),
                            LENGTH_SHORT).show();
                }
            };
            listView.setOnItemClickListener(itemListener);

           /* btn_check_items = view.findViewById(R.id.checkItems);
            btn_check_items.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int count = 0;
                    for (int i = 0; i < states.size(); i++) {
                        if (states.get(i).getFlag()) count++;
                    }
                    Toast.makeText(getContext(), "Количество " + count,
                            Toast.LENGTH_SHORT).show();

                }
            });*/

            searchProducts = view.findViewById(R.id.searchProducts);

            searchProducts.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    if(TextUtils.isEmpty(s)){
                        adapter.filter("");
                        listView.clearTextFilter();
                    }
                    else{
                        adapter.filter(s);
                    }
                    return true;
                }
            });

            return view;

        } else if (mPage == 3) {

            System.err.println("PAGE 3");

            view = inflater.inflate(R.layout.fragment_create_reciepe, container, false);

            linearLayout = view.findViewById(R.id.linLayout);

            imageButton = view.findViewById(R.id.imageBut);
            addEdit = view.findViewById(R.id.buttonAdd);
            delEdit = view.findViewById(R.id.buttonDel);


            addEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout ll = new LinearLayout(getContext());
                    EditText ed1 = new EditText(getContext());
                    EditText ed2 = new EditText(getContext());
                    EditText ed3 = new EditText(getContext());
                    ed1.setHint("Ингридиент");
                    ed2.setHint("Кол-во");
                    ed3.setHint("Мера");
                    ed1.setHintTextColor(Color.GRAY);
                    ed2.setHintTextColor(Color.GRAY);
                    ed3.setHintTextColor(Color.GRAY);
                    ed1.setTextColor(Color.LTGRAY);
                    ed2.setTextColor(Color.LTGRAY);
                    ed3.setTextColor(Color.LTGRAY);
                    ed1.setTextSize(16);
                    ed2.setTextSize(16);
                    ed3.setTextSize(16);
                    ll.setOrientation(LinearLayout.HORIZONTAL);
                    ed1.setLayoutParams(
                            new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    0.70f)
                    );
                    ed2.setLayoutParams(
                            new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    0.20f)
                    );
                    ed3.setLayoutParams(
                            new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    0.10f)
                    );

                    ll.addView(ed1);

                    editTexts.add(ed1);
                    ll.addView(ed2);
                    editTexts.add(ed2);
                    ll.addView(ed3);
                    editTexts.add(ed3);

                    linearLayout.addView(ll);
                    linearLayouts.add(ll);
                }
            });

            delEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (linearLayouts.size() > 0) {
                        // находим в коллекции последний TextView
                        LinearLayout tv1 = linearLayouts.get(linearLayouts.size() - 1);
                        // удаляем из диалога
                        linearLayout.removeView(tv1);
                        // удаляем из коллекции
                        linearLayouts.remove(tv1);
                    }
                }
            });
            /*TextView textView = (TextView) view;
            textView.setText("Fragment test #" + mPage);

            System.err.println("PAGE 3++++");*/
            return view;
        }

        System.err.println("PAGE NULL");
        return null;
    }


    View.OnClickListener viewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showPopupMenu(v);
        }
    };

    private void showPopupMenu(View v) {

        //PopupMenu popupMenu = new PopupMenu(getContext(), v);
        Context wrapper = new ContextThemeWrapper(getContext(),R.style.popupMenuStyle);
        PopupMenu popupMenu = new PopupMenu(wrapper, v);

        popupMenu.inflate(R.menu.popupmenu);

        popupMenu
                .setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.exit_acc:
                                makeText(getContext(),
                                        "Вы вышли из аккаунта",
                                        LENGTH_SHORT).show();

                                getActivity().onBackPressed();
                                
                                return true;
                           /* case R.id.menu2:
                                Toast.makeText(getContext(),
                                        "Вы выбрали PopupMenu 2",
                                        Toast.LENGTH_SHORT).show();
                                return true;*/
                            default:
                                return false;
                        }
                    }
                });

        popupMenu.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.searchProducts){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setInitialData() {
        states.clear();
        states.add(new State("Продукт1"));
        states.add(new State("Продукт2"));
        states.add(new State("Продукт3"));
        states.add(new State("Продукт4"));
        states.add(new State("Продукт5"));
        states.add(new State("Продукт6"));
        states.add(new State("Продукт7"));
        states.add(new State("Продукт8"));
        states.add(new State("Продукт9"));
        states.add(new State("Продукт10"));
        states.add(new State("Продукт11"));
        states.add(new State("Продукт12"));
        states.add(new State("Продукт13"));
        states.add(new State("q"));
        states.add(new State("qw"));
        states.add(new State("qggdfghfdgdfgdfgdfgfdgfdgfdgfdgfdgfdgfdgfdgfdg"));
    }


    private void initGridView() {
        states_profle.clear();
        states_profle.add(new State("Спагетти по залупски", R.drawable.r1, true));
        states_profle.add(new State("Залупа какая-то", R.drawable.r2, true));
        states_profle.add(new State("Пюрешка", R.drawable.r3, true));
    }

}

class State {

    private String name; // название
    private String title;  // столица
    private int flagResource; // ресурс флага
    private boolean flag;//
    private boolean favourRecipe;//

    private String nameProduct;
    private int imgProduct;

    public State(String name) {

        this.name = name;
        this.flag = false;
    }

    public State(String nameProduct, int imgProduct, boolean favourRecipe) {
        this.nameProduct = nameProduct;
        this.imgProduct = imgProduct;
        this.favourRecipe =  favourRecipe;
    }

    public String getNameProduct() {
        return this.nameProduct;
    }

    public boolean getFavour() {
        return this.favourRecipe;
    }

    public void setFavour(boolean f) {
        this.favourRecipe = f;
    }

    public void setNameProduct(String name) {
        this.nameProduct = name;
    }

    public int getImgProduct() {
        return this.imgProduct;
    }

    public void setImgProduct(int img) {
        this.imgProduct = img;
    }
//----------------------------


    public void setFlag(boolean f) {
        this.flag = f;
    }


    public boolean getFlag() {
        return this.flag;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}