//	-------------------------------------------------------------
//	SigmaPrayer for Android
//  Placed in the public domain:
//      It may be freely reproduced, distributed, transmitted, used, modified,
//      built upon, or otherwise exploited by anyone for any purpose, commercial or non-commercial,
//      and in any way
//  sidi.moctar.alaoui@gmail.com
//	-------------------------------------------------------------

package cc.sidi.SigmaPrayer;

import java.util.Calendar;
import java.util.Date;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

public class SigmaPrayerWidget extends AppWidgetProvider
{
	public static final int[] WIDGET_COLORS = { 0xFFE9EEBE, 0xFFEED6BE, 0xFFEEBED6, 0xFFD0BEEE, 0xFFBEE1EE, 0xFFBEEEC9, 0xFFD4EEBE };

	@Override
	public void onUpdate(Context contextT, AppWidgetManager managerT, int[] idsT)
	{
		super.onUpdate(contextT, managerT, idsT);

		SigmaPrayerAlarm.init(contextT);
		SigmaPrayerWidget.update(contextT);
	}

	@Override
	public void onDeleted(Context contextT, int[] idsT)
	{
		super.onDeleted(contextT, idsT);
	}

	public static void update(Context contextT)
	{
		// >> Update all widgets
		AppWidgetManager managerT = null;
		RemoteViews viewsT = null;
		int[] idsT = null;
		int iR = 0;

		Context widgetContext = contextT.getApplicationContext();


		try {
			managerT = AppWidgetManager.getInstance(widgetContext);
			viewsT = new RemoteViews(widgetContext.getPackageName(), R.layout.widget);
			idsT = managerT.getAppWidgetIds(new ComponentName(widgetContext, SigmaPrayerWidget.class));
		}
		catch (Throwable thT) {
			return;
		}

		try {
			SharedPreferences preferencesT = widgetContext.getSharedPreferences("SigmaPrayer", Context.MODE_PRIVATE);
			String strSelectedCity = preferencesT.getString("SelectedCity", PrayerTimes.INVALID_CITY);
			int iCalcMethod = preferencesT.getInt("CalcMethod", PrayerTimes.CALCMETHOD_ISNA);
			boolean bAlarmTime1 = preferencesT.getBoolean("EnableAlarmTime1", true);
			boolean bAlarmTime3 = preferencesT.getBoolean("EnableAlarmTime3", true);
			boolean bAlarmTime4 = preferencesT.getBoolean("EnableAlarmTime4", true);
			boolean bAlarmTime6 = preferencesT.getBoolean("EnableAlarmTime6", true);
			boolean bAlarmTime7 = preferencesT.getBoolean("EnableAlarmTime7", true);
			boolean bTimeformat = preferencesT.getBoolean("Timeformat", true);

			PrayerTimesHelper helperT = new PrayerTimesHelper(widgetContext, strSelectedCity, iCalcMethod);
			helperT.calc();
			String[] arrTimeStr = new String[7];
			arrTimeStr[0] = helperT.getTime1();
			arrTimeStr[1] = helperT.getTime2();
			arrTimeStr[2] = helperT.getTime3();
			arrTimeStr[3] = helperT.getTime4();
			arrTimeStr[4] = helperT.getTime5();
			arrTimeStr[5] = helperT.getTime6();
			arrTimeStr[6] = helperT.getTime7();
			int iTime = SigmaPrayerWidget.prayerIndex(arrTimeStr);

			viewsT.setTextViewText(R.id.txtCity, strSelectedCity);

			iR = (int)(Math.random() * (double)(SigmaPrayerWidget.WIDGET_COLORS.length));
			viewsT.setTextColor(R.id.txtHeader_AR, SigmaPrayerWidget.WIDGET_COLORS[iR % SigmaPrayerWidget.WIDGET_COLORS.length]);
			String strT = widgetContext.getString(R.string.strAyatSalat_AR);
			viewsT.setTextViewText(R.id.txtHeader_AR, ArabicReshaper.ARABIC_RESHAPE ? ArabicHelper.reshape(strT) : strT);

			Intent configIntent = new Intent(widgetContext, SigmaSettings.class);
			PendingIntent configPendingIntent = PendingIntent.getActivity(widgetContext, 0, configIntent, 0);
			viewsT.setOnClickPendingIntent(R.id.layoutCity, configPendingIntent);

			viewsT.setTextViewText(R.id.txtTime1, (bTimeformat == true) ? helperT.getTime1() : helperT.toTime12(helperT.getTime1()));
			viewsT.setTextViewText(R.id.txtTime3, (bTimeformat == true) ? helperT.getTime3() : helperT.toTime12(helperT.getTime3()));
			viewsT.setTextViewText(R.id.txtTime4, (bTimeformat == true) ? helperT.getTime4() : helperT.toTime12(helperT.getTime4()));
			viewsT.setTextViewText(R.id.txtTime6, (bTimeformat == true) ? helperT.getTime6() : helperT.toTime12(helperT.getTime6()));
			viewsT.setTextViewText(R.id.txtTime7, (bTimeformat == true) ? helperT.getTime7() : helperT.toTime12(helperT.getTime7()));
			viewsT.setTextColor(R.id.txtTime1, (iTime == 0) ? SigmaSettings.FONTCOLOR_HIGHLIGHT : ((bAlarmTime1 == true) ? SigmaSettings.FONTCOLOR_LIGHT : SigmaSettings.FONTCOLOR_LOWLIGHT));
			viewsT.setTextColor(R.id.txtTime3, (iTime == 2) ? SigmaSettings.FONTCOLOR_HIGHLIGHT : ((bAlarmTime3 == true) ? SigmaSettings.FONTCOLOR_LIGHT : SigmaSettings.FONTCOLOR_LOWLIGHT));
			viewsT.setTextColor(R.id.txtTime4, (iTime == 3) ? SigmaSettings.FONTCOLOR_HIGHLIGHT : ((bAlarmTime4 == true) ? SigmaSettings.FONTCOLOR_LIGHT : SigmaSettings.FONTCOLOR_LOWLIGHT));
			viewsT.setTextColor(R.id.txtTime6, (iTime == 5) ? SigmaSettings.FONTCOLOR_HIGHLIGHT : ((bAlarmTime6 == true) ? SigmaSettings.FONTCOLOR_LIGHT : SigmaSettings.FONTCOLOR_LOWLIGHT));
			viewsT.setTextColor(R.id.txtTime7, (iTime == 6) ? SigmaSettings.FONTCOLOR_HIGHLIGHT : ((bAlarmTime7 == true) ? SigmaSettings.FONTCOLOR_LIGHT : SigmaSettings.FONTCOLOR_LOWLIGHT));

			for (int ii = 0; ii < idsT.length; ii++) {
				managerT.updateAppWidget(idsT[ii], viewsT);
			}

		}
		catch (Throwable thT) {
			viewsT.setTextViewText(R.id.txtTime1, PrayerTimes.INVALID_TIME);
			viewsT.setTextViewText(R.id.txtTime3, PrayerTimes.INVALID_TIME);
			viewsT.setTextViewText(R.id.txtTime4, PrayerTimes.INVALID_TIME);
			viewsT.setTextViewText(R.id.txtTime6, PrayerTimes.INVALID_TIME);
			viewsT.setTextViewText(R.id.txtTime7, PrayerTimes.INVALID_TIME);
			for (int ii = 0; ii < idsT.length; ii++) {
				managerT.updateAppWidget(idsT[ii], viewsT);
			}

		}
		// <<
	}

	public static int prayerRawIndex(String[] arrTimeStr)
	{
		Date nowT = new Date();
		Calendar calT = Calendar.getInstance();
		calT.setTime(nowT);

		// {Subh,Shuruq,Dhuhr,Asr,Ghurub,Maghrib,Isha}
		int arrTime[] = {0, 0, 0, 0, 0, 0, 0};

		int ii;

		for (ii = 0; ii < arrTime.length; ii++) {
			arrTime[ii] = Integer.parseInt(arrTimeStr[ii].split(":")[1]) + (60 * Integer.parseInt(arrTimeStr[ii].split(":")[0]));
		}

		int iHour = calT.get(Calendar.HOUR_OF_DAY);
		int iMinute = calT.get(Calendar.MINUTE);
		int iTm = iMinute + (iHour * 60);

		return prayerRawIndex(iTm, arrTime);
	}

	public static int prayerRawIndex(int iTm, int[] arrTime)
	{
		try {

			int ii;

			int iTime = -1, iTmx, iTmy;
			for (ii = 0; ii < arrTime.length; ii++) {
				if (ii == 4) {
					// If Ghurub and Maghrib times are the same, return Maghrib index
					iTmx = arrTime[4];
					iTmy = arrTime[5];
					if (iTmx == iTmy) {
						iTmx = arrTime[5];
						iTmy = arrTime[6];
						if ((iTm >= iTmx) && (iTm < iTmy)) {
							iTime = 5;
							break;
						}
					}
					else if ((iTm >= iTmx) && (iTm < iTmy)) {
						iTime = 4;
						break;
					}
				}
				else if (ii == (arrTime.length - 1)) {
					// Isha
					iTmx = arrTime[arrTime.length - 1];
					iTmy = arrTime[0] + (24 * 60);
					if ((iTm >= iTmx) && (iTm < iTmy)) {
						iTime = arrTime.length - 1;
						break;
					}
				}
				else {
					iTmx = arrTime[ii];
					iTmy = arrTime[ii + 1];
					if ((iTm >= iTmx) && (iTm < iTmy)) {
						iTime = ii;
						break;
					}
				}

			}

			return iTime;
		}
		catch (Throwable thT) {
			return -1;
		}
	}

	public static int prayerIndex(String[] arrTimeStr)
	{

		try {

			Date nowT = new Date();
			Calendar calT = Calendar.getInstance();
			calT.setTime(nowT);

			// {Subh,Shuruq,Dhuhr,Asr,Ghurub,Maghrib,Isha}
			int arrTime[] = {0, 0, 0, 0, 0, 0, 0};

			int ii;

			for (ii = 0; ii < arrTime.length; ii++) {
				arrTime[ii] = Integer.parseInt(arrTimeStr[ii].split(":")[1]) + (60 * Integer.parseInt(arrTimeStr[ii].split(":")[0]));
			}

			int iHour = calT.get(Calendar.HOUR_OF_DAY);
			int iMinute = calT.get(Calendar.MINUTE);
			int iTm = iMinute + (iHour * 60), iTmx = 0, iTmy = 0;

			int iTime = prayerRawIndex(iTm, arrTime), iInterval = 0;
			if (iTime == 4) { // Ghurub
				iTmx = arrTime[iTime];
				iTmy = arrTime[iTime + 1];
				iInterval = Math.min(SigmaPrayerAlarm.ALARM_DURATION_DEFAULT, iTmy - iTmx - 1);
			}
			else if (iTime == (arrTime.length - 1)) { // Isha
				iTmx = arrTime[iTime];
				iTmy = arrTime[0] + (24 * 60);
				iInterval = Math.min(SigmaPrayerAlarm.ALARM_DURATION_DEFAULT, (iTmy - iTmx - 1) / 2);
			}
			else {
				iTmx = arrTime[iTime];
				iTmy = arrTime[iTime + 1];
				iInterval = Math.min(SigmaPrayerAlarm.ALARM_DURATION_DEFAULT, (iTmy - iTmx - 1) / 2);
			}
			if (iInterval < SigmaPrayerAlarm.ALARM_DURATION_MIN) {
				iInterval = SigmaPrayerAlarm.ALARM_DURATION_MIN;
			}
			if ((iTm >= iTmx) && (iTm < (iTmx + iInterval))) {
				return iTime;
			}

			return -1;
		}
		catch (Throwable thT) {

		}

		return -1;
	}

	public static int prayerNextIndex(String[] arrTimeStr)
	{

		try {

			// {Subh,Shuruq,Dhuhr,Asr,Ghurub,Maghrib,Isha}
			int arrTime[] = {0, 0, 0, 0, 0, 0, 0};

			int ii;

			for (ii = 0; ii < arrTime.length; ii++) {
				arrTime[ii] = Integer.parseInt(arrTimeStr[ii].split(":")[1]) + (60 * Integer.parseInt(arrTimeStr[ii].split(":")[0]));
			}

			Date nowT = new Date();
			Calendar calT = Calendar.getInstance();
			calT.setTime(nowT);
			int iHour = calT.get(Calendar.HOUR_OF_DAY);
			int iMinute = calT.get(Calendar.MINUTE);
			int iTm = iMinute + (iHour * 60), iTmx = 0, iTmy = 0;

			int iTime = prayerRawIndex(iTm, arrTime);
			if (iTime < 0) {
				return -1;
			}
			if (iTime >= (arrTime.length - 1)) { // Isha
				iTmx = arrTime[arrTime.length - 1];
				iTmy = arrTime[0] + (24 * 60);
				if ((iTm >= iTmx) && (iTm < iTmy)) {
					return 0;
				}
				else {
					return -1;
				}
			}

			return (iTime + 1);
		}
		catch (Throwable thT) {
			return -1;
		}

	}

}
