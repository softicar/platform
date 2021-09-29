package com.softicar.platform.emf.table.listener;

import com.softicar.platform.common.core.singleton.Singleton;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Collection;
import java.util.function.Supplier;

/**
 * Wraps the factory of a custom {@link IEmfSaveHook} into a new
 * {@link IEmfSaveHook}.
 * <p>
 * The factory is used to provide a thread-local instance of the
 * {@link IEmfSaveHook}.
 *
 * @author Oliver Richers
 */
public class EmfSaveHookWrapper<R extends IEmfTableRow<R, ?>> implements IEmfSaveHook<R> {

	private final Singleton<IEmfSaveHook<R>> threadLocalHookInstance;

	public EmfSaveHookWrapper(Supplier<IEmfSaveHook<R>> factory) {

		this.threadLocalHookInstance = new Singleton<>(factory);
	}

	@Override
	public void beforeSave(Collection<R> tableRows) {

		threadLocalHookInstance.get().beforeSave(tableRows);
	}

	@Override
	public void afterSave(Collection<R> tableRows) {

		threadLocalHookInstance.get().afterSave(tableRows);
	}
}
