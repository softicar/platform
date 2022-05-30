package com.softicar.platform.dom.input;

import com.softicar.platform.common.container.map.MapFactory;
import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.engine.IDomEngine;
import com.softicar.platform.dom.node.DomNodes;
import com.softicar.platform.dom.parent.DomParentElement;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Basic implementation of an Html select.
 * <p>
 * Note, this class should better be abstract.
 *
 * @author Oliver Richers
 */
public class DomSelect<O extends DomOption> extends DomParentElement implements IDomInput, IDomFocusable {

	private final Map<Integer, O> selectedOptions = MapFactory.createTreeMap();

	@Override
	public final DomElementTag getTag() {

		return DomElementTag.SELECT;
	}

	@Override
	public DomSelect<O> setDisabled(boolean disabled) {

		return DomNodes.setDisabled(this, disabled);
	}

	/**
	 * @deprecated use {@link #setDisabled(boolean)} instead
	 */
	@Deprecated
	public final DomSelect<O> setEnabled(boolean enabled) {

		return setDisabled(!enabled);
	}

	@Override
	public boolean isDisabled() {

		return DomNodes.isDisabled(this);
	}

	/**
	 * @deprecated use {@link #isDisabled()} instead
	 */
	@Deprecated
	public final boolean isEnabled() {

		return !isDisabled();
	}

	public final void setMultiple(boolean multiple) {

		setAttribute("multiple", multiple);
	}

	public final boolean isMultiple() {

		return getAttributeValue("multiple")//
			.map(value -> value.equals("true"))
			.orElse(false);
	}

	/**
	 * Returns the currently selected option object.
	 * <p>
	 * If more than one option is selected, this throws an exception.
	 *
	 * @return selected option object or null if none selected
	 */
	public final O getSelectedOption() {

		if (selectedOptions.isEmpty() && !isMultiple() && getChildCount() > 0) {
			return getOption(0);
		}
		if (selectedOptions.size() == 1) {
			return selectedOptions.values().iterator().next();
		} else if (selectedOptions.size() > 1) {
			throw new SofticarUserException(DomI18n.MULTIPLE_OPTIONS_WERE_SELECTED.concatSentence(DomI18n.ONLY_ONE_OPTION_MAY_BE_SELECTED));
		} else {
			return null;
		}
	}

	/**
	 * Returns all selected option objects.
	 *
	 * @return collection of selected options
	 */
	public final Collection<O> getSelectedOptions() {

		return selectedOptions.values();
	}

	/**
	 * Selects the specified option and un-selects all other options.
	 *
	 * @param option
	 *            the option to be selected
	 */
	public final void setSelectedOption(O option) {

		if (option == null) {
			throw new SofticarUserException(DomI18n.AN_NONEXISTENT_OPTION_WAS_SELECTED);
		}

		setSelectedOptions(Collections.singletonList(option));
	}

	/**
	 * Selects the specified options and un-selects all other options.
	 *
	 * @param selectedOptions
	 *            a collection with all option to be selected
	 */
	public final void setSelectedOptions(Collection<O> selectedOptions) {

		// reset selected options map
		this.selectedOptions.clear();
		for (O option: selectedOptions.stream().filter(Objects::nonNull).collect(Collectors.toList())) {
			this.selectedOptions.put(option.getNodeId(), option);
		}
		getDomEngine().setSelectedOptions(this, this.selectedOptions.values());
	}

	/**
	 * This is called by an {@link IDomEngine} after a button that was bound to
	 * a form (containing this select) was pressed.
	 *
	 * @param selectedOptions
	 *            DOM IDs of the selected option nodes
	 */
	@SuppressWarnings("unchecked")
	public final void setSelectedOptions_noJS(String selectedOptions) {

		// reset selected options map
		this.selectedOptions.clear();
		if (!selectedOptions.equals("")) {
			for (String optionId: selectedOptions.split(",")) {
				O option = (O) getDomDocument().getNode(optionId);
				if (option == null) {
					// Actually, it should not be possible that the selected option cannot be found,
					// but i42633 and i48744 prove that it can.
					throw new SofticarUserException(
						DomI18n.THE_SELECTED_OPTION_ARG1_VANISHED//
							.toDisplay(optionId)
							.concat(" ")
							.concat(DomI18n.PLEASE_TRY_AGAIN));
				}
				this.selectedOptions.put(option.getNodeId(), option);
			}
		}
	}

	@SuppressWarnings("unchecked")
	protected final O getOption(int index) {

		return (O) getChild(index);
	}
}
