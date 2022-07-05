package com.softicar.platform.core.module.email.buffer;

import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.core.module.email.buffer.attachment.BufferedEmailAttachmentSectionDiv;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.action.EmfActionSet;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.authorizer.EmfAuthorizer;
import com.softicar.platform.emf.form.section.EmfFormSectionConfiguration;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.permission.EmfPermissions;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGBufferedEmailTable extends EmfObjectTable<AGBufferedEmail, AGCoreModuleInstance> {

	public AGBufferedEmailTable(IDbObjectTableBuilder<AGBufferedEmail> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGBufferedEmail, Integer, AGCoreModuleInstance> configuration) {

		configuration.setIcon(CoreImages.EMAIL_BUFFER);
	}

	@Override
	public void customizeAuthorizer(EmfAuthorizer<AGBufferedEmail, AGCoreModuleInstance> authorizer) {

		authorizer.setViewPermission(CorePermissions.SUPER_USER.toOtherEntityPermission());
		authorizer.setEditPermission(EmfPermissions.never());
		authorizer.setCreationPermission(EmfPermissions.never());
	}

	@Override
	public void customizeActionSet(EmfActionSet<AGBufferedEmail, AGCoreModuleInstance> actionSet) {

		actionSet.addManagementAction(new BufferedEmailDeactivateAction());
	}

	@SuppressWarnings("unchecked")
	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGBufferedEmail> attributes) {

		attributes//
			.editAttributes(//
				AGBufferedEmail.EMAIL_SERVER,
				AGBufferedEmail.CREATED_AT,
				AGBufferedEmail.CREATED_BY,
				AGBufferedEmail.FROM,
				AGBufferedEmail.SENDER,
				AGBufferedEmail.REPLY_TO,
				AGBufferedEmail.TO,
				AGBufferedEmail.CC,
				AGBufferedEmail.BCC,
				AGBufferedEmail.MESSAGE_ID,
				AGBufferedEmail.IN_REPLY_TO,
				AGBufferedEmail.REFERENCES,
				AGBufferedEmail.CONTENT_TYPE,
				AGBufferedEmail.AUTO_SUBMITTED)
			.apply(it -> it.setImmutable(true));

		attributes//
			.editAttribute(AGBufferedEmail.SENT_AT)
			.setEditable(false);
		attributes//
			.editAttribute(AGBufferedEmail.SUBJECT)
			.setConcealed(true)
			.setImmutable(true);
		attributes//
			.editAttribute(AGBufferedEmail.CONTENT)
			.setConcealed(true)
			.setImmutable(true);
	}

	@Override
	public void customizeFormSections(EmfFormSectionConfiguration<AGBufferedEmail> configuration) {

		configuration.addSection(BufferedEmailAttachmentSectionDiv::new);
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<AGBufferedEmail> loggerSet) {

		loggerSet
			.addPlainChangeLogger(AGBufferedEmailLog.BUFFERED_EMAIL, AGBufferedEmailLog.TRANSACTION)
			.addMapping(AGBufferedEmail.ACTIVE, AGBufferedEmailLog.ACTIVE);
		loggerSet.addDummyLogger().addField(AGBufferedEmail.SENT_AT);
	}
}
