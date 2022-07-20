package com.softicar.platform.emf.form;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.string.formatting.Formatting;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.refresh.bus.IDomRefreshBusEvent;
import com.softicar.platform.dom.refresh.bus.IDomRefreshBusListener;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import org.junit.Assert;

class EmfFormTestFrame<R extends IEmfTableRow<R, ?>> extends DomDiv implements IEmfFormFrame<R>, IDomRefreshBusListener {

	private IDisplayString title;
	private IDisplayString subTitle;
	private boolean closed;
	private IDomRefreshBusEvent refreshBusEvent;

	public EmfFormTestFrame() {

		this.title = null;
		this.subTitle = null;
		this.closed = false;
		this.refreshBusEvent = null;
	}

	@Override
	public void setTitle(IDisplayString title, IDisplayString subTitle) {

		this.title = title;
		this.subTitle = subTitle;
	}

	@Override
	public void closeFrame() {

		this.closed = true;
	}

	@Override
	public void focusFrame() {

		// nothing to do
	}

	@Override
	public void refresh(IDomRefreshBusEvent event) {

		refreshBusEvent = event;
	}

	public void assertIsClosed() {

		assertTrue("Frame was not closed.", closed);
	}

	public void assertIsNotClosed() {

		assertFalse("Frame was unexpectedly closed.", closed);
	}

	public void assertTitle(String expectedTitle, Object...arguments) {

		Assert.assertEquals(Formatting.format(expectedTitle, arguments), "" + title);
	}

	public void assertSubTitle(String expectedSubTitle, Object...arguments) {

		Assert.assertEquals(Formatting.format(expectedSubTitle, arguments), "" + subTitle);
	}

	public void assertIsChangedOnRefreshBus(Object object) {

		assertNotNull("Missing refresh bus event.", refreshBusEvent);
		assertTrue("Missing object on refresh bus event.", refreshBusEvent.isChanged(object));
	}
}
