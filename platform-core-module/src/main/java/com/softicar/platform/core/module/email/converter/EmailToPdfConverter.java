package com.softicar.platform.core.module.email.converter;

import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.core.module.email.part.chooser.EmailAlternativePartsByTypeChooser;
import com.softicar.platform.core.module.email.part.sequencer.EmailPartsSequencer;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.internet.MimeMessage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;

/**
 * Converts an EML file into a PDF.
 *
 * @author Oliver Richers
 */
public class EmailToPdfConverter {

	private final static EmailAlternativePartsByTypeChooser CHOOSER = new EmailAlternativePartsByTypeChooser()//
		.addType(MimeType.TEXT_HTML)
		.addType(MimeType.TEXT_PLAIN);
	private ITextRenderer renderer;

	public byte[] convertToPdf(Supplier<InputStream> input) {

		try (var stream = input.get()) {
			return convertToPdf(new MimeMessage(null, stream));
		} catch (IOException | MessagingException exception) {
			throw new RuntimeException(exception);
		}
	}

	public byte[] convertToPdf(Part message) {

		var buffer = new ByteArrayOutputStream();
		convertToPdf(message, buffer);
		return buffer.toByteArray();
	}

	public void convertToPdf(Part message, OutputStream output) {

		this.renderer = new ITextRenderer();
		getInlineParts(message).forEach(part -> render(part, output));
		renderer.finishPDF();
	}

	private Collection<Part> getInlineParts(Part message) {

		return new EmailPartsSequencer(message)//
			.setAlternativeChooser(CHOOSER)
			.getParts()
			.stream()
			.filter(this::isInline)
			.collect(Collectors.toList());
	}

	private boolean isInline(Part part) {

		try {
			var disposition = part.getDisposition();
			return disposition == null || disposition.equalsIgnoreCase(Part.INLINE);
		} catch (MessagingException exception) {
			throw new RuntimeException(exception);
		}
	}

	private void render(Part part, OutputStream output) {

		try {
			String content = (String) part.getContent();
			if (part.isMimeType(MimeType.TEXT_HTML.getIdentifier())) {
				renderHtml(content, output);
			} else if (part.isMimeType(MimeType.TEXT_PLAIN.getIdentifier())) {
				var html = "<html><body><pre>" + escape(content) + "</pre></body></html>";
				renderHtml(html, output);
			}
		} catch (MessagingException | IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	private String escape(String text) {

		return text//
			.replace("<", "&lt;")
			.replace(">", "&gt;")
			.replace("&", "&amp;");
	}

	private void renderHtml(String html, OutputStream output) {

		var xhtml = convertHtmltoXhtml(html).replace("&nbsp;", " ");

		renderer.setDocumentFromString(xhtml);
		renderer.layout();
		renderer.createPDF(output, false);
	}

	private static String convertHtmltoXhtml(String html) {

		Document document = Jsoup.parse(html);
		document.head().append("<style type='text/css'><!--@page { margin: 0; }--></style>");
		document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
		return document.html();
	}
}
