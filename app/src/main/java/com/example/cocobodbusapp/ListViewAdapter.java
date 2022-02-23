package com.example.cocobodbusapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater inflater;
    private List<Destinations> destinationNamesList = null;
    private ArrayList<Destinations> arraylist;

    public ListViewAdapter(Context mContext, List<Destinations> destinationNamesList) {
        this.mContext = mContext;
        this.destinationNamesList = destinationNamesList;
        this.inflater = LayoutInflater.from(mContext);

        this.arraylist = new ArrayList<Destinations>();
        this.arraylist.addAll(destinationNamesList);
    }

    public class ViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return destinationNamesList.size();
    }

    @Override
    public Object getItem(int position) {
        return destinationNamesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final ViewHolder holder;
         SearchView editsearch;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.list_view_items, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.destinationName);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(destinationNamesList.get(position).getDestinationName());
        return view;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        destinationNamesList.clear();
        if (charText.length() == 0) {
            destinationNamesList.addAll(arraylist);
        } else {
            for (Destinations wp : arraylist) {
                if (wp.getDestinationName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    destinationNamesList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}
