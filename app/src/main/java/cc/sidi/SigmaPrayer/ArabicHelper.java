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

public class ArabicHelper
{

	private static boolean isArabicCharacter(char cTarget)
	{
		for (int ii = 0; ii < ArabicReshaper.ARABIC_GLYPH.length; ii++) {
			if (ArabicReshaper.ARABIC_GLYPH[ii][0] == cTarget) {
				return true;
			}
		}

		return false;
	}

	private static String[] getWords(String strSentence)
	{
		if (strSentence != null) {
			return strSentence.split("\\s");
		}
		else {
			return new String[0];
		}
	}

	public static boolean hasArabicLetters(String strWord)
	{
		for (int ii = 0; ii < strWord.length(); ii++) {
			if (isArabicCharacter(strWord.charAt(ii))) {
				return true;
			}
		}
		return false;
	}

	public static boolean isArabicWord(String strWord)
	{
		for (int ii = 0; ii < strWord.length(); ii++) {
			if (!isArabicCharacter(strWord.charAt(ii))) {
				return false;
			}
		}
		return true;
	}

	private static String[] getWordsFromMixedWord(String strWord)
	{
		ArrayList<String> finalWords = new ArrayList<String>();

		String tempWord = "";

		for (int ii = 0; ii < strWord.length(); ii++) {
			if (isArabicCharacter(strWord.charAt(ii))) {
				if (!tempWord.equals("") && !isArabicWord(tempWord)) {
					finalWords.add(tempWord);
					tempWord = "" + strWord.charAt(ii);

				}
				else {
					tempWord += strWord.charAt(ii);
				}

			}
			else {
				if (!tempWord.equals("") && isArabicWord(tempWord)) {
					finalWords.add(tempWord);
					tempWord = "" + strWord.charAt(ii);

				}
				else {
					tempWord += strWord.charAt(ii);
				}
			}
		}

		String[] theWords = new String[finalWords.size()];
		theWords = (String[])finalWords.toArray(theWords);

		return theWords;
	}

	public static String reshape(String allText)
	{
		if (allText != null) {
			StringBuffer result = new StringBuffer();
			String[] sentences = allText.split("\n");
			for (int ii = 0; ii < sentences.length; ii++) {
				result.append(reshapeSentence(sentences[ii]));
				if (ii != (sentences.length - 1)) {
					result.append("\n");
				}
			}
			return result.toString();
		}
		else {
			return null;
		}
	}

	public static String reshapeSentence(String strSentence)
	{
		String[] words = getWords(strSentence);
		StringBuffer reshapedText = new StringBuffer("");

		for (int ii = 0; ii < words.length; ii++) {
			if (hasArabicLetters(words[ii])) {
				if (isArabicWord(words[ii])) {
					ArabicReshaper arabicReshaper = new ArabicReshaper(words[ii], true);
					reshapedText.append(arabicReshaper.getReshapedWord());
				}
				else {
					String [] mixedWords = getWordsFromMixedWord(words[ii]);
					for (int jj = 0; jj < mixedWords.length; jj++) {
						ArabicReshaper arabicReshaper = new ArabicReshaper(mixedWords[jj], true);
						reshapedText.append(arabicReshaper.getReshapedWord());
					}
				}
			}
			else {
				reshapedText.append(words[ii]);
			}

			reshapedText.append(" ");
		}

		return reshapedText.toString();
	}

}