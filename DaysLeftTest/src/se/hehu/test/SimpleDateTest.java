package se.hehu.test;

import java.util.Calendar;

import se.hehu.SimpleDate;
import android.test.AndroidTestCase;

public class SimpleDateTest extends AndroidTestCase {
	private SimpleDate simpleDateFromToday(int days) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, days);
		return simpleDateFromCalendar(cal);
	}
	
	private SimpleDate simpleDateFromCalendar(Calendar cal) {
		return new SimpleDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
	}
	
	public void testConstructor() {
		SimpleDate date = new SimpleDate(2001, 10, 5);
		assertEquals(2001, date.getYear());
		assertEquals(10, date.getMonth());
		assertEquals(5, date.getDay());
	}
	
	public void testComparingDates() {
		SimpleDate a, b, c;
		a = new SimpleDate(2000, 1, 1);
		b = new SimpleDate(2001, 2, 3);
		c = new SimpleDate(2001, 2, 3);
		
		assertEquals(a, a);
		assertEquals(b, c);
		
		assertFalse(a.equals(b));
		assertFalse(c.equals(a));
		assertFalse(a.equals("2000-01-01"));
		assertFalse(a.equals(null));
	}
	
	public void testToString() {
		SimpleDate date = new SimpleDate(2000, 1, 2);
		assertEquals("2000-01-02", date.toString());
	}
	
	public void testDaysLeftOnTheDate() {
		SimpleDate date = simpleDateFromToday(0);
		assertEquals(date.toString(), 0, date.getDaysLeft());
	}

	public void testDaysLeftAWeekAgo() {
		SimpleDate date = simpleDateFromToday(-7);
		assertEquals(date.toString(), 0, date.getDaysLeft());
	}
	
	public void testDaysLeftAWeekFromNow() {
		SimpleDate date = simpleDateFromToday(7);
		assertEquals(date.toString(), 7, date.getDaysLeft());
	}

	public void testDaysLeftInTheFarFuture() {
		SimpleDate date = simpleDateFromToday(1000);
		assertEquals(date.toString(), 1000, date.getDaysLeft());
	}
}
