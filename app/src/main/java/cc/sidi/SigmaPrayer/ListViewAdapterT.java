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
import java.util.HashMap;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListViewAdapterT extends BaseAdapter
{
	private final static int[] COLORS = new int[] { 0x60596471, 0x6075818F };

	private final static int COLORSEL = 0x60FFBBC0;
	public static final String FIRST_COLUMN = "First";
	public static final String SECOND_COLUMN = "Second";
	public static final String THIRD_COLUMN = "Third";
	public static final String FOURTH_COLUMN = "Fourth";
	public static final String FIFTH_COLUMN = "Fifth";
	public static final String SIXTH_COLUMN = "Sixth";

	public ArrayList<HashMap<String,String>> m_List;
	public Activity m_Activity;

	public ListViewAdapterT(Activity activityT, ArrayList<HashMap<String,String>> listT)
	{
		super();
		this.m_Activity = activityT;
		this.m_List = listT;
	}

	@Override
	public int getCount() {
		return m_List.size();
	}

	@Override
	public Object getItem(int position) {
		return m_List.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	private class ViewHolder
	{
		TextView txtFirst;
		TextView txtSecond;
		TextView txtThird;
		TextView txtFourth;
		TextView txtFifth;
		TextView txtSixth;
	}

	@Override
	public View getView(int positionT, View convertView, ViewGroup parentT)
	{
		ViewHolder holderT;
		LayoutInflater inflaterT = m_Activity.getLayoutInflater();

		if (convertView == null) {
			convertView = inflaterT.inflate(R.layout.listrowcol, null);
			holderT = new ViewHolder();
			holderT.txtFirst = (TextView) convertView.findViewById(R.id.txtDay);
			holderT.txtSecond = (TextView) convertView.findViewById(R.id.txtTime1);
			holderT.txtThird = (TextView) convertView.findViewById(R.id.txtTime3);
			holderT.txtFourth = (TextView) convertView.findViewById(R.id.txtTime4);
			holderT.txtFifth = (TextView) convertView.findViewById(R.id.txtTime6);
			holderT.txtSixth = (TextView) convertView.findViewById(R.id.txtTime7);
			convertView.setTag(holderT);
		}
		else {
			holderT = (ViewHolder) convertView.getTag();
		}

		HashMap<String, String> mapT = m_List.get(positionT);
		String strFirst = (String) mapT.get(FIRST_COLUMN);
		holderT.txtFirst.setText((CharSequence) mapT.get(FIRST_COLUMN));
		holderT.txtSecond.setText((CharSequence) mapT.get(SECOND_COLUMN));
		holderT.txtThird.setText((CharSequence) mapT.get(THIRD_COLUMN));
		holderT.txtFourth.setText((CharSequence) mapT.get(FOURTH_COLUMN));
		holderT.txtFifth.setText((CharSequence) mapT.get(FIFTH_COLUMN));
		holderT.txtSixth.setText((CharSequence) mapT.get(SIXTH_COLUMN));
		if (strFirst.equals("") == true) {
			holderT.txtThird.setTypeface(Typeface.DEFAULT_BOLD);
			holderT.txtFourth.setTypeface(Typeface.DEFAULT_BOLD);
			holderT.txtThird.setTextColor(SigmaSettings.FONTCOLOR);
			holderT.txtFourth.setTextColor(SigmaSettings.FONTCOLOR);
		}
		else {
			holderT.txtFirst.setTypeface(Typeface.DEFAULT_BOLD);

			holderT.txtThird.setTypeface(Typeface.DEFAULT);
			holderT.txtFourth.setTypeface(Typeface.DEFAULT);
			holderT.txtThird.setTextColor(SigmaSettings.FONTCOLOR);

			holderT.txtFirst.setTextColor(SigmaSettings.FONTCOLOR);
			holderT.txtSecond.setTextColor(Color.rgb(247, 247, 229));
			holderT.txtThird.setTextColor(Color.rgb(253, 250, 237));
			holderT.txtFourth.setTextColor(Color.rgb(238, 223, 200));
			holderT.txtFifth.setTextColor(Color.rgb(225, 223, 243));
			holderT.txtSixth.setTextColor(Color.rgb(209, 205, 237));
		}

		Calendar calT = Calendar.getInstance();
		int dayT = calT.get(Calendar.DAY_OF_MONTH);
		int posT = 0;
		try {
			posT = Integer.parseInt(holderT.txtFirst.getText().toString());
		}
		catch (Throwable thT) {
			posT = 0;
		}
		if (dayT == posT) {
			convertView.setBackgroundColor(COLORSEL);
		}
		else {
			int colorPos = positionT % (COLORS.length);
			convertView.setBackgroundColor(COLORS[colorPos]);
		}

		return convertView;
	}


}