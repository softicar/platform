package com.softicar.platform.common.io.file;

import com.softicar.platform.common.string.Imploder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A simple implementation of a file system path.
 * 
 * @author Oliver Richers
 */
public class Path implements IPath {

	private static final String DELIMITER = "/";
	private final IPath parent;
	private final String name;

	public Path(IPath parent, String name) {

		this.parent = parent;
		this.name = name;
	}

	public static IPath parse(String absolutePath) {

		if (!absolutePath.startsWith(DELIMITER)) {
			throw new IllegalArgumentException(String.format("Absolute path '%s' must start with delimiter '%s'.", absolutePath, DELIMITER));
		}

		List<String> elements = Arrays.asList(absolutePath.split(DELIMITER));
		Path path = null;
		for (String element: elements) {
			// the first path element is empty in any case
			// but it is not necessarily the only empty element
			if (!element.isEmpty()) {
				path = new Path(path, element);
			}
		}
		return path;
	}

	@Override
	public IPath getParent() {

		return parent;
	}

	@Override
	public String getName() {

		return name;
	}

	@Override
	public List<String> getElements() {

		List<String> elements = new ArrayList<>();
		IPath path = this;
		while (path != null) {
			elements.add(path.getName());
			path = path.getParent();
		}
		Collections.reverse(elements);
		return elements;
	}

	@Override
	public String getAbsolutePath() {

		return DELIMITER + Imploder.implode(getElements(), DELIMITER);
	}

	@Override
	public String toString() {

		return getAbsolutePath();
	}
}
