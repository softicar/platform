package com.softicar.platform.dom.elements.select.value;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.common.core.item.IBasicItem;
import com.softicar.platform.dom.elements.AbstractDomValueSelect;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Default implementation of {@link AbstractDomValueSelect}.
 * <p>
 * This class implements the abstract methods {@link #getValueId} and
 * {@link #getValueDisplayString} using {@link IBasicItem#getId()} and
 * {@link IDisplayable#toDisplay()} if possible.
 *
 * @author Oliver Richers
 */
public class DomValueSelect<T> extends AbstractDomValueSelect<T> {

	private final Map<T, Integer> idMap = new WeakHashMap<>();

	/**
	 * Returns the ID of the given value.
	 * <p>
	 * If the given value implements {@link IBasicItem}, this method returns
	 * {@link IBasicItem#getId()}. Otherwise an ID is generated using
	 * {@link WeakHashMap}.
	 *
	 * @return the ID of the given value (never null)
	 */
	@Override
	protected Integer getValueId(T value) {

		if (value instanceof IBasicItem) {
			return ((IBasicItem) value).getId();
		} else {
			Integer id = idMap.get(value);
			if (id == null) {
				id = idMap.size();
				idMap.put(value, id);
			}
			return id;
		}
	}

	/**
	 * Returns the {@link IDisplayString} of the given value.
	 * <p>
	 * If the given value implements {@link IDisplayable}, this method returns
	 * {@link IDisplayable#toDisplay()}. Otherwise {@link Object#toString()} is
	 * used.
	 *
	 * @return the {@link IDisplayString} of the given value (never null)
	 */
	@Override
	protected IDisplayString getValueDisplayString(T value) {

		if (value instanceof IDisplayable) {
			return ((IDisplayable) value).toDisplay();
		} else {
			return IDisplayString.create("" + value);
		}
	}
}
