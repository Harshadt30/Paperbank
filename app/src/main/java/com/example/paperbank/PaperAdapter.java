package com.example.paperbank;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PaperAdapter extends BaseAdapter {

    Context context;

    public PaperAdapter(Context context, ArrayList<Papers> papers) {
        this.context = context;
        this.papers = papers;
    }

    ArrayList<Papers> papers = new ArrayList<>();

    @Override
    public int getCount() {
        return papers.size();
    }

    @Override
    public Object getItem(int position) {
        return papers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.paper_listview, null);
        }

        Papers papers_all = papers.get(position);
        TextView paper_year = convertView.findViewById(R.id.paper_year);
        TextView paper_name = convertView.findViewById(R.id.paper_name);
        TextView paper_id = convertView.findViewById(R.id.paper_id);
        TextView paper_size = convertView.findViewById(R.id.paper_size);
        TextView paper_path = convertView.findViewById(R.id.paper_path);

        paper_year.setText(papers_all.getPaper_year());
        paper_name.setText(papers_all.getPaper_name());
        paper_size.setText(papers_all.getPaper_size() + " KB");
        paper_id.setText(papers_all.getPaper_id());
        paper_path.setText(papers_all.getPaper_path());

        return convertView;
    }
}
