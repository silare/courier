package com.silare.nightowl;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.Calendar;

public class PendingMessage
	implements Parcelable
{
	private String contactName;
	private String phoneNumber;
	private String messageBody;
	private Calendar sendTime;
	// TODO Contact picture later on too?
	// TODO private boolean isEnabled; (for reminder-style messages)

	public static final Parcelable.Creator<PendingMessage> CREATOR
		= new Parcelable.Creator<PendingMessage>()
	{
		/**
		 * Create a new instance of the Parcelable class, instantiating it from
		 * the given Parcel whose data had previously been written by
		 * Parcelable.writeToParcel().
		 * @param in    The Parcel to read the object's data from.
		 * @return Return a new instance of the Parcelable class.
		 */
		@Override
		public PendingMessage createFromParcel(Parcel in)
		{
			return new PendingMessage(in);
		}


		/**
		 * Create a new array of the Parcelable class.
		 * @param size  The size of the array.
		 * @return Return an array of the Parcelable class, with every entry
		 *         initialized to null.
		 */
		@Override
		public PendingMessage[] newArray(int size)
		{
			return new PendingMessage[size];
		}
	};


	/**
	 * Creates a pending SMS message to a given name and phone number to be
	 * sent at a given time.
	 *
	 * @param contactName The name of the recipient.
	 * @param phoneNumber The phone number of the recipient.
	 * @param messageBody The SMS message body.
	 * @param sendTime    The time at which the message will be sent.
	 */
	public PendingMessage(String contactName, String phoneNumber,
	                      String messageBody, Calendar sendTime)
	{
		this.contactName = contactName;
		this.phoneNumber = phoneNumber;
		this.messageBody = messageBody;
		this.sendTime = sendTime;
	}


	/**
	 * Creates a pending SMS message based on the contents of a given Parcel.
	 *
	 * @param in The Parcel containing flattened values for each field.
	 */
	private PendingMessage(Parcel in)
	{
		contactName = in.readString();
		phoneNumber = in.readString();
		messageBody = in.readString();
		sendTime = (Calendar) in.readSerializable();
		Log.wtf("PendingMessage(Parcel in)", "PendingMessage(" + contactName +
			", " + phoneNumber + ", " + messageBody + ", " + sendTime + ")");
	}


	public String contactName()
	{
		return contactName;
	}


	public String phoneNumber()
	{
		return phoneNumber;
	}


	public String messageBody()
	{
		return messageBody;
	}


	public Calendar sendTime()
	{
		return sendTime;
	}


	/**
	 * Flatten this object in to a Parcel.
	 *
	 * @param out   The Parcel in which the object should be written.
	 * @param flags Additional flags about how the object should be written.
	 *              May be 0 or PARCELABLE_WRITE_RETURN_VALUE.
	 */
	@Override
	public void writeToParcel(Parcel out, int flags)
	{
		out.writeString(contactName);
		out.writeString(phoneNumber);
		out.writeString(messageBody);
		out.writeSerializable(sendTime);
	}


	/**
	 * Describe the kinds of special objects contained in this Parcelable's
	 * marshalled representation.
	 *
	 * @return A bitmask indicating the set of special object types
	 *         marshalled by the Parcelable.
	 */
	@Override
	public int describeContents()
	{
		return 0;
	}
}
