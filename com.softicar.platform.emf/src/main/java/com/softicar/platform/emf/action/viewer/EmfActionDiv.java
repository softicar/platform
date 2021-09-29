package com.softicar.platform.emf.action.viewer;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.element.DomSimpleElement;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.wiki.DomWikiDivBuilder;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.action.IEmfPrimaryAction;
import com.softicar.platform.emf.authorization.role.EmfAnyRole;
import com.softicar.platform.emf.authorization.role.EmfConditionalRole;
import com.softicar.platform.emf.authorization.role.EmfRole;
import com.softicar.platform.emf.authorization.role.IEmfRole;
import com.softicar.platform.emf.table.row.IEmfTableRow;

/**
 * Displays information about a given {@link IEmfPrimaryAction}.
 */
public class EmfActionDiv<R extends IEmfTableRow<R, ?>> extends DomDiv {

	public EmfActionDiv(IEmfPrimaryAction<R> action) {

		DomWikiDivBuilder builder = new DomWikiDivBuilder(this);

		builder.addHeadline("====", EmfI18n.PRECONDITION);
		builder.addLine(action.getPrecondition().getTitle().enclose("<info>", "</info>"));

		builder.addHeadline("====", EmfI18n.AUTHORIZED_ROLE);
		builder.registerElement("role", createRoleDiv(action.getAuthorizedRole()));
		builder.addLine(IDisplayString.create("{{role}}").enclose("<info>", "</info>"));

		builder.build();
	}

	private IDomElement createRoleDiv(IEmfRole<R> role) {

		if (role instanceof EmfRole) {
			return new RoleDiv((EmfRole<R>) role);
		} else if (role instanceof EmfAnyRole) {
			return new AnyRoleDiv((EmfAnyRole<R>) role);
		} else if (role instanceof EmfConditionalRole) {
			return new ConditionalRoleDiv((EmfConditionalRole<R>) role);
		} else {
			return new UnsupportedRoleClassDiv(role);
		}
	}

	private class UnsupportedRoleClassDiv extends DomDiv {

		public UnsupportedRoleClassDiv(IEmfRole<R> role) {

			appendText(EmfI18n.UNSUPPORTED_ROLE_CLASS_ARG1.toDisplay(role.getClass().getSimpleName()));
		}
	}

	private class RoleDiv extends DomDiv {

		public RoleDiv(EmfRole<R> role) {

			appendChild(role.getTitle());
		}
	}

	private class AnyRoleDiv extends DomDiv {

		public AnyRoleDiv(EmfAnyRole<R> anyRole) {

			appendText(EmfI18n.ONE_OF_THE_FOLLOWING_ROLES);
			appendNewChild(DomElementTag.BR);
			DomSimpleElement list = appendNewChild(DomElementTag.UL);
			for (IEmfRole<R> role: anyRole.getRoles()) {
				DomSimpleElement item = list.appendNewChild(DomElementTag.LI);
				item.appendChild(createRoleDiv(role));
			}
		}
	}

	private class ConditionalRoleDiv extends DomDiv {

		public ConditionalRoleDiv(EmfConditionalRole<R> role) {

			DomWikiDivBuilder builder = new DomWikiDivBuilder(this);
			builder.addLine(role.getRole().getTitle());
			builder.addLineBreak();
			builder.addLine(EmfI18n.IF.enclose("**"));
			builder.addLine(role.getPredicate().getTitle());
			builder.build();
		}
	}
}
