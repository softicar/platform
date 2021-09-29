package com.softicar.platform.emf.trait;

import com.softicar.platform.db.runtime.table.IDbTableBuilder;
import com.softicar.platform.emf.EmfTestRoles;
import com.softicar.platform.emf.authorizer.EmfAuthorizer;
import com.softicar.platform.emf.test.simple.EmfTestObject;
import com.softicar.platform.emf.trait.table.EmfTraitTable;

public class EmfTestTraitTable extends EmfTraitTable<EmfTestTrait, EmfTestObject> {

	public EmfTestTraitTable(IDbTableBuilder<EmfTestTrait, EmfTestObject> builder) {

		super(builder);
	}

	@Override
	public void customizeAuthorizer(EmfAuthorizer<EmfTestTrait, EmfTestObject> authorizer) {

		authorizer.setCreationRole(EmfTestRoles.AUTHORIZED_USER);
	}
}
