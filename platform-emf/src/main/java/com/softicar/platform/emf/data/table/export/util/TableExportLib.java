package com.softicar.platform.emf.data.table.export.util;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.string.regex.PatternFinder;
import com.softicar.platform.dom.elements.DomTable;
import com.softicar.platform.dom.elements.tables.pageable.DomPageableTable;
import com.softicar.platform.dom.elements.tables.scrollable.DomScrollableTable;
import java.util.Set;
import java.util.TreeSet;

/**
 * Provides common functionality and constants for the table export framework.
 *
 * @author Alexander Schmidt
 */
public class TableExportLib {

	public static final String FILE_NAME_INVALID_CHARS = "\\/:*?\"<>|";
	public static final String DEFLATE_COMPRESSION_FILE_NAME_EXTENSION = "zip";

	// ----

	public static boolean validateFileName(String fileName) {

		for (char c: FILE_NAME_INVALID_CHARS.toCharArray()) {
			if (fileName.indexOf(c) > -1) {
				return false;
			}
		}

		return true;
	}

	/**
	 * @param numElements
	 * @return A Set containing all Integer numbers in [0, numElements).
	 */
	public static Set<Integer> createIndexSet(int numElements) {

		Set<Integer> set = new TreeSet<>();

		for (int i = 0; i < numElements; i++) {
			set.add(i);
		}

		return set;
	}

	/**
	 * Throws an exception if the given table is a {@link DomScrollableTable}
	 * but NOT a {@link DomPageableTable}. Exporting such tables is unsupported.
	 *
	 * @param table
	 */
	public static void assertPageableIfScrollable(DomTable table) {

		if (table instanceof DomScrollableTable && !(table instanceof DomPageableTable)) {
			throw new SofticarDeveloperException(
				"If the given table is a %s, it must be a %s.",
				DomScrollableTable.class.getSimpleName(),
				DomPageableTable.class.getSimpleName());
		}
	}

	/**
	 * If the given file name ends with a dot and a 3- or 4-letter extension,
	 * those are removed.
	 *
	 * @param fileName
	 * @return The given file name, stripped off its extension, if any. Null if
	 *         the given file name was null.
	 */
	public static String removeFileNameSuffixIfPresent(String fileName) {

		if (fileName != null) {
			// remove 3-/4-letter file name suffix, if present
			int suffixIndex = PatternFinder.indexOfPattern("\\.[a-zA-Z]{3,4}$", fileName).getOffset();
			if (suffixIndex >= 0) {
				fileName = fileName.substring(0, suffixIndex);
			}
			return fileName;
		}

		else {
			return null;
		}
	}
}
