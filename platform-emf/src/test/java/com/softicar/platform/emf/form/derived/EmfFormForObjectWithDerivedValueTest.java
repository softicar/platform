package com.softicar.platform.emf.form.derived;

import com.softicar.platform.emf.AbstractEmfTest;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.form.EmfForm;
import com.softicar.platform.emf.management.EmfManagementDiv;
import com.softicar.platform.emf.table.listener.IEmfSaveHook;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.test.module.EmfTestModuleInstance;
import com.softicar.platform.emf.validation.IEmfValidator;
import org.junit.Test;

/**
 * This test ensures that all {@link IEmfSaveHook#beforeSave} instances are
 * called before any {@link IEmfValidator} instance is called by
 * {@link EmfForm}.
 * <p>
 * This behavior is crucial, because {@link IEmfSaveHook#beforeSave} may be used
 * to set derived values of an {@link IEmfTableRow}. These derived values must
 * be updated before any validation is done.
 *
 * @author Oliver Richers
 */
public class EmfFormForObjectWithDerivedValueTest extends AbstractEmfTest {

	public EmfFormForObjectWithDerivedValueTest() {

		setNodeSupplier(() -> new EmfManagementDiv<>(EmfTestObjectWithDerivedValue.TABLE, EmfTestModuleInstance.getInstance()));
	}

	@Test
	public void testCreationOfNewObject() {

		findManagementDiv().clickCreateButton();

		var popup = findFormPopup(EmfTestObjectWithDerivedValue.class);
		popup.setInputValue(EmfTestObjectWithDerivedValue.VALUE, "7");
		popup.clickSaveButton();

		// verify derived value computed before validation is done
		EmfTestObjectWithDerivedValue object = assertOne(EmfTestObjectWithDerivedValue.TABLE.loadAll());
		assertFalse(object.impermanent());
		assertNotNull(object.getId());
		assertEquals(7, object.getValue());
		assertEquals(49, object.getDerivedValue());
	}

	@Test
	public void testFailingCreationOfNewObject() {

		findManagementDiv().clickCreateButton();

		var popup = findFormPopup(EmfTestObjectWithDerivedValue.class);
		popup.setInputValue(EmfTestObjectWithDerivedValue.VALUE, "");
		popup.clickSaveButton();

		// no object is created and diagnostics are shown
		assertNone(EmfTestObjectWithDerivedValue.TABLE.loadAll());
		popup.assertContainsText(EmfI18n.THE_ATTRIBUTE_ARG1_IS_MANDATORY.toDisplay(EmfTestObjectWithDerivedValue.VALUE.getTitle()));
		popup.assertContainsText(EmfI18n.THE_ATTRIBUTE_ARG1_IS_MANDATORY.toDisplay(EmfTestObjectWithDerivedValue.DERIVED_VALUE.getTitle()));
	}
}
