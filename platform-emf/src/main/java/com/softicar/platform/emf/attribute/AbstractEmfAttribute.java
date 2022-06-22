package com.softicar.platform.emf.attribute;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.Consumers;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.emf.attribute.configuration.EmfAttributeConfiguration;
import com.softicar.platform.emf.attribute.display.IEmfAttributeFieldValueDisplayFactory;
import com.softicar.platform.emf.attribute.display.IEmfAttributeTableRowDisplayFactory;
import com.softicar.platform.emf.attribute.display.IEmfAttributeValueDisplayFactory;
import com.softicar.platform.emf.attribute.input.IEmfInput;
import com.softicar.platform.emf.attribute.input.IEmfInputFactory;
import com.softicar.platform.emf.data.table.column.handler.IEmfDataTableRowBasedColumnHandler;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class AbstractEmfAttribute<R extends IEmfTableRow<R, ?>, V> implements IEmfAttribute<R, V> {

	private final EmfAttributeConfiguration<R, V> configuration;
	private IEmfPredicate<R> visiblePredicate;
	private IEmfPredicate<R> editablePredicate;
	private IEmfPredicate<R> mandatoryPredicate;
	private Consumer<R> valueDeducer;

	public AbstractEmfAttribute() {

		this.configuration = new EmfAttributeConfiguration<>(this);
		this.visiblePredicate = EmfPredicates.always();
		this.editablePredicate = EmfPredicates.always();
		this.mandatoryPredicate = EmfPredicates.never();
		this.valueDeducer = Consumers.noOperation();
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
	public void applyValueDeducer(R tableRow) {

		valueDeducer.accept(tableRow);
	}

	@Override
	public final IDomElement createDisplay(R tableRow) {

		return Objects.requireNonNull(configuration.getDisplayFactory().createDisplay(tableRow));
	}

	@Override
	public final IDomElement createTabularDisplay(R tableRow) {

		return Objects.requireNonNull(configuration.getDisplayFactory().createTabularDisplay(tableRow));
	}

	@Override
	public final IEmfInput<V> createInput(R tableRow) {

		return Objects.requireNonNull(configuration.getInputFactory().createInput(tableRow));
	}

	@Override
	public final IEmfDataTableRowBasedColumnHandler<R, V> createColumnHandler() {

		return configuration.getColumnHandlerFactory().get();
	}

	@Override
	public Optional<Supplier<IDomElement>> getHelpDisplayFactory() {

		return configuration.getHelpDisplayFactory();
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

	public final AbstractEmfAttribute<R, V> setValueDeducer(Consumer<R> valueDeducer) {

		this.valueDeducer = valueDeducer;
		return this;
	}

	public final AbstractEmfAttribute<R, V> setValueDeducer(IEmfPredicate<R> predicate, V value) {

		return setValueDeducer(row -> {
			if (predicate.test(row)) {
				setValue(row, value);
			}
		});
	}

	// ------------------------------ Predicate Convenience ------------------------------ //

	/**
	 * Makes this {@link IEmfAttribute} visible and editable under the given
	 * {@link IEmfPredicate} and forces the given value otherwise.
	 *
	 * @param predicate
	 *            the {@link IEmfPredicate} (never <i>null</i>)
	 * @param fallbackValue
	 *            the value to assign to this {@link IEmfAttribute} if the
	 *            {@link IEmfPredicate} is <b>not</b> <i>true</i> (may be null)
	 * @return this
	 */
	public AbstractEmfAttribute<R, V> setConditionallyAvailable(IEmfPredicate<R> predicate, V fallbackValue) {

		setPredicateVisibleEditable(predicate);
		setValueDeducer(predicate.not(), fallbackValue);
		return this;
	}

	/**
	 * Makes this {@link IEmfAttribute} visible, editable and mandatory under
	 * the given {@link IEmfPredicate} and forces the given value otherwise.
	 *
	 * @param predicate
	 *            the {@link IEmfPredicate} (never <i>null</i>)
	 * @param fallbackValue
	 *            the value to assign to this {@link IEmfAttribute} if the
	 *            {@link IEmfPredicate} is <b>not</b> <i>true</i> (may be null)
	 * @return this
	 */
	public AbstractEmfAttribute<R, V> setConditionallyRequired(IEmfPredicate<R> predicate, V fallbackValue) {

		setPredicateVisibleEditableMandatory(predicate);
		setValueDeducer(predicate.not(), fallbackValue);
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

	// -------------------------------- Column Handler Factory -------------------------------- //

	public final AbstractEmfAttribute<R, V> setColumnHandlerFactory(Supplier<IEmfDataTableRowBasedColumnHandler<R, V>> columnHandlerFactory) {

		configuration.setColumnHandlerFactory(columnHandlerFactory);
		return this;
	}

	// -------------------------------- Help Display Factory -------------------------------- //

	public final AbstractEmfAttribute<R, V> setHelpDisplayFactory(Supplier<IDomElement> helpDisplayFactory) {

		configuration.setHelpDisplayFactory(helpDisplayFactory);
		return this;
	}

	public final AbstractEmfAttribute<R, V> setHelpDisplay(IDisplayString helpText) {

		configuration.setHelpDisplay(helpText);
		return this;
	}

	public final AbstractEmfAttribute<R, V> setHelpDisplayByWikiText(IDisplayString helpText) {

		configuration.setHelpDisplayByWikiText(helpText);
		return this;
	}

	// -------------------------------- Protected -------------------------------- //

	protected boolean isUserInputMandatory(R tableRow) {

		return getField()//
			.map(field -> isEditable() && !field.isNullable() && editablePredicate.test(tableRow))
			.orElse(false);
	}
}
