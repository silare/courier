package com.silare.nightowl;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements
	DatePickerDialog.OnDateSetListener
{
	private Calendar calendar;
	private MessagesActivity activity;

	/**
	 * Creates a new date picker dialog.
	 *
	 * @param savedInstanceState The last saved instance state of the
	 *                           Fragment, or null if this is a freshly
	 *                           created Fragment.
	 * @return Return a new DatePickerDialog instance to
	 *         be displayed by the DatePickerFragment,
	 *         initialized to a year, month, and day.
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		// Use the current date as the default date in the picker if the user
		// has not already selected one.
		final Calendar c = (calendar != null) ? calendar : Calendar
			.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		// Create a new DatePickerDialog instance and return it.
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}

	/**
	 * Updates the calling activity's date with the new date set.
	 *
	 * @param view  The view associated with this listener.
	 * @param year  The year that was set.
	 * @param month The month that was set (0-11) for compatibility with
	 *              Calendar.
	 * @param day   The day of the month that was set.
	 */
	@Override
	public void onDateSet(DatePicker view, int year, int month, int day)
	{
		calendar.set(year, month, day);
		activity.setSendTime(calendar);
	}

	public void setMessagesActivity(MessagesActivity activity)
	{
		this.activity = activity;
	}

	public void setCalendar(Calendar calendar)
	{
		this.calendar = calendar;
	}

	/**
	 * Returns a String representation of the given Calendar's date.
	 *
	 * @param calendar The Calendar to obtain a String representation of.
	 * @return Return a String representing the date of the given Calendar.
	 */
	public static String calendarDateString(Calendar calendar)
	{
		final String[] MONTHS = { "Jan", "Feb", "Mar", "Apr", "May", "Jun",
			"Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
		final String[] DAYS_OF_WEEK = { null, "Sun", "Mon", "Tue", "Wed",
			"Thu", "Fri", "Sat" };

		String year = "" + calendar.get(Calendar.YEAR);
		String month = MONTHS[calendar.get(Calendar.MONTH)];
		String day = "" + calendar.get(Calendar.DAY_OF_MONTH);
		String dayOfWeek = DAYS_OF_WEEK[calendar.get(Calendar.DAY_OF_WEEK)];
		return dayOfWeek + ", " + month + " " + day + ", " + year;
	}

}
