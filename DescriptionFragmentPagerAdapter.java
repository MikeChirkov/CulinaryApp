package com.example.mikechirkov.culinaryapplication;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;


public class DescriptionFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{"Ингриденты", "Описание"};
    private Context context;

    DescriptionFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragmentsDescriptionRecipe.newInstance(position + 1); //
    }

    @Override
    public CharSequence getPageTitle(int position) {
        SpannableString sb = new SpannableString(" " + tabTitles[position]);
        return sb;
    }
}