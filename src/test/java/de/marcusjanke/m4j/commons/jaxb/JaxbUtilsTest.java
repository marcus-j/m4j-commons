package de.marcusjanke.m4j.commons.jaxb;

import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;

import java.nio.charset.Charset;

import static de.marcusjanke.m4j.commons.jaxb.JaxbUtils.*;
import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;
import static javax.xml.bind.Marshaller.JAXB_ENCODING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class JaxbUtilsTest {

    @Test
    public void shouldCreateJaxbContext() {
        assertThat(jaxbContextFor(String.class)).isNotNull();
    }

    @Test
    public void shouldUseHashedJaxbContextOnSubsequentCalls() {
        JAXBContext firstJaxbContext = jaxbContextFor(String.class);
        assertThat(jaxbContextFor(String.class)).isEqualTo(firstJaxbContext);
    }

    @Test
    public void shouldThrowJaxbExceptionOnNullPointerException() {
        assertThatThrownBy(() -> jaxbContextFor(null)).isInstanceOf(JaxbException.class);
    }

    @Test
    public void shouldCreateMarshaller() {
        Marshaller actual = marshallerFor(String.class, ISO_8859_1);
        assertThat(actual).isNotNull();
    }

    @Test
    public void shouldCreateMarshallerForCharset() throws PropertyException {
        Marshaller actual = marshallerFor(String.class, ISO_8859_1);
        assertEncoding(actual, ISO_8859_1);
    }

    @Test
    public void shouldCreateMarshallerWithDefaultCharsetUtf8() throws PropertyException {
        Marshaller actual = marshallerFor(String.class);
        assertEncoding(actual, UTF_8);
    }

    private void assertEncoding(Marshaller actual, Charset charset) throws PropertyException {
        assertThat(actual.getProperty(JAXB_ENCODING)).isEqualTo(charset.name());
    }

    @Test
    public void shouldCreateUnmarshaller() {
        Unmarshaller actual = unmarshallerFor(String.class);
        assertThat(actual).isNotNull();
    }
}