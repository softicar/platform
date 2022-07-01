package com.softicar.platform.emf.page;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.module.IEmfModuleInstance;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import com.softicar.platform.emf.source.code.reference.point.IEmfSourceCodeReferencePoint;

/**
 * Common interface of all EMF web pages.
 * <p>
 * An {@link IEmfPage} is essentially a factory of an {@link IDomNode} for a
 * given {@link IEmfModuleInstance}.
 * <p>
 * Furthermore, an {@link IEmfPage} has
 * <ul>
 * <li>a title</li>
 * <li>a path</li>
 * <li>an icon</li>
 * <li>and user access controls</li>
 * </ul>
 *
 * @author Oliver Richers
 */
public interface IEmfPage<I extends IEmfModuleInstance<I>> extends IEmfSourceCodeReferencePoint {

	/**
	 * Returns the {@link Class} of the associated {@link IEmfModule}.
	 *
	 * @return the {@link IEmfModule} class (never <i>null</i>)
	 */
	Class<? extends IEmfModule<I>> getModuleClass();

	/**
	 * Creates a new {@link IDomNode} for the given {@link IEmfModuleInstance}.
	 *
	 * @param moduleInstance
	 *            the {@link IEmfModuleInstance} (never <i>null</i>)
	 * @return the new {@link IDomNode} (never <i>null</i>)
	 */
	IDomNode createContentNode(I moduleInstance);

	// -------------------- title, icon and path -------------------- //

	/**
	 * Returns the title of this {@link IEmfPage}.
	 * <p>
	 * The parameter will be <i>null</i> if the page title shall be determined
	 * for an unspecified module instance. Any implementation must either handle
	 * a <i>null</i> parameter, or ignore the parameter altogether.
	 *
	 * @param moduleInstance
	 *            the {@link IEmfModuleInstance} (may be <i>null</i>)
	 * @return the title (never <i>null</i>)
	 */
	IDisplayString getTitle(I moduleInstance);

	/**
	 * Returns the {@link EmfPagePath} of this {@link IEmfPage}.
	 * <p>
	 * The {@link EmfPagePath} defines where the page is to be located among all
	 * other pages, e.g. in a navigation bar.
	 *
	 * @param modulePath
	 *            the default {@link EmfPagePath} provided by the
	 *            {@link IEmfModule} (never <i>null</i>)
	 * @return the {@link EmfPagePath} (never <i>null</i>)
	 */
	default EmfPagePath getPagePath(EmfPagePath modulePath) {

		return modulePath;
	}

	/**
	 * Returns the icon of this {@link IEmfPage}.
	 *
	 * @return the icon (never <i>null</i>)
	 */
	default IResource getIcon() {

		return EmfImages.PAGE_DEFAULT.getResource();
	}

	// -------------------- access -------------------- //

	/**
	 * Defines if this {@link IEmfPage} allows user impersonation.
	 * <p>
	 * If user impersonation is allowed, a user may access this {@link IEmfPage}
	 * while impersonating another user.
	 * <p>
	 * It is usually a bad idea to allow user impersonation for administrative
	 * pages.
	 *
	 * @return <i>true</i> if user impersonation is allowed; <i>false</i>
	 *         otherwise
	 */
	default boolean isUserImpersonationAllowed() {

		return true;
	}

	/**
	 * Returns the precondition of this {@link IEmfPage}, determining the
	 * availability of the {@link IEmfPage} for a given
	 * {@link IEmfModuleInstance}.
	 * <p>
	 * Only if the returned {@link IEmfPredicate} tests to <i>true</i> for a
	 * given {@link IEmfModuleInstance}, this {@link IEmfPage} is accessible for
	 * it.
	 *
	 * @return the {@link IEmfPredicate} (never <i>null</i>)
	 */
	default IEmfPredicate<I> getPrecondition() {

		return EmfPredicates.always();
	}

	/**
	 * Returns whether the user is able to directly navigate to this
	 * {@link IEmfPage}.
	 *
	 * @return whether this {@link IEmfPage} is navigatable (<i>true</i> by
	 *         default)
	 */
	default boolean isNavigatable() {

		return true;
	}

	/**
	 * Returns the necessary {@link IEmfPermission} to access this
	 * {@link IEmfPage}.
	 * <p>
	 * Only users matching the {@link IEmfPermission} will be able to access
	 * this {@link IEmfPage}.
	 *
	 * @return the {@link IEmfPermission} (never <i>null</i>)
	 */
	IEmfPermission<I> getRequiredPermission();
}
