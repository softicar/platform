package com.softicar.platform.common.container.data.table.in.memory;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.core.i18n.IDisplayString;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Function;

/**
 * A builder for instances of {@link IDataTableColumn}.
 * <p>
 * Actually, this builder does not only create new columns but it adds them
 * directly to the given {@link InMemoryDataTableStructure}.
 *
 * @author Oliver Richers
 */
public class InMemoryDataTableColumnBuilder<R, V> {

	private final InMemoryDataTableStructure<R> structure;
	private final Class<V> valueClass;
	private Function<R, V> valueGetter;
	private IDisplayString title;
	private Optional<Comparator<V>> valueComparator;

	/**
	 * Constructs a new instance of this builder.
	 *
	 * @param structure
	 *            the structure that the created column shall be added to
	 * @param valueClass
	 *            the class of the column values
	 */
	public InMemoryDataTableColumnBuilder(InMemoryDataTableStructure<R> structure, Class<V> valueClass) {

		this.structure = structure;
		this.valueClass = valueClass;
		this.valueComparator = new InMemoryDataTableColumnInternalValueComparatorFactory<V>().create(valueClass);
	}

	public class GetterSetter {

		/**
		 * Defines the {@link Comparator} for the column values.
		 *
		 * @param comparator
		 *            the value comparator (never null)
		 */
		public GetterSetter setComparator(Optional<Comparator<V>> comparator) {

			valueComparator = comparator;
			return this;
		}

		/**
		 * Defines the {@link Comparator} for the column values.
		 *
		 * @param comparator
		 *            the value comparator (never null)
		 */
		public GetterSetter setComparator(Comparator<V> comparator) {

			valueComparator = Optional.of(comparator);
			return this;
		}

		/**
		 * Defines the getter function to extract a column value from a row.
		 *
		 * @param getter
		 *            the value getter (never null)
		 */
		public TitleSetter setGetter(Function<R, V> getter) {

			valueGetter = getter;
			return new TitleSetter();
		}
	}

	public class TitleSetter {

		/**
		 * Defines the title of the column.
		 *
		 * @param displayString
		 *            title display string (never null)
		 */
		public ColumnAdder setTitle(IDisplayString displayString) {

			title = displayString;
			return new ColumnAdder();
		}
	}

	public class ColumnAdder {

		/**
		 * Creates column instance and adds it to the table structure.
		 *
		 * @return the created and added column
		 */
		public IDataTableColumn<R, V> addColumn() {

			return structure.addColumn(InMemoryDataTableColumnBuilder.this);
		}
	}

	protected Function<R, V> getValueGetter() {

		return valueGetter;
	}

	protected Class<V> getValueClass() {

		return valueClass;
	}

	protected Optional<Comparator<V>> getValueComparator() {

		return valueComparator;
	}

	protected IDisplayString getTitle() {

		return title;
	}
}
