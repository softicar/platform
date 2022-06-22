package com.softicar.platform.emf.attribute;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.attribute.configuration.EmfAttributeColumnHandler;
import com.softicar.platform.emf.attribute.input.IEmfInput;
import com.softicar.platform.emf.data.table.column.handler.IEmfDataTableRowBasedColumnHandler;
import com.softicar.platform.emf.entity.IEmfEntity;
import com.softicar.platform.emf.predicate.EmfPredicateAdapter;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import com.softicar.platform.emf.sub.object.IEmfSubObject;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.validation.result.IEmfValidationResult;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class EmfInheritedAttribute<O extends IEmfSubObject<O, B>, B extends IEmfEntity<B, ?>, V> implements IEmfAttribute<O, V> {

	private final IDbForeignRowField<O, B, ?> baseField;
	private final IEmfAttribute<B, V> originalAttribute;
	private Optional<IDisplayString> title;
	private boolean editable;
	private boolean concealed;

	public EmfInheritedAttribute(IDbForeignRowField<O, B, ?> baseField, IEmfAttribute<B, V> originalAttribute) {

		this.baseField = Objects.requireNonNull(baseField);
		this.originalAttribute = Objects.requireNonNull(originalAttribute);
		this.title = Optional.empty();
		this.editable = true;
		this.concealed = false;
	}

	@Override
	public IEmfTable<O, ?, ?> getTable() {

		return (IEmfTable<O, ?, ?>) baseField.getTable();
	}

	@Override
	public Optional<IDbField<O, V>> getField() {

		return Optional.empty();
	}

	@Override
	public Class<V> getValueClass() {

		return originalAttribute.getValueClass();
	}

	@Override
	public Optional<Comparator<V>> getValueComparator() {

		return originalAttribute.getValueComparator();
	}

	@Override
	public void prefetchValues(Collection<O> entities) {

		List<B> baseEntities = entities//
			.stream()
			.map(IEmfSubObject::pk)
			.collect(Collectors.toList());
		originalAttribute.prefetchValues(baseEntities);
	}

	@Override
	public V getValue(O tableRow) {

		return originalAttribute.getValue(tableRow.pk());
	}

	@Override
	public void setValue(O tableRow, V value) {

		originalAttribute.setValue(tableRow.pk(), value);
	}

	@Override
	public IDisplayString getTitle() {

		return title.orElse(originalAttribute.getTitle());
	}

	@Override
	public IStaticObject getTestMarker() {

		return originalAttribute.getTestMarker();
	}

	@Override
	public boolean isConcealed() {

		return concealed || originalAttribute.isConcealed();
	}

	@Override
	public boolean isEditable() {

		return editable && originalAttribute.isEditable();
	}

	@Override
	public boolean isImmutable() {

		return originalAttribute.isImmutable();
	}

	@Override
	public boolean isTransient() {

		return false;
	}

	@Override
	public boolean isVisible(O tableRow) {

		return !concealed && originalAttribute.isVisible(baseField.getValue(tableRow));
	}

	@Override
	public IEmfPredicate<O> getPredicateVisible() {

		return new EmfPredicateAdapter<>(originalAttribute.getPredicateVisible(), baseField::getValue);
	}

	@Override
	public boolean isEditable(O tableRow) {

		return editable && originalAttribute.isEditable(baseField.getValue(tableRow));
	}

	@Override
	public IEmfPredicate<O> getPredicateEditable() {

		return new EmfPredicateAdapter<>(originalAttribute.getPredicateEditable(), baseField::getValue);
	}

	@Override
	public boolean isMandatory(O tableRow) {

		return originalAttribute.isMandatory(baseField.getValue(tableRow));
	}

	@Override
	public IEmfPredicate<O> getPredicateMandatory() {

		return new EmfPredicateAdapter<>(originalAttribute.getPredicateMandatory(), baseField::getValue);
	}

	@Override
	public void applyValueDeducer(O tableRow) {

		originalAttribute.applyValueDeducer(baseField.getValue(tableRow));
	}

	@Override
	public IDomElement createDisplay(O tableRow) {

		return originalAttribute.createDisplay(tableRow.pk());
	}

	@Override
	public IDomElement createTabularDisplay(O tableRow) {

		return originalAttribute.createTabularDisplay(tableRow.pk());
	}

	@Override
	public IEmfInput<V> createInput(O tableRow) {

		return originalAttribute.createInput(tableRow.pk());
	}

	@Override
	public IEmfDataTableRowBasedColumnHandler<O, V> createColumnHandler() {

		// TODO probably need here a special implementation
		return new EmfAttributeColumnHandler<>(this);
	}

	@Override
	public Optional<Supplier<IDomElement>> getHelpDisplayFactory() {

		return originalAttribute.getHelpDisplayFactory();
	}

	@Override
	public IEmfAttribute<B, V> getOriginalAttribute() {

		return originalAttribute;
	}

	@Override
	public void validate(O tableRow, IEmfValidationResult result) {

		B base = baseField.getValue(tableRow);
		if (base != null) {
			originalAttribute.validate(base, result);
		} else {
			if (isMandatory(tableRow)) {
				result.addError(this, EmfI18n.THE_ATTRIBUTE_ARG1_IS_MANDATORY.toDisplay(getTitle()));
			}
		}
	}

	public IDbForeignRowField<O, B, ?> getBaseField() {

		return baseField;
	}

	public EmfInheritedAttribute<O, B, V> setTitle(IDisplayString title) {

		this.title = Optional.of(title);
		return this;
	}

	public EmfInheritedAttribute<O, B, V> setEditable(boolean editable) {

		this.editable = editable;
		return this;
	}

	public EmfInheritedAttribute<O, B, V> setConcealed(boolean concealed) {

		this.concealed = concealed;
		return this;
	}
}
