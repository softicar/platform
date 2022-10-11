package com.softicar.platform.demo.core.module.moment;

import com.softicar.platform.common.date.CommonDateI18n;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.date.Time;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.management.EmfManagementDivBuilder;
import com.softicar.platform.emf.test.tester.EmfFormPopupTester;
import org.junit.Test;

public class DemoMomentInputTest extends AbstractDemoCoreModuleTest {

	public DemoMomentInputTest() {

		setNodeSupplier(() -> new EmfManagementDivBuilder<>(AGDemoMoment.TABLE, moduleInstance).build());
	}

	@Test
	public void testWithValidInputs() {

		findManagementDiv().clickCreateButton();

		EmfFormPopupTester popup = findFormPopup(AGDemoMoment.class);
		popup.setInputValue(AGDemoMoment.DAY, "2022-01-01");
		popup.setTimeInputValue(AGDemoMoment.TIME, "1:2:3");
		popup.clickSaveAndCloseButton();

		AGDemoMoment moment = assertOne(AGDemoMoment.TABLE.loadAll());
		assertEquals(Day.fromYMD(2022, 1, 1), moment.getDay());
		assertEquals(new Time(1, 2, 3), moment.getTime());
		assertEquals(DayTime.fromYMD_HMS(2022, 1, 1, 0, 0, 0), moment.getPointInTime());
		// note that the point-in-time value is derived from the day input, see DemoMomentPointInTimeInput
	}

	@Test
	public void testWithMissingInputs() {

		findManagementDiv().clickCreateButton();

		EmfFormPopupTester popup = findFormPopup(AGDemoMoment.class);
		popup.setInputValue(AGDemoMoment.DAY, "");
		popup.setTimeInputValue(AGDemoMoment.TIME, "::");
		popup.setDayTimeInputValue(AGDemoMoment.POINT_IN_TIME, "", "::");
		popup.clickSaveAndCloseButton();

		assertNone(AGDemoMoment.TABLE.loadAll());
		popup.findNode(AGDemoMoment.DAY).assertContainsText(EmfI18n.THE_ATTRIBUTE_ARG1_IS_MANDATORY.toDisplay(AGDemoMoment.DAY.getTitle()));
		popup.findNode(AGDemoMoment.TIME).assertContainsText(EmfI18n.THE_ATTRIBUTE_ARG1_IS_MANDATORY.toDisplay(AGDemoMoment.TIME.getTitle()));
		popup.findNode(AGDemoMoment.POINT_IN_TIME).assertContainsText(EmfI18n.THE_ATTRIBUTE_ARG1_IS_MANDATORY.toDisplay(AGDemoMoment.POINT_IN_TIME.getTitle()));
	}

	@Test
	public void testWithInvalidInputs() {

		findManagementDiv().clickCreateButton();

		EmfFormPopupTester popup = findFormPopup(AGDemoMoment.class);
		popup.setInputValue(AGDemoMoment.DAY, "foo");
		popup.setTimeInputValue(AGDemoMoment.TIME, "23:59:");
		popup.setDayTimeInputValue(AGDemoMoment.POINT_IN_TIME, "2022-04-31", "42:59:59");
		popup.clickSaveAndCloseButton();

		assertNone(AGDemoMoment.TABLE.loadAll());
		popup.findNode(AGDemoMoment.DAY).assertContainsText(CommonDateI18n.ILLEGAL_DATE_SPECIFICATION_ARG1.toDisplay("foo"));
		popup.findNode(AGDemoMoment.TIME).assertContainsText(CommonDateI18n.ILLEGAL_TIME_SPECIFICATION_ARG1.toDisplay("23:59:"));
		popup.findNode(AGDemoMoment.POINT_IN_TIME).assertContainsText(CommonDateI18n.ILLEGAL_DATE_SPECIFICATION_ARG1.toDisplay("2022-04-31"));
	}

	@Test
	public void testWithMissingTimeInput() {

		findManagementDiv().clickCreateButton();

		EmfFormPopupTester popup = findFormPopup(AGDemoMoment.class);
		popup.setDayTimeInputValue(AGDemoMoment.POINT_IN_TIME, "2022-01-31", "::");
		popup.clickSaveAndCloseButton();

		assertNone(AGDemoMoment.TABLE.loadAll());
		popup.findNode(AGDemoMoment.POINT_IN_TIME).assertContainsText(CommonDateI18n.MISSING_TIME_SPECIFICATION);
	}

	@Test
	public void testWithMissingDateInput() {

		findManagementDiv().clickCreateButton();

		EmfFormPopupTester popup = findFormPopup(AGDemoMoment.class);
		popup.setDayTimeInputValue(AGDemoMoment.POINT_IN_TIME, "", "13:23:03");
		popup.clickSaveAndCloseButton();

		assertNone(AGDemoMoment.TABLE.loadAll());
		popup.findNode(AGDemoMoment.POINT_IN_TIME).assertContainsText(CommonDateI18n.MISSING_DATE_SPECIFICATION);
	}
}
