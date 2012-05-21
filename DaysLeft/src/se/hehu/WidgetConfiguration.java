package se.hehu;

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
        return getPrefs().getString(getFullKeyName("title"), null);
    }

    public SimpleDate getSimpleDate() {
        int year, month, day;
        SharedPreferences prefs = getPrefs();
        year = prefs.getInt(getFullKeyName("date_year"), 0);
        month = prefs.getInt(getFullKeyName("date_month"), 0);
        day = prefs.getInt(getFullKeyName("date_day"), 0);
        if (year == 0 || month == 0 || day == 0)
            return null;
        else
            return new SimpleDate(year, month, day);
    }

    public void setTitle(String title) {
        SharedPreferences.Editor editor = getPrefsEditor();
        editor.putString(getFullKeyName("title"), title);
        editor.commit();
    }

    public void setSimpleDate(SimpleDate date) {
        SharedPreferences.Editor editor = getPrefsEditor();
        editor.putInt(getFullKeyName("date_year"), date.getYear());
        editor.putInt(getFullKeyName("date_month"), date.getMonth());
        editor.putInt(getFullKeyName("date_day"), date.getDay());
        editor.commit();
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
