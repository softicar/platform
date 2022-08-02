package com.softicar.platform.core.module.file.stored.download;

import com.softicar.platform.common.testing.Asserts;
import org.junit.Test;

public class StoredFileViewTypeTest extends Asserts {

	@Test
	public void testPdf() {

		assertSame(StoredFileViewType.PDF, StoredFileViewType.getByIdentifier("application/pdf"));
	}

	@Test
	public void testImage() {

		assertSame(StoredFileViewType.IMAGE, StoredFileViewType.getByIdentifier("image/bmp"));
		assertSame(StoredFileViewType.IMAGE, StoredFileViewType.getByIdentifier("image/gif"));
		assertSame(StoredFileViewType.IMAGE, StoredFileViewType.getByIdentifier("image/jpg"));
		assertSame(StoredFileViewType.IMAGE, StoredFileViewType.getByIdentifier("image/jepg"));
		assertSame(StoredFileViewType.IMAGE, StoredFileViewType.getByIdentifier("image/png"));
		assertSame(StoredFileViewType.IMAGE, StoredFileViewType.getByIdentifier("image/svg+xml"));
	}

	@Test
	public void testText() {

		assertSame(StoredFileViewType.TEXT, StoredFileViewType.getByIdentifier("text/css"));
		assertSame(StoredFileViewType.TEXT, StoredFileViewType.getByIdentifier("text/csv"));
		assertSame(StoredFileViewType.TEXT, StoredFileViewType.getByIdentifier("text/html"));
		assertSame(StoredFileViewType.TEXT, StoredFileViewType.getByIdentifier("text/javascript"));
		assertSame(StoredFileViewType.TEXT, StoredFileViewType.getByIdentifier("text/plain"));
		assertSame(StoredFileViewType.TEXT, StoredFileViewType.getByIdentifier("text/xml"));
	}

	@Test
	public void testMixedCases() {

		assertSame(StoredFileViewType.PDF, StoredFileViewType.getByIdentifier("Application/PDF"));
		assertSame(StoredFileViewType.IMAGE, StoredFileViewType.getByIdentifier("IMAge/pnG"));
	}

	@Test
	public void testUnknown() {

		assertSame(StoredFileViewType.UNKNOWN, StoredFileViewType.getByIdentifier("application/octet-stream"));
		assertSame(StoredFileViewType.UNKNOWN, StoredFileViewType.getByIdentifier("application/x-excel"));
	}

	@Test
	public void testIllegalIdentifiers() {

		assertSame(StoredFileViewType.UNKNOWN, StoredFileViewType.getByIdentifier(""));
		assertSame(StoredFileViewType.UNKNOWN, StoredFileViewType.getByIdentifier("/"));
		assertSame(StoredFileViewType.UNKNOWN, StoredFileViewType.getByIdentifier("/png"));
		assertSame(StoredFileViewType.UNKNOWN, StoredFileViewType.getByIdentifier("image"));
		assertSame(StoredFileViewType.UNKNOWN, StoredFileViewType.getByIdentifier("  image/png"));
	}
}
