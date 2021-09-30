package com.softicar.platform.core.module.component.external.type.library;

import com.softicar.platform.common.io.xml.parser.XmlNode;
import com.softicar.platform.common.io.xml.parser.XmlParser;
import com.softicar.platform.common.io.zip.file.ZipFileReader;
import com.softicar.platform.common.string.Substring;
import com.softicar.platform.common.string.title.capitalizer.TitleCapitalizer;
import com.softicar.platform.core.module.component.external.ExternalComponent;
import com.softicar.platform.core.module.component.external.IExternalComponent;
import com.softicar.platform.core.module.component.external.key.ExternalComponentKey;
import com.softicar.platform.core.module.component.external.license.rule.ExternalComponentLicensePredefinedRules;
import com.softicar.platform.core.module.component.external.release.ExternalComponentRelease;
import com.softicar.platform.core.module.component.external.release.ExternalComponentReleaseLicenseDeterminer;
import com.softicar.platform.core.module.component.external.type.ExternalComponentType;
import com.softicar.platform.core.module.license.License;
import com.softicar.platform.core.module.license.LicenseDeterminer;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;
import java.util.jar.Manifest;
import java.util.regex.Pattern;

class ExternalLibraryFactory {

	private static final ExternalComponentType COMPONENT_TYPE = ExternalComponentType.LIBRARY;
	private static final String JAR_FILE_EXTENSION = ".jar";
	private static final Pattern JAR_FILE_VERSION_PATTERN = Pattern.compile("-([0-9].*)");
	private static final Pattern LICENSE_TXT_PATTERN = Pattern.compile("^LICENSE(.*\\.txt|$)");
	private static final String MANIFEST_MF_NAME = "MANIFEST.MF";
	private static final String POM_XML_NAME = "pom.xml";

	private final ExternalComponentRelease release;
	private final ZipFileReader zipReader;
	private final Optional<String> pomXmlContent;
	private final Optional<XmlNode> pomXmlRoot;
	private final Optional<Manifest> manifest;

	/**
	 * Constructs a new {@link ExternalLibraryFactory}.
	 *
	 * @param jarFile
	 *            the library {@code ".jar"} file (never <i>null</i>)
	 * @throws IllegalArgumentException
	 *             if the name of the given file does not end with
	 *             {@code ".jar"}
	 */
	public ExternalLibraryFactory(File jarFile) {

		Objects.requireNonNull(jarFile);
		this.release = createRelease(jarFile);
		this.zipReader = new ZipFileReader(jarFile);
		this.pomXmlContent = loadPomXmlContent();

		this.pomXmlRoot = loadPomXmlRoot();
		this.manifest = loadManifest();
	}

	/**
	 * Creates an {@link IExternalComponent} instance by extracting information
	 * from the {@code ".jar"} file.
	 * <p>
	 * Regarding our purposes here, the {@code ".jar"} file format is only
	 * loosely standardized. Therefore, the attributes of the returned
	 * {@link IExternalComponent} are set on a trial-and-error basis. That is,
	 * we try to derive them from various information sources within the
	 * {@code ".jar"} file.
	 *
	 * @return a new {@link IExternalComponent} instance (never <i>null</i>)
	 */
	public IExternalComponent create() {

		var library = new ExternalComponent(release.getKey());
		library
			.setDescription(//
				() -> getFromPomXml("name"),
				() -> getFromManifest("Implementation-Title"),
				() -> getFromManifest("Bundle-SymbolicName"),
				() -> getFromManifest("Comment"),
				() -> getDescriptionFromRelease());
		library
			.setVersion(//
				() -> getVersionFromRelease());
		library
			.setLicense(//
				() -> getLicenseFromPredefinedRules(),
				() -> getLicenseFromLicenseTxt(),
				() -> getLicenseFromPomXml(),
				() -> getLicenseFromManifestMf(),
				() -> getLicenseFromPomXmlCommentBlock());
		return library;
	}

	private ExternalComponentRelease createRelease(File jarFile) {

		String filename = getFilename(jarFile);

		String name;
		String version;
		String prefix = filename.substring(0, filename.length() - JAR_FILE_EXTENSION.length());
		var matcher = JAR_FILE_VERSION_PATTERN.matcher(prefix);
		if (matcher.find()) {
			name = prefix.substring(0, matcher.start());
			version = matcher.group(1);
		} else {
			name = prefix;
			version = null;
		}

		var key = new ExternalComponentKey(COMPONENT_TYPE, name);
		return new ExternalComponentRelease(key, version);
	}

	private String getFilename(File jarFile) {

		String filename = jarFile.getName();
		if (!filename.endsWith(JAR_FILE_EXTENSION)) {
			throw new IllegalArgumentException();
		}
		return filename;
	}

	private String getDescriptionFromRelease() {

		return new TitleCapitalizer(release.getKey().getName().replace("-", " ")).capitalize();
	}

	private String getVersionFromRelease() {

		return release.getVersion();
	}

	private String getLicenseFromPredefinedRules() {

		var rules = ExternalComponentLicensePredefinedRules.getAll();
		return new ExternalComponentReleaseLicenseDeterminer(rules)//
			.determineLicense(release)
			.map(License::getName)
			.orElse(null);
	}

	private String getLicenseFromLicenseTxt() {

		return loadLicenseTxtContent()//
			.map(new LicenseDeterminer()::determineFromText)
			.orElse(Optional.empty())
			.map(License::getName)
			.orElse(null);
	}

	private String getLicenseFromPomXml() {

		return Optional//
			.ofNullable(getFromPomXml("licenses", "license", "name"))
			.map(new LicenseDeterminer()::determineFromText)
			.orElse(Optional.empty())
			.map(License::getName)
			.orElse(null);
	}

	private String getLicenseFromPomXmlCommentBlock() {

		return pomXmlContent//
			.map(text -> Substring.between(text, "<!--", "-->"))
			.map(new LicenseDeterminer()::determineFromText)
			.orElse(Optional.empty())
			.map(License::getName)
			.orElse(null);
	}

	private String getLicenseFromManifestMf() {

		return Optional//
			.ofNullable(getFromManifest("Bundle-License"))
			.map(new LicenseDeterminer()::determineFromText)
			.orElse(Optional.empty())
			.map(License::getName)
			.orElse(null);
	}

	private String getFromPomXml(String...path) {

		return pomXmlRoot//
			.map(root -> root.getNode(path).map(XmlNode::getTextContent))
			.orElse(Optional.empty())
			.orElse(null);
	}

	private String getFromManifest(String attribute) {

		return manifest//
			.map(Manifest::getMainAttributes)
			.map(attributes -> attributes.getValue(attribute))
			.orElse(null);
	}

	private Optional<String> loadLicenseTxtContent() {

		return zipReader//
			.findFirstPath(LICENSE_TXT_PATTERN)
			.map(zipReader::readTextContent)
			.orElse(Optional.empty());
	}

	private Optional<XmlNode> loadPomXmlRoot() {

		return pomXmlContent.map(content -> new XmlParser(() -> createInputStream(content)).parse());
	}

	private Optional<String> loadPomXmlContent() {

		return zipReader//
			.findFirstPath(POM_XML_NAME)
			.map(zipReader::readTextContent)
			.orElse(Optional.empty());
	}

	private Optional<Manifest> loadManifest() {

		return loadManifestContent().map(this::parseManifestContent);
	}

	private Optional<String> loadManifestContent() {

		return zipReader//
			.findFirstPath(MANIFEST_MF_NAME)
			.map(zipReader::readTextContent)
			.orElse(Optional.empty());
	}

	private Manifest parseManifestContent(String manifestContent) {

		try (var inputStream = createInputStream(manifestContent)) {
			return new Manifest(inputStream);
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	private InputStream createInputStream(String text) {

		return new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8));
	}
}
