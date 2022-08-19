package com.softicar.platform.common.core.uuid;

import java.util.UUID;

/**
 * Implemented by types which are annotated with a {@link UUID}.
 * <p>
 * TODO PLAT-1087 remove this interface
 *
 * @author Alexander Schmidt
 */
public interface IUuidAnnotated {

	/**
	 * Returns the {@link UUID} identifying the {@link Class} of this object.
	 *
	 * @return the {@link UUID} (never <i>null</i>)
	 */
	UUID getAnnotatedUuid();
}
