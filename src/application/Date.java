package application;

/**
 * Date class, gets date for account openings
 * 
 * @author Christian Rodriguez, Yazhini Shanmugam
 *
 */
public class Date implements Comparable<Date> {

	private int year;
	private int month;
	private int day;

	/**
	 * Default constructor for Date, set to 1/1/2000
	 */
	public Date() {
		this.year = 2000;
		this.month = 1;
		this.day = 1;
	}

	/**
	 * Constructor for date, take a month, day, and year
	 * 
	 * @param month the month of the date
	 * @param day   the day of the date
	 * @param year  the year of the date
	 */
	public Date(int month, int day, int year) {
		this.year = year;
		this.month = month;
		this.day = day;
	}

	/**
	 * Overridden compareTo, takes in a date and ensures it is the actual date it is
	 * supposed to be
	 * 
	 * @param date to be compared to
	 * @return integer 0 if it is correct, 1 if it is incorrect, and -1 if it does
	 *         not exist
	 */
	@Override
	public int compareTo(Date date) {
		// return 0, 1, or -1
		if (year == date.year){
            if (month == date.month){
                if (day == date.day){
                    return 0;
                }
                else if (day > date.day)
                    return 1;
            } else if (month > date.month)
                return 1;
        } else if (year > date.year)
            return 1;

        return -1;

	}

	/**
	 * Converts date to string, adds 0's to month and day where
	 * 
	 * @return String returns the date to string format
	 */
	public String toString() {
		StringBuilder dayToString = new StringBuilder(Integer.toString(this.day));
		StringBuilder monthToString = new StringBuilder(Integer.toString(this.month));

		if (this.day < 10) {
			dayToString.insert(0, 0);
		}

		if (this.month < 10) {
			monthToString.insert(0, 0);
		}

		return monthToString.toString() + "/" + dayToString.toString() + "/" + Integer.toString(this.year);
	}

	/**
	 * Checks whether a date is valid
	 * 
	 * @return boolean true if the date is valid, false if it is not
	 */
	public boolean isValid() {
		if (this.month <= 0 || this.month > 12 || this.day <= 0 || this.day > 31 || this.year < 2000
				|| this.year > 2100) {
			return false;
		}

		boolean isLeapYear = false;
		int[] maxDayOfMonth = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

		if (this.year % 4 == 0) {
			isLeapYear = true;
			maxDayOfMonth[1] = 29;
		}

		if (this.day <= maxDayOfMonth[this.month - 1]) {
			return true;
		}

		return false;
	}

	/*
	 * public static void main(String[] args) {
	 * System.out.println("Testbed main for Date"); Date test1 = new Date(1, 1,
	 * 2000); System.out.println(test1.toString());
	 * System.out.println(test1.isValid());
	 * 
	 * Date test2 = new Date(10, 1, 1997); System.out.println(test2.toString());
	 * System.out.println(test2.isValid());
	 * 
	 * Date test3 = new Date(5, 15, 2014); System.out.println(test3.toString());
	 * System.out.println(test3.isValid());
	 * 
	 * Date test4 = new Date(12, 24, 2020); System.out.println(test4.toString());
	 * System.out.println(test4.isValid());
	 * 
	 * }
	 */

}
