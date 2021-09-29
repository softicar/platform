package com.softicar.platform.emf.table.listener;

import com.softicar.platform.common.core.singleton.Singleton;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Collection;
import java.util.function.Supplier;

/**
 * Wraps the factory of a custom {@link IEmfDeleteHook} into a new
 * {@link IEmfDeleteHook}.
 * <p>
 * The factory is used to provide a thread-local instance of the
 * {@link IEmfDeleteHook}.
 *
 * @author Oliver Richers
 */
public class EmfDeleteHookWrapper<R extends IEmfTableRow<R, ?>> implements IEmfDeleteHook<R> {

	private final Singleton<IEmfDeleteHook<R>> threadLocalHookInstance;

	public EmfDeleteHookWrapper(Supplier<IEmfDeleteHook<R>> factory) {

		this.threadLocalHookInstance = new Singleton<>(factory);
	}

	@Override
	public void beforeDelete(Collection<R> tableRows) {

		threadLocalHookInstance.get().beforeDelete(tableRows);
	}

	@Override
	public void afterDelete(Collection<R> tableRows) {

		threadLocalHookInstance.get().afterDelete(tableRows);
	}
}
