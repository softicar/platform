package com.softicar.platform.dom.input;

import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.DomDocument;
import org.junit.Assert;
import org.junit.Test;

public class DomSelectTest extends Assert {

	private final DomSelect<?> select;

	public DomSelectTest() {

		CurrentDomDocument.set(new DomDocument());

		this.select = new DomSelect<>();
	}

	@Test
	public void testIsMultipleAfterConstruction() {

		assertFalse(select.isMultiple());
	}

	@Test
	public void testIsMultipleAfterSetMultipleWithTrue() {

		select.setMultiple(true);

		assertTrue(select.isMultiple());
	}

	@Test
	public void testIsMultipleAfterSetMultipleWithFalse() {

		select.setMultiple(true);
		select.setMultiple(false);

		assertFalse(select.isMultiple());
	}
}
