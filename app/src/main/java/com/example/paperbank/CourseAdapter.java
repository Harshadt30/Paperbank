package com.example.paperbank;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CourseAdapter extends ArrayAdapter<String> {

    Context context;
    String[] courseArrayList;

    public CourseAdapter(Context context, String[] courseArrayList) {

        super(context, R.layout.course_listview, courseArrayList);
        this.context = context;
        this.courseArrayList = courseArrayList;
    }

    public View getView(int position, View view, ViewGroup parent){

        if(view == null) {

            view = LayoutInflater.from(getContext()).inflate(R.layout.course_listview, parent, false);
        }

        TextView courseTitle = view.findViewById(R.id.courseTitle);
        courseTitle.setText(courseArrayList[position]);

        return view;
    }

}
