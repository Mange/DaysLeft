package se.hehu.test;

import se.hehu.SimpleDate;
import se.hehu.WidgetConfiguration;
import android.content.Context;
import android.content.SharedPreferences;
import android.test.AndroidTestCase;

public class WidgetConfigurationTest extends AndroidTestCase {
    WidgetConfiguration config;
    final int widgetId = 1, otherWidgetId = 2;

    protected void setUp() throws Exception {
        super.setUp();
        resetStoredPreferences();
        config = openConfig();
    }

    protected WidgetConfiguration openConfig() {
        return openConfig(widgetId);
    }

    protected WidgetConfiguration openConfig(int widgetId) {
        return new WidgetConfiguration(getContext(), widgetId);
    }

    private void resetStoredPreferences() {
        SharedPreferences.Editor editor = getContext().getSharedPreferences(
                WidgetConfiguration.PREF_NAME, Context.MODE_PRIVATE).edit();
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
}
