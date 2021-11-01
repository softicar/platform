package com.softicar.platform.emf.table.listener;

import com.softicar.platform.common.core.singleton.Singleton;
import com.softicar.platform.db.runtime.table.listener.IDbTableRowNotificationSet;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.function.Supplier;

/**
 * Wraps the factory of a custom {@link IEmfCommitHook} into a new
 * {@link IEmfCommitHook}.
 * <p>
 * The factory is used to provide a thread-local instance of the
 * {@link IEmfCommitHook}.
 *
 * @author Oliver Richers
 */
public class EmfCommitHookWrapper<R extends IEmfTableRow<R, ?>> implements IEmfCommitHook<R> {

	private final Singleton<IEmfCommitHook<R>> threadLocalHookInstance;

	public EmfCommitHookWrapper(Supplier<IEmfCommitHook<R>> factory) {

		this.threadLocalHookInstance = new Singleton<>(factory);
	}

	@Override
	public void beforeCommit(IDbTableRowNotificationSet<R> notificationSet) {

		threadLocalHookInstance.get().beforeCommit(notificationSet);
	}

	@Override
	public void afterCommit(IDbTableRowNotificationSet<R> notificationSet) {

		threadLocalHookInstance.get().afterCommit(notificationSet);
	}
}
