package com.softicar.platform.core.module.file.stored.chunk;

import com.softicar.platform.core.module.file.stored.AGStoredFile;
import java.io.InputStream;

public class StoredFileChunksInputStream extends InputStream {

	private AGStoredFile file;
	private byte[] data;
	private int chunkIndex = -1;
	private int bufferIndex = 0;

	public StoredFileChunksInputStream(AGStoredFile file) {

		this.file = file;
		this.data = fetchChunk();
	}

	@Override
	public int available() {

		// NOTE: not quite correct but better than the default implementation
		return data != null? data.length - bufferIndex : 0;
	}

	@Override
	public void close() {

		file = null;
		data = null;
	}

	@Override
	public int read() {

		while (data != null) {
			if (bufferIndex < data.length) {
				int result = data[bufferIndex++];
				return result >= 0? result : result + 256;
			}

			data = fetchChunk();
			bufferIndex = 0;
		}

		return -1;
	}

	private byte[] fetchChunk() {

		++chunkIndex;

		AGStoredFileChunk chunk =
				AGStoredFileChunk.createSelect().where(AGStoredFileChunk.FILE.isEqual(file)).where(AGStoredFileChunk.CHUNK_INDEX.isEqual(chunkIndex)).getOne();

		return chunk != null? chunk.getChunkData() : null;
	}
}
