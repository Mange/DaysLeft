package se.hehu;

import java.util.Calendar;

public class SimpleDate {
    private float MILLISECONDS_PER_DAY = 1000 * 60 * 60 * 24;
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

    public int getDaysLeft() {
        Calendar target = getTargetCalendar();
        Calendar now = Calendar.getInstance();

        /*
         * We'll work using millisecond deltas. Since we're only after whole
         * days, the inaccuracies that will show up due to daylight savings
         * time, etc., will be lost due to rounding.
         */
        if (target.after(now)) {
            long delta = (target.getTimeInMillis() - now.getTimeInMillis());
            return Math.round(delta / MILLISECONDS_PER_DAY);
        } else {
            return 0;
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + day;
        result = prime * result + month;
        result = prime * result + year;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        SimpleDate other = (SimpleDate) obj;
        return (day == other.day && month == other.month && year == other.year);
    }

    public String toString() {
        return String.format("%d-%02d-%02d", year, month, day);
    }

    private Calendar getTargetCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        return calendar;
    }
}
