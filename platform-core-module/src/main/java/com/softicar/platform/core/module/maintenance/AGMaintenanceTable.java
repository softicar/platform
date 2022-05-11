package com.softicar.platform.core.module.maintenance;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.core.module.CoreRoles;
import com.softicar.platform.core.module.maintenance.action.CancelMaintenanceAction;
import com.softicar.platform.core.module.maintenance.action.FinishMaintenanceAction;
import com.softicar.platform.core.module.maintenance.action.StartMaintenanceAction;
import com.softicar.platform.core.module.maintenance.status.AGMaintenanceStatusEnum;
import com.softicar.platform.core.module.module.instance.system.SystemModuleInstance;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.action.EmfActionSet;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.authorizer.EmfAuthorizer;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.management.EmfManagementConfiguration;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.table.configuration.EmfAttributeDefaultValueSet;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGMaintenanceTable extends EmfObjectTable<AGMaintenance, SystemModuleInstance> {

	public AGMaintenanceTable(IDbObjectTableBuilder<AGMaintenance> builder) {

		super(builder);
	}

	@Override
	public void customizeAuthorizer(EmfAuthorizer<AGMaintenance, SystemModuleInstance> authorizer) {

		authorizer.setCreationRole(CoreRoles.MAINTENANCE_ADMINISTRATOR);
		authorizer.setEditRole(CoreRoles.MAINTENANCE_ADMINISTRATOR.toOtherEntityRole());
		authorizer.setViewRole(CoreRoles.SUPER_USER.toOtherEntityRole());
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGMaintenance, Integer, SystemModuleInstance> configuration) {

		configuration.setEditPredicate(MaintenancePredicates.PENDING);
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGMaintenance> attributes) {

		attributes//
			.editAttribute(AGMaintenance.STATUS)
			.setEditable(false);
	}

	@Override
	public void customizeAttributeDefaultValues(EmfAttributeDefaultValueSet<AGMaintenance, SystemModuleInstance> defaultValueSet) {

		defaultValueSet.setSupplier(AGMaintenance.STATUS, AGMaintenanceStatusEnum.PENDING::getRecord);
	}

	@Override
	public void customizeActionSet(EmfActionSet<AGMaintenance, SystemModuleInstance> actionSet) {

		actionSet.addManagementAction(new StartMaintenanceAction());
		actionSet.addManagementAction(new FinishMaintenanceAction());
		actionSet.addManagementAction(new CancelMaintenanceAction());
	}

	@Override
	public void customizeManagementConfiguraton(EmfManagementConfiguration<AGMaintenance> configuration) {

		configuration.addOrderBy(AGMaintenance.EXPECTED_START, OrderDirection.ASCENDING);
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<AGMaintenance> loggerSet) {

		loggerSet
			.addPlainChangeLogger(AGMaintenanceLog.MAINTENANCE, AGMaintenanceLog.TRANSACTION)
			.addMapping(AGMaintenance.EXPECTED_START, AGMaintenanceLog.EXPECTED_START)
			.addMapping(AGMaintenance.EXPECTED_END, AGMaintenanceLog.EXPECTED_END)
			.addMapping(AGMaintenance.STATUS, AGMaintenanceLog.STATUS)
			.addMapping(AGMaintenance.COMMENT, AGMaintenanceLog.COMMENT);
	}
}
