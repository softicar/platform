package com.softicar.platform.core.module.file.stored.content;

import com.softicar.platform.common.string.hexadecimal.Hexadecimal;
import org.junit.Assert;
import org.junit.Test;

public class StoredFileContentNameTest {

	private final static String hashString = "0123456789ABCDEF0123456789ABCDEF01234567";
	private final static byte[] hash = Hexadecimal.getBytesFromHexString(hashString);

	@Test
	public void returnsCorrectFolderName() {

		String folderName = new StoredFileContentName(hash).getFolderName();

		Assert.assertEquals("/01", folderName);
	}

	@Test
	public void returnsCorrectFullFilename() {

		String filename = new StoredFileContentName(hash).getFullFilename();

		Assert.assertEquals("/01/23456789ABCDEF0123456789ABCDEF01234567", filename);
	}

	@Test
	public void returnsCorrectHashFromFilename() {

		StoredFileContentName contentName = StoredFileContentName.createFromFilename("/01/23456789ABCDEF0123456789ABCDEF01234567");

		Assert.assertArrayEquals(hash, contentName.getHash());
		Assert.assertEquals(hashString, contentName.getHashString());
	}
}
