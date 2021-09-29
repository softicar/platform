package com.softicar.platform.emf.action;

import com.softicar.platform.common.core.user.CurrentBasicUser;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.action.marker.EmfCommonActionMarker;
import com.softicar.platform.emf.authorization.role.EmfAnyRole;
import com.softicar.platform.emf.authorization.role.IEmfRole;
import com.softicar.platform.emf.form.IEmfFormBody;
import com.softicar.platform.emf.form.section.EmfFormSectionDiv;
import com.softicar.platform.emf.form.section.header.EmfFormSectionHeader;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public abstract class AbstractEmfAggregatingAction<R extends IEmfTableRow<R, ?>> implements IEmfCommonAction<R> {

	private final Collection<IEmfPrimaryAction<R>> actions;

	public AbstractEmfAggregatingAction() {

		this.actions = new ArrayList<>();
	}

	@Override
	public final void handleClick(IEmfFormBody<R> formBody) {

		formBody.showSectionContainer(new ActionDiv<>(this, formBody));
	}

	@Override
	public IEmfPredicate<R> getPrecondition() {

		IEmfPredicate<R> predicate = EmfPredicates.never();
		for (IEmfPrimaryAction<R> action: actions) {
			predicate = predicate.or(action.getPrecondition());
		}
		return predicate;
	}

	@Override
	public IEmfRole<R> getAuthorizedRole() {

		return new EmfAnyRole<>(
			actions//
				.stream()
				.map(action -> action.getAuthorizedRole())
				.collect(Collectors.toList()));
	}

	@Override
	public final boolean isAvailable(R tableRow, IBasicUser user) {

		return actions//
			.stream()
			.anyMatch(action -> action.isAvailable(tableRow, user));
	}

	public Collection<IEmfPrimaryAction<R>> getActions() {

		return actions;
	}

	protected void addAction(IEmfPrimaryAction<R> action) {

		actions.add(action);
	}

	private static class ActionDiv<R extends IEmfTableRow<R, ?>> extends EmfFormSectionDiv<R> {

		private final IEmfFormBody<R> formBody;

		public ActionDiv(AbstractEmfAggregatingAction<R> action, IEmfFormBody<R> formBody) {

			super(formBody, new SectionHeader<>(action));

			this.formBody = formBody;

			integrateActions(action.getActions(), formBody);
			setMarker(new EmfCommonActionMarker(action));

			addElement(CancelDiv::new);

			setOpen(true);
		}

		private void integrateActions(Collection<IEmfPrimaryAction<R>> actions, IEmfFormBody<R> formBody) {

			R tableRow = formBody.getTableRow();
			IBasicUser user = CurrentBasicUser.get();
			for (IEmfPrimaryAction<R> action: actions) {
				if (action.isAvailable(tableRow, user)) {
					action.integrate(tableRow, this);
				}
			}
		}

		private class CancelDiv extends DomDiv {

			public CancelDiv() {

				appendNewChild(DomElementTag.HR);

				appendChild(
					new DomButton()//
						.setIcon(DomElementsImages.DIALOG_CANCEL.getResource())
						.setLabel(EmfI18n.CANCEL)
						.setClickCallback(() -> formBody.showStandardSectionContainer()));
			}
		}
	}

	private static class SectionHeader<R extends IEmfTableRow<R, ?>> extends EmfFormSectionHeader {

		public SectionHeader(AbstractEmfAggregatingAction<R> action) {

			setIcon(action.getIcon()).setCaption(action.getTitle());
		}
	}
}
