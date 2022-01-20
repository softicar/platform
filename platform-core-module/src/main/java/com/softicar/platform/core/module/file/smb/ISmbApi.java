package com.softicar.platform.core.module.file.smb;

import java.io.InputStream;
import java.io.OutputStream;

public interface ISmbApi {

	void initialize();

	ISmbFile createFile(String url, ISmbCredentials credentials);

	InputStream createInputStream(ISmbFile file);

	OutputStream createOutputStream(ISmbFile file);
}
