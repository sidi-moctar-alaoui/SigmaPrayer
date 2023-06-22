//	-------------------------------------------------------------
//	SigmaPrayer for Android
//  Placed in the public domain:
//      It may be freely reproduced, distributed, transmitted, used, modified,
//      built upon, or otherwise exploited by anyone for any purpose, commercial or non-commercial,
//      and in any way
//  sidi.moctar.alaoui@gmail.com
//	-------------------------------------------------------------

package cc.sidi.SigmaPrayer;

import java.util.List;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListViewAdapterC extends ArrayAdapter<String>
{
	private final static int[] COLORS = new int[] { 0x60596471, 0x6075818F };

	public ListViewAdapterC(Context tContext, int textViewResourceId, List<String> objects)
	{
		super(tContext, textViewResourceId, objects);

	}

	@Override
	public View getView(int positionT, View convertView, ViewGroup parentT)
	{
		View viewT = super.getView(positionT, convertView, parentT);

		TextView txtView = (TextView) viewT.findViewById(R.id.txtItem);

		try {
			int colorPos = positionT % (COLORS.length);
			txtView.setBackgroundColor(COLORS[colorPos]);

			int iMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, txtView.getResources().getDisplayMetrics());
			txtView.setPadding(iMargin, iMargin, iMargin, iMargin);
			txtView.setTextColor(txtView.getResources().getColor(R.color.textcolor));
		}
		catch (Throwable thT) {

		}

		return viewT;
	}

}
