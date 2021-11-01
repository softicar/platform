package com.softicar.platform.db.runtime.field;

import com.softicar.platform.common.code.title.TitleFromIdentifierDeterminer;
import com.softicar.platform.common.core.i18n.IDisplayString;

/**
 * Utility methods for {@link IDbField} implementations.
 *
 * @author Oliver Richers
 */
public class DbFields {

	/**
	 * Returns a fallback title for the given {@link IDbField}.
	 * <p>
	 * The title is derived form the {@link IDbField#getName()} using
	 * {@link TitleFromIdentifierDeterminer}.
	 *
	 * @param field
	 *            the field (never null)
	 * @return the title (never null)
	 */
	public static IDisplayString getFallbackTitle(IDbField<?, ?> field) {

		return IDisplayString.create(new TitleFromIdentifierDeterminer(field.getName()).getTitle());
	}
}
