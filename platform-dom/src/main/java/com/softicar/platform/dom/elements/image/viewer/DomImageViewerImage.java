package com.softicar.platform.dom.elements.image.viewer;

import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.elements.DomImage;
import com.softicar.platform.dom.event.IDomClickEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.style.ICssLength;
import com.softicar.platform.dom.styles.CssCursor;

public class DomImageViewerImage extends DomImage implements IDomClickEventHandler {

	private final ICssLength maxWidth;
	private boolean limitWidth;

	public DomImageViewerImage(IResource resource, ICssLength maxWidth) {

		super(resource);
		this.maxWidth = maxWidth;
		setLimitWidth(true);
		addCssClass(DomCssClasses.DOM_IMAGE_VIEWER_IMAGE);
		refresh();
	}

	public void refresh() {

		if (isLimitWidth()) {
			setStyle(CssStyle.MAX_WIDTH, maxWidth);
			setStyle(CssCursor.ZOOM_IN);
		} else {
			unsetStyle(CssStyle.MAX_WIDTH);
			setStyle(CssCursor.ZOOM_OUT);
		}
	}

	@Override
	public void handleClick(IDomEvent event) {

		setLimitWidth(!isLimitWidth());
		refresh();
	}

	public boolean isLimitWidth() {

		return limitWidth;
	}

	public void setLimitWidth(boolean limitWidth) {

		this.limitWidth = limitWidth;
	}
}
