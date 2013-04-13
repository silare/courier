package com.silare.nightowl;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MessagesActivity extends Activity
{
	EditText phoneNumber;
	
	Button sendSMS;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_messages);
		
		phoneNumber = (EditText) findViewById(R.id.phone_number);
		
		sendSMS = (Button) findViewById(R.id.send_sms);
		sendSMS.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				sendSMS(phoneNumber.getText().toString(), "Moo!");
			}
		});
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.messages, menu);
		return true;
	}
	
	
	public void sendSMS(String phoneNumber, String message)
	{
		Log.d("SendSMS", "Sending " + phoneNumber + " the message \"" + message
				+ "\".");
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(phoneNumber, null, message, null, null);
	}
	
}
