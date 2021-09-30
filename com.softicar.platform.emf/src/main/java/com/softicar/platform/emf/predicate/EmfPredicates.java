package com.softicar.platform.emf.predicate;

import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public interface EmfPredicates {

	static <R> IEmfPredicate<R> always() {

		return new EmfPredicate<>(EmfI18n.ALWAYS, entity -> true);
	}

	static <R> IEmfPredicate<R> never() {

		return new EmfPredicate<>(EmfI18n.NEVER, entity -> false);
	}

	static <R extends IEmfTableRow<R, ?>> IEmfPredicate<R> persistent() {

		return new EmfPredicate<>(EmfI18n.PERSISTENT, entity -> !entity.impermanent());
	}

	static <R extends IEmfTableRow<R, ?>> IEmfPredicate<R> notPersistent() {

		return new EmfPredicate<>(EmfI18n.NOT_PERSISTENT, entity -> entity.impermanent());
	}

	static <R extends IEmfTableRow<R, ?>> IEmfPredicate<R> deactivationSupported() {

		return new EmfPredicate<>(
			EmfI18n.DEACTIVATION_SUPPORTED,
			entity -> entity.table().getEmfTableConfiguration().getDeactivationStrategy().isDeactivationSupported());
	}
}
