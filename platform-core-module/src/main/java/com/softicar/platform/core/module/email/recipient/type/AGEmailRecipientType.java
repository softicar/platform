package com.softicar.platform.core.module.email.recipient.type;

import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.common.core.exceptions.SofticarUnknownEnumConstantException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreI18n;
import javax.mail.Message.RecipientType;

public class AGEmailRecipientType extends AGEmailRecipientTypeGenerated implements IEntity {

	@Override
	public IDisplayString toDisplayWithoutId() {

		switch (getEnum()) {
		case BCC:
			return CoreI18n.BCC;
		case CC:
			return CoreI18n.CC;
		case TO:
			return CoreI18n.TO;
		}
		throw new SofticarUnknownEnumConstantException(getEnum());
	}

	@Override
	public IDisplayString toDisplay() {

		return toDisplayWithoutId();
	}

	public RecipientType getJavaRecipientType() {

		switch (getEnum()) {
		case BCC:
			return RecipientType.BCC;
		case CC:
			return RecipientType.CC;
		case TO:
			return RecipientType.TO;
		default:
			throw new SofticarUnknownEnumConstantException(getEnum());
		}
	}

	/**
	 * Merges two recipient types, preferring TO over CC, and CC over BCC.
	 *
	 * @param a
	 *            the first type
	 * @param b
	 *            the second type
	 * @return the more <i>important</i> type (never null)
	 */
	public static AGEmailRecipientTypeEnum merge(AGEmailRecipientTypeEnum a, AGEmailRecipientTypeEnum b) {

		return a.ordinal() < b.ordinal()? a : b;
	}
}
