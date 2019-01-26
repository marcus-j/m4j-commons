package de.marcusjanke.m4j.commons.jaxb;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class JaxbException extends RuntimeException {

    public JaxbException(String message) {
        super(message);
    }

    public JaxbException(String message, Exception e) {
        super(message, e);
    }
}
