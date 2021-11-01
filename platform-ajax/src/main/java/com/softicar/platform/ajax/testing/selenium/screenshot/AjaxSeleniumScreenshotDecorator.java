package com.softicar.platform.ajax.testing.selenium.screenshot;

import com.softicar.platform.ajax.testing.selenium.engine.common.geometry.AjaxSeleniumTestPoint;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;

class AjaxSeleniumScreenshotDecorator {

	private static final int POINT_MARKER_EXTENT = 20;

	public byte[] drawPointMarker(byte[] bytes, AjaxSeleniumTestPoint point) {

		Objects.requireNonNull(bytes);
		Objects.requireNonNull(point);

		try (ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes)) {
			int centerX = point.getX();
			int centerY = point.getY();
			BufferedImage bufferedImage = ImageIO.read(inputStream);
			Graphics2D graphics = bufferedImage.createGraphics();
			graphics.setColor(Color.RED);
			graphics
				.drawLine(//
					centerX - POINT_MARKER_EXTENT,
					centerY,
					centerX + POINT_MARKER_EXTENT,
					centerY);
			graphics
				.drawLine(//
					centerX,
					centerY - POINT_MARKER_EXTENT,
					centerX,
					centerY + POINT_MARKER_EXTENT);
			graphics
				.drawOval(//
					centerX - POINT_MARKER_EXTENT / 2,
					centerY - POINT_MARKER_EXTENT / 2,
					POINT_MARKER_EXTENT,
					POINT_MARKER_EXTENT);
			try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
				ImageIO.write(bufferedImage, IAjaxSeleniumScreenshot.EXTENSION, outputStream);
				return outputStream.toByteArray();
			}
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
}
