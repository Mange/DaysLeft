package se.hehu.test;

import se.hehu.SimpleDate;
import android.test.AndroidTestCase;

public class SimpleDateTest extends AndroidTestCase {
	public void testConstructor() {
		SimpleDate date = new SimpleDate(2001, 10, 5);
		assertEquals(2001, date.getYear());
		assertEquals(10, date.getMonth());
		assertEquals(5, date.getDay());
	}
}
