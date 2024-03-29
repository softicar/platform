package com.softicar.platform.emf.entity.table.property;

import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomIcon;
import com.softicar.platform.dom.elements.checkbox.DomCheckbox;
import com.softicar.platform.dom.elements.label.DomLabelGrid;
import com.softicar.platform.dom.elements.tab.DomTab;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.attribute.field.string.EmfStringDisplay;
import com.softicar.platform.emf.authorizer.IEmfAuthorizer;
import com.softicar.platform.emf.entity.table.permission.EmfTablePermissionOverviewDiv;
import com.softicar.platform.emf.entity.table.view.EmfTableViewButton;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.permission.EmfPermissionToDisplayVisitor;
import com.softicar.platform.emf.table.IEmfTable;

public class EmfTablePropertyTab extends DomTab {

	private final IEmfTable<?, ?, ?> table;
	private final IEmfAuthorizer<?, ?> authorizer;

	public EmfTablePropertyTab(IEmfTable<?, ?, ?> table) {

		super(EmfI18n.PROPERTIES);
		this.table = table;
		authorizer = table.getAuthorizer();
		buildInfoDisplay();
	}

	private void buildInfoDisplay() {

		DomLabelGrid detailsDisplay = new DomLabelGrid();
		detailsDisplay.add(EmfI18n.ICON, createTableIconDiv());
		detailsDisplay.add(EmfI18n.VALUE_CLASS, new EmfStringDisplay(table.getValueClass().getSimpleName()));
		detailsDisplay.add(EmfI18n.TITLE, new EmfStringDisplay(table.getTitle()));
		detailsDisplay.add(EmfI18n.PLURAL_TITLE, new EmfStringDisplay(table.getPluralTitle()));
		detailsDisplay.add(EmfI18n.DATABASE_TABLE, new EmfStringDisplay(table.getFullName().getCanonicalName()));
		detailsDisplay.add(EmfI18n.PERMISSION_TO_CREATE, new EmfPermissionToDisplayVisitor<>(authorizer.getCreationPermission()).toDisplay());
		detailsDisplay.add(EmfI18n.PERMISSION_TO_VIEW, new EmfPermissionToDisplayVisitor<>(authorizer.getViewPermission()).toDisplay());
		detailsDisplay.add(EmfI18n.PERMISSION_TO_EDIT, new EmfPermissionToDisplayVisitor<>(authorizer.getEditPermission()).toDisplay());
		detailsDisplay.add(EmfI18n.PERMISSION_TO_DELETE, new EmfPermissionToDisplayVisitor<>(authorizer.getDeletePermission()).toDisplay());
		detailsDisplay.add(EmfI18n.USED_PERMISSIONS, new EmfTablePermissionOverviewDiv(table));
		table.getScopeField().ifPresent(scopeField -> buildScopeTableRow(detailsDisplay, scopeField.getTargetTable()));
		detailsDisplay.add(EmfI18n.CHILD_TABLES, buildChildTablesRow());
		detailsDisplay
			.add(
				EmfI18n.ACTIVE_FIELD_EXISTS,
				new DomCheckbox(table.getEmfTableConfiguration().getDeactivationStrategy().isDeactivationSupported())//
					.setDisabled(true));
		detailsDisplay
			.add(
				EmfI18n.CHANGE_LOGGERS_EXIST,
				new DomCheckbox(!table.getChangeLoggers().isEmpty())//
					.setDisabled(true));
		appendChild(detailsDisplay);
	}

	private void buildScopeTableRow(DomLabelGrid detailsDisplay, ISqlTable<?> targetTable) {

		if (targetTable instanceof EmfObjectTable) {
			detailsDisplay.add(EmfI18n.SCOPE, new EmfTableViewButton((IEmfTable<?, ?, ?>) targetTable));
		} else {
			detailsDisplay.add(EmfI18n.SCOPE, new EmfStringDisplay(targetTable.getFullName().getCanonicalName()));
		}
	}

	private DomDiv createTableIconDiv() {

		DomDiv iconDiv = new DomDiv();
		iconDiv.appendChild(new DomIcon(table.getIcon()));
		return iconDiv;
	}

	private DomDiv buildChildTablesRow() {

		DomDiv childTablesDiv = new DomDiv();
		for (IEmfTable<?, ?, ?> table: table.getChildTables()) {
			childTablesDiv.appendChild(new EmfTableViewButton(table));
			childTablesDiv.appendNewChild(DomElementTag.BR);
		}
		return childTablesDiv;
	}
}
