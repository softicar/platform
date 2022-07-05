package com.softicar.platform.core.module.email.buffer;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.server.AGServer;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbDayTimeField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;

/**
 * This is the automatically generated class AGBufferedEmail for
 * database table <i>Core.BufferedEmail</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGBufferedEmailGenerated extends AbstractDbObject<AGBufferedEmail> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGBufferedEmail, AGBufferedEmailGenerated> BUILDER = new DbObjectTableBuilder<>("Core", "BufferedEmail", AGBufferedEmail::new, AGBufferedEmail.class);
	static {
		BUILDER.setTitle(CoreI18n.BUFFERED_EMAIL);
		BUILDER.setPluralTitle(CoreI18n.BUFFERED_EMAILS);
	}

	public static final IDbIdField<AGBufferedEmail> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbBooleanField<AGBufferedEmail> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(CoreI18n.ACTIVE).setDefault(true);
	public static final IDbForeignField<AGBufferedEmail, AGServer> EMAIL_SERVER = BUILDER.addForeignField("emailServer", o->o.m_emailServer, (o,v)->o.m_emailServer=v, AGServer.ID).setTitle(CoreI18n.EMAIL_SERVER).setCascade(false, true);
	public static final IDbDayTimeField<AGBufferedEmail> CREATED_AT = BUILDER.addDayTimeField("createdAt", o->o.m_createdAt, (o,v)->o.m_createdAt=v).setTitle(CoreI18n.CREATED_AT).setDefaultNow().setTimestamp();
	public static final IDbForeignField<AGBufferedEmail, AGUser> CREATED_BY = BUILDER.addForeignField("createdBy", o->o.m_createdBy, (o,v)->o.m_createdBy=v, AGUser.ID).setTitle(CoreI18n.CREATED_BY);
	public static final IDbDayTimeField<AGBufferedEmail> SENT_AT = BUILDER.addDayTimeField("sentAt", o->o.m_sentAt, (o,v)->o.m_sentAt=v).setTitle(CoreI18n.SENT_AT).setNullable().setDefault(null).setTimestamp();
	public static final IDbStringField<AGBufferedEmail> FROM = BUILDER.addStringField("from", o->o.m_from, (o,v)->o.m_from=v).setTitle(CoreI18n.FROM).setLengthBits(16);
	public static final IDbStringField<AGBufferedEmail> SENDER = BUILDER.addStringField("sender", o->o.m_sender, (o,v)->o.m_sender=v).setTitle(CoreI18n.SENDER).setDefault("").setLengthBits(16);
	public static final IDbStringField<AGBufferedEmail> REPLY_TO = BUILDER.addStringField("replyTo", o->o.m_replyTo, (o,v)->o.m_replyTo=v).setTitle(CoreI18n.REPLY_TO).setDefault("").setLengthBits(16);
	public static final IDbStringField<AGBufferedEmail> TO = BUILDER.addStringField("to", o->o.m_to, (o,v)->o.m_to=v).setTitle(CoreI18n.TO).setDefault("").setLengthBits(16);
	public static final IDbStringField<AGBufferedEmail> CC = BUILDER.addStringField("cc", o->o.m_cc, (o,v)->o.m_cc=v).setTitle(CoreI18n.CC).setDefault("").setLengthBits(16);
	public static final IDbStringField<AGBufferedEmail> BCC = BUILDER.addStringField("bcc", o->o.m_bcc, (o,v)->o.m_bcc=v).setTitle(CoreI18n.BCC).setDefault("").setLengthBits(16);
	public static final IDbStringField<AGBufferedEmail> MESSAGE_ID = BUILDER.addStringField("messageId", o->o.m_messageId, (o,v)->o.m_messageId=v).setTitle(CoreI18n.MESSAGE_ID).setNullable().setLengthBits(16);
	public static final IDbStringField<AGBufferedEmail> IN_REPLY_TO = BUILDER.addStringField("inReplyTo", o->o.m_inReplyTo, (o,v)->o.m_inReplyTo=v).setTitle(CoreI18n.IN_REPLY_TO).setNullable().setLengthBits(16);
	public static final IDbStringField<AGBufferedEmail> REFERENCES = BUILDER.addStringField("references", o->o.m_references, (o,v)->o.m_references=v).setTitle(CoreI18n.REFERENCES).setNullable().setLengthBits(16);
	public static final IDbStringField<AGBufferedEmail> SUBJECT = BUILDER.addStringField("subject", o->o.m_subject, (o,v)->o.m_subject=v).setTitle(CoreI18n.SUBJECT).setDefault("").setLengthBits(16);
	public static final IDbStringField<AGBufferedEmail> CONTENT = BUILDER.addStringField("content", o->o.m_content, (o,v)->o.m_content=v).setTitle(CoreI18n.CONTENT).setNullable().setLengthBits(24);
	public static final IDbStringField<AGBufferedEmail> CONTENT_TYPE = BUILDER.addStringField("contentType", o->o.m_contentType, (o,v)->o.m_contentType=v).setTitle(CoreI18n.CONTENT_TYPE).setNullable().setDefault(null).setMaximumLength(255);
	public static final IDbStringField<AGBufferedEmail> AUTO_SUBMITTED = BUILDER.addStringField("autoSubmitted", o->o.m_autoSubmitted, (o,v)->o.m_autoSubmitted=v).setTitle(CoreI18n.AUTO_SUBMITTED).setNullable().setDefault(null).setMaximumLength(255);
	public static final IDbKey<AGBufferedEmail> IK_CREATED_BY = BUILDER.addIndexKey("createdBy", CREATED_BY);
	public static final IDbKey<AGBufferedEmail> IK_EMAIL_SERVER = BUILDER.addIndexKey("emailServer", EMAIL_SERVER);
	public static final AGBufferedEmailTable TABLE = new AGBufferedEmailTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGBufferedEmail> createSelect() {

		return TABLE.createSelect();
	}

	public static AGBufferedEmail get(Integer id) {

		return TABLE.get(id);
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Boolean isActive() {

		return getValue(ACTIVE);
	}

	public final AGBufferedEmail setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	public final Integer getEmailServerID() {

		return getValueId(EMAIL_SERVER);
	}

	public final AGServer getEmailServer() {

		return getValue(EMAIL_SERVER);
	}

	public final AGBufferedEmail setEmailServer(AGServer value) {

		return setValue(EMAIL_SERVER, value);
	}

	public final DayTime getCreatedAt() {

		return getValue(CREATED_AT);
	}

	public final AGBufferedEmail setCreatedAt(DayTime value) {

		return setValue(CREATED_AT, value);
	}

	public final Integer getCreatedByID() {

		return getValueId(CREATED_BY);
	}

	public final AGUser getCreatedBy() {

		return getValue(CREATED_BY);
	}

	public final AGBufferedEmail setCreatedBy(AGUser value) {

		return setValue(CREATED_BY, value);
	}

	public final DayTime getSentAt() {

		return getValue(SENT_AT);
	}

	public final AGBufferedEmail setSentAt(DayTime value) {

		return setValue(SENT_AT, value);
	}

	public final String getFrom() {

		return getValue(FROM);
	}

	public final AGBufferedEmail setFrom(String value) {

		return setValue(FROM, value);
	}

	public final String getSender() {

		return getValue(SENDER);
	}

	public final AGBufferedEmail setSender(String value) {

		return setValue(SENDER, value);
	}

	public final String getReplyTo() {

		return getValue(REPLY_TO);
	}

	public final AGBufferedEmail setReplyTo(String value) {

		return setValue(REPLY_TO, value);
	}

	public final String getTo() {

		return getValue(TO);
	}

	public final AGBufferedEmail setTo(String value) {

		return setValue(TO, value);
	}

	public final String getCc() {

		return getValue(CC);
	}

	public final AGBufferedEmail setCc(String value) {

		return setValue(CC, value);
	}

	public final String getBcc() {

		return getValue(BCC);
	}

	public final AGBufferedEmail setBcc(String value) {

		return setValue(BCC, value);
	}

	public final String getMessageId() {

		return getValue(MESSAGE_ID);
	}

	public final AGBufferedEmail setMessageId(String value) {

		return setValue(MESSAGE_ID, value);
	}

	public final String getInReplyTo() {

		return getValue(IN_REPLY_TO);
	}

	public final AGBufferedEmail setInReplyTo(String value) {

		return setValue(IN_REPLY_TO, value);
	}

	public final String getReferences() {

		return getValue(REFERENCES);
	}

	public final AGBufferedEmail setReferences(String value) {

		return setValue(REFERENCES, value);
	}

	public final String getSubject() {

		return getValue(SUBJECT);
	}

	public final AGBufferedEmail setSubject(String value) {

		return setValue(SUBJECT, value);
	}

	public final String getContent() {

		return getValue(CONTENT);
	}

	public final AGBufferedEmail setContent(String value) {

		return setValue(CONTENT, value);
	}

	public final String getContentType() {

		return getValue(CONTENT_TYPE);
	}

	public final AGBufferedEmail setContentType(String value) {

		return setValue(CONTENT_TYPE, value);
	}

	public final String getAutoSubmitted() {

		return getValue(AUTO_SUBMITTED);
	}

	public final AGBufferedEmail setAutoSubmitted(String value) {

		return setValue(AUTO_SUBMITTED, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGBufferedEmailTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private Boolean m_active;
	private AGServer m_emailServer;
	private DayTime m_createdAt;
	private AGUser m_createdBy;
	private DayTime m_sentAt;
	private String m_from;
	private String m_sender;
	private String m_replyTo;
	private String m_to;
	private String m_cc;
	private String m_bcc;
	private String m_messageId;
	private String m_inReplyTo;
	private String m_references;
	private String m_subject;
	private String m_content;
	private String m_contentType;
	private String m_autoSubmitted;
}

