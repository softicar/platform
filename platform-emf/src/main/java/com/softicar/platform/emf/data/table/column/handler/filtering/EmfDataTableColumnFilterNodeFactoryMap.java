package com.softicar.platform.emf.data.table.column.handler.filtering;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.dom.elements.DomEnumSelect;
import com.softicar.platform.dom.elements.input.DomDoubleInput;
import com.softicar.platform.dom.elements.input.DomFloatInput;
import com.softicar.platform.dom.elements.input.DomIntegerInput;
import com.softicar.platform.dom.elements.input.DomLongInput;
import com.softicar.platform.dom.elements.number.decimal.DomBigDecimalInput;
import com.softicar.platform.dom.elements.time.day.DomDayInput;
import com.softicar.platform.dom.elements.time.daytime.DomDayTimeInput;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import com.softicar.platform.emf.data.table.filter.IEmfDataTableFilterNode;
import com.softicar.platform.emf.data.table.filter.bool.EmfDataTableBooleanFilterNode;
import com.softicar.platform.emf.data.table.filter.entity.EmfDataTableEntityFilterNode;
import com.softicar.platform.emf.data.table.filter.enums.EmfDataTableEnumFilterNode;
import com.softicar.platform.emf.data.table.filter.string.EmfDataTableStringFilterNode;
import com.softicar.platform.emf.data.table.filter.value.EmfDataTableValueFilterNode;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EmfDataTableColumnFilterNodeFactoryMap {

	private static final EmfDataTableColumnFilterNodeFactoryMap SINGLETON = new EmfDataTableColumnFilterNodeFactoryMap();
	private final Map<Class<?>, IEmfDataTableColumnFilterNodeFactory<?>> factories;

	public static EmfDataTableColumnFilterNodeFactoryMap get() {

		return SINGLETON;
	}

	public EmfDataTableColumnFilterNodeFactoryMap() {

		this.factories = new HashMap<>();

		addFactory(BigDecimal.class, this::createBigDecimalFilterNode);
		addFactory(Boolean.class, this::createBooleanFilterNode);
		addFactory(Day.class, this::createDayFilterNode);
		addFactory(DayTime.class, this::createDayTimeFilterNode);
		addFactory(Double.class, this::createDoubleFilterNode);
		addFactory(Float.class, this::createFloatFilterNode);
		addFactory(Integer.class, this::createIntegerFilterNode);
		addFactory(Long.class, this::createLongFilterNode);
		addFactory(String.class, this::createStringFilterNode);
	}

	private <T> void addFactory(Class<T> valueClass, IEmfDataTableColumnFilterNodeFactory<T> factory) {

		factories.put(valueClass, factory);
	}

	/**
	 * Determines a suitable {@link IEmfDataTableColumnFilterNodeFactory}
	 * according to the value class of the given {@link IDataTableColumn}.
	 * <p>
	 * Performs dynamic type checking to provide specialized factories in the
	 * following cases:
	 * <ul>
	 * <li>Value class is {@link Enum} and {@link IDisplayable}:
	 * {@link DisplayableEnumFilterNodeFactory}</li>
	 * <li>Value class is {@link Enum}: {@link EnumFilterNodeFactory}</li>
	 * <li>Value class is {@link IEntity}: {@link EntityFilterNodeFactory}</li>
	 * </ul>
	 * Explicitly defined factories are always preferred.
	 *
	 * @return a suitable {@link IEmfDataTableColumnFilterNodeFactory} according
	 *         to the value class of the given {@link IDataTableColumn}
	 */
	public <T> IEmfDataTableColumnFilterNodeFactory<T> getFactory(IDataTableColumn<?, T> dataColumn) {

		Class<T> valueClass = dataColumn.getValueClass();
		IEmfDataTableColumnFilterNodeFactory<?> factory = factories.get(valueClass);
		if (factory == null) {
			if (isTypeEnum(valueClass)) {
				if (isTypeDisplayable(valueClass)) {
					factory = new DisplayableEnumFilterNodeFactory<>();
				} else {
					factory = new EnumFilterNodeFactory<>();
				}
			} else if (isTypeEntity(valueClass)) {
				factory = new EntityFilterNodeFactory<>();
			}
		}
		return CastUtils.cast(factory);
	}

	private <T> boolean isTypeEnum(Class<T> valueClass) {

		return valueClass.isEnum();
	}

	private <T> boolean isTypeDisplayable(Class<T> valueClass) {

		return IDisplayable.class.isAssignableFrom(valueClass);
	}

	private <T> boolean isTypeEntity(Class<T> valueClass) {

		return IEntity.class.isAssignableFrom(valueClass);
	}

	private <R> IEmfDataTableFilterNode<R> createBooleanFilterNode(IEmfDataTableColumn<R, Boolean> column) {

		return new EmfDataTableBooleanFilterNode<>(column);
	}

	private <R> IEmfDataTableFilterNode<R> createBigDecimalFilterNode(IEmfDataTableColumn<R, BigDecimal> column) {

		return new EmfDataTableValueFilterNode<>(column, DomBigDecimalInput::new);
	}

	private <R> IEmfDataTableFilterNode<R> createDayFilterNode(IEmfDataTableColumn<R, Day> column) {

		return new EmfDataTableValueFilterNode<>(column, DomDayInput::new);
	}

	private <R> IEmfDataTableFilterNode<R> createDayTimeFilterNode(IEmfDataTableColumn<R, DayTime> column) {

		return new EmfDataTableValueFilterNode<>(column, DomDayTimeInput::new);
	}

	private <R> IEmfDataTableFilterNode<R> createDoubleFilterNode(IEmfDataTableColumn<R, Double> column) {

		return new EmfDataTableValueFilterNode<>(column, DomDoubleInput::new);
	}

	private <R> IEmfDataTableFilterNode<R> createFloatFilterNode(IEmfDataTableColumn<R, Float> column) {

		return new EmfDataTableValueFilterNode<>(column, DomFloatInput::new);
	}

	private <R> IEmfDataTableFilterNode<R> createIntegerFilterNode(IEmfDataTableColumn<R, Integer> column) {

		return new EmfDataTableValueFilterNode<>(column, DomIntegerInput::new);
	}

	private <R> IEmfDataTableFilterNode<R> createLongFilterNode(IEmfDataTableColumn<R, Long> column) {

		return new EmfDataTableValueFilterNode<>(column, DomLongInput::new);
	}

	private <R> IEmfDataTableFilterNode<R> createStringFilterNode(IEmfDataTableColumn<R, String> column) {

		return new EmfDataTableStringFilterNode<>(column);
	}

	private static class EnumFilterNodeFactory<E extends Enum<E>> implements IEmfDataTableColumnFilterNodeFactory<E> {

		@Override
		public <R> IEmfDataTableFilterNode<R> createFilterNode(IEmfDataTableColumn<R, E> column) {

			return new EmfDataTableEnumFilterNode<>(column);
		}
	}

	private static class DisplayableEnumFilterNodeFactory<E extends Enum<E>> implements IEmfDataTableColumnFilterNodeFactory<E> {

		@Override
		public <R> IEmfDataTableFilterNode<R> createFilterNode(IEmfDataTableColumn<R, E> column) {

			return new EmfDataTableEnumFilterNode<>(column, dummy -> new DisplayableItemEnumSelect());
		}

		private class DisplayableItemEnumSelect extends DomEnumSelect<E> {

			@Override
			protected IDisplayString getValueDisplayString(E value) {

				return Optional//
					.ofNullable(value)
					.map(IDisplayable.class::cast)
					.map(IDisplayable::toDisplay)
					.orElse(IDisplayString.EMPTY);
			}
		}
	}

	private static class EntityFilterNodeFactory<T extends IEntity> implements IEmfDataTableColumnFilterNodeFactory<T> {

		@Override
		public <R> IEmfDataTableFilterNode<R> createFilterNode(IEmfDataTableColumn<R, T> column) {

			return new EmfDataTableEntityFilterNode<>(column);
		}
	}
}
