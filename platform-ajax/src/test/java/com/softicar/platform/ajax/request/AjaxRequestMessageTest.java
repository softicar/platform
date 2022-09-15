package com.softicar.platform.ajax.request;

import com.softicar.platform.ajax.document.action.AjaxDocumentActionType;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.dom.document.DomDocument;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.DomModifier;
import com.softicar.platform.dom.event.DomRect;
import com.softicar.platform.dom.event.DomVector2d;
import com.softicar.platform.dom.event.DomVector3d;
import java.util.UUID;
import org.junit.Test;

public class AjaxRequestMessageTest extends AbstractTest {

	@Test
	public void testParseJson() {

		var uuid = UUID.fromString("c03c145c-dd8d-4280-b354-fcc980e5c934");
		var document = new DomDocument();
		var json = """
				{
					'instanceUuid'='%s',
					'requestIndex'=123,
					'actionType'='DOM_EVENT',
					'eventType'='WHEEL',
					'nodeId'='%s',
					'nodeRect'={'x':1.0,'y':2.1,'width':3.5,'height':4.6},
					'nodeValues'={'n1':'foo','n2':'bar'},
					'key'='ENTER',
					'modifierKeys':{'CONTROL'=true},
					'cursor':{'x':2.1,'y':5.2},
					'cursorRelative':{'x':234.3,'y':23.4},
					'wheelDelta':{'x':24.3,'y':-42.6,'z':73.5},
					'dragStart':{'x':4.1,'y':6.2},
					'dragPosition':{'x':23.1,'y':44.2},
					'windowPageOffset':{'x':5.1,'y':2.2},
					'windowInnerSize':{'x':88.1,'y':34.2}
				}
				""".formatted(uuid, document.getBody().getNodeIdString());
		var message = AjaxRequestMessage.parseJson(json);

		assertEquals(uuid, message.getInstanceUuid());
		assertEquals(123, message.getRequestIndex());
		assertEquals(AjaxDocumentActionType.DOM_EVENT, message.getActionType());
		assertEquals(DomEventType.WHEEL, message.getEventType());
		assertEquals(document.getBody(), message.getNode(document));
		assertEquals(new DomRect(1.0, 2.1, 3.5, 4.6), message.getNodeRect());
		assertEquals("{n1=foo, n2=bar}", message.getNodeValues().toString());
		assertEquals("ENTER", message.getKey());
		assertFalse(message.isModifierKey(DomModifier.ALT));
		assertTrue(message.isModifierKey(DomModifier.CONTROL));
		assertFalse(message.isModifierKey(DomModifier.META));
		assertFalse(message.isModifierKey(DomModifier.SHIFT));
		assertEquals(new DomVector2d(2.1, 5.2), message.getCursor());
		assertEquals(new DomVector2d(234.3, 23.4), message.getCursorRelative());
		assertEquals(new DomVector3d(24.3, -42.6, 73.5), message.getWheelDelta());
		assertEquals(new DomVector2d(4.1, 6.2), message.getDragStart());
		assertEquals(new DomVector2d(23.1, 44.2), message.getDragPosition());
		assertEquals(new DomVector2d(5.1, 2.2), message.getWindowPageOffset());
		assertEquals(new DomVector2d(88.1, 34.2), message.getWindowInnerSize());
	}

	@Test
	public void testValidation() {

		assertException("document instance", "{}");
		assertException("request index", "{'instanceUuid'='abc'}");
		assertException("action type", "{'instanceUuid'='abc','requestIndex'=0}");
		assertException("event type", "{'instanceUuid'='abc','requestIndex'=0,'actionType'='DOM_EVENT'}");

		// assert no exception
		AjaxRequestMessage.parseJson("{'instanceUuid'='abc','requestIndex'=0,'actionType'='KEEP_ALIVE'}");
		AjaxRequestMessage.parseJson("{'instanceUuid'='abc','requestIndex'=0,'actionType'='DOM_EVENT','eventType'='BLUR'}");
	}

	private void assertException(String expectedMessage, String json) {

		assertExceptionMessageContains(IDisplayString.create(expectedMessage), () -> AjaxRequestMessage.parseJson(json));
	}
}
