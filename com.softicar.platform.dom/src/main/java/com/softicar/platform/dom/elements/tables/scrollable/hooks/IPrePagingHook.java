package com.softicar.platform.dom.elements.tables.scrollable.hooks;

import com.softicar.platform.dom.elements.tables.scrollable.DomScrollableTable;

/**
 * Performs actions right before an interval of records is displayed in a
 * {@link DomScrollableTable}.
 *
 * @author Alexander Schmidt
 */
public interface IPrePagingHook {

	void handlePrePaging();
}
