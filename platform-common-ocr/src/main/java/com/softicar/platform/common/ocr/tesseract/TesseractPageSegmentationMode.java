package com.softicar.platform.common.ocr.tesseract;

/**
 * Enumerates Tesseract page segmentation modes.
 * <p>
 * Copied from {@code "PSM_"}-prefixed constants in
 * {@link org.bytedeco.tesseract.global.tesseract}
 *
 * @author Alexander Schmidt
 */
public enum TesseractPageSegmentationMode {

	/**
	 * Automatic page segmentation, but no OSD, or OCR.
	 */
	PSM_AUTO_ONLY(2),

	/**
	 * Fully automatic page segmentation, but no OSD.
	 */
	PSM_AUTO(3),

	/**
	 * Assume a single uniform block of text. (Default.)
	 */
	PSM_SINGLE_BLOCK(6),

	/**
	 * Find as much text as possible in no particular order.
	 */
	PSM_SPARSE_TEXT(11),

	//
	;

	private final int modeIndex;

	private TesseractPageSegmentationMode(int modeIndex) {

		this.modeIndex = modeIndex;
	}

	public int getModeIndex() {

		return modeIndex;
	}
}
