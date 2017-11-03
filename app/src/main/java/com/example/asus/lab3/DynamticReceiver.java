package com.example.asus.lab3;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;
import android.widget.Toast;

public class DynamticReceiver extends BroadcastReceiver{
    private static final String DYNAMICACTION = "dynamic_action";
    @Override
    public void onReceive(Context context, Intent intent){
        if(intent.getAction().equals(DYNAMICACTION)){
            Bundle bundle = intent.getExtras();
            Toast.makeText(context, bundle.getString("name"), Toast.LENGTH_LONG).show();
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), GoodsImage.getImage(bundle.getString("name")));
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setContentTitle(context.getResources().getString(R.string.take_order))
                   .setContentText(bundle.getString("name")+context.getResources().getString(R.string.put_into_shopcart))
                   .setTicker(context.getResources().getString(R.string.new_message))
                   .setLargeIcon(bitmap)
                   .setSmallIcon(R.mipmap.shopcart)
                   .setColor(Color.LTGRAY)
                   .setAutoCancel(true);
            intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_ONE_SHOT);
            builder.setContentIntent(pendingIntent);
            NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notify=builder.build();
            notificationManager.notify(0,notify);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.shop_cart_widget);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            views.setTextViewText(R.id.appwidget_text, bundle.getString("name")+context.getResources().getString(R.string.put_into_shopcart));
            views.setImageViewResource(R.id.appwidget_image,GoodsImage.getImage(bundle.getString("name")));
            PendingIntent pendingIntent1 = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.appwidget, pendingIntent1);
            ComponentName componentName1 = new ComponentName(context, ShopCartWidget.class);
            appWidgetManager.updateAppWidget(componentName1,views);
        }
    }
}
