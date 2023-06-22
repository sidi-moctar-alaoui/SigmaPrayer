//	-------------------------------------------------------------
//	SigmaPrayer for Android
//  Placed in the public domain:
//      It may be freely reproduced, distributed, transmitted, used, modified,
//      built upon, or otherwise exploited by anyone for any purpose, commercial or non-commercial,
//      and in any way
//  sidi.moctar.alaoui@gmail.com
//	-------------------------------------------------------------

package cc.sidi.SigmaPrayer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;

import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;

@SuppressLint("DefaultLocale")
public class SigmaSettings extends Activity implements View.OnClickListener
{
	public static final long MAXFILELENGTH = 8388608L;

	public static final int SCRIPT_MAXCHARS_OUT = 2048;
	public static final float FONTSIZE_MIN = 8f;
	public static final float FONTSIZE_SMALL = 12f;
	public static final float FONTSIZE_MEDIUM = 14f;
	public static final float FONTSIZE_BIG = 20f;
	public static final float FONTSIZE_MAX = 48f;

	public static final int FONTCOLOR = 0xFFF0F0F0;
	public static final int FONTCOLOR_LIGHT = 0xFFFFFFFF;
	public static final int FONTCOLOR_DISABLED = 0xFF6A6A6A;
	public static final int FONTCOLOR_LOWLIGHT = 0xFFC0C0C0;
	public static final int FONTCOLOR_HIGHLIGHT = 0xFF80FF80;
	public static final int FONTCOLOR_TIMEZONE = 0xFF4646C8;
	public static final int[] TOAST_COLORS = { 0xFFF0F0F0, 0xFFFFC8C8 };

	public static int MAX_COUNT = 512;

	private Button m_btnMenu = null;
	private TextView m_txtTitle = null;

	private List<String> m_listMenu = null;
	private String m_strSelectedMenuItem = "";

	private Dialog m_Dialog = null;

	private SQLiteDatabase m_dbCity = null;
	private Cursor m_cursorT = null;

	private boolean m_bTimeformat = true;

	private Button m_btnCity = null;
	private EditText m_edtCountry = null;
	private EditText m_edtCity = null;
	private EditText m_edtLatitude = null;
	private EditText m_edtLongitude = null;
	private EditText m_edtTimezone = null;
	private TextView m_txtTime1 = null;
	private TextView m_txtTime3 = null;
	private TextView m_txtTime4 = null;
	private TextView m_txtTime6 = null;
	private TextView m_txtTime7 = null;
	private TextView m_txtCalendar = null;
	private TextView m_txtCalendar_AR = null;

	private Button m_btnCalcMethod = null;
	private CheckBox m_optAlarmTime1 = null;
	private CheckBox m_optAlarmTime3 = null;
	private CheckBox m_optAlarmTime4 = null;
	private CheckBox m_optAlarmTime6 = null;
	private CheckBox m_optAlarmTime7 = null;
	private CheckBox m_optTimeformat = null;
	private CheckBox m_optTimezoneAuto = null;

	private String m_strSelectedCity = null;
	private List<String> m_arrCity = null;

	private List<String> m_arrMethod = null;
	private int m_iCalcMethod = PrayerTimes.CALCMETHOD_ISNA;

	private boolean m_bAlarmTime1 = true;
	private boolean m_bAlarmTime3 = true;
	private boolean m_bAlarmTime4 = true;
	private boolean m_bAlarmTime6 = true;
	private boolean m_bAlarmTime7 = true;
	private boolean m_bTimezoneAuto = true;

	private Dialog m_dlgHelp = null;
	private boolean m_bHelpShown = false;

	private Dialog m_dlgCityList = null;
	private boolean m_bCityListShown = false;
	private ListView m_listCity = null;

	private ArrayList<HashMap<String, String>> m_arrTimes = null;
	private Dialog m_dlgTimesList = null;
	private boolean m_bTimesListShown = false;
	private ListView m_listTimes = null;

	private Dialog m_dlgMethodList = null;
	private boolean m_bMethodListShown = false;
	private ListView m_listMethod = null;

	public void clearListCity()
	{
		if (m_arrCity != null) {
			m_arrCity.clear();
		}
	}

	public void addToListCity(String strCity)
	{
		if (m_arrCity != null) {
			m_arrCity.add(strCity);
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.settings);

		setTitle(R.string.strActivityTitle);

		m_listMenu = new ArrayList<String>();
		m_listMenu.clear();
		m_listMenu.add(getResources().getString(R.string.strTimesList));
		m_listMenu.add(getResources().getString(R.string.strHelp));
		m_listMenu.add(getResources().getString(R.string.strExit));

		m_btnMenu = (Button) findViewById(R.id.btnMenu);
		m_btnMenu.setBackgroundResource(R.drawable.titlebar);
		m_btnMenu.setTextColor(SigmaApp.MENUCOLOR);
		m_btnMenu.setText(R.string.strMenu);
		m_btnMenu.setTypeface(Typeface.DEFAULT_BOLD);
		m_btnMenu.setTextSize(SigmaApp.FONTSIZE_MEDIUM);
		m_btnMenu.setShadowLayer(1f, 1f, 1f, Color.GRAY);
		m_btnMenu.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View viewT)
			{
				showMenu();
			}
		});

		m_txtTitle = (TextView) findViewById(R.id.txtTitleBar);
		m_txtTitle.setBackgroundResource(R.drawable.titlebar);
		m_txtTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_title, 0, 0, 0);
		m_txtTitle.setTypeface(Typeface.DEFAULT_BOLD);
		m_txtTitle.setTextSize(SigmaApp.FONTSIZE_MEDIUM);
		m_txtTitle.setShadowLayer(1f, 1f, 1f, Color.GRAY);

		TextView txtT = (TextView) (findViewById(R.id.txtHeader_AR));
		txtT.setBackgroundColor(Color.TRANSPARENT);
		txtT.setTextColor(FONTCOLOR);
		txtT.setTextSize(TypedValue.COMPLEX_UNIT_SP, FONTSIZE_BIG);
		txtT.setTypeface(Typeface.DEFAULT_BOLD, Typeface.BOLD);
		String strT = getString(R.string.strAyatSalat_AR);
		txtT.setText(ArabicReshaper.ARABIC_RESHAPE ? ArabicHelper.reshape(strT) : strT);
		txtT.setGravity(Gravity.CENTER);

		m_btnCity = (Button) (findViewById(R.id.btnCity));
		m_btnCity.setBackgroundResource(R.drawable.buttonstyle);
		m_btnCity.setLines(1);
		m_btnCity.setTextColor(FONTCOLOR);
		m_btnCity.setTypeface(Typeface.DEFAULT_BOLD, Typeface.BOLD);
		m_btnCity.setTextSize(TypedValue.COMPLEX_UNIT_SP, FONTSIZE_BIG);
		m_btnCity.setOnClickListener(this);

		m_txtCalendar = (TextView) (findViewById(R.id.txtCalendar));
		m_txtCalendar.setBackgroundResource(R.drawable.viewstyle);
		m_txtCalendar.setTextColor(FONTCOLOR);
		m_txtCalendar.setTextSize(TypedValue.COMPLEX_UNIT_SP, FONTSIZE_MEDIUM);
		m_txtCalendar.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
		m_txtCalendar.setGravity(Gravity.LEFT);

		m_txtCalendar_AR = (TextView) (findViewById(R.id.txtCalendar_AR));
		m_txtCalendar_AR.setBackgroundResource(R.drawable.viewstyle);
		m_txtCalendar_AR.setTextColor(FONTCOLOR);
		m_txtCalendar_AR.setTextSize(TypedValue.COMPLEX_UNIT_SP, FONTSIZE_MEDIUM);
		m_txtCalendar_AR.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
		m_txtCalendar_AR.setGravity(Gravity.RIGHT);

		m_txtTime1 = (TextView) (findViewById(R.id.txtTime1));
		m_txtTime3 = (TextView) (findViewById(R.id.txtTime3));
		m_txtTime4 = (TextView) (findViewById(R.id.txtTime4));
		m_txtTime6 = (TextView) (findViewById(R.id.txtTime6));
		m_txtTime7 = (TextView) (findViewById(R.id.txtTime7));

		int ii;

		int idT[] = { R.id.txtCountryLabel, R.id.txtCountryLabel_AR, R.id.txtCityLabel, R.id.txtCityLabel_AR, R.id.txtLatitudeLabel,
			R.id.txtLatitudeLabel_AR, R.id.txtLongitudeLabel, R.id.txtLongitudeLabel_AR, R.id.txtTimezoneLabel, R.id.txtTimeformatLabel_AR,
			R.id.txtTimeformatLabel, R.id.txtTimezoneLabel_AR, R.id.txtTimezoneAutoLabel, R.id.txtTimezoneAutoLabel_AR,
			R.id.txtAlarmFileLabel, R.id.txtAlarmFileLabel_AR, R.id.txtCalcMethod, R.id.txtCalcMethod_AR, R.id.txtTime1, R.id.txtTime3,
			R.id.txtTime4, R.id.txtTime6, R.id.txtTime7 };
		for (ii = 0; ii < idT.length; ii++) {
			txtT = (TextView) (findViewById(idT[ii]));
			txtT.setBackgroundResource(R.drawable.viewstyle);
			txtT.setTextColor(FONTCOLOR);
			txtT.setTextSize(TypedValue.COMPLEX_UNIT_SP, FONTSIZE_MEDIUM);
			txtT.setTypeface(Typeface.DEFAULT_BOLD, Typeface.BOLD);
			if (ii >= 18) {
				txtT.setGravity(Gravity.CENTER);
			}
			else if ((ii == 14) || (ii == 15)) {
				txtT.setGravity((ii % 2) == 0 ? Gravity.LEFT : Gravity.RIGHT);
			}
			else {
				txtT.setGravity((ii % 2) == 0 ? Gravity.LEFT : Gravity.RIGHT);
			}
		}

		txtT = (TextView) (findViewById(R.id.txtCountryLabel_AR));
		strT = getString(R.string.strCountry_AR);
		txtT.setText(ArabicReshaper.ARABIC_RESHAPE ? ArabicHelper.reshape(strT) : strT);

		txtT = (TextView) (findViewById(R.id.txtCityLabel_AR));
		strT = getString(R.string.strCity_AR);
		txtT.setText(ArabicReshaper.ARABIC_RESHAPE ? ArabicHelper.reshape(strT) : strT);

		txtT = (TextView) (findViewById(R.id.txtLatitudeLabel_AR));
		strT = getString(R.string.strLatitude_AR);
		txtT.setText(ArabicReshaper.ARABIC_RESHAPE ? ArabicHelper.reshape(strT) : strT);

		txtT = (TextView) (findViewById(R.id.txtLongitudeLabel_AR));
		strT = getString(R.string.strLongitude_AR);
		txtT.setText(ArabicReshaper.ARABIC_RESHAPE ? ArabicHelper.reshape(strT) : strT);

		txtT = (TextView) (findViewById(R.id.txtTimezoneLabel_AR));
		strT = getString(R.string.strTimezone_AR);
		txtT.setText(ArabicReshaper.ARABIC_RESHAPE ? ArabicHelper.reshape(strT) : strT);

		txtT = (TextView) (findViewById(R.id.txtAlarmFileLabel_AR));
		strT = getString(R.string.strAlarmFile_AR);
		txtT.setText(ArabicReshaper.ARABIC_RESHAPE ? ArabicHelper.reshape(strT) : strT);

		txtT = (TextView) (findViewById(R.id.txtCalcMethod_AR));
		strT = getString(R.string.strCalcMethod_AR);
		txtT.setText(ArabicReshaper.ARABIC_RESHAPE ? ArabicHelper.reshape(strT) : strT);

		txtT = (TextView) (findViewById(R.id.txtTimeformatLabel_AR));
		strT = getString(R.string.strTimeformat_AR);
		txtT.setText(ArabicReshaper.ARABIC_RESHAPE ? ArabicHelper.reshape(strT) : strT);

		m_edtCountry = (EditText) (findViewById(R.id.edtCountry));
		m_edtCity = (EditText) (findViewById(R.id.edtCity));
		m_edtLatitude = (EditText) (findViewById(R.id.edtLatitude));
		m_edtLongitude = (EditText) (findViewById(R.id.edtLongitude));
		m_edtTimezone = (EditText) (findViewById(R.id.edtTimezone));

		EditText idE[] = { m_edtCountry, m_edtCity, m_edtLatitude, m_edtLongitude, m_edtTimezone };
		for (ii = 0; ii < idE.length; ii++) {
			idE[ii].setBackgroundResource(R.drawable.editstyle);
			idE[ii].setTextColor(FONTCOLOR);
			idE[ii].setTextSize(TypedValue.COMPLEX_UNIT_SP, FONTSIZE_MEDIUM);
			idE[ii].setTypeface(Typeface.DEFAULT_BOLD, Typeface.BOLD);
			idE[ii].setGravity(Gravity.CENTER);
			if (ii >= 2) {
				idE[ii]
					.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
			}
			else {
				idE[ii].setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
			}
		}

		m_optAlarmTime1 = (CheckBox) (findViewById(R.id.optAlarmTime1));
		m_optAlarmTime3 = (CheckBox) (findViewById(R.id.optAlarmTime3));
		m_optAlarmTime4 = (CheckBox) (findViewById(R.id.optAlarmTime4));
		m_optAlarmTime6 = (CheckBox) (findViewById(R.id.optAlarmTime6));
		m_optAlarmTime7 = (CheckBox) (findViewById(R.id.optAlarmTime7));
		m_optTimeformat = (CheckBox) (findViewById(R.id.optTimeformat));
		m_optTimezoneAuto = (CheckBox) (findViewById(R.id.optTimezoneAuto));
		CheckBox idC[] = { m_optAlarmTime1, m_optAlarmTime3, m_optAlarmTime4, m_optAlarmTime6, m_optAlarmTime7, m_optTimeformat,
			m_optTimezoneAuto };
		for (ii = 0; ii < idC.length; ii++) {
			idC[ii].setLines(1);
			idC[ii].setText("");
			idC[ii].setGravity(Gravity.CENTER);

			idC[ii].setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View tView)
				{
					try {
						updateData();

						Button btnAlarm = (Button) (findViewById(R.id.btnAlarm));
						btnAlarm.setEnabled((m_bAlarmTime1 == true) || (m_bAlarmTime3 == true) || (m_bAlarmTime4 == true)
							|| (m_bAlarmTime6 == true) || (m_bAlarmTime7 == true));
							m_bTimezoneAuto = m_optTimezoneAuto.isChecked();
							m_edtTimezone.setEnabled(m_bTimezoneAuto == false);
					}
					catch (Throwable thT) {

					}
				}
			});
		}

		txtT = (TextView) (findViewById(R.id.txtAlarmFilename));
		txtT.setText("");
		txtT.setTextColor(Color.LTGRAY);
		txtT.setTextSize(TypedValue.COMPLEX_UNIT_SP, FONTSIZE_SMALL);
		txtT.setTypeface(Typeface.DEFAULT, Typeface.ITALIC);
		txtT.setGravity(Gravity.CENTER);

		Button btnAlarm = (Button) (findViewById(R.id.btnAlarm));
		btnAlarm.setBackgroundResource(R.drawable.buttonstyle);
		btnAlarm.setLines(1);
		btnAlarm.setTextColor(FONTCOLOR);
		btnAlarm.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
		btnAlarm.setTextSize(TypedValue.COMPLEX_UNIT_SP, FONTSIZE_MEDIUM);
		btnAlarm.setOnClickListener(this);

		Button btnUpdateCity = (Button) (findViewById(R.id.btnUpdateCity));
		btnUpdateCity.setBackgroundResource(R.drawable.buttonstyle);
		btnUpdateCity.setLines(1);
		btnUpdateCity.setTextColor(FONTCOLOR);
		btnUpdateCity.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
		btnUpdateCity.setTextSize(TypedValue.COMPLEX_UNIT_SP, FONTSIZE_MEDIUM);
		btnUpdateCity.setText(ArabicReshaper.ARABIC_RESHAPE ? getString(R.string.strUpdateCity) + "  "
			+ ArabicHelper.reshape(getString(R.string.strUpdateCity_AR)) : getString(R.string.strUpdateCity) + "  "
			+ getString(R.string.strUpdateCity_AR));
		btnUpdateCity.setOnClickListener(this);

		Button btnRemoveCity = (Button) (findViewById(R.id.btnRemoveCity));
		btnRemoveCity.setBackgroundResource(R.drawable.buttonstyle);
		btnRemoveCity.setLines(1);
		btnRemoveCity.setTextColor(FONTCOLOR);
		btnRemoveCity.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
		btnRemoveCity.setTextSize(TypedValue.COMPLEX_UNIT_SP, FONTSIZE_MEDIUM);
		btnRemoveCity.setText(ArabicReshaper.ARABIC_RESHAPE ? getString(R.string.strRemoveCity) + "  "
			+ ArabicHelper.reshape(getString(R.string.strRemoveCity_AR)) : getString(R.string.strRemoveCity) + "  "
			+ getString(R.string.strRemoveCity_AR));
		btnRemoveCity.setOnClickListener(this);

		m_arrMethod = new ArrayList<String>();
		String[] arrT = getString(R.string.strCalcMethodList).split(":");
		for (ii = 0; ii < arrT.length; ii++) {
			m_arrMethod.add(arrT[ii]);
		}

		m_btnCalcMethod = (Button) (findViewById(R.id.btnCalcMethod));
		m_btnCalcMethod.setBackgroundResource(R.drawable.buttonstyle);
		m_btnCalcMethod.setLines(1);
		m_btnCalcMethod.setTextColor(FONTCOLOR);
		m_btnCalcMethod.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
		m_btnCalcMethod.setTextSize(TypedValue.COMPLEX_UNIT_SP, FONTSIZE_MEDIUM);
		m_btnCalcMethod.setOnClickListener(this);

		setTitle(ArabicReshaper.ARABIC_RESHAPE ? getString(R.string.strPrayerTimes) + "  "
			+ ArabicHelper.reshape(getString(R.string.strPrayerTimes_AR)) : getString(R.string.strPrayerTimes) + "  "
			+ getString(R.string.strPrayerTimes_AR));

		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

		SigmaDatabase.initData(this);
		updateCityList();
	}

	@Override
	public void onResume()
	{
		super.onResume();

		restoreState();

		// Returning from File Browser ?
		SharedPreferences preferencesT = getSharedPreferences(SigmaApp.PREFS_FILENAME, MODE_PRIVATE);
		String strT = preferencesT.getString("SigmaFileBrowser_ParentActivity", "");
		if (strT.equals("SigmaSettings")) {
			SharedPreferences.Editor editorT = preferencesT.edit();
			editorT.putString("AlarmFilePath", preferencesT.getString("BrowserSelectedFile", ""));
			editorT.remove("SigmaFileBrowser_ParentActivity");
			editorT.remove("BrowserSelectedFile");
			editorT.remove("BrowserCurrentDirectory");
			editorT.remove("BrowserFileExtension");
			editorT.remove("BrowserSelectDir");
			editorT.commit();
		}
		//

		getHijriDate();

		m_btnCity.setText(m_strSelectedCity.equals(PrayerTimes.INVALID_CITY) ? getString(R.string.strClickToChoose) : m_strSelectedCity);
		getCity(m_strSelectedCity);
		CheckBox idC[] = { m_optAlarmTime1, m_optAlarmTime3, m_optAlarmTime4, m_optAlarmTime6, m_optAlarmTime7, m_optTimeformat,
			m_optTimezoneAuto };
		boolean idB[] = { m_bAlarmTime1, m_bAlarmTime3, m_bAlarmTime4, m_bAlarmTime6, m_bAlarmTime7, m_bTimeformat, m_bTimezoneAuto };
		for (int ii = 0; ii < idC.length; ii++) {
			idC[ii].setChecked(idB[ii]);
		}
		TextView txtT = (TextView) (findViewById(R.id.txtAlarmFilename));
		String strAlarmFilePath = preferencesT.getString("AlarmFilePath", "");
		strT = "";
		if ((strAlarmFilePath.equals("") == false) && (strAlarmFilePath.toLowerCase().endsWith(".mp3") == true)) {
			strT = strAlarmFilePath.substring(1 + strAlarmFilePath.lastIndexOf('/')) + " ";
		}
		txtT.setText(strT);
		Button btnAlarm = (Button) (findViewById(R.id.btnAlarm));
		btnAlarm.setEnabled((m_bAlarmTime1 == true) || (m_bAlarmTime3 == true) || (m_bAlarmTime4 == true) || (m_bAlarmTime6 == true)
			|| (m_bAlarmTime7 == true));

		for (int ii = 0; ii < PrayerTimes.CALCMETHOD.length; ii++) {
			if (PrayerTimes.CALCMETHOD[ii] == m_iCalcMethod) {
				m_btnCalcMethod.setText(m_arrMethod.get(ii));
				break;
			}
		}

		calculatePrayerTimes();

		if (m_bCityListShown == true) {
			m_bCityListShown = false;
			showCityList();
		}
		else if (m_bTimesListShown == true) {
			m_bTimesListShown = false;
			showTimesList();
		}
		else if (m_bMethodListShown == true) {
			m_bMethodListShown = false;
			showMethodList();
		}
		else if (m_bHelpShown == true) {
			m_bHelpShown = false;
			showHelp();
		}

		m_edtCountry.setInputType(InputType.TYPE_CLASS_TEXT);
		m_edtCity.setInputType(InputType.TYPE_CLASS_TEXT);
		m_edtLatitude.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
		m_edtLongitude.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
		m_edtTimezone.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
		m_bTimezoneAuto = m_optTimezoneAuto.isChecked();
		m_edtTimezone.setEnabled(m_bTimezoneAuto == false);
	}

	@Override
	public void onPause()
	{
		try {
			removeKeyboard();

			destroyActivity();
		}
		catch (Throwable thT) {

		}

		super.onPause();
	}

	@Override
	public void onStart()
	{
		super.onStart();

		// ...

	}

	@Override
	public void onStop()
	{
		// ...

		super.onStop();
	}

	@Override
	public void onDestroy()
	{
		// ...

		super.onDestroy();
	}

	public void updateData()
	{
		try {
			m_strSelectedCity = m_btnCity.getText().toString();
			for (int ii = 0; ii < PrayerTimes.CALCMETHOD.length; ii++) {
				if (m_arrMethod.get(ii) == m_btnCalcMethod.getText().toString()) {
					m_iCalcMethod = PrayerTimes.CALCMETHOD[ii];
					break;
				}
			}
			m_bAlarmTime1 = m_optAlarmTime1.isChecked();
			m_bAlarmTime3 = m_optAlarmTime3.isChecked();
			m_bAlarmTime4 = m_optAlarmTime4.isChecked();
			m_bAlarmTime6 = m_optAlarmTime6.isChecked();
			m_bAlarmTime7 = m_optAlarmTime7.isChecked();

			boolean bTimeformat = m_optTimeformat.isChecked();
			boolean bTimezoneAuto = m_optTimezoneAuto.isChecked();
			if ((bTimeformat != m_bTimeformat) || (bTimezoneAuto != m_bTimezoneAuto)) {
				m_bTimeformat = bTimeformat;
				if ((bTimezoneAuto != m_bTimezoneAuto)) {
					m_bTimezoneAuto = bTimezoneAuto;
					try {
						String strCountry = m_edtCountry.getText().toString();
						String strCity = m_edtCity.getText().toString();
						double fLatitude = Double.parseDouble(m_edtLatitude.getText().toString().replace(',', '.'));
						double fLongitude = Double.parseDouble(m_edtLongitude.getText().toString().replace(',', '.'));
						double fTimezone = Double.parseDouble(m_edtTimezone.getText().toString().replace(',', '.'));
						addCity(strCountry, strCity, fLatitude, fLongitude, fTimezone, 0);
					}
					catch (Throwable thT) {
						SigmaApp.showMessage(this, getString(R.string.strInvalidData), true, true);
					}
				}
				calculatePrayerTimes();
			}

			saveState();
		}
		catch (Throwable thT) {

		}

	}

	public void restoreState()
	{
		try {

			SharedPreferences preferencesT = getSharedPreferences("SigmaPrayer", MODE_PRIVATE);
			m_strSelectedCity = preferencesT.getString("SelectedCity", PrayerTimes.INVALID_CITY);
			m_iCalcMethod = preferencesT.getInt("CalcMethod", PrayerTimes.CALCMETHOD_ISNA);
			m_bAlarmTime1 = preferencesT.getBoolean("EnableAlarmTime1", true);
			m_bAlarmTime3 = preferencesT.getBoolean("EnableAlarmTime3", true);
			m_bAlarmTime4 = preferencesT.getBoolean("EnableAlarmTime4", true);
			m_bAlarmTime6 = preferencesT.getBoolean("EnableAlarmTime6", true);
			m_bAlarmTime7 = preferencesT.getBoolean("EnableAlarmTime7", true);
			m_bMethodListShown = preferencesT.getBoolean("MethodListShown", false);
			m_bCityListShown = preferencesT.getBoolean("CityListShown", false);
			m_bTimesListShown = preferencesT.getBoolean("TimesListShown", false);
			m_bTimeformat = preferencesT.getBoolean("Timeformat", true);
			m_bTimezoneAuto = preferencesT.getBoolean("TimezoneAuto", true);
		}
		catch (Throwable thT) {

		}
	}

	public static void saveState(Context contextT, String strCity, int iCalcMethod, boolean bAlarmTime1, boolean bAlarmTime3,
		boolean bAlarmTime4, boolean bAlarmTime6, boolean bAlarmTime7, boolean bMethodListShown, boolean bCityListShown,
		boolean bTimesListShown, boolean bTimeformat, boolean bTimezoneAuto, String strAlarmFilePath)
	{
		try {

			SharedPreferences preferencesT = contextT.getSharedPreferences("SigmaPrayer", MODE_PRIVATE);
			if (strAlarmFilePath == null) {
				strAlarmFilePath = preferencesT.getString("AlarmFilePath", "");
			}
			SharedPreferences.Editor editorT = preferencesT.edit();
			editorT.putString("SelectedCity", strCity);
			editorT.putInt("CalcMethod", iCalcMethod);
			editorT.putBoolean("EnableAlarmTime1", bAlarmTime1);
			editorT.putBoolean("EnableAlarmTime3", bAlarmTime3);
			editorT.putBoolean("EnableAlarmTime4", bAlarmTime4);
			editorT.putBoolean("EnableAlarmTime6", bAlarmTime6);
			editorT.putBoolean("EnableAlarmTime7", bAlarmTime7);
			editorT.putBoolean("MethodListShown", bMethodListShown);
			editorT.putBoolean("CityListShown", bCityListShown);
			editorT.putBoolean("TimesListShown", bTimesListShown);
			editorT.putBoolean("Timeformat", bTimeformat);
			editorT.putBoolean("TimezoneAuto", bTimezoneAuto);
			editorT.putString("AlarmFilePath", strAlarmFilePath);
			editorT.commit();

			SigmaPrayerAlarm.init(contextT);
			SigmaPrayerAlarm.set(SigmaPrayerAlarm.DIFFER);
		}
		catch (Throwable thT) {

		}
	}

	public void saveState()
	{
		saveState(this, m_strSelectedCity, m_iCalcMethod, m_bAlarmTime1, m_bAlarmTime3, m_bAlarmTime4, m_bAlarmTime6, m_bAlarmTime7,
			m_bMethodListShown, m_bCityListShown, m_bTimesListShown, m_bTimeformat, m_bTimezoneAuto, null);
	}

	public boolean calculatePrayerTimes()
	{
		try {
			PrayerTimesHelper helperT = new PrayerTimesHelper(this, m_strSelectedCity, m_iCalcMethod);
			boolean bRet = helperT.calc();
			m_txtTime1.setText((m_bTimeformat == true) ? helperT.getTime1() : helperT.toTime12(helperT.getTime1()));
			m_txtTime3.setText((m_bTimeformat == true) ? helperT.getTime3() : helperT.toTime12(helperT.getTime3()));
			m_txtTime4.setText((m_bTimeformat == true) ? helperT.getTime4() : helperT.toTime12(helperT.getTime4()));
			m_txtTime6.setText((m_bTimeformat == true) ? helperT.getTime6() : helperT.toTime12(helperT.getTime6()));
			m_txtTime7.setText((m_bTimeformat == true) ? helperT.getTime7() : helperT.toTime12(helperT.getTime7()));
			return bRet;
		}
		catch (Throwable thT) {
			m_txtTime1.setText(PrayerTimes.INVALID_TIME);
			m_txtTime3.setText(PrayerTimes.INVALID_TIME);
			m_txtTime4.setText(PrayerTimes.INVALID_TIME);
			m_txtTime6.setText(PrayerTimes.INVALID_TIME);
			m_txtTime7.setText(PrayerTimes.INVALID_TIME);
			return false;
		}
	}

	private void createDialog(boolean bCustomTheme)
	{
		try {
			if (m_Dialog != null) {
				m_Dialog.dismiss();
				m_Dialog = null;
			}
			m_Dialog = new Dialog(SigmaSettings.this, bCustomTheme ? R.style.dialogTheme : android.R.style.Theme_Translucent_NoTitleBar);
		}
		catch (Throwable thT) {
			m_Dialog = null;
			return;
		}
	}

	private void showMenu()
	{
		try {

			if (m_Dialog != null) {
				if (m_Dialog.isShowing() == true) {
					return;
				}
			}

			m_strSelectedMenuItem = "";

			createDialog(false);
			if (m_Dialog == null) {
				return;
			}

			m_Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			m_Dialog.setContentView(R.layout.sigmamenu);
			m_Dialog.setCancelable(true);

			ListView listMenu = (ListView) m_Dialog.findViewById(R.id.listMenu);
			MenuAdapter menuList = new MenuAdapter(this, R.layout.listrow_menu, m_listMenu);
			listMenu.setAdapter(menuList);
			listMenu.setScrollingCacheEnabled(false);
			listMenu.setOnItemClickListener(new OnItemClickListener()
			{
				@Override
				public void onItemClick(AdapterView<?> parentT, View viewT, int positionT, long idT)
				{
					m_strSelectedMenuItem = ((TextView)viewT).getText().toString();

					m_Dialog.dismiss();
					m_Dialog = null;
				}
			});

			LayoutParams paramsT = m_Dialog.getWindow().getAttributes();
			paramsT.width = SigmaApp.calcDialogWidth(listMenu);
			paramsT.height = LayoutParams.WRAP_CONTENT;
			m_Dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams)paramsT);
			m_Dialog.getWindow().setGravity(Gravity.RIGHT | Gravity.TOP);

			m_Dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
				@Override
				public void onDismiss(DialogInterface dlgT) {
					m_Dialog = null;

					if (m_strSelectedMenuItem.equals("")) {
						return;
					}

					if (m_strSelectedMenuItem.equals(getResources().getString(R.string.strTimesList))) {
						showTimesList();
					}
					else if (m_strSelectedMenuItem.equals(getResources().getString(R.string.strHelp))) {
						showHelp();
					}
					else if (m_strSelectedMenuItem.equals(getResources().getString(R.string.strExit))) {
						finishThis();
					}
				}
			});

			m_Dialog.setOnCancelListener(new DialogInterface.OnCancelListener()
			{
				public void onCancel(DialogInterface dlgT)
				{
					if (m_Dialog != null) {
						m_Dialog.dismiss();
						m_Dialog = null;
					}
				}
			});

			m_Dialog.show();
			m_Dialog.setCanceledOnTouchOutside(true);
		}
		catch (Throwable thT) {
			if (m_Dialog != null) {
				m_Dialog.dismiss();
				m_Dialog = null;
			}
		}
	}

	@Override
	public void onClick(View tView)
	{
		int tid = tView.getId();
		if (R.id.btnCalcMethod == tid) {
			showMethodList();
		}
		else if (R.id.btnCity == tid) {
			showCityList();
		}
		else if (R.id.btnUpdateCity == tid) {
			try {
				updateData();

				String strCountry = m_edtCountry.getText().toString();
				String strCity = m_edtCity.getText().toString();
				double fLatitude = Double.parseDouble(m_edtLatitude.getText().toString().replace(',', '.'));
				double fLongitude = Double.parseDouble(m_edtLongitude.getText().toString().replace(',', '.'));
				double fTimezone = Double.parseDouble(m_edtTimezone.getText().toString().replace(',', '.'));
				addCity(strCountry, strCity, fLatitude, fLongitude, fTimezone, 0);
			}
			catch (Throwable thT) {
				SigmaApp.showMessage(this, getString(R.string.strInvalidData), true, true);
			}
		}
		else if (R.id.btnRemoveCity == tid) {
			try {
				String strCountry = m_edtCountry.getText().toString();
				String strCity = m_edtCity.getText().toString();
				removeCity(strCountry, strCity);
			}
			catch (Throwable thT) {
				SigmaApp.showMessage(this, getString(R.string.strInvalidData), true, true);
			}
		}
		else if (R.id.btnAlarm == tid) {
			try {

				updateData();

				if ((m_bAlarmTime1 == false) && (m_bAlarmTime3 == false) && (m_bAlarmTime4 == false) && (m_bAlarmTime6 == false)
						&& (m_bAlarmTime7 == false)) {
					return;
				}

				SharedPreferences preferencesT = getSharedPreferences(SigmaApp.PREFS_FILENAME, MODE_PRIVATE);
				SharedPreferences.Editor editorT = preferencesT.edit();
				editorT.putString("SigmaFileBrowser_ParentActivity", "SigmaSettings");
				editorT.putString("BrowserCurrentDirectory", "");
				editorT.putString("BrowserFileExtension", ".mp3");
				editorT.putBoolean("BrowserSelectDir", false);
				editorT.commit();

				Intent intentFileBrowser = new Intent(SigmaSettings.this, SigmaFileBrowser.class);
				intentFileBrowser.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intentFileBrowser);
			}
			catch (Throwable thT) {

			}
		}
	}

	public void getCity(String strCityT)
	{
		try {

			if (m_cursorT != null) {
				m_cursorT.close();
				m_cursorT = null;
			}
			if (m_dbCity != null) {
				m_dbCity.close();
				m_dbCity = null;
			}

			String[] arrCity = strCityT.split(":");
			String strCountry = arrCity[0].trim();
			String strCity = arrCity[1].trim();
			double fLatitude = 199.0;
			double fLongitude = 199.0;
			double fTimezone = 199.0;

			m_dbCity = openOrCreateDatabase(SigmaDatabase.DATABASE_NAME, MODE_PRIVATE, null);
			if (m_dbCity == null) {
				SigmaApp.showMessage(this, getString(R.string.strInvalidDatabase), true, true);
				return;
			}

			m_cursorT = m_dbCity.query(SigmaDatabase.TBL_CITY, null, "country='" + strCountry + "'" + " AND city='" + strCity + "'", null,
				null, null, null);
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

				m_edtCountry.setText(strCountry);
				m_edtCity.setText(strCity);
				m_edtLatitude.setText(String.format(SigmaApp.LOCALET, "%.5f", fLatitude));
				m_edtLongitude.setText(String.format(SigmaApp.LOCALET, "%.5f", fLongitude));
				m_edtTimezone.setText(String.format(SigmaApp.LOCALET, "%.1f", fTimezone));
				m_btnCity.setText(strCityT);
				m_edtCountry.requestFocus();
				m_edtCountry.setSelection(m_edtCountry.getText().length(), m_edtCountry.getText().length());

				m_cursorT.close();
				m_cursorT = null;
			}
			else {
				m_edtCountry.setText("-");
				m_edtCity.setText("-");
				m_edtLatitude.setText(PrayerTimes.INVALID_TIME);
				m_edtLongitude.setText(PrayerTimes.INVALID_TIME);
				m_edtTimezone.setText("0");
				m_btnCity.setText("- : -");
			}

			m_dbCity.close();
			m_dbCity = null;
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
			return;
		}
	}

	public void showHelp()
	{
		try {
			if (m_dlgHelp != null) {
				if (m_dlgHelp.isShowing() == true) {
					m_bHelpShown = true;
					return;
				}
			}

			m_dlgHelp = new Dialog(SigmaSettings.this, android.R.style.Theme_Translucent_NoTitleBar);
			if (m_dlgHelp != null) {

				m_dlgHelp.requestWindowFeature(Window.FEATURE_NO_TITLE);
				m_dlgHelp.setContentView(R.layout.help);
				m_dlgHelp.setCancelable(true);

				LinearLayout layoutT = (LinearLayout) (m_dlgHelp.findViewById(R.id.layoutHelp));
				layoutT.setBackgroundColor(Color.TRANSPARENT);

				TextView txtHeaderA = (TextView) (m_dlgHelp.findViewById(R.id.txtHelpHeaderA));
				txtHeaderA.setBackgroundColor(Color.TRANSPARENT);
				txtHeaderA.setTextColor(FONTCOLOR);
				txtHeaderA.setTextSize(TypedValue.COMPLEX_UNIT_SP, FONTSIZE_BIG);
				txtHeaderA.setTypeface(Typeface.DEFAULT_BOLD, Typeface.BOLD);

				String strT = ArabicReshaper.ARABIC_RESHAPE ? ArabicHelper.reshape(getString(R.string.strBismillah_AR))
					: getString(R.string.strBismillah_AR);
				strT += "\n" + (ArabicReshaper.ARABIC_RESHAPE ? ArabicHelper.reshape(getString(R.string.strAyatSalat_AR))
					: getString(R.string.strAyatSalat_AR));
				strT += "\n" + (ArabicReshaper.ARABIC_RESHAPE ? ArabicHelper.reshape(getString(R.string.strSadaqallah_AR))
					: getString(R.string.strSadaqallah_AR));
				txtHeaderA.setText(strT);

				TextView txtHelpA = (TextView) (m_dlgHelp.findViewById(R.id.txtHelpA));
				txtHelpA.setBackgroundColor(Color.TRANSPARENT);
				txtHelpA.setTextColor(FONTCOLOR);
				txtHelpA.setTextSize(TypedValue.COMPLEX_UNIT_SP, FONTSIZE_MEDIUM);
				txtHelpA.setTypeface(Typeface.DEFAULT_BOLD, Typeface.BOLD);

				try {
					strT = "\n";
					strT += ArabicReshaper.ARABIC_RESHAPE ? ArabicHelper.reshape(getString(R.string.strHelp1_AR))
						: getString(R.string.strHelp1_AR);
					strT += ":\n";
					strT += (ArabicReshaper.ARABIC_RESHAPE ? ArabicHelper.reshape(getString(R.string.strHelp2_AR))
						: getString(R.string.strHelp2_AR));
					strT += ".\n";
					strT += (ArabicReshaper.ARABIC_RESHAPE ? ArabicHelper.reshape(getString(R.string.strHelp3_AR))
						: getString(R.string.strHelp3_AR));
					strT += ".\n";
					strT += (ArabicReshaper.ARABIC_RESHAPE ? ArabicHelper.reshape(getString(R.string.strHelp4_AR))
						: getString(R.string.strHelp4_AR));
					strT += ".\n";
					strT += (ArabicReshaper.ARABIC_RESHAPE ? ArabicHelper.reshape(getString(R.string.strHelp5_AR))
						: getString(R.string.strHelp5_AR));
					strT += ".\n";
					txtHelpA.setText(strT);
				}
				catch (Throwable thT) {

				}

				TextView txtHeader = (TextView) (m_dlgHelp.findViewById(R.id.txtHelpHeader));
				txtHeader.setBackgroundColor(Color.TRANSPARENT);
				txtHeader.setTextColor(FONTCOLOR);

				TextView txtHelp = (TextView) (m_dlgHelp.findViewById(R.id.txtHelp));
				txtHelp.setText(R.string.strHelpContent);
				txtHelp.setTextColor(FONTCOLOR);

				LayoutParams paramsT = m_dlgHelp.getWindow().getAttributes();
				paramsT.width = LayoutParams.MATCH_PARENT;
				paramsT.height = LayoutParams.MATCH_PARENT;
				m_dlgHelp.getWindow().setAttributes((android.view.WindowManager.LayoutParams) paramsT);

				m_dlgHelp.setOnCancelListener(new DialogInterface.OnCancelListener()
				{
					@Override
					public void onCancel(DialogInterface dlgT)
					{
						m_dlgHelp = null;
						m_bHelpShown = false;
					}
				});

				m_bHelpShown = true;
				m_dlgHelp.show();
			}
		}
		catch (Throwable thT) {
			m_dlgHelp = null;
			m_bHelpShown = false;
			return;
		}
	}

	public void showCityList()
	{
		try {
			if (m_dlgCityList != null) {
				if (m_dlgCityList.isShowing() == true) {
					m_bCityListShown = true;
					return;
				}
			}

			m_dlgCityList = new Dialog(SigmaSettings.this, android.R.style.Theme_Translucent_NoTitleBar);
			if (m_dlgCityList != null) {
				m_dlgCityList.requestWindowFeature(Window.FEATURE_NO_TITLE);
				m_dlgCityList.setContentView(R.layout.listcity);
				m_dlgCityList.setCancelable(true);

				m_listCity = (ListView) (m_dlgCityList.findViewById(R.id.listCity));
				m_listCity.setSelector(R.drawable.browser);
				m_listCity.setAdapter(new ListViewAdapterC(this, R.layout.listrow, m_arrCity));

				int iClr = this.getResources().getColor(R.color.panelistSep);
				int[] dividerColors = new int[] { iClr, iClr, iClr };
				m_listCity.setDivider(new GradientDrawable(Orientation.RIGHT_LEFT, dividerColors));
				m_listCity.setDividerHeight(1);
				m_listCity.setScrollingCacheEnabled(false);

				m_listCity.setSelection(m_arrCity.indexOf(m_btnCity.getText().toString()));

				m_listCity.setOnItemClickListener(new OnItemClickListener()
				{
					@Override
					public void onItemClick(AdapterView<?> adapterT, View viewT, int positionT, long idT)
					{
						if (positionT >= 0) {
							try {
								String strSelectedCity = m_listCity.getItemAtPosition(positionT).toString();
								getCity(strSelectedCity);
								for (int ii = 0; ii < PrayerTimes.CALCMETHOD.length; ii++) {
									if (m_arrMethod.get(ii) == m_btnCalcMethod.getText().toString()) {
										m_iCalcMethod = PrayerTimes.CALCMETHOD[ii];
										break;
									}
								}
								m_strSelectedCity = strSelectedCity;
								m_btnCity.setText(strSelectedCity);
								m_optTimezoneAuto.setChecked(false);
								m_bTimezoneAuto = m_optTimezoneAuto.isChecked();
								m_edtTimezone.setEnabled(m_bTimezoneAuto == false);
								calculatePrayerTimes();
							}
							catch (Throwable thT) {

							}
						}

						m_dlgCityList.cancel();
						m_dlgCityList = null;
						m_bCityListShown = false;

					}
				});

				LayoutParams paramsT = m_dlgCityList.getWindow().getAttributes();
				paramsT.width = LayoutParams.MATCH_PARENT;
				paramsT.height = LayoutParams.WRAP_CONTENT;
				m_dlgCityList.getWindow().setAttributes((android.view.WindowManager.LayoutParams) paramsT);

				LinearLayout layoutT = (LinearLayout) (m_dlgCityList.findViewById(R.id.layoutCityList));
				layoutT.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
				layoutT.setPadding(layoutT.getPaddingRight() + layoutT.getVerticalScrollbarWidth(), layoutT.getPaddingTop(),
					layoutT.getPaddingRight(), layoutT.getPaddingBottom());

				m_dlgCityList.setOnCancelListener(new DialogInterface.OnCancelListener()
				{
					@Override
					public void onCancel(DialogInterface dlgT)
					{
						m_dlgCityList = null;
						m_bCityListShown = false;
					}
				});

				m_bCityListShown = true;
				m_dlgCityList.show();
				m_dlgCityList.setCanceledOnTouchOutside(true);
			}
		}
		catch (Throwable thT) {
			m_dlgCityList = null;
			m_bCityListShown = false;
			return;
		}
	}

	public void updateTimesList()
	{
		try {
			if (m_arrTimes != null) {
				m_arrTimes.clear();
			}
			else {
				m_arrTimes = new ArrayList<HashMap<String, String>>();
			}
		}
		catch (Throwable thT) {

		}

		if (m_arrTimes == null) {
			SigmaApp.showMessage(this, getString(R.string.strUnsufficientMemory), true, true);
			return;
		}

		try {

			// Times list
			Calendar calT = Calendar.getInstance();
			int yearT = calT.get(Calendar.YEAR);
			int monthT = calT.get(Calendar.MONTH) + 1;
			int daysCount = calT.getActualMaximum(Calendar.DAY_OF_MONTH);

			int idLG[] = { R.string.strGregorianMonth1Short, R.string.strGregorianMonth2Short, R.string.strGregorianMonth3Short,
				R.string.strGregorianMonth4Short, R.string.strGregorianMonth5Short, R.string.strGregorianMonth6Short,
				R.string.strGregorianMonth7Short, R.string.strGregorianMonth8Short, R.string.strGregorianMonth9Short,
				R.string.strGregorianMonth10Short, R.string.strGregorianMonth11Short, R.string.strGregorianMonth12Short };

			PrayerTimesHelper helperT = new PrayerTimesHelper(this, m_strSelectedCity, m_iCalcMethod);

			HashMap<String, String> hashi = new HashMap<String, String>();
			hashi.put(ListViewAdapterT.FIRST_COLUMN, "");
			hashi.put(ListViewAdapterT.SECOND_COLUMN, "");
			hashi.put(ListViewAdapterT.THIRD_COLUMN, getString(idLG[monthT - 1]));
			hashi.put(ListViewAdapterT.FOURTH_COLUMN, String.format(Locale.getDefault(), "%04d", yearT));
			hashi.put(ListViewAdapterT.FIFTH_COLUMN, "");
			hashi.put(ListViewAdapterT.SIXTH_COLUMN, "");
			m_arrTimes.add(hashi);

			for (int ii = 1; ii <= daysCount; ii++) {
				helperT.setDate(String.format(SigmaApp.LOCALET, "%04d-%02d-%02d", yearT, monthT, ii));
				helperT.calc();
				hashi = new HashMap<String, String>();
				hashi.put(ListViewAdapterT.FIRST_COLUMN, String.format(Locale.getDefault(), "%02d", ii));
				hashi.put(ListViewAdapterT.SECOND_COLUMN, (m_bTimeformat == true) ? helperT.getTime1() : helperT.toTime12(helperT.getTime1()));
				hashi.put(ListViewAdapterT.THIRD_COLUMN, (m_bTimeformat == true) ? helperT.getTime3() : helperT.toTime12(helperT.getTime3()));
				hashi.put(ListViewAdapterT.FOURTH_COLUMN, (m_bTimeformat == true) ? helperT.getTime4() : helperT.toTime12(helperT.getTime4()));
				hashi.put(ListViewAdapterT.FIFTH_COLUMN, (m_bTimeformat == true) ? helperT.getTime6() : helperT.toTime12(helperT.getTime6()));
				hashi.put(ListViewAdapterT.SIXTH_COLUMN, (m_bTimeformat == true) ? helperT.getTime7() : helperT.toTime12(helperT.getTime7()));
				m_arrTimes.add(hashi);
			}

			return;
		}
		catch (Throwable thT) {
			return;
		}
	}

	public void showTimesList()
	{
		try {
			if (m_dlgTimesList != null) {
				if (m_dlgTimesList.isShowing() == true) {
					m_bTimesListShown = true;
					return;
				}
			}

			m_dlgTimesList = new Dialog(SigmaSettings.this, android.R.style.Theme_Translucent_NoTitleBar);
			if (m_dlgTimesList == null) {
				return;
			}

			updateTimesList();

			m_dlgTimesList.requestWindowFeature(Window.FEATURE_NO_TITLE);
			m_dlgTimesList.setContentView(R.layout.listtimes);
			m_dlgTimesList.setCancelable(true);

			m_listTimes = (ListView) (m_dlgTimesList.findViewById(R.id.listTimes));
			m_listTimes.setSelector(R.drawable.browser);
			m_listTimes.setAdapter(new ListViewAdapterT(this, m_arrTimes));

			int iClr = this.getResources().getColor(R.color.panelistSep);
			int[] dividerColors = new int[] { iClr, iClr, iClr };
			m_listTimes.setDivider(new GradientDrawable(Orientation.RIGHT_LEFT, dividerColors));
			m_listTimes.setDividerHeight(1);
			m_listTimes.setScrollingCacheEnabled(false);

			LayoutParams paramsT = m_dlgTimesList.getWindow().getAttributes();
			paramsT.width = LayoutParams.MATCH_PARENT;
			paramsT.height = LayoutParams.WRAP_CONTENT;
			m_dlgTimesList.getWindow().setAttributes((android.view.WindowManager.LayoutParams) paramsT);

			LinearLayout layoutT = (LinearLayout) (m_dlgTimesList.findViewById(R.id.layoutTimesList));
			layoutT.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
			layoutT.setPadding(layoutT.getPaddingRight() + layoutT.getVerticalScrollbarWidth(), layoutT.getPaddingTop(),
				layoutT.getPaddingRight(), layoutT.getPaddingBottom());

			m_dlgTimesList.setOnCancelListener(new DialogInterface.OnCancelListener()
			{
				@Override
				public void onCancel(DialogInterface dlgT)
				{
					m_dlgTimesList = null;
					m_bTimesListShown = false;
				}
			});

			m_bTimesListShown = true;
			m_dlgTimesList.show();
			m_dlgTimesList.setCanceledOnTouchOutside(true);
		}
		catch (Throwable thT) {
			m_dlgTimesList = null;
			m_bTimesListShown = false;
			return;
		}
	}

	public void showMethodList()
	{
		try {
			if (m_dlgMethodList != null) {
				if (m_dlgMethodList.isShowing() == true) {
					m_bMethodListShown = true;
					return;
				}
			}

			m_dlgMethodList = new Dialog(SigmaSettings.this, android.R.style.Theme_Translucent_NoTitleBar);
			if (m_dlgMethodList != null) {
				m_dlgMethodList.requestWindowFeature(Window.FEATURE_NO_TITLE);
				m_dlgMethodList.setContentView(R.layout.listmethod);
				m_dlgMethodList.setCancelable(true);

				m_listMethod = (ListView) (m_dlgMethodList.findViewById(R.id.listMethod));
				m_listMethod.setSelector(R.drawable.browser);
				m_listMethod.setAdapter(new ListViewAdapterC(this, R.layout.listrow, m_arrMethod));

				int iClr = this.getResources().getColor(R.color.panelistSep);
				int[] dividerColors = new int[] { iClr, iClr, iClr };
				m_listMethod.setDivider(new GradientDrawable(Orientation.RIGHT_LEFT, dividerColors));
				m_listMethod.setDividerHeight(1);
				m_listMethod.setScrollingCacheEnabled(false);

				for (int ii = 0; ii < PrayerTimes.CALCMETHOD.length; ii++) {
					if (PrayerTimes.CALCMETHOD[ii] == m_iCalcMethod) {
						m_listMethod.setSelection(ii);
						break;
					}
				}

				m_listMethod.setOnItemClickListener(new OnItemClickListener()
				{
					@Override
					public void onItemClick(AdapterView<?> adapterT, View viewT, int positionT, long idT)
					{
						if (positionT >= 0) {
							try {
								String strSelectedMethod = m_listMethod.getItemAtPosition(positionT).toString();
								String strSelectedCity = m_btnCity.getText().toString();
								getCity(strSelectedCity);
								for (int ii = 0; ii < PrayerTimes.CALCMETHOD.length; ii++) {
									if (m_arrMethod.get(ii) == strSelectedMethod) {
										m_iCalcMethod = PrayerTimes.CALCMETHOD[ii];
										m_btnCalcMethod.setText(strSelectedMethod);
										break;
									}
								}
								calculatePrayerTimes();
							}
							catch (Throwable thT) {

							}
						}

						m_dlgMethodList.cancel();
						m_dlgMethodList = null;
						m_bMethodListShown = false;
					}
				});

				LayoutParams paramsT = m_dlgMethodList.getWindow().getAttributes();
				paramsT.width = LayoutParams.MATCH_PARENT;
				paramsT.height = LayoutParams.WRAP_CONTENT;
				m_dlgMethodList.getWindow().setAttributes((android.view.WindowManager.LayoutParams) paramsT);

				LinearLayout layoutT = (LinearLayout) (m_dlgMethodList.findViewById(R.id.layoutMethodList));
				layoutT.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
				layoutT.setPadding(layoutT.getPaddingRight() + layoutT.getVerticalScrollbarWidth(), layoutT.getPaddingTop(),
					layoutT.getPaddingRight(), layoutT.getPaddingBottom());

				m_dlgMethodList.setOnCancelListener(new DialogInterface.OnCancelListener()
				{
					@Override
					public void onCancel(DialogInterface dlgT)
					{
						m_dlgMethodList = null;
						m_bMethodListShown = false;
					}
				});

				m_bMethodListShown = true;
				m_dlgMethodList.show();
				m_dlgMethodList.setCanceledOnTouchOutside(true);
			}
		}
		catch (Throwable thT) {
			m_dlgMethodList = null;
			m_bMethodListShown = false;
			return;
		}
	}

	public void updateCityList()
	{
		try {
			if (m_cursorT != null) {
				m_cursorT.close();
				m_cursorT = null;
			}
			if (m_dbCity != null) {
				m_dbCity.close();
				m_dbCity = null;
			}

			m_dbCity = openOrCreateDatabase(SigmaDatabase.DATABASE_NAME, MODE_PRIVATE, null);

			if (m_arrCity != null) {
				m_arrCity.clear();
			}
			else {
				m_arrCity = new ArrayList<String>();
			}
		}
		catch (Throwable thT) {
			if (m_dbCity != null) {
				m_dbCity.close();
				m_dbCity = null;
			}
		}

		if ((m_dbCity == null) || (m_arrCity == null)) {
			if (m_dbCity != null) {
				m_dbCity.close();
				m_dbCity = null;
			}
			SigmaApp.showMessage(this, (m_dbCity == null) ? getString(R.string.strInvalidDatabase) : getString(R.string.strUnsufficientMemory), true, true);
			return;
		}

		try {

			// City list
			m_cursorT = m_dbCity.query(SigmaDatabase.TBL_CITY, null, null, null, null, null, "country asc");
			if (m_cursorT == null) {
				m_dbCity.close();
				m_dbCity = null;
				return;
			}

			int iCountryColumn = m_cursorT.getColumnIndex("country");
			int iCityColumn = m_cursorT.getColumnIndex("city");

			m_cursorT.moveToFirst();

			while (m_cursorT.isAfterLast() == false) {

				m_arrCity.add(String.format(SigmaApp.LOCALET, "%s : %s", m_cursorT.getString(iCountryColumn), m_cursorT.getString(iCityColumn)));

				m_cursorT.moveToNext();
			}

			m_cursorT.close();
			m_cursorT = null;

			m_dbCity.close();
			m_dbCity = null;

			return;
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
			return;
		}
	}

	public void addCity(String strCountry, String strCity, double fLatitude, double fLongitude, double fTimezone, int iZipcode)
	{

		try {

			if (m_cursorT != null) {
				m_cursorT.close();
				m_cursorT = null;
			}
			if (m_dbCity != null) {
				m_dbCity.close();
				m_dbCity = null;
			}

			try {
				if (m_arrCity == null) {
					m_arrCity = new ArrayList<String>();
				}
			}
			catch (Throwable thT) {

			}

			if (m_arrCity == null) {
				SigmaApp.showMessage(this, getString(R.string.strUnsufficientMemory), true, true);
				return;
			}

			if ((strCountry.length() > 1) && (strCountry.length() < 128) && (strCity.length() > 1) && (strCity.length() < 128)
				&& (fLatitude > -199.0) && (fLatitude < 199.0) && (fLongitude > -199.0) && (fLongitude < 199.0) && (fTimezone >= -14.0)
				&& (fTimezone <= 14.0) && (iZipcode >= 0) && (iZipcode <= 999999)) {

				boolean bUpdated = false;

				m_dbCity = openOrCreateDatabase(SigmaDatabase.DATABASE_NAME, MODE_PRIVATE, null);
				if (m_dbCity == null) {
					return;
				}

				String strCityT = String.format(Locale.getDefault(), "%s : %s", strCountry, strCity);
				ContentValues valuesT = new ContentValues();

				String strTimezone = "";

				if (m_arrCity.contains(strCityT) == true) {
					// Already defined ? -> Update if editable
					m_cursorT = m_dbCity.query(SigmaDatabase.TBL_CITY, null, "country='" + strCountry + "'" + " AND city='" + strCity + "'",
						null, null, null, null);
					if (m_cursorT != null) {
						m_cursorT.moveToFirst();

						int iFlagColumn = m_cursorT.getColumnIndex("flag");
						int iTimezoneColumn = m_cursorT.getColumnIndex("timezone");
						int iFlag = m_cursorT.getInt(iFlagColumn);
						String strTimezoneT = m_cursorT.getString(iTimezoneColumn);
						String[] arrT = strTimezoneT.split(";");
						double fTimezoneT = Double.parseDouble(arrT[0]);
						double fTimezoneOrg = Double.parseDouble(arrT[1]);

						// Automatically calculate GMT offset (for daylight saving time)
						if (m_bTimezoneAuto) {
							TimeZone tz = TimeZone.getDefault();
							Date nowT = new Date();
							float gmtOffset = (float) (tz.getOffset(nowT.getTime())) / 1000.0f / 3600.0f;
							if ((gmtOffset != fTimezone) && (gmtOffset >= (fTimezoneOrg - 3.0f)) && (gmtOffset <= (fTimezoneOrg + 3.0f))) {
								fTimezone = gmtOffset;
								m_edtTimezone.setText(Double.toString(fTimezone));
							}
						}
						//

						if (iFlag == 0) {
							if ((fTimezoneT != fTimezone) && (Math.abs(fTimezone - fTimezoneOrg) <= 3.0f)) {
								strTimezone = String.format(Locale.getDefault(), "%.1f;%.1f;TZ", fTimezone, fTimezoneOrg).replace(',', '.');
								valuesT.clear();
								valuesT.put("timezone", strTimezone);
								if (m_dbCity.update(SigmaDatabase.TBL_CITY, valuesT, "country='" + strCountry + "'" + " AND " + "city='" + strCity
									+ "'", null) == 1) {
									bUpdated = true;
								}
							}
							else if (Math.abs(fTimezone - fTimezoneOrg) > 3.0) {
								SigmaApp.showMessage(this, getString(R.string.strInvalidTimezone), true, true);
							}
						}
						else if (iFlag == 1) {
							int iLatitudeColumn = m_cursorT.getColumnIndex("latitude");
							int iLongitudeColumn = m_cursorT.getColumnIndex("longitude");
							double fLatitudeT = m_cursorT.getDouble(iLatitudeColumn);
							double fLongitudeT = m_cursorT.getDouble(iLongitudeColumn);

							if ((fTimezoneT != fTimezone) || (fLatitudeT != fLatitude) || (fLongitudeT != fLongitude)) {
								strTimezone = String.format(Locale.getDefault(), "%.1f;%.1f;TZ", fTimezone, fTimezoneOrg).replace(',', '.');
								valuesT.clear();
								valuesT.put("latitude", fLatitude);
								valuesT.put("longitude", fLongitude);
								valuesT.put("timezone", strTimezone);
								if (m_dbCity.update(SigmaDatabase.TBL_CITY, valuesT, "country='" + strCountry + "'" + " AND " + "city='" + strCity
									+ "'", null) == 1) {
									bUpdated = true;
								}
							}
						}

						m_cursorT.close();
						m_cursorT = null;
					}
				}
				else {
					if (DatabaseUtils.queryNumEntries(m_dbCity, SigmaDatabase.TBL_CITY) >= MAX_COUNT) {
						SigmaApp.showMessage(this, getString(R.string.strDatabaseNotUpdated), true, true);
					}
					else {
						// Add
						strTimezone = String.format(Locale.getDefault(), "%.1f;%.1f;TZ", fTimezone, fTimezone).replace(',', '.');
						valuesT.clear();
						valuesT.put("country", strCountry);
						valuesT.put("city", strCity);
						valuesT.put("latitude", fLatitude);
						valuesT.put("longitude", fLongitude);
						valuesT.put("timezone", strTimezone);
						valuesT.put("zipcode", iZipcode);
						valuesT.put("note", "");
						valuesT.put("flag", 1);
						if (m_dbCity.insert(SigmaDatabase.TBL_CITY, "note", valuesT) != -1) {
							bUpdated = true;
						}
					}
				}

				m_dbCity.close();
				m_dbCity = null;

				if (bUpdated == true) {
					// m_dbCity should be closed and null-ed before updateCityList
					updateCityList();
					String strSelectedCity = String.format(Locale.getDefault(), "%s : %s", strCountry, strCity);
					// m_dbCity should be closed and null-ed before getCity
					getCity(strSelectedCity);
					for (int ii = 0; ii < PrayerTimes.CALCMETHOD.length; ii++) {
						if (m_arrMethod.get(ii) == m_btnCalcMethod.getText().toString()) {
							m_iCalcMethod = PrayerTimes.CALCMETHOD[ii];
							break;
						}
					}
					calculatePrayerTimes();

					SigmaApp.showMessage(this, getString(R.string.strDatabaseUpdated), true, false);
				}
			}

			return;
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
			SigmaApp.showMessage(this, getString(R.string.strInvalidData), true, true);
			return;
		}
	}

	public void removeCity(String strCountry, String strCity)
	{

		try {

			if (m_cursorT != null) {
				m_cursorT.close();
				m_cursorT = null;
			}
			if (m_dbCity != null) {
				m_dbCity.close();
				m_dbCity = null;
			}

			String strCityT = String.format(Locale.getDefault(), "%s : %s", strCountry, strCity);

			if (m_arrCity.contains(strCityT) == true) {

				m_dbCity = openOrCreateDatabase(SigmaDatabase.DATABASE_NAME, MODE_PRIVATE, null);
				if (m_dbCity == null) {
					SigmaApp.showMessage(this, getString(R.string.strInvalidDatabase), true, true);
					return;
				}

				boolean bUpdated = false;

				// Delete if editable
				m_cursorT = m_dbCity.query(SigmaDatabase.TBL_CITY, null, "country='" + strCountry + "'" + " AND city='" + strCity + "'", null,
					null, null, null);
				if (m_cursorT != null) {
					m_cursorT.moveToFirst();

					int iFlagColumn = m_cursorT.getColumnIndex("flag");
					int iFlag = m_cursorT.getInt(iFlagColumn);

					if (iFlag == 0) {
						SigmaApp.showMessage(this, getString(R.string.strReadonly), true, true);
					}
					else if (iFlag == 1) {
						if (m_dbCity.delete(SigmaDatabase.TBL_CITY, "country='" + strCountry + "'" + " AND " + "city='" + strCity + "'", null) == 1) {
							bUpdated = true;
						}
					}

					m_cursorT.close();
					m_cursorT = null;
				}

				m_dbCity.close();
				m_dbCity = null;

				if (bUpdated == true) {
					String strSelectedCity = m_btnCity.getText().toString();
					int iT = m_arrCity.indexOf(strSelectedCity);
					// m_dbCity should be closed and null-ed before updateCityList
					updateCityList();
					if (strSelectedCity.equals(strCityT)) {
						strSelectedCity = m_arrCity.get(iT);
					}
					// m_dbCity should be closed and null-ed before getCity
					getCity(strSelectedCity);
					for (int ii = 0; ii < PrayerTimes.CALCMETHOD.length; ii++) {
						if (m_arrMethod.get(ii) == m_btnCalcMethod.getText().toString()) {
							m_iCalcMethod = PrayerTimes.CALCMETHOD[ii];
							break;
						}
					}
					calculatePrayerTimes();

					SigmaApp.showMessage(this, getString(R.string.strDatabaseUpdated), true, false);
				}
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
			SigmaApp.showMessage(this, getString(R.string.strInvalidData), true, true);
			return;
		}

		return;
	}

	private void getHijriDate()
	{
		double fGMT = (double) (System.currentTimeMillis());
		fGMT /= 1000.0;

		// Hijri
		double fTDays = (double) (Math.round(fGMT / (60.0 * 60.0 * 24.0)));
		double fHYear = (double) (Math.round(fTDays / 354.37419));
		double fRemain = fTDays - (fHYear * 354.37419);
		double fHMonths = (double) (Math.round(fRemain / 29.531182));
		double fHDays = fRemain - (fHMonths * 29.531182);
		fHYear += 1389.0;
		fHMonths += 10.0;
		fHDays = fHDays + 23.0;
		if ((fHDays > 29.531188) && (Math.round(fHDays) != 30)) {
			fHMonths += 1.0;
			fHDays = (double) (Math.floor(fHDays - 29.531182));
		}
		else {
			fHDays = (double) (Math.floor(fHDays));
		}
		if (fHMonths > 12.0) {
			fHMonths -= 12.0;
			fHYear += 1.0;
		}

		int iDay = (int) fHDays;
		int iMonth = (int) fHMonths;
		int iYear = (int) fHYear;

		Date nowT = new Date();
		Calendar calT = Calendar.getInstance();
		calT.setTime(nowT);

		int iDayG = (int) (calT.get(Calendar.DAY_OF_MONTH));
		int iMonthG = 1 + (int) (calT.get(Calendar.MONTH));
		int iYearG = (int) (calT.get(Calendar.YEAR));

		int idL[] = { R.string.strHijriMonth1_AR, R.string.strHijriMonth2_AR, R.string.strHijriMonth3_AR, R.string.strHijriMonth4_AR,
			R.string.strHijriMonth5_AR, R.string.strHijriMonth6_AR, R.string.strHijriMonth7_AR, R.string.strHijriMonth8_AR,
			R.string.strHijriMonth9_AR, R.string.strHijriMonth10_AR, R.string.strHijriMonth11_AR, R.string.strHijriMonth12_AR };
		int idLG[] = { R.string.strGregorianMonth1, R.string.strGregorianMonth2, R.string.strGregorianMonth3, R.string.strGregorianMonth4,
			R.string.strGregorianMonth5, R.string.strGregorianMonth6, R.string.strGregorianMonth7, R.string.strGregorianMonth8,
			R.string.strGregorianMonth9, R.string.strGregorianMonth10, R.string.strGregorianMonth11, R.string.strGregorianMonth12 };

		try {
			m_txtCalendar.setText(String.format(SigmaApp.LOCALET, "%d %s %04d", iDayG, getString(idLG[iMonthG - 1]), iYearG));
			String strT = String.format(Locale.getDefault(), "%d %s %04d", iDay, getString(idL[iMonth - 1]), iYear);
			String strT_AR = String.format(Locale.getDefault(), "%04d %s %d", iYear, getString(idL[iMonth - 1]), iDay);
			m_txtCalendar_AR.setText(ArabicReshaper.ARABIC_RESHAPE ? ArabicHelper.reshape(strT_AR) : strT);
		}
		catch (Throwable thT) {

		}
	}

	private void removeKeyboard()
	{
		// Hide soft keyboard before quitting, to avoid warning ('showstatusicon
		// on inactive inputconnection')
		try {

			InputMethodManager inputMgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

			inputMgr.hideSoftInputFromWindow(m_edtCountry.getWindowToken(), 0);
			m_edtCountry.setInputType(InputType.TYPE_NULL);

			inputMgr.hideSoftInputFromWindow(m_edtCity.getWindowToken(), 0);
			m_edtCity.setInputType(InputType.TYPE_NULL);

			inputMgr.hideSoftInputFromWindow(m_edtLatitude.getWindowToken(), 0);
			m_edtLatitude.setInputType(InputType.TYPE_NULL);

			inputMgr.hideSoftInputFromWindow(m_edtLongitude.getWindowToken(), 0);
			m_edtLongitude.setInputType(InputType.TYPE_NULL);

			inputMgr.hideSoftInputFromWindow(m_edtTimezone.getWindowToken(), 0);
			m_edtTimezone.setInputType(InputType.TYPE_NULL);
		}
		catch (Throwable thT) {

		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			try {
				finishThis();
			}
			catch (Throwable thT) {

			}
		}

		return super.onKeyDown(keyCode, event);
	}

	private void destroyActivity()
	{
		try {

			try {
				if (m_Dialog != null) {
					m_Dialog.dismiss();
					m_Dialog = null;
				}

				if (m_cursorT != null) {
					m_cursorT.close();
					m_cursorT = null;
				}
				if (m_dbCity != null) {
					m_dbCity.close();
					m_dbCity = null;
				}

				if (m_dlgCityList != null) {
					m_dlgCityList.dismiss();
					m_dlgCityList = null;
				}

				if (m_dlgTimesList != null) {
					m_dlgTimesList.dismiss();
					m_dlgTimesList = null;
				}

				if (m_dlgMethodList != null) {
					m_dlgMethodList.dismiss();
					m_dlgMethodList = null;
				}

				if (m_dlgHelp != null) {
					m_dlgHelp.dismiss();
					m_dlgHelp = null;
				}

			}
			catch (Throwable thT) {

			}

			updateData();

		}
		catch (Throwable thT) {

		}
	}

	private void finishThis()
	{
		destroyActivity();

		SigmaSettings.this.finish();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
	}

}
