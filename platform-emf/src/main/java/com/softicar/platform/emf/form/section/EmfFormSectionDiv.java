package com.softicar.platform.emf.form.section;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.exception.IDomExceptionDisplayElement;
import com.softicar.platform.dom.parent.IDomParentElement;
import com.softicar.platform.emf.EmfCssClasses;
import com.softicar.platform.emf.concurrency.EmfOutdatedTableRowException;
import com.softicar.platform.emf.form.IEmfFormBody;
import com.softicar.platform.emf.form.refresh.EmfFormInteractiveRefreshSpan;
import com.softicar.platform.emf.form.section.exception.EmfFormExceptionDiv;
import com.softicar.platform.emf.form.section.header.EmfFormSectionHeaderDiv;
import com.softicar.platform.emf.form.section.header.IEmfFormSectionHeader;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * TODO rework CTORs
 */
public class EmfFormSectionDiv<R extends IEmfTableRow<R, ?>> extends DomDiv implements IEmfFormSectionDiv<R>, IDomExceptionDisplayElement {

	private final IEmfFormBody<R> formBody;
	private final IEmfFormSectionHeader sectionHeader;
	private final Consumer<IEmfFormSectionDiv<R>> callbackAfterOpened;
	private final EmfFormSectionHeaderDiv headerDiv;
	private final DomDiv contentDiv;
	private final List<Supplier<IDomElement>> elementSuppliers;
	private Consumer<EmfFormSectionHeaderDiv> headerModifier;
	private boolean invisible = false;
	private boolean open;

	public EmfFormSectionDiv(IEmfFormBody<R> formBody, IEmfFormSectionHeader sectionHeader) {

		this(formBody, sectionHeader, null, null);
	}

	public EmfFormSectionDiv(IEmfFormBody<R> formBody, IEmfFormSectionHeader sectionHeader, Consumer<IEmfFormSectionDiv<R>> callbackAfterOpened,
			Consumer<EmfFormSectionHeaderDiv> headerModifier) {

		this.formBody = formBody;
		this.sectionHeader = sectionHeader;
		this.callbackAfterOpened = callbackAfterOpened;
		this.headerModifier = headerModifier;
		this.open = false;
		this.headerDiv = appendChild(new EmfFormSectionHeaderDiv()).setClickCallback(this::toggleOpen);
		this.contentDiv = appendChild(new DomDiv());
		this.elementSuppliers = new ArrayList<>();
		setCssClass(EmfCssClasses.EMF_FORM_SECTION_DIV);
		refreshHeaderAndContent();
	}

	@Override
	public IEmfFormBody<R> getFormBody() {

		return formBody;
	}

	@Override
	public void clearElements() {

		this.elementSuppliers.clear();
	}

	@Override
	public void addElement(Supplier<IDomElement> elementSupplier) {

		this.elementSuppliers.add(elementSupplier);
	}

	@Override
	public void setElements(Collection<Supplier<IDomElement>> elementSuppliers) {

		clearElements();
		this.elementSuppliers.addAll(elementSuppliers);
	}

	@Override
	public void setInvisibleMode() {

		invisible = true;
		headerDiv.setInvisibleMode();
	}

	@Override
	public void display(Exception exception) {

		IDomParentElement container = createContainer();
		if (exception instanceof EmfOutdatedTableRowException) {
			container.appendChild(new EmfFormInteractiveRefreshSpan<>(formBody, true, () -> container.getParent().replaceChild(contentDiv, container)));
		} else {
			container.appendChild(new EmfFormExceptionDiv(exception, () -> container.getParent().replaceChild(contentDiv, container)));
		}
		replaceContentWith(container);

		// need to discard refresh event (see #35867)
		getDomDocument().getRefreshBus().discardEvent();
	}

	public void toggleOpen() {

		setOpen(!isOpen());
	}

	public boolean isOpen() {

		return open;
	}

	public void setOpen(boolean open) {

		if (open != isOpen()) {
			if (open) {
				if (callbackAfterOpened != null) {
					callbackAfterOpened.accept(this);
				}
			}
			this.open = open;
			refreshHeaderAndContent();
		}
	}

	public boolean showSection() {

		return elementSuppliers.size() > 0 || sectionHeader.getPlaceholder().isPresent();
	}

	protected IDisplayString getTitle(IEmfFormSectionHeader sectionHeader, R tableRow) {

		DevNull.swallow(tableRow);
		return sectionHeader.getDisplayString();
	}

	protected void refreshHeaderAndContent() {

		refreshHeader();
		refreshContent();
	}

	protected void refreshHeader() {

		sectionHeader.getIcon().ifPresent(headerDiv::setIcon);
		headerDiv.setSectionTitle(getTitle(sectionHeader, formBody.getTableRow()));
		headerDiv.setOpen(open);
		if (headerModifier != null) {
			headerModifier.accept(headerDiv);
		}
	}

	protected void refreshContent() {

		contentDiv.removeChildren();
		if (open) {
			IDomParentElement container = contentDiv.appendChild(createContainer());
			if (!elementSuppliers.isEmpty()) {
				elementSuppliers.stream().map(Supplier::get).forEach(container::appendChild);
			} else {
				sectionHeader.getPlaceholder().ifPresent(supplier -> container.appendChild(new NoContentSpan(supplier.get())));
			}
		}
	}

	private void replaceContentWith(IDomParentElement container) {

		contentDiv.getParent().replaceChild(container, contentDiv);
	}

	private IDomParentElement createContainer() {

		DomDiv container = new DomDiv();
		container.setCssClass(EmfCssClasses.EMF_FORM_SECTION_CONTENT_DIV);
		if (invisible) {
			container.addCssClass(EmfCssClasses.INVISIBLE);
		}
		return container;
	}

	private class NoContentSpan extends DomDiv {

		public NoContentSpan(IDisplayString message) {

			setCssClass(EmfCssClasses.EMF_FORM_SECTION_NO_CONTENT);
			appendText(message.encloseInParentheses());
		}
	}
}
