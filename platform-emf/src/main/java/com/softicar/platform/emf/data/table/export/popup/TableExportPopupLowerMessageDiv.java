package com.softicar.platform.emf.data.table.export.popup;

import com.softicar.platform.common.core.i18n.DisplayString;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.string.unicode.UnicodeEnum;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomTable;
import com.softicar.platform.dom.elements.message.DomMessageDiv;
import com.softicar.platform.dom.elements.message.style.DomMessageType;
import com.softicar.platform.emf.data.table.export.engine.factory.ITableExportEngineFactory;
import com.softicar.platform.emf.data.table.export.model.TableExportTableModel;
import com.softicar.platform.emf.data.table.export.precondition.TableExportPreconditionResult;
import com.softicar.platform.emf.data.table.export.precondition.TableExportPreconditionResultContainer;
import com.softicar.platform.emf.data.table.export.precondition.TableExportPreconditionResult.Level;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Displayed at the bottom of {@link TableExportPopup} to show hints, warnings
 * and errors to the user, depending on the table and the currently selected
 * engine factory.
 *
 * @author Alexander Schmidt
 */
public class TableExportPopupLowerMessageDiv extends DomDiv {

	public void refreshForSelectedFactory(DomTable table, Optional<ITableExportEngineFactory<?>> factory) {

		removeChildren();

		if (table != null) {
			if (factory.isPresent()) {
				TableExportPreconditionResultContainer preconditions =
						factory.get().checkPreconditions(Collections.singleton(new TableExportTableModel(table, null)));

				if (preconditions != null) {
					DomMessageDiv errorDiv = createPreconditionMessageDiv(
						preconditions.getAllByLevel(Level.ERROR),
						DomI18n.THE_TABLE_CANNOT_BE_EXPORTED_DUE_TO_THE_FOLLOWING_REASON,
						DomI18n.THE_TABLE_CANNOT_BE_EXPORTED_DUE_TO_THE_FOLLOWING_REASONS,
						DomMessageType.ERROR);

					if (errorDiv != null) {
						appendChild(errorDiv);
						appendNewChild(DomElementTag.P);
					}

					DomMessageDiv warningDiv = createPreconditionMessageDiv(
						preconditions.getAllByLevel(Level.WARNING),
						DomI18n.THE_FOLLOWING_PROBLEM_CAN_OCCUR_WHEN_EXPORTING_THE_TABLE,
						DomI18n.THE_FOLLOWING_PROBLEMS_CAN_OCCUR_WHEN_EXPORTING_THE_TABLE,
						DomMessageType.WARNING);

					if (warningDiv != null) {
						appendChild(warningDiv);
						appendNewChild(DomElementTag.P);
					}

					DomMessageDiv infoDiv = createPreconditionMessageDiv(
						preconditions.getAllByLevel(Level.INFO),
						DomI18n.THE_FOLLOWING_SHOULD_BE_CONSIDERED_WHEN_EXPORTING_THE_TABLE,
						DomI18n.THE_FOLLOWING_SHOULD_BE_CONSIDERED_WHEN_EXPORTING_THE_TABLE,
						DomMessageType.INFO);

					if (infoDiv != null) {
						appendChild(infoDiv);
					}
				}
			}

			else {
				appendChild(new DomMessageDiv(DomMessageType.INFO, DomI18n.PLEASE_SELECT_A_FORMAT));
			}
		}
	}

	private DomMessageDiv createPreconditionMessageDiv(List<TableExportPreconditionResult> preconditionResults, IDisplayString headerSingular,
			IDisplayString headerPlural, DomMessageType messageType) {

		if (!preconditionResults.isEmpty()) {
			DisplayString msg = new DisplayString();

			if (preconditionResults.size() == 1) {
				msg.append(headerSingular);
			} else {
				msg.append(headerPlural);
			}

			msg.append(":\n");

			for (TableExportPreconditionResult result: preconditionResults) {
				msg.append(UnicodeEnum.BULLET.toString() + " " + result.getMessage() + "\n");
			}

			return new DomMessageDiv(messageType, msg);
		}

		else {
			return null;
		}
	}
}
