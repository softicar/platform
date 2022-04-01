package com.softicar.platform.emf.log.viewer;

import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.string.formatting.StackTraceFormatting;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.elements.DomColorEnum;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.styles.CssFontWeight;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.function.Supplier;

public class EmfLogDisplayFactoryWrapper {

	public <R extends IEmfTableRow<R, ?>> IDomElement createDisplay(Supplier<IDomElement> displaySupplier) {

		try {
			return displaySupplier.get();
		} catch (Exception e) {
			Log.ferror("Suppressed Exception: %s", StackTraceFormatting.getStackTraceAsString(e));
			return new ExceptionDisplayElement();
		}
	}

	private class ExceptionDisplayElement extends DomDiv {

		public ExceptionDisplayElement() {

			appendText(EmfI18n.ERROR);
			setTitle(EmfI18n.AN_ERROR_OCCURRED_WHILE_DISPLAYING_THIS_VALUE);
			setStyle(CssFontWeight.BOLD);
			setColor(DomColorEnum.RED);
		}
	}
}
