package com.tco.query;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCredential {

    private Credential credential;

    @BeforeEach
    void setUp() {
        credential = new Credential();
    }

    @AfterEach
    void tearDown() {
        credential.setTunnel(null);
        credential.onDocker(null);
    }

    @Test
    @DisplayName("bscheidt: test tunnel being true")
    public void testGetUrlUseTunnelTrue() {
        // Set the environment variable for useTunnel to true
        credential.setTunnel("true");
        String expectedUrl = "jdbc:mariadb://127.0.0.1:56247/cs314";
        String actualUrl = Credential.getUrl();
        assertEquals(expectedUrl, actualUrl, "The URL should match the tunnel configuration.");
    }

    @Test
    @DisplayName("bscheidt: test docker being true")
    public void testGetUrlOnDockerTrue() {
        // Set the environment variable for onDocker to true
        credential.onDocker("true");
        String expectedUrl = "jdbc:mariadb://127.0.0.1:3306/cs314";
        String actualUrl = Credential.getUrl();
        assertEquals(expectedUrl, actualUrl, "The URL should match the Docker configuration.");
    }

    @Test
    @DisplayName("bscheidt: test default config")
    public void testGetUrlDefault() {
        // Ensure both useTunnel and onDocker are not set or false
        // This simulates the default case where no specific environment variables are influencing the behavior
        credential.setTunnel(null);
        credential.onDocker(null);
        String expectedUrl = "jdbc:mariadb://faure.cs.colostate.edu/cs314";
        String actualUrl = Credential.getUrl();
        assertEquals(expectedUrl, actualUrl, "The URL should match the default configuration.");
    }

    @Test
    @DisplayName("bscheidt: test both variables being true")
    public void testGetUrlUseTunnelAndDockerTrue() {
        // Simulate a scenario where both environment variables are set to true
        credential.setTunnel("true");
        credential.onDocker("true");
        String expectedUrl = "jdbc:mariadb://127.0.0.1:56247/cs314"; 
        String actualUrl = Credential.getUrl();
        assertEquals(expectedUrl, actualUrl, "The URL should match the configuration when both tunnel and Docker are true.");
    }
}
