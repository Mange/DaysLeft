package se.hehu;

import java.util.Date;

import android.content.Context;
import android.content.SharedPreferences;

public class WidgetConfiguration {
	private Context context;
	private int widgetId;
	
	public static final String PREF_NAME = "se.hehu.DaysLeft";
	private static final String PREF_PREFIX = "widget_";
	
	public WidgetConfiguration(Context context, int widgetId) {
		this.context = context;
		this.widgetId = widgetId;
	}
	
	public String getTitle() {
		return getPrefs().getString(getFullKeyName("title"), "No title");
	}
	
	public Date getDate() {
		// TODO: Implement
		return new Date();
	}
	
	public void setTitle(String title) {
		SharedPreferences.Editor editor = getPrefsEditor();
		editor.putString(getFullKeyName("title"), title);
		editor.commit();
	}
	
	public void setDate(Date date) {
		// TODO: Implement
	}
	
	private SharedPreferences getPrefs() {
		return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
	}
	
	private String getFullKeyName(String keyName) {
		return PREF_PREFIX + widgetId + "_" + keyName;
	}
	
	private SharedPreferences.Editor getPrefsEditor() {
		return getPrefs().edit();
	}
}
