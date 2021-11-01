package com.softicar.platform.dom.elements.checkbox;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.Consumers;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.DomCssPseudoClasses;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsCssClasses;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.IDomClickEventHandler;
import com.softicar.platform.dom.event.IDomEnterKeyEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.event.IDomSpaceKeyEventHandler;
import java.util.Collections;
import java.util.function.Consumer;

/**
 * A non-native check-box similar to {@link DomButton}.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class DomCheckbox extends DomDiv implements IDomClickEventHandler, IDomEnterKeyEventHandler, IDomSpaceKeyEventHandler {

	private boolean enabled;
	private boolean checked;
	private Consumer<Boolean> changeCallback;
	private final DomCheckboxBox checkboxBox;
	private final DomCheckboxLabel checkboxLabel;

	public DomCheckbox() {

		this.enabled = false;
		this.checked = false;
		this.changeCallback = Consumers.noOperation();

		this.checkboxBox = appendChild(new DomCheckboxBox());
		this.checkboxLabel = new DomCheckboxLabel();

		setEnabled(true);

		setCssClass(DomElementsCssClasses.DOM_CHECKBOX);

		getDomEngine().setFireOnKeyUp(this, DomEventType.ENTER, true);
		getDomEngine().setFireOnKeyUp(this, DomEventType.SPACE, true);
		getDomEngine().setCssClassOnKeyDown(this, DomEventType.ENTER, Collections.singleton(DomCssPseudoClasses.ACTIVE));
		getDomEngine().setCssClassOnKeyDown(this, DomEventType.SPACE, Collections.singleton(DomCssPseudoClasses.ACTIVE));
	}

	@Override
	public void handleClick(IDomEvent event) {

		handleEvent();
	}

	@Override
	public void handleEnterKey(IDomEvent event) {

		handleEvent();
	}

	@Override
	public void handleSpaceKey(IDomEvent event) {

		handleEvent();
	}

	public DomCheckbox setLabel(IDisplayString label) {

		checkboxLabel.setLabel(label);
		appendChild(checkboxLabel);
		return this;
	}

	@Override
	public DomCheckbox setTitle(IDisplayString title) {

		super.setTitle(title);
		return this;
	}

	/**
	 * Sets the callback to be triggered when the state of this object is
	 * changed.
	 * <p>
	 *
	 * @param changeCallback
	 *            the change callback to trigger (never null)
	 */
	public DomCheckbox setChangeCallback(INullaryVoidFunction changeCallback) {

		return setChangeCallback(dummy -> changeCallback.apply());
	}

	/**
	 * Sets the callback to be triggered when the checked state of the object is
	 * changed.
	 * <p>
	 *
	 * @param changeCallback
	 *            the change callback to trigger (never null)
	 */
	public DomCheckbox setChangeCallback(Consumer<Boolean> changeCallback) {

		if (!hasCustomChangeCallback()) {
			this.changeCallback = changeCallback;
			return this;
		} else {
			throw new SofticarDeveloperException("Tried to overwrite the change callback.");
		}
	}

	public boolean hasCustomChangeCallback() {

		return !Consumers.isNoOperation(changeCallback);
	}

	public boolean isEnabled() {

		return enabled;
	}

	public DomCheckbox setEnabled(boolean enabled) {

		if (enabled != this.enabled) {
			this.enabled = enabled;
			if (enabled) {
				setTabIndex(0);
				listenToEvent(DomEventType.CLICK);
				listenToEvent(DomEventType.ENTER);
				listenToEvent(DomEventType.SPACE);
				removeCssClass(DomCssPseudoClasses.DISABLED);
			} else {
				setTabIndex(-1);
				unlistenToEvent(DomEventType.CLICK);
				unlistenToEvent(DomEventType.ENTER);
				unlistenToEvent(DomEventType.SPACE);
				addCssClass(DomCssPseudoClasses.DISABLED);
			}
		}
		return this;
	}

	public boolean isChecked() {

		return checked;
	}

	public DomCheckbox setChecked(boolean checked) {

		setCheckedState(checked);
		executeCallback();
		return this;
	}

	private void setCheckedState(boolean checked) {

		if (checked != this.checked) {
			this.checkboxBox.setChecked(checked);
			this.checked = checked;
		}
	}

	private void handleEvent() {

		if (enabled) {
			setCheckedState(!checked);
			executeCallback();
		}
	}

	private void executeCallback() {

		changeCallback.accept(checked);
	}

	private class DomCheckboxBox extends DomDiv {

		private final DomCheckboxBoxFiller filler;

		public DomCheckboxBox() {

			this.filler = new DomCheckboxBoxFiller();
			setCssClass(DomElementsCssClasses.DOM_CHECKBOX_BOX);
		}

		public void setChecked(boolean checked) {

			removeChildren();
			if (checked) {
				appendChild(filler);
			}
		}

		private class DomCheckboxBoxFiller extends DomDiv {

			public DomCheckboxBoxFiller() {

				setCssClass(DomElementsCssClasses.DOM_CHECKBOX_BOX_FILLER);
			}
		}
	}

	private class DomCheckboxLabel extends DomDiv {

		public DomCheckboxLabel() {

			setCssClass(DomElementsCssClasses.DOM_CHECKBOX_LABEL);
		}

		public void setLabel(IDisplayString label) {

			removeChildren();
			if (label != null) {
				appendText(label.toString());
			}
		}
	}
}
