package com.softicar.platform.common.io.file;

import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.core.logging.StatisticLog;
import java.io.File;
import java.io.FileFilter;
import java.util.Collection;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * A file finder that searches for files recursively.
 *
 * @author Oliver Richers
 */
public class FileFinder {

	/**
	 * Collects all files in the specified folders that satisfy the specified
	 * filter.
	 *
	 * @param folders
	 *            collection of folder names to scan
	 * @param filter
	 *            a java file filter object, deciding which files to return
	 * @return a sorted map, that maps from the filename (without path) to the
	 *         associated file object
	 */
	public static SortedMap<String, File> collectFiles(Collection<String> folders, FileFilter filter) {

		SortedMap<String, File> filemap = new TreeMap<>();
		StatisticLog.start("Collecting files");
		for (String folder: folders) {
			addFilesRecursively(new File(folder), filter, filemap);
		}
		StatisticLog.stop("Collecting files");
		return filemap;
	}

	/**
	 * This function is equivalent to collectFiles(Collection folders,
	 * FileFilter filter) except that this function takes only one folder name.
	 *
	 * @see #collectFiles(Collection folders, FileFilter filter)
	 */
	public static SortedMap<String, File> collectFiles(String dirname, FileFilter filter) {

		SortedMap<String, File> filemap = new TreeMap<>();
		StatisticLog.start("Collecting files");
		addFilesRecursively(new File(dirname), filter, filemap);
		StatisticLog.stop("Collecting files");
		return filemap;
	}

	private static void addFilesRecursively(File file, FileFilter filter, Map<String, File> filemap) {

		if (file.isDirectory()) {
			Log.verbose("Entering directory " + file.getPath());
			File[] files = file.listFiles();
			for (int i = 0; i != files.length; ++i) {
				addFilesRecursively(files[i], filter, filemap);
			}
		} else if (filter.accept(file)) {
			filemap.put(file.getName().toUpperCase(), file);
			StatisticLog.inc("Collecting files");
		}
	}
}
