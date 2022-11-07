package com.softicar.platform.emf.form.delegator;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.emf.EmfCssClasses;
import com.softicar.platform.emf.form.EmfForm;
import com.softicar.platform.emf.form.IEmfForm;
import com.softicar.platform.emf.form.IEmfFormFrame;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.validation.IEmfValidator;
import java.util.Collection;
import java.util.function.Consumer;

/**
 * An implementation of {@link IEmfForm} that delegates interface calls to an
 * internal {@link #form} field.
 * <p>
 * Should be used as a base class for custom {@link IEmfForm} implementations.
 *
 * @author Daniel Klose
 */
public abstract class AbstractEmfFormDelegator<R extends IEmfTableRow<R, ?>> extends DomDiv implements IEmfForm<R> {

	protected EmfForm<R> form;

	public AbstractEmfFormDelegator(IEmfFormFrame<R> frame, R tableRow) {

		this.form = new EmfForm<>(frame, tableRow);
		addCssClass(EmfCssClasses.EMF_FORM_DELEGATOR);
	}

	@Override
	public IEmfFormFrame<R> getFrame() {

		return form.getFrame();
	}

	@Override
	public R getTableRow() {

		return form.getTableRow();
	}

	@Override
	public IEmfForm<R> peekAndRefresh() {

		return form.peekAndRefresh();
	}

	@Override
	public void setCallbackAfterCreation(Consumer<R> callbackAfterCreation) {

		form.setCallbackAfterCreation(callbackAfterCreation);
	}

	@Override
	public IEmfForm<R> addAdditionalValidator(IEmfValidator<R> validator) {

		return form.addAdditionalValidator(validator);
	}

	@Override
	public void setDirectEditing(boolean enabled) {

		form.setDirectEditing(enabled);
	}

	@Override
	public Collection<IEmfValidator<R>> getAdditionalValidators() {

		return form.getAdditionalValidators();
	}

	@Override
	public void finishEditMode(boolean closeAfterFinish) {

		form.finishEditMode(closeAfterFinish);
	}
}
