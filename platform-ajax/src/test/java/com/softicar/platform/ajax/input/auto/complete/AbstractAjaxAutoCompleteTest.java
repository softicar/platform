package com.softicar.platform.ajax.input.auto.complete;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.AbstractAjaxSeleniumLowLevelTest;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteIndicatorType;
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

	protected void waitForIndicatorToHide(DomAutoCompleteIndicatorType indicatorType) {

		testEngine.getAutoCompleteExtension().waitForIndicatorToHide(indicatorType);
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

	protected boolean isAutoCompleteIndicatorDisplayed(DomAutoCompleteIndicatorType indicatorType) {

		return testEngine.getAutoCompleteExtension().isAutoCompleteIndicatorDisplayed(indicatorType);
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

	protected class IndicatorProxy {

		public void assertNotOkay() {

			assertNotOkay(true);
		}

		public void assertNotOkay(boolean displayed) {

			assertIndicates(DomAutoCompleteIndicatorType.NOT_OKAY, displayed);
		}

		public void assertValueAmbiguous() {

			assertValueAmbiguous(true);
		}

		public void assertValueAmbiguous(boolean displayed) {

			assertIndicates(DomAutoCompleteIndicatorType.AMBIGUOUS, displayed);
		}

		public void assertValueIllegal() {

			assertValueIllegal(true);
		}

		public void assertValueIllegal(boolean displayed) {

			assertIndicates(DomAutoCompleteIndicatorType.ILLEGAL, displayed);
		}

		public void assertValueMissing() {

			assertValueMissing(true);
		}

		public void assertValueMissing(boolean displayed) {

			assertIndicates(DomAutoCompleteIndicatorType.MISSING, displayed);
		}

		public void assertValueValid() {

			assertValueValid(true);
		}

		public void assertValueValid(boolean displayed) {

			assertIndicates(DomAutoCompleteIndicatorType.VALID, displayed);
		}

		public void assertIndicates(DomAutoCompleteIndicatorType indicatorType) {

			assertIndicates(indicatorType, true);
		}

		public void assertIndicates(DomAutoCompleteIndicatorType indicatorType, boolean displayed) {

			if (displayed) {
				assertTrue(//
					"Expected " + indicatorType.getTitle() + " indicator, but encountered " + getDisplayedIndicatorName() + " indicator.",
					isAutoCompleteIndicatorDisplayed(indicatorType));
			} else {
				assertFalse(//
					"Unexpectedly encountered " + indicatorType.getTitle() + " indicator.",
					isAutoCompleteIndicatorDisplayed(indicatorType));
			}
		}

		private String getDisplayedIndicatorName() {

			return getDisplayedIndicator()//
				.map(it -> it.getTitle().toString())
				.orElse("none");
		}

		private Optional<DomAutoCompleteIndicatorType> getDisplayedIndicator() {

			for (DomAutoCompleteIndicatorType indicatorType: DomAutoCompleteIndicatorType.values()) {
				if (isAutoCompleteIndicatorDisplayed(indicatorType)) {
					return Optional.of(indicatorType);
				}
			}
			return Optional.empty();
		}
	}
}
