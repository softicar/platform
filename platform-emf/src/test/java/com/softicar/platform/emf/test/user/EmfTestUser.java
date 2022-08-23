package com.softicar.platform.emf.test.user;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.locale.ILocale;
import com.softicar.platform.common.core.locale.Locale;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.emf.object.AbstractEmfObject;

public class EmfTestUser extends AbstractEmfObject<EmfTestUser> implements IBasicUser {

	// @formatter:off
	public static final DbObjectTableBuilder<EmfTestUser, EmfTestUser> BUILDER = new DbObjectTableBuilder<>("Emf", "TestUser", EmfTestUser::new, EmfTestUser.class);
	public static final IDbIdField<EmfTestUser> ID = BUILDER.addIdField("id", o -> o.id, (o, v) -> o.id = v);
	public static final IDbStringField<EmfTestUser> FIRST_NAME = BUILDER.addStringField("firstName", o -> o.firstName, (o, v) -> o.firstName = v);
	public static final IDbStringField<EmfTestUser> LAST_NAME = BUILDER.addStringField("lastName", o -> o.lastName, (o, v) -> o.lastName = v);
	public static final EmfTestUserTable TABLE = new EmfTestUserTable(BUILDER);
	// @formatter:on

	private Integer id;
	private String firstName;
	private String lastName;

	public static EmfTestUser insert(String firstName, String lastName) {

		return new EmfTestUser()//
			.setFirstName(firstName)
			.setLastName(lastName)
			.save();
	}

	public String getFirstName() {

		return getValue(FIRST_NAME);
	}

	public EmfTestUser setFirstName(String firstName) {

		return setValue(FIRST_NAME, firstName);
	}

	public String getLastName() {

		return getValue(LAST_NAME);
	}

	public EmfTestUser setLastName(String lastName) {

		return setValue(LAST_NAME, lastName);
	}

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString.create(getFirstName() + " " + getLastName());
	}

	@Override
	public String getLoginName() {

		return getFirstName().toLowerCase() + "." + getLastName().toLowerCase();
	}

	@Override
	public ILocale getLocale() {

		return new Locale();
	}

	@Override
	public EmfTestUserTable table() {

		return TABLE;
	}
}
