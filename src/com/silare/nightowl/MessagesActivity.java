package com.silare.nightowl;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;

import java.util.Calendar;

import static android.provider.ContactsContract.CommonDataKinds.Phone;

public class MessagesActivity extends Activity
{
	private AutoCompleteTextView phoneNumber;
	private TextView dateSpinner;
	private TextView timeSpinner;
	private EditText message;
	private Button sendSMS;
	private Calendar sendTime;

	/**
	 * Initializes the activity and its user interface when it starts.
	 *
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_messages);

		// Initialize sendTime to the previous send time selected by the user,
		// or if none exists, initialize sendTime to the current time.
		if ((savedInstanceState != null
			&& savedInstanceState.containsKey("sendTime")))
		{
			sendTime = (Calendar) savedInstanceState.get("sendTime");
		}
		else
		{
			sendTime = Calendar.getInstance();
		}

		// Set up autocomplete functionality for the phone number field.
		initializePhoneNumber();

		// Initialize the message body field and the button for sending the
		// delayed SMS message.
		message = (EditText) findViewById(R.id.message);
		sendSMS = (Button) findViewById(R.id.send_sms);
		sendSMS.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				sendSMS(phoneNumber.getText().toString(), message.getText()
					.toString());
			}
		});

		// Initialize the date spinner and time spinner.
		initializeDateSpinner();
		initializeTimeSpinner();
	}

	/**
	 * Initializes the phone number field to support autocomplete by contact
	 * names. Clicking on a contact name inputs their phone number.
	 */
	private void initializePhoneNumber()
	{
		phoneNumber = (AutoCompleteTextView) findViewById(R.id.phone_number);

		// Set up the contact names so that clicking on them automatically
		// inputs their phone number.
		phoneNumber.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
									int i, long l)
			{
				Cursor cursor = (Cursor)
					adapterView.getItemAtPosition(i);
				String number = cursor.getString(
					cursor.getColumnIndexOrThrow(Phone.NUMBER));
				phoneNumber.setText(number);
			}
		});

		// Set up a Cursor that will find all phone numbers stored in the
		// user's contact list and obtain their display names, phone numbers,
		// and phone number types.
		CursorLoader loader = new CursorLoader(this,
			Phone.CONTENT_URI,
			new String[]{Phone._ID, Phone.DISPLAY_NAME, Phone.NUMBER,
				Phone.TYPE},
			null, null, Phone.DISPLAY_NAME + " ASC");
		Cursor phones = loader.loadInBackground();
		CursorAdapter adapter = new

			SimpleCursorAdapter(this,
				R.layout.view_contact, phones,
				new String[]{Phone.DISPLAY_NAME, Phone.NUMBER, Phone.TYPE},
				new int[]{R.id.phone_contact, R.id.phone_number, R.id.phone_type},
				0)
			{
				public void bindView(View view, Context context, Cursor cursor)
				{
					// Get the cursor indices.
					int nameIndex = cursor.getColumnIndexOrThrow(
						Phone.DISPLAY_NAME);
					int typeIndex = cursor.getColumnIndex(
						Phone.TYPE);
					int numberIndex = cursor.getColumnIndex(
						Phone.NUMBER);

					// Set up the cursor values.
					String contactName = cursor.getString(nameIndex);
					int numberType = cursor.getInt(typeIndex);
					String phoneNumber = cursor.getString(numberIndex);

					// Set up the TextViews to have their display name,
					// phone number, and phone number type.
					TextView name = (TextView) view.findViewById(
						R.id.phone_contact);
					TextView number = (TextView) view.findViewById(
						R.id.phone_number);
					TextView type = (TextView) view.findViewById(
						R.id.phone_type);

					name.setText(contactName);
					String typeStr = "";

					if (numberType == Phone.TYPE_HOME)
					{
						typeStr = "Home";
					}
					else if (numberType == Phone.TYPE_MOBILE)
					{
						typeStr = "Mobile";
					}
					else if (numberType == Phone.TYPE_WORK)
					{
						typeStr = "Work";
					}
					else if (numberType == Phone.TYPE_FAX_HOME)
					{
						typeStr = "Home Fax";
					}
					else if (numberType == Phone.TYPE_FAX_WORK)
					{
						typeStr = "Work Fax";
					}
					else if (numberType == Phone.TYPE_MAIN)
					{
						typeStr = "Main";
					}
					else if (numberType == Phone.TYPE_OTHER)
					{
						typeStr = "Other";
					}
					else if (numberType == Phone.TYPE_PAGER)
					{
						typeStr = "Pager";
					}
					else
					{
						typeStr = "Other";
					}
					type.setText(typeStr.toUpperCase());
					number.setText(phoneNumber);
				}
			};

		adapter.setFilterQueryProvider(new FilterQueryProvider()
		{
			public Cursor runQuery(CharSequence constraint)
			{
				return getContentResolver().query(Phone.CONTENT_URI,
					new String[]{Phone._ID, Phone.DISPLAY_NAME,
						Phone.NUMBER, Phone.TYPE},
					Phone.DISPLAY_NAME + " LIKE '" + constraint + "%'",
					null, null);
			}
		});
		phoneNumber.setAdapter(adapter);
	}

	/**
	 * Initializes the date spinner to call a DatePickerDialog on touch.
	 */
	private void initializeDateSpinner()
	{
		dateSpinner = (TextView) findViewById(R.id.date_spinner);
		dateSpinner.setText(DatePickerFragment.calendarDateString(sendTime));

		View.OnTouchListener dateSpinnerOnTouch = new View.OnTouchListener()
		{
			public boolean onTouch(View v, MotionEvent event)
			{
				if (event.getAction() == MotionEvent.ACTION_UP)
				{
					showDatePickerDialog();
				}
				return true;
			}
		};
		View.OnKeyListener dateSpinnerOnKey = new

			View.OnKeyListener()
			{
				public boolean onKey(View v, int keyCode, KeyEvent event)
				{
					if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER)
					{
						showDatePickerDialog();
						return true;
					}
					else
					{
						return false;
					}
				}
			};

		dateSpinner.setOnTouchListener(dateSpinnerOnTouch);
		dateSpinner.setOnKeyListener(dateSpinnerOnKey);
	}

	/**
	 * Initializes the time spinner to call a TimePickerDialog on touch.
	 */
	private void initializeTimeSpinner()
	{
		timeSpinner = (TextView) findViewById(R.id.time_spinner);
		timeSpinner.setText(TimePickerFragment.calendarTimeString(sendTime));

		View.OnTouchListener timeSpinnerOnTouch = new View.OnTouchListener()
		{
			public boolean onTouch(View v, MotionEvent event)
			{
				if (event.getAction() == MotionEvent.ACTION_UP)
				{
					showTimePickerDialog();
				}
				return true;
			}
		};
		View.OnKeyListener timeSpinnerOnKey = new

			View.OnKeyListener()
			{
				public boolean onKey(View v, int keyCode, KeyEvent event)
				{
					if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER)
					{
						showTimePickerDialog();
						return true;
					}
					else
					{
						return false;
					}
				}
			};

		timeSpinner.setOnTouchListener(timeSpinnerOnTouch);
		timeSpinner.setOnKeyListener(timeSpinnerOnKey);
	}

	/**
	 * Initializes the contents of the Activity's standard options menu.
	 *
	 * @param menu The options menu in which you place your items.
	 * @return Return true for the menu to be displayed.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.messages, menu);
		return true;
	}

	/**
	 * Called to retrieve the send time from an activity before being killed
	 * so that the send time can be restored in onCreate(Bundle) or killed so
	 * that the state can be restored in onCreate(Bundle) or
	 * onRestoreInstanceState(Bundle).
	 *
	 * @param outState Bundle in which to place your saved state.
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		outState.putSerializable("sendTime", sendTime);
		super.onSaveInstanceState(outState);
	}

	public void sendSMS(String phoneNumber, String message)
	{
		AlarmManager alarmMgr = (AlarmManager) getSystemService(
			Context.ALARM_SERVICE);
		Calendar time = sendTime;
		Intent intent = new Intent(null, Uri.parse("timer:" + time
			+ phoneNumber + message), this, SMSSendReceiver.class);

		// Put information in intent.
		intent.putExtra("time", time);
		intent.putExtra("phoneNumber", phoneNumber);
		intent.putExtra("message", message);

		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
			intent, Intent.FLAG_ACTIVITY_NEW_TASK);
		alarmMgr.set(AlarmManager.RTC_WAKEUP, time.getTimeInMillis(),
			pendingIntent);
		Log.d("SendSMS",
			"[" + DatePickerFragment.calendarDateString(time) + " :: "
				+ TimePickerFragment.calendarTimeString(time) + " ("
				+ time.get(Calendar.SECOND) + ")]" + " " + phoneNumber
				+ " << \"" + message + "\"");
	}

	/**
	 * Calls a DatePickerDialog to prompt the user for a new date to send the
	 * SMS message.
	 */
	public void showDatePickerDialog()
	{
		DatePickerFragment newFragment = new DatePickerFragment();
		newFragment.setMessagesActivity(this);
		newFragment.setCalendar(sendTime);
		newFragment.show(getFragmentManager(), "Date");
	}

	/**
	 * Calls a TimePickerDialog to prompt the user for a new time to send the
	 * SMS message.
	 */
	public void showTimePickerDialog()
	{
		TimePickerFragment newFragment = new TimePickerFragment();
		newFragment.setMessagesActivity(this);
		newFragment.setCalendar(sendTime);
		newFragment.show(getFragmentManager(), "Time");
	}

	/**
	 * Sets the send time for the SMS message to the new one, based on user
	 * input for the DatePickerDialog / TimePickerDialog. Updates the text
	 * boxes displaying the send date and send time.
	 *
	 * @param sendTime The date and time to send the SMS message at,
	 *                 based on user input from the DatePickerDialog or
	 *                 TimePickerDialog.
	 */
	public void setSendTime(Calendar sendTime)
	{
		this.sendTime = sendTime;
		dateSpinner.setText(DatePickerFragment.calendarDateString(sendTime));
		timeSpinner.setText(TimePickerFragment.calendarTimeString(sendTime));
	}
}