package com.example.paperbank;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.w3c.dom.Text;

public class PaperAdapter extends ArrayAdapter<String> {

    Context context;
    String[] papersArrayList;

    public PaperAdapter(Context context, String[] papersArrayList) {
        super(context, R.layout.paper_listview, papersArrayList);
        this.context = context;
        this.papersArrayList = papersArrayList;
    }

    public View getView(int position, View view, ViewGroup parent) {

        if(view == null) {

            view = LayoutInflater.from(getContext()).inflate(R.layout.paper_listview, parent, false);
        }

        TextView paperName = view.findViewById(R.id.paperName);

        paperName.setText(papersArrayList[position]);
        return view;
    }
}
