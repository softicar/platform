package com.softicar.platform.core.module.file.stored.content;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.io.StreamUtils;
import com.softicar.platform.common.string.hash.Hash;
import com.softicar.platform.common.string.hexadecimal.Hexadecimal;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import org.junit.Ignore;
import org.junit.Test;

// FIXME: this should not really connect to the database
public class StoredFileInputStreamCreatorSystemTest extends AbstractTest {

	public StoredFileInputStreamCreatorSystemTest() {

	}

	@Test
	@Ignore
	public void readsContentCorrectlyFromSmbStore() {

		checkFile(979200, "81397D17358D398CD103ECA4DDC8FE5FCEB4ABB1");
	}

	@Test
	@Ignore
	public void readsContentCorrectlyFromChunkStore() {

		checkFile(970913, "C35DB814B64AAD288D0F5F8CD9B706B4DC1AA2F4");
	}

	@Test
	@Ignore
	public void readsContentCorrectlyFromFieldStore() {

		checkFile(978905, "8902F491C6656397C53CA1107D8327C188813172");
	}

	private static void checkFile(int id, String expectedHash) {

		try (InputStream inputStream = AGStoredFile.TABLE.getStub(id).getFileContentInputStream()) {
			MessageDigest messageDigest = Hash.SHA1.createDigest();

			StreamUtils.copy(inputStream, messageDigest);

			String actualHash = Hexadecimal.getHexStringUC(messageDigest.digest());
			assertEquals(expectedHash, actualHash);
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}
}
