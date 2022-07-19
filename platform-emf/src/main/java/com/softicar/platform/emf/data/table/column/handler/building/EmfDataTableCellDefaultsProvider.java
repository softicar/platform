package com.softicar.platform.emf.data.table.column.handler.building;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.dom.styles.CssTextAlign;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

/**
 * Defines and provides default {@link IEmfDataTableCellBuilder} and
 * {@link CssTextAlign}, depending on the value-class of
 * {@link IDataTableColumn}s.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class EmfDataTableCellDefaultsProvider {

	private static final EmfDataTableCellDefaultsProvider SINGLETON = new EmfDataTableCellDefaultsProvider();

	private final ClassMap<IEmfDataTableCellBuilder<?>> builders;
	private final ClassMap<CssTextAlign> textAligns;

	public static EmfDataTableCellDefaultsProvider get() {

		return SINGLETON;
	}

	private EmfDataTableCellDefaultsProvider() {

		this.builders = new ClassMap<>(new EmfDataTableObjectCellBuilder<>());
		this.textAligns = new ClassMap<>(CssTextAlign.LEFT);

		set(Boolean.class, CssTextAlign.CENTER, new EmfDataTableBooleanCellBuilder());
		set(Integer.class, CssTextAlign.RIGHT, new EmfDataTableObjectCellBuilder<>());
		set(Long.class, CssTextAlign.RIGHT, new EmfDataTableObjectCellBuilder<>());
		set(Float.class, CssTextAlign.RIGHT, new EmfDataTableFloatCellBuilder());
		set(Double.class, CssTextAlign.RIGHT, new EmfDataTableDoubleCellBuilder());
		set(BigDecimal.class, CssTextAlign.RIGHT, new EmfDataTableBigDecimalCellBuilder());
		set(Number.class, CssTextAlign.RIGHT, new EmfDataTableNumberCellBuilder());
		set(Day.class, CssTextAlign.CENTER, new EmfDataTableDisplayableCellBuilder<>());
		set(DayTime.class, CssTextAlign.CENTER, new EmfDataTableDisplayableCellBuilder<>());
		set(String.class, CssTextAlign.LEFT, new EmfDataTableObjectCellBuilder<>());
		set(IEntity.class, CssTextAlign.LEFT, new EmfDataTableEntityCellBuilder<>());
		set(IDisplayable.class, CssTextAlign.LEFT, new EmfDataTableDisplayableCellBuilder<>());
	}

	/**
	 * Returns the default {@link IEmfDataTableCellBuilder} for the value type
	 * of the given {@link IDataTableColumn}.
	 * <p>
	 * Defaults to {@link EmfDataTableObjectCellBuilder}.
	 *
	 * @param column
	 *            the {@link IDataTableColumn} for which the default
	 *            {@link IEmfDataTableCellBuilder} is required
	 * @return the default {@link IEmfDataTableCellBuilder} for the value type
	 *         of the given {@link IDataTableColumn} (never null)
	 */
	public <V> IEmfDataTableCellBuilder<V> getBuilder(IDataTableColumn<?, V> column) {

		return CastUtils.cast(builders.getValue(column));
	}

	/**
	 * Returns the default {@link CssTextAlign} for the value type of the given
	 * {@link IDataTableColumn}.
	 * <p>
	 * Defaults to {@link CssTextAlign#LEFT}.
	 *
	 * @param column
	 *            the {@link IDataTableColumn} for which the default
	 *            {@link CssTextAlign} is required
	 * @return the default {@link CssTextAlign} for the value type of the given
	 *         {@link IDataTableColumn} (never null)
	 */
	public CssTextAlign getTextAlign(IDataTableColumn<?, ?> column) {

		return textAligns.getValue(column);
	}

	private <T> void set(Class<T> valueClass, CssTextAlign textAlign, IEmfDataTableCellBuilder<T> builder) {

		this.builders.put(valueClass, builder);
		this.textAligns.put(valueClass, textAlign);
	}

	private static class ClassMap<V> {

		private final Map<Class<?>, V> map;
		private final V defaultValue;

		public ClassMap(V defaultValue) {

			this.map = new TreeMap<>(new ClassByCanonicalNameComparator());
			this.defaultValue = defaultValue;
		}

		public void put(Class<?> clazz, V value) {

			map.put(clazz, value);
		}

		public V getValue(IDataTableColumn<?, ?> column) {

			return getValueFromDirectClass(column)//
				.orElse(
					getValueFromAssignableClass(column)//
						.orElse(defaultValue));
		}

		private Optional<V> getValueFromDirectClass(IDataTableColumn<?, ?> column) {

			return Optional.ofNullable(map.get(column.getValueClass()));
		}

		private Optional<V> getValueFromAssignableClass(IDataTableColumn<?, ?> column) {

			return map//
				.entrySet()
				.stream()
				.filter(entry -> entry.getKey().isAssignableFrom(column.getValueClass()))
				.map(entry -> entry.getValue())
				.findFirst();
		}

		private static class ClassByCanonicalNameComparator implements Comparator<Class<?>> {

			@Override
			public int compare(Class<?> first, Class<?> second) {

				return Comparator//
					.comparing(Class<?>::getCanonicalName)
					.compare(first, second);
			}
		}
	}
}
