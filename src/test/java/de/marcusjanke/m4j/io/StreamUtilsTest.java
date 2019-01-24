package de.marcusjanke.m4j.io;

import org.junit.Test;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import static de.marcusjanke.m4j.io.StreamUtils.gzipInputStreamStream;
import static org.assertj.core.api.Assertions.assertThat;

public class StreamUtilsTest {

    @Test
    public void shouldWrapWithGZIPInputStream() throws IOException {
        final byte[] gzippedContent = gzip("test");
        try (InputStream inputStream = gzipInputStreamStream(new ByteArrayInputStream(gzippedContent))) {
            assertThat(inputStream).isInstanceOf(GZIPInputStream.class);
        }
    }

    @Test
    public void shouldNotWrapWithGZIPInputStream() throws IOException {
        final byte[] rawContent = "test".getBytes();
        try (InputStream inputStream = gzipInputStreamStream(new ByteArrayInputStream(rawContent))) {
            assertThat(inputStream).isInstanceOf(PushbackInputStream.class);
        }
    }

    private static byte[] gzip(String data) throws IOException {
        try (
                ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length());
                GZIPOutputStream gzipOutputStream = new GZIPOutputStream(bos);
        ) {
            gzipOutputStream.write(data.getBytes());
            return bos.toByteArray();
        }
    }
}