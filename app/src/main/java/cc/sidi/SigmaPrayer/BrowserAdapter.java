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
import java.util.List;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class BrowserAdapter extends ArrayAdapter<String>
{
	private final static int[] COLORS = new int[] { 0x60596471, 0x6075818F };
	private final static int[] COLORS_DIR = new int[] { 0xF0FEFBAE, 0xF0D4E7FE, 0xF0FED4D7 };

	private String m_strCurrentDirectory = "";

	public BrowserAdapter(Context tContext, int textViewResourceId, List<String> objects)
	{
		super(tContext, textViewResourceId, objects);

	}

	public void setCurrentDirectory(String strCurrentDirectory)
	{
		m_strCurrentDirectory = strCurrentDirectory;
		int iLen = m_strCurrentDirectory.length();
		if (iLen > 0) {
			if (m_strCurrentDirectory.charAt(iLen - 1) != '/') {
				m_strCurrentDirectory += "/";
			}
		}
	}

	@Override
	public View getView(int positionT, View convertView, ViewGroup parentT)
	{
		View viewT = super.getView(positionT, convertView, parentT);

		TextView txtView = (TextView) viewT.findViewById(R.id.txtItem);

		try {
			int colorPos = positionT % (COLORS.length);
			txtView.setBackgroundColor(COLORS[colorPos]);

			int iLen = m_strCurrentDirectory.length();
			if (iLen > 0) {
				String strT = txtView.getText().toString();
				if (strT.equals(".") || strT.equals("..")) {
					txtView.setTextColor(COLORS_DIR[0]);
					txtView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_dir, 0, 0, 0);
				}
				else {
					File fileT = new File(m_strCurrentDirectory + strT);
					if (fileT.isDirectory()) {
						txtView.setTextColor(COLORS_DIR[0]);
						txtView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_dir, 0, 0, 0);
					}
					else if (fileT.isFile()) {
						txtView.setTextColor(COLORS_DIR[1]);
						txtView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_file, 0, 0, 0);
					}
					else {
						txtView.setTextColor(COLORS_DIR[2]);
						txtView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_unknown, 0, 0, 0);
					}
				}
			}
			else {
				txtView.setTextColor(COLORS_DIR[2]);
				txtView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_unknown, 0, 0, 0);
			}
			int iMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, txtView.getResources().getDisplayMetrics());
			txtView.setPadding(iMargin, iMargin, iMargin, iMargin);
		}
		catch (Throwable thT) {

		}

		return viewT;
	}

}
