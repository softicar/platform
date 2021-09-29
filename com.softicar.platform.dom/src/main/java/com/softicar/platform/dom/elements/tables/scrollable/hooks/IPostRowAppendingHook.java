package com.softicar.platform.dom.elements.tables.scrollable.hooks;

import com.softicar.platform.dom.elements.DomRow;
import com.softicar.platform.dom.elements.tables.scrollable.DomScrollableTable;

/**
 * Performs actions on a {@link DomRow} right after that row has been appended
 * to the body of a {@link DomScrollableTable}.
 *
 * @author Alexander Schmidt
 */
public interface IPostRowAppendingHook {

	void handlePostRowAppending(DomRow row);
}
