package com.softicar.platform.demo.module.invoice.importing;

import com.softicar.platform.common.string.charset.Charsets;
import com.softicar.platform.demo.invoice.AbstractDemoInvoiceModuleTest;
import com.softicar.platform.demo.invoice.module.invoice.AGDemoInvoice;
import com.softicar.platform.demo.invoice.module.type.AGDemoInvoiceTypeEnum;
import com.softicar.platform.dom.event.upload.IDomFileUpload;
import com.softicar.platform.dom.event.upload.IDomFileUploadHandler;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.management.EmfManagementDivBuilder;
import com.softicar.platform.emf.management.EmfManagementMarker;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import org.junit.Test;

public class DemoInvoiceImportTest extends AbstractDemoInvoiceModuleTest {

	private static final String CSV_CONTENT = """
			%s,ACME,,123,2022-02-02,0,,,
			%s,,ACME,234,2022-02-10,1,,,
			"""
		.formatted(//
			AGDemoInvoiceTypeEnum.INBOUND.getId(),
			AGDemoInvoiceTypeEnum.OUTBOUND.getId());

	public DemoInvoiceImportTest() {

		setNodeSupplier(() -> new EmfManagementDivBuilder<>(AGDemoInvoice.TABLE, moduleInstance).build());
	}

	@Test
	public void testNormalImport() {

		findButton(EmfManagementMarker.IMPORT_BUTTON).click();

		var popup = findPopup(EmfManagementMarker.IMPORT_POPUP);
		popup//
			.findNodes()
			.filter(IDomFileUploadHandler.class::isInstance)
			.assertOne(node -> IDomFileUploadHandler.class.cast(node))
			.handleFileUploads(List.of(new DummyUpload("foo.csv", CSV_CONTENT)));
		popup.clickNode(EmfI18n.ANALYZE);
		popup.clickNode(EmfI18n.SAVE_AND_CLOSE);

		var table = findEmfDataTable(AGDemoInvoice.TABLE);
		assertEquals("123|234", table.getTextInCells(AGDemoInvoice.INVOICE_NUMBER));
		assertEquals("2022-02-02|2022-02-10", table.getTextInCells(AGDemoInvoice.INVOICE_DATE));
		assertEquals("No|Yes", table.getTextInCells(AGDemoInvoice.LOCKED_ITEMS));
	}

	private static class DummyUpload implements IDomFileUpload {

		private final String filename;
		private final String content;

		public DummyUpload(String filename, String content) {

			this.filename = filename;
			this.content = content;
		}

		@Override
		public String getFilename() {

			return filename;
		}

		@Override
		public InputStream getStream() {

			return new ByteArrayInputStream(content.getBytes(Charsets.UTF8));
		}

		@Override
		public String getContentType() {

			return "";
		}
	}
}
