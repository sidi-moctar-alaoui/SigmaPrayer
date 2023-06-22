//	-------------------------------------------------------------
//	SigmaPrayer for Android
//  Placed in the public domain:
//      It may be freely reproduced, distributed, transmitted, used, modified,
//      built upon, or otherwise exploited by anyone for any purpose, commercial or non-commercial,
//      and in any way
//  sidi.moctar.alaoui@gmail.com
//	-------------------------------------------------------------

package cc.sidi.SigmaPrayer;

import java.lang.String;

import android.os.Build;

public class ArabicReshaper
{
	public static final boolean ARABIC_RESHAPE = (Build.VERSION.SDK_INT >= 8) && (Build.VERSION.SDK_INT <= 10);

	private String m_strReturn;
	public String getReshapedWord()
	{
		return m_strReturn;
	}

	public static char ORGINAL_ALF_UPPER_MDD = 0x0622;
	public static char ORGINAL_ALF_UPPER_HAMAZA = 0x0623;
	public static char ORGINAL_ALF_LOWER_HAMAZA = 0x0625;
	public static char ORGINAL_ALF = 0x0627;
	public static char ORGINAL_LAM = 0x0644;

	public static char[][] LAM_ALEF_GLYPH = {
		{ 15270,	65270, 65269 },
		{ 15271,	65272, 65271 },
		{ 1575, 	65276, 65275 },
		{ 1573, 	65274, 65273 }
	};

	public static char[][] ARABIC_GLYPH = {
		{ 1569, 65152, 65163, 65164, 65152, 3 },
		{ 1570, 65153, 65153, 65154, 65154, 2 },
		{ 1571, 65155, 65155, 65156, 65156, 2 },
		{ 1572, 65157, 65157, 65158, 65158, 2 },
		{ 1573, 65159, 65159, 65160, 65160, 2 },
		{ 1574, 65161, 65161, 65162, 65162, 2 },
		{ 1575, 65165, 65165, 65166, 65166, 2 },
		{ 1576, 65167, 65169, 65170, 65168, 4 },
		{ 1577, 65171, 65171, 65172, 65172, 2 },
		{ 1578, 65173, 65175, 65176, 65174, 4 },
		{ 1579, 65177, 65179, 65180, 65178, 4 },
		{ 1580, 65181, 65183, 65184, 65182, 4 },
		{ 1581, 65185, 65187, 65188, 65186, 4 },
		{ 1582, 65189, 65191, 65192, 65190, 4 },
		{ 1583, 65193, 65193, 65194, 65194, 2 },
		{ 1584, 65195, 65195, 65196, 65196, 2 },
		{ 1585, 65197, 65197, 65198, 65198, 2 },
		{ 1586, 65199, 65199, 65200, 65200, 2 },
		{ 1587, 65201, 65203, 65204, 65202, 4 },
		{ 1588, 65205, 65207, 65208, 65206, 4 },
		{ 1589, 65209, 65211, 65212, 65210, 4 },
		{ 1590, 65213, 65215, 65216, 65214, 4 },
		{ 1591, 65217, 65219, 65218, 65220, 4 },
		{ 1592, 65221, 65223, 65222, 65222, 4 },
		{ 1593, 65225, 65227, 65228, 65226, 4 },
		{ 1594, 65229, 65231, 65232, 65230, 4 },
		{ 1601, 65233, 65235, 65236, 65234, 4 },
		{ 1602, 65237, 65239, 65240, 65238, 4 },
		{ 1603, 65241, 65243, 65244, 65242, 4 },
		{ 1604, 65245, 65247, 65248, 65246, 4 },
		{ 1605, 65249, 65251, 65252, 65250, 4 },
		{ 1606, 65253, 65255, 65256, 65254, 4 },
		{ 1607, 65257, 65259, 65260, 65258, 4 },
		{ 1608, 65261, 65261, 65262, 65262, 2 },
		{ 1609, 65263, 65263, 65264, 65264, 2 },
		{ 1610, 65265, 65267, 65268, 65266, 4 }
	};

	private int findGlyph(int iStart, int iEnd, char cTarget)
	{
		if (iStart < 0) {
			return -1;
		}
		if (iEnd > 35) {
			return -1;
		}
		int iMiddle = iStart + ((iEnd - iStart) / 2);
		if (iMiddle < iEnd) {
			if (ARABIC_GLYPH[iMiddle + 1][0] == cTarget) {
				return iMiddle + 1;
			}
		}
		if (ARABIC_GLYPH[iMiddle][0] == cTarget) {
			return iMiddle;
		}
		if ((iEnd - iMiddle) == 1) {
			return -1;
		}
		if (ARABIC_GLYPH[iMiddle][0] < cTarget) {
			return findGlyph(iMiddle, iEnd, cTarget);
		}
		if (ARABIC_GLYPH[iMiddle][0] > cTarget) {
			return findGlyph(iStart, iMiddle, cTarget);
		}

		return -1;
	}

	private char getReshapedGlyph(char cTarget, int iLocation)
	{
		int iFound = findGlyph(0, 35, cTarget);
		if (iFound == -1) {
			return cTarget;
		}
		return ARABIC_GLYPH[iFound][iLocation];
	}

	private int getGlyphTypeBefore(String wholeTarget, int iLocation)
	{
		if (iLocation == 0) {
			return 2;
		}
		char cTarget = wholeTarget.charAt(iLocation);
		int foundGlyph = findGlyph(0, 35, cTarget);
		if (foundGlyph == -1) {
			iLocation--;
			return getGlyphTypeBefore(wholeTarget, iLocation);
		}
		return ARABIC_GLYPH[foundGlyph][5];
	}

	private int getGlyphType(String wholeTarget, int iLocation)
	{
		char cTarget = wholeTarget.charAt(iLocation);

		int nn = findGlyph(0, 35, cTarget);
		if (nn == -1) {
			return 2;
		}
		return ARABIC_GLYPH[nn][5];
	}

	private char getLamAlef(char candidateAlef, char candidateLam, boolean isEndOfWord)
	{
		int shiftRate = 1;
		char reshapedLamAlef = 0;
		if (isEndOfWord) {
			shiftRate++;
		}

		if ((int)ORGINAL_LAM == (int)candidateLam) {
			if ((int)candidateAlef == (int)ORGINAL_ALF_UPPER_MDD) {
				reshapedLamAlef = LAM_ALEF_GLYPH[0][shiftRate];
			}
			if ((int)candidateAlef == (int)ORGINAL_ALF_UPPER_HAMAZA) {
				reshapedLamAlef = LAM_ALEF_GLYPH[1][shiftRate];
			}
			if ((int)candidateAlef == (int)ORGINAL_ALF_LOWER_HAMAZA) {
				reshapedLamAlef = LAM_ALEF_GLYPH[3][shiftRate];
			}
			if ((int)candidateAlef == (int)ORGINAL_ALF) {
				reshapedLamAlef = LAM_ALEF_GLYPH[2][shiftRate];
			}
		}

		return reshapedLamAlef;
	}

	public ArabicReshaper(String unshapedWord)
	{
		m_strReturn = reshapeIt(unshapedWord);
	}

	public ArabicReshaper(String unshapedWord, boolean supportAlefLam)
	{
		if (!supportAlefLam) {
			m_strReturn = reshapeIt(unshapedWord);
		}
		else {
			m_strReturn = reshapeItWithLamAlef(unshapedWord);
		}
	}

	public String reshapeIt(String unshapedWord)
	{
		StringBuffer reshapedWord=new StringBuffer("");
		int wordLength = unshapedWord.length();
		char[] wordLetters = new char[wordLength];

		unshapedWord.getChars(0, wordLength, wordLetters, 0);
		reshapedWord.append(getReshapedGlyph(wordLetters[0], 2));	//2 is the Form when the Letter is at the start of the word

		for (int ii = 1; ii < wordLength - 1; ii++) {
			int beforeLast = ii - 1;
			if (getGlyphTypeBefore(unshapedWord,beforeLast) == 2) {
				reshapedWord.append(getReshapedGlyph(wordLetters[ii], 2));
			}
			else {
				reshapedWord.append(getReshapedGlyph(wordLetters[ii], 3));
			}
		}
		if (getGlyphTypeBefore(unshapedWord, wordLength - 2) == 2) {
			reshapedWord.append(getReshapedGlyph(wordLetters[wordLength - 1], 1));
		}
		else {
			reshapedWord.append(getReshapedGlyph(wordLetters[wordLength - 1], 4));
		}

		return reshapedWord.toString();
	}

	public String reshapeItWithLamAlef(String unshapedWord)
	{
		StringBuffer reshapedWord = new StringBuffer("");
		int wordLength = unshapedWord.length();
		char[] wordLetters = new char[wordLength];
		char[] reshapedLetters = new char[wordLength];
		char lamIndicator = 43; //The '+'

		unshapedWord.getChars(0, wordLength, wordLetters,0 );

		if (wordLength == 0) {
			return "";
		}
		if (wordLength == 1) {
			return getReshapedGlyph(wordLetters[0], 1) + "";
		}
		if (wordLength == 2) {
			char lam = wordLetters[0];
			char alef = wordLetters[1];
			if (getLamAlef(alef, lam, true) > 0) {
				return (char)getLamAlef(alef, lam, true) + " ";
			}
		}

		reshapedLetters[0] = getReshapedGlyph(wordLetters[0], 2);

		char currentLetter = wordLetters[0];

		for (int ii = 1; ii < wordLength - 1; ii++) {
			if (getLamAlef(wordLetters[ii], currentLetter, true) > 0) {
				if (getGlyphTypeBefore(unshapedWord, ii - 2) == 2) {
					reshapedLetters[ii - 1] = lamIndicator;
					reshapedLetters[ii] = (char)getLamAlef(wordLetters[ii], currentLetter, true);
				}
				else {
					reshapedLetters[ii - 1] = lamIndicator;
					reshapedLetters[ii] = (char)getLamAlef(wordLetters[ii], currentLetter, false);
				}
			}
			else {
				int beforeLast = ii - 1;
				if (getGlyphTypeBefore(unshapedWord, beforeLast) == 2) {
					reshapedLetters[ii] = getReshapedGlyph(wordLetters[ii], 2);
				}
				else {
					reshapedLetters[ii] = getReshapedGlyph(wordLetters[ii], 3);
				}
			}
			currentLetter = wordLetters[ii];
		}

		if (getLamAlef(wordLetters[wordLength - 1], wordLetters[wordLength - 2], true) > 0) {
			if (getGlyphType(unshapedWord,wordLength - 3) == 2) {
				reshapedLetters[wordLength-2]=lamIndicator;
				reshapedLetters[wordLength-1]=(char)getLamAlef(wordLetters[wordLength - 1], wordLetters[wordLength - 2], true);
			}
			else {
				reshapedLetters[wordLength - 2] = lamIndicator;
				reshapedLetters[wordLength - 1] = (char)getLamAlef(wordLetters[wordLength - 1], wordLetters[wordLength - 2], false);
			}
		}
		else {
			if (getGlyphType(unshapedWord,wordLength - 2) == 2) {
				reshapedLetters[wordLength - 1] = getReshapedGlyph(wordLetters[wordLength - 1], 1);
			}
			else {
				reshapedLetters[wordLength - 1] = getReshapedGlyph(wordLetters[wordLength - 1], 4);
			}
		}

		for (int ii = 0; ii < reshapedLetters.length; ii++) {
			if (reshapedLetters[ii] != lamIndicator) {
				reshapedWord.append(reshapedLetters[ii]);
			}
		}

		return reshapedWord.toString();
	}

}