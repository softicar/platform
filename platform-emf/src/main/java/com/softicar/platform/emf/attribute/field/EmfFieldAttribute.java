package com.softicar.platform.emf.attribute.field;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.emf.attribute.AbstractEmfAttribute;
import com.softicar.platform.emf.attribute.validator.EmfAttributeMandatorynessValidator;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.validation.IEmfValidator;
import com.softicar.platform.emf.validation.result.IEmfValidationResult;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

/**
 * This is the base class for all attributes based on {@link IDbField}.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class EmfFieldAttribute<R extends IEmfTableRow<R, ?>, V> extends AbstractEmfAttribute<R, V> {

	protected final IDbField<R, V> field;
	private final Collection<IEmfValidator<R>> validators;
	private IDisplayString title;
	private boolean concealed;
	private boolean editable;
	private boolean immutable;

	public EmfFieldAttribute(IDbField<R, V> field) {

		this.field = field;
		this.validators = new ArrayList<>();
		this.title = field.getTitle();
		this.concealed = false;
		this.editable = true;
		this.immutable = false;

		addValidator(new EmfAttributeMandatorynessValidator<>(this));
	}

	@Override
	public IEmfTable<R, ?, ?> getTable() {

		// TODO remove or improve cast (i54622)
		return (IEmfTable<R, ?, ?>) field.getTable();
	}

	@Override
	public final Optional<IDbField<R, V>> getField() {

		return Optional.of(field);
	}

	@Override
	public final Class<V> getValueClass() {

		return field.getValueType().getValueClass();
	}

	@Override
	public final Optional<Comparator<V>> getValueComparator() {

		return Optional.of(field.getValueType()::compare);
	}

	@Override
	public final void prefetchValues(Collection<R> entities) {

		if (field instanceof IDbForeignField) {
			IDbForeignField<R, ?> foreignField = (IDbForeignField<R, ?>) field;
			foreignField.prefetch(entities);
		}
	}

	@Override
	public final IDisplayString getTitle() {

		return title;
	}

	@Override
	public IStaticObject getTestMarker() {

		return field;
	}

	@Override
	public final void validate(R tableRow, IEmfValidationResult result) {

		validators.forEach(validator -> validator.validate(tableRow, result));
	}

	@Override
	public V getValue(R tableRow) {

		return field.getValue(tableRow);
	}

	@Override
	public void setValue(R tableRow, V value) {

		field.setValue(tableRow, value);
	}

	@Override
	public boolean isConcealed() {

		return concealed || isImplicitlyConcealed();
	}

	@Override
	public boolean isEditable() {

		return editable;
	}

	@Override
	public boolean isImmutable() {

		return immutable;
	}

	@Override
	public boolean isTransient() {

		return false;
	}

	// ------------------------------ configuration methods ------------------------------ //

	/**
	 * Adds the given {@link IEmfValidator} to this attribute.
	 * <p>
	 * The {@link IEmfValidator} is executed whenever the entity is saved to the
	 * database.
	 *
	 * @param validator
	 *            the {@link IEmfValidator} instance (never null)
	 * @return this attribute
	 */
	public final EmfFieldAttribute<R, V> addValidator(IEmfValidator<R> validator) {

		this.validators.add(validator);
		return this;
	}

	/**
	 * Sets the title of this attribute to use instead of the default title.
	 *
	 * @param title
	 *            the title (never null)
	 * @return this attribute
	 */
	public final EmfFieldAttribute<R, V> setTitle(IDisplayString title) {

		this.title = Objects.requireNonNull(title);
		return this;
	}

	/**
	 * Defines if this attribute is explicitly concealed.
	 * <p>
	 * Concealed attributes are not displayed in any user interface.
	 * <p>
	 * As opposed to explicit concealing, some attributes are implicitly
	 * concealed (for example, scope attributes).
	 *
	 * @param concealed
	 *            true to explicitly conceal this attribute; false otherwise
	 * @return this attribute
	 * @see #isConcealed()
	 */
	public final EmfFieldAttribute<R, V> setConcealed(boolean concealed) {

		this.concealed = concealed;
		return this;
	}

	/**
	 * Defines if the value of this attribute is editable.
	 * <p>
	 * Note that non-editable does not imply immutable. A non-editable attribute
	 * is not writable by the user directly, but its value might be changed
	 * indirectly.
	 *
	 * @param editable
	 *            true to make this field read-only; false otherwise
	 * @return this attribute
	 * @see #isEditable()
	 */
	public final EmfFieldAttribute<R, V> setEditable(boolean editable) {

		this.editable = editable;
		return this;
	}

	/**
	 * Defines if the value of this attribute is immutable.
	 * <p>
	 * Note that immutable does not imply non-editable. An immutable attribute
	 * will never change its value, but only <b>after</b> its creation, that is,
	 * the user may be able to define its initial value as long as the attribute
	 * is editable.
	 *
	 * @param immutable
	 *            true to make this field immutable; false otherwise
	 * @return this attribute
	 * @see #isImmutable()
	 */
	public final EmfFieldAttribute<R, V> setImmutable(boolean immutable) {

		this.immutable = immutable;
		return this;
	}

	private boolean isImplicitlyConcealed() {

		return isScopeAttribute(getTable());
	}

	private <S> boolean isScopeAttribute(IEmfTable<R, ?, S> table) {

		return table//
			.getScopeField()
			.map(getTable()::getAttribute)
			.filter(this::equals)
			.isPresent();
	}
}
