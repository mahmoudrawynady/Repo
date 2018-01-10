package com.example.mahmoudrawy.repo.Views;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mahmoudrawy.repo.R;

import java.util.List;

/**
 * Created by mahmoud rawy™ 01221240053 on 09/01/2018.
 */

/*
this class is adapter for the list in the dialog that will appear to user for html url
 */
public class DialogListAdapter extends ArrayAdapter<URLItem> {
    public DialogListAdapter(Context context, List<URLItem> objectList) {
        super(context, R.layout.list_item, R.id.TV_html, objectList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = super.getView(position, convertView, parent);
        TextView textHtmlURL = (TextView) row.findViewById(R.id.TV_html);
        ImageView iconHtmlUL = (ImageView) row.findViewById(R.id.IM_html);
        URLItem htmlURLItem = getItem(position);
        textHtmlURL.setText(htmlURLItem.getItemLabel());
        iconHtmlUL.setImageResource(htmlURLItem.getItemIcon());
        return row;
    }
}
