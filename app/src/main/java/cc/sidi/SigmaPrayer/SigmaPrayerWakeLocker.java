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
import android.content.Context;
import android.os.PowerManager;

@SuppressLint("DefaultLocale")
public abstract class SigmaPrayerWakeLocker
{
	private static PowerManager.WakeLock m_WakeLock;

	@SuppressLint("Wakelock")
	public static void acquire(Context ctx)
	{
		if (m_WakeLock != null) {
			m_WakeLock.release();
		}

		PowerManager pm = (PowerManager) ctx.getSystemService(Context.POWER_SERVICE);
		m_WakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, "SigmaPrayerWakeLocker");
		m_WakeLock.acquire();
	}

	public static void release()
	{
		if (m_WakeLock != null) {
			m_WakeLock.release();
		}
		m_WakeLock = null;
	}
}
