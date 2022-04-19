package com.softicar.platform.dom.elements.select.value.simple.display;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.common.core.item.IBasicItem;
import com.softicar.platform.common.testing.AbstractTest;
import java.util.Comparator;
import java.util.Objects;
import org.junit.Test;

public class DomSimpleValueSelectDefaultValueComparatorTest extends AbstractTest {

	@Test
	public void testWithStrings() {

		Comparator<String> comparator = comparator();

		assertTrue(comparator.compare("foo", "foo") == 0);
		assertTrue(comparator.compare("bar", "foo") < 0);
		assertTrue(comparator.compare("foo", "bar") > 0);

		assertTrue(comparator.compare(null, null) == 0);
		assertTrue(comparator.compare(null, "null") < 0);
		assertTrue(comparator.compare("null", null) > 0);
	}

	@Test
	public void testWithDisplayable() {

		Comparator<IDisplayable> comparator = comparator();

		assertTrue(comparator.compare(displayable("foo"), displayable("foo")) == 0);
		assertTrue(comparator.compare(displayable("bar"), displayable("foo")) < 0);
		assertTrue(comparator.compare(displayable("foo"), displayable("bar")) > 0);

		assertTrue(comparator.compare(null, null) == 0);
		assertTrue(comparator.compare(null, displayable("null")) < 0);
		assertTrue(comparator.compare(displayable("null"), null) > 0);
	}

	@Test
	public void testWithBasicItem() {

		Comparator<IBasicItem> comparator = comparator();

		assertTrue(comparator.compare(basicItem(22), basicItem(22)) == 0);
		assertTrue(comparator.compare(basicItem(11), basicItem(22)) < 0);
		assertTrue(comparator.compare(basicItem(22), basicItem(11)) > 0);

		assertTrue(comparator.compare(null, null) == 0);
		assertTrue(comparator.compare(null, basicItem(11)) < 0);
		assertTrue(comparator.compare(basicItem(11), null) > 0);
	}

	@Test
	public void testWithDisplayableBasicItem() {

		Comparator<IDisplayableBasicItem> comparator = comparator();

		assertTrue(comparator.compare(displayableBasicItem(22, "foo"), displayableBasicItem(22, "foo")) == 0);
		assertTrue(comparator.compare(displayableBasicItem(11, "bar"), displayableBasicItem(22, "foo")) < 0);
		assertTrue(comparator.compare(displayableBasicItem(22, "foo"), displayableBasicItem(11, "bar")) > 0);

		assertTrue(comparator.compare(displayableBasicItem(22, "foo"), displayableBasicItem(22, "xxx")) < 0);
		assertTrue(comparator.compare(displayableBasicItem(22, "xxx"), displayableBasicItem(22, "foo")) > 0);

		assertTrue(comparator.compare(displayableBasicItem(11, "foo"), displayableBasicItem(22, "foo")) < 0);
		assertTrue(comparator.compare(displayableBasicItem(22, "foo"), displayableBasicItem(11, "foo")) > 0);

		assertTrue(comparator.compare(null, null) == 0);
		assertTrue(comparator.compare(null, displayableBasicItem(11, "bar")) < 0);
		assertTrue(comparator.compare(displayableBasicItem(11, "bar"), null) > 0);
	}

	private static <V> DomSimpleValueSelectDefaultValueComparator<V> comparator() {

		return new DomSimpleValueSelectDefaultValueComparator<>(() -> new DomSimpleValueSelectDefaultDisplayStringFunction<>());
	}

	private static IDisplayable displayable(String string) {

		return new Displayable(string);
	}

	private static IBasicItem basicItem(int id) {

		return new BasicItem(id);
	}

	private static IDisplayableBasicItem displayableBasicItem(int id, String string) {

		return new DisplayableBasicItem(id, string);
	}

	private static class Displayable implements IDisplayable {

		private final String string;

		public Displayable(String string) {

			this.string = Objects.requireNonNull(string);
		}

		@Override
		public IDisplayString toDisplay() {

			return IDisplayString.create(string);
		}
	}

	private static class BasicItem implements IBasicItem {

		private final int id;

		public BasicItem(int id) {

			this.id = id;
		}

		@Override
		public Integer getId() {

			return id;
		}

		@Override
		public int compareTo(IBasicItem other) {

			return getId().compareTo(other.getId());
		}
	}

	private static interface IDisplayableBasicItem extends IBasicItem, IDisplayable {

		// nothing
	}

	private static class DisplayableBasicItem implements IDisplayableBasicItem {

		private final int id;
		private final String string;

		public DisplayableBasicItem(int id, String string) {

			this.id = id;
			this.string = Objects.requireNonNull(string);
		}

		@Override
		public IDisplayString toDisplay() {

			return IDisplayString.create(string);
		}

		@Override
		public Integer getId() {

			return id;
		}

		@Override
		public int compareTo(IBasicItem other) {

			return getId().compareTo(other.getId());
		}
	}
}
