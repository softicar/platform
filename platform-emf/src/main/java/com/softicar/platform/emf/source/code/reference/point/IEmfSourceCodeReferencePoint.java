package com.softicar.platform.emf.source.code.reference.point;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;
import java.util.UUID;

/**
 * Implemented by classes representing reference points in the source code.
 * <p>
 * Classes implementing this interface need to define a {@link UUID} by using
 * the annotation {@link EmfSourceCodeReferencePointUuid}.
 * <p>
 * TODO Extract this class (PLAT-861).
 *
 * @author Oliver Richers
 */
public interface IEmfSourceCodeReferencePoint extends IDisplayable {

	/**
	 * The default implementation returns the canonical name of this class.
	 *
	 * @return the canonical class name (never <i>null</i>)
	 */
	@Override
	default IDisplayString toDisplay() {

		return IDisplayString.create(getClass().getSimpleName());
	}

	/**
	 * Returns the {@link UUID} that uniquely identifies this
	 * {@link IEmfSourceCodeReferencePoint}.
	 * <p>
	 * Throws an {@link Exception} if this class has no valid
	 * {@link EmfSourceCodeReferencePointUuid} annotation.
	 *
	 * @return the {@link UUID} (never <i>null</i>)
	 */
	default UUID getAnnotatedUuid() {

		return EmfSourceCodeReferencePoints.getUuidOrThrow(getClass());
	}
}
