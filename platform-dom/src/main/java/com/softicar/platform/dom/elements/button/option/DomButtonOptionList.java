package com.softicar.platform.dom.elements.button.option;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.Consumers;
import com.softicar.platform.common.core.interfaces.ITestMarker;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.dom.DomCssPseudoClasses;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Implements a list of alternative options (also known as radio button group)
 * using {@link DomButton}.
 *
 * @author Oliver Richers
 */
public class DomButtonOptionList<T> extends DomActionBar {

	private final Map<T, DomButton> optionButtonMap;
	private boolean nullable;
	private Consumer<T> selectCallback;
	private Optional<T> selectedOption;
	private Optional<DomButton> selectedButton;

	public DomButtonOptionList() {

		this.nullable = false;
		this.optionButtonMap = new HashMap<>();
		this.selectCallback = Consumers.noOperation();
		this.selectedOption = Optional.empty();
		this.selectedButton = Optional.empty();
	}

	public DomButtonOptionList<T> setNullable(boolean nullable) {

		this.nullable = nullable;
		return this;
	}

	public boolean isNullable() {

		return nullable;
	}

	// -------------------- option -------------------- //

	public DomButtonOptionList<T> addOption(T option, IResource icon, IDisplayString label) {

		DomButton button = new DomButton()//
			.setClickCallback(() -> handleClick(option))
			.setIcon(icon)
			.setLabel(label);
		appendChild(button);
		optionButtonMap.put(option, button);
		return this;
	}

	public DomButtonOptionList<T> setOptionMarker(T option, ITestMarker marker) {

		optionButtonMap.get(option).addMarker(marker);
		return this;
	}

	// -------------------- callback -------------------- //

	public DomButtonOptionList<T> setSelectCallback(Consumer<T> selectCallback) {

		this.selectCallback = selectCallback;
		return this;
	}

	// -------------------- selection -------------------- //

	public DomButtonOptionList<T> setSelectedOption(T option) {

		selectedButton.ifPresent(button -> unmarkAsSelected(button));

		if (option != null) {
			DomButton optionButton = optionButtonMap.get(option);
			if (optionButton != null) {
				markAsSelected(optionButton);
				selectedOption = Optional.of(option);
				selectedButton = Optional.of(optionButton);
			} else {
				throw new SofticarUserException(DomI18n.TRIED_TO_SELECT_AN_OPTION_THAT_WAS_NOT_CONTAINED_IN_THE_LIST_OF_AVAILABLE_OPTIONS);
			}
		} else {
			selectedOption = Optional.empty();
			selectedButton = Optional.empty();
		}
		return this;
	}

	public Optional<T> getSelectedOption() {

		return selectedOption;
	}

	// -------------------- style-------------------- //

	public static void markAsSelected(DomButton button) {

		button.addCssClass(DomCssPseudoClasses.SELECTED);
	}

	public static void unmarkAsSelected(DomButton button) {

		button.removeCssClass(DomCssPseudoClasses.SELECTED);
	}

	// -------------------- private-------------------- //

	private void handleClick(T option) {

		if (nullable && getSelectedOption().equals(Optional.ofNullable(option))) {
			setSelectedOption(null);
		} else {
			setSelectedOption(option);
		}
		selectCallback.accept(option);
	}
}
