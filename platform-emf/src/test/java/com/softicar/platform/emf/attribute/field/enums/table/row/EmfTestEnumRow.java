package com.softicar.platform.emf.attribute.field.enums.table.row;

import com.softicar.platform.common.core.exceptions.SofticarUnknownEnumConstantException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.emf.EmfI18n;

public class EmfTestEnumRow extends EmfTestEnumRowGenerated implements IDisplayable {

	@Override
	public IDisplayString toDisplay() {

		switch (getEnum()) {
		case ONE:
			return EmfI18n.ONE;
		case TWO:
			return EmfI18n.TWO;
		case THREE:
			return EmfI18n.THREE;
		case FOUR:
			return EmfI18n.FOUR;
		case FIVE:
			return EmfI18n.FIVE;
		}
		throw new SofticarUnknownEnumConstantException(getEnum());
	}
}
