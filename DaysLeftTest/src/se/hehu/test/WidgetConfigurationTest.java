package se.hehu.test;

import se.hehu.SimpleDate;
import se.hehu.WidgetConfiguration;
import android.content.Context;
import android.content.SharedPreferences;
import android.test.AndroidTestCase;

public class WidgetConfigurationTest extends AndroidTestCase {
    private class ScopedWidgetConfiguration extends WidgetConfiguration {
        public static final String PREF_NAME = "se.hehu.test.DaysLeft";

        public ScopedWidgetConfiguration(Context context, int widgetId) {
            super(context, widgetId);
        }

        protected SharedPreferences getPrefs() {
            return context
                    .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        }
    }

    ScopedWidgetConfiguration config;
    final int widgetId = 1, otherWidgetId = 2;

    protected void setUp() throws Exception {
        super.setUp();
        resetStoredPreferences();
        config = openConfig();
    }

    protected ScopedWidgetConfiguration openConfig() {
        return openConfig(widgetId);
    }

    protected ScopedWidgetConfiguration openConfig(int widgetId) {
        return new ScopedWidgetConfiguration(getContext(), widgetId);
    }

    private void resetStoredPreferences() {
        SharedPreferences prefs = getContext().getSharedPreferences(
                ScopedWidgetConfiguration.PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.clear();
        editor.commit();
    }

    public void testSettingsBeingNilWhenUnset() {
        assertNull(config.getTitle());
        assertNull(config.getSimpleDate());
    }

    public void testSavingTitle() {
        String newTitle = "The new title";
        assertFalse(newTitle.equals(config.getTitle()));
        config.setTitle(newTitle);
        assertEquals(newTitle, config.getTitle());
        assertEquals(newTitle, openConfig().getTitle());

        assertFalse(newTitle.equals(openConfig(otherWidgetId).getTitle()));
    }

    public void testSavingSimpleDate() {
        SimpleDate date = new SimpleDate(2000, 1, 2);
        assertNull("Should have no date by default", config.getSimpleDate());
        config.setSimpleDate(date);
        assertEquals(date, config.getSimpleDate());
        assertEquals(date, openConfig().getSimpleDate());

        assertFalse(date.equals(openConfig(otherWidgetId).getSimpleDate()));
    }

    public void testClearingConfig() {
        SimpleDate date = new SimpleDate(2000, 1, 2);
        String title = "Some title";

        ScopedWidgetConfiguration other = openConfig(otherWidgetId);

        config.setTitle(title);
        config.setSimpleDate(date);
        other.setTitle(title);
        other.setSimpleDate(date);

        other.clear();

        assertFalse("Title got cleared", title.equals(other.getTitle()));
        assertFalse("Date got cleared", date.equals(other.getSimpleDate()));

        assertEquals("Title of other config was not changed", title,
                config.getTitle());
        assertEquals("Date of other config was not changed", date,
                config.getSimpleDate());

    }
}
