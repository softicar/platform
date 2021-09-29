package com.softicar.platform.emf.data.table.column.settings;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.styles.CssTextAlign;

public class EmfDataTableColumnSettings implements IEmfDataTableColumnSettings {

	private boolean concealed;
	private boolean hidden;
	private boolean verticalHeader;
	private CssTextAlign alignment;
	private boolean showIds;
	private IDisplayString titleOverride;

	public EmfDataTableColumnSettings() {

		this.concealed = false;
		this.hidden = false;
		this.verticalHeader = false;
		this.alignment = null;
		this.showIds = true;
		this.titleOverride = null;
	}

	public EmfDataTableColumnSettings(IEmfDataTableColumnSettings other) {

		setAll(other);
	}

	@Override
	public void setAll(IEmfDataTableColumnSettings other) {

		this.concealed = other.isConcealed();
		this.hidden = other.isHidden();
		this.verticalHeader = other.isVerticalHeader();
		this.alignment = other.getAlignmentOrDefault(null);
		this.showIds = other.isShowIds();
		this.titleOverride = other.getTitleOverride();
	}

	@Override
	public boolean isConcealed() {

		return concealed;
	}

	@Override
	public boolean isHidden() {

		return hidden;
	}

	@Override
	public boolean isVerticalHeader() {

		return verticalHeader;
	}

	@Override
	public CssTextAlign getAlignmentOrDefault(CssTextAlign defaultAlignment) {

		return alignment != null? alignment : defaultAlignment;
	}

	@Override
	public boolean isShowIds() {

		return showIds;
	}

	@Override
	public IDisplayString getTitleOverride() {

		return titleOverride;
	}

	public void setConcealed(boolean concealed) {

		this.concealed = concealed;
	}

	public void setHidden(boolean hidden) {

		this.hidden = hidden;
	}

	public void setVerticalHeader(boolean verticalHeader) {

		this.verticalHeader = verticalHeader;
	}

	public void toggleHidden() {

		setHidden(!isHidden());
	}

	public void setAlignment(CssTextAlign alignment) {

		this.alignment = alignment;
	}

	public void setShowIds(boolean showIds) {

		this.showIds = showIds;
	}

	public void setTitleOverride(IDisplayString titleOverride) {

		this.titleOverride = titleOverride;
	}
}
