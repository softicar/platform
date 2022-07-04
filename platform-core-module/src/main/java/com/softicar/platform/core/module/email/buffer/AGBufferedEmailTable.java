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

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGBufferedEmail> attributes) {

		attributes//
			.editAttribute(AGBufferedEmail.EMAIL_SERVER)
			.setImmutable(true);
		attributes//
			.editAttribute(AGBufferedEmail.CREATED_AT)
			.setImmutable(true);
		attributes//
			.editAttribute(AGBufferedEmail.CREATED_BY)
			.setImmutable(true);
		attributes//
			.editAttribute(AGBufferedEmail.SENT_AT)
			.setEditable(false);
		attributes//
			.editAttribute(AGBufferedEmail.FROM)
			.setImmutable(true);
		attributes//
			.editAttribute(AGBufferedEmail.SENDER)
			.setImmutable(true);
		attributes//
			.editAttribute(AGBufferedEmail.REPLY_TO)
			.setImmutable(true);
		attributes//
			.editAttribute(AGBufferedEmail.TO)
			.setImmutable(true);
		attributes//
			.editAttribute(AGBufferedEmail.CC)
			.setImmutable(true);
		attributes//
			.editAttribute(AGBufferedEmail.BCC)
			.setImmutable(true);
		attributes//
			.editAttribute(AGBufferedEmail.MESSAGE_ID)
			.setImmutable(true);
		attributes//
			.editAttribute(AGBufferedEmail.IN_REPLY_TO)
			.setImmutable(true);
		attributes//
			.editAttribute(AGBufferedEmail.REFERENCES)
			.setImmutable(true);
		attributes//
			.editAttribute(AGBufferedEmail.SUBJECT)
			.setConcealed(true)
			.setImmutable(true);
		attributes//
			.editAttribute(AGBufferedEmail.CONTENT)
			.setConcealed(true)
			.setImmutable(true);
		attributes//
			.editAttribute(AGBufferedEmail.CONTENT_TYPE)
			.setImmutable(true);
		attributes//
			.editAttribute(AGBufferedEmail.AUTO_SUBMITTED)
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
