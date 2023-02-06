package com.softicar.platform.core.module.maintenance;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.core.module.maintenance.action.CancelMaintenanceAction;
import com.softicar.platform.core.module.maintenance.action.FinishMaintenanceAction;
import com.softicar.platform.core.module.maintenance.action.StartMaintenanceAction;
import com.softicar.platform.core.module.maintenance.state.AGMaintenanceStateEnum;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.action.EmfActionSet;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.authorizer.EmfAuthorizer;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.management.EmfManagementConfiguration;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.table.configuration.EmfAttributeDefaultValueSet;

public class AGMaintenanceWindowTable extends EmfObjectTable<AGMaintenanceWindow, AGCoreModuleInstance> {

	public AGMaintenanceWindowTable(IDbObjectTableBuilder<AGMaintenanceWindow> builder) {

		super(builder);
	}

	@Override
	public void customizeAuthorizer(EmfAuthorizer<AGMaintenanceWindow, AGCoreModuleInstance> authorizer) {

		authorizer.setCreationPermission(CorePermissions.ADMINISTRATION);
		authorizer.setEditPermission(CoreModule.getAdministationPermission());
		authorizer.setViewPermission(CoreModule.getOperationPermission());
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGMaintenanceWindow> attributes) {

		attributes//
			.editAttribute(AGMaintenanceWindow.STATE)
			.setEditable(false);
	}

	@Override
	public void customizeAttributeDefaultValues(EmfAttributeDefaultValueSet<AGMaintenanceWindow, AGCoreModuleInstance> defaultValueSet) {

		defaultValueSet.setSupplier(AGMaintenanceWindow.STATE, AGMaintenanceStateEnum.PENDING::getRecord);
		defaultValueSet.setSupplier(AGMaintenanceWindow.EXPECTED_START, DayTime::now);
		defaultValueSet.setSupplier(AGMaintenanceWindow.EXPECTED_END, DayTime::now);
	}

	@Override
	public void customizeActionSet(EmfActionSet<AGMaintenanceWindow, AGCoreModuleInstance> actionSet) {

		actionSet.addManagementAction(new StartMaintenanceAction());
		actionSet.addManagementAction(new FinishMaintenanceAction());
		actionSet.addManagementAction(new CancelMaintenanceAction());
	}

	@Override
	public void customizeManagementConfiguraton(EmfManagementConfiguration<AGMaintenanceWindow> configuration) {

		configuration.addOrderBy(AGMaintenanceWindow.EXPECTED_START, OrderDirection.DESCENDING);
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
