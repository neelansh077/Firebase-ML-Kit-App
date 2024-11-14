package com.example.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class appAdapter extends BaseAdapter {

    Context context;
    String listApps[];
    int listImages[];
    LayoutInflater inflater;

    public appAdapter(Context ctx, String[] appNames, int[] appImages) {
        this.context = ctx;
        this.listApps = appNames;
        this.listImages = appImages;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return listApps.length ;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View convertView = inflater.inflate(R.layout.activity_list_view,null);
        TextView txtView = (TextView) convertView.findViewById(R.id.textView3);
        ImageView imgView = (ImageView) convertView.findViewById(R.id.imageView);
        txtView.setText(listApps[position]);
        imgView.setImageResource(listImages[position]);
        return convertView;
    }
}
