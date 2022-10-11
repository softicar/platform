package com.softicar.platform.emf.page;

import com.softicar.platform.common.code.reference.point.ISourceCodeReferencePoint;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.module.IEmfModuleInstance;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.predicate.IEmfPredicate;

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
public interface IEmfPage<I extends IEmfModuleInstance<I>> extends ISourceCodeReferencePoint {

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
	 * Returns the necessary {@link IEmfPermission} to access this
	 * {@link IEmfPage}.
	 * <p>
	 * Only users matching the {@link IEmfPermission} will be able to access
	 * this {@link IEmfPage}.
	 *
	 * @return the {@link IEmfPermission} (never <i>null</i>)
	 */
	IEmfPermission<I> getRequiredPermission();

	/**
	 * Determines whether links to this {@link IEmfPage} are listed in UIs.
	 * <p>
	 * If an {@link IEmfPage} is not listed, it is still accessible via URL,
	 * assuming that {@link #getPrecondition()} and
	 * {@link #getRequiredPermission()} are evaluated successfully.
	 * <p>
	 * By default, an {@link IEmfPage} is listed.
	 *
	 * @return <i>true</i> if links are listed; <i>false</i> otherwise
	 */
	default boolean isListed() {

		return true;
	}
}
