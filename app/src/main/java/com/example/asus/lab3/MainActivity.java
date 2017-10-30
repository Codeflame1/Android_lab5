package com.example.asus.lab3;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Random;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.OvershootInRightAnimator;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private GoodsAdapter goodsAdapter;
    private ShopCartAdapter shopCartAdapter;
    private ListView listView;
    private FloatingActionButton floatingActionButton;

    DynamticReceiver dynamicReceiver = new DynamticReceiver();
    String DYNAMICACTION = "dynamic_action";
    private ArrayList<Goods> shopcartlist = new ArrayList<>();
    private ArrayList<Goods> goodslist = new ArrayList<>();

    final String[] name = new String[]{"Enchated Forest", "Arla Milk", "Devondale Milk", "Kindle Oasis", "waitrose 早餐麦片", "Mcvitie's 饼干", "Ferrero Rocher", "Maltesers", "Lindt", "Borggreve"};
    final String[] price = new String[]{"¥ 5.00", "¥ 59.00", "¥ 79.00", "¥ 2399.00", "¥ 179.00", "¥ 14.00", "¥ 132.59", "¥ 141.43", "139.43", "28.90"};
    final String[] message = new String[]{"作者 Johanna Basford", "产地 德国", "产地 澳大利亚", "版本 8GB", "重量 2Kg", "产地 英国", "重量 300g", "重量 118g", "重量 249g", "重量 640g"};

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.goodslist);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        EventBus.getDefault().register(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DYNAMICACTION);
        registerReceiver(dynamicReceiver, intentFilter);

        for(int i = 0; i < name.length; i++){
            goodslist.add(new Goods(name[i], price[i], message[i]));
        }
        goodsAdapter = new GoodsAdapter(goodslist);
        goodsAdapter.setOnItemClickListener(new GoodsAdapter.OnItemClickListener(){
            @Override
            public void onClick(int position){
                Intent intent = new Intent(MainActivity.this, Detail.class);
                intent.putExtra("name", goodslist.get(position).getName())
                      .putExtra("price", goodslist.get(position).getPrice())
                      .putExtra("message", goodslist.get(position).getMessage());
                startActivityForResult(intent,0);
            }
            @Override
            public void onLongClick(int position){
                Toast.makeText(MainActivity.this,"移除第"+String.valueOf(position+1)+"个商品",Toast.LENGTH_SHORT).show();
                goodsAdapter.remove(position);
                goodsAdapter.notifyDataSetChanged();
            }
        });
        ScaleInAnimationAdapter animatorAdapter = new ScaleInAnimationAdapter(goodsAdapter);
        animatorAdapter.setDuration(1000);
        recyclerView.setAdapter(animatorAdapter);
        recyclerView.setItemAnimator(new OvershootInRightAnimator());
        Random random = new Random();
        int position = random.nextInt(goodslist.size());
        Intent intent = new Intent("static_action");
        intent.putExtra("name", goodslist.get(position).getName())
              .putExtra("price", goodslist.get(position).getPrice())
              .putExtra("message",goodslist.get(position).getMessage())
              .putExtra("image", GoodsImage.getImage(goodslist.get(position).getName()));
        sendBroadcast(intent);

        shopcartlist.add(new Goods("购物车", "0", "价格"));
        listView = findViewById(R.id.shoppingcart);
        shopCartAdapter = new ShopCartAdapter(this, shopcartlist);
        listView.setVisibility(View.INVISIBLE);
        listView.setAdapter(shopCartAdapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l){
                if(i==0) return true;
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("移除商品")
                           .setMessage("从购物车移除" + shopcartlist.get(i).getName() + "?")
                           .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which){
                        if (shopcartlist.remove(i)!=null )
                            shopCartAdapter.notifyDataSetChanged();
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), R.string.cancel_choose, Toast.LENGTH_SHORT).show();
                    }
                }).show();
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView,View view, final int i,long l){
                if(i==0) return;
                Intent intent = new Intent(MainActivity.this, Detail.class);
                intent.putExtra("name", shopcartlist.get(i).getName())
                      .putExtra("price", shopcartlist.get(i).getPrice())
                      .putExtra("message", shopcartlist.get(i).getMessage());
                startActivityForResult(intent,0);
            }
        });

        floatingActionButton = findViewById(R.id.floutingbutton);
        floatingActionButton.setTag("0");
        floatingActionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (floatingActionButton.getTag() == "0"){
                    floatingActionButton.setImageResource(R.mipmap.mainpage);
                    floatingActionButton.setTag("1");
                    listView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.INVISIBLE);
                } else {
                    floatingActionButton.setImageResource(R.mipmap.shoplist);
                    floatingActionButton.setTag("0");
                    listView.setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        listView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        floatingActionButton.setImageResource(R.mipmap.mainpage);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
  public void onMessageEvent(MessageEvent messageEvent){
        shopcartlist.add(new Goods(messageEvent.getName(),messageEvent.getPrice(),messageEvent.getMessage()));
        shopCartAdapter.notifyDataSetChanged();
    }

    @Override
   protected void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

