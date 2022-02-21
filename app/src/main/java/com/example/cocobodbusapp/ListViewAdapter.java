package com.example.cocobodbusapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater inflater;
    private List<Destinations> destinationsList = null;
    private ArrayList<Destinations> arraylist;

    public ListViewAdapter(Context mContext, List<Destinations> destinationsList) {
        this.mContext = mContext;
        this.destinationsList = destinationsList;
        this.inflater = LayoutInflater.from(mContext);

        this.arraylist = new ArrayList<Destinations>();
        this.arraylist.addAll(destinationsList);
    }

    public class ViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final ViewHolder holder;
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
        holder.name.setText(destinationsList.get(position).getDestinationName());
        return view;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        destinationsList.clear();
        if (charText.length() == 0) {
            destinationsList.addAll(arraylist);
        } else {
            for (Destinations wp : arraylist) {
                if (wp.getDestinationName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    destinationsList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}
