package com.softicar.platform.emf.page.badge;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.refresh.bus.IDomRefreshBusEvent;
import com.softicar.platform.dom.refresh.bus.IDomRefreshBusListener;
import com.softicar.platform.emf.page.IEmfPage;
import com.softicar.platform.emf.validation.result.EmfDiagnosticLevel;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Defines a badge on a {@link IEmfPage} navigation link.
 *
 * @author Oliver Richers
 */
public class EmfPageBadge {

	private final EmfDiagnosticLevel level;
	private final Supplier<Integer> countSupplier;
	private IDisplayString title;
	private Predicate<IDomRefreshBusEvent> refreshPredicate;
	private boolean hideZero;

	/**
	 * Constructs this badge with the given {@link EmfDiagnosticLevel} and count
	 * supplier.
	 *
	 * @param level
	 *            the {@link EmfDiagnosticLevel} (never <i>null</i>)
	 * @param countSupplier
	 *            the {@link Supplier} for the badge counter (never <i>null</i>)
	 */
	public EmfPageBadge(EmfDiagnosticLevel level, Supplier<Integer> countSupplier) {

		this.level = Objects.requireNonNull(level);
		this.countSupplier = Objects.requireNonNull(countSupplier);
		this.title = null;
		this.refreshPredicate = event -> event.isAllChanged();
		this.hideZero = true;
	}

	/**
	 * Defines the title of this {@link EmfPageBadge}.
	 *
	 * @param title
	 *            the title (may be <i>null</i>)
	 * @return this
	 */
	public EmfPageBadge setTitle(IDisplayString title) {

		this.title = title;
		return this;
	}

	/**
	 * Defines for which classes {@link IDomRefreshBusListener#refresh} shall
	 * update the badge.
	 *
	 * @param classes
	 *            the classes to watch (never <i>null</i>)
	 * @return this
	 */
	public EmfPageBadge setRefreshClasses(Class<?>...classes) {

		this.refreshPredicate = event -> event.isAnyObjectChanged(classes);
		return this;
	}

	/**
	 * Defines when {@link IDomRefreshBusListener#refresh} shall update the
	 * badge.
	 * <p>
	 * By default, {@link IDomRefreshBusEvent#isAllChanged()} is tested.
	 * <p>
	 * You can only use either {@link #setRefreshClasses} or
	 * {@link #setRefreshPredicate}.
	 *
	 * @param refreshPredicate
	 *            the {@link Predicate} to test (never <i>null</i>)
	 * @return this
	 */
	public EmfPageBadge setRefreshPredicate(Predicate<IDomRefreshBusEvent> refreshPredicate) {

		this.refreshPredicate = refreshPredicate;
		return this;
	}

	/**
	 * Defines whether to hide this badge if the count is zero.
	 *
	 * @param hideZero
	 *            <i>true</i> to hide the badge when the count is zero;
	 *            <i>false</i> to also show the badge if count is zero
	 * @return this
	 */
	public EmfPageBadge setHideZero(boolean hideZero) {

		this.hideZero = hideZero;
		return this;
	}

	/**
	 * Returns the {@link EmfDiagnosticLevel} of this badge.
	 *
	 * @return the {@link EmfDiagnosticLevel} (never <i>null</i>)
	 */
	public EmfDiagnosticLevel getLevel() {

		return level;
	}

	/**
	 * The current count to show on the badge.
	 *
	 * @return the count
	 */
	public int getCount() {

		try {
			return countSupplier.get();
		} catch (Throwable throwable) {
			throwable.printStackTrace();
			return -1;
		}
	}

	/**
	 * Returns the optional title of this {@link EmfPageBadge}.
	 *
	 * @return the title as {@link Optional}
	 */
	public Optional<IDisplayString> getTitle() {

		return Optional.ofNullable(title);
	}

	/**
	 * Tests where the badge display needs an update.
	 *
	 * @param event
	 *            the {@link IDomRefreshBusEvent} (never <i>null</i>)
	 * @return <i>true</i> if the badge display needs to be updated;
	 *         <i>false</i> otherwise
	 */
	public boolean needsUpdate(IDomRefreshBusEvent event) {

		Objects.requireNonNull(event);

		try {
			return refreshPredicate.test(event);
		} catch (Throwable throwable) {
			throwable.printStackTrace();
			return false;
		}
	}

	/**
	 * Returns whether to hide the badge when the count is zero.
	 *
	 * @return <i>true</i> if the badge should now be shown if the count is
	 *         zero; <i>false</i> to always show show the badge
	 */
	public boolean isHideZero() {

		return hideZero;
	}
}
