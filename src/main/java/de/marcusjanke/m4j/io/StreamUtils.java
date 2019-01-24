package de.marcusjanke.m4j.io;

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.zip.GZIPInputStream;

@UtilityClass
public class StreamUtils {

    private static final int AMOUNT_OF_GZIP_SIGNATURE_BYTES = 2;

    /**
     * Wraps a given {@link InputStream} in a {@link GZIPInputStream} only if it is gzipped.
     *
     * @param input
     * @return {@link InputStream}
     * @throws IOException {@link IOException}
     */
    public static InputStream gzipInputStreamStream(final InputStream input) throws IOException {
        final PushbackInputStream pushbackInputStream = new PushbackInputStream(input, AMOUNT_OF_GZIP_SIGNATURE_BYTES);
        final byte[] signature = readSignature(pushbackInputStream);
        if (isGZipSignature(signature)) {
            return new GZIPInputStream(pushbackInputStream);
        } else {
            return pushbackInputStream;
        }
    }

    private static byte[] readSignature(final PushbackInputStream pb) throws IOException {
        final byte[] bytes = new byte[StreamUtils.AMOUNT_OF_GZIP_SIGNATURE_BYTES];
        pb.read(bytes);
        pb.unread(bytes);
        return bytes;
    }

    private static boolean isGZipSignature(final byte[] signature) {
        return signature[0] == (byte) 0x1f && signature[1] == (byte) 0x8b;
    }
}
