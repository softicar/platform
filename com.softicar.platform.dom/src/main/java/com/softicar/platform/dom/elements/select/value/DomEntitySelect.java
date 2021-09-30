package com.softicar.platform.dom.elements.select.value;

import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.elements.AbstractDomValueSelect;
import java.util.Collection;

/**
 * An implementation of {@link AbstractDomValueSelect} for {@link IEntity}.
 *
 * @author Oliver Richers
 */
public class DomEntitySelect<T extends IEntity> extends AbstractDomValueSelect<T> {

	public DomEntitySelect() {

		// no items added by default
	}

	public DomEntitySelect(Collection<T> values) {

		addNilValue(DomI18n.PLEASE_SELECT.encloseInBrackets());
		addValues(values);
	}

	@Override
	protected Integer getValueId(T value) {

		return value.getId();
	}

	@Override
	protected IDisplayString getValueDisplayString(T value) {

		return value.toDisplay();
	}
}
