package com.softicar.platform.dom.elements.input.auto;

import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsCssClasses;
import com.softicar.platform.dom.elements.bar.DomBar;
import com.softicar.platform.dom.elements.input.auto.entity.DomAutoCompleteEntityInput;
import com.softicar.platform.dom.elements.input.auto.string.DomAutoCompleteStringInput;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.event.IDomEventHandler;
import com.softicar.platform.dom.input.DomTextInput;
import com.softicar.platform.dom.input.IDomTextualInput;
import com.softicar.platform.dom.input.auto.DomAutoCompleteInputValidationMode;
import com.softicar.platform.dom.input.auto.DomAutoCompleteList;
import com.softicar.platform.dom.input.auto.IDomAutoCompleteInput;
import com.softicar.platform.dom.input.auto.IDomAutoCompleteInputConfiguration;
import com.softicar.platform.dom.input.auto.IDomAutoCompleteInputSelection;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * A generic auto-complete input element. It supplements a single-line text
 * input field with a filter-popup and validation functionality.
 * <p>
 * Supports different validation modes (see
 * {@link DomAutoCompleteInputValidationMode}).
 * <p>
 * There are standard implementations for the most common use cases:<br>
 * - String based input: {@link DomAutoCompleteStringInput}<br>
 * - {@link IEntity} based input: {@link DomAutoCompleteEntityInput}
 *
 * @author Alexander Schmidt
 */
public class DomAutoCompleteInput<T> extends DomDiv implements IDomAutoCompleteInput<T> {

	private final boolean sloppyAmbiguityCheck;
	private final DomAutoCompleteInputFilterDisplay filterDisplay;
	private final InputField inputField;
	private INullaryVoidFunction changeCallback;
	private final IDomAutoCompleteInputConfiguration configuration;
	protected final IDomAutoCompleteInputEngine<T> inputEngine;
	protected final DomBar inputBar;

	public DomAutoCompleteInput(IDomAutoCompleteInputEngine<T> inputEngine, boolean sloppyAmbiguityCheck, DomAutoCompleteInputValidationMode defaultMode) {

		this.inputEngine = inputEngine;
		this.sloppyAmbiguityCheck = sloppyAmbiguityCheck;
		this.inputBar = new DomBar();
		this.filterDisplay = new DomAutoCompleteInputFilterDisplay();
		this.inputField = inputBar.appendChild(new InputField());
		this.changeCallback = null;
		this.configuration = new DomAutoCompleteInputConfiguration(this, inputField);

		setCssClass(DomElementsCssClasses.DOM_AUTO_COMPLETE_INPUT);
		appendChild(inputBar);
		appendChild(filterDisplay);

		refreshFilters();

		initializeAutoComplete(defaultMode);
	}

	public void refreshFilters() {

		inputEngine.refresh();
		filterDisplay.refresh(inputEngine);
		refreshInputValidity();
	}

	// TODO change argument to Consumer<IDomAutoCompleteInputSelection> ?
	public void setChangeCallback(INullaryVoidFunction changeCallback) {

		if (changeCallback != null) {
			this.changeCallback = new CompositeCallback(this::refreshInputValidity, changeCallback)::apply;
			this.inputField.listenToEvent(DomEventType.CHANGE);
		} else {
			this.changeCallback = null;
			this.inputField.unlistenToEvent(DomEventType.CHANGE);
		}
	}

	public void applyChangeCallback() {

		Optional//
			.ofNullable(changeCallback)
			.ifPresent(INullaryVoidFunction::apply);
	}

	@Override
	public IDomAutoCompleteInputConfiguration getConfiguration() {

		return configuration;
	}

	@Override
	public IDomTextualInput getInputField() {

		return inputField;
	}

	@Override
	public DomAutoCompleteList getItemList(String pattern) {

		return new DomAutoCompleteListGenerator<>(inputEngine, DomAutoCompleteList.MAXIMUM_ELEMENTS_TO_LOAD)//
			.generate(getTrimmedLowerCase(pattern));
	}

	@Override
	public IDomAutoCompleteInputSelection<T> getSelection() {

		return new DomAutoCompleteInputSelection<>(//
			inputEngine,
			configuration,
			this::getMatchingItems,
			inputField.getInputTextTrimmed());
	}

	@Override
	public Optional<T> getValue() {

		return getSelection().getValue();
	}

	@Override
	public void setValue(T value) {

		String valueString = Optional//
			.ofNullable(value)
			.map(inputEngine::getDisplayString)
			.map(IDisplayString::toString)
			.orElse("");
		inputField.setInputText(valueString);
	}

	@Override
	public void setValueAndValidate(T value) {

		setValue(value);
		refreshInputValidity();
	}

	public DomAutoCompleteInput<T> setPlaceholder(IDisplayString placeholder) {

		inputField.setPlaceholder(placeholder);
		return this;
	}

	public DomAutoCompleteInput<T> setFocus(boolean focus) {

		inputField.setFocus(focus);
		return this;
	}

	public IDomElement setEnabled(boolean enabled) {

		getConfiguration().setEnabled(enabled);
		return inputField;
	}

	public DomAutoCompleteInput<T> select() {

		inputField.select();
		return this;
	}

	public DomAutoCompleteInput<T> setMaxLength(int maxLength) {

		inputField.setMaxLength(maxLength);
		return this;
	}

	/**
	 * Fetches the current, textual content of the input element, exactly as it
	 * was entered. No transformations like trimming or case-enforcement are
	 * applied.
	 *
	 * @return the raw, non-transformed String value that the input element
	 *         currently contains (never null)
	 */
	protected String getRawValueString() {

		return inputField.getInputText();
	}

	private Collection<T> getMatchingItems(String pattern) {

		if (sloppyAmbiguityCheck) {
			return getMatchingItems(pattern, 2);
		} else {
			// Assumes that the entered string is among the first DomAutoCompleteList.MAXIMUM_ELEMENT_COUNT matches, respecting capitalization.
			// FIXME This is still a problem in theory. However, it was neglected because the number of items which have names that only differ in
			// FIXME capitalization was assumed to always be lower than DomAutoCompleteList.MAXIMUM_ELEMENT_COUNT.
			return getMatchingItems(pattern, DomAutoCompleteList.MAXIMUM_ELEMENTS_TO_LOAD);
		}
	}

	private Collection<T> getMatchingItems(String pattern, int limit) {

		if (!pattern.isEmpty()) {
			return inputEngine.findMatches(pattern, limit);
		} else {
			return Collections.emptySet();
		}
	}

	private String getTrimmed(String pattern) {

		return Optional.ofNullable(pattern).map(String::trim).orElse("");
	}

	private String getTrimmedLowerCase(String pattern) {

		return Optional.ofNullable(getTrimmed(pattern)).map(String::toLowerCase).orElse("");
	}

	private void refreshInputValidity() {

		inputField.setInputText(inputField.getInputText());
		if (!getSelection().isValid()) {
			getDomEngine().setAutoCompleteInputInvalid(this);
		}
	}

	/**
	 * Enables the auto-complete engine features and performs subsequent
	 * initialization steps.
	 * <p>
	 * TODO Must be called as last step to ensure that indicators are shown. As
	 * soon as CSS based indicators (#38798) are implemented, this can be
	 * relaxed.
	 */
	private void initializeAutoComplete(DomAutoCompleteInputValidationMode defaultMode) {

		getDomEngine().enableAutoComplete(this);
		initializeEnabledAutoComplete(defaultMode);
	}

	/**
	 * Performs initialization steps that must happen after the auto-complete
	 * engine feature was enabled.
	 */
	private void initializeEnabledAutoComplete(DomAutoCompleteInputValidationMode defaultMode) {

		if (configuration.getValidationMode() != defaultMode) {
			configuration.setValidationMode(defaultMode);
		}
	}

	private class InputField extends DomTextInput implements IDomEventHandler {

		public InputField() {

			addCssClass(DomElementsCssClasses.DOM_AUTO_COMPLETE_INPUT_FIELD);
		}

		@Override
		public void handleDOMEvent(IDomEvent event) {

			if (changeCallback != null) {
				changeCallback.apply();
			}
		}
	}

	private class CompositeCallback implements INullaryVoidFunction {

		private final List<INullaryVoidFunction> callbacks;

		public CompositeCallback(INullaryVoidFunction...callbacks) {

			this.callbacks = Arrays.asList(callbacks);
		}

		@Override
		public void apply() {

			callbacks//
				.stream()
				.filter(Objects::nonNull)
				.forEach(INullaryVoidFunction::apply);
		}
	}
}
