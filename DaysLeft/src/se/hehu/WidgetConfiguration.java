package se.hehu;

import android.content.Context;
import android.content.SharedPreferences;

public class WidgetConfiguration {
    protected Context context;
    protected int widgetId;

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
        int year = 0, month = 0, day = 0;

        if (date != null) {
            year = date.getYear();
            month = date.getMonth();
            day = date.getDay();
        }

        editor.putInt(getFullKeyName("date_year"), year);
        editor.putInt(getFullKeyName("date_month"), month);
        editor.putInt(getFullKeyName("date_day"), day);

        editor.commit();
    }

    protected SharedPreferences getPrefs() {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    protected String getFullKeyName(String keyName) {
        return PREF_PREFIX + widgetId + "_" + keyName;
    }

    protected SharedPreferences.Editor getPrefsEditor() {
        return getPrefs().edit();
    }
}
