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
import org.apache.pdfbox.rendering.PDFRenderer;

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
	 * Defines the DPI to use for rendering.
	 * <p>
	 * Default is {@value #DEFAULT_DPI}.
	 *
	 * @param dpi
	 *            the number of DPI to use (must be positive)
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
	 * Defaults to the number of CPU threads available to the {@link Runtime}.
	 *
	 * @param maxRenderingThreads
	 *            the maximum number of concurrent rendering threads (at least
	 *            1)
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
			var workers = new ArrayList<PageRenderingWorker>();
			for (var page = 0; page < document.getNumberOfPages(); page++) {
				workers.add(new PageRenderingWorker(dpi, imageType, bytes, page));
			}

			var threadRunner = new LimitedThreadRunner<PageRenderingWorker>(maxRenderingThreads);
			workers.forEach(threadRunner::addRunnable);
			do {
				threadRunner.startThreads();
				Sleep.sleep(Duration.ofMillis(10));
			} while (!threadRunner.isFinished());

			return workers//
				.stream()
				.map(PageRenderingWorker::getImage)
				.collect(Collectors.toList());
		} catch (IOException exception) {
			throw new SofticarIOException(exception, "Failed to render PDF.");
		}
	}

	private static class PageRenderingWorker implements Runnable {

		private final int dpi;
		private final ImageType imageType;
		private final byte[] bytes;
		private final int pageIndex;
		private volatile BufferedImage image;

		public PageRenderingWorker(int dpi, ImageType imageType, byte[] bytes, int pageIndex) {

			this.dpi = dpi;
			this.imageType = imageType;
			this.bytes = bytes;
			this.pageIndex = pageIndex;
			this.image = null;
		}

		@Override
		public void run() {

			try (var document = PDDocument.load(bytes)) {
				this.image = render(document);
			} catch (IOException exception) {
				throw new SofticarIOException(exception, "Failed to render PDF.");
			}
		}

		/**
		 * Returns the rendered image, or <i>null</i> if rendering has not yet
		 * concluded.
		 *
		 * @return the rendered image (may be <i>null</i>)
		 */
		public BufferedImage getImage() {

			return image;
		}

		/**
		 * Renders the pages of the {@link PDDocument} into one or more
		 * {@link BufferedImage} instances.
		 * <p>
		 * The given {@link PDDocument} will <b>not</b> be closed by this
		 * method.
		 *
		 * @param document
		 *            the {@link PDDocument} to render; needs to be closed by
		 *            the caller (never <i>null</i>)
		 * @return list of rendered images; one per page (never <i>null</i>)
		 */
		private BufferedImage render(PDDocument document) {

			var renderer = new PDFRenderer(document);
			renderer.setSubsamplingAllowed(true);
			try {
				return renderer.renderImageWithDPI(pageIndex, dpi, imageType);
			} catch (IOException exception) {
				throw new SofticarIOException(exception);
			}
		}
	}
}
