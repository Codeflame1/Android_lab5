package com.example.asus.lab3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class ShopCartAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<Goods> cartgoods;

    ShopCartAdapter(Context context, ArrayList<Goods> cartgoods){
        this.context = context;
        this.cartgoods = cartgoods;
    }
    private class ViewHolder{
        TextView minitial;
        TextView mname;
        TextView mprice;
    }

    @Override
    public int getCount(){
        if (cartgoods != null){
            return cartgoods.size();
        } else return 1;
    }

    @Override
    public Object getItem(int i){
        if (cartgoods == null){
            return null;
        }
        return cartgoods.get(i);
    }

    @Override
    public long getItemId(int i){
        return i;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewHolder viewholder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.shopcart_list, null);
            viewholder = new ViewHolder();
            viewholder.minitial = convertView.findViewById(R.id.initial);
            viewholder.mname = convertView.findViewById(R.id.name);
            viewholder.mprice = convertView.findViewById(R.id.price);
            convertView.setTag(viewholder);
        }    else {
            viewholder = (ViewHolder) convertView.getTag();
        }
        viewholder.mname.setText(cartgoods.get(position).getName());
        viewholder.mprice.setText(cartgoods.get(position).getPrice());
        String first = cartgoods.get(position).getName();
        if(first.equals("购物车")){
            viewholder.minitial.setText("*");
        }
        else viewholder.minitial.setText(cartgoods.get(position).getName().substring(0,1).toUpperCase());
        return convertView;
    }
}
