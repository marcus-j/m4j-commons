package de.marcusjanke.m4j.commons.jaxb;

import lombok.experimental.UtilityClass;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.nio.charset.Charset;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Optional.ofNullable;
import static javax.xml.bind.JAXBContext.newInstance;
import static javax.xml.bind.Marshaller.JAXB_ENCODING;

@UtilityClass
public class JaxbUtils {

    private static final ConcurrentHashMap<Class<?>, JAXBContext> contexts = new ConcurrentHashMap<>();

    public static JAXBContext jaxbContextFor(Class<?> clazz) {
        return Optional.ofNullable(clazz).map(
                aClass -> contexts.computeIfAbsent(clazz, JaxbUtils::newJaxbContext)
        ).orElseThrow(() -> new JaxbException("Provided class is null"));
    }

    public static Unmarshaller unmarshallerFor(Class<?> clazz) {
        JAXBContext jaxbContext = jaxbContextFor(clazz);
        try {
            return jaxbContext.createUnmarshaller();
        } catch (JAXBException e) {
            throw new JaxbException("Could not create Unmarshaller", e);
        }
    }

    public static Marshaller marshallerFor(Class<?> clazz) {
        return marshallerFor(clazz, UTF_8);
    }

    public static Marshaller marshallerFor(Class<?> clazz, Charset charset) {
        JAXBContext jaxbContext = jaxbContextFor(clazz);
        try {
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(JAXB_ENCODING, charset.name());
            return marshaller;
        } catch (JAXBException e) {
            throw new JaxbException("Could not create Marshaller", e);
        }
    }

    private static JAXBContext newJaxbContext(Class<?> clazz) {
        try {
            return newInstance(clazz);
        } catch (JAXBException e) {
            String clazzName = ofNullable(clazz).map(Class::getName).orElse("null");
            throw new JaxbException(format("Could not create JAXBContext for class %s", clazzName), e);
        }
    }
}
