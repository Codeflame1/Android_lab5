package com.example.asus.lab3;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

public class ShopCartWidget extends AppWidgetProvider{
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId){
        CharSequence widgetText = context.getString(R.string.appwidget_text);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.shop_cart_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);
        Intent intent = new Intent(context,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
        for (int appWidgetId : appWidgetIds){
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context){
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context){
        super.onDisabled(context);
    }

    @Override
    public void onReceive(Context context, Intent intent){
        super.onReceive(context, intent);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.shop_cart_widget);
        if (intent.getAction().equals("static_action")){
            Bundle bundle = intent.getExtras();
            views.setTextViewText(R.id.appwidget_text, bundle.getString("name")+"仅售"+bundle.getString("price"));
            views.setImageViewResource(R.id.appwidget_image,GoodsImage.getImage(bundle.getString("name")));
            Intent intent1 = new Intent(context, Detail.class);
            intent1.addCategory(Intent.CATEGORY_LAUNCHER)
                   .putExtra("name", bundle.getString("name"))
                   .putExtra("price",bundle.getString("price"))
                   .putExtra("message",bundle.getString("message"));
            PendingIntent pendingIntent1 = PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.appwidget, pendingIntent1);
            ComponentName componentName1 = new ComponentName(context, ShopCartWidget.class);
            appWidgetManager.updateAppWidget(componentName1,views);
        }
    }
}

