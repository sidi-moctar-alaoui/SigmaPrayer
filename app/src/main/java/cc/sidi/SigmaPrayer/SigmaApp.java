//	-------------------------------------------------------------
//	SigmaPrayer for Android
//  Placed in the public domain:
//      It may be freely reproduced, distributed, transmitted, used, modified,
//      built upon, or otherwise exploited by anyone for any purpose, commercial or non-commercial,
//      and in any way
//  sidi.moctar.alaoui@gmail.com
//	-------------------------------------------------------------

package cc.sidi.SigmaPrayer;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Locale;

public class SigmaApp extends Application
{
	public static final int MENUCOLOR = Color.rgb(245,220,180);

	public static final Locale LOCALET = Locale.getDefault();

	public static final String PREFS_FILENAME = "SigmaPrayer";
	public static final String PREFS_FILE = "SigmaPreferences";
	public static final String PREFS_EULA_ACCEPTED = "eula.accepted";
	public static final String PREFS_VERSION = "sigma.version";

	public static final int FILENAME_MAXLENGTH = 255;
	public static final int FILENAME_MAXLENGTH_SHORT = 63;
	public static final char[] ILLEGAL_CHARACTERS = { '/', '\n', '\r', '\t', '\0', '\f', '`', '?', '*', '\\', '<', '>', '|', '\"', ':' };
	public static final int FILE_MAXSIZE = 2097152; // 2 MB

	public static final float FONTSIZE_MIN = 8f;
	public static final float FONTSIZE_SMALL = 12f;
	public static final float FONTSIZE_MEDIUM = 14f;
	public static final float FONTSIZE_BIG = 20f;
	public static final float FONTSIZE_MAX = 48f;

	@Override
	public void onCreate()
	{
		super.onCreate();

	}

	@Override
	public void onTerminate()
	{

		super.onTerminate();
	}

	public static int calcDialogWidth(ListView listT)
	{
		try {

			WindowManager wm = (WindowManager) listT.getContext().getSystemService(Context.WINDOW_SERVICE);
			Display dispT = wm.getDefaultDisplay();
			DisplayMetrics displayMetrics = new DisplayMetrics();
			dispT.getMetrics(displayMetrics);
			int widthScreen = displayMetrics.widthPixels;
			if (widthScreen < 320) {
				widthScreen = 320;
			}

			// Calculate the menu width
			float widthMax = 0f, widthT = 0f, fmax, fmin;
			TextView viewT = null;
			TextPaint paintT = null;
			String strT = "";
			String[] arrT = null;
			for (int ii = 0; ii < listT.getAdapter().getCount(); ii++) {
				viewT = (TextView) (listT.getAdapter().getView(ii, null, null));
				paintT = viewT.getPaint();

				if (ii == 0) {
					widthMax = paintT.measureText("WWWWWWW");
				}

				arrT = viewT.getText().toString().split("\n");
				if ((arrT != null) && (arrT.length == 2)) {
					strT = arrT[0] + "WW";
					widthT = paintT.measureText(strT);
					if (widthT > widthMax) {
						widthMax = widthT;
					}
					strT = arrT[1] + "WW";
					widthT = paintT.measureText(strT);
					if (widthT > widthMax) {
						widthMax = widthT;
					}
				}
				else {
					strT = viewT.getText().toString() + "WW";
					widthT = paintT.measureText(strT);
					if (widthT > widthMax) {
						widthMax = widthT;
					}
				}
			}
			fmin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, listT.getResources().getDisplayMetrics());
			fmax = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 512, listT.getResources().getDisplayMetrics());
			if (widthMax < fmin) {
				widthMax = fmin;
			}
			else if (widthMax > fmax) {
				widthMax = fmax;
			}
			widthMax += TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, listT.getResources().getDisplayMetrics());
			//

			if (widthMax > widthScreen) {
				widthMax = widthScreen;
			}

			return (int)widthMax;
		}
		catch (Throwable thT) {
			return LayoutParams.WRAP_CONTENT;
		}
		//
	}

	public static void showMessage(Activity activityT, String strMessage)
	{
		showMessage(activityT, strMessage, true, false);
	}
	public static void showMessage(Activity activityT, String strMessage, boolean bLong)
	{
		showMessage(activityT, strMessage, bLong, false);
	}
	public static void showMessage(Activity activityT, String strMessage, boolean bLong, boolean bWarn)
	{
		try {
			final int[] TOAST_COLORS = { activityT.getResources().getColor(R.color.textcolor),
				activityT.getResources().getColor(R.color.warncolor) };

			LayoutInflater inflaterT = activityT.getLayoutInflater();
			View layoutT = inflaterT.inflate(R.layout.toast, (ViewGroup) (activityT.findViewById(R.id.layoutToast)));
			TextView txtToast = (TextView) layoutT.findViewById(R.id.txtToast);
			txtToast.setSingleLine(false);
			txtToast.setTextColor((bWarn == false) ? TOAST_COLORS[0] : TOAST_COLORS[1]);
			txtToast.setText(strMessage);

			Toast toastT = new Toast(activityT);
			toastT.setDuration((bLong == true) ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);

			toastT.setView(layoutT);
			toastT.setGravity(Gravity.CENTER, 0, 0);
			toastT.show();
		}
		catch (Throwable thT) {

		}
	}

}
