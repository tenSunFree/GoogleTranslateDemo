package com.home.googletranslatedemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SideDrawerBottomListAdapter extends ArrayAdapter<SideDrawerBottomItem> {

    private Context context;
    private LayoutInflater layoutInflater;
    private ViewHolder viewHolder;
    private SideDrawerBottomItem sideDrawerBottomItem;

    static class ViewHolder {
        ImageView pictureImageView;
        TextView textTextView;
    }

    public SideDrawerBottomListAdapter(@NonNull Context context) {
        super(context, R.layout.item_side_drawer_bottom);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_side_drawer_bottom, null);
            viewHolder.pictureImageView = (ImageView) convertView.findViewById(R.id.pictureImageView);
            viewHolder.textTextView = (TextView) convertView.findViewById(R.id.textTextView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        sideDrawerBottomItem = this.getItem(position);
        viewHolder.pictureImageView.setImageResource(sideDrawerBottomItem.getPictureId());
        viewHolder.textTextView.setText(sideDrawerBottomItem.getText());
        return convertView;
    }
}
