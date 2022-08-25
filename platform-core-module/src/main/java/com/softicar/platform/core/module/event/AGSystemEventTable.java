package com.softicar.platform.core.module.event;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.event.properties.SystemEventPropertiesDisplay;
import com.softicar.platform.core.module.event.severity.SystemEventSeverityDisplay;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.dom.DomCssPseudoClasses;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.action.EmfActionSet;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.authorizer.EmfAuthorizer;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.management.EmfManagementConfiguration;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.permission.EmfPermissions;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGSystemEventTable extends EmfObjectTable<AGSystemEvent, AGCoreModuleInstance> {

	public AGSystemEventTable(IDbObjectTableBuilder<AGSystemEvent> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGSystemEvent, Integer, AGCoreModuleInstance> configuration) {

		configuration.setIcon(EmfImages.ENTITY_LOG);
	}

	@Override
	public void customizeAuthorizer(EmfAuthorizer<AGSystemEvent, AGCoreModuleInstance> authorizer) {

		authorizer.setCreationPermission(EmfPermissions.never());
		authorizer.setEditPermission(EmfPermissions.never());
		authorizer.setViewPermission(CoreModule.getAdministationPermission());
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGSystemEvent> attributes) {

		attributes//
			.editAttribute(AGSystemEvent.SEVERITY)
			.setImmutable(true)
			.setDisplayFactory(SystemEventSeverityDisplay::new);
		attributes//
			.editAttribute(AGSystemEvent.CAUSED_BY)
			.setImmutable(true);
		attributes//
			.editAttribute(AGSystemEvent.CAUSED_AT)
			.setImmutable(true);
		attributes//
			.editAttribute(AGSystemEvent.MESSAGE)
			.setImmutable(true);
		attributes//
			.editAttribute(AGSystemEvent.PROPERTIES)
			.setImmutable(true)
			.setDisplayFactory(SystemEventPropertiesDisplay::new);
	}

	@Override
	public void customizeActionSet(EmfActionSet<AGSystemEvent, AGCoreModuleInstance> actionSet) {

		actionSet.addPrimaryAction(new SystemEventConfirmAction());
		actionSet.addManagementAction(new SystemEventConfirmAction());
		actionSet.addScopeAction(new SystemEventConfirmAllAction());
	}

	@Override
	public void customizeManagementConfiguraton(EmfManagementConfiguration<AGSystemEvent> configuration) {

		configuration.addOrderBy(AGSystemEvent.NEEDS_CONFIRMATION, OrderDirection.DESCENDING);
		configuration.addOrderBy(AGSystemEvent.CAUSED_AT, OrderDirection.DESCENDING);
		configuration.addOrderBy(AGSystemEvent.ID, OrderDirection.DESCENDING);

		configuration.setRowCustomizer(row -> {
			var dataRow = row.getDataRow();
			if (dataRow.isNeedsConfirmation()) {
				if (dataRow.getSeverity().isError()) {
					row.addCssClass(DomCssPseudoClasses.ERROR);
				} else if (dataRow.getSeverity().isWarning()) {
					row.addCssClass(DomCssPseudoClasses.WARNING);
				}
			}
		});
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<AGSystemEvent> loggerSet) {

		loggerSet//
			.addPlainChangeLogger(AGSystemEventLog.SYSTEM_EVENT, AGSystemEventLog.TRANSACTION)
			.addMapping(AGSystemEvent.NEEDS_CONFIRMATION, AGSystemEventLog.NEEDS_CONFIRMATION);
	}
}
