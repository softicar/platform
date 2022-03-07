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

	public static final int MAXIMUM_ELEMENT_TO_DISPLAY = 16;
	public static final int MAXIMUM_ELEMENTS_TO_LOAD = MAXIMUM_ELEMENT_TO_DISPLAY + 1;
	private final List<String> items;

	public DomAutoCompleteList() {

		this.items = new ArrayList<>();
	}

	/**
	 * Adds the specified item with its name and description.
	 *
	 * @param name
	 *            the name of the item (never null)
	 */
	public void add(String name) {

		if (items.size() < MAXIMUM_ELEMENTS_TO_LOAD) {
			items.add(String.format("{v:'%s'}", JavascriptEscaping.getEscaped(name)));
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
