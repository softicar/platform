package com.softicar.platform.emf.attribute;

import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.transients.ITransientField;
import com.softicar.platform.db.sql.field.ISqlField;
import com.softicar.platform.db.sql.field.ISqlForeignRowField;
import com.softicar.platform.emf.attribute.field.EmfFieldAttribute;
import com.softicar.platform.emf.attribute.field.EmfFieldAttributeList;
import com.softicar.platform.emf.attribute.field.foreign.entity.EmfForeignEntityAttribute;
import com.softicar.platform.emf.attribute.field.indirect.entity.foreign.EmfForeignIndirectEntityAttribute;
import com.softicar.platform.emf.attribute.field.indirect.entity.foreign.EmfForeignIndirectEntityAttributeCastException;
import com.softicar.platform.emf.attribute.field.string.EmfStringAttribute;
import com.softicar.platform.emf.attribute.transients.EmfTransientAttribute;
import com.softicar.platform.emf.entity.IEmfEntity;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.security.KeyStore.Entry.Attribute;

/**
 * A list of attributes of an {@link IEmfTableRow}.
 *
 * @author Oliver Richers
 */
public interface IEmfAttributeList<R extends IEmfTableRow<R, ?>> {

	// ------------------------------ getting ------------------------------ //

	/**
	 * Returns the attribute for the given {@link ISqlField}.
	 *
	 * @param field
	 *            the field (never <i>null</i>)
	 * @return the corresponding attribute (never <i>null</i>)
	 */
	<V> IEmfAttribute<R, V> getAttribute(ISqlField<R, V> field);

	/**
	 * Returns the attribute associated with the given {@link ITransientField}.
	 *
	 * @param field
	 *            the field (never <i>null</i>)
	 * @return the corresponding attribute (may be <i>null</i> if no such field
	 *         has been defined)
	 */
	<V> IEmfAttribute<R, V> getAttribute(ITransientField<R, V> field);

	// ------------------------------ editing ------------------------------ //

	/**
	 * Returns the editable attribute for the given {@link ISqlField}.
	 *
	 * @param field
	 *            the field (never <i>null</i>)
	 * @return the corresponding attribute (never <i>null</i>)
	 */
	<V> EmfFieldAttribute<R, V> editAttribute(ISqlField<R, V> field);

	/**
	 * Returns an {@link EmfFieldAttributeList} for the given {@link ISqlField}
	 * references.
	 *
	 * @param fields
	 *            the fields
	 * @return an {@link EmfFieldAttributeList} of the given fields (never
	 *         <i>null</i>)
	 */
	@SuppressWarnings("unchecked")
	EmfFieldAttributeList<R> editAttributes(ISqlField<R, ?>...fields);

	/**
	 * Returns the editable {@link EmfForeignEntityAttribute} for the given
	 * {@link ISqlForeignRowField}.
	 * <p>
	 * FIXME Return type and method name should correspond to each other.
	 *
	 * @param field
	 *            the foreign-key field (never <i>null</i>)
	 * @return the corresponding attribute (never <i>null</i>)
	 */
	<F extends IEmfEntity<F, ?>> EmfForeignEntityAttribute<R, F> editEntityAttribute(ISqlForeignRowField<R, F, ?> field);

	/**
	 * Returns the editable {@link EmfForeignIndirectEntityAttribute} for the
	 * given {@link IDbField}.
	 * <p>
	 * FIXME Return type and method name should correspond to each other.
	 *
	 * @param <F>
	 *            the value type of the foreign-key field
	 * @param field
	 *            the foreign-key field (never <i>null</i>)
	 * @return the corresponding attribute (never <i>null</i>)
	 * @throws EmfForeignIndirectEntityAttributeCastException
	 *             if the given field is not represented by an
	 *             {@link EmfForeignIndirectEntityAttribute}
	 */
	<F extends IEmfEntity<F, ?>> EmfForeignIndirectEntityAttribute<R, F> editIndirectEntityAttribute(ISqlForeignRowField<R, F, ?> field);

	/**
	 * Returns the editable {@link EmfStringAttribute} for the given
	 * {@link IDbField}.
	 *
	 * @param field
	 *            the field (never <i>null</i>)
	 * @return the corresponding attribute (never <i>null</i>)
	 */
	EmfStringAttribute<R> editStringAttribute(IDbField<R, String> field);

	/**
	 * Returns the editable attribute for the given {@link ITransientField}.
	 *
	 * @param field
	 *            the field (never <i>null</i>)
	 * @return the corresponding attribute (may be <i>null</i> if no such field
	 *         has been defined)
	 */
	<V> EmfTransientAttribute<R, V> editAttribute(ITransientField<R, V> field);

	// ------------------------------ adding ------------------------------ //

	/**
	 * Adds the given attribute to this list.
	 *
	 * @param attribute
	 *            the {@link Attribute} to add (never <i>null</i>)
	 * @return returns the new attribute
	 */
	<A extends IEmfAttribute<R, ?>> A addAttribute(A attribute);

	/**
	 * Adds the given {@link ITransientField} as attribute to this list.
	 *
	 * @param transientField
	 *            the {@link ITransientField} to add (never <i>null</i>)
	 * @return returns the new attribute
	 */
	<F extends IEmfTableRow<F, ?>, V> EmfTransientAttribute<R, V> addTransientAttribute(ITransientField<R, V> transientField);

	// ------------------------------ overriding ------------------------------ //

	/**
	 * Overrides an attribute for the given {@link IDbField}.
	 * <p>
	 * This method will probably be removed. Don't use it for new code.
	 *
	 * @param field
	 *            the field (never <i>null</i>)
	 * @param attribute
	 *            the {@link Attribute} to use (never <i>null</i>)
	 */
	<V> void setAttribute(IDbField<R, V> field, EmfFieldAttribute<R, V> attribute);
}
