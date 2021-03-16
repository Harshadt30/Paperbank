package com.example.paperbank;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SubjectAdapter extends ArrayAdapter<String> {

    Context context;
    String[] subjectArrayList;

    public SubjectAdapter(Context context, String[] subjectArrayList) {

        super(context, R.layout.subject_listview, subjectArrayList);
        this.context = context;
        this.subjectArrayList = subjectArrayList;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){

        if(view == null) {

            view = LayoutInflater.from(getContext()).inflate(R.layout.subject_listview, parent, false);
        }

        TextView subjectName = (TextView) view.findViewById(R.id.subjectName);
        subjectName.setText(subjectArrayList[position]);

        return view;
    }
}
