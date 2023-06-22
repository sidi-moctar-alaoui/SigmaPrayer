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
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.os.Environment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint("DefaultLocale")
public class SigmaFileBrowser extends ListActivity
{
	private List<String> m_listDirectory = null;
	private File m_CurrentDirectory = null;
	private boolean m_bBrowsed = false;

	private Dialog m_dlgAlert = null;

	private String m_strExt = ".mp3";

	private boolean m_bSelectDir = false;

	private Dialog m_dlgCreateDir = null;

	private String m_strParentActivity = "";

	private boolean m_bCanCancel = false;

	private TextView m_txtHeader = null;
	private TextView m_txtFooter = null;

	private int m_iFooterColor = Color.LTGRAY;
	private ForegroundColorSpan m_ColorQuit = new ForegroundColorSpan(Color.WHITE);
	private StyleSpan m_StyleQuit = new StyleSpan(android.graphics.Typeface.ITALIC);

	private void createActivity()
	{
		try {
			SharedPreferences preferencesT = getSharedPreferences(SigmaApp.PREFS_FILENAME, MODE_PRIVATE);
			if (preferencesT.getBoolean("FileBrowserActivityCreated", false) == true) {
				return;
			}

			requestWindowFeature(Window.FEATURE_NO_TITLE);
			ListView listviewT = getListView();
			listviewT.setSelector(R.drawable.browser);
			listviewT.setBackgroundColor(this.getResources().getColor(R.color.browserBackground));

			int iClr = this.getResources().getColor(R.color.panelistSep);
			int[] dividerColors = new int[] { iClr, iClr, iClr };
			listviewT.setDivider(new GradientDrawable(Orientation.RIGHT_LEFT, dividerColors));
			listviewT.setDividerHeight(1);
			listviewT.setScrollingCacheEnabled(false);

			try {
				ViewGroup headerT = (ViewGroup) getLayoutInflater().inflate(R.layout.listview_header, listviewT, false);
				ViewGroup footerT = (ViewGroup) getLayoutInflater().inflate(R.layout.listview_footer, listviewT, false);
				listviewT.addHeaderView(headerT, null, false);
				listviewT.addFooterView(footerT, null, false);
				m_txtHeader = (TextView) headerT.findViewById(R.id.txtListviewHeader);
				m_txtFooter = (TextView) footerT.findViewById(R.id.txtListviewFooter);
			}
			catch (Throwable thT) {
				cancelThis();
				return;
			}

			m_listDirectory = new ArrayList<String>();

			String strState = Environment.getExternalStorageState();
			if ((strState.equals(Environment.MEDIA_MOUNTED) == false) && (strState.equals(Environment.MEDIA_MOUNTED_READ_ONLY) == false)) {
				SigmaApp.showMessage(SigmaFileBrowser.this, getResources().getString(R.string.strMemoryAccessErr), true, true);
				cancelThis();
				return;
			}

			listviewT.setLongClickable(true);
			listviewT.setOnItemLongClickListener(new OnItemLongClickListener()
			{
				@Override
				public boolean onItemLongClick(AdapterView<?> adapterT, View viewT, int positionT, long idT)
				{
					m_bCanCancel = false;

					if (m_bSelectDir) {
						String strSelectedDir = (String) adapterT.getItemAtPosition(positionT);
						String strDir;
						try {
							strDir = m_CurrentDirectory.getCanonicalPath();
						}
						catch (IOException excT) {
							cancelThis();
							return false;
						}
						int iLen = strDir.length();
						if (iLen >= 1) {
							if (strDir.charAt(iLen - 1) != '/') {
								strDir += "/";
							}
							strDir += strSelectedDir + "/";

							finishThis(strDir);
						}

						return true;
					}
					else {
						return false;
					}
				}
			});

			m_txtHeader.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View viewT)
				{
					showCreateDir();
				}
			});

			m_txtFooter.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View viewT)
				{
					if (m_dlgAlert != null) {
						m_dlgAlert.dismiss();
						m_dlgAlert = null;
					}
					cancelThis();
				}
			});

			SharedPreferences.Editor editorT = preferencesT.edit();
			editorT.putBoolean("FileBrowserActivityCreated", true);
			editorT.putBoolean("FileBrowserActivityStopped", false);
			editorT.commit();
		}
		catch (Throwable thT) {
			SharedPreferences preferencesT = getSharedPreferences(SigmaApp.PREFS_FILENAME, MODE_PRIVATE);
			SharedPreferences.Editor editorT = preferencesT.edit();
			editorT.putBoolean("FileBrowserActivityCreated", false);
			editorT.putBoolean("FileBrowserActivityStopped", false);
			editorT.commit();
		}

	}

	@Override
	public void onCreate(Bundle bundleT)
	{
		super.onCreate(bundleT);

		SharedPreferences preferencesT = getSharedPreferences(SigmaApp.PREFS_FILENAME, MODE_PRIVATE);
		SharedPreferences.Editor editorT = preferencesT.edit();
		editorT.putBoolean("FileBrowserActivityStopped", false);
		editorT.putBoolean("FileBrowserActivityCreated", false);
		editorT.commit();

		createActivity();
	}

	@Override
	public void onStart()
	{
		super.onStart();

		// Create UI if not yet done in onCreate
		createActivity();
	}

	@Override
	public void onResume()
	{
		super.onResume();

		// Create UI if not yet done in onCreate
		createActivity();

		try {
			SharedPreferences preferencesT = getSharedPreferences(SigmaApp.PREFS_FILENAME, MODE_PRIVATE);
			m_strParentActivity = preferencesT.getString("SigmaFileBrowser_ParentActivity", "");
			if ((m_strParentActivity == null) || m_strParentActivity.equals("")) {
				cancelThis();
				return;
			}

			String strCurrentDirectory = preferencesT.getString("BrowserCurrentDirectory", "");

			if (strCurrentDirectory.equals("")) {
				m_bSelectDir = preferencesT.getBoolean("BrowserSelectDir", false);
				m_strExt = preferencesT.getString("BrowserFileExtension", ".*");
				boolean bCurrentFile = false;
				String strCurrentFilename = preferencesT.getString("BrowserCurrentFilename", "");
				if ((strCurrentFilename.equals("") == false)
					&& ((m_bSelectDir == false) && (strCurrentFilename.toLowerCase().endsWith(m_strExt) == true))) {
					File fileScript = new File(strCurrentFilename);
					if ((fileScript.exists() == true) && (fileScript.length() >= 1L) && (fileScript.length() <= SigmaSettings.MAXFILELENGTH)) {
						bCurrentFile = true;
					}
				}

				if (bCurrentFile == true) {
					m_CurrentDirectory = new File(strCurrentFilename.substring(0, 1 + strCurrentFilename.lastIndexOf('/')));
				}
				else {
					try {
						m_CurrentDirectory = new File(Environment.getExternalStorageDirectory().getCanonicalPath());
					}
					catch (IOException excT) {
						m_CurrentDirectory = null;
						cancelThis();
						return;
					}
				}
			}
			else {
				m_bSelectDir = preferencesT.getBoolean("BrowserSelectDir", false);
				m_strExt = preferencesT.getString("BrowserFileExtension", ".*");
				m_CurrentDirectory = new File(strCurrentDirectory);
			}

			m_bCanCancel = true;

			browseTo(m_CurrentDirectory);
			m_bBrowsed = false;

			SharedPreferences.Editor editorT = preferencesT.edit();
			try {
				editorT.putString("BrowserCurrentDirectory", m_CurrentDirectory.getCanonicalPath());
			}
			catch (IOException excT) {
				editorT.putString("BrowserCurrentDirectory", "");
			}
			editorT.putString("BrowserFileExtension", m_strExt);
			editorT.putBoolean("BrowserSelectDir", m_bSelectDir);
			editorT.commit();

			m_txtHeader.setBackgroundResource(R.drawable.headertext);
			m_txtHeader.setTextColor(m_iFooterColor);
			m_txtHeader.setSingleLine(false);
			m_txtHeader.setGravity(Gravity.CENTER);

			m_txtFooter.setBackgroundResource(R.drawable.headertext);
			m_txtFooter.setTextColor(m_iFooterColor);
			m_txtFooter.setSingleLine(false);
			m_txtFooter.setGravity(Gravity.CENTER);

			int iStart = 0, iEnd = 0;
			if (m_bSelectDir) {
				setTitle(getString(R.string.strSelectDirMessage));
				SigmaApp.showMessage(SigmaFileBrowser.this, getResources().getString(R.string.strSelectDirMessage), false);
				m_txtFooter.setText(getString(R.string.strSelectDirMessage) + getString(R.string.strCloseFileBrowserMessage));
				iStart = getString(R.string.strSelectDirMessage).length();
				iEnd = iStart + getString(R.string.strCloseFileBrowserMessage).length();
			}
			else {
				setTitle(getString(R.string.strSelectFileB));
				m_txtFooter.setText(getString(R.string.strSelectFileB) + getString(R.string.strCloseFileBrowserMessage));
				iStart = getString(R.string.strSelectFileB).length();
				iEnd = iStart + getString(R.string.strCloseFileBrowserMessage).length();
			}

			try {
				SpannableString spannableT = new SpannableString(m_txtFooter.getText());
				spannableT.setSpan(m_ColorQuit, iStart, iEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				spannableT.setSpan(m_StyleQuit, iStart, iEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				m_txtFooter.setText(spannableT);
			}
			catch (Throwable thT) {

			}

		}
		catch (Throwable thT) {
			cancelThis();
			return;
		}
	}

	private void destroyActivity()
	{
		try {
			SharedPreferences preferencesT = getSharedPreferences(SigmaApp.PREFS_FILENAME, MODE_PRIVATE);
			if (preferencesT.getBoolean("FileBrowserActivityStopped", false) == true) {
				return;
			}

			SharedPreferences.Editor editorT = preferencesT.edit();
			editorT.putBoolean("FileBrowserActivityStopped", true);
			editorT.putBoolean("FileBrowserActivityCreated", false);
			editorT.commit();
		}
		catch (Throwable thT) {
			SharedPreferences preferencesT = getSharedPreferences(SigmaApp.PREFS_FILENAME, MODE_PRIVATE);
			SharedPreferences.Editor editorT = preferencesT.edit();
			editorT.putBoolean("FileBrowserActivityStopped", true);
			editorT.putBoolean("FileBrowserActivityCreated", false);
			editorT.commit();
		}
	}

	@Override
	public void onPause()
	{
		destroyActivity();

		super.onPause();
	}

	@Override
	public void onStop()
	{
		// Destroy UI if not yet done in onPause
		destroyActivity();

		super.onStop();
	}

	@Override
	public void onDestroy()
	{
		// Destroy UI if not yet done in onPause
		destroyActivity();

		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			try {
				if ((m_bCanCancel) || (m_CurrentDirectory.getCanonicalFile().equals("/"))) {
					cancelThis();
				}
				else if (m_bBrowsed == true) {
					upOneLevel();
					m_bCanCancel = false;
					return true;
				}
			}
			catch (IOException excT) {
				cancelThis();
			}
		}

		return super.onKeyDown(keyCode, event);
	}

	public void showCreateDir()
	{
		try {
			if (m_dlgCreateDir != null) {
				if (m_dlgCreateDir.isShowing() == true) {
					return;
				}
			}

			try {
				m_dlgCreateDir = new Dialog(this, R.style.dialogTheme);
			}
			catch (Throwable thT) {
				m_dlgCreateDir = null;
				return;
			}
			if (m_dlgCreateDir == null) {
				return;
			}

			m_dlgCreateDir.requestWindowFeature(Window.FEATURE_NO_TITLE);
			m_dlgCreateDir.setContentView(R.layout.selectf);
			m_dlgCreateDir.setCancelable(true);

			TextView txtDir = (TextView) (m_dlgCreateDir.findViewById(R.id.lblSelectFile));
			txtDir.setText(R.string.strCreateDir);

			final EditText edtDir = (EditText) (m_dlgCreateDir.findViewById(R.id.edtSelectFile));
			edtDir.setBackgroundResource(R.drawable.inputstyle);
			edtDir.setTextColor(Color.WHITE);
			edtDir.setText("");

			edtDir.setTypeface(Typeface.DEFAULT_BOLD);
			edtDir.setTextSize(SigmaSettings.FONTSIZE_MEDIUM);

			LinearLayout layoutSelectFile = (LinearLayout) (m_dlgCreateDir.findViewById(R.id.layoutSelectFile));
			LinearLayout layoutSelectFileNote = (LinearLayout) (m_dlgCreateDir.findViewById(R.id.layoutSelectFileNote));
			layoutSelectFile.removeView(layoutSelectFileNote);

			Button btnOK = (Button) (m_dlgCreateDir.findViewById(R.id.btnOK));
			btnOK.setBackgroundResource(R.drawable.buttondlg);
			btnOK.setLines(1);
			btnOK.setTextColor(Color.WHITE);

			Button btnCancel = (Button) (m_dlgCreateDir.findViewById(R.id.btnCancel));
			btnCancel.setBackgroundResource(R.drawable.buttondlg);
			btnCancel.setLines(1);
			btnCancel.setTextColor(Color.WHITE);

			// Set buttons size
			SigmaSize sizeT = new SigmaSize(this);
			sizeT.resizeButtons(btnOK, btnCancel);

			btnOK.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View tView)
				{

					try {
						String strFilename = edtDir.getText().toString();

						if (strFilename.equals("")) {
							m_dlgCreateDir.dismiss();
							m_dlgCreateDir = null;
							return;
						}

						int ii;
						for (ii = 0; ii < SigmaApp.ILLEGAL_CHARACTERS.length; ii++) {
							if (strFilename.indexOf(SigmaApp.ILLEGAL_CHARACTERS[ii]) >= 0) {
								SigmaApp.showMessage(SigmaFileBrowser.this, getResources().getString(R.string.strInvalidFilename), false, true);
								return;
							}
						}

						m_dlgCreateDir.dismiss();
						m_dlgCreateDir = null;

						String strDir = m_CurrentDirectory.getCanonicalPath();
						int iLen = strDir.length();
						if (iLen >= 1) {
							if (strDir.charAt(iLen - 1) != '/') {
								strDir += "/";
							}
							strDir += strFilename + "/";
							File dirT = new File(strDir);
							dirT.mkdirs();
							browseTo(m_CurrentDirectory);
						}

						return;
					}
					catch (Throwable thT) {
						m_dlgCreateDir.dismiss();
						m_dlgCreateDir = null;
						return;
					}
				}
			});
			btnCancel.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View tView)
				{
					m_dlgCreateDir.dismiss();
					m_dlgCreateDir = null;
				}
			});

			LayoutParams paramsT = m_dlgCreateDir.getWindow().getAttributes();
			paramsT.width = LayoutParams.WRAP_CONTENT;
			paramsT.height = LayoutParams.WRAP_CONTENT;
			m_dlgCreateDir.getWindow().setAttributes((android.view.WindowManager.LayoutParams) paramsT);
			m_dlgCreateDir.getWindow().setGravity(Gravity.CENTER);

			m_dlgCreateDir.setOnDismissListener(new DialogInterface.OnDismissListener()
			{
				@Override
				public void onDismiss(DialogInterface dlgT)
				{
					m_dlgCreateDir = null;
				}
			});

			m_dlgCreateDir.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
			m_dlgCreateDir.show();
			m_dlgCreateDir.setCanceledOnTouchOutside(true);
		}
		catch (Throwable thT) {
			if (m_dlgCreateDir != null) {
				m_dlgCreateDir.dismiss();
				m_dlgCreateDir = null;
			}
			return;
		}
	}

	private void upOneLevel()
	{
		try {
			if (m_CurrentDirectory.getParent() != null) {
				browseTo(m_CurrentDirectory.getParentFile());
			}
		}
		catch (Throwable thT) {
			cancelThis();
		}
	}

	private void browseTo(final File selectedDirectory)
	{
		try {
			if (selectedDirectory.isDirectory()) {

				File[] dirsT = selectedDirectory.listFiles(new FilenameFilter()
				{
					public boolean accept(File dirT, String nameT)
					{
						File fileT = new File(dirT, nameT);
						if (fileT.isDirectory()) {
							return true;
						}
						else {
							return false;
						}
					}
				});

				File[] filesT = selectedDirectory.listFiles(new FilenameFilter()
				{
					public boolean accept(File dirT, String nameT)
					{
						File fileT = new File(dirT, nameT);
						if (fileT.isDirectory()) {
							return false;
						}
						else {
							if (m_bSelectDir == false) {
								int iLen = nameT.length();
								if (iLen < 4) {
									return false;
								}
								String strExt = nameT.substring(iLen - 4).toLowerCase(SigmaApp.LOCALET);
								if (m_strExt.equals(".*")) {
									return (strExt.equals(".lua") || strExt.equals(".txt") || strExt.equals(".dat") || strExt.equals(".jpg")
										|| strExt.equals(".c") || strExt.equals(".cpp") || strExt.equals(".php") || strExt.equals(".py")
										|| strExt.equals(".mp3"));
								}
								return strExt.equals(m_strExt);
							}
							return false;
						}
					}
				});

				Comparator<File> comparatorT = new Comparator<File>() {
					@Override
					public int compare(File arg0, File arg1)
					{
						try {
							return arg0.getName().compareToIgnoreCase(arg1.getName());
						}
						catch (Throwable thT) {
							return 0;
						}
					}
				};

				Arrays.sort(dirsT, comparatorT);
				Arrays.sort(filesT, comparatorT);

				m_CurrentDirectory = selectedDirectory;
				updateList(dirsT, filesT);
				m_bBrowsed = true;

				m_txtHeader.setText(m_CurrentDirectory.getCanonicalPath() + getString(R.string.strCreateDirMessage));
				int iStart = m_CurrentDirectory.getCanonicalPath().length();
				int iEnd = iStart + getString(R.string.strCreateDirMessage).length();
				try {
					SpannableString spannableT = new SpannableString(m_txtHeader.getText());
					spannableT.setSpan(m_ColorQuit, iStart, iEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
					spannableT.setSpan(m_StyleQuit, iStart, iEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
					m_txtHeader.setText(spannableT);
				}
				catch (Throwable thT) {

				}

			}
			else if (selectedDirectory.isFile()) {
				finishThis(selectedDirectory.getCanonicalPath());
			}
			else {
				SigmaApp.showMessage(SigmaFileBrowser.this, getResources().getString(R.string.strSelectFileErr), true, true);
				cancelThis();
			}
		}
		catch (Throwable thT) {

		}
	}

	private void updateList(File[] dirsT, File[] filesT)
	{
		try {
			m_listDirectory.clear();

			try {
				Thread.sleep(10L);
			}
			catch (Throwable thT) {

			}

			m_listDirectory.add(".");
			if (m_CurrentDirectory.getParent() != null) {
				m_listDirectory.add("..");
			}

			String strPath = "", strName = "";
			int indexT = 0;
			for (File fileT : dirsT) {
				strPath = fileT.getCanonicalPath();
				indexT = strPath.lastIndexOf("/");
				strName = strPath.substring(indexT + 1);
				if (fileT.isDirectory()) {
					m_listDirectory.add(strName);
				}
			}
			for (File fileT : filesT) {
				strPath = fileT.getCanonicalPath();
				indexT = strPath.lastIndexOf("/");
				strName = strPath.substring(indexT + 1);
				if (fileT.isDirectory() == false) {
					String strT = fileT.getName().toLowerCase();
					if (m_strExt.equals(".*")) {
						if (strT.endsWith(".lua") || strT.endsWith(".txt") || strT.endsWith(".dat") || strT.endsWith(".jpg")
							|| strT.endsWith(".c") || strT.endsWith(".cpp") || strT.endsWith(".php") || strT.endsWith(".py")) {
							m_listDirectory.add(strName);
						}
					}
					else if (strT.endsWith(m_strExt)) {
						m_listDirectory.add(strName);
					}
				}
			}

			BrowserAdapter directoryList = new BrowserAdapter(this, R.layout.listrow_browser, m_listDirectory);
			directoryList.setCurrentDirectory(m_CurrentDirectory.getCanonicalPath());
			setListAdapter(directoryList);

			getListView().setSelection(directoryList.getCount() - 1);

			SharedPreferences preferencesT = getSharedPreferences(SigmaApp.PREFS_FILENAME, MODE_PRIVATE);
			SharedPreferences.Editor editorT = preferencesT.edit();
			try {
				editorT.putString("BrowserCurrentDirectory", m_CurrentDirectory.getCanonicalPath());
			}
			catch (IOException excT) {
				editorT.putString("BrowserCurrentDirectory", "");
			}
			editorT.commit();

		}
		catch (Throwable thT) {
			cancelThis();
		}
	}

	@Override
	protected void onListItemClick(ListView listT, View viewT, int positionT, long idT)
	{
		try {
			m_bCanCancel = false;

			String strSelectedFile = listT.getItemAtPosition(positionT).toString();

			if (strSelectedFile.equals(".")) {
				browseTo(m_CurrentDirectory);
			}
			else if (strSelectedFile.equals("..")) {
				upOneLevel();
			}
			else {
				File clickedFile = null;

				String strCurrentDirectory = m_CurrentDirectory.getCanonicalPath();
				if (strCurrentDirectory.endsWith("/") == false) {
					strCurrentDirectory += "/";
				}

				try {
					clickedFile = new File(strCurrentDirectory + strSelectedFile);
				}
				catch (Throwable thT) {
					SigmaApp.showMessage(SigmaFileBrowser.this, getResources().getString(R.string.strSelectFileErr), true, true);
					clickedFile = null;
				}

				if (clickedFile != null) {
					browseTo(clickedFile);
				}
			}
		}
		catch (Throwable thT) {
			return;
		}
	}

	public void finishThis(String strSelectedFile)
	{
		// Save data for the calling activity
		SharedPreferences preferencesT = getSharedPreferences(SigmaApp.PREFS_FILENAME, MODE_PRIVATE);
		SharedPreferences.Editor editorT = preferencesT.edit();
		editorT.putString("SigmaFileBrowser_ParentActivity", m_strParentActivity);
		editorT.putString("BrowserSelectedFile", strSelectedFile);
		try {
			editorT.putString("BrowserCurrentDirectory", m_CurrentDirectory.getCanonicalPath());
		}
		catch (IOException excT) {
			editorT.putString("BrowserCurrentDirectory", "");
		}
		editorT.putString("BrowserFileExtension", m_strExt);
		editorT.putBoolean("BrowserSelectDir", m_bSelectDir);

		editorT.putInt(m_strParentActivity + "_WindowShown", 0);

		editorT.commit();

		SigmaFileBrowser.this.finish();

	}

	private void cancelThis()
	{
		// If activity cancelled, remove any data for the calling activity (the activity must restart the file browser)
		SharedPreferences preferencesT = getSharedPreferences(SigmaApp.PREFS_FILENAME, MODE_PRIVATE);
		SharedPreferences.Editor editorT = preferencesT.edit();
		editorT.remove("SigmaFileBrowser_ParentActivity");
		editorT.remove("BrowserSelectedFile");
		editorT.remove("BrowserCurrentDirectory");
		editorT.remove("BrowserFileExtension");
		editorT.remove("BrowserSelectDir");

		editorT.putInt(m_strParentActivity + "_WindowShown", 0);

		editorT.commit();

		SigmaFileBrowser.this.finish();

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
	}

}
