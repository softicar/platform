package com.softicar.platform.common.pdf;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.io.stream.InputStreamCollection;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Supplier;

import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.io.RandomAccessReadBuffer;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.multipdf.PDFMergerUtility.DocumentMergeMode;

/**
 * Merges a sequence of PDFs into a single PDF.
 *
 * @author Oliver Richers
 */
public class PdfMerger {

	private final Supplier<OutputStream> outputStreamSupplier;
	private final Collection<Supplier<InputStream>> pdfInputStreamSuppliers;

	/**
	 * Takes a {@link Supplier} for the {@link OutputStream} to write the merged
	 * PDF to.
	 *
	 * @param outputStreamSupplier
	 *            the {@link Supplier} for the {@link OutputStream} (never
	 *            <i>null</i>)
	 */
	public PdfMerger(Supplier<OutputStream> outputStreamSupplier) {

		this.outputStreamSupplier = Objects.requireNonNull(outputStreamSupplier);
		this.pdfInputStreamSuppliers = new ArrayList<>();
	}

	/**
	 * Adds a PDF to be merged, by taking a {@link Supplier} of the
	 * {@link InputStream} providing the PDF.
	 *
	 * @param pdfInputStreamSupplier
	 *            the {@link Supplier} for the {@link InputStream} (never
	 *            <i>null</i>)
	 * @return this
	 */
	public PdfMerger addPdf(Supplier<InputStream> pdfInputStreamSupplier) {

		pdfInputStreamSuppliers.add(Objects.requireNonNull(pdfInputStreamSupplier));
		return this;
	}

	/**
	 * Merges all added PDFs into a single PDF.
	 * <p>
	 * The resulting PDF is written it to the {@link OutputStream} which is
	 * returned by the {@link Supplier} given to the constructor.
	 */
	public void merge() {

		var mergerUtility = new PDFMergerUtility();
		try (var inputStreamCollection = new InputStreamCollection()) {
			inputStreamCollection.addAll(pdfInputStreamSuppliers);
			inputStreamCollection.getAllInputStreams().stream().map(it-> {
                try {
                    return new RandomAccessReadBuffer(it);
                } catch (IOException e) {
                    throw new SofticarIOException(e);
                }
            }).forEach(mergerUtility::addSource);
			try (var outputStream = outputStreamSupplier.get()) {
				mergerUtility.setDestinationStream(outputStream);
				mergerUtility.setDocumentMergeMode(DocumentMergeMode.OPTIMIZE_RESOURCES_MODE);
				mergerUtility.mergeDocuments(IOUtils.createMemoryOnlyStreamCache());
			} catch (IOException exception) {
				throw new SofticarIOException(exception);
			}
		}
	}
}
