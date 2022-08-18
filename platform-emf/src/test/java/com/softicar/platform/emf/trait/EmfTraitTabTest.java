package com.softicar.platform.emf.trait;



import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.testing.Asserts;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.tab.DomTabBar;
import com.softicar.platform.emf.AbstractEmfTest;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfTestMarker;
import com.softicar.platform.emf.action.marker.EmfCommonActionMarker;
import com.softicar.platform.emf.editor.EmfEditAction;
import com.softicar.platform.emf.form.IEmfForm;
import com.softicar.platform.emf.form.IEmfFormFrame;
import com.softicar.platform.emf.test.simple.EmfTestObject;
import org.junit.Test;
import org.mockito.Mockito;

public class EmfTraitTabTest extends AbstractEmfTest {

	private static final Integer SOME_VALUE = 31;
	private static final Integer OTHER_VALUE = 37;
	private final EmfTestObject object;
	private final IEmfFormFrame<EmfTestObject> frame;

	public EmfTraitTabTest() {

		this.object = new EmfTestObject()//
			.setActive(true)
			.setName("foo")
			.setDay(Day.today())
			.save();
		this.frame = Mockito.mock(IEmfFormFrame.class);

		setNodeSupplier(() -> new TestDiv());
	}

	@Test
	public void testSaveWithNewTrait() {

		findBody().clickNode(EmfI18n.CONFIGURE_TRAIT);
		findBody().setInputValue(EmfTestTrait.VALUE, "" + SOME_VALUE);
		findBody().clickNode(EmfTestMarker.FORM_SAVE_AND_CLOSE);

		EmfTestTrait trait = Asserts.assertOne(EmfTestTrait.TABLE.loadAll());
		assertSame(object, trait.getObject());
		assertEquals(SOME_VALUE, trait.getValue());
	}

	@Test
	public void testSaveWithExistingTrait() {

		EmfTestTrait trait = EmfTestTrait.TABLE//
			.getOrCreate(object)
			.setValue(SOME_VALUE)
			.save();

		findBody().clickNode(new EmfCommonActionMarker(EmfEditAction.class));
		findBody().setInputValue(EmfTestTrait.VALUE, "" + OTHER_VALUE);
		findBody().clickNode(EmfTestMarker.FORM_SAVE_AND_CLOSE);

		trait.reload();
		assertSame(object, trait.getObject());
		assertEquals(OTHER_VALUE, trait.getValue());
	}

	@Test
	public void testCancelWithNewTrait() {

		findBody().clickNode(EmfI18n.CONFIGURE_TRAIT);
		findBody().clickNode(EmfTestMarker.FORM_CANCEL);

		Asserts.assertNone(EmfTestTrait.TABLE.loadAll());
		Mockito.verify(frame).closeFrame();
	}

	@Test
	public void testCancelWithExistingTrait() {

		EmfTestTrait.TABLE//
			.getOrCreate(object)
			.setValue(SOME_VALUE)
			.save();

		findBody().clickNode(new EmfCommonActionMarker(EmfEditAction.class));
		findBody().clickNode(EmfTestMarker.FORM_CANCEL);

		Mockito.verify(frame, Mockito.never()).closeFrame();
	}

	private class TestDiv extends DomDiv {

		public TestDiv() {

			IEmfForm<EmfTestObject> form = Mockito.mock(IEmfForm.class);
			Mockito.when(form.getTableRow()).thenReturn(object);
			Mockito.when(form.getFrame()).thenReturn(frame);

			DomTabBar tabBar = appendChild(new DomTabBar());
			tabBar.appendTab(new EmfTraitTab<>(form, EmfTestTrait.TABLE));
		}
	}
}
