package de.marcusjanke.m4j.commons.net;

import lombok.experimental.UtilityClass;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Optional;

@UtilityClass
public class ContainerUtils {

    public static Optional<String> getJBossBindAddress() {
        return Optional.ofNullable(System.getProperty("jboss.bind.address"));
    }

    public static Optional<String> getLocalhostAddress() throws UnknownHostException {
        return Optional.ofNullable(Inet4Address.getLocalHost().getHostAddress());
    }
}