package se.hehu.test;

import se.hehu.WidgetConfiguration;
import android.content.Context;
import android.content.SharedPreferences;
import android.test.AndroidTestCase;

public class WidgetConfigurationTest extends AndroidTestCase {
	WidgetConfiguration config;
	final int widgetId = 1;

	protected void setUp() throws Exception {
		super.setUp();
		resetStoredPreferences();
		config = new WidgetConfiguration(getContext(), widgetId);
	}

	private void resetStoredPreferences() {
		SharedPreferences.Editor editor = getContext().getSharedPreferences(
				WidgetConfiguration.PREF_NAME, Context.MODE_PRIVATE).edit();
		editor.clear();
		editor.commit();
	}

	public void testSavingTitle() {
		String newTitle = "The new title";
		assertNotSame(newTitle, config.getTitle());
		config.setTitle(newTitle);
		assertSame(newTitle, config.getTitle());
	}
}
