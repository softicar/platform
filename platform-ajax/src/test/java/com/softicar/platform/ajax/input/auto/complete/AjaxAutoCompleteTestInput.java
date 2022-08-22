package com.softicar.platform.ajax.input.auto.complete;

import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import com.softicar.platform.dom.input.auto.DomAutoCompleteInputValidationMode;
import org.junit.Assert;

public class AjaxAutoCompleteTestInput extends DomAutoCompleteInput<AjaxAutoCompleteTestValue> {

	private int eventCount;
	private final AjaxAutoCompleteTestInputEngine engine;

	public AjaxAutoCompleteTestInput(AjaxAutoCompleteTestInputEngine engine) {

		super(engine, DomAutoCompleteInputValidationMode.DEDUCTIVE);
		this.engine = engine;

		this.eventCount = 0;
	}

	public void listenToChange() {

		addChangeCallback(this::handleDOMEvent);
	}

	public AjaxAutoCompleteTestInputEngine getEngine() {

		return engine;
	}

	public void handleDOMEvent() {

		this.eventCount++;
	}

	public int getEventCount() {

		return eventCount;
	}

	public void assertOneEvent() {

		Assert.assertEquals("Expected exactly one event.", 1, eventCount);
	}

	public void assertNoEvent() {

		Assert.assertEquals("Expected no event.", 0, eventCount);
	}

	public void clearEvents() {

		this.eventCount = 0;
	}
}
