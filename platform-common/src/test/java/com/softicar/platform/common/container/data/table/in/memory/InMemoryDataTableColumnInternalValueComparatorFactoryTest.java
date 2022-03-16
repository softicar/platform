package com.softicar.platform.common.container.data.table.in.memory;

import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.item.IBasicItem;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.Test;

public class InMemoryDataTableColumnInternalValueComparatorFactoryTest extends Assert {

	@Test
	public void testCreateWithEntity() {

		var entity1 = new TestEntity(1, "b");
		var entity2 = new TestEntity(2, "ć");
		var entity3 = new TestEntity(3, "a");
		var entity4 = new TestEntity(4, "d");

		var comparator = create(TestEntity.class);

		assertTrue(comparator.isPresent());
		assertEquals(//
			List.of(entity3, entity1, entity2, entity4),
			sort(comparator, List.of(entity1, entity2, entity3, entity4)));
	}

	@Test
	public void testCreateWithString() {

		var comparator = create(String.class);

		assertTrue(comparator.isPresent());
		assertEquals(//
			"_ . 1 a A Ä b B C ć Č d s S ß t T z",
			sort(comparator, "b B _ 1 ß s S t T z A a . C Č ć Ä d"));
	}

	@Test
	public void testCreateWithComparable() {

		var comparator = create(Integer.class);

		assertTrue(comparator.isPresent());
		assertEquals(//
			List.of(11, 22, 33),
			sort(comparator, List.of(22, 33, 11)));
	}

	@Test
	public void testCreateWithNonSortableType() {

		var comparator = create(Object.class);

		assertFalse(comparator.isPresent());
	}

	private <V> Optional<Comparator<V>> create(Class<V> valueClass) {

		return new InMemoryDataTableColumnInternalValueComparatorFactory<V>().create(valueClass);
	}

	private <V> List<V> sort(Optional<Comparator<V>> comparator, List<V> input) {

		assertTrue(comparator.isPresent());
		return input//
			.stream()
			.sorted(comparator.get())
			.collect(Collectors.toList());
	}

	private String sort(Optional<Comparator<String>> comparator, String input) {

		return sort(comparator, Arrays.asList(input.split(" ")))//
			.stream()
			.collect(Collectors.joining(" "));
	}

	private static class TestEntity implements IEntity {

		private final Integer id;
		private final String name;

		public TestEntity(Integer id, String name) {

			this.id = id;
			this.name = name;
		}

		@Override
		public Integer getId() {

			return id;
		}

		@Override
		public IDisplayString toDisplayWithoutId() {

			return IDisplayString.create(name);
		}

		@Override
		public int compareTo(IBasicItem other) {

			return id.compareTo(other.getId());
		}
	}
}
