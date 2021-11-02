package com.softicar.platform.emf.attribute;

import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.emf.attribute.configuration.EmfAttributeConfiguration;
import com.softicar.platform.emf.attribute.data.table.IEmfDataTableStrategy;
import com.softicar.platform.emf.attribute.display.IEmfAttributeFieldValueDisplayFactory;
import com.softicar.platform.emf.attribute.display.IEmfAttributeTableRowDisplayFactory;
import com.softicar.platform.emf.attribute.display.IEmfAttributeValueDisplayFactory;
import com.softicar.platform.emf.attribute.input.IEmfInput;
import com.softicar.platform.emf.attribute.input.IEmfInputFactory;
import com.softicar.platform.emf.data.table.column.handler.IEmfDataTableRowBasedColumnHandler;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Optional;
import java.util.function.Supplier;

public abstract class AbstractEmfAttribute<R extends IEmfTableRow<R, ?>, V> implements IEmfAttribute<R, V> {

	private final EmfAttributeConfiguration<R, V> configuration;
	private IEmfPredicate<R> visiblePredicate;
	private IEmfPredicate<R> editablePredicate;
	private IEmfPredicate<R> mandatoryPredicate;

	public AbstractEmfAttribute() {

		this.configuration = new EmfAttributeConfiguration<>(this);
		this.visiblePredicate = EmfPredicates.always();
		this.editablePredicate = EmfPredicates.always();
		this.mandatoryPredicate = EmfPredicates.never();
	}

	@Override
	public final boolean isVisible(R tableRow) {

		return !isConcealed() && visiblePredicate.test(tableRow);
	}

	@Override
	public final IEmfPredicate<R> getPredicateVisible() {

		return visiblePredicate;
	}

	@Override
	public final boolean isEditable(R tableRow) {

		return isEditable() && !(isImmutable() && !tableRow.impermanent()) && isVisible(tableRow) && editablePredicate.test(tableRow);
	}

	@Override
	public final IEmfPredicate<R> getPredicateEditable() {

		return editablePredicate;
	}

	@Override
	public final boolean isMandatory(R tableRow) {

		return isUserInputMandatory(tableRow) || mandatoryPredicate.test(tableRow);
	}

	@Override
	public final IEmfPredicate<R> getPredicateMandatory() {

		return mandatoryPredicate;
	}

	@Override
	public final Optional<IDomElement> createDisplay(R tableRow) {

		IEmfAttributeTableRowDisplayFactory<R> displayFactory = configuration.getDisplayFactory();
		if (displayFactory != null) {
			return Optional.of(displayFactory.createDisplay(tableRow));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public final Optional<IDomElement> createTabularDisplay(R tableRow) {

		IEmfAttributeTableRowDisplayFactory<R> displayFactory = configuration.getDisplayFactory();
		if (displayFactory != null) {
			return Optional.of(displayFactory.createTabularDisplay(tableRow));
		} else {
			return createDisplay(tableRow);
		}
	}

	@Override
	public final Optional<IEmfInput<V>> createInput(R tableRow) {

		IEmfInputFactory<R, V> inputFactory = configuration.getInputFactory();
		if (inputFactory != null) {
			return Optional.of(inputFactory.createInput(tableRow));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public final IEmfDataTableStrategy<R> createDataTableStrategy() {

		return configuration.getDataTableStrategyFactory().get();
	}

	@Override
	public final IEmfDataTableRowBasedColumnHandler<R, V> createColumnHandler() {

		return configuration.getColumnHandlerFactory().get();
	}

	// ------------------------------ Predicate ------------------------------ //

	public final AbstractEmfAttribute<R, V> setPredicateVisible(IEmfPredicate<R> predicate) {

		this.visiblePredicate = predicate;
		return this;
	}

	public final AbstractEmfAttribute<R, V> setPredicateEditable(IEmfPredicate<R> predicate) {

		this.editablePredicate = predicate;
		return this;
	}

	public final AbstractEmfAttribute<R, V> setPredicateMandatory(IEmfPredicate<R> predicate) {

		this.mandatoryPredicate = predicate;
		return this;
	}

	public final AbstractEmfAttribute<R, V> setPredicateVisibleEditable(IEmfPredicate<R> predicate) {

		this.visiblePredicate = predicate;
		this.editablePredicate = predicate;
		return this;
	}

	public final AbstractEmfAttribute<R, V> setPredicateVisibleMandatory(IEmfPredicate<R> predicate) {

		this.visiblePredicate = predicate;
		this.mandatoryPredicate = predicate;
		return this;
	}

	public final AbstractEmfAttribute<R, V> setPredicateVisibleEditableMandatory(IEmfPredicate<R> predicate) {

		this.visiblePredicate = predicate;
		this.editablePredicate = predicate;
		this.mandatoryPredicate = predicate;
		return this;
	}

	public final AbstractEmfAttribute<R, V> setPredicateEditableMandatory(IEmfPredicate<R> predicate) {

		this.editablePredicate = predicate;
		this.mandatoryPredicate = predicate;
		return this;
	}

	// -------------------------------- Display Factory -------------------------------- //

	public final AbstractEmfAttribute<R, V> setDisplayFactory(IEmfAttributeValueDisplayFactory<V> displayFactory) {

		configuration.setDisplayFactoryByValue(displayFactory);
		return this;
	}

	/**
	 * TODO try to remove 'ByEntity' suffix with Java 9 (removing the suffix
	 * triggers a bug in the Oracle JDK 8 compiler; confirmed with 1.8.0_144 and
	 * 1.8.0_181)
	 */
	public final AbstractEmfAttribute<R, V> setDisplayFactoryByEntity(IEmfAttributeTableRowDisplayFactory<R> displayFactory) {

		configuration.setDisplayFactoryByEntity(displayFactory);
		return this;
	}

	/**
	 * TODO try to remove 'ByEntityField' suffix with Java 9 (removing the
	 * suffix triggers a bug in the Oracle JDK 8 compiler; confirmed with
	 * 1.8.0_144 and 1.8.0_181)
	 */
	public final AbstractEmfAttribute<R, V> setDisplayFactoryByEntityFieldValue(IEmfAttributeFieldValueDisplayFactory<R, V> displayFactory) {

		configuration.setDisplayFactoryByEntityFieldValue(displayFactory);
		return this;
	}

	// -------------------------------- Input Factory -------------------------------- //

	public final AbstractEmfAttribute<R, V> setInputFactory(Supplier<IEmfInput<V>> inputFactory) {

		configuration.setInputFactoryByValue(inputFactory);
		return this;
	}

	/**
	 * TODO try to remove 'ByEntity' suffix with Java 9 (removing the suffix
	 * triggers a bug in the Oracle JDK 8 compiler; confirmed with 1.8.0_144 and
	 * 1.8.0_181)
	 */
	public final AbstractEmfAttribute<R, V> setInputFactoryByEntity(IEmfInputFactory<R, V> inputFactory) {

		configuration.setInputFactoryByEntity(inputFactory);
		return this;
	}

	// -------------------------------- Data Table Strategy Factory -------------------------------- //

	public final AbstractEmfAttribute<R, V> setDataTableStrategyFactory(Supplier<IEmfDataTableStrategy<R>> dataTableStrategyFactory) {

		configuration.setDataTableStrategyFactory(dataTableStrategyFactory);
		return this;
	}

	public final AbstractEmfAttribute<R, V> setColumnHandlerFactory(Supplier<IEmfDataTableRowBasedColumnHandler<R, V>> columnHandlerFactory) {

		configuration.setColumnHandlerFactory(columnHandlerFactory);
		return this;
	}

	protected boolean isUserInputMandatory(R tableRow) {

		return getField()//
			.map(field -> isEditable() && !field.isNullable() && editablePredicate.test(tableRow))
			.orElse(false);
	}
}
