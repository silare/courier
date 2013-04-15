package com.silare.nightowl;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

public class DatePickerFragment extends DialogFragment implements
		DatePickerDialog.OnDateSetListener
{
	private Calendar calendar;
	private MessagesActivity activity;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		// Use the current date as the default date in the picker
		final Calendar c = (calendar != null) ? calendar : Calendar
				.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		
		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}
	
	
	@Override
	public void onDateSet(DatePicker view, int year, int month, int day)
	{
		// Do something with the date chosen by the user
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
}
