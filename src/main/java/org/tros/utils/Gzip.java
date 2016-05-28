/*
 * This work is licensed under the Creative Commons Attribution 3.0 Unported
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to Creative
 * Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
 */
package org.tros.utils;

import java.io.IOException;
import java.util.zip.GZIPInputStream;

/**
 * Gunzip a file.
 * 
 * Used in web.
 *
 * @author matta
 */
public final class Gzip {

    /**
     * Hidden Constructor.
     */
    private Gzip() {
    }

    /**
     * Gunzip.
     *
     * @param compressedFile
     * @param decompressedFile
     * @throws IOException
     */
    public final static void GunzipFile(final java.io.InputStream compressedFile, final java.io.OutputStream decompressedFile) throws IOException {

        final byte[] buffer = new byte[1024];

        final GZIPInputStream gZIPInputStream = new GZIPInputStream(compressedFile);
        int bytes_read;
        while ((bytes_read = gZIPInputStream.read(buffer)) > 0) {
            decompressedFile.write(buffer, 0, bytes_read);
        }

        decompressedFile.flush();
    }
}

