package com.softicar.platform.common.ui.refresh;

import com.softicar.platform.common.core.interfaces.IRefreshable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Facilitates handling multiple {@link IRefreshable}s.
 * 
 * @author Alexander Schmidt
 */
public class MultiRefreshable implements IRefreshable {

	private final List<IRefreshable> refreshables = new ArrayList<>();

	public MultiRefreshable(IRefreshable...refreshables) {

		if (refreshables != null) {
			addRefreshables(Arrays.asList(refreshables));
		}
	}

	public MultiRefreshable(Collection<IRefreshable> refreshables) {

		addRefreshables(refreshables);
	}

	@Override
	public void refresh() {

		for (IRefreshable refreshable: this.refreshables) {
			if (refreshable != null) {
				refreshable.refresh();
			}
		}
	}

	public MultiRefreshable addRefreshable(IRefreshable refreshable) {

		if (refreshable != null) {
			if (!this.refreshables.contains(refreshable)) {
				this.refreshables.add(refreshable);
			}
		}

		return this;
	}

	public MultiRefreshable addRefreshables(Collection<IRefreshable> refreshables) {

		if (refreshables != null) {
			for (IRefreshable refreshable: refreshables) {
				addRefreshable(refreshable);
			}
		}

		return this;
	}

	public MultiRefreshable addRefreshables(IRefreshable...refreshables) {

		addRefreshables(Arrays.asList(refreshables));

		return this;
	}

	public MultiRefreshable removeRefreshable(IRefreshable refreshable) {

		this.refreshables.remove(refreshable);

		return this;
	}

	public MultiRefreshable removeRefreshables(Collection<IRefreshable> refreshables) {

		if (refreshables != null) {
			for (IRefreshable refreshable: refreshables) {
				removeRefreshable(refreshable);
			}
		}

		return this;
	}

	public MultiRefreshable removeRefreshables(IRefreshable...refreshables) {

		removeRefreshables(Arrays.asList(refreshables));

		return this;
	}
}
