package com.softicar.platform.emf.data.table.column.settings;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.styles.CssTextAlign;

public interface IEmfDataTableColumnSettings {

	void setAll(IEmfDataTableColumnSettings other);

	boolean isConcealed();

	boolean isHidden();

	boolean isVerticalHeader();

	CssTextAlign getAlignmentOrDefault(CssTextAlign defaultAlignment);

	boolean isShowIds();

	IDisplayString getTitleOverride();
}
