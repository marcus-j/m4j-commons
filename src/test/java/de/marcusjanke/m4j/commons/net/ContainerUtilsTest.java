package de.marcusjanke.m4j.commons.net;

import org.junit.Test;

import java.net.UnknownHostException;
import java.util.Optional;
import java.util.regex.Pattern;

import static de.marcusjanke.m4j.commons.net.ContainerUtils.getJBossBindAddress;
import static de.marcusjanke.m4j.commons.net.ContainerUtils.getLocalhostAddress;
import static java.util.regex.Pattern.compile;
import static org.assertj.core.api.Assertions.assertThat;

public class ContainerUtilsTest {

    private static final Pattern IP4_PATTERN = compile("^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

    @Test
    public void shouldRetrieveJBossBindAddress() {
        String expected = "199.199.1.1";
        System.setProperty("jboss.bind.address", expected);

        assertThat(getJBossBindAddress()).isEqualTo(Optional.of(expected));
    }

    @Test
    public void shouldNotFindJBossBindAddressOnEmptySystemProperty() {
        System.getProperties().remove("jboss.bind.address");

        assertThat(getJBossBindAddress()).isEqualTo(Optional.empty());
    }

    @Test
    public void shouldRetrieveLocalhostAddress() throws UnknownHostException {
        assertThat(getLocalhostAddress()).isNotEmpty();
    }

    @Test
    public void shouldReturnLocalhostAddressAsIPv4() throws UnknownHostException {
        assertThat(IP4_PATTERN.matcher(getLocalhostAddress().get()).matches()).isTrue();
    }
}
