package com.example.quuanlybanhang;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends ArrayAdapter<item> {
    private Context context;
    private List<item> itemList;

    public ItemAdapter(@NonNull Context context, ArrayList<item> itemList) {
        super(context, R.layout.activity_item_holder, itemList);
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_item_holder, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tv_name = convertView.findViewById(R.id.tv_name_item);
            viewHolder.tv_mota = convertView.findViewById(R.id.tv_mota_item);
            viewHolder.tv_price = convertView.findViewById(R.id.tv_price);
            viewHolder.img_item = convertView.findViewById(R.id.imageView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        item currentItem = itemList.get(position);
        viewHolder.tv_name.setText(currentItem.getName());
        viewHolder.tv_mota.setText(currentItem.getMota());
        viewHolder.tv_price.setText(currentItem.getPrice());
        viewHolder.img_item.setImageBitmap(getImageView(currentItem.getImage()));

        return convertView;
    }

    private Bitmap getImageView(String encodeImage) {
        if (encodeImage == null || encodeImage.isEmpty()) {
            // Trả về một hình ảnh mặc định hoặc null nếu encodeImage là null hoặc trống
            return null;
        }
        byte[] bytes = Base64.decode(encodeImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    static class ViewHolder {
        TextView tv_name;
        TextView tv_mota;
        TextView tv_price;
        ImageView img_item;
    }
}
