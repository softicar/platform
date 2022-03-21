package com.softicar.platform.emf.trait;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.message.DomMessageDiv;
import com.softicar.platform.dom.elements.message.style.DomMessageType;
import com.softicar.platform.dom.elements.tab.DomTab;
import com.softicar.platform.dom.input.IDomTextualInput;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.form.EmfForm;
import com.softicar.platform.emf.form.IEmfForm;
import com.softicar.platform.emf.form.IEmfFormFrame;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.trait.table.IEmfTraitTable;

public class EmfTraitTab<T extends IEmfTrait<T, R>, R extends IEmfTableRow<R, ?>> extends DomTab implements IEmfFormFrame<T> {

	private final IEmfForm<R> parentForm;
	private final IEmfTraitTable<T, R> traitTable;
	private EmfForm<T> traitForm;

	public EmfTraitTab(IEmfForm<R> parentForm, IEmfTraitTable<T, R> traitTable) {

		super(traitTable.getTitle());

		this.parentForm = parentForm;
		this.traitTable = traitTable;
		this.traitForm = null;

		setOnShowRefreshable(this::onShow);
	}

	@Override
	public void setTitle(IDisplayString title, IDisplayString subTitle) {

		// nothing to do
	}

	@Override
	public void closeFrame() {

		parentForm.getFrame().closeFrame();
	}

	@Override
	public void focusFrame() {

		IDomTextualInput.focusFirstTextualInput(this);
	}

	private void onShow() {

		if (traitForm != null) {
			traitForm.peekAndRefresh();
		} else {
			T trait = traitTable.get(parentForm.getTableRow());
			if (trait != null) {
				appendTraitForm(trait);
			} else {
				showUnconfiguredTraitMessage();
			}
		}
	}

	private void showUnconfiguredTraitMessage() {

		removeChildren();
		appendChild(new DomMessageDiv(DomMessageType.INFO, EmfI18n.THIS_TRAIT_WAS_NOT_CONFIGURED_YET));
		appendChild(new DomActionBar())
			.appendChild(
				new DomButton()//
					.setIcon(EmfImages.ENTITY_TRAIT_CONFIGURE.getResource())
					.setLabel(EmfI18n.CONFIGURE_TRAIT)
					.setClickCallback(this::createTraitAndShowForm));
	}

	private void createTraitAndShowForm() {

		removeChildren();
		appendTraitForm(traitTable.getOrCreate(parentForm.getTableRow()));
	}

	private void appendTraitForm(T trait) {

		this.traitForm = new EmfForm<>(this, trait);
		appendChild(traitForm);
		traitForm.peekAndRefresh();
	}
}
