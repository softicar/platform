package com.softicar.platform.emf.action.viewer;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.element.DomSimpleElement;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.wiki.DomWikiDivBuilder;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.action.IEmfPrimaryAction;
import com.softicar.platform.emf.permission.EmfAnyPermission;
import com.softicar.platform.emf.permission.EmfConditionalPermission;
import com.softicar.platform.emf.permission.EmfPermission;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.emf.table.row.IEmfTableRow;

/**
 * Displays information about a given {@link IEmfPrimaryAction}.
 */
public class EmfActionDiv<R extends IEmfTableRow<R, ?>> extends DomDiv {

	public EmfActionDiv(IEmfPrimaryAction<R> action) {

		DomWikiDivBuilder builder = new DomWikiDivBuilder(this);

		builder.addHeadline("====", EmfI18n.PRECONDITION);
		builder.addLine(action.getPrecondition().getTitle().enclose("<info>", "</info>"));

		builder.addHeadline("====", EmfI18n.REQUIRED_PERMISSION);
		builder.registerElement("permission", createPermissionDiv(action.getRequiredPermission()));
		builder.addLine(IDisplayString.create("{{permission}}").enclose("<info>", "</info>"));

		builder.build();
	}

	private IDomElement createPermissionDiv(IEmfPermission<R> permission) {

		if (permission instanceof EmfPermission) {
			return new PermissionDiv((EmfPermission<R>) permission);
		} else if (permission instanceof EmfAnyPermission) {
			return new AnyPermissionDiv((EmfAnyPermission<R>) permission);
		} else if (permission instanceof EmfConditionalPermission) {
			return new ConditionalPermissionDiv((EmfConditionalPermission<R>) permission);
		} else {
			return new UnsupportedPermissionClassDiv(permission);
		}
	}

	private class UnsupportedPermissionClassDiv extends DomDiv {

		public UnsupportedPermissionClassDiv(IEmfPermission<R> permission) {

			appendText(EmfI18n.UNSUPPORTED_PERMISSION_CLASS_ARG1.toDisplay(permission.getClass().getSimpleName()));
		}
	}

	private class PermissionDiv extends DomDiv {

		public PermissionDiv(EmfPermission<R> permission) {

			appendChild(permission.getTitle());
		}
	}

	private class AnyPermissionDiv extends DomDiv {

		public AnyPermissionDiv(EmfAnyPermission<R> anyPermission) {

			appendText(EmfI18n.ONE_OF_THE_FOLLOWING_PERMISSIONS);
			appendNewChild(DomElementTag.BR);
			DomSimpleElement list = appendNewChild(DomElementTag.UL);
			for (IEmfPermission<R> permission: anyPermission.getPermissions()) {
				DomSimpleElement item = list.appendNewChild(DomElementTag.LI);
				item.appendChild(createPermissionDiv(permission));
			}
		}
	}

	private class ConditionalPermissionDiv extends DomDiv {

		public ConditionalPermissionDiv(EmfConditionalPermission<R> permission) {

			DomWikiDivBuilder builder = new DomWikiDivBuilder(this);
			builder.addLine(permission.getPermission().getTitle());
			builder.addLineBreak();
			builder.addLine(EmfI18n.IF.enclose("**"));
			builder.addLine(permission.getPredicate().getTitle());
			builder.build();
		}
	}
}
