package com.softicar.platform.emf.data.table.column.handler.building;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.styles.CssTextAlign;
import java.math.BigDecimal;
import java.util.Collection;
import org.junit.Assert;
import org.junit.Test;

public class EmfDataTableCellDefaultsProviderTest extends Assert {

	private final EmfDataTableCellDefaultsProvider provider;

	public EmfDataTableCellDefaultsProviderTest() {

		this.provider = EmfDataTableCellDefaultsProvider.get();
	}

	@Test
	public void testGetBuilderWithMatchingColumnValueClass() {

		TestDataTableColumn<BigDecimal> column = new TestDataTableColumn<>(BigDecimal.class);
		IEmfDataTableCellBuilder<BigDecimal> builder = provider.getBuilder(column);
		assertEquals(EmfDataTableBigDecimalCellBuilder.class, builder.getClass());
	}

	@Test
	public void testGetBuilderWithAssignableColumnValueClass() {

		TestDataTableColumn<TestNumber> column = new TestDataTableColumn<>(TestNumber.class);
		IEmfDataTableCellBuilder<TestNumber> builder = provider.getBuilder(column);
		assertEquals(EmfDataTableNumberCellBuilder.class, builder.getClass());
	}

	@Test
	public void testGetBuilderWithUnmatchedColumnValueClass() {

		// assumes that we have no explicit defaults for Object
		TestDataTableColumn<Object> column = new TestDataTableColumn<>(Object.class);
		IEmfDataTableCellBuilder<Object> builder = provider.getBuilder(column);
		assertEquals(EmfDataTableObjectCellBuilder.class, builder.getClass());
	}

	@Test
	public void testGetBuilderPrefersMatchingOverAssignableColumnValueClass() {

		TestDataTableColumn<Double> column = new TestDataTableColumn<>(Double.class);
		IEmfDataTableCellBuilder<Double> builder = provider.getBuilder(column);
		assertEquals(EmfDataTableDoubleCellBuilder.class, builder.getClass());
	}

	@Test
	public void testGetTextAlignWithMatchingColumnValueClass() {

		TestDataTableColumn<BigDecimal> column = new TestDataTableColumn<>(BigDecimal.class);
		CssTextAlign textAlign = provider.getTextAlign(column);
		assertEquals(CssTextAlign.RIGHT, textAlign);
	}

	@Test
	public void testGetTextAlignWithAssignableColumnValueClass() {

		TestDataTableColumn<TestNumber> column = new TestDataTableColumn<>(TestNumber.class);
		CssTextAlign textAlign = provider.getTextAlign(column);
		assertEquals(CssTextAlign.RIGHT, textAlign);
	}

	@Test
	public void testGetTextAlignWithUnmatchedColumnValueClass() {

		// assumes that we have no explicit defaults for Object
		TestDataTableColumn<Object> column = new TestDataTableColumn<>(Object.class);
		CssTextAlign textAlign = provider.getTextAlign(column);
		assertEquals(CssTextAlign.LEFT, textAlign);
	}

	@Test
	public void testGetTextAlignPrefersMatchingOverAssignableColumnValueClass() {

		TestDataTableColumn<Double> column = new TestDataTableColumn<>(Double.class);
		CssTextAlign textAlign = provider.getTextAlign(column);
		assertEquals(CssTextAlign.RIGHT, textAlign);
	}

	private class TestDataTableColumn<T> implements IDataTableColumn<Object, T> {

		private final Class<T> clazz;

		public TestDataTableColumn(Class<T> clazz) {

			this.clazz = clazz;
		}

		@Override
		public IDisplayString getTitle() {

			return IDisplayString.format("%s column", String.class.getSimpleName());
		}

		@Override
		public Class<T> getValueClass() {

			return clazz;
		}

		@Override
		public T getValue(Object tableRow) {

			throw new UnsupportedOperationException();
		}

		@Override
		public void prefetchData(Collection<T> values) {

			throw new UnsupportedOperationException();
		}
	}

	private class TestNumber extends Number {

		@Override
		public int intValue() {

			throw new UnsupportedOperationException();
		}

		@Override
		public long longValue() {

			throw new UnsupportedOperationException();
		}

		@Override
		public float floatValue() {

			throw new UnsupportedOperationException();
		}

		@Override
		public double doubleValue() {

			throw new UnsupportedOperationException();
		}
	}
}
