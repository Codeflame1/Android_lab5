package com.example.asus.lab3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;


public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder>{
    private String Name;
    private String Price;
    private String Message;
    private Context context;
    private Activity activity;
    private static int shopcart = 0;
    private String DYNAMICACTION = "dynamic_action";

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView mname;
        private TextView mprice;
        private TextView mmessage;
        private ImageView mimage;
        private ImageButton mback;
        private ImageButton mstar;
        private ImageButton mcart;

        private ViewHolder(View view){
            super(view);
            mname = view.findViewById(R.id.goodsname);
            mprice = view.findViewById(R.id.goodsprice);
            mmessage = view.findViewById(R.id.goodsmessage);
            mimage = view.findViewById(R.id.goodsimage);
            mback= view.findViewById(R.id.back);
            mstar= view.findViewById(R.id.star);
            mcart= view.findViewById(R.id.shopcartimage);
            mstar.setTag("0");
        }
    }

    DetailAdapter(String Name, String Price, String Message, Context context, Activity activity){
        this.Name = Name;
        this.Price = Price;
        this.Message = Message;
        this.activity = activity;
        this.context = context;
    }

    @Override
    public DetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position){
        viewHolder.mname.setText(Name);
        viewHolder.mprice.setText(Price);
        viewHolder.mmessage.setText(Message);
        viewHolder.mback.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });

        viewHolder.mcart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                shopcart++;
                Intent intent = new Intent();
                intent.setAction(DYNAMICACTION)
                      .putExtra("shopcart",shopcart)
                      .putExtra("name",Name)
                      .putExtra("price",Price)
                      .putExtra("message",Message);
                activity.setResult(1,intent);
                activity.sendBroadcast(intent);
                EventBus.getDefault().post(new MessageEvent(Name,Price,Message));
                Toast.makeText(context,R.string.put_into_shopcart, Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.mstar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Object tag=viewHolder.mstar.getTag();
                if(tag.equals("0")){
                    viewHolder.mstar.setTag("1");
                    viewHolder.mstar.setImageResource(R.mipmap.full_star);
                } else {
                    viewHolder.mstar.setTag("0");
                    viewHolder.mstar.setImageResource(R.mipmap.empty_star);
                }
            }
        });

        viewHolder.mimage.setImageResource(GoodsImage.getImage(Name));
    }

    @Override
    public int getItemCount(){
        return 1;
    }
}