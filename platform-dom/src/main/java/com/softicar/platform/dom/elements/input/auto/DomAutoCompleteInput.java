package com.softicar.platform.dom.elements.input.auto;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.elements.bar.DomBar;
import com.softicar.platform.dom.input.AbstractDomValueInputDiv;
import com.softicar.platform.dom.input.DomInputException;
import com.softicar.platform.dom.input.IDomTextualInput;
import com.softicar.platform.dom.style.CssPixel;
import com.softicar.platform.dom.style.CssStyle;
import java.util.ArrayList;
import java.util.Collection;
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
public class DomAutoCompleteInput<T> extends AbstractDomValueInputDiv<T> {

	public static final int MAXIMUM_ELEMENTS_TO_DISPLAY = 16;

	private final Collection<INullaryVoidFunction> inputConstraintRefreshCallbacks;
	private final IDomAutoCompleteInputEngine<T> inputEngine;
	private final DomAutoCompleteInputValidationMode validationMode;
	private final DomAutoCompleteValueAndStateCache<T> valueAndStateCache;
	private final DomBar inputBar;
	private final DomAutoCompleteInputField inputField;
	private final DomAutoCompleteInputFilterDisplay filterDisplay;
	private final DomAutoCompleteIndicator<T> indicator;
	private final DomAutoCompleteBackdrop backdrop;
	private final DomAutoCompletePopup<T> popup;
	private T committedValue;

	public DomAutoCompleteInput(Supplier<Collection<T>> loader) {

		this(new DomAutoCompleteDefaultInputEngine<>(loader));
	}

	public DomAutoCompleteInput(Supplier<Collection<T>> loader, DomAutoCompleteInputValidationMode validationMode) {

		this(new DomAutoCompleteDefaultInputEngine<>(loader), validationMode);
	}

	public DomAutoCompleteInput(IDomAutoCompleteInputEngine<T> inputEngine) {

		this(inputEngine, DomAutoCompleteInputValidationMode.DEDUCTIVE);
	}

	public DomAutoCompleteInput(IDomAutoCompleteInputEngine<T> inputEngine, DomAutoCompleteInputValidationMode validationMode) {

		this.inputConstraintRefreshCallbacks = new ArrayList<>();
		this.inputEngine = inputEngine;
		this.validationMode = validationMode;
		this.valueAndStateCache = new DomAutoCompleteValueAndStateCache<>(this);
		this.inputBar = new DomBar();
		this.inputField = new DomAutoCompleteInputField(this);
		this.filterDisplay = new DomAutoCompleteInputFilterDisplay(inputEngine);
		this.indicator = new DomAutoCompleteIndicator<>(valueAndStateCache::getValueAndState);
		this.backdrop = new DomAutoCompleteBackdrop(this);
		this.popup = new DomAutoCompletePopup<>(this);
		this.committedValue = null;

		addCssClass(DomCssClasses.DOM_AUTO_COMPLETE_INPUT);
		appendChild(inputBar);
		appendChild(filterDisplay);
		appendChild(indicator);
		inputBar.appendChild(inputField);
	}

	/**
	 * Returns the {@link IDomAutoCompleteInputEngine} given to the constructor.
	 *
	 * @return the {@link IDomAutoCompleteInputEngine} (never <i>null</i>)
	 */
	public IDomAutoCompleteInputEngine<T> getInputEngine() {

		return inputEngine;
	}

	// TODO make final
	public void refreshInputConstraints() {

		inputEngine.refresh();
		inputEngine.reloadCache();
		valueAndStateCache.clear();
		indicator.refresh();
		filterDisplay.refresh();

		inputConstraintRefreshCallbacks.forEach(INullaryVoidFunction::apply);
	}

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

	// ------------------------------ value methods ------------------------------ //

	@Override
	public Optional<T> getValue() {

		var valueAndState = valueAndStateCache.getValueAndState();
		if (valueAndState.isAmbiguousOrIllegal()) {
			throw new DomInputException(DomI18n.PLEASE_SELECT_A_VALID_ENTRY);
		} else {
			return Optional.ofNullable(valueAndState.getValue());
		}
	}

	@Override
	public void setValue(T value) {

		setFieldValue(value);
		this.committedValue = value;
		refreshIndicator();
	}

	// ------------------------------ input field methods ------------------------------ //

	/**
	 * Returns underlying {@link IDomTextualInput} used for textual input.
	 *
	 * @return the {@link IDomTextualInput} (never <i>null</i>)
	 */
	public IDomTextualInput getInputField() {

		return inputField;
	}

	public String getValueText() {

		return inputField.getValueText();
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
			closePopup();
		}
	}

	protected void onSelection(T value) {

		setFieldValue(value);
		closePopup();
		onChange();
	}

	protected void onBlur() {

		closePopup();
		deduceValue();
		onChange();
	}

	protected void onBackdropClick() {

		closePopupAndDeduceValueAndHandleChange();
	}

	protected void onEscape() {

		boolean popupWasAppended = popup.isAppended();
		closePopupAndDeduceValueAndHandleChange();
		if (!popupWasAppended) {
			sendEscKeyToParent();
		}
	}

	protected void onClickOrFocusByTab() {

		if (!popup.isAppended()) {
			refreshPopup();
		}
	}

	// ------------------------------ protected getters ------------------------------ //

	protected String getPattern() {

		return inputField.getValueTextTrimmed().toLowerCase();
	}

	protected DomAutoCompleteInputValidationMode getValidationMode() {

		return validationMode;
	}

	protected DomBar getInputBar() {

		return inputBar;
	}

	// ------------------------------ internal ------------------------------ //

	@Override
	protected void doSetDisabled(boolean disabled) {

		inputField.setDisabled(disabled);
	}

	private void refreshIndicator() {

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

	private void deduceValue() {

		var valueAndState = valueAndStateCache.getValueAndState();
		if (valueAndState.isValid()) {
			inputField.setValue(inputEngine.getDisplayString(valueAndState.getValue()).toString());
		}
	}

	private void closePopupAndDeduceValueAndHandleChange() {

		closePopup();
		deduceValue();
		onChange();
		inputField.focus();
	}

	private void sendEscKeyToParent() {

		Optional.ofNullable(getParent()).ifPresent(parent -> {
			getDomEngine().sendKeyboardEvent(parent, "keydown", "Escape");
			getDomEngine().sendKeyboardEvent(parent, "keyup", "Escape");
		});
	}

	private void setFieldValue(T value) {

		String valueString = Optional//
			.ofNullable(value)
			.map(inputEngine::getDisplayString)
			.map(IDisplayString::toString)
			.orElse("");
		inputField.setValue(valueString);
	}
}
