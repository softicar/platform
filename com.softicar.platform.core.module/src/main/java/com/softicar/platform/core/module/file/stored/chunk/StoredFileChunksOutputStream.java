package com.softicar.platform.core.module.file.stored.chunk;

import com.softicar.platform.core.module.file.stored.AGStoredFile;
import java.io.OutputStream;

public class StoredFileChunksOutputStream extends OutputStream {

	public static final int MAX_CHUNK_SIZE = 4096;

	private final AGStoredFile storedFile;
	private final byte[] buffer = new byte[MAX_CHUNK_SIZE];
	private int bufferIndex = 0;
	private int chunkIndex = 0;

	public StoredFileChunksOutputStream(AGStoredFile storedFile) {

		this.storedFile = storedFile;

		if (storedFile.getId() == null) {
			storedFile.save();
		}
	}

	@Override
	public void close() {

		flush();
	}

	@Override
	public void flush() {

		if (bufferIndex > 0) {
			// copy data
			byte[] data = new byte[bufferIndex];
			for (int i = 0; i != bufferIndex; ++i) {
				data[i] = buffer[i];
			}

			// save chunk
			AGStoredFileChunk chunk = new AGStoredFileChunk();
			chunk.setFile(AGStoredFile.TABLE.getStub(storedFile.getId()));
			chunk.setChunkIndex(chunkIndex);
			chunk.setChunkData(data);
			chunk.setChunkSize(data.length);
			chunk.save();

			// update indexes
			bufferIndex = 0;
			++chunkIndex;
		}
	}

	@Override
	public void write(int b) {

		if (bufferIndex >= MAX_CHUNK_SIZE) {
			flush();
		}

		buffer[bufferIndex++] = (byte) b;
	}
}
