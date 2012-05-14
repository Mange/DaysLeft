package se.hehu.test;

import se.hehu.WidgetConfiguration;
import android.test.AndroidTestCase;

public class WidgetConfigurationTest extends AndroidTestCase {
	WidgetConfiguration config;
	final int widgetId = 1;
	
	protected void setUp() throws Exception {
		super.setUp();
		config = new WidgetConfiguration(getContext(), widgetId);
	}
	
	public void testSavingTitle() {
		String newTitle = "The new title";
		assertNotSame(newTitle, config.getTitle());
		config.setTitle(newTitle);
		assertSame(newTitle, config.getTitle());
	}
}
