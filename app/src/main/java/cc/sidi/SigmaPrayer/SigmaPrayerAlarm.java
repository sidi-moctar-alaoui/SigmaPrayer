//	-------------------------------------------------------------
//	SigmaPrayer for Android
//  Placed in the public domain:
//      It may be freely reproduced, distributed, transmitted, used, modified,
//      built upon, or otherwise exploited by anyone for any purpose, commercial or non-commercial,
//      and in any way
//  sidi.moctar.alaoui@gmail.com
//	-------------------------------------------------------------

package cc.sidi.SigmaPrayer;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;

@SuppressLint("DefaultLocale")
public final class SigmaPrayerAlarm
{
	public static final boolean START = true;
	public static final boolean DIFFER = false;

	private static final int m_arrMessage[] = { R.string.strTime1, R.string.strTime2, R.string.strTime3, R.string.strTime4, R.string.strTime5, R.string.strTime6, R.string.strTime7 };
	private static final int m_arrMessage_AR[] = { R.string.strTime1_AR, R.string.strTime2_AR, R.string.strTime3_AR, R.string.strTime4_AR, R.string.strTime5_AR, R.string.strTime6_AR, R.string.strTime7_AR };

	public static final int ALARM_DURATION_DEFAULT = 30;	// minutes
	public static final int ALARM_DURATION_MIN = 1;			// minutes

	private static Context m_Context = null;
	private static PendingIntent m_PendingIntent = null;
	private static NotificationManager m_AlarmNotification = null;

	// { Subh, Shuruq, Dhuhr, Asr, Ghurub, Maghrib, Isha }
	private static String m_arrTimeStr[] = { PrayerTimes.INVALID_TIME, PrayerTimes.INVALID_TIME, PrayerTimes.INVALID_TIME,
			PrayerTimes.INVALID_TIME, PrayerTimes.INVALID_TIME, PrayerTimes.INVALID_TIME, PrayerTimes.INVALID_TIME };
	private static int m_arrTime[] = { 0, 0, 0, 0, 0, 0, 0 };
	private static int m_arrTimeHour[] = { 0, 0, 0, 0, 0, 0, 0 };
	private static int m_arrTimeMinute[] = { 0, 0, 0, 0, 0, 0, 0 };
	private static int m_iInterval = 0;

	private static String m_strSelectedCity = "";
	private static int m_iCalcMethod = PrayerTimes.CALCMETHOD_ISNA;

	public static void init(Context contextT)
	{
		m_Context = contextT.getApplicationContext();

		SharedPreferences preferencesT = m_Context.getSharedPreferences("SigmaPrayer", Context.MODE_PRIVATE);
		m_strSelectedCity = preferencesT.getString("SelectedCity", PrayerTimes.INVALID_CITY);
		m_iCalcMethod = preferencesT.getInt("CalcMethod", PrayerTimes.CALCMETHOD_ISNA);

		m_PendingIntent = PendingIntent.getBroadcast(m_Context, 77777, new Intent(m_Context, SigmaPrayerAlarmReceiver.class), PendingIntent.FLAG_CANCEL_CURRENT);
	}

	private SigmaPrayerAlarm()
	{
		super();
	}

	public static void set(boolean bStart)
	{
		try {

			SharedPreferences preferencesT = m_Context.getSharedPreferences("SigmaPrayer", Context.MODE_PRIVATE);
			boolean bAlarmTime1 = preferencesT.getBoolean("EnableAlarmTime1", true);
			boolean bAlarmTime3 = preferencesT.getBoolean("EnableAlarmTime3", true);
			boolean bAlarmTime4 = preferencesT.getBoolean("EnableAlarmTime4", true);
			boolean bAlarmTime6 = preferencesT.getBoolean("EnableAlarmTime6", true);
			boolean bAlarmTime7 = preferencesT.getBoolean("EnableAlarmTime7", true);

			boolean arrEnableAlarm[] = { bAlarmTime1, false, bAlarmTime3, bAlarmTime4, false, bAlarmTime6, bAlarmTime7 };

			calculatePrayerTimes();
			int iTime = SigmaPrayerWidget.prayerIndex(m_arrTimeStr);

			int iNextTime = -1;

			if ((bStart == SigmaPrayerAlarm.START) && (iTime >= 0) && (iTime < m_arrMessage.length)) {

				if (iTime < (m_arrMessage.length - 1)) {
					iNextTime = iTime + 1;
				}
				else {
					iNextTime = 0;
				}

				if (arrEnableAlarm[iTime] == true) {

					String strAlarmFilePath = preferencesT.getString("AlarmFilePath", "");

					m_AlarmNotification = (NotificationManager)(m_Context.getSystemService(Context.NOTIFICATION_SERVICE));

					CharSequence strAlarmMessage = m_Context.getText(m_arrMessage[iTime]) + "   " + (ArabicReshaper.ARABIC_RESHAPE ? ArabicHelper.reshape(m_Context.getText(m_arrMessage_AR[iTime]).toString()) : m_Context.getText(m_arrMessage_AR[iTime]));

					/*
					Notification notificationT = new Notification(R.drawable.icon, strAlarmMessage, System.currentTimeMillis());
						Intent intentT = new Intent(m_Context, SigmaSettings.class);
						intentT.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
						PendingIntent contentIntent = PendingIntent.getActivity(m_Context, 0, intentT, PendingIntent.FLAG_CANCEL_CURRENT);
						notificationT.setLatestEventInfo(m_Context, m_Context.getText(R.string.strAppName), strAlarmMessage, contentIntent);
						notificationT.flags |= (Notification.FLAG_AUTO_CANCEL | Notification.FLAG_INSISTENT);
					*/

					Intent intentT = new Intent(m_Context, SigmaSettings.class);
					intentT.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
					PendingIntent contentIntent = PendingIntent.getActivity(m_Context, 0, intentT, PendingIntent.FLAG_CANCEL_CURRENT);
					Notification notificationT = new Notification.Builder(m_Context)
							  .setTicker(strAlarmMessage)
							  .setSmallIcon(R.drawable.icon)
							  .setWhen(System.currentTimeMillis())
							  .setContentIntent(contentIntent)
							  .build();
					notificationT.flags |= (Notification.FLAG_AUTO_CANCEL | Notification.FLAG_INSISTENT);

					boolean bAlarmFile = false;

					if ((strAlarmFilePath.equals("") == false) && (strAlarmFilePath.toLowerCase().endsWith(".mp3") == true)) {
						File fileAlarm = new File(strAlarmFilePath);
						if ((fileAlarm.exists() == true) && (fileAlarm.length() >= 1024L) && (fileAlarm.length() <= 134217728L)) {
							bAlarmFile = true;
						}
					}
					if (bAlarmFile == false) {

						Uri alertT = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
						if (alertT == null) {
							alertT = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
							if (alertT == null) {
								alertT = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
							}
						}

						notificationT.sound = alertT;
					}
					else {
						notificationT.sound = Uri.parse("file://" + strAlarmFilePath);
					}

					m_AlarmNotification.notify(R.string.strAppName, notificationT);
				}
			}

			if (iNextTime == -1) {
				iNextTime = SigmaPrayerWidget.prayerNextIndex(m_arrTimeStr);
			}

			if ((iNextTime >= 0) && (iNextTime < m_arrMessage.length)) {

				long iInterval = 0L;
				Date nowT = new Date();
				Calendar calT = Calendar.getInstance();
				calT.setTime(nowT);

				int iHour = calT.get(Calendar.HOUR_OF_DAY);
				int iMinute = calT.get(Calendar.MINUTE);
				int iTm = iMinute + (iHour * 60), iTmx = 0;

				if (iNextTime == 0) {
					iInterval = (3600000L * (long)m_arrTimeHour[0]) + (60000L * (long)m_arrTimeMinute[0])
							- (3600000L * (long)(calT.get(Calendar.HOUR_OF_DAY))) - (60000L * (long)(calT.get(Calendar.MINUTE)));

					iTmx = m_arrTimeMinute[m_arrTimeHour.length - 1] + (m_arrTimeHour[m_arrTimeHour.length - 1] * 60);
					if (iTm > iTmx) {
						iInterval += (3600000L * 24L);	// Next day
					}
				}
				else {
					// Skip Shuruq and Ghurub
					if ((iNextTime == 1) || (iNextTime == 4)) {
						iNextTime += 1;
					}
					iInterval = (3600000L * (long)m_arrTimeHour[iNextTime]) + (60000L * (long)m_arrTimeMinute[iNextTime])
							- (3600000L * (long)(calT.get(Calendar.HOUR_OF_DAY))) - (60000L * (long)(calT.get(Calendar.MINUTE)));
				}

				// >> Set next alarm
				if (iInterval > 0L) {
					AlarmManager alarmT = (AlarmManager)(m_Context.getSystemService(Context.ALARM_SERVICE));
					alarmT.cancel(m_PendingIntent);
					alarmT.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + iInterval, m_PendingIntent);

					// LOG
					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
					Date nextTime = new Date(System.currentTimeMillis() + iInterval);
					Log.i("SigmaPrayer", String.format(Locale.getDefault(), "Alarm set to %s", sdf.format(nextTime)));
					//
				}
				// <<
			}


			try {
				SigmaPrayerWidget.update(m_Context);
			}
			catch (Throwable thT) {

			}
		}
		catch (Throwable thT) {

		}

	}

	public static void cancel()
	{
		try {
			AlarmManager alarmT = (AlarmManager)(m_Context.getSystemService(Context.ALARM_SERVICE));
			alarmT.cancel(m_PendingIntent);
		}
		catch(Throwable thT) {

		}

	}

	public static void calculatePrayerTimes()
	{

		int ii;

		try {
			PrayerTimesHelper helperT = new PrayerTimesHelper(m_Context, m_strSelectedCity, m_iCalcMethod);
			if (helperT.calc() == false) {
				for (ii = 0; ii < m_arrTimeHour.length; ii++) {
					m_arrTimeStr[ii] = PrayerTimes.INVALID_TIME;
					m_arrTimeHour[ii] = 0;
					m_arrTimeMinute[ii] = 0;
					m_arrTime[ii] = 0;
					m_iInterval = 0;
				}
				return;
			}
			String arrT[] = { helperT.getTime1(), helperT.getTime2(), helperT.getTime3(), helperT.getTime4(), helperT.getTime5(), helperT.getTime6(), helperT.getTime7() };

			for (ii = 0; ii < m_arrTimeHour.length; ii++) {
				m_arrTimeStr[ii] = arrT[ii];
				m_arrTimeHour[ii] = Integer.parseInt(arrT[ii].split(":")[0]);
				m_arrTimeMinute[ii] = Integer.parseInt(arrT[ii].split(":")[1]);
				m_arrTime[ii] = m_arrTimeMinute[ii] + (60 * m_arrTimeHour[ii]);
			}
			m_iInterval = m_arrTime[2] - m_arrTime[0];
			for (ii = 2; ii < m_arrTime.length - 1; ii++) {
				if ((m_arrTime[ii + 1] != m_arrTime[ii]) && ((m_arrTime[ii + 1] - m_arrTime[ii]) < m_iInterval)) {
					m_iInterval = m_arrTime[ii + 1] - m_arrTime[ii];
				}
			}
		}
		catch (Throwable thT) {
			for (ii = 0; ii < m_arrTimeHour.length; ii++) {
				m_arrTimeStr[ii] = PrayerTimes.INVALID_TIME;
				m_arrTimeHour[ii] = 0;
				m_arrTimeMinute[ii] = 0;
				m_arrTime[ii] = 0;
				m_iInterval = 0;
			}
			return;
		}
	}

}
