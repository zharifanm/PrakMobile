package com.example.shoppinglist;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import java.util.ArrayList;

public class ShoppingListAdapter extends BaseAdapter {

    private final ArrayList<ShoppingItem> shoppingItems;
    private final LayoutInflater inflater;

    public ShoppingListAdapter(Context context, ArrayList<ShoppingItem> shoppingItems) {
        this.shoppingItems = shoppingItems;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return shoppingItems.size();
    }

    @Override
    public Object getItem(int position) {
        return shoppingItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder();
            holder.tvItemName = convertView.findViewById(R.id.tvItemName);
            holder.tvCategory = convertView.findViewById(R.id.tvCategory);
            holder.checkBox = convertView.findViewById(R.id.checkBox);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ShoppingItem item = shoppingItems.get(position);

        holder.tvItemName.setText(item.getName());
        holder.tvCategory.setText(item.getCategory());
        holder.checkBox.setChecked(item.isPurchased());

        // Apply strikethrough effect if item is purchased
        if (item.isPurchased()) {
            holder.tvItemName.setPaintFlags(holder.tvItemName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tvItemName.setAlpha(0.6f);
            holder.tvCategory.setAlpha(0.6f);
        } else {
            holder.tvItemName.setPaintFlags(holder.tvItemName.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            holder.tvItemName.setAlpha(1.0f);
            holder.tvCategory.setAlpha(1.0f);
        }

        // Set checkbox listener
        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(item.isPurchased());
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            item.setPurchased(isChecked);
            notifyDataSetChanged();
        });

        return convertView;
    }

    static class ViewHolder {
        TextView tvItemName;
        TextView tvCategory;
        CheckBox checkBox;
    }
}