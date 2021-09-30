package com.softicar.platform.dom.refresh.bus;

import org.junit.Assert;
import org.junit.Test;

public class DomRefreshBusEventTest extends Assert {

	private final DomRefreshBusEvent event;

	public DomRefreshBusEventTest() {

		this.event = new DomRefreshBusEvent();
	}

	@Test
	public void testSetAllChanged() {

		event.setAllChanged();

		assertTrue(event.isAllChanged());
		assertTrue(event.isAnyObjectChanged(String.class));
		assertTrue(event.isChanged("foo"));
	}

	@Test
	public void testSetAllChangedWithClass() {

		event.setAllChanged(String.class);

		assertFalse(event.isAllChanged());

		assertTrue(event.isAnyObjectChanged(String.class));
		assertFalse(event.isAnyObjectChanged(Integer.class));

		assertTrue(event.isChanged("foo"));
		assertFalse(event.isChanged(42));
	}

	@Test
	public void testSetChanged() {

		String foo = "foo";
		event.setChanged(foo);

		assertFalse(event.isAllChanged());

		assertTrue(event.isAnyObjectChanged(String.class));
		assertFalse(event.isAnyObjectChanged(Integer.class));

		assertTrue(event.isChanged(foo));
		assertFalse(event.isChanged("bar"));
		assertFalse(event.isChanged(42));
	}
}
