package com.softicar.platform.common.pdf;

import com.softicar.platform.common.io.stream.InputStreamCollection;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Supplier;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.multipdf.PDFMergerUtility.DocumentMergeMode;

/**
 * Merges a {@link Collection} of PDFs into a single PDF.
 *
 * @author Oliver Richers
 */
public class PdfMerger {

	private final Collection<Supplier<InputStream>> pdfInputStreamSuppliers;
	private final Supplier<OutputStream> outputStreamSupplier;

	public PdfMerger(Supplier<OutputStream> outputStreamSupplier) {

		this.pdfInputStreamSuppliers = new ArrayList<>();
		this.outputStreamSupplier = outputStreamSupplier;
	}

	public PdfMerger addPdf(Supplier<InputStream> pdfInputStreamSupplier) {

		pdfInputStreamSuppliers.add(pdfInputStreamSupplier);
		return this;
	}

	public void merge() {

		var mergerUtility = new PDFMergerUtility();
		try (var inputStreamCollection = new InputStreamCollection()) {
			inputStreamCollection.addAll(pdfInputStreamSuppliers);
			inputStreamCollection.getAll().forEach(mergerUtility::addSource);
			try (var outputStream = outputStreamSupplier.get()) {
				mergerUtility.setDestinationStream(outputStream);
				mergerUtility.setDocumentMergeMode(DocumentMergeMode.OPTIMIZE_RESOURCES_MODE);
				mergerUtility.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
			} catch (IOException exception) {
				throw new RuntimeException(exception);
			}
		}
	}
}
