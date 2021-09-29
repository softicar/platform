package com.softicar.platform.common.core.item;

import java.util.Comparator;

/**
 * A simple comparator that is based on the ID of the basic items.
 *
 * @author Oliver Richers
 */
public final class BasicItemComparator implements Comparator<IBasicItem> {

	private static final BasicItemComparator INSTANCE = new BasicItemComparator();

	private BasicItemComparator() {

		// private to make it a singleton
	}

	public static BasicItemComparator get() {

		return INSTANCE;
	}

	@Override
	public int compare(IBasicItem item1, IBasicItem item2) {

		return item1.getItemId().compareTo(item2.getItemId());
	}
}
