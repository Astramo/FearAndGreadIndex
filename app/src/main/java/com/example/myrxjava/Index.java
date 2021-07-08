package com.example.myrxjava;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.myrxjava.api.ApiClass;
import com.example.myrxjava.api.FAGResponse;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Implementation of App Widget functionality.
 */
public class Index extends AppWidgetProvider {
    public static String ACTION_AUTO_UPDATE_WIDGET = "ACTION_AUTO_UPDATE_WIDGET";
    private static final String TAG = "Index";


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.index);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    public static void getData(Context context, AppWidgetManager appWidgetManager,
                               int appWidgetId){

        ApiClass apiClass = new ApiClass();
        Single<FAGResponse> call = apiClass.getFag();
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<FAGResponse>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NotNull FAGResponse response) {
                        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.index);
                        views.setTextViewText(R.id.appwidget_text, response.getData().get(0).getValue());
                        views.setTextViewText(R.id.appwidget_title, response.getData().get(0).getValueClassification());
                        appWidgetManager.updateAppWidget(appWidgetId,views);
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {

                    }
                });

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            getData(context,appWidgetManager,appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        Intent intent = new Intent(Index.ACTION_AUTO_UPDATE_WIDGET);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 4);
        c.set(Calendar.MILLISECOND, 0);

        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmMgr.setInexactRepeating(AlarmManager.RTC, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);
    }

    @Override
    public void onDisabled(Context context) {
        Intent intent = new Intent(Index.ACTION_AUTO_UPDATE_WIDGET);
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmMgr.cancel(PendingIntent.getBroadcast(context, 0, intent, 0));
        Log.e(TAG, "onDisabled: stop");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.i(TAG, "onReceive: "+ACTION_AUTO_UPDATE_WIDGET);

        if (ACTION_AUTO_UPDATE_WIDGET.equals(intent.getAction())) {
            // do something useful here
            Toast.makeText(context, ACTION_AUTO_UPDATE_WIDGET, Toast.LENGTH_LONG).show();
        }
    }
}