package com.softicar.platform.core.module.file.stored.content.store;

import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.string.Trim;
import com.softicar.platform.common.string.formatting.StackTraceFormatting;
import com.softicar.platform.core.module.file.stored.repository.AGStoredFileRepository;
import java.net.MalformedURLException;
import java.util.Optional;
import java.util.regex.Pattern;

class StoredFileContentStoreFactory {

	public Optional<IStoredFileContentStore> create(AGStoredFileRepository repository) {

		try {
			var url = new SimpleUrl(repository.getUrl());
			String protocol = url.getProtocol();
			switch (protocol) {
			case "smb":
				return Optional.of(new StoredFileSmbContentStore(repository));
			case "file":
				return Optional.of(new StoredFileFileSystemContentStore(url.getPath()));
			default:
				Log.ferror("Cannot handle content store URLs that start with '%s'.", protocol);
			}
		} catch (MalformedURLException exception) {
			Log.ferror("Invalid file repository URL: %s", StackTraceFormatting.getStackTraceAsString(exception));
		}

		return Optional.empty();
	}

	/**
	 * Represents a URL in one of the following formats:
	 *
	 * <pre>
	 * file:///dir/subdir  - an absolute path
	 * file://dir/subdir   - a relative path
	 * file:/dir/subdir    - an absolute path (short form)
	 * </pre>
	 */
	private static class SimpleUrl {

		private static final Pattern PATTERN = Pattern.compile("([a-z]+):(.+)");
		private final String protocol;
		private final String path;

		public SimpleUrl(String text) throws MalformedURLException {

			var matcher = PATTERN.matcher(text);
			matcher.find();
			if (matcher.groupCount() != 2) {
				throw new MalformedURLException("Illegal URL: '%s'".formatted(text));
			}

			this.protocol = matcher.group(1).toLowerCase();
			this.path = Trim.trimPrefix(matcher.group(2), "//");
		}

		public String getProtocol() {

			return protocol;
		}

		public String getPath() {

			return path;
		}
	}
}
