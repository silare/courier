package com.silare.nightowl;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements
	TimePickerDialog.OnTimeSetListener
{
	private Calendar calendar;
	private MessagesActivity activity;

	/**
	 * Creates a new time picker dialog.
	 *
	 * @param savedInstanceState The last saved instance state of the
	 *                           Fragment, or null if this is a freshly
	 *                           created Fragment.
	 * @return Return a new TimePickerDialog instance to
	 *         be displayed by the TimePickerFragment,
	 *         initialized to an hour and a minute.
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		// Use the current time as the default time in the picker if the user
		// has not already selected one.
		final Calendar c = (calendar != null) ? calendar : Calendar
			.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);

		// Create a new TimePickerDialog instance and return it.
		return new TimePickerDialog(getActivity(), this, hour, minute,
			DateFormat.is24HourFormat(getActivity()));
	}

	/**
	 * Updates the calling activity's time with the new time set.
	 *
	 * @param view      The view associated with this listener.
	 * @param hourOfDay The hour that was set.
	 * @param minute    The minute that was set.
	 */
	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute)
	{
		calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, 0);
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
	 * Returns a String representation of the given Calendar's time.
	 *
	 * @param calendar The Calendar to obtain a String representation of.
	 * @return Return a String representing the time of the given Calendar.
	 */
	public static String calendarTimeString(Calendar calendar)
	{
		String hour = (calendar.get(Calendar.HOUR) != 0) ? "" +
			calendar.get(Calendar.HOUR) : "12";
		int currentMinute = calendar.get(Calendar.MINUTE);
		String prefix = (currentMinute < 10) ? "0" : "";
		String minute = prefix + currentMinute;
		String meridiem = (calendar.get(Calendar.HOUR_OF_DAY) < 12) ? "am"
			: "pm";
		return hour + ":" + minute + meridiem;
	}
}
