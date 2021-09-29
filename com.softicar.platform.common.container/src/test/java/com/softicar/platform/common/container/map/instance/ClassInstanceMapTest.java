package com.softicar.platform.common.container.map.instance;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Test;

public class ClassInstanceMapTest extends Assert {

	private final ClassInstanceMap<Object> map;

	public ClassInstanceMapTest() {

		this.map = new ClassInstanceMap<>();
	}

	@Test
	public void testPutInstance() {

		map.putInstance("foo");
		map.putInstance("bar");

		Collection<Object> elements = map.getAll();
		assertEquals(1, elements.size());
		assertEquals("bar", elements.iterator().next());
	}

	@Test
	public void testPutInstanceIfAbsent() {

		map.putInstanceIfAbsent("foo");
		map.putInstanceIfAbsent("bar");

		Collection<Object> elements = map.getAll();
		assertEquals(1, elements.size());
		assertEquals("foo", elements.iterator().next());
	}

	@Test
	public void testGetOrPutInstance() {

		map.putInstance("foo");
		String string = map.getOrPutInstance(String.class, () -> "bar");
		Integer integer = map.getOrPutInstance(Integer.class, () -> 33);

		assertEquals("foo", string);
		assertEquals(33, integer.intValue());
	}

	@Test(expected = SofticarDeveloperException.class)
	public void testGetOrPutInstanceWithWrongClass() {

		map.getOrPutInstance(List.class, ArrayList::new);
	}

	@Test
	public void testGetOptional() {

		map.putInstance("foo");
		Optional<String> optionalString = map.getOptional(String.class);
		Optional<Integer> optionalInteger = map.getOptional(Integer.class);

		assertTrue(optionalString.isPresent());
		assertEquals("foo", optionalString.get());
		assertFalse(optionalInteger.isPresent());
	}
}
