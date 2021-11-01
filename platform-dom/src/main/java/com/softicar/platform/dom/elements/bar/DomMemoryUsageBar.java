package com.softicar.platform.dom.elements.bar;

import com.softicar.platform.common.string.formatting.MemoryFormatting;

/**
 * Displays a {@link DomPercentageBar} representing the memory usage.
 *
 * @author Oliver Richers
 */
public class DomMemoryUsageBar extends DomPercentageBar {

	public DomMemoryUsageBar(long usage, long total) {

		super(usage / (double) total);

		setTitle(String.format("%s/%s", MemoryFormatting.formatMemory(usage, 1), MemoryFormatting.formatMemory(total, 1)));
	}
}
