package com.softicar.platform.common.string.distance;

/**
 * This class computes the <i>Levenshtein</i> distance.
 * <p>
 * The Levenshtein distance is a metric for measuring the amount of difference
 * between two sequences (i.e., the so called edit distance). The Levenshtein
 * distance between two strings is given by the minimum number of operations
 * needed to transform one string into the other, where an operation is an
 * insertion, deletion, or substitution of a single character.
 * 
 * @see "http://en.wikipedia.org/wiki/Levenshtein_distance"
 * @author Torsten Sommerfeld
 * @author Oliver Richers
 */
public class LevenshteinDistance {

	/**
	 * Computes the <i>Levenshtein</i> distance between the given to strings.
	 * 
	 * @param src
	 *            the source string (not null)
	 * @param dest
	 *            the target string (not null)
	 * @return the <i>Levenshtein</i> distance between the given strings
	 */
	public static int computeDistance(String src, String dest) {

		int[][] d = new int[src.length() + 1][dest.length() + 1];
		int i, j, cost;
		char[] str1 = src.toCharArray();
		char[] str2 = dest.toCharArray();

		for (i = 0; i <= str1.length; i++) {
			d[i][0] = i;
		}
		for (j = 0; j <= str2.length; j++) {
			d[0][j] = j;
		}
		for (i = 1; i <= str1.length; i++) {
			for (j = 1; j <= str2.length; j++) {

				if (str1[i - 1] == str2[j - 1]) {
					cost = 0;
				} else {
					cost = 1;
				}

				d[i][j] = Math
					.min(
						d[i - 1][j] + 1,       // Deletion
						Math
							.min(
								d[i][j - 1] + 1,         // Insertion
								d[i - 1][j - 1] + cost)); // Substitution

				if ((i > 1) && (j > 1) && (str1[i - 1] == str2[j - 2]) && (str1[i - 2] == str2[j - 1])) {
					d[i][j] = Math.min(d[i][j], d[i - 2][j - 2] + cost);
				}
			}
		}
		return d[str1.length][str2.length];
	}
}
