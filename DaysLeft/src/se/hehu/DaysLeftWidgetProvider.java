package se.hehu;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

public class DaysLeftWidgetProvider extends AppWidgetProvider {
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
            int[] appWidgetIds) {
        WidgetRefresher refresher = new WidgetRefresher(context,
                appWidgetManager);
        int totalWidgets = appWidgetIds.length;
        for (int i = 0; i < totalWidgets; i++) {
            refresher.refresh(appWidgetIds[i]);
        }
    }

    public static void updateAppWidget(Context context,
            AppWidgetManager appWidgetManager, int widgetId) {
        new WidgetRefresher(context, appWidgetManager).refresh(widgetId);
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
            views.setTextViewText(R.id.eventTitle, config.getTitle());

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

        private WidgetConfiguration getConfig(int widgetId) {
            return new WidgetConfiguration(context, widgetId);
        }
    }
}
