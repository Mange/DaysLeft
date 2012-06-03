package se.hehu.daysleft;

import java.util.Date;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class WidgetProvider extends AppWidgetProvider {
    private PendingIntent alarmIntent;
    private final long alarmInterval = AlarmManager.INTERVAL_HOUR;
    public final String REFRESH_ACTION = "DAYSLEFT_WIDGET_REFRESH";

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (REFRESH_ACTION.equals(intent.getAction())) {
            updateAllWidgets(context);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager widgetManager,
            int[] ids) {
        updateAllWidgets(context, widgetManager, ids);
        setupAlarm(context);
    }

    @Override
    public void onDisabled(Context context) {
        teardownAlarm(context);
    }

    public static void updateAppWidget(Context context,
            AppWidgetManager appWidgetManager, int widgetId) {
        new WidgetRefresher(context, appWidgetManager).refresh(widgetId);
    }

    private void updateAllWidgets(Context context) {
        AppWidgetManager widgetManager = AppWidgetManager.getInstance(context);

        final String className = WidgetProvider.class.getName();
        ComponentName componentName = new ComponentName(
                context.getPackageName(), className);

        final int[] widgetIds = widgetManager.getAppWidgetIds(componentName);
        updateAllWidgets(context, widgetManager, widgetIds);
    }

    private void updateAllWidgets(Context context,
            AppWidgetManager widgetManager, final int[] ids) {
        WidgetRefresher refresher = new WidgetRefresher(context, widgetManager);
        for (int i = 0; i < ids.length; i++) {
            refresher.refresh(ids[i]);
        }
    }

    private void setupAlarm(Context context) {
        if (alarmIntent == null) {
            final Intent intent = new Intent(REFRESH_ACTION);

            alarmIntent = PendingIntent.getBroadcast(context, 0, intent,
                    PendingIntent.FLAG_CANCEL_CURRENT);

            final AlarmManager alarms = getAlarmManager(context);
            alarms.setInexactRepeating(AlarmManager.RTC, new Date().getTime(),
                    alarmInterval, alarmIntent);

        }
    }

    private void teardownAlarm(Context context) {
        if (alarmIntent != null) {
            getAlarmManager(context).cancel(alarmIntent);
        }
    }

    private final AlarmManager getAlarmManager(Context context) {
        return (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    private static class WidgetRefresher {
        Context context;
        AppWidgetManager appWidgetManager;

        public WidgetRefresher(Context context,
                AppWidgetManager appWidgetManager) {
            this.context = context;
            this.appWidgetManager = appWidgetManager;
        }

        public void refresh(int widgetId) {
            RemoteViews views = new RemoteViews(context.getPackageName(),
                    R.layout.widget);
            WidgetConfiguration config = getConfig(widgetId);

            SimpleDate eventDate = config.getSimpleDate();
            if (eventDate != null) {
                updateTextWithDate(views, eventDate);
            } else {
                updateTextWithoutDate(views);
            }

            updateTitle(views, config.getTitle());
            setupClickIntent(views, widgetId);

            appWidgetManager.updateAppWidget(widgetId, views);
        }

        private void updateTextWithDate(RemoteViews views, SimpleDate date) {
            String daysLeft = Integer.toString(date.getDaysLeft());
            views.setTextViewText(R.id.eventDate, date.toString());
            views.setTextViewText(R.id.daysLeft, daysLeft);
        }

        private void updateTextWithoutDate(RemoteViews views) {
            views.setTextViewText(R.id.eventDate, "");
            views.setTextViewText(R.id.daysLeft, "");
        }

        private void updateTitle(RemoteViews views, String title) {
            if (title == null) {
                title = context.getString(R.string.no_title);
            }
            views.setTextViewText(R.id.eventTitle, title);
        }

        private WidgetConfiguration getConfig(int widgetId) {
            return new WidgetConfiguration(context, widgetId);
        }

        private void setupClickIntent(RemoteViews views, int widgetId) {
            Intent intent = new Intent(context, ConfigureActivity.class);

            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);

            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            PendingIntent pendingIntent = PendingIntent.getActivity(context,
                    widgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            views.setOnClickPendingIntent(R.id.eventTitle, pendingIntent);
        }
    }
}
