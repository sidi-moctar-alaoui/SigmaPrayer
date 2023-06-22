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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

@SuppressLint("DefaultLocale")
public class PrayerTimesHelper
{
	Context m_Context = null;

	private SQLiteDatabase m_dbCity = null;
	private Cursor m_cursorT = null;

	private String m_strCity = "";
	private int m_iCalcMethod = PrayerTimes.CALCMETHOD_ISNA;
	private String m_strTimezone = "";
	private String m_strDate = "";

	private String m_strTime1 = PrayerTimes.INVALID_TIME;
	private String m_strTime2 = PrayerTimes.INVALID_TIME;
	private String m_strTime3 = PrayerTimes.INVALID_TIME;
	private String m_strTime4 = PrayerTimes.INVALID_TIME;
	private String m_strTime5 = PrayerTimes.INVALID_TIME;
	private String m_strTime6 = PrayerTimes.INVALID_TIME;
	private String m_strTime7 = PrayerTimes.INVALID_TIME;

	public PrayerTimesHelper(Context contextT, String strCity, int iCalcMethod)
	{
		m_Context = contextT;

		m_strCity = strCity;
		m_iCalcMethod = iCalcMethod;
		m_strDate = "";
	}

	public void setDate(String strDate)
	{
		m_strDate = strDate;
	}

	public void close()
	{
		if (m_cursorT != null) {
			m_cursorT.close();
			m_cursorT = null;
		}
		if (m_dbCity != null) {
			m_dbCity.close();
			m_dbCity = null;
		}
	}

	@Override
	protected void finalize() throws Throwable
	{
		if (m_cursorT != null) {
			m_cursorT.close();
			m_cursorT = null;
		}
		if (m_dbCity != null) {
			m_dbCity.close();
			m_dbCity = null;
		}

		super.finalize();
	}

	public Context getContext()
	{
		return m_Context;
	}

	public void setContext(Context contextT)
	{
		m_Context = contextT;
	}

	public String getCity()
	{
		return m_strCity;
	}

	public void setCity(String strCity)
	{
		m_strCity = strCity;
	}

	public int getCalcMethod()
	{
		return m_iCalcMethod;
	}

	public void setCalcMethod(int iCalcMethod)
	{
		m_iCalcMethod = iCalcMethod;
	}

	public String getTimezone()
	{
		return m_strTimezone;
	}

	public String getTime1()
	{
		return m_strTime1;
	}

	public String getTime2()
	{
		return m_strTime2;
	}

	public String getTime3()
	{
		return m_strTime3;
	}

	public String getTime4()
	{
		return m_strTime4;
	}

	public String getTime5()
	{
		return m_strTime5;
	}

	public String getTime6()
	{
		return m_strTime6;
	}

	public String getTime7()
	{
		return m_strTime7;
	}

	public boolean calc(Context contextT, String strCity, String strTimezone, int iCalcMethod)
	{
		m_Context = contextT;

		m_strCity = strCity;
		m_iCalcMethod = iCalcMethod;
		m_strTimezone = strTimezone;

		return calc();
	}
	public boolean calc(String strCity, String strTimezone, int iCalcMethod)
	{
		m_strCity = strCity;
		m_iCalcMethod = iCalcMethod;
		m_strTimezone = strTimezone;

		return calc();
	}
	public boolean calc()
	{

		try {

			if ((m_strCity == null) || (m_strCity.equals(""))) {
				return false;
			}

			PrayerTimes prayersT = new PrayerTimes();

			prayersT.setTimeFormat(PrayerTimes.TIMEFORMAT_24);
			prayersT.setCalcMethod(m_iCalcMethod);
			if (m_iCalcMethod == PrayerTimes.CALCMETHOD_UOIF) {
				double arrOffsets[] = {-5.0, 0.0, 5.0, 0.0, 0.0, 0.0, 5.0};	// {Subh,Shuruq,Dhuhr,Asr,Ghurub,Maghrib,Isha}
				prayersT.tune(arrOffsets);
			}
			else {
				double arrOffsets[] = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};	// {Subh,Shuruq,Dhuhr,Asr,Ghurub,Maghrib,Isha}
				prayersT.tune(arrOffsets);
			}

			prayersT.setAsrMethod(PrayerTimes.ASRMETHOD_SHAFII);
			prayersT.setLatitudeCorr(PrayerTimes.LATCORR_NONE);

			Calendar calT = Calendar.getInstance();
			if (m_strDate.equals("")) {
				Date nowT = new Date();
				calT.setTime(nowT);
			}
			else {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
				calT.setTime(dateFormat.parse(m_strDate));
			}

			String strCountry = "";
			String strCity = "";
			double fLatitude = 199.0;
			double fLongitude = 199.0;
			double fTimezone = 199.0;

			String[] arrCity = m_strCity.split(":");
			strCountry = arrCity[0].trim();
			strCity = arrCity[1].trim();

			if (m_cursorT != null) {
				m_cursorT.close();
				m_cursorT = null;
			}
			if (m_dbCity != null) {
				m_dbCity.close();
				m_dbCity = null;
			}

			// >> BEGIN Database
			m_dbCity = m_Context.openOrCreateDatabase(SigmaDatabase.DATABASE_NAME, Context.MODE_PRIVATE, null);
			if (m_dbCity == null) {
				return false;
			}

			m_cursorT = m_dbCity.query(SigmaDatabase.TBL_CITY, null, "country='" + strCountry + "'" + " AND city='" + strCity + "'", null, null, null, null);
			if (m_cursorT != null) {
				m_cursorT.moveToFirst();

				int iCountryColumn = m_cursorT.getColumnIndex("country");
				int iCityColumn = m_cursorT.getColumnIndex("city");
				int iLatitudeColumn = m_cursorT.getColumnIndex("latitude");
				int iLongitudeColumn = m_cursorT.getColumnIndex("longitude");
				int iTimezoneColumn = m_cursorT.getColumnIndex("timezone");

				strCountry = m_cursorT.getString(iCountryColumn);
				strCity = m_cursorT.getString(iCityColumn);
				fLatitude = m_cursorT.getDouble(iLatitudeColumn);
				fLongitude = m_cursorT.getDouble(iLongitudeColumn);
				String strTimezone = m_cursorT.getString(iTimezoneColumn);
				String[] arrT = strTimezone.split(";");
				fTimezone = Double.parseDouble(arrT[0]);
				double fTimezoneOrg = Double.parseDouble(arrT[1]);

				// Automatically calculate GMT offset (for daylight saving time)
				try {
					SharedPreferences preferencesT = m_Context.getSharedPreferences("SigmaPrayer", Context.MODE_PRIVATE);
					boolean bTimezoneAuto = preferencesT.getBoolean("TimezoneAuto", true);
					if (bTimezoneAuto) {
						TimeZone tz = TimeZone.getDefault();
						Date nowTZ = new Date();
						float gmtOffset = (float)(tz.getOffset(nowTZ.getTime())) / 1000.0f / 3600.0f;
						if ((gmtOffset != fTimezone) && (gmtOffset >= (fTimezoneOrg - 3.0f)) && (gmtOffset <= (fTimezoneOrg + 3.0f))) {
							fTimezone = gmtOffset;
						}
					}

				}
				catch (Throwable thT) {

				}
				//

				m_strTimezone = arrT[0];

				m_cursorT.close();
				m_cursorT = null;
			}

			m_dbCity.close();
			m_dbCity = null;
			// << END Database

			if ((strCountry.length() > 1) && (strCountry.length() < 128)
				&& (strCity.length() > 1) && (strCity.length() < 128)
				&& (fLatitude > -199.0) && (fLatitude < 199.0)
				&& (fLongitude > -199.0) && (fLongitude < 199.0)) {

				ArrayList<String> timesT = prayersT.getPrayerTimes(calT, fLatitude, fLongitude, fTimezone);

				boolean bRet = false;
				if (timesT.get(0).equals(PrayerTimes.INVALID_TIME) || timesT.get(1).equals(PrayerTimes.INVALID_TIME)
					|| timesT.get(2).equals(PrayerTimes.INVALID_TIME) || timesT.get(3).equals(PrayerTimes.INVALID_TIME)
					|| timesT.get(5).equals(PrayerTimes.INVALID_TIME) || timesT.get(6).equals(PrayerTimes.INVALID_TIME)) {

					for (int ii = 0; ii < PrayerTimes.LATCORR.length; ii++) {
						prayersT.setLatitudeCorr(PrayerTimes.LATCORR[ii]);
						timesT = prayersT.getPrayerTimes(calT, fLatitude, fLongitude, fTimezone);
						if ((timesT.get(0).equals(PrayerTimes.INVALID_TIME) == false) && (timesT.get(1).equals(PrayerTimes.INVALID_TIME) == false)
								&& (timesT.get(2).equals(PrayerTimes.INVALID_TIME) == false) && (timesT.get(3).equals(PrayerTimes.INVALID_TIME) == false)
								&& (timesT.get(5).equals(PrayerTimes.INVALID_TIME) == false) && (timesT.get(6).equals(PrayerTimes.INVALID_TIME) == false)) {
							break;
						}
					}
				}
				else {
					bRet = true;
				}

				m_strTime1 = timesT.get(0);
				m_strTime2 = timesT.get(1);
				m_strTime3 = timesT.get(2);
				m_strTime4 = timesT.get(3);
				m_strTime5 = timesT.get(4);
				m_strTime6 = timesT.get(5);
				m_strTime7 = timesT.get(6);

				return bRet;
			}
			else {

				m_strTime1 = PrayerTimes.INVALID_TIME;
				m_strTime2 = PrayerTimes.INVALID_TIME;
				m_strTime3 = PrayerTimes.INVALID_TIME;
				m_strTime4 = PrayerTimes.INVALID_TIME;
				m_strTime5 = PrayerTimes.INVALID_TIME;
				m_strTime6 = PrayerTimes.INVALID_TIME;
				m_strTime7 = PrayerTimes.INVALID_TIME;

				return false;
			}
		}
		catch (Throwable thT) {

			if (m_cursorT != null) {
				m_cursorT.close();
				m_cursorT = null;
			}

			if (m_dbCity != null) {
				m_dbCity.close();
				m_dbCity = null;
			}

			m_strTime1 = PrayerTimes.INVALID_TIME;
			m_strTime2 = PrayerTimes.INVALID_TIME;
			m_strTime3 = PrayerTimes.INVALID_TIME;
			m_strTime4 = PrayerTimes.INVALID_TIME;
			m_strTime5 = PrayerTimes.INVALID_TIME;
			m_strTime6 = PrayerTimes.INVALID_TIME;
			m_strTime7 = PrayerTimes.INVALID_TIME;

			return false;
		}
	}

	public String toTime12(String strTime24)
	{

		if ((strTime24 == null) || strTime24.equals("")) {
			return PrayerTimes.INVALID_TIME;
		}

		if (strTime24.indexOf(':') < 0) {
			return PrayerTimes.INVALID_TIME;
		}

		String[] arrT = strTime24.split(":");

		int iHour = 0;
		int iMinute = 0;
		try {
			iHour = Integer.parseInt(arrT[0]);
			iMinute = Integer.parseInt(arrT[1]);
		}
		catch (Throwable thT) {
			return PrayerTimes.INVALID_TIME;
		}

		int iHourT = ((((iHour + 12) - 1) % (12)) + 1);
		String strResult = String.format((iHourT <= 9) ? " %d:%02d" : "%d:%02d", iHourT, iMinute);
		strResult += (iHour >= 12) ? "p" : "a";

		return strResult;
	}

}
