package com.softicar.platform.emf.editor;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.Consumers;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomLabel;
import com.softicar.platform.dom.elements.button.popup.DomPopupButton;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.EmfCssClasses;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.attribute.field.bool.EmfBooleanAttribute;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.function.Supplier;

public class EmfAttributeValueLabel<R extends IEmfTableRow<R, ?>, V> extends DomLabel {

	private final IEmfAttribute<R, V> attribute;
	private EmfAttributeValueMode valueMode;

	public EmfAttributeValueLabel(IEmfAttribute<R, V> attribute, EmfAttributeValueMode valueMode) {

		this.attribute = attribute;
		this.valueMode = valueMode;
		refresh();
	}

	/**
	 * By default, if a label contains a focusable child and if no "for"
	 * attribute is defined, the label would act as if there was a "for"
	 * attribute that refers to the focusable child. For example, if the label
	 * contains a button, hovering above the label would trigger the hover
	 * effect of the button. Likewise, clicking the label would trigger the
	 * contained button.
	 * <p>
	 * This override suppresses said default behavior by setting the "for"
	 * attribute to an empty string instead of leaving it undefined.
	 */
	@Override
	public DomLabel updateFor(IDomNode node) {

		super.updateFor(node);
		getAttributeValue(FOR_ATTRIBUTE_NAME)//
			.ifPresentOrElse(Consumers.noOperation(), this::setForEmpty);
		return this;
	}

	public void refresh(EmfAttributeValueMode valueMode) {

		if (valueMode != this.valueMode) {
			this.valueMode = valueMode;
			refresh();
		}
	}

	private void refresh() {

		setDisplayNone(valueMode.isHidden());

		removeChildren();
		appendChild(new EmfAttributeValueTitle(attribute.getTitle()));
		if (showMandatoryIndicator()) {
			appendChild(new EmfAttributeValueMandatoryIndicator());
		}
		attribute//
			.getHelpDisplayFactory()
			.map(EmfAttributeValueHelpIndicator::new)
			.ifPresent(this::appendChild);
	}

	private boolean showMandatoryIndicator() {

		return valueMode.isMandatory() && !EmfBooleanAttribute.class.isInstance(attribute);
	}

	private void setForEmpty() {

		setAttribute(FOR_ATTRIBUTE_NAME, "");
	}

	private class EmfAttributeValueTitle extends DomDiv {

		public EmfAttributeValueTitle(IDisplayString title) {

			addCssClass(EmfCssClasses.EMF_ATTRIBUTE_VALUE_TITLE);
			appendText(title);
		}
	}

	private class EmfAttributeValueMandatoryIndicator extends DomDiv {

		public EmfAttributeValueMandatoryIndicator() {

			addCssClass(EmfCssClasses.EMF_ATTRIBUTE_VALUE_MANDATORY_INDICATOR);
			appendText("*");
		}
	}

	private class EmfAttributeValueHelpIndicator extends DomDiv {

		public EmfAttributeValueHelpIndicator(Supplier<IDomElement> factory) {

			addCssClass(EmfCssClasses.EMF_ATTRIBUTE_VALUE_HELP_INDICATOR);
			appendChild(new HelpPopoverButton(factory));
		}
	}

	private class HelpPopoverButton extends DomPopupButton {

		public HelpPopoverButton(Supplier<IDomElement> factory) {

			setPopupFactory(() -> new HelpPopover(factory));
			setIcon(EmfImages.ATTRIBUTE_HELP.getResource());
		}
	}

	private class HelpPopover extends DomPopup {

		public HelpPopover(Supplier<IDomElement> factory) {

			configuration.setDisplayModePopover();
			appendChild(factory.get());
		}
	}
}
