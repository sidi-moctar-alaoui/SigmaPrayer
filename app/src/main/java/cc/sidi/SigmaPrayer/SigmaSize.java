//	-------------------------------------------------------------
//	SigmaPrayer for Android
//  Placed in the public domain:
//      It may be freely reproduced, distributed, transmitted, used, modified,
//      built upon, or otherwise exploited by anyone for any purpose, commercial or non-commercial,
//      and in any way
//  sidi.moctar.alaoui@gmail.com
//	-------------------------------------------------------------

package cc.sidi.SigmaPrayer;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Button;

public class SigmaSize
{
	private int m_iPadding = 0;
	private int m_iCharSize = 0;
	private int m_iViewSize = 0;
	private int m_iGridSize = 0;
	private int m_iScreenWidth = 0;
	private int m_iScreenHeight = 0;
	private int m_iPaneSize = 0;
	private int m_iIconSize = 0;

	public int getPadding()
	{
		return m_iPadding;
	}
	public int getCharSize()
	{
		return m_iCharSize;
	}
	public int getViewSize()
	{
		return m_iViewSize;
	}
	public int getGridSize()
	{
		return m_iGridSize;
	}
	public int getDialogWidth()
	{
		return (m_iViewSize << 1) + (m_iViewSize >> 1);
	}
	public int getIconSize()
	{
		return m_iIconSize;
	}
	public int getScreenWidth()
	{
		return m_iScreenWidth;
	}
	public int getScreenHeight()
	{
		return m_iScreenHeight;
	}
	public int getPaneSize()
	{
		return m_iPaneSize;
	}

	public SigmaSize(Context contextT)
	{
		m_iPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, contextT.getResources().getDisplayMetrics());
		m_iCharSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, contextT.getResources().getDisplayMetrics());
		m_iViewSize = (m_iCharSize * 9) + (m_iPadding * 2);
		m_iPaneSize = (m_iCharSize * 13) + (m_iPadding * 2);
		m_iIconSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, contextT.getResources().getDisplayMetrics());

		int iPaddingM = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, contextT.getResources().getDisplayMetrics());
		m_iGridSize = (((m_iCharSize * 3) + (iPaddingM * 2)) * 6) + (m_iPadding * 5);

		WindowManager wm = (WindowManager) contextT.getSystemService(Context.WINDOW_SERVICE);
		Display dispT = wm.getDefaultDisplay();
		DisplayMetrics displayMetrics = new DisplayMetrics();
		dispT.getMetrics(displayMetrics);
		m_iScreenWidth = displayMetrics.widthPixels;
		m_iScreenHeight = displayMetrics.heightPixels;
	}

	public void resizeButtons(Button btnYes, Button btnNo)
	{
		int iBtnWidth = m_iViewSize;
		int iBtnHeight = (m_iCharSize * 2) + (m_iPadding * 2);
		btnYes.setWidth(iBtnWidth);
		btnYes.setHeight(iBtnHeight);
		btnYes.setPadding(m_iPadding, m_iPadding, m_iPadding, m_iPadding);
		btnNo.setWidth(iBtnWidth);
		btnNo.setHeight(iBtnHeight);
		btnNo.setPadding(m_iPadding, m_iPadding, m_iPadding, m_iPadding);
	}

}
