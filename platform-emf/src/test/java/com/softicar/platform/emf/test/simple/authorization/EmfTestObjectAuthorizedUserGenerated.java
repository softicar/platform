package com.softicar.platform.emf.test.simple.authorization;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.emf.test.simple.EmfTestObject;
import com.softicar.platform.emf.test.user.EmfTestUser;

public class EmfTestObjectAuthorizedUserGenerated extends AbstractDbObject<EmfTestObjectAuthorizedUser> {

	// @formatter:off
	public static final DbObjectTableBuilder<EmfTestObjectAuthorizedUser, EmfTestObjectAuthorizedUserGenerated> BUILDER = new DbObjectTableBuilder<>("Test", "SimpleEntityAuthorizedUser", EmfTestObjectAuthorizedUser::new, EmfTestObjectAuthorizedUser.class);
	static {
		BUILDER.setTitle(IDisplayString.create("Test Object Authorized User"));
		BUILDER.setTitle(IDisplayString.create("Test Object Authorized Users"));
	}
	public static final IDbIdField<EmfTestObjectAuthorizedUser> ID = BUILDER.addIdField("id", o -> o.id, (o, v) -> o.id = v);
	public static final IDbForeignField<EmfTestObjectAuthorizedUser, EmfTestObject> OBJECT = BUILDER.addForeignField("object", o->o.object, (o,v)->o.object=v, EmfTestObject.ID);
	public static final IDbForeignField<EmfTestObjectAuthorizedUser, EmfTestUser> USER = BUILDER.addForeignField("user", o->o.user, (o,v)->o.user=v, EmfTestUser.ID).setNullable().setDefault(null);

	public static final EmfTestObjectAuthorizedUserTable TABLE = new EmfTestObjectAuthorizedUserTable(BUILDER);
	public static final IDbKey<EmfTestObjectAuthorizedUser> UK_OBJECT_USER = BUILDER.addUniqueKey("objectUser", OBJECT, USER);
	// @formatter:on

	private Integer id;
	private EmfTestObject object;
	private EmfTestUser user;

	public EmfTestObjectAuthorizedUser setObject(EmfTestObject object) {

		return setValue(OBJECT, object);
	}

	public EmfTestObject getObject() {

		return getValue(OBJECT);
	}

	public EmfTestObjectAuthorizedUser setUser(EmfTestUser user) {

		return setValue(USER, user);
	}

	public EmfTestUser getUser() {

		return getValue(USER);
	}

	@Override
	public EmfTestObjectAuthorizedUserTable table() {

		return TABLE;
	}
}
