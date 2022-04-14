package com.softicar.platform.common.code.java;

import com.softicar.platform.common.code.java.IdentifierNames.Flag;
import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class IdentifierNamesTest extends AbstractTest {

	@Test
	public void createsCorrectLowerCamelCase() {

		assertEquals("fooBar", IdentifierNames.Type.LOWER_CAMEL.get("foo_bar"));
	}

	@Test
	public void keepsUpperCaseFragmentsByDefault() {

		assertEquals("FooID", IdentifierNames.Type.UPPER_CAMEL.get("fooID"));
		assertEquals("fooID", IdentifierNames.Type.LOWER_CAMEL.get("fooID"));
		assertEquals("FOO_ID", IdentifierNames.Type.UPPER_WITH_UNDER_SCORE.get("fooID"));

		assertEquals("FIELDID", IdentifierNames.Type.UPPER_CAMEL.get("FIELD_ID"));
		assertEquals("fieldID", IdentifierNames.Type.LOWER_CAMEL.get("FIELD_ID"));
		assertEquals("FIELD_ID", IdentifierNames.Type.UPPER_WITH_UNDER_SCORE.get("FIELD_ID"));

		assertEquals("TCPCounter", IdentifierNames.Type.UPPER_CAMEL.get("TCPCounter"));
		assertEquals("tcpCounter", IdentifierNames.Type.LOWER_CAMEL.get("TCPCounter"));
		assertEquals("TCP_COUNTER", IdentifierNames.Type.UPPER_WITH_UNDER_SCORE.get("TCPCounter"));
	}

	@Test
	public void obeysJavaConventionsIfRequested() {

		assertEquals("FooId", IdentifierNames.Type.UPPER_CAMEL.get("fooID", Flag.OBEY_JAVA_CONVENTIONS));
		assertEquals("fooId", IdentifierNames.Type.LOWER_CAMEL.get("fooID", Flag.OBEY_JAVA_CONVENTIONS));
		assertEquals("FOO_ID", IdentifierNames.Type.UPPER_WITH_UNDER_SCORE.get("fooID", Flag.OBEY_JAVA_CONVENTIONS));

		assertEquals("FieldId", IdentifierNames.Type.UPPER_CAMEL.get("FIELD_ID", Flag.OBEY_JAVA_CONVENTIONS));
		assertEquals("fieldId", IdentifierNames.Type.LOWER_CAMEL.get("FIELD_ID", Flag.OBEY_JAVA_CONVENTIONS));
		assertEquals("FIELD_ID", IdentifierNames.Type.UPPER_WITH_UNDER_SCORE.get("FIELD_ID", Flag.OBEY_JAVA_CONVENTIONS));

		assertEquals("TcpCounter", IdentifierNames.Type.UPPER_CAMEL.get("TCPCounter", Flag.OBEY_JAVA_CONVENTIONS));
		assertEquals("tcpCounter", IdentifierNames.Type.LOWER_CAMEL.get("TCPCounter", Flag.OBEY_JAVA_CONVENTIONS));
		assertEquals("TCP_COUNTER", IdentifierNames.Type.UPPER_WITH_UNDER_SCORE.get("TCPCounter", Flag.OBEY_JAVA_CONVENTIONS));
	}
}
