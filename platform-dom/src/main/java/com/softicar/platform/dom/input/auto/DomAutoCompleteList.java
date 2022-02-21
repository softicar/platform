package com.softicar.platform.dom.input.auto;

import com.softicar.platform.dom.utils.JavascriptEscaping;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A list of item names and descriptions for auto-complete input elements.
 *
 * @author Oliver Richers
 */
public class DomAutoCompleteList implements Iterable<String> {

	public static final int MAXIMUM_ELEMENT_COUNT = 16;
	private final List<String> items;

	public DomAutoCompleteList() {

		this.items = new ArrayList<>();
	}

	/**
	 * Adds the specified item with its name and description.
	 *
	 * @param name
	 *            the name of the item (never null)
	 * @param description
	 *            the description of the value (may be any string)
	 */
	public void add(String name, String description) {

		if (items.size() < MAXIMUM_ELEMENT_COUNT) {
			items.add(String.format("{v:'%s',d:'%s'}", JavascriptEscaping.getEscaped(name), JavascriptEscaping.getEscaped(description)));
		}
	}

	@Override
	public Iterator<String> iterator() {

		return items.iterator();
	}

	/**
	 * Returns the number of items
	 *
	 * @return the item count
	 */
	public int size() {

		return items.size();
	}

	public boolean isEmpty() {

		return size() == 0;
	}
}
