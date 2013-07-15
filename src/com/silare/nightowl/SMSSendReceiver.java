package com.silare.nightowl;

import java.util.Calendar;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.widget.Toast;

public class SMSSendReceiver
	extends BroadcastReceiver
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
		PendingMessage pendingMessage = (PendingMessage)
			intent.getParcelableExtra("pendingMessage");
		Calendar time = pendingMessage.sendTime();
		String contactName = pendingMessage.contactName();
		String phoneNumber = pendingMessage.phoneNumber();
		String message = pendingMessage.messageBody();

		// Notify the user that the SMS message was sent at the correct time.
		Bitmap icNotifLarge = BitmapFactory.decodeResource(
			context.getResources(), R.drawable.ic_notif_large);
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
			context).setSmallIcon(R.drawable.ic_notif_small)
			.setLargeIcon(icNotifLarge)
			.setContentTitle("Sent to " + contactName)
			.setContentText(message)
			.setTicker("Sent to " + contactName + ": " + message);
		NotificationManager manager = (NotificationManager) context
			.getSystemService(Context.NOTIFICATION_SERVICE);
		manager.notify(1, builder.build());
		Toast.makeText(context, "Sent to " + contactName + ": " + message,
			Toast.LENGTH_LONG);

		// Send the SMS message.
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(phoneNumber, null, message, null, null);
	}

}
