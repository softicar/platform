package com.softicar.platform.ajax.input.auto.complete;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.AbstractAjaxSeleniumLowLevelTest;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.elements.IAjaxSeleniumTestDomAutoCompleteInputIndicator;
import com.softicar.platform.common.core.logging.Log;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public abstract class AbstractAjaxAutoCompleteTest extends AbstractAjaxSeleniumLowLevelTest {

	protected IndicatorProxy indicator;

	public AbstractAjaxAutoCompleteTest() {

		this.indicator = new IndicatorProxy();
	}

	// -------------------- elements -------------------- //

	protected List<String> getAutoCompletePopupItemNames() {

		return testEngine.getAutoCompleteExtension().getAutoCompletePopupItemNames();
	}

	protected void clickAutoCompletePopupItem(int index) {

		testEngine.getAutoCompleteExtension().clickAutoCompletePopupItem(index);
	}

	protected Optional<Integer> getAutoCompletePopupSelectedItemIndex() {

		return testEngine.getAutoCompleteExtension().getAutoCompletePopupSelectedItemIndex();
	}

	// -------------------- waiting -------------------- //

	protected void waitForAutoCompletePopup() {

		testEngine.getAutoCompleteExtension().waitForAutoCompletePopup();
	}

	protected void waitForAutoCompletePopupToHide() {

		testEngine.getAutoCompleteExtension().waitForAutoCompletePopupToHide();
	}

	protected void waitForIndicatorToHide(IAjaxSeleniumTestDomAutoCompleteInputIndicator indicator) {

		testEngine.getAutoCompleteExtension().waitForIndicatorToHide(indicator);
	}

	// -------------------- status -------------------- //

	protected boolean isAutoCompletePopupDisplayed() {

		return testEngine.getAutoCompleteExtension().isAutoCompletePopupDisplayed();
	}

	protected boolean isAutoCompletePopupItemSelected() {

		return getAutoCompletePopupSelectedItemIndex().isPresent();
	}

	protected boolean isAutoCompleteItemPlaceholderElementDisplayed() {

		return testEngine.getAutoCompleteExtension().isAutoCompleteItemPlaceholderDisplayed();
	}

	protected boolean isAutoCompleteMoreItemsInfoElementDisplayed() {

		return testEngine.getAutoCompleteExtension().isAutoCompleteMoreItemsInfoDisplayed();
	}

	// -------------------- asserts -------------------- //

	protected void assertPopupIsDisplayed(boolean displayed) {

		if (displayed) {
			assertTrue("Expected auto-complete popup to be displayed.", isAutoCompletePopupDisplayed());
		} else {
			assertFalse("Expected auto-complete popup to not be displayed.", isAutoCompletePopupDisplayed());
		}
	}

	@SuppressWarnings("unchecked")
	protected <I> void assertPopupItems(Function<I, String> nameGetter, I...items) {

		assertPopupItems(nameGetter, Arrays.asList(items));
	}

	protected <I> void assertPopupItems(Function<I, String> nameGetter, List<I> items) {

		List<String> elementNames = getAutoCompletePopupItemNames();
		try {
			assertEquals(//
				"Unexpected number of items in the popup.",
				items.size(),
				elementNames.size());
			for (int i = 0; i < items.size(); i++) {
				assertContainsText(elementNames.get(i), nameGetter.apply(items.get(i)));
			}
		} catch (AssertionError error) {
			Log.finfo("elements {");
			elementNames.forEach(e -> Log.finfo(e));
			Log.finfo("}");
			throw error;
		}
	}

	// -------------------- indicators -------------------- //

	protected boolean isAutoCompleteIndicatorDisplayed(Indicator indicator) {

		return testEngine.getAutoCompleteExtension().isAutoCompleteIndicatorDisplayed(indicator);
	}

	// -------------------- modality -------------------- //

	protected boolean isAutoCompleteModalDivDisplayed() {

		return testEngine.getAutoCompleteExtension().isAutoCompleteModalDivDisplayed();
	}

	protected void clickAutoCompleteModalDiv() {

		testEngine.getAutoCompleteExtension().clickAutoCompleteModalDiv();
	}

	// -------------------- miscellaneous -------------------- //

	protected void clickAutoCompleteItem(AjaxAutoCompleteTestItem item) {

		testEngine.getAutoCompleteExtension().clickAutoCompleteItem(item);
	}

	// -------------------- private -------------------- //

	private void assertContainsText(String string, String subString) {

		assertTrue(String.format("Text '%s' does not contain '%s'.", string, subString), string.contains(subString));
	}

	public static enum Indicator implements IAjaxSeleniumTestDomAutoCompleteInputIndicator {

		COMMITTING("committing", "AutoCompleteIndicatorCommitting"),
		LOADING("loading", "AutoCompleteIndicatorLoading"),
		NOT_OKAY("not-okay", "AutoCompleteIndicatorNotOkay"),
		VALUE_AMBIGUOUS("value-ambiguous", "AutoCompleteIndicatorValueAmbiguous"),
		VALUE_ILLEGAL("value-illegal", "AutoCompleteIndicatorValueIllegal"),
		VALUE_MISSING("value-missing", "AutoCompleteIndicatorValueMissing"),
		VALUE_VALID("value-valid", "AutoCompleteIndicatorValueValid"),
		//
		;

		private final String name;
		private final String idString;

		private Indicator(String name, String idString) {

			this.name = name;
			this.idString = idString;
		}

		@Override
		public String getName() {

			return name;
		}

		@Override
		public String getIdString() {

			return idString;
		}
	}

	protected class IndicatorProxy {

		public void assertCommitting() {

			assertCommitting(true);
		}

		public void assertCommitting(boolean displayed) {

			assertIndicates(Indicator.COMMITTING, displayed);
		}

		public void assertLoading() {

			assertLoading(true);
		}

		public void assertLoading(boolean displayed) {

			assertIndicates(Indicator.LOADING, displayed);
		}

		public void assertNotOkay() {

			assertNotOkay(true);
		}

		public void assertNotOkay(boolean displayed) {

			assertIndicates(Indicator.NOT_OKAY, displayed);
		}

		public void assertValueAmbiguous() {

			assertValueAmbiguous(true);
		}

		public void assertValueAmbiguous(boolean displayed) {

			assertIndicates(Indicator.VALUE_AMBIGUOUS, displayed);
		}

		public void assertValueIllegal() {

			assertValueIllegal(true);
		}

		public void assertValueIllegal(boolean displayed) {

			assertIndicates(Indicator.VALUE_ILLEGAL, displayed);
		}

		public void assertValueMissing() {

			assertValueMissing(true);
		}

		public void assertValueMissing(boolean displayed) {

			assertIndicates(Indicator.VALUE_MISSING, displayed);
		}

		public void assertValueValid() {

			assertValueValid(true);
		}

		public void assertValueValid(boolean displayed) {

			assertIndicates(Indicator.VALUE_VALID, displayed);
		}

		public void assertIndicates(Indicator indicator) {

			assertIndicates(indicator, true);
		}

		public void assertIndicates(Indicator indicator, boolean displayed) {

			if (displayed) {
				assertTrue(//
					"Expected " + indicator.getName() + " indicator, but encountered " + getDisplayedIndicatorName() + " indicator.",
					isAutoCompleteIndicatorDisplayed(indicator));
			} else {
				assertFalse(//
					"Unexpectedly encountered " + indicator.getName() + " indicator.",
					isAutoCompleteIndicatorDisplayed(indicator));
			}
		}

		private String getDisplayedIndicatorName() {

			return getDisplayedIndicator()//
				.map(it -> it.getName())
				.orElse("none");
		}

		private Optional<Indicator> getDisplayedIndicator() {

			for (Indicator indicator: Indicator.values()) {
				if (isAutoCompleteIndicatorDisplayed(indicator)) {
					return Optional.of(indicator);
				}
			}
			return Optional.empty();
		}
	}
}
