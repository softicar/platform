package com.softicar.platform.emf.form.tab.factory;


import com.softicar.platform.dom.elements.tab.DomTab;
import com.softicar.platform.emf.AbstractEmfTest;
import com.softicar.platform.emf.form.IEmfForm;
import com.softicar.platform.emf.test.simple.EmfTestObject;
import com.softicar.platform.emf.trait.EmfTraitTab;
import java.util.Collection;
import org.junit.Test;
import org.mockito.Mockito;

public class EmfFormTabConfigurationTest extends AbstractEmfTest {

	private final EmfTestObject object;
	private final IEmfForm<EmfTestObject> form;

	public EmfFormTabConfigurationTest() {

		this.object = new EmfTestObject().save();
		this.form = Mockito.mock(IEmfForm.class);
		Mockito.when(form.getTableRow()).thenReturn(object);
	}

	@Test
	public void testWithAuthorization() {

		object.addAuthorizedUser(user);

		Collection<DomTab> tabs = new EmfFormTabConfiguration<>(EmfTestObject.TABLE).createVisibleTabs(form);

		DomTab tab = assertOne(tabs);
		assertTrue(tab instanceof EmfTraitTab);
	}

	@Test
	public void testWithoutAuthorization() {

		Collection<DomTab> tabs = new EmfFormTabConfiguration<>(EmfTestObject.TABLE).createVisibleTabs(form);

		assertNone(tabs);
	}
}
