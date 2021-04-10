package com.example.paperbank;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CourseAdapter extends BaseAdapter {

    Context context;
    ArrayList<Courses> courses;

    public CourseAdapter(Context context, ArrayList<Courses> courses) {
        this.context = context;
        this.courses = courses;
    }

    @Override
    public int getCount() {
        return courses.size();
    }

    @Override
    public Object getItem(int position) {
        return courses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.course_listview,null);
        }

        TextView course_id = convertView.findViewById(R.id.course_id);
        TextView course_name = convertView.findViewById(R.id.course_name);
        TextView course_detail = convertView.findViewById(R.id.course_detail);

        Courses course_all = courses.get(position);

        course_id.setText(course_all.getId());
        course_name.setText(course_all.getName());
        course_detail.setText(course_all.getDetail());

        return convertView;
    }
}
