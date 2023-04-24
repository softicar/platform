package com.softicar.platform.emf.page.badge;

import com.softicar.platform.emf.validation.result.EmfDiagnosticLevel;
import java.util.function.Supplier;

/**
 * An {@link EmfPageBadge} with {@link EmfDiagnosticLevel#INFO}.
 *
 * @author Oliver Richers
 */
public class EmfPageInfoBadge extends EmfPageBadge {

	/**
	 * Constructs this badge with the given count supplier.
	 *
	 * @param countSupplier
	 *            the {@link Supplier} for the badge counter (never <i>null</i>)
	 */
	public EmfPageInfoBadge(Supplier<Integer> countSupplier) {

		super(EmfDiagnosticLevel.INFO, countSupplier);
	}
}
