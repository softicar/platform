package com.softicar.platform.ajax.testing.selenium.screenshot;

import com.softicar.platform.ajax.testing.selenium.AjaxSeleniumTestProperties;
import com.softicar.platform.common.io.StreamUtils;
import com.softicar.platform.common.io.file.FileOutputStreamFactory;
import com.softicar.platform.common.string.Padding;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

/**
 * Accumulates and stores PNG-formatted screenshots, for Selenium based unit
 * tests.
 * <p>
 * In case {@link AjaxSeleniumTestProperties#EXECUTION_SCREENSHOT_ON_FAILURE}
 * and {@link AjaxSeleniumTestProperties#EXECUTION_SCREENSHOT_ON_FINISHED} are
 * both <i>false</i>, screenshots will neither be taken nor accumulated.
 *
 * @author Alexander Schmidt
 */
public class AjaxSeleniumScreenshotQueue {

	private static final String TEMPORARY_DIRECTORY_PREFIX = "selenium-test";
	private final Supplier<WebDriver> webDriverSupplier;
	private final List<IAjaxSeleniumScreenshot> queuedScreenshots;
	private Path screenshotDirectory;

	/**
	 * Constructs a new {@link AjaxSeleniumScreenshotQueue}.
	 *
	 * @param webDriverSupplier
	 *            a {@link Supplier} of the {@link WebDriver} from which
	 *            screenshots shall be obtained (never <i>null</i>)
	 */
	public AjaxSeleniumScreenshotQueue(Supplier<WebDriver> webDriverSupplier) {

		this.webDriverSupplier = Objects.requireNonNull(webDriverSupplier);
		this.queuedScreenshots = new ArrayList<>();
		this.screenshotDirectory = null;
	}

	/**
	 * Takes a screenshot and adds it to the queue, using the given file name.
	 * <p>
	 * Returns the newly-taken {@link IAjaxSeleniumScreenshot}.
	 *
	 * @param fileName
	 *            the name of the screenshot file (never <i>null</i>)
	 * @return the new screenshot (never <i>null</i>)
	 */
	public IAjaxSeleniumScreenshot addNewScreenshot(String fileName) {

		IAjaxSeleniumScreenshot screenshot = createScreenshot(fileName);
		if (takeScreenshots()) {
			queuedScreenshots.add(screenshot);
		}
		return screenshot;
	}

	/**
	 * Writes all previously-queued screenshots to files inside the temporary
	 * directory, for the given {@link Description} which represents a JUnit
	 * test method.
	 *
	 * @param description
	 *            the {@link Description} of a JUnit test method (never
	 *            <i>null</i>)
	 */
	public void writeAll(Description description) {

		if (!queuedScreenshots.isEmpty()) {
			Path directory = getOrCreateScreenshotDirectory(description);
			for (int index = 0; index < queuedScreenshots.size(); index++) {
				IAjaxSeleniumScreenshot screenshot = queuedScreenshots.get(index);
				writeFile(directory, index + 1, screenshot.getFileName(), screenshot.getBytes());
			}
			queuedScreenshots.clear();
		}
	}

	private Path getOrCreateScreenshotDirectory(Description description) {

		if (screenshotDirectory == null) {
			this.screenshotDirectory = createScreenshotDirectory(description);
		}
		return screenshotDirectory;
	}

	private Path createScreenshotDirectory(Description description) {

		try {
			String directoryPrefix = String
				.format(//
					"%s-%s.%s-%s-",
					TEMPORARY_DIRECTORY_PREFIX,
					description.getClassName(),
					description.getMethodName(),
					new SimpleDateFormat("yyyy-MM-dd.HH-mm-ss.SSS").format(new Date()));
			return Files.createTempDirectory(directoryPrefix);
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	private byte[] getScreenshotBytes() {

		return ((TakesScreenshot) webDriverSupplier.get()).getScreenshotAs(OutputType.BYTES);
	}

	private void writeFile(Path directory, int number, String simpleFileName, byte[] bytes) {

		try (FileOutputStream outputStream = FileOutputStreamFactory.create(createFile(directory, number, simpleFileName))) {
			StreamUtils.copyAndClose(new ByteArrayInputStream(bytes), outputStream);
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	private File createFile(Path directory, int number, String simpleFileName) throws IOException {

		String fileName = String
			.format(//
				"%s.%s.%s",
				Padding.padLeft(number + "", '0', 3),
				simpleFileName,
				IAjaxSeleniumScreenshot.EXTENSION);
		Path filePath = directory.resolve(fileName);
		return Files.createFile(filePath).toFile();
	}

	private IAjaxSeleniumScreenshot createScreenshot(String fileName) {

		if (takeScreenshots()) {
			return new AjaxSeleniumScreenshot(fileName, getScreenshotBytes());
		} else {
			return new AjaxSeleniumDummyScreenshot();
		}
	}

	private Boolean takeScreenshots() {

		return AjaxSeleniumTestProperties.EXECUTION_SCREENSHOT_ON_FAILURE.getValue() || AjaxSeleniumTestProperties.EXECUTION_SCREENSHOT_ON_FINISHED.getValue();
	}
}
