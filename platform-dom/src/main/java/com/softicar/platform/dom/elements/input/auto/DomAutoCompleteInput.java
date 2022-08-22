package com.softicar.platform.dom.elements.input.auto;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.elements.DomElementsCssClasses;
import com.softicar.platform.dom.elements.bar.DomBar;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.input.AbstractDomValueInputDiv;
import com.softicar.platform.dom.input.IDomTextualInput;
import com.softicar.platform.dom.input.auto.DomAutoCompleteInputValidationMode;
import com.softicar.platform.dom.input.auto.IDomAutoCompleteInput;
import com.softicar.platform.dom.input.auto.IDomAutoCompleteInputConfiguration;
import com.softicar.platform.dom.input.auto.IDomAutoCompleteInputSelection;
import com.softicar.platform.dom.style.CssPixel;
import com.softicar.platform.dom.style.CssStyle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * A generic auto-complete input element. It supplements a single-line text
 * input field with a filter-popup and validation functionality.
 * <p>
 * Supports different validation modes (see
 * {@link DomAutoCompleteInputValidationMode}).
 *
 * @author Alexander Schmidt
 */
public class DomAutoCompleteInput<T> extends AbstractDomValueInputDiv<T> implements IDomAutoCompleteInput<T> {

	protected final IDomAutoCompleteInputEngine<T> inputEngine;
	protected final DomBar inputBar;
	private final DomAutoCompleteInputFilterDisplay filterDisplay;
	private final DomAutoCompleteBackdrop backdrop;
	private final DomAutoCompletePopup<T> popup;
	private final DomAutoCompleteInputField inputField;
	private final IDomAutoCompleteInputConfiguration configuration;
	private final Collection<INullaryVoidFunction> inputConstraintRefreshCallbacks;
	private DomAutoCompleteIndicator<T> indicator;
	private T committedValue;
	private boolean hasFocus;

	public DomAutoCompleteInput(Supplier<Collection<T>> loader) {

		this(new DomAutoCompleteDefaultInputEngine<>(loader));
	}

	public DomAutoCompleteInput(IDomAutoCompleteInputEngine<T> inputEngine) {

		this(inputEngine, DomAutoCompleteInputValidationMode.DEDUCTIVE);
	}

	public DomAutoCompleteInput(IDomAutoCompleteInputEngine<T> inputEngine, DomAutoCompleteInputValidationMode validationMode) {

		this.inputEngine = inputEngine;
		this.inputBar = new DomBar();
		this.filterDisplay = new DomAutoCompleteInputFilterDisplay(inputEngine);
		this.backdrop = new DomAutoCompleteBackdrop(this);
		this.popup = new DomAutoCompletePopup<>(this);
		this.inputField = new DomAutoCompleteInputField(this);
		this.configuration = new DomAutoCompleteInputConfiguration(this, validationMode);
		this.inputConstraintRefreshCallbacks = new ArrayList<>();
		this.indicator = new DomAutoCompleteIndicator<>(this);
		this.committedValue = null;
		this.hasFocus = false;

		setCssClass(DomElementsCssClasses.DOM_AUTO_COMPLETE_INPUT);
		appendChild(inputBar);
		appendChild(filterDisplay);
		appendChild(indicator);
		inputBar.appendChild(inputField);
	}

	// TODO make final
	public void refreshInputConstraints() {

		inputEngine.refresh();
		inputEngine.reloadCache();
		indicator.refresh();
		filterDisplay.refresh();

		inputConstraintRefreshCallbacks.forEach(INullaryVoidFunction::apply);
	}

	@Override
	public IDomAutoCompleteInputConfiguration getConfiguration() {

		return configuration;
	}

	@Override
	public IDomAutoCompleteInputSelection<T> getSelection() {

		return new DomAutoCompleteInputSelection<>(//
			inputEngine,
			configuration,
			this::getMatchingValues,
			inputField.getValueTextTrimmed());
	}

	@Override
	public Optional<T> getValue() {

		return getSelection().getValue();
	}

	@Override
	public void setValue(T value) {

		setFieldValue(value);
		this.committedValue = value;
		refreshIndicator();
	}

	// ------------------------------ input field methods ------------------------------ //

	@Override
	public IDomTextualInput getInputField() {

		return inputField;
	}

	public boolean isBlank() {

		return inputField.isBlank();
	}

	public DomAutoCompleteInput<T> setPlaceholder(IDisplayString placeholder) {

		inputField.setPlaceholder(placeholder);
		return this;
	}

	public DomAutoCompleteInput<T> setFocus(boolean focus) {

		inputField.setFocus(focus);
		return this;
	}

	public DomAutoCompleteInput<T> selectText() {

		inputField.selectText();
		return this;
	}

	// ------------------------------ event handling ------------------------------ //

	/**
	 * Use this method to add functions that should be executed when this input
	 * gets refreshed via {@link #refreshInputConstraints()}.
	 *
	 * @param refreshCallback
	 *            the callback to be executed (never <i>null</i>)
	 */
	public void addInputConstraintRefreshCallback(INullaryVoidFunction refreshCallback) {

		inputConstraintRefreshCallbacks.add(Objects.requireNonNull(refreshCallback));
	}

	@Override
	protected void onChangeCallbackAdded() {

		this.inputField.listenToEvent(DomEventType.CHANGE);
	}

	protected void onInput() {

		refreshPopup();
		refreshIndicator();
	}

	protected void onChange() {

		refreshIndicator();

		var value = getValueNoThrow().orElse(null);
		if (!Objects.equals(value, committedValue)) {
			this.committedValue = value;
			executeChangeCallbacks();
		}
	}

	protected void onArrowDown() {

		if (popup.isAppended()) {
			popup.moveSelectionDown();
		} else {
			refreshPopup();
		}
	}

	protected void onArrowUp() {

		if (popup.isAppended()) {
			popup.moveSelectionUp();
		} else {
			refreshPopup();
		}
	}

	protected void onEnterOrTab() {

		if (popup.isAppended()) {
			popup.applySelection();
		}
	}

	protected void onSelection(T value) {

		setFieldValue(value);
		closePopup();
		onChange();
		inputField.focus();
	}

	protected void onFocus() {

		this.hasFocus = true;
		refreshIndicator();
	}

	protected void onBlur() {

		this.hasFocus = false;
		closePopup();
		deduceValue();
		onChange();
	}

	protected void onBackdropClickOrEscape() {

		closePopup();
		deduceValue();
		onChange();
		inputField.focus();
	}

	// ------------------------------ internal ------------------------------ //

	private void deduceValue() {

		if (!isBlank()) {
			var matches = inputEngine.findMatches(getPattern(), 2);
			if (matches.size() == 1) {
				var value = matches.iterator().next();
				inputField.setValue(inputEngine.getDisplayString(value).toString());
			}
		}
	}

	@Override
	protected void doSetDisabled(boolean disabled) {

		inputField.setDisabled(disabled);
	}

	protected String getPattern() {

		return inputField.getValueTextTrimmed().toLowerCase();
	}

	protected boolean hasFocus() {

		return hasFocus;
	}

	protected void refreshIndicator() {

		indicator.refresh();
	}

	private void refreshPopup() {

		if (!popup.isAppended()) {
			appendChild(backdrop);
			getDomEngine().raise(backdrop);

			appendChild(popup);
			getDomEngine().raise(popup);

			getDomEngine().raise(inputField);
			getDomEngine().raise(indicator);
		}

		var rect = getCurrentEvent().getBoundingClientRect();
		popup.setStyle(CssStyle.TOP, new CssPixel(rect.getHeight()));
		popup.setStyle(CssStyle.MIN_WIDTH, new CssPixel(rect.getWidth()));
		popup.refresh();
	}

	private void closePopup() {

		if (popup.isAppended()) {
			backdrop.disappend();
			popup.disappend();

			inputField.unsetStyle(CssStyle.Z_INDEX);
			indicator.unsetStyle(CssStyle.Z_INDEX);
		}
	}

	private void setFieldValue(T value) {

		String valueString = Optional//
			.ofNullable(value)
			.map(inputEngine::getDisplayString)
			.map(IDisplayString::toString)
			.orElse("");
		inputField.setValue(valueString);
	}

	private Collection<T> getMatchingValues(String pattern) {

		return getMatchingValues(pattern, 2);
	}

	private Collection<T> getMatchingValues(String pattern, int limit) {

		if (!pattern.isEmpty()) {
			return inputEngine.findMatches(pattern, limit);
		} else {
			return Collections.emptySet();
		}
	}
}
