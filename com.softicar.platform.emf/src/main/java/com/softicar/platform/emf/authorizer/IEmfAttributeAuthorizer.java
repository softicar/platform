package com.softicar.platform.emf.authorizer;

import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.table.row.IEmfTableRow;

/**
 * The attribute authorizer controls with attribute are accessible.
 *
 * @author Oliver Richers
 */
public interface IEmfAttributeAuthorizer<R extends IEmfTableRow<R, ?>> {

	boolean isVisible(IEmfAttribute<R, ?> attribute);

	boolean isChangable(IEmfAttribute<R, ?> attribute);
}
