package com.softicar.platform.emf.matrix;

import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.dom.elements.bar.DomBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.message.DomMessageDiv;
import com.softicar.platform.dom.elements.message.style.DomMessageType;
import com.softicar.platform.dom.event.IDomEnterKeyEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfTestMarker;
import com.softicar.platform.emf.attribute.field.foreign.entity.input.EmfEntityInput;
import com.softicar.platform.emf.entity.IEmfEntity;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * An {@link EmfEntityInput} based entity pre-selector input element.
 * <p>
 * Intended for use with an {@link EmfSettingMatrixDiv}.
 * <p>
 * TODO improve this. coupling with {@link EmfSettingMatrixDiv} is too loose.
 *
 * @author Alexander Schmidt
 */
public class EmfSettingMatrixPreselectorDiv<E extends IEmfEntity<E, ?>> extends DomDiv {

	private final Function<E, IDomElement> contentFactory;
	private final Form form;
	private final DomDiv container;

	public EmfSettingMatrixPreselectorDiv(Supplier<EmfEntityInput<E>> inputSupplier, Function<E, IDomElement> contentFactory) {

		this.contentFactory = contentFactory;
		this.form = new Form(inputSupplier);
		this.container = new DomDiv();

		appendChild(form);
		appendNewChild(DomElementTag.HR);
		appendChild(container);

		refresh();
	}

	private void refresh() {

		container.removeChildren();
		Optional<E> selectedValue = form.getSelectedValue();
		if (selectedValue.isPresent()) {
			container.appendChild(contentFactory.apply(selectedValue.get()));
		} else {
			container.appendChild(new DomMessageDiv(DomMessageType.INFO, EmfI18n.PLEASE_SELECT_AN_ENTRY));
		}
	}

	private class Form extends DomBar implements IDomEnterKeyEventHandler {

		private final EmfEntityInput<E> input;

		public Form(Supplier<EmfEntityInput<E>> inputSupplier) {

			this.input = appendChild(inputSupplier.get());
			this.input.addMarker(EmfTestMarker.SETTING_MATRIX_ENTITY_INPUT);
			appendChild(
				new DomButton()//
					.setLabel(EmfI18n.SELECT)
					.setIcon(DomElementsImages.DIALOG_OKAY.getResource())
					.setClickCallback(() -> refresh())
					.addMarker(EmfTestMarker.SETTING_MATRIX_ENTITY_SELECT_BUTTON));
		}

		@Override
		public void handleEnterKey(IDomEvent event) {

			refresh();
		}

		public Optional<E> getSelectedValue() {

			return input.getValue();
		}
	}
}
