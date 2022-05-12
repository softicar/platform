package com.softicar.platform.ajax.request;

import com.softicar.platform.ajax.event.AjaxDomEvent;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.Consumers;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomTextArea;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.input.DomOption;
import com.softicar.platform.dom.input.DomSelect;
import com.softicar.platform.dom.input.DomTextInput;
import java.util.Objects;
import java.util.function.Consumer;

public class AjaxRequestInputValuesTestDiv extends DomDiv {

	public final DomTextInput textInput;
	public final DomTextArea textArea;
	public final DomSelect<DomOption> select;
	public final DomOption option1;
	public final DomOption option2;
	public final DomOption option3;
	public final TestButton testButton;
	private IDomEvent lastEvent;

	public AjaxRequestInputValuesTestDiv() {

		this(Consumers.noOperation());
	}

	public AjaxRequestInputValuesTestDiv(Consumer<AjaxRequestInputValuesTestDiv> customizer) {

		this.textInput = new DomTextInput();
		this.textArea = new DomTextArea();
		this.select = new DomSelect<>();
		this.option1 = new DomOption();
		this.option2 = new DomOption();
		this.option3 = new DomOption();
		this.testButton = new TestButton();
		this.lastEvent = null;

		appendChild(textInput);
		appendChild(textArea);
		appendChild(select);
		appendChild(testButton);

		option1.appendText("one");
		option2.appendText("two");
		option3.appendText("three");

		customizer.accept(this);
		select.appendChildren(option1, option2, option3);
	}

	public AjaxDomEvent getLastEvent() {

		return (AjaxDomEvent) Objects.requireNonNull(lastEvent);
	}

	private class TestButton extends DomButton {

		public TestButton() {

			setLabel(IDisplayString.create("Test"));
			setClickCallback(this::onClick);
		}

		private void onClick() {

			lastEvent = getCurrentEvent();
		}
	}
}
