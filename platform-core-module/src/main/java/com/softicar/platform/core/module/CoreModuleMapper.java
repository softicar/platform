package com.softicar.platform.core.module;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.mapper.IEmfTableRowMapper;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Optional;

/**
 * Conveniently maps permissions to {@link AGCoreModuleInstance}.
 */
class CoreModuleMapper<T extends IEmfTableRow<T, ?>> implements IEmfTableRowMapper<T, AGCoreModuleInstance> {

	public static <T extends IEmfTableRow<T, ?>> IEmfTableRowMapper<T, AGCoreModuleInstance> get() {

		return new CoreModuleMapper<>();
	}

	@Override
	public IDisplayString getTitle() {

		return CoreI18n.CORE_MODULE_INSTANCE;
	}

	@Override
	public Optional<AGCoreModuleInstance> apply(T entity) {

		return Optional.of(AGCoreModuleInstance.getInstance());
	}
}
