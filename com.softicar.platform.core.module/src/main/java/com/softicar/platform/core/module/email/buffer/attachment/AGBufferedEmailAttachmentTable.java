package com.softicar.platform.core.module.email.buffer.attachment;

import com.softicar.platform.core.module.CoreRoles;
import com.softicar.platform.core.module.email.buffer.AGBufferedEmail;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.authorization.role.EmfRoles;
import com.softicar.platform.emf.authorizer.EmfAuthorizer;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGBufferedEmailAttachmentTable extends EmfObjectTable<AGBufferedEmailAttachment, AGBufferedEmail> {

	public AGBufferedEmailAttachmentTable(IDbObjectTableBuilder<AGBufferedEmailAttachment> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGBufferedEmailAttachment, Integer, AGBufferedEmail> configuration) {

		configuration.setScopeField(AGBufferedEmailAttachment.EMAIL);
	}

	@Override
	public void customizeAuthorizer(EmfAuthorizer<AGBufferedEmailAttachment, AGBufferedEmail> authorizer) {

		authorizer.setViewRole(CoreRoles.SUPER_USER.toOtherEntityRole());
		authorizer.setEditRole(EmfRoles.nobody());
		authorizer.setCreationRole(EmfRoles.nobody());
	}
}
