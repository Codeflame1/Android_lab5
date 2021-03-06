package com.example.asus.lab3;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;


public class StaticReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals("static_action")){
            Bundle bundle = intent.getExtras();
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), GoodsImage.getImage(bundle.getString("name")));
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setContentTitle(context.getResources().getString(R.string.goods_for_sale))
                   .setContentText(bundle.getString("name")+"仅售"+bundle.getString("price"))
                   .setTicker(context.getResources().getString(R.string.new_message))
                   .setLargeIcon(bitmap)
                   .setSmallIcon(R.mipmap.shopcart)
                   .setColor(Color.alpha(R.color.gray_3))
                   .setWhen(System.currentTimeMillis())
                   .setAutoCancel(true);
            intent = new Intent(context, Detail.class);
            intent.putExtra("name", bundle.getString("name"))
                  .putExtra("price",bundle.getString("price"))
                  .putExtra("message",bundle.getString("message"));
            PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_ONE_SHOT);
            builder.setContentIntent(pendingIntent);
            NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notify=builder.build();
            manager.notify(0,notify);
        }
    }
}
