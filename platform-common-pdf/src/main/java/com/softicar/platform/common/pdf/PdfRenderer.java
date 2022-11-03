package com.softicar.platform.common.pdf;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.core.thread.runner.LimitedThreadRunner;
import com.softicar.platform.common.core.thread.sleep.Sleep;
import com.softicar.platform.common.io.buffer.ByteBuffer;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;

/**
 * Renders the pages of a PDF file into {@link BufferedImage} instances.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class PdfRenderer {

	private static final int DEFAULT_DPI = 300;
	private static final ImageType DEFAULT_IMAGE_TYPE = ImageType.RGB;

	private int dpi;
	private ImageType imageType;
	private int maxRenderingThreads;

	public PdfRenderer() {

		this.dpi = DEFAULT_DPI;
		this.imageType = DEFAULT_IMAGE_TYPE;
		this.maxRenderingThreads = Runtime.getRuntime().availableProcessors();
	}

	/**
	 * Defines the DPI value to use for rendering.
	 * <p>
	 * Default is {@value #DEFAULT_DPI}.
	 *
	 * @param dpi
	 *            the DPI value to use (must be positive)
	 * @return this {@link PdfRenderer}
	 */
	public PdfRenderer setDpi(int dpi) {

		this.dpi = dpi;
		return this;
	}

	/**
	 * Defines the {@link ImageType} to use for rendering.
	 * <p>
	 * Default is {@link ImageType#RGB}.
	 *
	 * @param imageType
	 *            the {@link ImageType} to use (never <i>null</i>)
	 * @return this {@link PdfRenderer}
	 */
	public PdfRenderer setImageType(ImageType imageType) {

		this.imageType = imageType;
		return this;
	}

	/**
	 * Defines the maximum number of concurrent rendering threads.
	 * <p>
	 * Defaults to {@link Runtime#availableProcessors}.
	 *
	 * @param maxRenderingThreads
	 *            the maximum number of concurrent rendering threads (must be
	 *            positive)
	 * @throws IllegalArgumentException
	 *             if the given number of threads is zero or negative
	 */
	public void setMaxRenderingThreads(int maxRenderingThreads) {

		if (maxRenderingThreads < 1) {
			throw new IllegalArgumentException();
		}
		this.maxRenderingThreads = maxRenderingThreads;
	}

	/**
	 * Renders the pages of the PDF document into one or more
	 * {@link BufferedImage} instances.
	 * <p>
	 * The given {@link InputStream} will <b>not</b> be closed by this method.
	 *
	 * @param inputStream
	 *            the {@link InputStream} providing the PDF document to render;
	 *            needs to be closed by the caller (never <i>null</i>)
	 * @return list of rendered images; one per page (never <i>null</i>)
	 */
	public List<BufferedImage> render(InputStream inputStream) {

		byte[] bytes = new ByteBuffer(() -> inputStream).getBytes();

		try (var document = PDDocument.load(bytes)) {
			var workers = new ArrayList<PdfSinglePageRenderer>();
			for (var page = 0; page < document.getNumberOfPages(); page++) {
				workers.add(new PdfSinglePageRenderer(dpi, imageType, bytes, page));
			}

			var threadRunner = new LimitedThreadRunner<PdfSinglePageRenderer>(maxRenderingThreads);
			workers.forEach(threadRunner::addRunnable);
			do {
				threadRunner.startThreads();
				Sleep.sleep(Duration.ofMillis(10));
			} while (!threadRunner.isFinished());

			return workers//
				.stream()
				.map(PdfSinglePageRenderer::getImage)
				.collect(Collectors.toList());
		} catch (IOException exception) {
			throw new SofticarIOException(exception, "Failed to render PDF.");
		}
	}
}
