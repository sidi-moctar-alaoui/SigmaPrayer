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
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MenuAdapter extends ArrayAdapter<String>
{
	private int m_Color = Color.WHITE;

	private ForegroundColorSpan m_ColorResult = new ForegroundColorSpan(Color.rgb(180, 180, 180));
	private boolean m_bMultiline = false;

	public MenuAdapter(Context tContext, int textViewResourceId, List<String> objects)
	{
		super(tContext, textViewResourceId, objects);
	}
	public void setMultiline(boolean bMultiline)
	{
		m_bMultiline = bMultiline;
	}

	@Override
	public View getView(int positionT, View convertView, ViewGroup parentT)
	{
		View viewT = super.getView(positionT, convertView, parentT);

		TextView txtView = (TextView) viewT.findViewById(R.id.txtItem);

		try {
			LayoutParams paramsT = txtView.getLayoutParams();
			paramsT.width = LayoutParams.MATCH_PARENT;
			paramsT.height = LayoutParams.WRAP_CONTENT;
			txtView.setLayoutParams(paramsT);

			int iMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6, txtView.getResources().getDisplayMetrics());
			txtView.setPadding(iMargin, iMargin, iMargin, iMargin);

			txtView.setSingleLine(m_bMultiline == false);

			String[] arrT = txtView.getText().toString().split("\n");
			int iLength = arrT.length;
			String strT = "";
			if (iLength == 2) {
				strT = arrT[0] + "\n= " + arrT[1];
				txtView.setText(strT);
			}

			// Separator item --> disable item (not clickable)
			if (txtView.getText().toString().startsWith("+")) {
				txtView.setTextColor(Color.GRAY);
			}
			else {

				int iLen = txtView.getText().toString().length();
				int iSel = txtView.getText().toString().indexOf("=");

				if ((iSel > 0) && (iSel < (iLen - 1))) {

					SpannableString spannableT = new SpannableString(txtView.getText());
					for (ForegroundColorSpan spanT : spannableT.getSpans(0, spannableT.length(), ForegroundColorSpan.class)) {
						spannableT.removeSpan(spanT);
					}
					ForegroundColorSpan colorResult = m_ColorResult;

					txtView.setTextColor(m_Color);

					if ((iSel > 0) && (iSel < (iLen - 1))) {
						spannableT.setSpan(colorResult, iSel, iLen, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
						txtView.setText(spannableT);
					}
					else if (iSel == (iLen - 1)) {
						txtView.setText(spannableT.subSequence(0, iSel));
					}
				}
				else {
					txtView.setTextColor(m_Color);
				}
			}

		}
		catch (Throwable thT) {

		}

		return viewT;
	}

}
