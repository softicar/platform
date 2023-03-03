package com.softicar.platform.core.module.file.stored.repository;

import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.elements.wiki.DomWikiDivBuilder;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.authorizer.EmfAuthorizer;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGStoredFileRepositoryTable extends EmfObjectTable<AGStoredFileRepository, AGCoreModuleInstance> {

	public AGStoredFileRepositoryTable(IDbObjectTableBuilder<AGStoredFileRepository> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGStoredFileRepository, Integer, AGCoreModuleInstance> configuration) {

		configuration.setIcon(CoreImages.STORED_FILE_SERVER);
		configuration.addValidator(StoredFileRepositoryValidator::new);
	}

	@Override
	public void customizeAuthorizer(EmfAuthorizer<AGStoredFileRepository, AGCoreModuleInstance> authorizer) {

		authorizer.setCreationPermission(CorePermissions.ADMINISTRATION);
		authorizer.setEditPermission(CoreModule.getAdministationPermission());
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGStoredFileRepository> attributes) {

		attributes//
			.editAttribute(AGStoredFileRepository.URL)
			.setHelpDisplayFactory(this::createUrlHelpElement)
			.setPredicateMandatory(EmfPredicates.always());
		attributes//
			.editStringAttribute(AGStoredFileRepository.PASSWORD)
			.setPasswordMode(true);
	}

	private IDomElement createUrlHelpElement() {

		return new DomWikiDivBuilder()
			.addLine(CoreI18n.SUPPORTED_URLS.concatColon())
			.addUnorderedListItem(CoreI18n.ARG1_FOR_A_LOCAL_DIRECTORY.toDisplay("<code>file:///absolute/path</code>"))
			.addUnorderedListItem(CoreI18n.ARG1_FOR_AN_SMB_SHARE.toDisplay("<code>smb://host/share</code>"))
			.build();
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<AGStoredFileRepository> loggerSet) {

		loggerSet
			.addPlainChangeLogger(AGStoredFileRepositoryLog.STORED_FILE_REPOSITORY, AGStoredFileRepositoryLog.TRANSACTION)
			.addMapping(AGStoredFileRepository.ACTIVE, AGStoredFileRepositoryLog.ACTIVE)
			.addMapping(AGStoredFileRepository.URL, AGStoredFileRepositoryLog.URL)
			.addMapping(AGStoredFileRepository.DOMAIN, AGStoredFileRepositoryLog.DOMAIN)
			.addMapping(AGStoredFileRepository.USERNAME, AGStoredFileRepositoryLog.USERNAME)
			.addMapping(AGStoredFileRepository.PASSWORD, AGStoredFileRepositoryLog.PASSWORD);
	}
}
