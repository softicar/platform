package com.softicar.platform.core.module.email.buffer;

import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.CoreRoles;
import com.softicar.platform.core.module.email.buffer.attachment.BufferedEmailAttachmentSectionDiv;
import com.softicar.platform.core.module.module.instance.system.SystemModuleInstance;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.authorization.role.EmfRoles;
import com.softicar.platform.emf.authorizer.EmfAuthorizer;
import com.softicar.platform.emf.form.section.EmfFormSectionConfiguration;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGBufferedEmailTable extends EmfObjectTable<AGBufferedEmail, SystemModuleInstance> {

	public AGBufferedEmailTable(IDbObjectTableBuilder<AGBufferedEmail> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGBufferedEmail, Integer, SystemModuleInstance> configuration) {

		configuration.setIcon(CoreImages.EMAIL_BUFFER);
	}

	@Override
	public void customizeAuthorizer(EmfAuthorizer<AGBufferedEmail, SystemModuleInstance> authorizer) {

		authorizer.setViewRole(CoreRoles.SUPER_USER.toOtherEntityRole());
		authorizer.setEditRole(EmfRoles.nobody());
		authorizer.setCreationRole(EmfRoles.nobody());
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGBufferedEmail> attributes) {

		attributes.editAttribute(AGBufferedEmail.CONTENT).setConcealed(true);
		attributes.editAttribute(AGBufferedEmail.SUBJECT).setConcealed(true);
	}

	@Override
	public void customizeFormSections(EmfFormSectionConfiguration<AGBufferedEmail> configuration) {

		configuration.addSection(BufferedEmailAttachmentSectionDiv::new);
	}
}
