package com.softicar.platform.emf.authorizer;

import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.HashMap;
import java.util.Map;

/**
 * Default implementation of {@link IEmfAttributeAuthorizer}.
 *
 * @author Oliver Richers
 */
public class EmfAttributeAuthorizer<R extends IEmfTableRow<R, ?>> implements IEmfAttributeAuthorizer<R> {

	private final R tableRow;
	private final IBasicUser user;
	private final Map<IEmfAttribute<R, ?>, Authorization> authorizations;

	public EmfAttributeAuthorizer(R tableRow, IBasicUser user) {

		this.tableRow = tableRow;
		this.user = user;
		this.authorizations = new HashMap<>();
	}

	@Override
	public boolean isVisible(IEmfAttribute<R, ?> attribute) {

		return getAuthorization(attribute).isVisible();
	}

	@Override
	public boolean isChangable(IEmfAttribute<R, ?> attribute) {

		return getAuthorization(attribute).isChangable();
	}

	public R getTableRow() {

		return tableRow;
	}

	public IBasicUser getUser() {

		return user;
	}

	protected void setConcealed(IEmfAttribute<R, ?> attribute) {

		authorizations.put(attribute, Authorization.CONCEALED);
	}

	protected void setReadOnly(IEmfAttribute<R, ?> attribute) {

		authorizations.put(attribute, Authorization.READ_ONLY);
	}

	protected void setReadWrite(IEmfAttribute<R, ?> attribute) {

		authorizations.put(attribute, Authorization.READ_WRITE);
	}

	protected void setAllReadOnly() {

		setAll(Authorization.READ_ONLY);
	}

	protected void setAllReadWrite() {

		setAll(Authorization.READ_WRITE);
	}

	protected void setAll(Authorization authorization) {

		for (IEmfAttribute<R, ?> attribute: tableRow.table().getAttributes()) {
			authorizations.put(attribute, authorization);
		}
	}

	private Authorization getAuthorization(IEmfAttribute<R, ?> attribute) {

		return authorizations.getOrDefault(attribute, Authorization.READ_WRITE);
	}

	private static enum Authorization {

		CONCEALED,
		READ_ONLY,
		READ_WRITE;

		public boolean isVisible() {

			return this == READ_ONLY || this == READ_WRITE;
		}

		public boolean isChangable() {

			return this == READ_WRITE;
		}
	}
}
