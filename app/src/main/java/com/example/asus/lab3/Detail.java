package com.example.asus.lab3;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class Detail extends AppCompatActivity{
    private DynamticReceiver dynamicReceiver = new DynamticReceiver();
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_view);

        Intent intent = getIntent();
        final String goodsname = intent.getStringExtra("name");
        final String goodsprice = intent.getStringExtra("price");
        final String goodsmessage = intent.getStringExtra("message");

        RecyclerView mrecyclerView = findViewById(R.id.recycler_view_1);
        mrecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(this);
        mrecyclerView.setLayoutManager(mlayoutManager);
        DetailAdapter mAdapter = new DetailAdapter(goodsname, goodsprice, goodsmessage, this, this);
        mrecyclerView.setAdapter(mAdapter);
        IntentFilter intentFilter=new IntentFilter();
        String DYNAMICACTION = "dynamic_action";
        intentFilter.addAction(DYNAMICACTION);
        registerReceiver(dynamicReceiver,intentFilter);

        ListView listView = findViewById(R.id.list_1);
        final String[] more= new String[]{"一键下单","分享商品","不感兴趣","查看更多商品促销信息"};
        ArrayList<String> arrayList = new ArrayList<>();
        Collections.addAll(arrayList, more);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(dynamicReceiver);
    }
}
