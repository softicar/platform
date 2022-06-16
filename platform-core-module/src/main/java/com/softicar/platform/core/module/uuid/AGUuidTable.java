package com.softicar.platform.core.module.uuid;

import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.attribute.field.indirect.entity.foreign.EmfForeignIndirectEntityAttribute;
import com.softicar.platform.emf.authorizer.EmfAuthorizer;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.permission.EmfPermissions;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGUuidTable extends EmfObjectTable<AGUuid, AGCoreModuleInstance> {

	public AGUuidTable(IDbObjectTableBuilder<AGUuid> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGUuid, Integer, AGCoreModuleInstance> configuration) {

		configuration.setIcon(CoreImages.UUID);
		configuration.setAttributeFactory(EmfForeignIndirectEntityAttribute::new);
		configuration.setEditPredicate(EmfPredicates.never());
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGUuid> attributes) {

		attributes//
			.editAttribute(AGUuid.UUID_BYTES)
			.setConcealed(true);
	}

	@Override
	public void customizeAuthorizer(EmfAuthorizer<AGUuid, AGCoreModuleInstance> authorizer) {

		authorizer.setCreationPermission(EmfPermissions.never());
	}
}
