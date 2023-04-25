package com.softicar.platform.emf.page.badge;

import com.softicar.platform.emf.validation.result.EmfDiagnosticLevel;
import java.util.function.Supplier;

/**
 * An {@link EmfPageBadge} with {@link EmfDiagnosticLevel#WARNING}.
 *
 * @author Oliver Richers
 */
public class EmfPageWarningBadge extends EmfPageBadge {

	/**
	 * Constructs this badge with the given count supplier.
	 *
	 * @param countSupplier
	 *            the {@link Supplier} for the badge counter (never <i>null</i>)
	 */
	public EmfPageWarningBadge(Supplier<Integer> countSupplier) {

		super(EmfDiagnosticLevel.WARNING, countSupplier);
	}
}
