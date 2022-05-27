package com.softicar.platform.core.module.event;

import com.softicar.platform.core.module.CoreRoles;
import com.softicar.platform.core.module.event.properties.SystemEventPropertiesDisplay;
import com.softicar.platform.core.module.module.instance.system.SystemModuleInstance;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.dom.DomCssPseudoClasses;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.action.EmfActionSet;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.authorization.role.EmfRoles;
import com.softicar.platform.emf.authorizer.EmfAuthorizer;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.management.EmfManagementConfiguration;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGSystemEventTable extends EmfObjectTable<AGSystemEvent, SystemModuleInstance> {

	public AGSystemEventTable(IDbObjectTableBuilder<AGSystemEvent> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGSystemEvent, Integer, SystemModuleInstance> configuration) {

		configuration.setIcon(EmfImages.ENTITY_LOG);
	}

	@Override
	public void customizeAuthorizer(EmfAuthorizer<AGSystemEvent, SystemModuleInstance> authorizer) {

		authorizer.setCreationRole(EmfRoles.nobody());
		authorizer.setEditRole(EmfRoles.nobody());
		authorizer.setViewRole(CoreRoles.SYSTEM_ADMINISTRATOR.toOtherEntityRole());
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGSystemEvent> attributes) {

		attributes//
			.editAttribute(AGSystemEvent.SEVERITY)
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
	public void customizeActionSet(EmfActionSet<AGSystemEvent, SystemModuleInstance> actionSet) {

		actionSet.addPrimaryAction(new SystemEventConfirmAction());
		actionSet.addManagementAction(new SystemEventConfirmAction());
	}

	@Override
	public void customizeManagementConfiguraton(EmfManagementConfiguration<AGSystemEvent> configuration) {

		configuration.setRowCustomizer(row -> {
			var dataRow = row.getDataRow();
			if (dataRow.isNeedsAttention()) {
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
			.addMapping(AGSystemEvent.NEEDS_ATTENTION, AGSystemEventLog.NEEDS_ATTENTION);
	}
}
