package com.softicar.platform.common.core.logging;

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

		this.outputs = Arrays.asList(outputs);
	}

	@Override
	public void logLine(String line) {

		outputs.forEach(output -> output.logLine(line));
	}
}
