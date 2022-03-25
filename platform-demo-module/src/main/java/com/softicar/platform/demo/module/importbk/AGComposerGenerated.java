package com.softicar.platform.demo.module.importbk;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import com.softicar.platform.demo.module.DemoI18n;

/**
 * This is the automatically generated class AGComposer for
 * database table <i>Demo.Composer</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGComposerGenerated extends AbstractDbObject<AGComposer> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGComposer, AGComposerGenerated> BUILDER = new DbObjectTableBuilder<>("Demo", "Composer", AGComposer::new, AGComposer.class);
	static {
		BUILDER.setTitle(DemoI18n.COMPOSER);
		BUILDER.setPluralTitle(DemoI18n.COMPOSERS);
	}

	public static final IDbIdField<AGComposer> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(DemoI18n.ID);
	public static final IDbStringField<AGComposer> NAME = BUILDER.addStringField("name", o->o.m_name, (o,v)->o.m_name=v).setTitle(DemoI18n.NAME);
	public static final IDbStringField<AGComposer> BIRTHDAY = BUILDER.addStringField("birthday", o->o.m_birthday, (o,v)->o.m_birthday=v).setTitle(DemoI18n.BIRTHDAY);
	public static final IDbKey<AGComposer> UK_NAME_BIRTHDAY = BUILDER.addUniqueKey("nameBirthday", NAME, BIRTHDAY);
	public static final AGComposerTable TABLE = new AGComposerTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGComposer> createSelect() {

		return TABLE.createSelect();
	}

	public static AGComposer get(Integer id) {

		return TABLE.get(id);
	}

	public static AGComposer loadByNameAndBirthday(String name, String birthday) {

		return TABLE//
				.createSelect()
				.where(NAME.equal(name))
				.where(BIRTHDAY.equal(birthday))
				.getOne();
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final String getName() {

		return getValue(NAME);
	}

	public final AGComposer setName(String value) {

		return setValue(NAME, value);
	}

	public final String getBirthday() {

		return getValue(BIRTHDAY);
	}

	public final AGComposer setBirthday(String value) {

		return setValue(BIRTHDAY, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGComposerTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private String m_name;
	private String m_birthday;
}

