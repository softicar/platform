package com.softicar.platform.emf.form;

import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import com.softicar.platform.emf.table.listener.IEmfSaveHook;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.validation.IEmfValidator;
import java.util.Collection;
import java.util.function.Consumer;

public interface IEmfForm<R extends IEmfTableRow<R, ?>> {

	/**
	 * Returns the {@link IEmfFormFrame} containing this {@link IEmfForm}.
	 *
	 * @return the {@link IEmfFormFrame} (never <i>null</i>)
	 */
	IEmfFormFrame<R> getFrame();

	/**
	 * Returns the {@link IEmfTableRow} handled by this {@link IEmfForm}.
	 *
	 * @return the {@link IEmfTableRow} (never <i>null</i>)
	 */
	R getTableRow();

	/**
	 * Peeks for freshness of the shown entity and refreshes this form.
	 * <p>
	 * This method first peeks the database for modifications to the entity,
	 * refreshes this form with the current (potentially stale) entity data, and
	 * offers an interactive reload action if the entity is stale.
	 *
	 * @return this {@link IEmfForm}
	 */
	IEmfForm<R> peekAndRefresh();

	/**
	 * Sets a callback {@link Consumer} to be called whenever a new
	 * {@link IEmfTableRow} was inserted through this {@link IEmfForm}.
	 * <p>
	 * This callback mechanism is primarily intended for UI updates. It is
	 * called when the new {@link IDbTableRow} was already <b>persistently</b>
	 * inserted and all transactions were successfully <b>committed</b>. The
	 * callback should <b>not</b> write to the database.
	 * <p>
	 * If you intend to insert or update additional {@link IEmfTableRow} objects
	 * through the callback, you should use an {@link IEmfSaveHook} instead.
	 * Only that will ensure that the original {@link IDbTableRow} and the
	 * additional {@link IDbTableRow} objects will be inserted and updated
	 * atomically, that is, within the same transaction.
	 *
	 * @param callbackAfterCreation
	 *            the callback {@link Consumer} (never <i>null</i>)
	 */
	void setCallbackAfterCreation(Consumer<R> callbackAfterCreation);

	IEmfForm<R> addAdditionalValidator(IEmfValidator<R> validator);

	/**
	 * Enables or disables direct editing for this {@link IEmfForm}.
	 * <p>
	 * If direct editing is enabled, the {@link IEmfForm} will be spawned in
	 * edit mode, so that clicking the common <i>edit</i> action is not
	 * necessary. Additionally, when the user clicks the <i>cancel</i> button,
	 * the form is closed, instead of switching to view mode.
	 * <p>
	 * Direct editing is disabled by default.
	 *
	 * @param enabled
	 *            <i>true</i> if direct editing should be enabled; <i>false</i>
	 *            otherwise
	 */
	void setDirectEditing(boolean enabled);

	/**
	 * Determines whether direct editing is enabled for this {@link IEmfForm}.
	 *
	 * @return <i>true</i> if direct editing is enabled; <i>false</i> otherwise
	 */
	boolean isDirectEditingEnabled();

	/**
	 * Returns additional {@link IEmfValidator} objects that should be executed
	 * before the {@link IEmfTableRow} returned by {@link #getTableRow()} may be
	 * persisted.
	 *
	 * @return an {@link IEmfValidator} (never <i>null</i>)
	 */
	Collection<IEmfValidator<R>> getAdditionalValidators();
}
