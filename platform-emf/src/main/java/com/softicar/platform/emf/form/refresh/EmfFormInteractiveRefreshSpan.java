package com.softicar.platform.emf.form.refresh;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.message.DomMessageDiv;
import com.softicar.platform.dom.elements.message.style.DomMessageType;
import com.softicar.platform.dom.elements.wiki.DomWikiDivBuilder;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.EmfMarker;
import com.softicar.platform.emf.form.IEmfFormBody;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Objects;

public class EmfFormInteractiveRefreshSpan<R extends IEmfTableRow<R, ?>> extends DomDiv {

	private final IEmfFormBody<R> formBody;
	private final boolean cancelSemantics;
	private final INullaryVoidFunction okayCallback;

	/**
	 * @param formBody
	 * @param cancelSemantics
	 *            <i>true</i> if UI elements presented to the user should
	 *            indicate that a current action is cancelled. <i>false</i>
	 *            otherwise (i.e. if there is no current action).
	 */
	public EmfFormInteractiveRefreshSpan(IEmfFormBody<R> formBody, boolean cancelSemantics, INullaryVoidFunction okayCallback) {

		this.formBody = formBody;
		this.cancelSemantics = cancelSemantics;
		this.okayCallback = Objects.requireNonNull(okayCallback);
		appendChild(new DomMessageDiv(DomMessageType.WARNING, new MessageElement()));
	}

	private class MessageElement extends DomDiv {

		public MessageElement() {

			IDisplayString message = EmfI18n.THE_ENTRY_IS_OUTDATED.concat(" ");
			if (cancelSemantics) {
				message = message.concat(EmfI18n.CANCEL_THE_CURRENT_ACTION_AND_RELOAD_THE_ENTRY_QUESTION);
			} else {
				message = message.concat(EmfI18n.RELOAD_THE_ENTRY_QUESTION);
			}
			new DomWikiDivBuilder().addLine(message.enclose("**")).buildAndAppendTo(this);
			appendChild(new DomActionBar(new RefreshButton()));
		}
	}

	private class RefreshButton extends DomButton {

		public RefreshButton() {

			if (cancelSemantics) {
				setLabel(EmfI18n.CANCEL_AND_REFRESH);
			} else {
				setLabel(EmfI18n.REFRESH);
			}
			setIcon(EmfImages.REFRESH.getResource());
			setClickCallback(this::queueEntityForRefreshAndCancelEditMode);
			setMarker(EmfMarker.INTERACTIVE_REFRESH_BUTTON);
		}

		private void queueEntityForRefreshAndCancelEditMode() {

			formBody.refreshAfterConcurrentModification();
			okayCallback.apply();
		}
	}
}
