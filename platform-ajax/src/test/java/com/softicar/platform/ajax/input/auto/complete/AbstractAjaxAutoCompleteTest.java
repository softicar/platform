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

	protected List<String> getAutoCompletePopupValueNames() {

		return testEngine.getAutoCompleteInput().getAutoCompletePopupValueNames();
	}

	protected void clickAutoCompletePopupValue(int index) {

		testEngine.getAutoCompleteInput().clickAutoCompletePopupValue(index);
	}

	protected Optional<Integer> getAutoCompletePopupSelectedValueIndex() {

		return testEngine.getAutoCompleteInput().getAutoCompletePopupSelectedValueIndex();
	}

	// -------------------- status -------------------- //

	protected boolean isAutoCompletePopupDisplayed() {

		return testEngine.getAutoCompleteInput().isAutoCompletePopupDisplayed();
	}

	protected boolean isAutoCompletePopupValueSelected() {

		return getAutoCompletePopupSelectedValueIndex().isPresent();
	}

	protected boolean isAutoCompleteValuePlaceholderElementDisplayed() {

		return testEngine.getAutoCompleteInput().isAutoCompleteValuePlaceholderDisplayed();
	}

	protected boolean isAutoCompleteMoreValuesInfoElementDisplayed() {

		return testEngine.getAutoCompleteInput().isAutoCompleteMoreValuesInfoDisplayed();
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
	protected <I> void assertPopupValues(Function<I, String> nameGetter, I...values) {

		assertPopupValues(nameGetter, Arrays.asList(values));
	}

	protected <I> void assertPopupValues(Function<I, String> nameGetter, List<I> values) {

		List<String> elementNames = getAutoCompletePopupValueNames();
		try {
			assertEquals(//
				"Unexpected number of values in the popup.",
				values.size(),
				elementNames.size());
			for (int i = 0; i < values.size(); i++) {
				assertContainsText(elementNames.get(i), nameGetter.apply(values.get(i)));
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

		return testEngine.getAutoCompleteInput().isAutoCompleteIndicatorDisplayed(indicatorType);
	}

	protected boolean isAutoCompleteIndicatorNotDisplayed() {

		return testEngine.getAutoCompleteInput().isAutoCompleteIndicatorDisplayed(null);
	}

	// -------------------- backdrop -------------------- //

	protected boolean isAutoCompleteBackdropDisplayed() {

		return testEngine.getAutoCompleteInput().isAutoCompleteBackdropDisplayed();
	}

	protected void clickAutoCompleteBackdrop() {

		testEngine.getAutoCompleteInput().clickAutoCompleteBackdrop();
	}

	// -------------------- miscellaneous -------------------- //

	protected void clickAutoCompleteValue(AjaxAutoCompleteTestValue value) {

		testEngine.getAutoCompleteInput().clickAutoCompleteValue(value);
	}

	// -------------------- private -------------------- //

	private void assertContainsText(String string, String subString) {

		assertTrue(String.format("Text '%s' does not contain '%s'.", string, subString), string.contains(subString));
	}

	protected class IndicatorProxy {

		public void assertIndicatesAmbiguous() {

			assertIndicates(DomAutoCompleteIndicatorType.AMBIGUOUS);
		}

		public void assertIndicatesIllegal() {

			assertIndicates(DomAutoCompleteIndicatorType.ILLEGAL);
		}

		public void assertIndicates(DomAutoCompleteIndicatorType indicatorType) {

			boolean actual = isAutoCompleteIndicatorDisplayed(indicatorType);
			if (indicatorType != null) {
				assertTrue(//
					"Expected " + indicatorType.getTitle() + " indicator, but encountered " + getDisplayedIndicatorName() + " indicator.",
					actual);
			} else {
				assertTrue("Unexpectedly encountered an indicator.", actual);
			}
		}

		public void assertIndicatesNothing() {

			assertIndicates(null);
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
