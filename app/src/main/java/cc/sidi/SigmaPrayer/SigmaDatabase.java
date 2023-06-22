//	-------------------------------------------------------------
//	SigmaPrayer for Android
//  Placed in the public domain:
//      It may be freely reproduced, distributed, transmitted, used, modified,
//      built upon, or otherwise exploited by anyone for any purpose, commercial or non-commercial,
//      and in any way
//  sidi.moctar.alaoui@gmail.com
//	-------------------------------------------------------------

package cc.sidi.SigmaPrayer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class SigmaDatabase
{
	// Database complete path
	public static final String DATABASE_NAME = "SigmaPrayer.SQLite";
	public static final String TBL_CITY = "tbl_city";

	// Check the database
	public static boolean isDatabaseReady(Context contextT)
	{
		SQLiteDatabase dbCity = null;
		Cursor tCursor = null;

		try {
			dbCity = contextT.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);

			if (dbCity == null) {
				return false;
			}

			tCursor = dbCity.query(TBL_CITY, null, null, null, null, null, null);
			if (tCursor == null) {
				dbCity.close();
				dbCity = null;
				return false;
			}
			tCursor.close();
			tCursor = null;

			dbCity.close();
			dbCity = null;

			return true;
		}
		catch (SQLiteException excT) {
			if (tCursor != null) {
				tCursor.close();
				tCursor = null;
			}
			if (dbCity != null) {
				dbCity.close();
				dbCity = null;
			}
			return false;
		}
		catch (Throwable excT) {
			if (tCursor != null) {
				tCursor.close();
				tCursor = null;
			}
			if (dbCity != null) {
				dbCity.close();
				dbCity = null;
			}
			return false;
		}
	}

	public static boolean initData(Context contextT)
	{
		return initData(contextT, false);
	}
	// Done only one time
	public static boolean initData(Context contextT, boolean bReset)
	{
		if ((bReset == false) && (isDatabaseReady(contextT) == true)) {
			return true;
		}

		SQLiteDatabase dbCity = null;

		try {

			contextT.deleteDatabase(DATABASE_NAME);
			dbCity = contextT.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
			if (dbCity == null) {
				return false;
			}

			dbCity.execSQL("DROP TABLE IF EXISTS " + TBL_CITY);

			String sqlT = "CREATE TABLE IF NOT EXISTS " + TBL_CITY + " (" +
				"itemid INTEGER PRIMARY KEY AUTOINCREMENT, " +
				"country TEXT NOT NULL, " +
				"city TEXT NOT NULL, " +
				"latitude REAL NOT NULL, " +
				"longitude REAL NOT NULL, " +
				"timezone TEXT NOT NULL, " +
				"zipcode INTEGER, " +
				"flag INTEGER, " +
				"note TEXT);";
			dbCity.execSQL(sqlT);

			ContentValues valuesT = new ContentValues();

			// Cities

			valuesT.clear();
			valuesT.put("country", "Afghanistan");
			valuesT.put("city", "Kabul");
			valuesT.put("latitude", 34.58330);
			valuesT.put("longitude", 69.20000);
			valuesT.put("timezone", "4.5;4.5;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Algeria");
			valuesT.put("city", "Algiers");
			valuesT.put("latitude", 36.75290);
			valuesT.put("longitude", 3.04205);
			valuesT.put("timezone", "0.0;0.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Algeria");
			valuesT.put("city", "Oran");
			valuesT.put("latitude", 35.69690);
			valuesT.put("longitude", -0.63300);
			valuesT.put("timezone", "0.0;0.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Algeria");
			valuesT.put("city", "Constantine");
			valuesT.put("latitude", 36.35000);
			valuesT.put("longitude", 6.60000);
			valuesT.put("timezone", "0.0;0.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Algeria");
			valuesT.put("city", "Adrar");
			valuesT.put("latitude", 26.41810);
			valuesT.put("longitude", -0.60150);
			valuesT.put("timezone", "0.0;0.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Argentina");
			valuesT.put("city", "Buenos Aires");
			valuesT.put("latitude", -34.58330);
			valuesT.put("longitude", -58.48330);
			valuesT.put("timezone", "-3.0;-3.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Argentina");
			valuesT.put("city", "Cordoba");
			valuesT.put("latitude", -31.36670);
			valuesT.put("longitude", -64.25000);
			valuesT.put("timezone", "-4.0;-4.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Australia");
			valuesT.put("city", "Adelaide");
			valuesT.put("latitude", -34.93330);
			valuesT.put("longitude", 138.58330);
			valuesT.put("timezone", "9.5;9.5;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Australia");
			valuesT.put("city", "Alice Springs");
			valuesT.put("latitude", -23.80000);
			valuesT.put("longitude", 133.88330);
			valuesT.put("timezone", "9.5;9.5;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Australia");
			valuesT.put("city", "Brisbane");
			valuesT.put("latitude", -27.46670);
			valuesT.put("longitude", 153.03329);
			valuesT.put("timezone", "10.0;10.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Australia");
			valuesT.put("city", "Darwin");
			valuesT.put("latitude", -12.46670);
			valuesT.put("longitude", 130.85001);
			valuesT.put("timezone", "9.5;9.5;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Australia");
			valuesT.put("city", "Melbourne");
			valuesT.put("latitude", -37.81670);
			valuesT.put("longitude", 144.96671);
			valuesT.put("timezone", "10.0;10.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Australia");
			valuesT.put("city", "Perth");
			valuesT.put("latitude", -31.95000);
			valuesT.put("longitude", 115.85000);
			valuesT.put("timezone", "8.0;8.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Australia");
			valuesT.put("city", "Sydney");
			valuesT.put("latitude", -33.86670);
			valuesT.put("longitude", 151.20000);
			valuesT.put("timezone", "10.0;10.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Austria");
			valuesT.put("city", "Vienna");
			valuesT.put("latitude", 48.25000);
			valuesT.put("longitude", 16.36670);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Bahamas");
			valuesT.put("city", "Nassau");
			valuesT.put("latitude", 25.08330);
			valuesT.put("longitude", -77.35000);
			valuesT.put("timezone", "-5.0;-5.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Bahrein");
			valuesT.put("city", "Manama");
			valuesT.put("latitude", 26.21660);
			valuesT.put("longitude", 50.58330);
			valuesT.put("timezone", "3.0;3.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Bangladesh");
			valuesT.put("city", "Chittagong");
			valuesT.put("latitude", 22.35000);
			valuesT.put("longitude", 91.83330);
			valuesT.put("timezone", "6.0;6.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Belgium");
			valuesT.put("city", "Brussels");
			valuesT.put("latitude", 50.80000);
			valuesT.put("longitude", 4.35000);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Belarus");
			valuesT.put("city", "Minsk");
			valuesT.put("latitude", 53.90000);
			valuesT.put("longitude", 27.55000);
			valuesT.put("timezone", "2.0;2.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Bolivia");
			valuesT.put("city", "La Paz");
			valuesT.put("latitude", -16.50000);
			valuesT.put("longitude", -68.15000);
			valuesT.put("timezone", "-4.0;-4.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Brazil");
			valuesT.put("city", "Belem");
			valuesT.put("latitude", -1.45000);
			valuesT.put("longitude", -48.48330);
			valuesT.put("timezone", "-3.0;-3.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Brazil");
			valuesT.put("city", "Brasilia");
			valuesT.put("latitude", -15.86670);
			valuesT.put("longitude", -47.91670);
			valuesT.put("timezone", "-3.0;-3.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Brazil");
			valuesT.put("city", "Porto Alegre");
			valuesT.put("latitude", -30.03330);
			valuesT.put("longitude", -51.21670);
			valuesT.put("timezone", "-3.0;-3.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Brazil");
			valuesT.put("city", "Recife");
			valuesT.put("latitude", -8.06670);
			valuesT.put("longitude", -34.88330);
			valuesT.put("timezone", "-3.0;-3.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Brazil");
			valuesT.put("city", "Rio De Janeiro");
			valuesT.put("latitude", -22.91670);
			valuesT.put("longitude", -43.20000);
			valuesT.put("timezone", "-3.0;-3.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Brazil");
			valuesT.put("city", "Salvador");
			valuesT.put("latitude", -13.00000);
			valuesT.put("longitude", -38.50000);
			valuesT.put("timezone", "-3.0;-3.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Brazil");
			valuesT.put("city", "Sao Paulo");
			valuesT.put("latitude", -23.55000);
			valuesT.put("longitude", -46.63330);
			valuesT.put("timezone", "-3.0;-3.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Belize");
			valuesT.put("city", "Belize");
			valuesT.put("latitude", 17.51670);
			valuesT.put("longitude", -88.18330);
			valuesT.put("timezone", "-5.5;-5.5;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Bulgaria");
			valuesT.put("city", "Sofia");
			valuesT.put("latitude", 42.70000);
			valuesT.put("longitude", 23.33330);
			valuesT.put("timezone", "2.0;2.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Burma");
			valuesT.put("city", "Mandalay");
			valuesT.put("latitude", 21.98330);
			valuesT.put("longitude", 96.10000);
			valuesT.put("timezone", "6.5;6.5;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Burma");
			valuesT.put("city", "Rangoon");
			valuesT.put("latitude", 16.78330);
			valuesT.put("longitude", 96.15000);
			valuesT.put("timezone", "6.5;6.5;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Cambodia");
			valuesT.put("city", "Phnom Penh");
			valuesT.put("latitude", 11.55000);
			valuesT.put("longitude", 104.85000);
			valuesT.put("timezone", "7.0;7.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Canada");
			valuesT.put("city", "Calgary, Al");
			valuesT.put("latitude", 51.10000);
			valuesT.put("longitude", -114.01670);
			valuesT.put("timezone", "-7.0;-7.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Canada");
			valuesT.put("city", "Edmonton, Al");
			valuesT.put("latitude", 53.56670);
			valuesT.put("longitude", -113.51670);
			valuesT.put("timezone", "-7.0;-7.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Canada");
			valuesT.put("city", "Hamilton, On");
			valuesT.put("latitude", 43.26670);
			valuesT.put("longitude", -79.90000);
			valuesT.put("timezone", "-5.0;-5.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Canada");
			valuesT.put("city", "Montreal,Qu");
			valuesT.put("latitude", 45.46670);
			valuesT.put("longitude", -73.75000);
			valuesT.put("timezone", "-5.0;-5.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Canada");
			valuesT.put("city", "Ottawa, On");
			valuesT.put("latitude", 45.31670);
			valuesT.put("longitude", -75.66670);
			valuesT.put("timezone", "-5.0;-5.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Canada");
			valuesT.put("city", "Regina, Sk");
			valuesT.put("latitude", 50.43330);
			valuesT.put("longitude", -104.66670);
			valuesT.put("timezone", "-7.0;-7.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Canada");
			valuesT.put("city", "Thunder Bay, On");
			valuesT.put("latitude", 48.36670);
			valuesT.put("longitude", -89.31670);
			valuesT.put("timezone", "-5.0;-5.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Canada");
			valuesT.put("city", "Toronto, On");
			valuesT.put("latitude", 43.68330);
			valuesT.put("longitude", -79.63330);
			valuesT.put("timezone", "-5.0;-5.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Canada");
			valuesT.put("city", "Vancouver, Bc");
			valuesT.put("latitude", 49.18330);
			valuesT.put("longitude", -123.16670);
			valuesT.put("timezone", "-8.0;-8.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Canada");
			valuesT.put("city", "Winnipeg, Mb");
			valuesT.put("latitude", 49.90000);
			valuesT.put("longitude", -97.23330);
			valuesT.put("timezone", "-6.0;-6.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Chile");
			valuesT.put("city", "Punta Arenas");
			valuesT.put("latitude", -53.16670);
			valuesT.put("longitude", -70.90000);
			valuesT.put("timezone", "-4.0;-4.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Chile");
			valuesT.put("city", "Santiago");
			valuesT.put("latitude", -33.45000);
			valuesT.put("longitude", -70.70000);
			valuesT.put("timezone", "-4.0;-4.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Chile");
			valuesT.put("city", "Valparaiso");
			valuesT.put("latitude", -33.01670);
			valuesT.put("longitude", -71.63330);
			valuesT.put("timezone", "-4.0;-4.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "China");
			valuesT.put("city", "Chongquing");
			valuesT.put("latitude", 29.55000);
			valuesT.put("longitude", 106.55000);
			valuesT.put("timezone", "8.0;8.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "China");
			valuesT.put("city", "Shanghai");
			valuesT.put("latitude", 31.20000);
			valuesT.put("longitude", 121.43330);
			valuesT.put("timezone", "8.0;8.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "China");
			valuesT.put("city", "Beijing");
			valuesT.put("latitude", 39.90400);
			valuesT.put("longitude", 116.40750);
			valuesT.put("timezone", "8.0;8.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Colombia");
			valuesT.put("city", "Bogota");
			valuesT.put("latitude", 4.60000);
			valuesT.put("longitude", -74.08330);
			valuesT.put("timezone", "-5.0;-5.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Colombia");
			valuesT.put("city", "Cali");
			valuesT.put("latitude", 3.41670);
			valuesT.put("longitude", -76.50000);
			valuesT.put("timezone", "-5.0;-5.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Colombia");
			valuesT.put("city", "Medellin");
			valuesT.put("latitude", 6.21670);
			valuesT.put("longitude", -75.60000);
			valuesT.put("timezone", "-5.0;-5.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Congo");
			valuesT.put("city", "Brazzaville");
			valuesT.put("latitude", -4.25000);
			valuesT.put("longitude", 15.25000);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Cuba");
			valuesT.put("city", "Guantanamo Bay");
			valuesT.put("latitude", 19.90000);
			valuesT.put("longitude", -75.15000);
			valuesT.put("timezone", "-5.0;-5.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Cuba");
			valuesT.put("city", "Havana");
			valuesT.put("latitude", 23.13330);
			valuesT.put("longitude", -82.35000);
			valuesT.put("timezone", "-5.0;-5.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Czech Republic");
			valuesT.put("city", "Praha");
			valuesT.put("latitude", 50.08330);
			valuesT.put("longitude", 14.41670);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Czech Republic");
			valuesT.put("city", "Ostrava");
			valuesT.put("latitude", 49.85000);
			valuesT.put("longitude", 18.30000);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Denmark");
			valuesT.put("city", "Copenhagen");
			valuesT.put("latitude", 55.68330);
			valuesT.put("longitude", 12.55000);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Dominican Republic");
			valuesT.put("city", "Santo Domingo");
			valuesT.put("latitude", 18.48330);
			valuesT.put("longitude", -69.90000);
			valuesT.put("timezone", "-5.0;-5.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Equador");
			valuesT.put("city", "Guayaquil");
			valuesT.put("latitude", -21.00000);
			valuesT.put("longitude", -79.88330);
			valuesT.put("timezone", "-5.0;-5.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Equador");
			valuesT.put("city", "Quito");
			valuesT.put("latitude", -0.21670);
			valuesT.put("longitude", -78.53330);
			valuesT.put("timezone", "-5.0;-5.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Egypt");
			valuesT.put("city", "Alexandria");
			valuesT.put("latitude", 31.20010);
			valuesT.put("longitude", 29.91870);
			valuesT.put("timezone", "2.0;2.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Egypt");
			valuesT.put("city", "Assouan");
			valuesT.put("latitude", 24.08890);
			valuesT.put("longitude", 32.89980);
			valuesT.put("timezone", "2.0;2.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Egypt");
			valuesT.put("city", "Cairo");
			valuesT.put("latitude", 30.04440);
			valuesT.put("longitude", 31.23570);
			valuesT.put("timezone", "2.0;2.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "El Salvador");
			valuesT.put("city", "San Salvador");
			valuesT.put("latitude", 13.70000);
			valuesT.put("longitude", -89.21670);
			valuesT.put("timezone", "-6.0;-6.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Ethiopia");
			valuesT.put("city", "Addis Ababa");
			valuesT.put("latitude", 9.0227);
			valuesT.put("longitude", 38.7468);
			valuesT.put("timezone", "3.0;3.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Ethiopia");
			valuesT.put("city", "Asmara");
			valuesT.put("latitude", 15.28330);
			valuesT.put("longitude", 38.91670);
			valuesT.put("timezone", "3.0;3.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Finland");
			valuesT.put("city", "Helsinki");
			valuesT.put("latitude", 60.16670);
			valuesT.put("longitude", 24.95000);
			valuesT.put("timezone", "2.0;2.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "France");
			valuesT.put("city", "Bordeaux");
			valuesT.put("latitude", 44.83780);
			valuesT.put("longitude", -0.57918);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "France");
			valuesT.put("city", "Dijon");
			valuesT.put("latitude", 47.32200);
			valuesT.put("longitude", 5.04148);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "France");
			valuesT.put("city", "Grenoble");
			valuesT.put("latitude", 45.18850);
			valuesT.put("longitude", 5.72452);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "France");
			valuesT.put("city", "Lille");
			valuesT.put("latitude", 50.62930);
			valuesT.put("longitude", 3.05725);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "France");
			valuesT.put("city", "Lyon");
			valuesT.put("latitude", 45.70000);
			valuesT.put("longitude", 4.78330);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "France");
			valuesT.put("city", "Marseille");
			valuesT.put("latitude", 43.30000);
			valuesT.put("longitude", 5.38330);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "France");
			valuesT.put("city", "Metz");
			valuesT.put("latitude", 49.11960);
			valuesT.put("longitude", 6.17690);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "France");
			valuesT.put("city", "Montpellier");
			valuesT.put("latitude", 43.61077);
			valuesT.put("longitude", 3.87670);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "France");
			valuesT.put("city", "Mulhouse");
			valuesT.put("latitude", 47.75084);
			valuesT.put("longitude", 7.33590);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "France");
			valuesT.put("city", "Nancy");
			valuesT.put("latitude", 48.69205);
			valuesT.put("longitude", 6.18440);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "France");
			valuesT.put("city", "Nantes");
			valuesT.put("latitude", 47.25000);
			valuesT.put("longitude", -1.56670);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "France");
			valuesT.put("city", "Nice");
			valuesT.put("latitude", 43.70000);
			valuesT.put("longitude", 7.26670);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "France");
			valuesT.put("city", "Paris");
			valuesT.put("latitude", 48.83570);
			valuesT.put("longitude", 2.33180);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "France");
			valuesT.put("city", "Reims");
			valuesT.put("latitude", 49.25833);
			valuesT.put("longitude", 4.03169);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "France");
			valuesT.put("city", "Rennes");
			valuesT.put("latitude", 48.11347);
			valuesT.put("longitude", -1.67570);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "France");
			valuesT.put("city", "Strasbourg");
			valuesT.put("latitude", 48.58330);
			valuesT.put("longitude", 7.76670);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "France");
			valuesT.put("city", "Toulon");
			valuesT.put("latitude", 43.12423);
			valuesT.put("longitude", 5.92800);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "France");
			valuesT.put("city", "Toulouse");
			valuesT.put("latitude", 43.60465);
			valuesT.put("longitude", 1.44420);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Germany");
			valuesT.put("city", "Berlin");
			valuesT.put("latitude", 52.45000);
			valuesT.put("longitude", 13.30000);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Germany");
			valuesT.put("city", "Hamburg");
			valuesT.put("latitude", 53.55000);
			valuesT.put("longitude", 9.96670);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Germany");
			valuesT.put("city", "Hannover");
			valuesT.put("latitude", 52.40000);
			valuesT.put("longitude", 9.66670);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Germany");
			valuesT.put("city", "Mannheim");
			valuesT.put("latitude", 49.56670);
			valuesT.put("longitude", 8.46670);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Germany");
			valuesT.put("city", "Munich");
			valuesT.put("latitude", 48.15000);
			valuesT.put("longitude", 11.56670);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Georgia");
			valuesT.put("city", "Tbilisi");
			valuesT.put("latitude", 41.71670);
			valuesT.put("longitude", 44.80000);
			valuesT.put("timezone", "4.0;4.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Ghana");
			valuesT.put("city", "Accra");
			valuesT.put("latitude", 5.55000);
			valuesT.put("longitude", -0.20000);
			valuesT.put("timezone", "0.0;0.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Gibraltar");
			valuesT.put("city", "Gibraltar");
			valuesT.put("latitude", 36.15000);
			valuesT.put("longitude", -5.36670);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Greece");
			valuesT.put("city", "Athens");
			valuesT.put("latitude", 37.96670);
			valuesT.put("longitude", 23.71670);
			valuesT.put("timezone", "2.0;2.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Greece");
			valuesT.put("city", "Thessaloniki");
			valuesT.put("latitude", 40.61670);
			valuesT.put("longitude", 22.95000);
			valuesT.put("timezone", "2.0;2.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Greenland");
			valuesT.put("city", "Narsarssuaq");
			valuesT.put("latitude", 61.18330);
			valuesT.put("longitude", -45.41670);
			valuesT.put("timezone", "-1.0;-1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Guatemala");
			valuesT.put("city", "Guatemala City");
			valuesT.put("latitude", 14.61670);
			valuesT.put("longitude", -90.51670);
			valuesT.put("timezone", "-6.0;-6.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Guyana");
			valuesT.put("city", "Georgetown");
			valuesT.put("latitude", 6.83330);
			valuesT.put("longitude", -58.20000);
			valuesT.put("timezone", "-4.0;-4.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Haiti");
			valuesT.put("city", "Port Au Prince");
			valuesT.put("latitude", 18.55000);
			valuesT.put("longitude", -72.33330);
			valuesT.put("timezone", "-5.0;-5.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Honduras");
			valuesT.put("city", "Tegucigalpa");
			valuesT.put("latitude", 14.10000);
			valuesT.put("longitude", -87.21670);
			valuesT.put("timezone", "-6.0;-6.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Hong Kong");
			valuesT.put("city", "Hong Kong");
			valuesT.put("latitude", 22.30000);
			valuesT.put("longitude", 114.16670);
			valuesT.put("timezone", "8.0;8.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Hungary");
			valuesT.put("city", "Budapest");
			valuesT.put("latitude", 47.51670);
			valuesT.put("longitude", 19.03330);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Iceland");
			valuesT.put("city", "Reykjavik");
			valuesT.put("latitude", 64.13330);
			valuesT.put("longitude", 21.93330);
			valuesT.put("timezone", "0.0;0.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "India");
			valuesT.put("city", "Ahmedabad");
			valuesT.put("latitude", 23.03330);
			valuesT.put("longitude", 72.58330);
			valuesT.put("timezone", "5.5;5.5;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "India");
			valuesT.put("city", "Bangalore");
			valuesT.put("latitude", 12.95000);
			valuesT.put("longitude", 77.61670);
			valuesT.put("timezone", "5.5;5.5;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "India");
			valuesT.put("city", "Bombay");
			valuesT.put("latitude", 18.90000);
			valuesT.put("longitude", 72.81670);
			valuesT.put("timezone", "5.5;5.5;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "India");
			valuesT.put("city", "Calcutta");
			valuesT.put("latitude", 22.53330);
			valuesT.put("longitude", 88.33330);
			valuesT.put("timezone", "5.5;5.5;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "India");
			valuesT.put("city", "Madras");
			valuesT.put("latitude", 13.06670);
			valuesT.put("longitude", 80.25000);
			valuesT.put("timezone", "5.5;5.5;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "India");
			valuesT.put("city", "Nagpur");
			valuesT.put("latitude", 21.15000);
			valuesT.put("longitude", 79.11670);
			valuesT.put("timezone", "5.5;5.5;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "India");
			valuesT.put("city", "New Delhi");
			valuesT.put("latitude", 28.58330);
			valuesT.put("longitude", 77.20000);
			valuesT.put("timezone", "5.5;5.5;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Indonesia");
			valuesT.put("city", "Djakarta");
			valuesT.put("latitude", -6.18330);
			valuesT.put("longitude", 106.83330);
			valuesT.put("timezone", "7.5;7.5;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Indonesia");
			valuesT.put("city", "Kupang");
			valuesT.put("latitude", -10.16670);
			valuesT.put("longitude", 123.56670);
			valuesT.put("timezone", "7.5;7.5;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Indonesia");
			valuesT.put("city", "Makassar");
			valuesT.put("latitude", -5.13330);
			valuesT.put("longitude", 119.46670);
			valuesT.put("timezone", "7.5;7.5;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Indonesia");
			valuesT.put("city", "Medan");
			valuesT.put("latitude", 3.58330);
			valuesT.put("longitude", 98.68330);
			valuesT.put("timezone", "7.5;7.5;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Indonesia");
			valuesT.put("city", "Palembang");
			valuesT.put("latitude", -3.00000);
			valuesT.put("longitude", 104.76670);
			valuesT.put("timezone", "7.5;7.5;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Indonesia");
			valuesT.put("city", "Surabaya");
			valuesT.put("latitude", -7.21670);
			valuesT.put("longitude", 112.71670);
			valuesT.put("timezone", "7.5;7.5;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Iran");
			valuesT.put("city", "Abadan");
			valuesT.put("latitude", 30.35000);
			valuesT.put("longitude", 48.26670);
			valuesT.put("timezone", "3.5;3.5;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Iran");
			valuesT.put("city", "Meshed");
			valuesT.put("latitude", 36.28330);
			valuesT.put("longitude", 59.60000);
			valuesT.put("timezone", "3.5;3.5;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Iran");
			valuesT.put("city", "Tehran");
			valuesT.put("latitude", 35.68330);
			valuesT.put("longitude", 51.41670);
			valuesT.put("timezone", "3.5;3.5;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Iran");
			valuesT.put("city", "Qom");
			valuesT.put("latitude", 34.64000);
			valuesT.put("longitude", 50.87640);
			valuesT.put("timezone", "3.5;3.5;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Iraq");
			valuesT.put("city", "Baghdad");
			valuesT.put("latitude", 33.33330);
			valuesT.put("longitude", 44.40000);
			valuesT.put("timezone", "3.0;3.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Iraq");
			valuesT.put("city", "Mosul");
			valuesT.put("latitude", 36.31670);
			valuesT.put("longitude", 43.15000);
			valuesT.put("timezone", "3.0;3.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Iraq");
			valuesT.put("city", "Karbala");
			valuesT.put("latitude", 32.61430);
			valuesT.put("longitude", 44.02480);
			valuesT.put("timezone", "3.0;3.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Iraq");
			valuesT.put("city", "Najaf");
			valuesT.put("latitude", 32.00050);
			valuesT.put("longitude", 44.33080);
			valuesT.put("timezone", "3.0;3.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Ireland");
			valuesT.put("city", "Dublin");
			valuesT.put("latitude", 53.36670);
			valuesT.put("longitude", -6.35000);
			valuesT.put("timezone", "0.0;0.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Ireland");
			valuesT.put("city", "Shannon");
			valuesT.put("latitude", 52.68330);
			valuesT.put("longitude", -8.91670);
			valuesT.put("timezone", "0.0;0.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Israel");
			valuesT.put("city", "Tel Aviv");
			valuesT.put("latitude", 32.10000);
			valuesT.put("longitude", 34.78330);
			valuesT.put("timezone", "2.0;2.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Italy");
			valuesT.put("city", "Milan");
			valuesT.put("latitude", 45.45000);
			valuesT.put("longitude", 9.28330);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Italy");
			valuesT.put("city", "Naples");
			valuesT.put("latitude", 40.88330);
			valuesT.put("longitude", 14.30000);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Italy");
			valuesT.put("city", "Rome");
			valuesT.put("latitude", 41.80000);
			valuesT.put("longitude", 12.60000);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Ivory Coast");
			valuesT.put("city", "Abidjan");
			valuesT.put("latitude", 5.31670);
			valuesT.put("longitude", -4.01670);
			valuesT.put("timezone", "0.0;0.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Japan");
			valuesT.put("city", "Kyoto");
			valuesT.put("latitude", 35.01160);
			valuesT.put("longitude", 135.76801);
			valuesT.put("timezone", "9.0;9.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Japan");
			valuesT.put("city", "Osaka");
			valuesT.put("latitude", 34.69370);
			valuesT.put("longitude", 135.50211);
			valuesT.put("timezone", "9.0;9.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Japan");
			valuesT.put("city", "Tokyo");
			valuesT.put("latitude", 35.68330);
			valuesT.put("longitude", 139.76669);
			valuesT.put("timezone", "9.0;9.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Jordan");
			valuesT.put("city", "Amman");
			valuesT.put("latitude", 31.95000);
			valuesT.put("longitude", 35.95000);
			valuesT.put("timezone", "2.0;2.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Kazakhstan");
			valuesT.put("city", "Almaty");
			valuesT.put("latitude", 43.23330);
			valuesT.put("longitude", 76.88330);
			valuesT.put("timezone", "5.5;5.5;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Kenya");
			valuesT.put("city", "Nairobi");
			valuesT.put("latitude", -1.26670);
			valuesT.put("longitude", 36.80000);
			valuesT.put("timezone", "3.0;3.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Korea");
			valuesT.put("city", "Pyongyang");
			valuesT.put("latitude", 39.03330);
			valuesT.put("longitude", 125.68330);
			valuesT.put("timezone", "9.0;9.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Korea");
			valuesT.put("city", "Seoul");
			valuesT.put("latitude", 37.56670);
			valuesT.put("longitude", 126.96670);
			valuesT.put("timezone", "9.0;9.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Kuwait");
			valuesT.put("city", "Kuwait City");
			valuesT.put("latitude", 29.36970);
			valuesT.put("longitude", 47.97830);
			valuesT.put("timezone", "3.0;3.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Lebanon");
			valuesT.put("city", "Beirut");
			valuesT.put("latitude", 33.90000);
			valuesT.put("longitude", 35.46670);
			valuesT.put("timezone", "2.0;2.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Liberia");
			valuesT.put("city", "Monrovia");
			valuesT.put("latitude", 6.30000);
			valuesT.put("longitude", -10.80000);
			valuesT.put("timezone", "-0.8;-0.8;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Libya");
			valuesT.put("city", "Benghazi");
			valuesT.put("latitude", 32.10000);
			valuesT.put("longitude", 20.06670);
			valuesT.put("timezone", "2.0;2.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Libya");
			valuesT.put("city", "Tripoli");
			valuesT.put("latitude", 32.87620);
			valuesT.put("longitude", 13.18750);
			valuesT.put("timezone", "2.0;2.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Madagascar");
			valuesT.put("city", "Tananarive");
			valuesT.put("latitude", -18.91670);
			valuesT.put("longitude", 47.55000);
			valuesT.put("timezone", "3.0;3.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Malaysia");
			valuesT.put("city", "Kuala Lumpur");
			valuesT.put("latitude", 3.11670);
			valuesT.put("longitude", 101.70000);
			valuesT.put("timezone", "7.5;7.5;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Malaysia");
			valuesT.put("city", "Penang");
			valuesT.put("latitude", 5.41670);
			valuesT.put("longitude", 100.31670);
			valuesT.put("timezone", "7.5;7.5;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Mali");
			valuesT.put("city", "Bamako");
			valuesT.put("latitude", 12.65000);
			valuesT.put("longitude", -8.00000);
			valuesT.put("timezone", "0.0;0.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Mali");
			valuesT.put("city", "Kidal");
			valuesT.put("latitude", 18.44430);
			valuesT.put("longitude", 1.40150);
			valuesT.put("timezone", "0.0;0.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Mali");
			valuesT.put("city", "Segou");
			valuesT.put("latitude", 13.44060);
			valuesT.put("longitude", -6.26530);
			valuesT.put("timezone", "0.0;0.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Mali");
			valuesT.put("city", "Tombouctou");
			valuesT.put("latitude", 16.77530);
			valuesT.put("longitude", -3.00820);
			valuesT.put("timezone", "0.0;0.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Martinique");
			valuesT.put("city", "Fort De France");
			valuesT.put("latitude", 14.61670);
			valuesT.put("longitude", -61.08330);
			valuesT.put("timezone", "-3.0;-3.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Mauritania");
			valuesT.put("city", "Akjoujt");
			valuesT.put("latitude", 19.74730);
			valuesT.put("longitude", -14.39090);
			valuesT.put("timezone", "0.0;0.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Mauritania");
			valuesT.put("city", "Aleg");
			valuesT.put("latitude", 17.05830);
			valuesT.put("longitude", -13.90870);
			valuesT.put("timezone", "0.0;0.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Mauritania");
			valuesT.put("city", "Atar");
			valuesT.put("latitude", 20.53300);
			valuesT.put("longitude", -13.13300);
			valuesT.put("timezone", "0.0;0.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Mauritania");
			valuesT.put("city", "Aioun");
			valuesT.put("latitude", 16.71280);
			valuesT.put("longitude", -9.63730);
			valuesT.put("timezone", "0.0;0.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Mauritania");
			valuesT.put("city", "Kaedi");
			valuesT.put("latitude", 16.20000);
			valuesT.put("longitude", -13.53300);
			valuesT.put("timezone", "0.0;0.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Mauritania");
			valuesT.put("city", "Kiffa");
			valuesT.put("latitude", 16.63300);
			valuesT.put("longitude", -11.46700);
			valuesT.put("timezone", "0.0;0.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Mauritania");
			valuesT.put("city", "Nema");
			valuesT.put("latitude", 16.61620);
			valuesT.put("longitude", -7.26080);
			valuesT.put("timezone", "0.0;0.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Mauritania");
			valuesT.put("city", "Nouadhibou");
			valuesT.put("latitude", 20.90000);
			valuesT.put("longitude", -17.01700);
			valuesT.put("timezone", "0.0;0.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Mauritania");
			valuesT.put("city", "Nouakchott");
			valuesT.put("latitude", 18.15000);
			valuesT.put("longitude", -15.96700);
			valuesT.put("timezone", "0.0;0.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Mauritania");
			valuesT.put("city", "Rosso");
			valuesT.put("latitude", 16.48300);
			valuesT.put("longitude", -15.88300);
			valuesT.put("timezone", "0.0;0.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Mauritania");
			valuesT.put("city", "Selibaby");
			valuesT.put("latitude", 15.15920);
			valuesT.put("longitude", -12.18330);
			valuesT.put("timezone", "0.0;0.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Mauritania");
			valuesT.put("city", "Zouerate");
			valuesT.put("latitude", 22.73300);
			valuesT.put("longitude", -12.35000);
			valuesT.put("timezone", "0.0;0.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Morocco");
			valuesT.put("city", "Casablanca");
			valuesT.put("latitude", 33.53330);
			valuesT.put("longitude", -7.58330);
			valuesT.put("timezone", "0.0;0.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Morocco");
			valuesT.put("city", "Marrakech");
			valuesT.put("latitude", 31.63330);
			valuesT.put("longitude", -8.00000);
			valuesT.put("timezone", "0.0;0.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Morocco");
			valuesT.put("city", "Meknes");
			valuesT.put("latitude", 33.89500);
			valuesT.put("longitude", -5.55470);
			valuesT.put("timezone", "0.0;0.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Morocco");
			valuesT.put("city", "Rabat");
			valuesT.put("latitude", 34.01500);
			valuesT.put("longitude", -6.83270);
			valuesT.put("timezone", "0.0;0.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Mexico");
			valuesT.put("city", "Guadalajara");
			valuesT.put("latitude", 20.68330);
			valuesT.put("longitude", -103.33330);
			valuesT.put("timezone", "-6.0;-6.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Mexico");
			valuesT.put("city", "Merida");
			valuesT.put("latitude", 20.96670);
			valuesT.put("longitude", -89.63330);
			valuesT.put("timezone", "-6.0;-6.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Mexico");
			valuesT.put("city", "Mexico City");
			valuesT.put("latitude", 19.40000);
			valuesT.put("longitude", -99.20000);
			valuesT.put("timezone", "-6.0;-6.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Mexico");
			valuesT.put("city", "Monterrey");
			valuesT.put("latitude", 25.66670);
			valuesT.put("longitude", -100.30000);
			valuesT.put("timezone", "-6.0;-6.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Mexico");
			valuesT.put("city", "Vera Cruz");
			valuesT.put("latitude", 19.20000);
			valuesT.put("longitude", -96.13330);
			valuesT.put("timezone", "-6.0;-6.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Nepal");
			valuesT.put("city", "Katmandu");
			valuesT.put("latitude", 27.70000);
			valuesT.put("longitude", 85.20000);
			valuesT.put("timezone", "5.8;5.8;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Netherlands");
			valuesT.put("city", "Amsterdam");
			valuesT.put("latitude", 52.35000);
			valuesT.put("longitude", 4.90000);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "New Zealand");
			valuesT.put("city", "Auckland");
			valuesT.put("latitude", -36.85000);
			valuesT.put("longitude", 174.76669);
			valuesT.put("timezone", "12.0;12.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "New Zealand");
			valuesT.put("city", "Wellington");
			valuesT.put("latitude", -41.28330);
			valuesT.put("longitude", 174.76669);
			valuesT.put("timezone", "12.0;12.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Nicaragua");
			valuesT.put("city", "Managua");
			valuesT.put("latitude", 12.16670);
			valuesT.put("longitude", -86.25000);
			valuesT.put("timezone", "-6.0;-6.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Niger");
			valuesT.put("city", "Agades");
			valuesT.put("latitude", 16.96660);
			valuesT.put("longitude", 7.98330);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Niger");
			valuesT.put("city", "Niamey");
			valuesT.put("latitude", 13.51270);
			valuesT.put("longitude", 2.11250);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Nigeria");
			valuesT.put("city", "Lagos");
			valuesT.put("latitude", 6.45000);
			valuesT.put("longitude", 3.40000);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Norway");
			valuesT.put("city", "Bergen");
			valuesT.put("latitude", 60.40000);
			valuesT.put("longitude", 5.31670);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Norway");
			valuesT.put("city", "Oslo");
			valuesT.put("latitude", 59.93330);
			valuesT.put("longitude", 10.73330);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Oman");
			valuesT.put("city", "Muscat");
			valuesT.put("latitude", 23.61000);
			valuesT.put("longitude", 58.54000);
			valuesT.put("timezone", "4.0;4.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Pakistan");
			valuesT.put("city", "Karachi");
			valuesT.put("latitude", 24.80000);
			valuesT.put("longitude", 66.98330);
			valuesT.put("timezone", "5.0;5.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Pakistan");
			valuesT.put("city", "Lahore");
			valuesT.put("latitude", 31.58330);
			valuesT.put("longitude", 74.33330);
			valuesT.put("timezone", "5.0;5.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Pakistan");
			valuesT.put("city", "Peshwar");
			valuesT.put("latitude", 34.01670);
			valuesT.put("longitude", 71.58330);
			valuesT.put("timezone", "5.0;5.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Palestine");
			valuesT.put("city", "al-Quds");
			valuesT.put("latitude", 31.76830);
			valuesT.put("longitude", 35.21370);
			valuesT.put("timezone", "2.0;2.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Palestine");
			valuesT.put("city", "Gaza");
			valuesT.put("latitude", 31.52250);
			valuesT.put("longitude", 34.45360);
			valuesT.put("timezone", "2.0;2.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Panama");
			valuesT.put("city", "Panama City");
			valuesT.put("latitude", 8.96670);
			valuesT.put("longitude", -79.55000);
			valuesT.put("timezone", "-5.0;-5.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Papua New Guinea");
			valuesT.put("city", "Port Moresby");
			valuesT.put("latitude", -9.48330);
			valuesT.put("longitude", 147.14999);
			valuesT.put("timezone", "10.0;10.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Paraguay");
			valuesT.put("city", "Ascuncion");
			valuesT.put("latitude", -25.28330);
			valuesT.put("longitude", -57.50000);
			valuesT.put("timezone", "-4.0;-4.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Peru");
			valuesT.put("city", "Lima");
			valuesT.put("latitude", -12.08330);
			valuesT.put("longitude", -77.05000);
			valuesT.put("timezone", "-5.0;-5.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Philippines");
			valuesT.put("city", "Manila");
			valuesT.put("latitude", 14.58330);
			valuesT.put("longitude", 120.98330);
			valuesT.put("timezone", "8.0;8.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Poland");
			valuesT.put("city", "Krakow");
			valuesT.put("latitude", 50.06670);
			valuesT.put("longitude", 19.95000);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Poland");
			valuesT.put("city", "Warsaw");
			valuesT.put("latitude", 52.21670);
			valuesT.put("longitude", 21.03330);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Portugal");
			valuesT.put("city", "Lisbon");
			valuesT.put("latitude", 38.71670);
			valuesT.put("longitude", -9.13330);
			valuesT.put("timezone", "0.0;0.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Puerto Rico");
			valuesT.put("city", "San Juan");
			valuesT.put("latitude", 18.48330);
			valuesT.put("longitude", -66.11670);
			valuesT.put("timezone", "-4.0;-4.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Qatar");
			valuesT.put("city", "Doha");
			valuesT.put("latitude", 25.28020);
			valuesT.put("longitude", 51.52250);
			valuesT.put("timezone", "3.0;3.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Rumania");
			valuesT.put("city", "Bucharest");
			valuesT.put("latitude", 44.41670);
			valuesT.put("longitude", 26.10000);
			valuesT.put("timezone", "2.0;2.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Russia");
			valuesT.put("city", "Archangelsk");
			valuesT.put("latitude", 64.55000);
			valuesT.put("longitude", 40.53330);
			valuesT.put("timezone", "3.0;3.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Russia");
			valuesT.put("city", "Belgorod");
			valuesT.put("latitude", 50.63330);
			valuesT.put("longitude", 36.50000);
			valuesT.put("timezone", "3.0;3.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Russia");
			valuesT.put("city", "Ekaterinburg");
			valuesT.put("latitude", 56.81670);
			valuesT.put("longitude", 60.63330);
			valuesT.put("timezone", "0.0;0.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Russia");
			valuesT.put("city", "Kaliningrad");
			valuesT.put("latitude", 54.71670);
			valuesT.put("longitude", 20.50000);
			valuesT.put("timezone", "3.0;3.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Russia");
			valuesT.put("city", "Krasnoyarsk");
			valuesT.put("latitude", 56.01670);
			valuesT.put("longitude", 92.95000);
			valuesT.put("timezone", "6.0;6.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Russia");
			valuesT.put("city", "Moscow");
			valuesT.put("latitude", 55.76670);
			valuesT.put("longitude", 37.66670);
			valuesT.put("timezone", "3.0;3.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Russia");
			valuesT.put("city", "Petropavlovsk");
			valuesT.put("latitude", 52.88330);
			valuesT.put("longitude", 158.70000);
			valuesT.put("timezone", "12.0;12.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Russia");
			valuesT.put("city", "Rostov On Don");
			valuesT.put("latitude", 47.21670);
			valuesT.put("longitude", 39.71670);
			valuesT.put("timezone", "3.0;3.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Russia");
			valuesT.put("city", "Samara");
			valuesT.put("latitude", 53.18330);
			valuesT.put("longitude", 50.10000);
			valuesT.put("timezone", "3.0;3.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Russia");
			valuesT.put("city", "St.Petersburg");
			valuesT.put("latitude", 59.93330);
			valuesT.put("longitude", 30.26670);
			valuesT.put("timezone", "3.0;3.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Russia");
			valuesT.put("city", "Vladivostok");
			valuesT.put("latitude", 43.11670);
			valuesT.put("longitude", 131.91670);
			valuesT.put("timezone", "10.0;10.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Russia");
			valuesT.put("city", "Volgograd");
			valuesT.put("latitude", 48.70000);
			valuesT.put("longitude", 44.51670);
			valuesT.put("timezone", "3.0;3.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Russia");
			valuesT.put("city", "Voronezh");
			valuesT.put("latitude", 51.66800);
			valuesT.put("longitude", 39.19510);
			valuesT.put("timezone", "3.0;3.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Saudi Arabia");
			valuesT.put("city", "Dhahran");
			valuesT.put("latitude", 26.28330);
			valuesT.put("longitude", 50.15000);
			valuesT.put("timezone", "3.0;3.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Saudi Arabia");
			valuesT.put("city", "Jeddah");
			valuesT.put("latitude", 21.46670);
			valuesT.put("longitude", 39.16670);
			valuesT.put("timezone", "3.0;3.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Saudi Arabia");
			valuesT.put("city", "Makkah");
			valuesT.put("latitude", 21.41660);
			valuesT.put("longitude", 39.81660);
			valuesT.put("timezone", "3.0;3.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Saudi Arabia");
			valuesT.put("city", "Medina");
			valuesT.put("latitude", 24.46090);
			valuesT.put("longitude", 39.62010);
			valuesT.put("timezone", "3.0;3.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Saudi Arabia");
			valuesT.put("city", "Qatif");
			valuesT.put("latitude", 26.55900);
			valuesT.put("longitude", 49.99560);
			valuesT.put("timezone", "3.0;3.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Saudi Arabia");
			valuesT.put("city", "Riyadh");
			valuesT.put("latitude", 24.65000);
			valuesT.put("longitude", 46.70000);
			valuesT.put("timezone", "3.0;3.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Senegal");
			valuesT.put("city", "Dakar");
			valuesT.put("latitude", 14.70000);
			valuesT.put("longitude", -17.48330);
			valuesT.put("timezone", "0.0;0.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Senegal");
			valuesT.put("city", "Kaolack");
			valuesT.put("latitude", 14.01960);
			valuesT.put("longitude", -16.06400);
			valuesT.put("timezone", "0.0;0.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Senegal");
			valuesT.put("city", "Louga");
			valuesT.put("latitude", 15.61470);
			valuesT.put("longitude", -16.24560);
			valuesT.put("timezone", "0.0;0.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Senegal");
			valuesT.put("city", "St Louis");
			valuesT.put("latitude", 16.02460);
			valuesT.put("longitude", -16.48950);
			valuesT.put("timezone", "0.0;0.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Singapore");
			valuesT.put("city", "Singapore");
			valuesT.put("latitude", 1.30000);
			valuesT.put("longitude", 103.83330);
			valuesT.put("timezone", "7.5;7.5;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Slovakia");
			valuesT.put("city", "Bratislava");
			valuesT.put("latitude", 48.16670);
			valuesT.put("longitude", 17.13330);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Somalia");
			valuesT.put("city", "Mogadiscio");
			valuesT.put("latitude", 2.03330);
			valuesT.put("longitude", 49.31670);
			valuesT.put("timezone", "3.0;3.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "South Africa");
			valuesT.put("city", "Cape Town");
			valuesT.put("latitude", -33.93330);
			valuesT.put("longitude", 18.48330);
			valuesT.put("timezone", "2.0;2.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "South Africa");
			valuesT.put("city", "Johannesburg");
			valuesT.put("latitude", -26.18330);
			valuesT.put("longitude", 28.05000);
			valuesT.put("timezone", "2.0;2.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "South Africa");
			valuesT.put("city", "Pretoria");
			valuesT.put("latitude", -25.75000);
			valuesT.put("longitude", 28.23330);
			valuesT.put("timezone", "2.0;2.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Spain");
			valuesT.put("city", "Barcelona");
			valuesT.put("latitude", 41.40000);
			valuesT.put("longitude", 2.15000);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Spain");
			valuesT.put("city", "Madrid");
			valuesT.put("latitude", 40.41670);
			valuesT.put("longitude", -3.68330);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Spain");
			valuesT.put("city", "Valencia");
			valuesT.put("latitude", 39.46670);
			valuesT.put("longitude", -0.38330);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Sri Lanka");
			valuesT.put("city", "Colombo");
			valuesT.put("latitude", 6.90000);
			valuesT.put("longitude", 79.86670);
			valuesT.put("timezone", "5.5;5.5;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Sudan");
			valuesT.put("city", "Khartoum");
			valuesT.put("latitude", 15.61670);
			valuesT.put("longitude", 32.55000);
			valuesT.put("timezone", "2.0;2.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Surinam");
			valuesT.put("city", "Paramaribo");
			valuesT.put("latitude", 5.81670);
			valuesT.put("longitude", -55.15000);
			valuesT.put("timezone", "-3.5;-3.5;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Sweden");
			valuesT.put("city", "Stockholm");
			valuesT.put("latitude", 59.35000);
			valuesT.put("longitude", 18.06670);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Switzerland");
			valuesT.put("city", "Zurich");
			valuesT.put("latitude", 47.38330);
			valuesT.put("longitude", 8.55000);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Syria");
			valuesT.put("city", "Damascus");
			valuesT.put("latitude", 33.50000);
			valuesT.put("longitude", 36.33330);
			valuesT.put("timezone", "2.0;2.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Taiwan");
			valuesT.put("city", "Tainan");
			valuesT.put("latitude", 22.95000);
			valuesT.put("longitude", 120.20000);
			valuesT.put("timezone", "8.0;8.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Taiwan");
			valuesT.put("city", "Taipei");
			valuesT.put("latitude", 25.03330);
			valuesT.put("longitude", 121.51670);
			valuesT.put("timezone", "8.0;8.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Tanzania");
			valuesT.put("city", "Dar Es Salaam");
			valuesT.put("latitude", -6.83330);
			valuesT.put("longitude", 39.30000);
			valuesT.put("timezone", "3.0;3.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Tchad");
			valuesT.put("city", "Ndjamena");
			valuesT.put("latitude", 12.10480);
			valuesT.put("longitude", 15.04450);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Thailand");
			valuesT.put("city", "Bangkok");
			valuesT.put("latitude", 13.73330);
			valuesT.put("longitude", 100.50000);
			valuesT.put("timezone", "7.0;7.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Trinidad");
			valuesT.put("city", "Port Of Spain");
			valuesT.put("latitude", 10.66670);
			valuesT.put("longitude", -61.51670);
			valuesT.put("timezone", "-4.0;-4.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Tunisia");
			valuesT.put("city", "Tunis");
			valuesT.put("latitude", 36.78330);
			valuesT.put("longitude", 10.20000);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Turkey");
			valuesT.put("city", "Adana");
			valuesT.put("latitude", 36.98330);
			valuesT.put("longitude", 35.30000);
			valuesT.put("timezone", "2.0;2.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Turkey");
			valuesT.put("city", "Ankara");
			valuesT.put("latitude", 39.95000);
			valuesT.put("longitude", 32.88330);
			valuesT.put("timezone", "2.0;2.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Turkey");
			valuesT.put("city", "Istanbul");
			valuesT.put("latitude", 40.96670);
			valuesT.put("longitude", 28.83330);
			valuesT.put("timezone", "2.0;2.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Turkey");
			valuesT.put("city", "Izmir");
			valuesT.put("latitude", 38.43330);
			valuesT.put("longitude", 27.16670);
			valuesT.put("timezone", "2.0;2.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Ukraine");
			valuesT.put("city", "Kiev");
			valuesT.put("latitude", 50.45000);
			valuesT.put("longitude", 30.50000);
			valuesT.put("timezone", "2.0;2.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Ukraine");
			valuesT.put("city", "Kharkov");
			valuesT.put("latitude", 50.00000);
			valuesT.put("longitude", 36.23330);
			valuesT.put("timezone", "2.0;2.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Ukraine");
			valuesT.put("city", "Odessa");
			valuesT.put("latitude", 46.48330);
			valuesT.put("longitude", 30.73330);
			valuesT.put("timezone", "2.0;2.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "United Arab Emirates");
			valuesT.put("city", "Abu Dhabi");
			valuesT.put("latitude", 24.46660);
			valuesT.put("longitude", 54.36660);
			valuesT.put("timezone", "4.0;4.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "United Arab Emirates");
			valuesT.put("city", "Dubai");
			valuesT.put("latitude", 25.27110);
			valuesT.put("longitude", 55.30750);
			valuesT.put("timezone", "4.0;4.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "United Kingdom");
			valuesT.put("city", "Belfast");
			valuesT.put("latitude", 54.6000);
			valuesT.put("longitude", -5.9301);
			valuesT.put("timezone", "0.0;0.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "United Kingdom");
			valuesT.put("city", "Birmingham");
			valuesT.put("latitude", 52.48330);
			valuesT.put("longitude", -1.93330);
			valuesT.put("timezone", "0.0;0.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "United Kingdom");
			valuesT.put("city", "Cardiff");
			valuesT.put("latitude", 51.46670);
			valuesT.put("longitude", -3.16670);
			valuesT.put("timezone", "0.0;0.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "United Kingdom");
			valuesT.put("city", "Edinburgh");
			valuesT.put("latitude", 55.91670);
			valuesT.put("longitude", -3.18330);
			valuesT.put("timezone", "0.0;0.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "United Kingdom");
			valuesT.put("city", "Glasgow");
			valuesT.put("latitude", 55.86670);
			valuesT.put("longitude", -4.28330);
			valuesT.put("timezone", "0.0;0.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "United Kingdom");
			valuesT.put("city", "London");
			valuesT.put("latitude", 51.48330);
			valuesT.put("longitude", 0.00000);
			valuesT.put("timezone", "0.0;0.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "United States");
			valuesT.put("city", "Chicago, Il");
			valuesT.put("latitude", 41.78330);
			valuesT.put("longitude", -87.75000);
			valuesT.put("timezone", "-6.0;-6.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "United States");
			valuesT.put("city", "Cincinnati, Oh");
			valuesT.put("latitude", 39.15000);
			valuesT.put("longitude", -84.51670);
			valuesT.put("timezone", "-5.0;-5.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "United States");
			valuesT.put("city", "Denver, Co");
			valuesT.put("latitude", 39.75000);
			valuesT.put("longitude", -104.86670);
			valuesT.put("timezone", "-7.0;-7.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "United States");
			valuesT.put("city", "Detroit, Mi");
			valuesT.put("latitude", 42.41670);
			valuesT.put("longitude", -83.01670);
			valuesT.put("timezone", "-5.0;-5.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "United States");
			valuesT.put("city", "Houston, Tx");
			valuesT.put("latitude", 29.96670);
			valuesT.put("longitude", -95.35000);
			valuesT.put("timezone", "-6.0;-6.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "United States");
			valuesT.put("city", "Jacksonville, Fl");
			valuesT.put("latitude", 30.50000);
			valuesT.put("longitude", -81.70000);
			valuesT.put("timezone", "-5.0;-5.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "United States");
			valuesT.put("city", "Kansas City, Ks");
			valuesT.put("latitude", 39.11670);
			valuesT.put("longitude", -94.63330);
			valuesT.put("timezone", "-6.0;-6.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "United States");
			valuesT.put("city", "Los Angeles, Ca");
			valuesT.put("latitude", 33.93330);
			valuesT.put("longitude", -118.40000);
			valuesT.put("timezone", "-8.0;-8.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "United States");
			valuesT.put("city", "Miami, Fl");
			valuesT.put("latitude", 25.80000);
			valuesT.put("longitude", -80.26670);
			valuesT.put("timezone", "-5.0;-5.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "United States");
			valuesT.put("city", "New Orleans, La");
			valuesT.put("latitude", 29.98330);
			valuesT.put("longitude", -90.25000);
			valuesT.put("timezone", "-6.0;-6.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "United States");
			valuesT.put("city", "New York, Ny");
			valuesT.put("latitude", 40.78330);
			valuesT.put("longitude", -73.96670);
			valuesT.put("timezone", "-5.0;-5.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "United States");
			valuesT.put("city", "Norfolk, Va");
			valuesT.put("latitude", 36.90000);
			valuesT.put("longitude", -76.20000);
			valuesT.put("timezone", "-5.0;-5.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "United States");
			valuesT.put("city", "Philadelphia, Pa");
			valuesT.put("latitude", 39.88330);
			valuesT.put("longitude", -75.25000);
			valuesT.put("timezone", "-5.0;-5.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "United States");
			valuesT.put("city", "Portland, Or");
			valuesT.put("latitude", 45.60000);
			valuesT.put("longitude", -122.60000);
			valuesT.put("timezone", "-8.0;-8.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "United States");
			valuesT.put("city", "San Francisco, Ca");
			valuesT.put("latitude", 37.61670);
			valuesT.put("longitude", -122.38330);
			valuesT.put("timezone", "-8.0;-8.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "United States");
			valuesT.put("city", "Seattle, Wa");
			valuesT.put("latitude", 47.45000);
			valuesT.put("longitude", -122.30000);
			valuesT.put("timezone", "-8.0;-8.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "United States");
			valuesT.put("city", "St. Louis, Ms");
			valuesT.put("latitude", 38.75000);
			valuesT.put("longitude", -90.38330);
			valuesT.put("timezone", "-6.0;-6.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "United States");
			valuesT.put("city", "Washington, Dc");
			valuesT.put("latitude", 38.85000);
			valuesT.put("longitude", -77.03330);
			valuesT.put("timezone", "-5.0;-5.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Uruguay");
			valuesT.put("city", "Montevideo");
			valuesT.put("latitude", -34.85000);
			valuesT.put("longitude", -56.21670);
			valuesT.put("timezone", "-3.0;-3.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Uzbekistan");
			valuesT.put("city", "Tashkent");
			valuesT.put("latitude", 41.33330);
			valuesT.put("longitude", 69.30000);
			valuesT.put("timezone", "6.0;6.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Venezuela");
			valuesT.put("city", "Caracas");
			valuesT.put("latitude", 10.50000);
			valuesT.put("longitude", -66.93330);
			valuesT.put("timezone", "-4.0;-4.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Venezuela");
			valuesT.put("city", "Maracaibo");
			valuesT.put("latitude", 10.65000);
			valuesT.put("longitude", -71.60000);
			valuesT.put("timezone", "-4.0;-4.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Vietnam");
			valuesT.put("city", "Hanoi");
			valuesT.put("latitude", 21.03330);
			valuesT.put("longitude", 105.86670);
			valuesT.put("timezone", "7.0;7.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Vietnam");
			valuesT.put("city", "Ho Chi Minh City");
			valuesT.put("latitude", 10.78330);
			valuesT.put("longitude", 106.70000);
			valuesT.put("timezone", "7.0;7.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Yemen");
			valuesT.put("city", "Aden");
			valuesT.put("latitude", 12.83330);
			valuesT.put("longitude", 45.03330);
			valuesT.put("timezone", "3.0;3.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Yemen");
			valuesT.put("city", "Saada");
			valuesT.put("latitude", 16.94320);
			valuesT.put("longitude", 43.76530);
			valuesT.put("timezone", "3.0;3.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Yemen");
			valuesT.put("city", "Sanaa");
			valuesT.put("latitude", 15.35200);
			valuesT.put("longitude", 44.20740);
			valuesT.put("timezone", "3.0;3.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Yugoslavia");
			valuesT.put("city", "Belgrade");
			valuesT.put("latitude", 44.80000);
			valuesT.put("longitude", 20.46670);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Zaire");
			valuesT.put("city", "Kinshasa");
			valuesT.put("latitude", -4.33330);
			valuesT.put("longitude", 15.30000);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			valuesT.clear();
			valuesT.put("country", "Zaire");
			valuesT.put("city", "Kisangani");
			valuesT.put("latitude", 0.5193);
			valuesT.put("longitude", 25.1961);
			valuesT.put("timezone", "1.0;1.0;TZ");
			valuesT.put("zipcode", 0);
			valuesT.put("flag", 0);
			valuesT.put("note", "");
			dbCity.insert(TBL_CITY, "note", valuesT);

			dbCity.close();
			dbCity = null;

			return true;
		}
		catch(Throwable thT) {
			if (dbCity != null) {
				dbCity.close();
				dbCity = null;
			}
			return false;
		}

	}

}
