package com.softicar.platform.emf.form;

import com.softicar.platform.dom.refresh.bus.IDomRefreshBus;
import com.softicar.platform.emf.form.section.IEmfFormSectionContainer;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;

/**
 * Represents the body element of the {@link IEmfForm}.
 * <p>
 * TODO This interface exposes internal details of the {@link IEmfForm}. We
 * should merge this interface into {@link IEmfForm}.
 *
 * @author Oliver Richers
 */
public interface IEmfFormBody<R extends IEmfTableRow<R, ?>> {

	/**
	 * Returns the {@link IEmfForm} containing this {@link IEmfFormBody}.
	 *
	 * @return the {@link IEmfForm} (never <i>null</i>)
	 */
	IEmfForm<R> getForm();

	/**
	 * Returns the entity shown by the form body.
	 *
	 * @return the entity (never null)
	 */
	R getTableRow();

	default IEmfTable<R, ?, ?> getEntityTable() {

		return getTableRow().table();
	}

	// ------------------------------ refresh bus ------------------------------ //

	IDomRefreshBus getRefreshBus();

	/**
	 * Adds the entity returned by {@link #getTableRow()} and all its attribute
	 * owners to the queue of the {@link IDomRefreshBus}.
	 */
	void queueEntityForRefresh();

	// ------------------------------ show sections ------------------------------ //

	/**
	 * Replaces the current section container with the standard section
	 * container.
	 */
	void showStandardSectionContainer();

	/**
	 * Replaces the current section container with the given section container.
	 *
	 * @param sectionContainer
	 *            the new section container (never null)
	 */
	void showSectionContainer(IEmfFormSectionContainer sectionContainer);

	/**
	 * Replaces the current section container with a section container for an
	 * action that offers interactive entity refreshing.
	 */
	void showInteractiveRefreshSectionContainer();

	/**
	 * Closes the frame that currently displays this body.
	 */
	void closeFrame();

	// ------------------------------ toggle mode ------------------------------ //

	void enterEditMode();

	void finishEditMode(boolean closeAfterFinish);

	void cancelEditMode();

	// ------------------------------ entity creation ------------------------------ //

	void applyCallbackAfterCreation();
}
