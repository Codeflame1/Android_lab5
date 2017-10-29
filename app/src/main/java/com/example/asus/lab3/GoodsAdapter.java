package com.example.asus.lab3;


import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.ViewHolder>{
    private ArrayList<Goods> list;
    public interface OnItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }

    private OnItemClickListener onItemClickListener=null;
    class ViewHolder extends RecyclerView.ViewHolder{
        View mview;
        TextView mInitial;
        TextView mName;

        ViewHolder(View view){
            super(view);
            mview = view;
            mInitial = view.findViewById(R.id.initial_1);
            mName = view.findViewById(R.id.name_1);
        }
    }

    void remove(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }

    GoodsAdapter(ArrayList<Goods> myDataset){
        list = myDataset;
    }

    @Override
    public GoodsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.goods_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewholder, @SuppressLint("RecyclerView") final int position) {
        final Goods good = list.get(position);
        viewholder.mInitial.setText(good.getName().substring(0,1).toUpperCase());
        viewholder.mName.setText(good.getName());
        if(onItemClickListener!=null){
            viewholder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    onItemClickListener.onClick(position);
                }
            });

            viewholder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View view){
                    onItemClickListener.onLongClick(position);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount(){
        return list.size();
    }

    void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

}