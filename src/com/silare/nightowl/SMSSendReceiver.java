package com.silare.nightowl;

import java.util.Calendar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class SMSSendReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent)
	{
		// Put information in intent.
		Calendar time = (Calendar) intent.getSerializableExtra("time");
		String phoneNumber = intent.getStringExtra("phoneNumber");
		String message = intent.getStringExtra("message");
		
		Toast.makeText(
				context,
				"Sending SMS message... " + "[" + calendarDateString(time)
						+ " :: " + calendarTimeString(time)
						+ time.get(Calendar.SECOND) + "]" + " " + phoneNumber
						+ " << \"" + message + "\"", Toast.LENGTH_SHORT).show();
		Log.d("SENDING MESSAGE", "[" + calendarDateString(time) + " :: "
				+ calendarTimeString(time) + time.get(Calendar.SECOND) + "]"
				+ " " + phoneNumber + " << \"" + message + "\"");
		
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(phoneNumber, null, message, null, null);
	}
	
	
	public static String calendarDateString(Calendar calendar)
	{
		final String[] MONTHS = { "Jan", "Feb", "Mar", "Apr", "May", "Jun",
				"Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
		final String[] DAYS_OF_WEEK = { "Sun", "Mon", "Tue", "Wed", "Thu",
				"Fri", "Sat" };
		
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		return DAYS_OF_WEEK[dayOfWeek] + ", " + MONTHS[month] + " " + day
				+ ", " + year;
	}
	
	
	public static String calendarTimeString(Calendar calendar)
	{
		int hour = calendar.get(Calendar.HOUR);
		int minute = calendar.get(Calendar.MINUTE);
		String meridiem = (calendar.get(Calendar.HOUR_OF_DAY) < 12) ? "am"
				: "pm";
		return hour + ":" + minute + meridiem;
	}
}
