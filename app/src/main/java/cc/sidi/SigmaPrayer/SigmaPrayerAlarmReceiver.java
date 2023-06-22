//	-------------------------------------------------------------
//	SigmaPrayer for Android
//  Placed in the public domain:
//      It may be freely reproduced, distributed, transmitted, used, modified,
//      built upon, or otherwise exploited by anyone for any purpose, commercial or non-commercial,
//      and in any way
//  sidi.moctar.alaoui@gmail.com
//	-------------------------------------------------------------

package cc.sidi.SigmaPrayer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

@SuppressLint("DefaultLocale")
public class SigmaPrayerAlarmReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive(Context contextT, Intent intentT)
	{
		try {
			SigmaPrayerWakeLocker.acquire(contextT);

			try {
				SigmaPrayerWidget.update(contextT);
			}
			catch (Throwable thT) {

			}

			SigmaPrayerAlarm.set(SigmaPrayerAlarm.START);

			// LOG
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
			Date nextTime = new Date(System.currentTimeMillis());
			Log.i("SigmaPrayer", String.format(Locale.getDefault(), "Alarm received at %s", sdf.format(nextTime)));
			//

			SigmaPrayerWakeLocker.release();
		}
		catch (Throwable thT) {
			try {
				SigmaPrayerWakeLocker.release();
			}
			catch (Throwable thTT) {

			}

			// LOG
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
			Date nextTime = new Date(System.currentTimeMillis());
			Log.i("SigmaPrayer", String.format(Locale.getDefault(), "Alarm OnReceive Throw at %s", sdf.format(nextTime)));
			//
		}

	}

}
