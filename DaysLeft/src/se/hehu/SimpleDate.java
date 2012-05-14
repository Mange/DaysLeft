package se.hehu;

public class SimpleDate {
	private int year;
	private int month;
	private int day;

	public SimpleDate(int year, int month, int day) {
		this.setYear(year);
		this.setMonth(month);
		this.setDay(day);
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}
}
