package com.softicar.platform.common.core.logging;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * An {@link ILogOutput} that writes log lines to several outputs.
 *
 * @author Alexander Schmidt
 */
public class MultiLogOutput implements ILogOutput {

	private final Collection<ILogOutput> outputs;

	public MultiLogOutput(ILogOutput...outputs) {

		this(Arrays.asList(outputs));
	}

	public MultiLogOutput(Collection<ILogOutput> outputs) {

		this.outputs = new ArrayList<>(outputs);
	}

	@Override
	public void logLine(String line) {

		outputs.forEach(output -> output.logLine(line));
	}
}
