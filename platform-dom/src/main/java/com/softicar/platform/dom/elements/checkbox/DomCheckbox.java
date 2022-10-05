package com.softicar.platform.dom.elements.checkbox;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.core.interfaces.NullaryVoidFunctionList;
import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.DomCssPseudoClasses;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.IDomClickEventHandler;
import com.softicar.platform.dom.event.IDomEnterKeyEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.event.IDomSpaceKeyEventHandler;
import com.softicar.platform.dom.input.IDomValueInput;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

/**
 * A non-native check-box similar to {@link DomButton}.
 * <p>
 * TODO merge common code with {@link DomButton}
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class DomCheckbox extends DomDiv implements IDomValueInput<Boolean>, IDomClickEventHandler, IDomEnterKeyEventHandler, IDomSpaceKeyEventHandler {

	private final NullaryVoidFunctionList callbacks;
	private boolean disabled;
	private boolean checked;
	private final DomCheckboxBox checkboxBox;
	private final DomCheckboxLabel checkboxLabel;

	public DomCheckbox(boolean checked) {

		this.callbacks = new NullaryVoidFunctionList();
		this.disabled = false;
		this.checked = checked;

		this.checkboxBox = appendChild(new DomCheckboxBox());
		this.checkboxBox.setChecked(checked);
		this.checkboxLabel = new DomCheckboxLabel();

		setTabIndex(0);

		setCssClass(DomCssClasses.DOM_CHECKBOX);

		getDomEngine().setFireOnKeyUp(this, DomEventType.ENTER, true);
		getDomEngine().setFireOnKeyUp(this, DomEventType.SPACE, true);
		getDomEngine().setCssClassOnKeyDown(this, DomEventType.ENTER, Collections.singleton(DomCssPseudoClasses.ACTIVE));
		getDomEngine().setCssClassOnKeyDown(this, DomEventType.SPACE, Collections.singleton(DomCssPseudoClasses.ACTIVE));
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

	@Override
	public void handleClick(IDomEvent event) {

		toggleCheckedState();
	}

	@Override
	public void handleEnterKey(IDomEvent event) {

		toggleCheckedState();
	}

	@Override
	public void handleSpaceKey(IDomEvent event) {

		toggleCheckedState();
	}

	@Override
	public DomCheckbox setDisabled(boolean disabled) {

		if (disabled != this.disabled) {
			this.disabled = disabled;
			if (disabled) {
				setTabIndex(-1);
				unlistenToEvent(DomEventType.CLICK);
				unlistenToEvent(DomEventType.ENTER);
				unlistenToEvent(DomEventType.SPACE);
				addCssClass(DomCssPseudoClasses.DISABLED);
			} else {
				setTabIndex(0);
				listenToEvent(DomEventType.CLICK);
				listenToEvent(DomEventType.ENTER);
				listenToEvent(DomEventType.SPACE);
				removeCssClass(DomCssPseudoClasses.DISABLED);
			}
		}
		return this;
	}

	@Override
	public boolean isDisabled() {

		return disabled;
	}

	@Override
	public final DomCheckbox setEnabled(boolean enabled) {

		return setDisabled(!enabled);
	}

	@Override
	public final boolean isEnabled() {

		return !isDisabled();
	}

	public boolean isChecked() {

		return checked;
	}

	@Override
	public Optional<Boolean> getValue() {

		return Optional.of(isChecked());
	}

	@Override
	public void setValue(Boolean checked) {

		if (Objects.requireNonNull(checked) != this.checked) {
			this.checkboxBox.setChecked(checked);
			this.checked = checked;
		}
	}

	@Override
	public void setValueAndHandleChangeCallback(Boolean value) {

		setValue(value);
		callbacks.apply();
	}

	@Override
	public void addChangeCallback(INullaryVoidFunction callback) {

		this.callbacks.add(callback);
	}

	protected final void toggleCheckedState() {

		if (!disabled) {
			setValueAndHandleChangeCallback(!checked);
		}
	}

	private class DomCheckboxBox extends DomDiv {

		private final DomCheckboxBoxFiller filler;

		public DomCheckboxBox() {

			this.filler = new DomCheckboxBoxFiller();
			setCssClass(DomCssClasses.DOM_CHECKBOX_BOX);
		}

		public void setChecked(boolean checked) {

			removeChildren();
			if (checked) {
				appendChild(filler);
			}
		}

		private class DomCheckboxBoxFiller extends DomDiv {

			public DomCheckboxBoxFiller() {

				setCssClass(DomCssClasses.DOM_CHECKBOX_BOX_FILLER);
			}
		}
	}

	private class DomCheckboxLabel extends DomDiv {

		public DomCheckboxLabel() {

			setCssClass(DomCssClasses.DOM_CHECKBOX_LABEL);
		}

		public void setLabel(IDisplayString label) {

			removeChildren();
			if (label != null) {
				appendText(label.toString());
			}
		}
	}

	@Override
	public IDomValueInput<Boolean> setRequired(boolean required) {

		checkboxBox.setAttribute("required", required? "" : null);
		return this;
	}

	@Override
	public boolean isRequired() {

		return checkboxBox.getAttributeValue("required").isPresent();
	}
}
