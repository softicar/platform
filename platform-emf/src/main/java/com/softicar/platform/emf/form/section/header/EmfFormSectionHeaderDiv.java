package com.softicar.platform.emf.form.section.header;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.dom.DomCssPseudoClasses;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomImage;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.IDomClickEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.EmfCssClasses;

public class EmfFormSectionHeaderDiv extends DomDiv implements IDomClickEventHandler {

	private final OpenIndicator openIndicator;
	private final DomDiv iconHolder;
	private final DomDiv titleHolder;
	private final DomDiv contentHolder;
	private INullaryVoidFunction callback;

	public EmfFormSectionHeaderDiv() {

		this.openIndicator = appendChild(new OpenIndicator());
		this.iconHolder = appendChild(new DomDiv());
		this.titleHolder = appendChild(new DomDiv());
		this.contentHolder = appendChild(new DomDiv());
		this.callback = INullaryVoidFunction.NO_OPERATION;
		setCssClass(EmfCssClasses.EMF_FORM_SECTION_HEADER_DIV);
		iconHolder.setCssClass(EmfCssClasses.EMF_FORM_SECTION_HEADER_ICON);
		titleHolder.setCssClass(EmfCssClasses.EMF_FORM_SECTION_HEADER_TITLE);
		contentHolder.setCssClass(EmfCssClasses.EMF_FORM_SECTION_HEADER_CONTENT);
		setClickable(true);
	}

	@Override
	public void handleClick(IDomEvent event) {

		callback.apply();
	}

	public void setInvisibleMode() {

		addCssClass(EmfCssClasses.INVISIBLE);
	}

	public EmfFormSectionHeaderDiv setOpen(boolean open) {

		openIndicator.setOpen(open);
		return this;
	}

	public EmfFormSectionHeaderDiv setIcon(IResource icon) {

		iconHolder.removeChildren();
		iconHolder.appendChild(new DomImage(icon));
		return this;
	}

	public EmfFormSectionHeaderDiv setSectionTitle(IDisplayString title) {

		titleHolder.removeChildren();
		titleHolder.appendText(title.toString());
		return this;
	}

	public EmfFormSectionHeaderDiv setContent(IDomNode node) {

		contentHolder.removeChildren();
		contentHolder.appendChild(node);
		return this;
	}

	public EmfFormSectionHeaderDiv setClickCallback(INullaryVoidFunction callback) {

		this.callback = callback;
		return this;
	}

	public EmfFormSectionHeaderDiv setClickable(boolean enabled) {

		if (enabled) {
			removeCssClass(DomCssPseudoClasses.DISABLED);
			listenToEvent(DomEventType.CLICK);
		} else {
			addCssClass(DomCssPseudoClasses.DISABLED);
			unlistenToEvent(DomEventType.CLICK);
		}
		openIndicator.setVisible(enabled);
		return this;
	}

	private static class OpenIndicator extends DomDiv {

		private boolean open = false;

		public OpenIndicator() {

			setCssClass(EmfCssClasses.EMF_FORM_SECTION_HEADER_OPEN_INDICATOR);
			setOpen(open);
		}

		public void setOpen(boolean open) {

			this.open = open;
			if (open) {
				addCssClass(EmfCssClasses.OPEN);
			} else {
				removeCssClass(EmfCssClasses.OPEN);
			}
		}

		public void setVisible(boolean visible) {

			if (visible) {
				removeCssClass(EmfCssClasses.INVISIBLE);
			} else {
				addCssClass(EmfCssClasses.INVISIBLE);
			}
			setOpen(open);
		}
	}
}
