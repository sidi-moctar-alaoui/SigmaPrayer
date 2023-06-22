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
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.Calendar;

@SuppressLint("DefaultLocale")
public class PrayerTimes {

	private int m_iCalcMethod; 									// Calculation method
	private int m_iAsrMethod; 										// Juristic method for Asr
	private int m_iDhuhrOffset; 									// Minutes after mid-day for Dhuhr
	private int m_iLatitudeCorr; 									// Adjusting method for higher latitudes
	private int m_iTimeFormat; 									// Time format
	private double m_fLatitude; 									// Latitude
	private double m_fLongitude; 									// Longitude
	private double m_fTimezone; 									// Time zone
	private double m_fJulianDate; 								// Julian date

	public final static int CALCMETHOD_JAFARI = 1; 			// Ithna Ashari Shia
	public final static int CALCMETHOD_TEHRAN = 2; 			// Institute of Geophysics, University of Tehran
	public final static int CALCMETHOD_KARACHI = 3; 		// University of Islamic Sciences, Karachi
	public final static int CALCMETHOD_ISNA = 4; 			// Islamic Society of North America (ISNA)
	public final static int CALCMETHOD_MWL = 5; 				// Muslim World League (MWL)
	public final static int CALCMETHOD_MAKKAH = 6; 			// Umm al-Qura, Makkah
	public final static int CALCMETHOD_EGYPT = 7; 			// Egyptian General Authority of Survey
	public final static int CALCMETHOD_UOIF = 8; 			// Union of French Islamic Organizations (UOIF)
	public final static int CALCMETHOD_CUSTOM = 99; 		// Custom Setting

	public final static int CALCMETHOD[] = {CALCMETHOD_ISNA, CALCMETHOD_MAKKAH, CALCMETHOD_TEHRAN, CALCMETHOD_MWL, CALCMETHOD_UOIF};

	public final static int ASRMETHOD_SHAFII = 1;			// Shafii (standard)
	public final static int ASRMETHOD_HANAFI = 2;			// Hanafi

	public final static int LATCORR_NONE = 1; 				// No adjustment
	public final static int LATCORR_MIDNIGHT = 2; 			// Middle of night
	public final static int LATCORR_SEVENTH = 3; 			// 1/7th of night
	public final static int LATCORR_ANGLE = 4; 				// Angle/60th of night

	public final static int LATCORR[] = {LATCORR_NONE, LATCORR_ANGLE, LATCORR_MIDNIGHT, LATCORR_SEVENTH};

	public final static int TIMEFORMAT_FLOAT = 1;			// Floating point number
	public final static int TIMEFORMAT_24 = 2; 				// 24-hour format

	private ArrayList<String> m_arrTimes;
	public final static String INVALID_CITY = "- : -";
	public final static String INVALID_TIME = "--:--";
	public final static String INVALID_TIMEZONE = "-.-";

	private int m_iIter;

	// { 	Subh angle,  Maghrib selector (0 = angle; 1 = minutes after sunset), Maghrib parameter value (in angle or minutes),
	//		Isha selector (0 = angle; 1 = minutes after Maghrib), Isha parameter value (in angle or minutes) }
	private SparseArray<double[]> m_arrParams = null;

	private double[] m_arrOffsets = null;

	public PrayerTimes()
	{
		setCalcMethod(CALCMETHOD_JAFARI);
		setAsrMethod(ASRMETHOD_SHAFII);
		setLatitudeCorr(LATCORR_ANGLE);
		setTimeFormat(TIMEFORMAT_24);
		setDhuhrOffset(0);

		// Time Names
		m_arrTimes = new ArrayList<String>();
		m_arrTimes.add("Subh");
		m_arrTimes.add("Shuruq");
		m_arrTimes.add("Dhuhr");
		m_arrTimes.add("Asr");
		m_arrTimes.add("Ghurub");
		m_arrTimes.add("Maghrib");
		m_arrTimes.add("Isha");

		setIter(1); 						// Number of iterations needed to compute times

		m_arrOffsets = new double[7];
		m_arrOffsets[0] = 0.0;
		m_arrOffsets[1] = 0.0;
		m_arrOffsets[2] = 0.0;
		m_arrOffsets[3] = 0.0;
		m_arrOffsets[4] = 0.0;
		m_arrOffsets[5] = 0.0;
		m_arrOffsets[6] = 0.0;

		m_arrParams = new SparseArray<double[]>();

		// { 	Subh angle,  Maghrib selector (0 = angle; 1 = minutes after sunset), Maghrib parameter value (in angle or minutes),
		//		Isha selector (0 = angle; 1 = minutes after Maghrib), Isha parameter value (in angle or minutes) }

		// Jafari
		double[] Jvalues = {16.0, 0.0, 4.0, 0.0, 14.0};
		m_arrParams.put(CALCMETHOD_JAFARI, Jvalues);

		// Tehran
		double[] Tvalues = {17.7, 0.0, 4.5, 0.0, 14.0};
		m_arrParams.put(CALCMETHOD_TEHRAN, Tvalues);

		// Karachi
		double[] Kvalues = {18.0, 1.0, 0.0, 0.0, 18.0};
		m_arrParams.put(CALCMETHOD_KARACHI, Kvalues);

		// ISNA
		double[] Ivalues = {15.0, 1.0, 0.0, 0.0, 15.0};
		m_arrParams.put(CALCMETHOD_ISNA, Ivalues);

		// MWL
		double[] MWvalues = {18.0, 1.0, 0.0, 0.0, 17.0};
		m_arrParams.put(CALCMETHOD_MWL, MWvalues);

		// Makkah
		double[] MKvalues = {18.5, 1.0, 0.0, 1.0, 90.0};
		m_arrParams.put(CALCMETHOD_MAKKAH, MKvalues);

		// Egypt
		double[] Evalues = {19.5, 1.0, 0.0, 0.0, 17.5};
		m_arrParams.put(CALCMETHOD_EGYPT, Evalues);

		// UOIF
		double[] Uvalues = {12.0, 1.0, 5.0, 0.0, 12.0};
		m_arrParams.put(CALCMETHOD_UOIF, Uvalues);

		// Custom
		double[] Cvalues = {18.0, 1.0, 0.0, 0.0, 17.0};
		m_arrParams.put(CALCMETHOD_CUSTOM, Cvalues);

	}

	public int getCalcMethod()
	{
		return m_iCalcMethod;
	}
	public void setCalcMethod(int iCalcMethod)
	{
		m_iCalcMethod = iCalcMethod;
	}

	public int getAsrMethod()
	{
		return m_iAsrMethod;
	}
	public void setAsrMethod(int iAsrMethod)
	{
		m_iAsrMethod = iAsrMethod;
	}

	public int getDhuhrOffset()
	{
		return m_iDhuhrOffset;
	}
	public void setDhuhrOffset(int iDhuhrOffset)
	{
		m_iDhuhrOffset = iDhuhrOffset;
	}

	public int getLatitudeCorr()
	{
		return m_iLatitudeCorr;
	}
	public void setLatitudeCorr(int iLatitudeCorr)
	{
		m_iLatitudeCorr = iLatitudeCorr;
	}

	public int getTimeFormat()
	{
		return m_iTimeFormat;
	}
	public void setTimeFormat(int iTimeFormat)
	{
		m_iTimeFormat = iTimeFormat;
	}

	public double getLatitude()
	{
		return m_fLatitude;
	}
	public void setLatitude(double fLatitude)
	{
		m_fLatitude = fLatitude;
	}

	public double getLongitude()
	{
		return m_fLongitude;
	}
	public void setLongitude(double fLongitude)
	{
		m_fLongitude = fLongitude;
	}

	public double getTimezone()
	{
		return m_fTimezone;
	}
	public void setTimezone(double fTimezone)
	{
		m_fTimezone = fTimezone;
	}

	public double getJulianDate()
	{
		return m_fJulianDate;
	}
	public void setJulianDate(double jDate)
	{
		m_fJulianDate = jDate;
	}

	private int getIter()
	{
		return m_iIter;
	}
	private void setIter(int iIter)
	{
		m_iIter = iIter;
	}

	public ArrayList<String> getTimeNames()
	{
		return m_arrTimes;
	}

	// return prayer times for a given date
	public ArrayList<String> getDatePrayerTimes(int iYear, int iMonth, int iDay, double fLatitude, double fLongitude, double fTimezone)
	{
		setLatitude(fLatitude);
		setLongitude(fLongitude);
		setTimezone(fTimezone);
		setJulianDate(julianDate(iYear, iMonth, iDay));
		double fLongitudeDiff = fLongitude / (15.0 * 24.0);
		setJulianDate(getJulianDate() - fLongitudeDiff);

		return computeDayTimes();
	}

	// return prayer times for a given date
	public ArrayList<String> getPrayerTimes(Calendar tDate, double fLatitude, double fLongitude, double fTimezone)
	{
		int iYear = tDate.get(Calendar.YEAR);
		int iMonth = tDate.get(Calendar.MONTH);
		int iDay = tDate.get(Calendar.DATE);

		return getDatePrayerTimes(iYear, iMonth + 1, iDay, fLatitude, fLongitude, fTimezone);
	}

	// range reduce angle in degrees.
	private double fixAngle(double a)
	{
		a = a - (360.0 * (Math.floor(a / 360.0)));
		a = (a < 0.0) ? (a + 360.0) : a;
		return a;
	}

	// range reduce hours to 0..23
	private double fixHour(double a)
	{
		a = a - 24.0 * Math.floor(a / 24.0);
		a = (a < 0.0) ? (a + 24.0) : a;
		return a;
	}

	// radian to degree
	private double radiansToDegrees(double alpha)
	{
		return ((alpha * 180.0) / Math.PI);
	}

	// deree to radian
	private double DegreesToRadians(double alpha)
	{
		return ((alpha * Math.PI) / 180.0);
	}

	// degree sin
	private double dsin(double d)
	{
		return (Math.sin(DegreesToRadians(d)));
	}

	// degree cos
	private double dcos(double d)
	{
		return (Math.cos(DegreesToRadians(d)));
	}

	// degree tan
	private double dtan(double d)
	{
		return (Math.tan(DegreesToRadians(d)));
	}

	// degree arcsin
	private double darcsin(double x)
	{
		return radiansToDegrees(Math.asin(x));
	}

	// degree arccos
	private double darccos(double x)
	{
		return radiansToDegrees(Math.acos(x));
	}

	// degree arctan2
	private double darctan2(double y, double x)
	{
		return radiansToDegrees(Math.atan2(y, x));
	}

	// degree arccot
	private double darccot(double x)
	{
		return radiansToDegrees(Math.atan2(1.0, x));
	}

	// calculate julian date from a calendar date
	private double julianDate(int iYear, int iMonth, int iDay)
	{
		if (iMonth <= 2) {
			iYear -= 1;
			iMonth += 12;
		}
		double A = Math.floor(((double)iYear) / 100.0);
		double B = 2.0 - A + Math.floor(A / 4.0);
		double JD = Math.floor(365.25 * (iYear + 4716)) + Math.floor(30.6001 * (double)(iMonth + 1)) + ((double)iDay) + B - 1524.5;

		return JD;
	}

	// compute declination angle of sun and equation of time
	private double[] sunPosition(double jd)
	{
		double D = jd - 2451545.0;
		double g = fixAngle(357.529 + (0.98560028 * D));
		double q = fixAngle(280.459 + (0.98564736 * D));
		double L = fixAngle(q + (1.915 * dsin(g)) + (0.020 * dsin(2.0 * g)));
		double e = 23.439 - (0.00000036 * D);
		double d = darcsin(dsin(e) * dsin(L));
		double RA = (darctan2((dcos(e) * dsin(L)), (dcos(L)))) / 15.0;
		RA = fixHour(RA);

		double EqT = (q / 15.0) - RA;
		double[] sPosition = new double[2];
		sPosition[0] = d;
		sPosition[1] = EqT;

		return sPosition;
	}

	// compute equation of time
	private double equationOfTime(double jd)
	{
		return sunPosition(jd)[1];
	}

	// compute declination angle of sun
	private double sunDeclination(double jd)
	{
		return sunPosition(jd)[0];
	}

	// compute mid-day (Dhuhr, Zawal) time
	private double computeMidDay(double t)
	{
		double T = equationOfTime(getJulianDate() + t);
		double Z = fixHour(12 - T);

		return Z;
	}

	// compute time for a given angle G
	private double computeTime(double G, double t)
	{
		double D = sunDeclination(getJulianDate() + t);
		double Z = computeMidDay(t);
		double Beg = -dsin(G) - dsin(D) * dsin(getLatitude());
		double Mid = dcos(D) * dcos(getLatitude());
		double V = darccos(Beg / Mid) / 15.0;

		return (Z + ((G > 90.0) ? -V : V));
	}

	// compute the time of Asr
	// Shafii: step=1, Hanafi: step=2
	private double computeAsr(double step, double t)
	{
		double D = sunDeclination(getJulianDate() + t);
		double G = -darccot(step + dtan(Math.abs(getLatitude() - D)));

		return computeTime(G, t);
	}

	// compute the difference between two times
	private double timeDiff(double fTimea, double fTimeb)
	{
		return fixHour(fTimeb - fTimea);
	}

	// set custom values for calculation parameters
	private void setCustomParams(double[] arParams)
	{

		for (int i = 0; i < 5; i++) {
			if (arParams[i] == -1.0) {
				arParams[i] = m_arrParams.get(getCalcMethod())[i];
				m_arrParams.put(CALCMETHOD_CUSTOM, arParams);
			}
			else {
				m_arrParams.get(CALCMETHOD_CUSTOM)[i] = arParams[i];
			}
		}
		setCalcMethod(CALCMETHOD_CUSTOM);
	}

	// set the angle for calculating Subh
	public void setSubhAngle(double angle)
	{
		double[] arParams = {angle, -1.0, -1.0, -1.0, -1.0};
		setCustomParams(arParams);
	}

	// set the angle for calculating Maghrib
	public void setMaghribAngle(double angle)
	{
		double[] arParams = {-1.0, 0.0, angle, -1.0, -1.0};
		setCustomParams(arParams);
	}

	// set the angle for calculating Isha
	public void setIshaAngle(double angle)
	{
		double[] arParams = {-1.0, -1.0, -1.0, 0, angle};
		setCustomParams(arParams);
	}

	// set the minutes after Ghurub for calculating Maghrib
	public void setMaghribMinutes(double fMinutes)
	{
		double[] arParams = {-1.0, 1.0, fMinutes, -1.0, -1.0};
		setCustomParams(arParams);

	}

	// set the minutes after Maghrib for calculating Isha
	public void setIshaMinutes(double fMinutes)
	{
		double[] arParams = {-1.0, -1.0, -1.0, 1.0, fMinutes};
		setCustomParams(arParams);
	}

	// convert double hours to 24h format
	public String floatToTime24(double fTime)
	{
		String strResult = "";

		if (Double.isNaN(fTime)) {
			return INVALID_TIME;
		}

		fTime = fixHour(fTime + (0.5 / 60.0)); // add 0.5 minutes to round
		int iHours = (int)Math.floor(fTime);
		double fMinutes = Math.floor((fTime - iHours) * 60.0);

		if (((iHours >= 0) && (iHours <= 9)) && ((fMinutes >= 0.0) && (fMinutes <= 9.0))) {
			strResult = "0" + iHours + ":0" + Math.round(fMinutes);
		}
		else if (((iHours >= 0) && (iHours <= 9))) {
			strResult = "0" + iHours + ":" + Math.round(fMinutes);
		}
		else if (((fMinutes >= 0) && (fMinutes <= 9))) {
			strResult = iHours + ":0" + Math.round(fMinutes);
		}
		else {
			strResult = iHours + ":" + Math.round(fMinutes);
		}

		return strResult;
	}

	// compute prayer times at given Julian date
	private double[] computeTimes(double[] arTimes)
	{
		double[] arT = dayPortion(arTimes);

		double fSubh = computeTime(180.0 - m_arrParams.get(getCalcMethod())[0], arT[0]);

		double fShuruq = computeTime(180.0 - 0.833, arT[1]);

		double fDhuhr = computeMidDay(arT[2]);
		double fAsr = computeAsr(getAsrMethod(), arT[3]);
		double fGhurub = computeTime(0.833, arT[4]);

		double fMaghrib = computeTime(m_arrParams.get(getCalcMethod())[2], arT[5]);
		double fIsha = computeTime(m_arrParams.get(getCalcMethod())[4], arT[6]);

		double[] CTimes = { fSubh, fShuruq, fDhuhr, fAsr, fGhurub, fMaghrib, fIsha };

		return CTimes;
	}

	// compute prayer times at given Julian date
	private ArrayList<String> computeDayTimes()
	{
		double[] arTimes = { 5.0, 6.0, 12.0, 13.0, 18.0, 18.0, 18.0 }; // default times

		for (int i = 1; i <= getIter(); i++) {
			arTimes = computeTimes(arTimes);
		}

		arTimes = adjustTimes(arTimes);
		arTimes = tuneTimes(arTimes);

		return adjustTimesFormat(arTimes);
	}

	// adjust times in a prayer time array
	private double[] adjustTimes(double[] arTimes)
	{
		for (int i = 0; i < arTimes.length; i++) {
			arTimes[i] += (getTimezone() - (getLongitude() / 15.0));
		}

		// Dhuhr
		arTimes[2] += (getDhuhrOffset() / 60.0);

		// Maghrib
		if (m_arrParams.get(getCalcMethod())[1] == 1) {
			arTimes[5] = arTimes[4] + (m_arrParams.get(getCalcMethod())[2] / 60.0);
		}
		// Isha
		if (m_arrParams.get(getCalcMethod())[3] == 1) {
			arTimes[6] = arTimes[5] + (m_arrParams.get(getCalcMethod())[4] / 60.0);
		}

		if (getLatitudeCorr() != LATCORR_NONE) {
			arTimes = adjustLatTimes(arTimes);
		}

		return arTimes;
	}

	// convert times array to given float or 24h time format
	private ArrayList<String> adjustTimesFormat(double[] arTimes)
	{
		ArrayList<String> arResult = new ArrayList<String>();

		if (getTimeFormat() == TIMEFORMAT_FLOAT) {
			for (double fTime : arTimes) {
				arResult.add(String.valueOf(fTime));
			}
			return arResult;
		}
		else {	// TIMEFORMAT_24
			for (int ii = 0; ii < 7; ii++) {
				arResult.add(floatToTime24(arTimes[ii]));
			}
		}

		return arResult;
	}

	// adjust Subh, Isha and Maghrib for locations in higher latitudes
	private double[] adjustLatTimes(double[] arTimes)
	{
		double fNightTime = timeDiff(arTimes[4], arTimes[1]); // sunset to sunrise

		// Adjust Subh
		double fSubhDiff = nightPortion(m_arrParams.get(getCalcMethod())[0]) * fNightTime;

		if (Double.isNaN(arTimes[0]) || (timeDiff(arTimes[0], arTimes[1]) > fSubhDiff)) {
			arTimes[0] = arTimes[1] - fSubhDiff;
		}

		// Adjust Isha
		double fIshaAngle = (m_arrParams.get(getCalcMethod())[3] == 0.0) ? m_arrParams.get(getCalcMethod())[4] : 18.0;
		double fIshaDiff = nightPortion(fIshaAngle) * fNightTime;
		if (Double.isNaN(arTimes[6]) || (timeDiff(arTimes[4], arTimes[6]) > fIshaDiff)) {
			arTimes[6] = arTimes[4] + fIshaDiff;
		}

		// Adjust Maghrib
		double fMaghribAngle = (m_arrParams.get(getCalcMethod())[1] == 0.0) ? m_arrParams.get(getCalcMethod())[2] : 4.0;
		double fMaghribDiff = nightPortion(fMaghribAngle) * fNightTime;
		if (Double.isNaN(arTimes[5]) || (timeDiff(arTimes[4], arTimes[5]) > fMaghribDiff)) {
			arTimes[5] = arTimes[4] + fMaghribDiff;
		}

		return arTimes;
	}

	// the night portion used for adjusting times in higher latitudes
	private double nightPortion(double fAngle)
	{
		double fCalc = 0;

		if (m_iLatitudeCorr == LATCORR_ANGLE) {
			fCalc = fAngle / 60.0;
		}
		else if (m_iLatitudeCorr == LATCORR_MIDNIGHT) {
			fCalc = 0.5;
		}
		else if (m_iLatitudeCorr == LATCORR_SEVENTH) {
			fCalc = 0.14286;
		}

		return fCalc;
	}

	// convert hours to day portions
	private double[] dayPortion(double[] arTimes)
	{
		for (int i = 0; i < 7; i++) {
			arTimes[i] /= 24.0;
		}

		return arTimes;
	}

	// Tune timings for adjustments
	public void tune(double[] arrOffsets)
	{
		for (int i = 0; i < arrOffsets.length; i++) {
			// offsetTimes length should be 7 in order of Subh, Shuruq, Dhuhr, Asr, Ghurub, Maghrib, Isha
			m_arrOffsets[i] = arrOffsets[i];
		}
	}

	private double[] tuneTimes(double[] arTimes)
	{
		for (int i = 0; i < arTimes.length; i++) {
			arTimes[i] = arTimes[i] + (m_arrOffsets[i] / 60.0);
		}

		return arTimes;
	}

}
