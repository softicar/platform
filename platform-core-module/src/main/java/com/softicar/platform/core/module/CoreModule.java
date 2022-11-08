package com.softicar.platform.core.module;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.core.module.ajax.exception.AGAjaxException;
import com.softicar.platform.core.module.ajax.session.AGAjaxSession;
import com.softicar.platform.core.module.ajax.session.revision.AGAjaxSessionRevision;
import com.softicar.platform.core.module.event.AGSystemEvent;
import com.softicar.platform.core.module.log.process.AGLogProcess;
import com.softicar.platform.core.module.maintenance.AGMaintenanceWindow;
import com.softicar.platform.core.module.module.AbstractModule;
import com.softicar.platform.core.module.module.instance.IModuleInstanceTable;
import com.softicar.platform.core.module.program.execution.AGProgramExecution;
import com.softicar.platform.core.module.replication.checksum.AGReplicationChecksum;
import com.softicar.platform.core.module.user.login.AGUserLoginLog;
import com.softicar.platform.core.module.user.login.failure.AGUserLoginFailureLog;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.emf.page.EmfPagePath;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Collection;
import java.util.List;

@SourceCodeReferencePointUuid("a8b076bd-582d-446d-9bce-85a8a180afd5")
public class CoreModule extends AbstractModule<AGCoreModuleInstance> {

	private static final IDisplayString PARENT_FOLDER_TITLE = CoreI18n.SYSTEM.encloseInBrackets();

	@Override
	public IResource getIcon() {

		return CoreImages.CORE_MODULE.getResource();
	}

	@Override
	public IDisplayString toDisplay() {

		return CoreI18n.CORE;
	}

	@Override
	public IModuleInstanceTable<AGCoreModuleInstance> getModuleInstanceTable() {

		return AGCoreModuleInstance.TABLE;
	}

	@Override
	public EmfPagePath getDefaultPagePath(AGCoreModuleInstance moduleInstance) {

		return getParentPagePath().append(toDisplay());
	}

	/**
	 * Returns the {@link EmfPagePath} of the artificial parent folder that
	 * contains sub-folders for {@link CoreModule} and potentially further
	 * modules.
	 *
	 * @return the parent folder as an {@link EmfPagePath} (never <i>null</i>)
	 */
	public static EmfPagePath getParentPagePath() {

		return new EmfPagePath().append(PARENT_FOLDER_TITLE);
	}

	@Override
	public Collection<IDbTable<?, ?>> getTransactionDataTables() {

		return List
			.of(//
				AGAjaxException.TABLE,
				AGAjaxSession.TABLE,
				AGAjaxSessionRevision.TABLE,
				AGLogProcess.TABLE,
				AGMaintenanceWindow.TABLE,
				AGProgramExecution.TABLE,
				AGReplicationChecksum.TABLE,
				AGSystemEvent.TABLE,
				AGUserLoginFailureLog.TABLE,
				AGUserLoginLog.TABLE);
	}

	// TODO Remove with PLAT-1099
	public static <T extends IEmfTableRow<T, ?>> IEmfPermission<T> getAdministationPermission() {

		return CorePermissions.ADMINISTRATION.<T> of(CoreModuleMapper.get());
	}

	// TODO Remove with PLAT-1099
	public static <T extends IEmfTableRow<T, ?>> IEmfPermission<T> getOperationPermission() {

		return CorePermissions.OPERATION.<T> of(CoreModuleMapper.get());
	}

	// TODO Remove with PLAT-1099
	public static <T extends IEmfTableRow<T, ?>> IEmfPermission<T> getViewPermission() {

		return CorePermissions.VIEW.<T> of(CoreModuleMapper.get());
	}
}
