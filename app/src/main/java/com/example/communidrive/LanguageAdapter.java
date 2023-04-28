package com.example.communidrive;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class LanguageAdapter extends BaseAdapter {

    Context context;
    int[] flags;
    String[] countryNames;
    LayoutInflater inflater;

    public LanguageAdapter(Context context, int[] flags, String[] countryNames) {
        this.context = context;
        this.flags = flags;
        this.countryNames = countryNames;
        inflater = (LayoutInflater.from(context.getApplicationContext()));
    }

    @Override public int getCount() { return flags.length; }
    @Override public Object getItem(int i) { return null; }
    @Override public long getItemId(int i) {
        return 0;
    }
    @Override public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.language_spinner_item, null);
        ImageView icon = (ImageView) view.findViewById(R.id.language_imageview);
        TextView name = (TextView) view.findViewById(R.id.language_textview);
        icon.setImageResource(flags[i]);
        name.setText(countryNames[i]);
        return view;
    }
}
