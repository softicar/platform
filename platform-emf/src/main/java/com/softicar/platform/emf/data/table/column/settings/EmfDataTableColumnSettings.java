package com.softicar.platform.emf.data.table.column.settings;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.dom.styles.CssTextAlign;
import java.util.ArrayList;
import java.util.Collection;

public class EmfDataTableColumnSettings implements IEmfDataTableColumnSettings {

	private boolean concealed;
	private boolean hidden;
	private boolean verticalHeader;
	private CssTextAlign alignment;
	private boolean showIds;
	private IDisplayString titleOverride;
	private Collection<IStaticObject> markers;

	public EmfDataTableColumnSettings() {

		this.concealed = false;
		this.hidden = false;
		this.verticalHeader = false;
		this.alignment = null;
		this.showIds = true;
		this.titleOverride = null;
		this.markers = new ArrayList<>();
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
		this.markers = new ArrayList<>(other.getMarkers());
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

	@Override
	public Collection<IStaticObject> getMarkers() {

		return markers;
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

	public void addMarker(IStaticObject marker) {

		this.markers.add(marker);
	}
}
