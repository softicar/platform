package com.softicar.platform.emf.form;

import com.softicar.platform.common.core.user.CurrentBasicUser;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.editor.EmfAttributesDiv;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfFormAttributesDiv<R extends IEmfTableRow<R, ?>> extends EmfAttributesDiv<R> {

	public EmfFormAttributesDiv(R tableRow, boolean editMode) {

		super(tableRow, editMode);

		for (IEmfAttribute<R, ?> attribute: tableRow.table().getAttributes()) {
			if (attribute.isConcealed() || isScopeViewPermissionMissing(attribute)) {
				// skip concealed attributes
			} else if (tableRow.impermanent() && attribute.isTransient()) {
				// skip transient attributes in creation mode
			} else {
				addAttribute(attribute);
			}
		}
	}

	//FIXME This works, but needs simplification
	private boolean isScopeViewPermissionMissing(IEmfAttribute<R, ?> attribute) {

		var scopeAttribute = tableRow.table().getScopeAttribute();
		if (scopeAttribute.map(it -> it.equals(attribute)).orElse(false)) {
			var scope = scopeAttribute.get().getValue(tableRow);
			return !scopeAttribute//
				.get()
				.asForeignRowAttribute()
				.get()
				.getTargetTable()
				.getAuthorizer()
				.getViewPermission()
				.test(CastUtils.cast(scope), CurrentBasicUser.get());
		}
		return false;
	}
}
