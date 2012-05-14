package se.hehu;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

public class DaysLeftWidgetProvider extends AppWidgetProvider {
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		int totalWidgets = appWidgetIds.length;
		for (int i = 0; i < totalWidgets; i++) {
			updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
		}
	}
	
	private void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
		WidgetConfiguration config = getConfig(context, appWidgetId);
		
		views.setTextViewText(R.id.eventTitle, config.getTitle());
		views.setTextViewText(R.id.eventDate, config.getDate().toLocaleString());
		
		appWidgetManager.updateAppWidget(appWidgetId, views);
	}
	
	private WidgetConfiguration getConfig(Context context, int widgetId) {
		return new WidgetConfiguration(context, widgetId);
	}
}
