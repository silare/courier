package com.silare.nightowl;

import java.util.Calendar;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

public class TimePickerFragment extends DialogFragment implements
		TimePickerDialog.OnTimeSetListener
{
	private Calendar calendar;
	private MessagesActivity activity;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		// Use the current date as the default date in the picker
		final Calendar c = (calendar != null) ? calendar : Calendar
				.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		
		// Create a new instance of DatePickerDialog and return it
		// return new TimePickerDialog(getActivity(), this, year, month, day);
		return new TimePickerDialog(getActivity(), this, hour, minute,
				DateFormat.is24HourFormat(getActivity()));
	}
	
	
	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute)
	{
		// TODO Auto-generated method stub
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
}
