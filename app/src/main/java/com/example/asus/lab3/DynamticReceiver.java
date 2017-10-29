package com.example.asus.lab3;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class DynamticReceiver extends BroadcastReceiver{
    private static final String DYNAMICACTION = "dynamic_action";
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(DYNAMICACTION)){
            Bundle bundle = intent.getExtras();
            Toast.makeText(context, bundle.getString("name"), Toast.LENGTH_LONG).show();
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), GoodsImage.getImage(bundle.getString("name")));
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setContentTitle(context.getResources().getString(R.string.take_order))
                   .setContentText(bundle.getString("name")+context.getResources().getString(R.string.put_into_shopcart))
                   .setTicker(context.getResources().getString(R.string.new_message))
                   .setLargeIcon(bitmap)
                   .setSmallIcon(R.mipmap.shoplist)
                   .setAutoCancel(true);
            intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);
            NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notify=builder.build();
            manager.notify(0,notify);
        }
    }
}
