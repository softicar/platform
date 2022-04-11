package com.softicar.platform.dom.elements.checkbox;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.DomCssPseudoClasses;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsCssClasses;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.event.DomEventType;
import java.util.Collections;
import java.util.Objects;

/**
 * A non-native check-box similar to {@link DomButton}.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class DomCheckbox extends DomDiv {

	private boolean enabled;
	private boolean checked;
	private final DomCheckboxBox checkboxBox;
	private final DomCheckboxLabel checkboxLabel;

	public DomCheckbox() {

		this(false);
	}

	public DomCheckbox(boolean checked) {

		this.enabled = false;
		this.checked = checked;

		this.checkboxBox = appendChild(new DomCheckboxBox());
		this.checkboxBox.setChecked(checked);
		this.checkboxLabel = new DomCheckboxLabel();

		setEnabled(true);

		setCssClass(DomElementsCssClasses.DOM_CHECKBOX);

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

	public void setValue(Boolean checked) {

		setCheckedState(Objects.requireNonNull(checked));
	}

	private void setCheckedState(boolean checked) {

		if (checked != this.checked) {
			this.checkboxBox.setChecked(checked);
			this.checked = checked;
		}
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
