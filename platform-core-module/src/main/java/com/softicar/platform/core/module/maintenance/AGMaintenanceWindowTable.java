package com.softicar.platform.core.module.maintenance;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.core.module.maintenance.action.CancelMaintenanceAction;
import com.softicar.platform.core.module.maintenance.action.FinishMaintenanceAction;
import com.softicar.platform.core.module.maintenance.action.StartMaintenanceAction;
import com.softicar.platform.core.module.maintenance.state.AGMaintenanceStateEnum;
import com.softicar.platform.core.module.module.instance.system.SystemModuleInstance;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.action.EmfActionSet;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.authorizer.EmfAuthorizer;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.management.EmfManagementConfiguration;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.table.configuration.EmfAttributeDefaultValueSet;

public class AGMaintenanceWindowTable extends EmfObjectTable<AGMaintenanceWindow, SystemModuleInstance> {

	public AGMaintenanceWindowTable(IDbObjectTableBuilder<AGMaintenanceWindow> builder) {

		super(builder);
	}

	@Override
	public void customizeAuthorizer(EmfAuthorizer<AGMaintenanceWindow, SystemModuleInstance> authorizer) {

		authorizer.setCreationPermission(CorePermissions.SYSTEM_ADMINISTRATION);
		authorizer.setEditPermission(CorePermissions.SYSTEM_ADMINISTRATION.toOtherEntityPermission());
		authorizer.setViewPermission(CorePermissions.SUPER_USER.toOtherEntityPermission());
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGMaintenanceWindow> attributes) {

		attributes//
			.editAttribute(AGMaintenanceWindow.STATE)
			.setEditable(false);
	}

	@Override
	public void customizeAttributeDefaultValues(EmfAttributeDefaultValueSet<AGMaintenanceWindow, SystemModuleInstance> defaultValueSet) {

		defaultValueSet.setSupplier(AGMaintenanceWindow.STATE, AGMaintenanceStateEnum.PENDING::getRecord);
	}

	@Override
	public void customizeActionSet(EmfActionSet<AGMaintenanceWindow, SystemModuleInstance> actionSet) {

		actionSet.addManagementAction(new StartMaintenanceAction());
		actionSet.addManagementAction(new FinishMaintenanceAction());
		actionSet.addManagementAction(new CancelMaintenanceAction());
	}

	@Override
	public void customizeManagementConfiguraton(EmfManagementConfiguration<AGMaintenanceWindow> configuration) {

		configuration.addOrderBy(AGMaintenanceWindow.EXPECTED_START, OrderDirection.ASCENDING);
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<AGMaintenanceWindow> loggerSet) {

		loggerSet
			.addPlainChangeLogger(AGMaintenanceWindowLog.MAINTENANCE_WINDOW, AGMaintenanceWindowLog.TRANSACTION)
			.addMapping(AGMaintenanceWindow.EXPECTED_START, AGMaintenanceWindowLog.EXPECTED_START)
			.addMapping(AGMaintenanceWindow.EXPECTED_END, AGMaintenanceWindowLog.EXPECTED_END)
			.addMapping(AGMaintenanceWindow.STATE, AGMaintenanceWindowLog.STATE)
			.addMapping(AGMaintenanceWindow.COMMENT, AGMaintenanceWindowLog.COMMENT);
	}
}
