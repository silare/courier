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
	/**
	 * Sends the SMS message when the SMSSendReceiver receives an Intent
	 * broadcast, at the date and time specified by the user.
	 *
	 * @param context The Content in which the receiver is running.
	 * @param intent  The Intent being received.
	 */
	@Override
	public void onReceive(Context context, Intent intent)
	{
		// Put information in intent.
		Calendar time = (Calendar) intent.getSerializableExtra("time");
		String phoneNumber = intent.getStringExtra("phoneNumber");
		String message = intent.getStringExtra("message");

		// Notify the user that the SMS message was sent at the correct time.
		Toast.makeText(
			context,
			"Sending SMS message... " + "[" + DatePickerFragment.calendarDateString(
				time)
				+ " :: " + TimePickerFragment.calendarTimeString(time) + " ("
				+ time.get(Calendar.SECOND) + ")]" + " " + phoneNumber
				+ " << \"" + message + "\"", Toast.LENGTH_SHORT).show();
		Log.d("SENDING MESSAGE", "[" + DatePickerFragment.calendarDateString(
			time) + " :: "
			+ TimePickerFragment.calendarTimeString(time) + " (" + time.get(
			Calendar.SECOND)
			+ ")]" + " " + phoneNumber + " << \"" + message + "\"");

		// Send the SMS message.
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(phoneNumber, null, message, null, null);
	}

}
