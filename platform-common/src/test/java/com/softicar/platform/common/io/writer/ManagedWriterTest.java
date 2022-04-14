package com.softicar.platform.common.io.writer;

import com.softicar.platform.common.io.reader.ManagedReader;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.string.charset.Charsets;
import com.softicar.platform.common.testing.AbstractTest;
import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.util.Optional;
import org.junit.Test;
import org.mockito.Mockito;

public class ManagedWriterTest extends AbstractTest {

	private static final String NON_ASCII_TEXT = "äöü";
	private final StringBuilder output;
	private final ManagedWriter writer;

	public ManagedWriterTest() {

		this.output = new StringBuilder();
		this.writer = new ManagedWriter(output);
	}

	@Test
	public void testWriteWithCharacter() {

		writer.write('f');
		writer.write('o');
		writer.write('o');

		assertOutput("foo");
	}

	@Test
	public void testWriteWithCharSequence() {

		writer.write("foo");
		writer.write("bar");

		assertOutput("foobar");
	}

	@Test
	public void testWriteWithCharSequenceAndOffsets() {

		writer.write("foobar", 1, 5);

		assertOutput("ooba");
	}

	@Test
	public void testWriteWithManagedReader() {

		writer.write(new ManagedReader("foo"));

		assertOutput("foo");
	}

	@Test
	public void testWriteWithResourceAndUtf8() {

		byte[] bytes = NON_ASCII_TEXT.getBytes(Charsets.UTF8);
		IResource resource = createResource(bytes, Charsets.UTF8);

		writer.write(resource);

		assertOutput(NON_ASCII_TEXT);
	}

	@Test
	public void testWriteWithResourceAndLatin1() {

		byte[] bytes = NON_ASCII_TEXT.getBytes(Charsets.ISO_8859_1);
		IResource resource = createResource(bytes, Charsets.ISO_8859_1);

		writer.write(resource);

		assertOutput(NON_ASCII_TEXT);
	}

	private void assertOutput(String expected) {

		assertEquals(expected, output.toString());
	}

	@SuppressWarnings("resource")
	private IResource createResource(byte[] bytes, Charset charset) {

		IResource resource = Mockito.mock(IResource.class);
		Mockito.when(resource.getResourceAsStream()).thenAnswer(dummy -> new ByteArrayInputStream(bytes));
		Mockito.when(resource.getCharset()).thenReturn(Optional.of(charset));
		return resource;
	}
}
