package com.softicar.platform.dom.elements.image.free.text;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.io.mime.IMimeType;
import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.hash.ResourceHash;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Optional;
import javax.imageio.ImageIO;

public class DomFreeTextImageResource implements IResource {

	private final IDomFreeTextImageConfiguration configuration;

	public DomFreeTextImageResource(IDomFreeTextImageConfiguration configuration) {

		this.configuration = configuration;
	}

	@Override
	public InputStream getResourceAsStream() {

		// create font
		Font font = new Font(configuration.getFontFamily(), configuration.getAwtFontStyle(), configuration.getFontSize());

		// get bounds
		Rectangle bounds = getTextBounds(configuration.getText(), font);

		// apply rotation
		AffineTransform transformation = new AffineTransform();
		int x, y, width, height;
		if (configuration.isRotate()) {
			x = -bounds.y;
			y = bounds.width + bounds.x;
			width = bounds.height;
			height = bounds.width;

			transformation.setToRotation(-Math.PI / 2.0, x, y);
		} else {
			x = -bounds.x;
			y = -bounds.y;
			width = bounds.width;
			height = bounds.height;

			transformation.setToIdentity();
		}

		// create image
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = image.createGraphics();

		try {
			// fill background
			graphics.setColor(new Color(0, true));
			graphics.fillRect(0, 0, image.getWidth(), image.getHeight());

			// draw text
			graphics.setFont(font);
			int fontAlpha = configuration.getFontAlpha();
			int rgbValue = configuration.getFontColor().getRgbValue();
			graphics.setColor(fontAlpha == 255? new Color(rgbValue) : new Color((fontAlpha << 24) + rgbValue, true));
			graphics.setTransform(transformation);
			if (configuration.isAntiAliasing()) {
				graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			}
			graphics.drawString(configuration.getText(), x, y);

			ByteArrayOutputStream output = new ByteArrayOutputStream();
			ImageIO.write(image, "png", output);
			return new ByteArrayInputStream(output.toByteArray());
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		} finally {
			// cleanup
			graphics.dispose();
		}
	}

	private static Rectangle getTextBounds(String text, Font font) {

		// using a tiny image as dummy to create the Graphics2D object
		BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = image.createGraphics();

		// computing the pixel bounds of the text layout
		FontRenderContext renderContext = graphics.getFontRenderContext();
		TextLayout layout = new TextLayout(text, font, renderContext);
		return layout.getPixelBounds(renderContext, 0, 0);
	}

	@Override
	public IMimeType getMimeType() {

		return MimeType.IMAGE_PNG;
	}

	@Override
	public Optional<Charset> getCharset() {

		return Optional.empty();
	}

	@Override
	public Optional<String> getFilename() {

		return Optional.empty();
	}

	@Override
	public Optional<ResourceHash> getContentHash() {

		return Optional.empty();
	}
}
