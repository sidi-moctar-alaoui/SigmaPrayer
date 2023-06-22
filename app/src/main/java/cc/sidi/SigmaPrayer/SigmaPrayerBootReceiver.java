//	-------------------------------------------------------------
//	SigmaPrayer for Android
//  Placed in the public domain:
//      It may be freely reproduced, distributed, transmitted, used, modified,
//      built upon, or otherwise exploited by anyone for any purpose, commercial or non-commercial,
//      and in any way
//  sidi.moctar.alaoui@gmail.com
//	-------------------------------------------------------------

package cc.sidi.SigmaPrayer;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

@SuppressLint("DefaultLocale")
public class SigmaPrayerBootReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive(Context contextT, Intent intentT)
	{
		try {
			SigmaPrayerWidget.update(contextT);
			SigmaPrayerAlarm.set(SigmaPrayerAlarm.DIFFER);
		}
		catch(Throwable thT) {

		}

	}

}
