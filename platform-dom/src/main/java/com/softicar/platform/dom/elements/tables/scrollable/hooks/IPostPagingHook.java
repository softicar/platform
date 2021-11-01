package com.softicar.platform.dom.elements.tables.scrollable.hooks;

import com.softicar.platform.dom.elements.tables.scrollable.DomScrollableTable;

/**
 * Performs actions right after an interval of records is displayed in a
 * {@link DomScrollableTable}.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public interface IPostPagingHook {

	void handlePostPaging();
}
