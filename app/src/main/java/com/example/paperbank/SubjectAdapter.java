package com.example.paperbank;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SubjectAdapter extends BaseAdapter {

    Context context;
    ArrayList<Subjects> subjects;

    public SubjectAdapter(Context context, ArrayList<Subjects> subjects) {
        this.context = context;
        this.subjects = subjects;
    }

    @Override
    public int getCount() {
        return subjects.size();
    }

    @Override
    public Object getItem(int position) {
        return subjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.subject_listview, null);

        }

        TextView subject_id = convertView.findViewById(R.id.subject_id);
        TextView subject_name = convertView.findViewById(R.id.subject_name);

        Subjects subject_all = subjects.get(position);

        subject_id.setText(subject_all.getSubject_id());
        subject_name.setText(subject_all.getSubject_name());

        return convertView;
    }
}
