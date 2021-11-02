package com.softicar.platform.ajax.dom.event;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.style.CssPixel;
import com.softicar.platform.dom.style.CssStyle;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;

public class AbstractAjaxDomEventTestDiv extends DomDiv {

	private final List<IDomEvent> events;

	public AbstractAjaxDomEventTestDiv() {

		this(100, 100);
	}

	public AbstractAjaxDomEventTestDiv(int width, int height) {

		this.events = new ArrayList<>();

		// for click events, we need a size
		setStyle(CssStyle.WIDTH, new CssPixel(width));
		setStyle(CssStyle.HEIGHT, new CssPixel(height));

		// for key events, we need a tab-index
		setTabIndex(0);
	}

	public void clearEvents() {

		events.clear();
	}

	public IDomEvent getEvent() {

		Assert.assertEquals("Expected exactly one event.", 1, events.size());
		return events.get(0);
	}

	public List<IDomEvent> getEvents() {

		return events;
	}

	protected void addEvent(IDomEvent event) {

		events.add(event);
	}
}
