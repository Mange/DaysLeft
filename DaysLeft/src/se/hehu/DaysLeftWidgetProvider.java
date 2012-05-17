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
	
	public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
		WidgetConfiguration config = getConfig(context, appWidgetId);
		
		SimpleDate eventDate = config.getSimpleDate();
		if (eventDate != null) {
			views.setTextViewText(R.id.eventDate, eventDate.toString());
			views.setTextViewText(R.id.daysLeft, Integer.toString(eventDate.getDaysLeft()));
		} else {
			views.setTextViewText(R.id.eventDate, "");
			views.setTextViewText(R.id.daysLeft, "");			
		}
		views.setTextViewText(R.id.eventTitle, config.getTitle());
		
		appWidgetManager.updateAppWidget(appWidgetId, views);
	}
	
	private static WidgetConfiguration getConfig(Context context, int widgetId) {
		return new WidgetConfiguration(context, widgetId);
	}
}
