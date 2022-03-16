package com.softicar.platform.common.string.formatting;

/**
 * This class provides static formatting methods for memory.
 *
 * @author Oliver Richers
 */
public class MemoryFormatting {

	private static final double MEMORY_UNIT = 1024;
	private static final double MEMORY_UNIT_THRESHOLD = 1 * MEMORY_UNIT;
	private static final String[] MEMORY_UNITS = { "B", "KiB", "MiB", "GiB", "TiB", "PiB", "EiB" };

	public static String formatMemory(long bytes, int precision) {

		int unitIndex = 0;
		double value = bytes < 0? -bytes : bytes;
		while (value >= MEMORY_UNIT_THRESHOLD && unitIndex < MEMORY_UNITS.length - 1) {
			value /= MEMORY_UNIT;
			++unitIndex;
		}

		String format = "%." + precision + "f";
		return (bytes < 0? "-" : "") + String.format(format, value) + " " + MEMORY_UNITS[unitIndex];
	}
}
