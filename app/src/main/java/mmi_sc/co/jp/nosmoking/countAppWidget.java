package mmi_sc.co.jp.nosmoking;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class countAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.app_name);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.count_app_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);
        views.setOnClickPendingIntent(R.id.button_count, clickButton(context));

        Intent intent = new Intent(context, MainActivity.class);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setAction(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.button_status, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    public static PendingIntent clickButton(Context context) {
        // クリック回数を増加
        MyReceiver.clickCount ++;

        // initiate widget update request
        Intent intent = new Intent();
        intent.setAction("UPDATE_WIDGET");
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

}

