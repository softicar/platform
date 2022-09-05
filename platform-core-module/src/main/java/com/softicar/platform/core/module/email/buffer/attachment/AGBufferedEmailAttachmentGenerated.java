package com.softicar.platform.core.module.email.buffer.attachment;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.email.buffer.AGBufferedEmail;
import com.softicar.platform.db.runtime.field.IDbByteArrayField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbIntegerField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;

/**
 * This is the automatically generated class AGBufferedEmailAttachment for
 * database table <i>Core.BufferedEmailAttachment</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGBufferedEmailAttachmentGenerated extends AbstractDbObject<AGBufferedEmailAttachment> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGBufferedEmailAttachment, AGBufferedEmailAttachmentGenerated> BUILDER = new DbObjectTableBuilder<>("Core", "BufferedEmailAttachment", AGBufferedEmailAttachment::new, AGBufferedEmailAttachment.class);
	static {
		BUILDER.setTitle(CoreI18n.BUFFERED_EMAIL_ATTACHMENT);
		BUILDER.setPluralTitle(CoreI18n.BUFFERED_EMAIL_ATTACHMENTS);
	}

	public static final IDbIdField<AGBufferedEmailAttachment> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbForeignField<AGBufferedEmailAttachment, AGBufferedEmail> EMAIL = BUILDER.addForeignField("email", o->o.m_email, (o,v)->o.m_email=v, AGBufferedEmail.ID).setTitle(CoreI18n.EMAIL).setCascade(true, true);
	public static final IDbIntegerField<AGBufferedEmailAttachment> INDEX = BUILDER.addIntegerField("index", o->o.m_index, (o,v)->o.m_index=v).setTitle(CoreI18n.INDEX);
	public static final IDbStringField<AGBufferedEmailAttachment> NAME = BUILDER.addStringField("name", o->o.m_name, (o,v)->o.m_name=v).setTitle(CoreI18n.NAME).setLengthBits(16);
	public static final IDbStringField<AGBufferedEmailAttachment> TYPE = BUILDER.addStringField("type", o->o.m_type, (o,v)->o.m_type=v).setTitle(CoreI18n.TYPE).setLengthBits(16);
	public static final IDbByteArrayField<AGBufferedEmailAttachment> DATA = BUILDER.addByteArrayField("data", o->o.m_data, (o,v)->o.m_data=v).setTitle(CoreI18n.DATA).setLengthBits(32);
	public static final IDbKey<AGBufferedEmailAttachment> UK_EMAIL_INDEX = BUILDER.addUniqueKey("emailIndex", EMAIL, INDEX);
	public static final AGBufferedEmailAttachmentTable TABLE = new AGBufferedEmailAttachmentTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGBufferedEmailAttachment> createSelect() {

		return TABLE.createSelect();
	}

	public static AGBufferedEmailAttachment get(Integer id) {

		return TABLE.get(id);
	}

	public static AGBufferedEmailAttachment loadByEmailAndIndex(AGBufferedEmail email, Integer index) {

		return TABLE//
				.createSelect()
				.where(EMAIL.isEqual(email))
				.where(INDEX.isEqual(index))
				.getOne();
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getEmailID() {

		return getValueId(EMAIL);
	}

	public final AGBufferedEmail getEmail() {

		return getValue(EMAIL);
	}

	public final AGBufferedEmailAttachment setEmail(AGBufferedEmail value) {

		return setValue(EMAIL, value);
	}

	public final Integer getIndex() {

		return getValue(INDEX);
	}

	public final AGBufferedEmailAttachment setIndex(Integer value) {

		return setValue(INDEX, value);
	}

	public final String getName() {

		return getValue(NAME);
	}

	public final AGBufferedEmailAttachment setName(String value) {

		return setValue(NAME, value);
	}

	public final String getType() {

		return getValue(TYPE);
	}

	public final AGBufferedEmailAttachment setType(String value) {

		return setValue(TYPE, value);
	}

	public final byte[] getData() {

		return getValue(DATA);
	}

	public final AGBufferedEmailAttachment setData(byte[] value) {

		return setValue(DATA, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGBufferedEmailAttachmentTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGBufferedEmail m_email;
	private Integer m_index;
	private String m_name;
	private String m_type;
	private byte[] m_data;
}

