package com.softicar.platform.emf.table.specialization;

import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.attribute.field.foreign.row.IEmfForeignRowAttributeFactory;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Optional;

/**
 * This interface is an specialization point for the varying implementations of
 * {@link IEmfTable}.
 *
 * @author Oliver Richers
 */
public interface IEmfTableSpecialization<R extends IEmfTableRow<R, P>, P, S> {

	/**
	 * Initializes the attributes in the given {@link IEmfAttributeList}.
	 *
	 * @param attributeList
	 *            the {@link IEmfAttributeList} (never null)
	 */
	void initializeAttributeList(IEmfAttributeList<R> attributeList);

	/**
	 * Initializes the fields of the given {@link IEmfTableRow}s.
	 *
	 * @param row
	 *            the {@link IEmfTableRow} to initialize (never null)
	 * @param scope
	 *            the {@link IEmfTableRow} scope (may be null)
	 */
	void initializeFields(R row, S scope);

	/**
	 * Creates an impermanent copy of the given {@link IEmfTableRow}.
	 * <p>
	 * Care must be taken with the returned copy.
	 *
	 * @param row
	 *            the {@link IEmfTableRow} to copy (never null)
	 * @return the copy of the {@link IEmfTableRow} (never null)
	 */
	R createImpermanentCopy(R row);

	/**
	 * Creates a default display element for the given {@link IEmfTableRow}.
	 *
	 * @param row
	 *            the {@link IEmfTableRow} to display (never null)
	 * @return the display element (never null)
	 */
	IDomElement createDefaultDisplay(R row);

	/**
	 * Creates a new popup to edit and save a new {@link IEmfTableRow}.
	 *
	 * @param scope
	 *            the scope for the new {@link IEmfTableRow} (may be null)
	 * @return the new popup (never null)
	 */
	DomPopup createNewTableRowPopup(S scope);

	/**
	 * Returns the optional base {@link IEmfTableRow} of the given
	 * {@link IEmfTableRow}.
	 *
	 * @param row
	 *            the {@link IEmfTableRow} (never null)
	 * @return the optional base {@link IEmfTableRow}
	 */
	Optional<IEmfTableRow<?, ?>> getBase(R row);

	/**
	 * Returns the optional base table.
	 *
	 * @return the optional base table
	 */
	Optional<IEmfTable<?, ?, ?>> getBaseTable();

	/**
	 * Returns the default implementation of
	 * {@link IEmfForeignRowAttributeFactory}.
	 *
	 * @return the {@link IEmfForeignRowAttributeFactory} (never null)
	 */
	IEmfForeignRowAttributeFactory<R, P> getDefaultAttributeFactory();
}
